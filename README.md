### 项目介绍
**Code-Set** 是一个**模仿**并**简单**实现 [leetcode](https://leetcode-cn.com/) 网站**部分功能**的项目，主要用来练习多条件查询。
其中服务端采用 **Springboot** + **Mybatis** 搭建， 此外查询功能中大量使用了 **Java8 Stream API**。

### 项目功能
- 题目模块
  - 后台增、删、改
  - 标签查询、状态查询、难度查询
  - 点赞量
- 题解模块
  - 发布、修改、删除
  - 标签查询
  - 点赞量
- 提交记录
- 标签分类

### 技术栈
- Springboot
- Mybatis

### 工具
- [PM2](https://github.com/Unitech/pm2) 进程管理工具
- [requests](https://github.com/psf/requests) 简单易用的 HTTP 库
- [mycli](https://github.com/dbcli/mycli) 带有自动补全和语法高亮的 MYSQL 命令行客户端 
- [redoc](https://github.com/Redocly/redoc) 漂亮的接口文档工具

### 开发环境

- JDK-11
- MySQL 8.0