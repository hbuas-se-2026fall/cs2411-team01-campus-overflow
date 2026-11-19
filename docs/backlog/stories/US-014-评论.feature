Feature: 评论
  作为学生
  我希望对问题和回答写短评论
  以便补充讨论

  Background:
    Given 用户已登录
    And 存在一个问题及其一条回答

  Scenario: 对问题发表评论
    When 用户提交评论 "补充一下，这个错误和 JDK 版本有关"
    Then 评论挂在该问题下，按时间记录

  Scenario: 对回答发表评论
    When 用户在某条回答下提交评论
    Then 评论挂在该回答下

  Scenario: 评论超长
    When 用户提交超过 500 字的评论
    Then 返回错误"评论不能超过 500 字"

  Scenario: 评论按时间排序
    Then 同一目标下的评论按发布时间升序展示

  Scenario: 评论不可投票
    When 用户尝试对评论点赞或踩
    Then 系统不提供评论投票入口
