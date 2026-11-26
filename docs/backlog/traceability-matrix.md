# 需求追溯矩阵（Traceability Matrix）

> PO：张宇航 | 更新日期：2026-11-26
> 用途：把每条用户故事从验收标准一路追溯到后端实现、前端页面与测试覆盖，供 Sprint Review 答辩与课程验收核对。
> 测试覆盖口径：领域/应用层单元测试 + `CampusOverflowFlowIT` 端到端集成测试（Testcontainers MySQL8/Redis7）。
> 状态图例：✅ 已落地并通过 CI；🟡 主链路已通，存在测试盲点或局部交互待补；❌ 未实现。

## P0 — 核心问答链路（Sprint 2）

| 故事 | 优先级 | Gherkin 验收标准 | 后端落点 | 前端落点 | 测试 | 综合状态 | 待补说明 |
|---|---|---|---|---|---|---|---|
| US-001 注册账号 | P0 | `stories/US-001-注册.feature` | AuthController.register → 身份注册应用服务（参数校验、密码加密、昵称唯一） | LoginPage 注册表单与错误提示 | 🟡 | 🟡 | 身份应用层注册/唯一约束用例偏薄，仅有 User/Course 域测试 |
| US-002 登录退出 | P0 | `stories/US-002-登录退出.feature` | AuthController.login/me/logout；会话过滤器；`RedisLoginThrottle` 失败 5 次锁定 1 分钟 | LoginPage、Header 退出按钮、登录态读写 | 🟡 | 🟡 | 登录节流、锁定窗口、鉴权拒绝用例待补 |
| US-003 发布问题 | P0 | `stories/US-003-发布问题.feature` | QuestionController.create → 提问应用服务（标题/正文/标签校验、聚合落库） | AskPage + MarkdownEditor，提交跳转详情 | ✅ | ✅ | — |
| US-004 浏览问题列表 | P0 | `stories/US-004-浏览问题列表.feature` | QuestionController.list → 问题查询服务（分页、排序、标签/课程筛选） | QuestionListPage、排序切换、标签筛选、分页 | ✅ | ✅ | — |
| US-005 查看问题详情 | P0 | `stories/US-005-查看问题详情.feature` | QuestionController.get → 详情读模型（Markdown、回答按票数排序） | QuestionDetailPage 渲染与回答区 | ✅ | ✅ | — |
| US-006 回答问题 | P0 | `stories/US-006-回答问题.feature` | AnswerController.create → 回答服务（落库、通知提问者、禁止自答） | 详情页回答框与提交反馈 | 🟡 | 🟡 | 回答边界、通知触发副作用分支用例待补 |
| US-007 投票（赞/踩） | P0 | `stories/US-007-投票.feature` | VoteController → 投票服务（一票、可改票、禁自投、票数更新） | 列表/详情投票按钮与实时票数 | 🟡 | 🟡 | 投票规则应用层分支用例待补 |
| US-008 采纳回答 | P0 | `stories/US-008-采纳回答.feature` | AnswerController.accept → 采纳服务（仅提问者、唯一采纳、问题置已解决） | 详情页采纳操作与状态展示 | 🟡 | 🟡 | 采纳权限、重复采纳、已解决状态用例待补 |

## P1 — 增值特性（Sprint 3）

| 故事 | 优先级 | Gherkin 验收标准 | 后端落点 | 前端落点 | 测试 | 综合状态 | 待补说明 |
|---|---|---|---|---|---|---|---|
| US-009 声誉积分系统 | P1 | `stories/US-009-声誉积分.feature` | ReputationController（概览/流水）→ 声誉账本（投票/采纳/踩结算、冻结/发放/退回、徽章策略） | ❌ 个人声誉展示页缺失；`api.reputation` 已封装但无页面调用 | 🟡 | 🟡 | ① 前端补个人中心/声誉概览位；② 声誉应用层与对账用例偏薄 |
| US-010 排行榜 | P1 | `stories/US-010-排行榜.feature` | LeaderboardController → 排行榜查询（按声誉排序、Top N、Redis 短时缓存） | LeaderboardPage、周期切换 | ✅ | ✅ | — |
| US-011 中文搜索 | P1 | `stories/US-011-中文搜索.feature` | SearchController.search → 检索应用服务（全文检索、中文分词、相关度排序、词项 ≥2 字符） | 列表页搜索框、标签/未回答筛选、排序切换 | ✅ | ✅ | Discovery 域与应用层含边界用例 |
| US-012 @提及通知 | P1 | `stories/US-012-提及通知.feature` | NotificationController + `NotificationProjector`/`MentionParser`（@昵称解析、去重、上限、自提忽略、未知昵称丢弃） | NotificationsPage、未读角标、30 秒轮询兜底 | ✅ | 🟡 | 前端缺 @昵称自动补全提示（输入框仅有占位提示）；SSE 端点未实现，当前为轮询兜底 |
| US-013 悬赏问题 | P1 | `stories/US-013-悬赏问题.feature` | BountyController（查询/设置悬赏）→ 悬赏服务（10–500 积分、1–14 天、冻结/发放/到期退回；仅教师/助教发起） | BountyDialog + 详情展示（期限上限已对齐 14 天） | 🟡 | 🟡 | 悬赏额度/期限/积分不足的应用层边界用例待补；发起人权限口径待与 feature 对齐（见审计报告开放项） |
| US-014 评论 | P1 | `stories/US-014-评论.feature` | CommentController → 评论服务（≤500 字、时间序、不可投票、父评论） | 详情页问题/回答评论区、发表评论 | 🟡 | 🟡 | 评论长度与边界用例待补 |
| US-015 相关问题推荐 | P1 | `stories/US-015-相关问题推荐.feature` | SearchController.related → 推荐服务（标签 + 标题相似度、最多 5 条） | 详情页相关推荐位 | ✅ | ✅ | `RelatedQuestionPolicy` 排序用例已覆盖 |

## 非前台故事的支撑模块

| 模块 | 后端落点 | 说明 |
|---|---|---|
| 课程管理 | CourseController | 课程列表、加入课程、成员管理 |
| 后台用户 | AdminUserController | 用户列表、审核、角色调整 |
| 后台声誉对账 | AdminReputationController | 声誉账本对账不一致核查（FF-8） |
| 系统监控 | Actuator `/actuator/health`、`/actuator/prometheus` | 健康探针与 Micrometer 指标接入 Prometheus/Grafana |

## 覆盖现状说明（2026-11-26 复核）

- **后端单元测试**：shared-kernel 2、identity 域 2（User/Course）、qa 域 3 + 应用 1、reputation 域 2、discovery 域 2 + 应用 2，另有架构约束 ArchUnit 1 条。
- **端到端集成测试**：`CampusOverflowFlowIT` 覆盖 注册 → 登录 → 提问 → 回答 → 采纳 → 声誉结算 → 搜索 → 通知 主链路，并断言业务错误码。
- **测试盲点（Lab7 覆盖率补测优先级）**：身份应用层注册/登录节流；QA 应用层回答/投票/采纳/评论的副作用分支与错误码；Reputation 应用层结算/冻结/对账；Discovery 通知投影的边缘路径。
- **覆盖率门禁**：`domain.coverage.minimum` 当前为 0，盲点用例补齐后再按 Lab7 口径（整体 ≥70%、核心聚合 ≥90%）提升阈值。
- **前端待补交互**：仅 US-009 个人声誉展示页、US-012 @昵称自动补全；搜索入口、评论区、相关推荐位、通知角标、悬赏弹窗均已落地。
