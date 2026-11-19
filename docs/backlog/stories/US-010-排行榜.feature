Feature: 排行榜
  作为学生
  我希望看到全站声誉 Top 10 排行榜
  以便知道谁是校园大神

  Background:
    Given 系统中已有若干用户及其声誉积分

  Scenario: 查看 Top 10
    When 用户打开排行榜
    Then 按声誉从高到低返回最多 10 名
    And 每条显示昵称和声誉积分

  Scenario: 排名顺序稳定
    Then 声誉相同的用户名次规则一致，不随请求抖动

  Scenario: 缓存命中
    When 5 分钟内再次打开排行榜
    Then 直接返回缓存结果，不重复扫描全量账户

  Scenario: 空榜
    Given 系统中还没有任何有声誉记录的用户
    When 用户打开排行榜
    Then 返回空列表，不报错
