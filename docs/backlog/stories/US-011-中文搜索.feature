Feature: 中文搜索
  作为学生
  我希望用中文关键词搜索问题
  以便找到类似问题

  Background:
    Given 问题库中已有若干标题和正文含中文的问题

  Scenario: 命中标题或正文
    When 用户搜索关键词 "启动报错"
    Then 返回标题或正文中包含该词项的问题

  Scenario: 结果按相关度排序
    Then 搜索结果按相关度从高到低排列

  Scenario: 关键词过短
    When 用户搜索单个字 "错"
    Then 返回错误"搜索词项至少 2 个字符"

  Scenario: 无匹配结果
    When 用户搜索不存在的关键词
    Then 返回空列表
