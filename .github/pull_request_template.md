<!--
PR 标题格式：<type>(<scope>): <一句话说明>
type: feat | fix | docs | refactor | test | chore
示例：feat(qa): 支持发布问题时选择标签
分支约定：从最新 main 切出，所有改动走 PR，不直推 main（见 CONTRIBUTING.md）
-->

## 1. 背景与目标（Why）

<!-- 关联的 Issue / 用户故事编号，例如 Closes #17；说明这次改动要解决什么问题。 -->

## 2. 方案与改动（What）

- 改动点 1：
- 改动点 2：
- 涉及模块 / 上下文：

<!-- 如有 ADR、接口契约、数据库迁移，请在此引用；不允许跨上下文直接访问对方数据表。 -->

## 3. 测试与验收（How）

- [ ] 单元测试已补充 / 更新，后端本地 `mvn -B test`、前端 `npm run lint && npm run typecheck && npm run build` 通过
- [ ] 领域层无框架依赖（ArchUnit 全绿）
- [ ] 错误码、日志、异常类型符合《错误码》约定
- [ ] 自测步骤：
  1.
  2.

## 自检清单（提交人）

- [ ] 分支从最新 main 切出，未直推 main
- [ ] PR 描述三段式完整，关联了对应 Issue
- [ ] 未夹带与本 PR 无关的格式化 / 重构改动
- [ ] 敏感信息（令牌、密码、连接串）未入库
