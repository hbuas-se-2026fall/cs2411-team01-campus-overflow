Feature: 登录退出
  作为注册用户
  我希望用邮箱密码登录
  以便提问和回答

  Background:
    Given 系统已启动，数据库已连接
    And 已存在账号 "student@hbu.edu.cn"，密码 "Password123"

  Scenario: 正常登录
    When 用户输入邮箱 "student@hbu.edu.cn" 和密码 "Password123"
    Then 系统建立服务端会话，返回登录态

  Scenario: 密码错误
    When 用户输入错误密码
    Then 登录失败，提示邮箱或密码错误
    And 连续失败次数 +1

  Scenario: 连续失败 5 次锁定
    Given 用户已连续失败 4 次
    When 用户再次输入错误密码
    Then 账号被临时锁定，拒绝继续登录

  Scenario: 退出登录
    Given 用户已登录
    When 用户退出登录
    Then 服务端会话失效
    And 再次访问受保护接口返回未登录
