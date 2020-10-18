# Creek
## 简介
这是一个基于`SpringBoot 2.2.2.RELEASE`，用于搭建`RESTful API`快速开发脚手架, 使用`Spring Security` + `JWT Token` + `RBAC`的方式实现认证和授权，持久层使用`Mybatis plus`。避免每次重复编写认证和授权功能、角色管理、异常处理、参数校验等代码，直接上手业务代码，不再烦恼于构建项目与风格统一。

## 快速开始
1.本项目默认使用`H2`数据库，首次启动修改如下配置会自动创建数据库文件并初始化
```properties
spring:
  datasource:
    initialization-mode: always
```
启动后在将改配置值改为`never`，防止每次都初始化

2.运行`AppApplication`

## 特性
- 认证和授权
- security权限管理
- 全局异常处理
- 全局参数校验
- 统一项目风格
- 日志
- 支持第三方社交登录

[前端使用示例项目](https://github.com/guqing/creek)