# JChat Java 课程设计

---

## 项目介绍

一款基于 Java 的聊天程序。  

---

## 项目构成

### 用户认证系统

管理用户的注册与登录。[More...][UAS]  

* 注册 GUI
* 登录 GUI

### 通信系统

用于传递用户认证与聊天消息的系统。[More...][CMS]  

* 客户端
* 服务器端

### 聊天系统

用户与程序进行交互的主要界面,提供各种场合的使用界面。用户可直观地切换到其他聊天场合。[More...][CS]  

* 私人聊天 GUI
* 聊天室 GUI

### 数据库系统

提供数据库，提供单例模式的数据库管理程序，并使用适配器模式提供更加直观的数据库操作。[More...][DBS]

* 数据库构建
* 数据库交互
* 数据库维护

### 加密系统

提供密码与聊天信息加密。[More...][ES]  

* 密码加密算法
* 信息加密与解密算法

---

## 工作分配

### Project Director 项目总监

* 整体进度规划与控制
* 维护团队沟通
* 文档编纂
* 客户端前端服务程序

LviatYi

### Network engineer 网络工程师

* 数据通信
* 网络日志

TopKang / IMposter

### Back-end engineer 后端工程师

* 客户端前端通信程序
* 服务器后端程序
* 服务器后台

TopKang / IMposter

### DBA 数据库工程师

* 数据库建立
* 数据库交互
* 数据库维护

Sticker

### UI Designer 界面设计师

* 程序前台界面

LviatYi

### IS Engineer 信安工程师

* 密码加密
* 端到端通信加密

May_bebe

[UAS]:./wiki/userAuthenticationSystem.md
[ABS]:./wiki/addressBookSystem.md
[CS]:./wiki/chatSystem.md
[CMS]:./wiki/communicationSystem.md
[DBS]:./wiki/databaseSystem.md
[ES]:./wiki/encryptionSystem.md
