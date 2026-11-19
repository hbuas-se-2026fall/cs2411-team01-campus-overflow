Feature: 查看问题详情
  作为学生
  我希望打开一个问题看到完整正文和所有回答
  以便阅读讨论

  Background:
    Given 存在一个问题 "Spring Boot 启动报错"
    And 该问题下已有若干回答，投票数各不相同

  Scenario: 查看完整正文
    When 用户打开问题详情
    Then 显示完整 Markdown 正文并渲染为 HTML

  Scenario: 回答按投票数排序
    Then 回答按投票数从高到低排列

  Scenario: 显示作者与时间
    Then 显示提问者昵称、发布时间与每条回答的作者和时间

  Scenario: 未登录可浏览
    When 用户未登录打开问题详情
    Then 可以查看问题正文与全部回答
