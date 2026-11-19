# 需求追溯矩阵（Traceability Matrix）

> PO：张宇航 | 生成日期：2026-11-19
> 用途：把每条用户故事从验收标准一路追溯到后端实现、前端页面与测试覆盖，供 Sprint Review 答辩与课程验收核对。
> 测试覆盖口径：领域/应用层单元测试 + `CampusOverflowFlowIT` 端到端集成测试（Testcontainers MySQL8/Redis7）。

| 故事 | 优先级 | Gherkin 验收标准 | 后端落点（控制器 / 服务） | 前端落点（路由 / 页面） | 测试覆盖 | 状态 |
|---|---|---|---|---|---|---|
| US-001 注册账号 | P0 | `stories/US-001-注册.feature` | AuthController.register → 身份注册应用服务 | LoginPage（注册表单） | 身份域/应用层用例偏薄 | 🟡 |
| US-002 登录退出 | P0 | `stories/US-002-登录退出.feature` | AuthController.login/me，会话过滤器处理 logout | LoginPage、Header 退出按钮 | 登录节流/鉴权用例待补 | 🟡 |
| US-003 发布问题 | P0 | `stories/US-003-发布问题.feature` | QuestionController.create → 提问应用服务 | AskPage + MarkdownEditor | 提问边界/错误码用例 | ✅ |
| US-004 浏览问题列表 | P0 | `stories/US-004-浏览问题列表.feature` | QuestionController.list → 问题查询服务 | QuestionListPage | 列表查询用例 | ✅ |
| US-005 查看问题详情 | P0 | `stories/US-005-查看问题详情.feature` | QuestionController.get → 详情读模型 | QuestionDetailPage | 详情查询用例 | ✅ |
| US-006 回答问题 | P0 | `stories/US-006-回答问题.feature` | AnswerController.create → 回答服务 | QuestionDetailPage 回答区 | 回答边界/通知触发用例待补 | 🟡 |
| US-007 投票（赞/踩） | P0 | `stories/US-007-投票.feature` | VoteController → 投票服务 | 列表/详情投票按钮 | 投票规则用例待补 | 🟡 |
| US-008 采纳回答 | P0 | `stories/US-008-采纳回答.feature` | AnswerController.accept → 采纳服务 | QuestionDetailPage 采纳操作 | 采纳权限/重复采纳用例待补 | 🟡 |
| US-009 声誉积分系统 | P1 | `stories/US-009-声誉积分.feature` | ReputationController（概览/流水）→ 声誉账本 | 个人声誉展示位（待补） | 声誉规则/对账用例 | 🟡 |
| US-010 排行榜 | P1 | `stories/US-010-排行榜.feature` | LeaderboardController → 排行榜查询 | LeaderboardPage | 排行榜查询用例 | ✅ |
| US-011 中文搜索 | P1 | `stories/US-011-中文搜索.feature` | SearchController.search → 检索应用服务 | 列表页搜索入口（待补） | 搜索边界/分词用例 | 🟡 |
| US-012 @提及通知 | P1 | `stories/US-012-提及通知.feature` | NotificationController（列表/未读/已读） | NotificationsPage + 未读角标 | 提及通知触发用例待补 | 🟡 |
| US-013 悬赏问题 | P1 | `stories/US-013-悬赏问题.feature` | BountyController（查询/设置悬赏） | BountyDialog + 详情展示 | 悬赏额度/期限边界用例待补 | 🟡 |
| US-014 评论 | P1 | `stories/US-014-评论.feature` | CommentController → 评论服务 | 详情页评论区（待补） | 评论边界用例待补 | 🟡 |
| US-015 相关问题推荐 | P1 | `stories/US-015-相关问题推荐.feature` | SearchController.related → 推荐服务 | 详情页推荐位（待补） | 推荐结果用例待补 | 🟡 |

## 非前台故事的支撑模块

| 模块 | 后端落点 | 说明 |
|---|---|---|
| 课程管理 | CourseController | 课程列表、加入课程、成员管理 |
| 后台用户 | AdminUserController | 用户列表、审核、角色调整 |
| 后台声誉对账 | AdminReputationController | 声誉账本对账不一致核查（FF-8） |
| 系统监控 | Actuator `/actuator/health`、`/actuator/prometheus` | 健康探针与 Micrometer 指标接入 Prometheus/Grafana |

## 覆盖现状说明

- **后端单元测试**：shared-kernel 2、QA 领域 18、QA 应用 4、Reputation 领域 15、Discovery 领域 19、Discovery 应用 32，另有架构约束 ArchUnit 1 条。
- **端到端集成测试**：`CampusOverflowFlowIT` 覆盖 注册 → 登录 → 提问 → 回答 → 采纳 → 声誉结算 → 搜索 → 通知 主链路，并断言业务错误码。
- **待补强项**：身份域应用层、QA/Reputation 应用层副作用分支、Discovery 通知投影；覆盖率门禁阈值当前为 0，待盲点测试补齐后再按 Lab7 要求（整体 ≥70%、核心聚合 ≥90%）提升。
- **前端待补交互**：搜索入口、个人声誉展示、评论区、@提示、相关推荐位；对应后端接口均已就位。
