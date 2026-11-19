package com.campusoverflow.qa.application.query;

import com.campusoverflow.identity.api.IdentityApi;
import com.campusoverflow.identity.api.UserSummary;
import com.campusoverflow.qa.application.port.ContentRenderer;
import com.campusoverflow.qa.application.port.QaReadModel;
import com.campusoverflow.qa.application.port.QuestionListCache;
import com.campusoverflow.qa.application.port.ViewCounter;
import com.campusoverflow.qa.domain.TargetType;
import com.campusoverflow.shared.domain.NotFoundException;
import com.campusoverflow.shared.page.PageRequest;
import com.campusoverflow.shared.page.PageResult;
import com.campusoverflow.shared.security.Actor;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 问答查询用例（读路径）。 */
@Service
@Transactional(readOnly = true)
public class QuestionQueryService {

    private static final int EXCERPT_LENGTH = 160;

    private final QaReadModel readModel;
    private final IdentityApi identity;
    private final ContentRenderer renderer;
    private final ViewCounter viewCounter;
    private final QuestionListCache cache;

    public QuestionQueryService(QaReadModel readModel, IdentityApi identity, ContentRenderer renderer,
                                ViewCounter viewCounter, QuestionListCache cache) {
        this.readModel = readModel;
        this.identity = identity;
        this.renderer = renderer;
        this.viewCounter = viewCounter;
        this.cache = cache;
    }

    public PageResult<QuestionSummaryView> list(Long courseId, PageRequest page) {
        return cache.get(courseId, page).orElseGet(() -> {
            PageResult<QuestionRow> rows = readModel.listQuestions(courseId, page);
            Map<Long, UserSummary> authors = identity.findUsers(
                    rows.items().stream().map(QuestionRow::authorId).collect(Collectors.toSet()));
            PageResult<QuestionSummaryView> view = rows.map(row -> new QuestionSummaryView(
                    row.id(),
                    row.title(),
                    renderer.toPlainText(row.body(), EXCERPT_LENGTH),
                    row.tags(),
                    row.courseId(),
                    AuthorView.of(row.authorId(), authors.get(row.authorId())),
                    row.score(),
                    row.answerCount(),
                    row.viewCount(),
                    row.acceptedAnswerId() != null,
                    row.createdAt()));
            cache.put(courseId, page, view);
            return view;
        });
    }

    public QuestionDetailView detail(long questionId, Actor actorOrNull) {
        QuestionRow question = readModel.findQuestion(questionId)
                .orElseThrow(() -> new NotFoundException("QA-4001", "问题不存在或已被删除"));
        viewCounter.increment(questionId);

        List<AnswerRow> answers = readModel.findAnswers(questionId);
        List<CommentRow> comments = readModel.findComments(questionId);
        Set<Long> userIds = new HashSet<>();
        userIds.add(question.authorId());
        answers.forEach(a -> {
            userIds.add(a.authorId());
            if (a.endorsedBy() != null) {
                userIds.add(a.endorsedBy());
            }
        });
        comments.forEach(c -> userIds.add(c.authorId()));
        Map<Long, UserSummary> users = identity.findUsers(userIds);

        Map<String, Integer> myVotes = actorOrNull == null
                ? Map.of()
                : readModel.findMyVotes(actorOrNull.userId(), questionId,
                        answers.stream().map(AnswerRow::id).toList());

        CourseRefView course = question.courseId() == null
                ? null
                : identity.findCourse(question.courseId())
                        .map(c -> new CourseRefView(c.id(), c.code(), c.name()))
                        .orElse(null);

        boolean manages = actorOrNull != null && identity.canManageCourse(actorOrNull.userId(), question.courseId());
        boolean isAsker = actorOrNull != null && actorOrNull.is(question.authorId());
        PermissionsView permissions = actorOrNull == null
                ? PermissionsView.NONE
                : new PermissionsView(isAsker || manages,
                        manages || (isAsker && question.answerCount() == 0),
                        (isAsker || manages) && question.acceptedAnswerId() == null,
                        manages);

        List<CommentView> questionComments = toCommentViews(comments, TargetType.QUESTION, questionId, users);
        List<AnswerView> answerViews = answers.stream()
                .map(row -> new AnswerView(
                        row.id(),
                        row.body(),
                        renderer.toSafeHtml(row.body()),
                        AuthorView.of(row.authorId(), users.get(row.authorId())),
                        row.score(),
                        question.acceptedAnswerId() != null && question.acceptedAnswerId() == row.id(),
                        row.endorsedBy() == null ? null : AuthorView.of(row.endorsedBy(), users.get(row.endorsedBy())),
                        myVotes.getOrDefault(voteKey(TargetType.ANSWER, row.id()), 0),
                        actorOrNull != null && (actorOrNull.is(row.authorId()) || manages),
                        row.createdAt(),
                        row.updatedAt(),
                        toCommentViews(comments, TargetType.ANSWER, row.id(), users)))
                .toList();

        return new QuestionDetailView(
                question.id(),
                question.title(),
                question.body(),
                renderer.toSafeHtml(question.body()),
                question.tags(),
                course,
                AuthorView.of(question.authorId(), users.get(question.authorId())),
                question.score(),
                question.answerCount(),
                question.viewCount(),
                question.acceptedAnswerId(),
                myVotes.getOrDefault(voteKey(TargetType.QUESTION, questionId), 0),
                question.createdAt(),
                question.updatedAt(),
                permissions,
                questionComments,
                answerViews);
    }

    public List<TagView> popularTags(int limit) {
        return readModel.popularTags(limit);
    }

    private static List<CommentView> toCommentViews(List<CommentRow> rows, TargetType type, long targetId,
                                                    Map<Long, UserSummary> users) {
        return rows.stream()
                .filter(r -> type.name().equals(r.targetType()) && r.targetId() == targetId)
                .map(r -> new CommentView(r.id(), AuthorView.of(r.authorId(), users.get(r.authorId())), r.parentId(),
                        r.body(), r.createdAt()))
                .toList();
    }

    private static String voteKey(TargetType type, long targetId) {
        return type.name() + ":" + targetId;
    }
}
