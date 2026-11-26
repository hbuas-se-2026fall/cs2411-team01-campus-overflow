# CampusOverflow 团队看板（Scrum Board）

> 负责人：陈洲（SM）｜ 快照日期：2026-11-26
> 看板列：**To Do / In Progress / In Review / Done**
> 数据来源：`docs/sprint/sprint-backlog.md`、`docs/backlog/product-backlog.md`；用户故事卡片为对应 Issue，In Review 列挂接开放 PR。

## 列定义与流转

| 列 | 含义 | 进入条件 |
|---|---|---|
| To Do | 已排期但未开始，或 P2 候选与跨故事待办 | 已写入 Backlog，尚未进入开发 |
| In Progress | 后端已实现，前端交互或测试待补 | 已有分支/提交，仍在开发 |
| In Review | 代码已提交 PR，待队友评审批准 | PR 打开、CI 跑完，等待 LGTM |
| Done | 已落地并通过 CI，合并进 main | 评审通过并合并、CI 全绿 |

## To Do（7）

| 卡片 | 优先级 | SP | 负责人 | 说明 |
|---|---|---|---|---|
| [#20 US-016](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/20) 徽章系统 | P2 | 5 | 待 PO 排期 | 里程碑徽章；纳入 Sprint 4 前由 PO 结合反馈重新排序，待补 Gherkin 验收标准 |
| [#21 US-017](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/21) 编辑问题 | P2 | 2 | 待 PO 排期 | 无回答可编辑、有回答仅补充说明；待补验收标准 |
| [#22 US-018](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/22) 收藏问题 | P2 | 2 | 待 PO 排期 | 收藏列表、取消收藏、收藏数统计；待补验收标准 |
| [#23 US-019](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/23) Markdown 实时预览 | P2 | 3 | 待 PO 排期 | 分屏预览、常用语法按钮；待补验收标准 |
| [#24 US-020](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/24) 移动端适配 | P2 | 5 | 待 PO 排期 | 响应式布局与手机端可用性；待补验收标准 |
| [#25 TASK-CI](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/25) 覆盖率补齐与 Sonar 质量门禁接入 | — | 0 | 袁有为 / 彭天赐 / 陈洲 | 提升领域层测试覆盖率至阈值；SonarCloud 项目 Key 与质量门禁待 PO/管理员确认 |
| [#26 TASK-REVIEW](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/26) 跨成员代码评审记录（≥4 份，每份 ≥5 条实质意见） | — | 0 | 全员 | 按 docs/review/code-review-checklist-v1.0.md 执行并留存评审记录 |

## In Progress（11）

| 卡片 | 优先级 | SP | 负责人 | 说明 |
|---|---|---|---|---|
| [#9 US-001](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/9) 注册账号 | P0 | 3 | 袁有为(后端)/彭天赐(前端) | 注册校验、密码加密、昵称唯一；注册表单与错误提示 |
| [#10 US-002](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/10) 登录退出 | P0 | 2 | 袁有为(后端)/彭天赐(前端) | 会话保持、失败锁定、会话轮换；登录态读写与退出 |
| [#11 US-006](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/11) 回答问题 | P0 | 5 | 袁有为(后端)/彭天赐(前端) | 回答落库、通知提问者、禁止自答；回答框与提交反馈 |
| [#12 US-007](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/12) 投票（赞/踩） | P0 | 5 | 袁有为(后端)/彭天赐(前端) | 每人每篇一票、可改票、自投禁止；投票按钮与实时票数 |
| [#13 US-008](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/13) 采纳回答 | P0 | 3 | 袁有为(后端)/彭天赐(前端) | 仅提问者可采纳、唯一采纳、问题置已解决；采纳操作与状态展示 |
| [#14 US-009](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/14) 声誉积分系统 | P1 | 5 | 袁有为(后端)/彭天赐(前端) | 投票/采纳/踩的声誉结算与账本流水；个人声誉分展示 |
| [#15 US-011](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/15) 中文搜索 | P1 | 5 | 彭天赐(全栈) | 标题/正文检索、中文分词、相关度排序；搜索框与结果展示 |
| [#16 US-012](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/16) @提及通知 | P1 | 5 | 彭天赐(全栈) | @昵称检测、通知落库、未读标记；通知列表与未读角标 |
| [#17 US-013](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/17) 悬赏问题 | P1 | 5 | 袁有为(后端)/彭天赐(前端) | 悬赏额度与期限、扣减与发放；悬赏弹窗与额度展示 |
| [#18 US-014](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/18) 评论 | P1 | 3 | 袁有为(后端)/彭天赐(前端) | 评论 ≤500 字、按时间排序、不可投票；评论区与发表评论 |
| [#19 US-015](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/19) 相关问题推荐 | P1 | 3 | 彭天赐(全栈) | 按标签与标题相似度推荐、最多 5 条；详情页相关推荐位 |

## In Review（4）

| PR | 标题 | 负责人 |
|---|---|---|
| [#1](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/pull/1) | PR 模板与分支保护文档（Sprint 2） | 陈洲 |
| [#2](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/pull/2) | CI 质量门禁：Checkstyle / Sonar / 前端 format（Sprint 3） | 陈洲 |
| [#3](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/pull/3) | jlink 精简镜像、GHCR 推送与条件化自动部署（Sprint 4） | 陈洲 |
| [#4](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/pull/4) | Backlog 口径复核与 Sprint4 进度审计 | 张宇航 |

## Done（4）

| 卡片 | 优先级 | SP | 负责人 | 说明 |
|---|---|---|---|---|
| [#5 US-003](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/5) 发布问题 | P0 | 5 | 袁有为(后端)/彭天赐(前端) | 标题/正文/标签校验、问题聚合落库；提问表单与 Markdown 编辑器 |
| [#6 US-004](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/6) 浏览问题列表 | P0 | 3 | 袁有为(后端)/彭天赐(前端) | 分页、排序、标签/课程筛选；列表页与分页控件 |
| [#7 US-005](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/7) 查看问题详情 | P0 | 3 | 袁有为(后端)/彭天赐(前端) | 详情读模型、回答按票数排序；Markdown 渲染与回答区 |
| [#8 US-010](https://github.com/hbuas-se-2026fall/cs2411-team01-campus-overflow/issues/8) 排行榜 | P1 | 3 | 袁有为(后端)/彭天赐(前端) | 按声誉排序 Top N、短时缓存；排行榜页面 |

## 状态一致性核对

- Sprint Backlog 图例与本看板列对应：✅ 已落地 → Done；🟡 后端已实现、前端/测试待补 → In Progress；⬜ 未开始 → To Do。
- 每次 Daily Standup 与 Sprint 回顾会后，同步更新本快照与对应 Issue 的状态；PR 合并后把对应故事卡片移入 Done。
- P2 候选故事（US-016~020）在纳入排期前，由 PO 补齐 Gherkin 验收标准。

## 在 GitHub Projects（v2）中落地本看板

> 当前自动化令牌未授予 `project` 权限，无法经接口创建 Projects v2；看板实体需在网页创建一次，Issue/PR 卡片已备好可直接挂接。

1. 仓库页面 → **Projects** → **New project** → 选 **Board** 布局，命名 `CampusOverflow 看板`，并 Link 本仓库。
2. 确认 **Status** 字段选项为：`To Do`、`In Progress`、`In Review`、`Done`（如缺 `In Review`，在字段设置中补齐）。
3. **Add items**：加入本仓 Issue `#5`–`#26`；再把 PR `#1`–`#4` 加入看板，置于 In Review 列。
4. 按上表将各卡片拖入对应列；后续在 Issue/PR 右侧 Projects 面板切换状态，本 Markdown 作为离线快照与评审基线。
5. 截图作为 Lab 1 提交物（看板初始 Backlog 不少于 5 条）。

## 当前阻塞与待办

- **评审**：PR #1–#4 待 Dev/PO 批准；分支保护要求至少 1 名评审 LGTM，SM 不能自批。
- **PO 张宇航**：P2 故事排期前补验收标准；确认 SonarCloud 组织/项目 Key 与质量门禁阈值。
- **Dev 袁有为 / 彭天赐**：清理既有 lint 警告、补齐领域层覆盖率；按评审清单完成 ≥4 份评审记录。
