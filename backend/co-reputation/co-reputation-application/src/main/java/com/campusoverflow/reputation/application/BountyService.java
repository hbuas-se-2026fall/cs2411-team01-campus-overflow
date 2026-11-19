package com.campusoverflow.reputation.application;

import com.campusoverflow.identity.api.IdentityApi;
import com.campusoverflow.identity.api.UserSummary;
import com.campusoverflow.qa.api.QaQueryApi;
import com.campusoverflow.qa.api.QuestionRef;
import com.campusoverflow.reputation.api.event.BountyAwardedEvent;
import com.campusoverflow.reputation.api.event.BountyClosedEvent;
import com.campusoverflow.reputation.api.event.BountyOpenedEvent;
import com.campusoverflow.reputation.domain.Bounty;
import com.campusoverflow.reputation.domain.BountyRepository;
import com.campusoverflow.reputation.domain.BountyStatus;
import com.campusoverflow.reputation.domain.ReputationAccount;
import com.campusoverflow.reputation.domain.ReputationAccountRepository;
import com.campusoverflow.reputation.domain.ReputationReason;
import com.campusoverflow.shared.audit.AuditTrail;
import com.campusoverflow.shared.domain.ConflictException;
import com.campusoverflow.shared.domain.NotFoundException;
import com.campusoverflow.shared.event.EventIds;
import com.campusoverflow.shared.event.IntegrationEventPublisher;
import com.campusoverflow.shared.security.Actor;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 用例：悬赏的发起、结算、到期退款。积分冻结与悬赏状态在同一事务中变更。 */
@Service
public class BountyService {

    private final BountyRepository bounties;
    private final ReputationAccountRepository accounts;
    private final ReputationLedgerService ledgerService;
    private final QaQueryApi qa;
    private final IdentityApi identity;
    private final IntegrationEventPublisher events;
    private final AuditTrail audit;
    private final Clock clock;

    public BountyService(BountyRepository bounties, ReputationAccountRepository accounts,
                         ReputationLedgerService ledgerService, QaQueryApi qa, IdentityApi identity,
                         IntegrationEventPublisher events, AuditTrail audit, Clock clock) {
        this.bounties = bounties;
        this.accounts = accounts;
        this.ledgerService = ledgerService;
        this.qa = qa;
        this.identity = identity;
        this.events = events;
        this.audit = audit;
        this.clock = clock;
    }

    @Transactional
    public long open(Actor actor, long questionId, int points, int days) {
        QuestionRef question = qa.findQuestion(questionId)
                .orElseThrow(() -> new NotFoundException("REP-4001", "问题不存在或已被删除"));
        if (question.deleted() || question.acceptedAnswerId() != null) {
            throw new ConflictException("REP-2002", "问题已删除或已采纳，不能再发起悬赏");
        }
        if (bounties.findOpenByQuestion(questionId).isPresent()) {
            throw new ConflictException("REP-2003", "该问题已有进行中的悬赏");
        }
        boolean manages = identity.canManageCourse(actor.userId(), question.courseId());
        Instant now = clock.instant();
        Bounty bounty = Bounty.open(questionId, true, actor, manages, points, days, now);
        ReputationAccount sponsor = accounts.findByUserId(actor.userId())
                .orElseGet(() -> ReputationAccount.open(actor.userId(), 0));
        sponsor.freezePoints(points);
        accounts.save(sponsor);
        Bounty saved = bounties.save(bounty);
        audit.record("BOUNTY_OPENED", actor.userId(), "BOUNTY", String.valueOf(saved.id()),
                "发起悬赏 " + points + " 积分，有效期 " + days + " 天");
        events.publish(new BountyOpenedEvent(EventIds.next(), now, saved.id(), questionId, actor.userId(),
                points, saved.expiresAt()));
        return saved.id();
    }

    /** 由 AnswerAccepted 事件触发（在处理器事务内）。幂等：悬赏已结束则直接返回。 */
    @Transactional(propagation = Propagation.MANDATORY)
    public void settleOnAccept(long questionId, long answerId, long winnerId, String sourceEventId) {
        Optional<Bounty> open = bounties.findOpenByQuestion(questionId);
        if (open.isEmpty()) {
            return;
        }
        Bounty bounty = open.get();
        Instant now = clock.instant();
        ReputationAccount sponsor = accounts.findByUserId(bounty.sponsorId()).orElse(null);
        if (winnerId == bounty.sponsorId()) {
            // 发起人自己的回答被采纳：悬赏不予发放，关闭并退回冻结积分
            closeAndRefund(bounty, sponsor, now);
            return;
        }
        bounty.award(answerId, winnerId, now);
        bounties.save(bounty);
        if (sponsor != null) {
            sponsor.spendFrozen(bounty.points());
            accounts.save(sponsor);
        }
        ledgerService.record(winnerId, sourceEventId, ReputationReason.BOUNTY_AWARDED, bounty.points(),
                "BOUNTY", bounty.id() == null ? questionId : bounty.id());
        audit.record("BOUNTY_AWARDED", winnerId, "BOUNTY", String.valueOf(bounty.id()),
                "悬赏发放 " + bounty.points() + " 积分");
        events.publish(new BountyAwardedEvent(EventIds.next(), now, bounty.id() == null ? 0L : bounty.id(),
                questionId, bounty.sponsorId(), winnerId, bounty.points()));
    }

    /** 到期未采纳的悬赏：关闭并退回冻结积分。由定时任务调用。 */
    @Transactional
    public int expireDue() {
        Instant now = clock.instant();
        int closed = 0;
        for (Bounty bounty : bounties.findDue(now, 100)) {
            if (bounty.status() != BountyStatus.OPEN) {
                continue;
            }
            ReputationAccount sponsor = accounts.findByUserId(bounty.sponsorId()).orElse(null);
            closeAndRefund(bounty, sponsor, now);
            closed++;
        }
        return closed;
    }

    @Transactional(readOnly = true)
    public Optional<BountyView> latest(long questionId) {
        return bounties.findLatestByQuestion(questionId).map(bounty -> new BountyView(
                bounty.id() == null ? 0L : bounty.id(),
                bounty.questionId(),
                bounty.sponsorId(),
                identity.findUser(bounty.sponsorId()).map(UserSummary::displayName).orElse("已注销用户"),
                bounty.points(),
                bounty.status().name(),
                bounty.createdAt(),
                bounty.expiresAt(),
                bounty.winnerId(),
                bounty.answerId()));
    }

    private void closeAndRefund(Bounty bounty, ReputationAccount sponsor, Instant now) {
        bounty.close(now);
        bounties.save(bounty);
        if (sponsor != null) {
            sponsor.refundFrozen(bounty.points());
            accounts.save(sponsor);
        }
        audit.record("BOUNTY_CLOSED", bounty.sponsorId(), "BOUNTY", String.valueOf(bounty.id()),
                "悬赏关闭并退回 " + bounty.points() + " 积分");
        events.publish(new BountyClosedEvent(EventIds.next(), now, bounty.id() == null ? 0L : bounty.id(),
                bounty.questionId(), bounty.sponsorId(), bounty.points()));
    }
}
