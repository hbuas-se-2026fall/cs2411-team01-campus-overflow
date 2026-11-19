Feature: 相关问题推荐
  作为用户
  我希望看问题详情时看到相关推荐
  以便找到更多内容

  Background:
    Given 问题库中存在与当前问题标签或标题相近的问题

  Scenario: 推荐相近问题
    When 用户打开问题详情
    Then 展示按标签重合度和标题相似度排序的相关问题

  Scenario: 推荐数量上限
    Then 相关推荐最多返回 5 条

  Scenario: 无相似问题
    Given 没有与当前问题相近的其他问题
    When 用户打开详情
    Then 推荐区返回空列表，不报错
