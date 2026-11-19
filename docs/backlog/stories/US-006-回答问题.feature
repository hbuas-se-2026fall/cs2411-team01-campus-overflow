Feature: 回答问题
  作为学生
  我希望对一个问题写回答
  以便帮助提问者

  Background:
    Given 用户已登录
    And 存在一个未被自己回答的问题

  Scenario: 正常提交回答
    When 用户提交 Markdown 正文 "检查 JDK 版本与依赖冲突..."
    Then 回答关联到该问题，状态为"已提交"

  Scenario: 不能回答自己的问题
    Given 问题是当前用户自己发布的
    When 用户尝试回答自己的问题
    Then 返回错误"不能回答自己的问题"

  Scenario: 回答后通知提问者
    When 回答提交成功
    Then 提问者收到一条新回答通知

  Scenario: 正文为空
    When 用户提交空正文回答
    Then 返回错误"回答正文不能为空"
