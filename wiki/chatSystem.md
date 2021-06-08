# Wiki - 聊天系统

## 目录

* [介绍](#Description)
* [元素](#Component)
* [功能](#Function)
* [接口](#Interface)
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

## <a id="Function">功能</a>

聊天系统是用户使用的主要系统，应包含大部分功能。  

* 添加/进入/删除聊天室
  * 在输入栏中会展示一个空的聊天室的 ID。这个 ID 在登录时向服务器索要。  
  * 输入栏只允许输入数字。  
* 添加/删除好友
  * 输入栏只允许输入数字。  
* 用户个人信息设置
  * 用户昵称修改
  * 用户密码修改
* 向聊天室发送消息
  * 消息包含以下细节：
    * 消息内容
    * 发送者 ID
    * 发送时间
* 浏览聊天室的消息
  * 当登录时，会尝试访问服务端，索要用户加入的聊天室列表。  
  * 用户可以看到如下细节：  
    * 发送者
    * 发送时间
  * 如果发送者来自自己，则该条记录应处于聊天记录布局的右侧。否则为左侧。  
* 浏览通讯录
  * 当登录时，会尝试访问服务端，索要用户的好友列表。  
  * 用户可以看到如下细节：
    * 好友昵称
    * 好友 ID  
  * 如果用户点击了聊天室的某个对象，则用户进入特定的聊天室中：
    * 如果存在一个仅有用户和对象的聊天室，则加入。  
    * 如果不存在，则新建聊天室。  
    * 不需要考虑加入权限。

## <a id="Interface">接口</a>

### Manager

* addFriend()
  * 当用户被添加为好友时调用。  
  * 同时会刷新本地客户端的好友列表。
  * 并调用 GUI 的相应页面刷新。
* receiveMessage()
  * 当用户成为信息发送对象时调用。  
  * 当 Message 来自一个新的聊天室（客户端 ChatRoomList 中不存在）时：
    * 本地客户端会添加新的聊天室。  
    * 会调用 GUI 的相应页面刷新。  
  * 否则，仅更新相应的 MessageList 表。  

### GUI

* updateFriendListPl()
  * 按照 FriendList 表刷新 FriendList 界面
* updateChatListPl()
  * 按照 ChatList 表刷新 ChatList 界面
* updateMessagePl()
  * 按照 MessageList 表刷新 MessagePl 界面

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
