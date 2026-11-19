Feature: 声誉积分系统
  作为活跃用户
  我希望我的好回答能获得积分
  以便在排行榜上有名次

  Background:
    Given 用户已登录
    And 存在回答者 A 的一条回答

  Scenario: 回答被投赞成加分
    When 其他用户对 A 的回答投赞成票
    Then A 的声誉 +10

  Scenario: 回答被采纳加分
    When 提问者采纳 A 的回答
    Then A 的声誉 +15

  Scenario: 回答被踩扣分
    When 其他用户对 A 的回答投反对票
    Then A 的声誉 -2

  Scenario: 给自己投票不加分
    Given 回答者 A 给自己的回答投票
    When 投票完成
    Then A 的声誉不发生变化

  Scenario: 积分流水可查
    When 用户查看自己的声誉明细
    Then 按时间列出每笔积分变动及原因
