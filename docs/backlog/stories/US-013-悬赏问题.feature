Feature: 悬赏问题
  作为提问者
  我希望用积分悬赏吸引别人回答
  以便更快得到答案

  Background:
    Given 用户已登录
    And 用户账户有可用声誉积分

  Scenario: 正常发起悬赏
    When 用户为问题设置悬赏 100 积分、期限 7 天
    Then 100 积分从账户冻结，悬赏状态为"进行中"

  Scenario: 悬赏积分低于下限
    When 用户设置悬赏 5 积分
    Then 返回错误"悬赏积分须在 10 到 500 之间"

  Scenario: 悬赏积分超过上限
    When 用户设置悬赏 600 积分
    Then 返回错误"悬赏积分须在 10 到 500 之间"

  Scenario: 悬赏期限超出范围
    When 用户设置期限 20 天
    Then 返回错误"悬赏期限须在 1 到 14 天之间"

  Scenario: 可用积分不足
    Given 用户可用积分低于悬赏金额
    When 用户发起悬赏
    Then 返回错误"可用积分不足"

  Scenario: 采纳后悬赏转给回答者
    When 提问者采纳某个回答
    Then 冻结的悬赏积分转入该回答者账户，悬赏关闭
