Feature: @提及通知
  作为用户
  我希望被人 @ 时收到通知
  以便及时知道有人提到我

  Background:
    Given 用户已登录
    And 系统中存在昵称 "李同学" 的用户

  Scenario: 解析 @ 昵称
    When 用户在正文里写 "@李同学 请看这个问题"
    Then 系统识别出被提及用户 "李同学"

  Scenario: 被提及收到通知
    When 内容发布成功
    Then "李同学" 收到一条提及通知

  Scenario: 通知列表与未读标记
    When 用户打开通知列表
    Then 未读通知带有未读标记

  Scenario: 查看后标记已读
    When 用户打开一条通知
    Then 该通知标记为已读，未读数减 1
