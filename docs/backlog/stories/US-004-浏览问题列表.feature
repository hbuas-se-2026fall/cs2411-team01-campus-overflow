Feature: 浏览问题列表
  作为学生
  我希望按最新/热门/未回答排序浏览问题
  以便找到我能帮忙的问题

  Background:
    Given 用户已登录
    And 列表中存在若干问题，含不同回答数与投票数

  Scenario: 默认按最新排序分页浏览
    When 用户打开问题列表第 1 页
    Then 问题按发布时间倒序排列
    And 每页显示固定条数并返回总条数

  Scenario: 按标签筛选
    When 用户选择标签 "Spring Boot"
    Then 列表只显示带该标签的问题

  Scenario: 切换热门排序
    When 用户按"热门"排序
    Then 问题按投票数降序排列

  Scenario: 只看未回答
    When 用户选择"未回答"筛选
    Then 列表只显示回答数为 0 的问题

  Scenario: 列表卡片展示信息
    Then 每条问题显示标题、标签、回答数和投票数
