# Sprint 4 进度审计报告（PO 复核）

> 仓库：`hbuas-se-2026fall/cs2411-team01-campus-overflow`
> 基线：`main` @ `154ae5f`（2026-11-19，CI 全绿）｜ 复核日期：2026-11-26 ｜ 负责人：PO 张宇航
> 口径：对照《CampusOverflow 实验部分》8 个 Lab 的提交物与评分要点，逐项核对 main 现状、3 个开放 PR 与线下交付物。

## 一、总体进度

| 维度 | 完成度 | 结论 |
|---|---|---|
| 后端主干代码 | ~90% | 6 模块 DDD 分层、14 个 REST 控制器、主链路集成测试就位，无残留 TODO 标记 |
| 前端主干代码 | ~85% | 6 页面 3 组件；搜索/评论/推荐/通知/悬赏/教师认证已打通；仅缺个人声誉展示页与 @昵称补全 |
| 工程化与文档 | ~80% | Backlog、故事地图、追溯矩阵、ADR、回顾/站会纪要、运维手册齐全；覆盖率门禁与评审记录未闭环 |
| CI/CD 与上线 | ~50% | 编译/测试/检查 CI 绿；镜像推送、自动部署、监控上线能力在未合并 PR 中，Secrets 未配、未上线 |
| 线下交付物 | ~20% | 录屏、截图、签名、现场照片等指导书要求的产物基本未入库 |

## 二、按 Lab 逐项核对

### Lab 1 团队组建与工具链
- ✅ 团队仓库已建；**main 分支保护已在 GitHub 服务端开启**（要求 1 人评审通过、驳回过时评审、管理员同样受限）。
- ✅ 章程 `docs/team/charter-v1.0.md`；4 份 `hello-*.md`；分支保护说明文档。
- ❌ 仓库未关联 GitHub Projects 看板（org 内其他队有看板，本仓库项目列表为空，Issues 为 0）。
- ❌ 章程其余三人 commit 签名未齐；看板截图、首次 PR 合并记录截图缺失。
- ❌ 仓库历史无已合并 PR：Lab1 Step3 的「切分支 → PR → LGTM → SM 合并」演练未在 GitHub 留痕。

### Lab 2 需求建模
- ✅ 用户故事地图 `docs/backlog/user-story-map.md`；Product Backlog 20 条 + INVEST 自评；**15 个 Gherkin feature**（要求 ≥8，每条 ≥3 场景，最少 US-015 为 3 条）；Sprint Backlog 分派；追溯矩阵。
- ❌ 故事地图 PNG/PDF 未入库；Sprint 1 Backlog 截图缺失。

### Lab 3 事件风暴 / DDD
- ✅ `docs/domain/event-storming.md`、`context-map.md`、`glossary.md`（47 条术语，要求 ≥30）；聚合与不变式清单。
- ❌ 事件风暴现场全景照片、上下文映射图 PNG 缺失。

### Lab 4 后端工程骨架
- ✅ 6 个 Maven 模块（shared-kernel / identity / qa / reputation / discovery / bootstrap）按 DDD 分层；Flyway 5 个初始化脚本；`GlobalExceptionHandler` 统一异常；springdoc OpenAPI 配置；Postman 集合 7 组 31 接口；CI `mvn install` 全绿；ArchUnit 架构约束 1 条。
- ⚠️ 未见独立的 JSON 结构化日志配置文件（仅 `application.yml` 的 logging 段）。

### Lab 5 前端工程骨架
- ✅ React 18 + TS5 + Vite，`strict: true`；ESLint / Prettier / Husky 钩子齐全；lint、format:check、typecheck、build 在 CI 全绿；提问 → 列表 → 详情链路贯通。
- ❌ 个人声誉展示页缺失：`api.reputation` 已封装，无页面/个人中心调用。
- ⚠️ 偏差项：未启用 `noUncheckedIndexedAccess`；未使用 TanStack Query / Zustand（手写 useState + fetch）；未从 OpenAPI 生成类型（手写 api.ts）；30 秒 E2E 录屏未提交。

### Lab 6 核心特性端到端 + Sprint Review
- ✅ 投票、采纳、评论、标签、声誉、悬赏、通知、中文搜索、相关推荐后端与前端主链路就位；3 份回顾纪要、3 份站会记录、8 份 ADR。
- ❌ Sprint Review 演示录屏缺失。
- ⚠️ 测试盲点：身份应用层、QA/Reputation 应用层副作用分支、Discovery 通知投影边缘路径。

### Lab 7 测试 / 评审 / 质量门禁（缺口最集中）
- ✅ JaCoCo 插件、Checkstyle 配置、`sonar-project.properties`、评审清单 `docs/team/review-checklist.md`；14 个测试类 + 1 个 Testcontainers 集成测试 `CampusOverflowFlowIT`。
- ❌ **覆盖率门禁阈值仍为 0**，未达 Lab7 口径（整体 ≥70%、核心聚合 ≥90%）；盲点用例未补。
- ❌ `SONAR_TOKEN` 未配置，Sonar 扫描条件跳过；无 Sonar Dashboard 截图。
- ❌ **跨成员 PR 评审记录为 0**（要求 ≥4 份、每份 ≥5 条实质意见）；JaCoCo HTML 报告未入库（仅 CI artifact）。
- ⚠️ 无 Pact 契约测试（加分项，非强制）；前端无 E2E 测试。

### Lab 8 CI/CD、容器化与上线
- ✅ compose 6 服务（mysql / redis / backend / web / prometheus / grafana）；后端两阶段镜像 + 非 root；前端 nginx alpine + 反代；Prometheus **5 条告警规则**（死信、积压、声誉对账、5xx >1%、P95 >500ms）；Grafana 看板 + provisioning；运维手册 `docs/ops/runbook-v1.0.md`；`.env.example`。
- ⏳ 以下能力在未合并 PR #3 中：jlink 三阶段精简 JRE、前端 HEALTHCHECK、compose `service_healthy` 依赖、GHCR 双标签推送、条件化 SSH 自动部署 + 60 秒 readiness 冒烟检查。
- ❌ main 的 images job 仅本地构建，无推送/部署；线上系统 URL 未取得；Grafana 真实流量截图未提交。
- ❌ Secrets 未配置：`SONAR_TOKEN`、`SSH_HOST` / `SSH_USER` / `SSH_KEY`、GHCR 登录、`DEPLOY_PATH` 变量；测试服务器子域名待向老师申请。

## 三、待合并 PR 评估

| PR | 作者 | 内容 | 规模 | CI | 风险 |
|---|---|---|---|---|---|
| #1 | 陈洲 | PR 模板 + 分支保护文档更新（纯文档） | 2 文件 +42/-4 | ✅ 绿 | 极低 |
| #2 | 陈洲 | Checkstyle 结果展示、条件化 Sonar、前端 format:check 门禁；pom 锁定 sonar-maven-plugin 5.2.0；前端 Prettier 纯格式重排 | 17 文件 +1123/-253 | ✅ 绿 | 低（无语义变更） |
| #3 | 陈洲 | jlink 三阶段镜像、HEALTHCHECK、健康依赖、GHCR 推送、条件化 deploy + 冒烟；`.env.example` 补字段密钥项 | 20 文件 +1249/-269 | ✅ 绿（docker/deploy 在 PR 事件按设计跳过） | 中低，部署链路需 Secrets 到位才真实执行 |

**依赖与合并顺序**：PR #3 的 `ci.yml` 是 #2 的超集；#2 独有价值是 pom 里 sonar 插件版本锁定；两分支前端改动一致。建议 **#3 → #2** 顺序合并，避免 ci.yml 二次冲突；合并前需另一成员 Approve（当前 3 个 PR 均 `REVIEW_REQUIRED / BLOCKED`，0 评审、0 评论）。

## 四、角色分工与待办

### SM 陈洲（流程与基础设施）
1. 推动 #1/#2/#3 评审闭环，落实 1 人 LGTM 后按 #3 → #2 合并。
2. 在仓库下创建 GitHub Projects 看板（To Do / In Progress / In Review / Done），挂接 Backlog 卡片。
3. 向老师申请测试服务器与子域名；配置 `SSH_HOST/SSH_USER/SSH_KEY`、GHCR 登录、`SONAR_TOKEN`、`DEPLOY_PATH`。
4. 补齐章程三人签名；补首次 PR 流程留痕。

### Dev A 袁有为（Q&A、Reputation 后端）
1. 补身份域、QA/Reputation 应用层副作用分支与错误码边界测试：登录节流/锁定、回答通知触发、投票规则、采纳权限/重复采纳、悬赏额度/期限/积分不足、声誉对账。
2. 配合将覆盖率阈值从 0 逐步抬到 Lab7 口径（整体 ≥70%、核心聚合 ≥90%）。
3. 对 PR #2（质量门禁）做跨成员评审，留 ≥5 条实质意见。

### Dev B / QA 彭天赐（Discovery、前端、测试）
1. 补个人声誉展示页（后端 `/v1/users/{id}/reputation` 与 api 封装已就位）。
2. 补 @昵称自动补全提示；评估加一条前端 E2E 或契约测试。
3. 归档 JaCoCo HTML 报告与后续 Sonar/Grafana 截图；对 PR #3（部署链路）做评审留痕。

### PO 张宇航（需求与验收）
1. ✅ 复核并校正追溯矩阵过期状态；对齐 Backlog 验收口径（悬赏 10–500 / 1–14 天、登录 5 次锁定 1 分钟）。
2. ✅ 修复悬赏弹窗期限上限与后端不一致（30 天 → 14 天）。
3. ⬜ 组织并跟踪 ≥4 次跨成员评审（自己未参与的 PR），沉淀评审记录。
4. ⬜ 收齐线下交付物：Sprint Review 录屏、30 秒 E2E 录屏、看板/合并记录截图、故事地图与上下文图 PNG、事件风暴照片、Grafana 真实流量截图。
5. ⬜ 跟老师确认验收口径：是否强制 PR 流程、是否卡覆盖率 70%/90%、是否必须上线域名访问。

## 五、开放决策项

1. **US-013 发起人权限**：后端 `Bounty.open` 限定教师/助教发起（REP-3001），feature 背景写"提问者"。需 PO 与团队确认以哪一版为准，再同步修改 Gherkin 与 Backlog 文案。
2. **PR 合并方式**：代码与 CI 已审过、质量可用；按分支保护规则需另一成员 Approve。待确认由谁评审、是否先合并。
3. **覆盖率补测排期**：抬阈值到 70% 需要袁有为/彭天赐补一批测试，是否纳入下个 Sprint Backlog。
4. **实时通知方案**：后端无 SSE `/stream` 端点，前端 EventSource 失败后自动退回 30 秒轮询；是否需要补 SSE 推送，按验收要求决定。

## 六、本次 PO 侧变更（2026-11-26）

- `docs/backlog/traceability-matrix.md`：按后端/前端/测试三态重写，纠正"搜索/评论/推荐/通知待补"的过期标注，保留真实缺口。
- `docs/backlog/product-backlog.md`：US-002 限流口径、US-013 悬赏区间与发起人权限对齐实现；更新复核日期。
- `docs/sprint/sprint-backlog.md`：状态与矩阵对齐；新增看板、Secrets、评审记录等待办行；登记 US-013 权限口径开放项。
- `frontend/src/components/BountyDialog.tsx`：悬赏期限上限 30 → 14 天，选项移除 30 天，对齐后端 REP-1004 与 Gherkin 验收。
