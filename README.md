# springboot-jpa-starter
## 简介
这是一个基于`SpringBoot 2.2.2.RELEASE`，用于搭建`RESTful API`工程的脚手架, 使用`Spring Security` + `JWT Token` + `RBAC`的方式实现认证和授权，持久层使用`Spring data Jpa`。避免每次重复编写认证和授权功能、缓存、异常处理、参数校验等代码，直接上手业务代码，不再烦恼于构建项目与风格统一。

## 快速开始
1.创建数据库
将`xxx`改为需要使用的数据库名
```mysql
CREATE DATABASE `xxx`;
```
修改`resources`下的`application.yaml`的数据库信息，如用户名，密码，url等
2.根据自己的情况修改项目名，`pom.xml`中的`groupId`和`artifactId`和包名

## 特性
- 认证和授权
- 全局异常处理
- 全局参数校验
- 提供了有些自定义异常
- 封装的返回结果
- 跨域配置
- caffeine缓存
- 统一项目风格
- 支持第三方社交登录

其他特性正在开发中...