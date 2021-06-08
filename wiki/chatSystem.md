# Wiki - 聊天系统

## 目录

* [介绍](#Description)
* [元素](#Component)
* [类图](#Class)
* [状态图](#Statu)

## <a id="Description">介绍</a>

聊天系统是 Jchat 的主要程序。  
主要提供聊天、用户管理、通讯录等功能。  
UI 设计师提供了扁平化思路，因此诸多功能集与一身。  

* 聊天系统需要调用 [加密系统][ES] 中的信息加密。  
* 聊天系统需要调用 [通信系统][CMS] 中的信息传输。  

## <a id="Component">元素</a>

包含如下界面：  

* 聊天室列表
* 通讯录
* 用户个人信息
* 设置
* 聊天窗口

聊天室列表：

* 包括一列聊天室。  
* 聊天室包括：
  * 聊天室 ID
  * 聊天室名

通讯录：

* 包括一列好友
* 好友包括：
  * 好友 昵称
  * 好友 ID

用户个人信息：

* 用户 ID
* 用户 昵称

设置：

* 用户 昵称修改
* 用户 密码修改
* 注销账号

聊天窗口：

* 当前聊天室名
* 当前聊天室中的信息记录
  * 内容
  * 发送者 ID
  

错误界面：

* 联网失败

## <a id="Class">类图</a>

<!-- TODO_LviatYi -->

## <a id="Statu">状态图</a>

<!-- TODO_LviatYi -->

<!-- TODO_LviatYi -->

[UAS]:./userAuthenticationSystem.md
[CS]:./chatSystem.md
[CMS]:./communicationSystem.md
[DBS]:./databaseSystem.md
[ES]:./encryptionSystem.md
