# Wiki - 通信系统

## 目录

* [介绍](#Description)
* [元素](#Component)
* [功能](#Function)
* [接口](#Interface)
* [类图](#Class)
* [状态图](#Statu)

## <a id="Description">介绍</a>

用于传递用户认证与聊天消息的系统。  
主要提供客户端信息收发、服务端信息收发、与数据库通信等功能。  
通讯系统相当于船航管理局，其在服务器与客户端设备两处建设船坞，负责收发数据包，但不应附加处理数据包的责任。  

* 通信系统被 [用户认证系统][UAS] 中的密码信息传输调用。  
* 通信系统被 [聊天系统][CS] 中的聊天信息传输调用。  
* 通信系统需要调用 [数据库系统][DBS] 中的账号验证。  
* 通信系统需要调用 [数据库系统][DBS] 中的密码消息验证。  
* 通信系统需要调用 [数据库系统][DBS] 中的聊天消息传输。  
* 通信系统需要调用 [数据库系统][DBS] 中的增删改查用户设置。  

## <a id="Component">元素</a>

### 客户端信息管理器

允许客户端的其他程序调用其内部的算法，从而与服务器通信。  

### 服务器端信息管理器

允许服务器端的其他程序调用其内部的算法，从而与客户端或数据库端通信。  

## <a id="Function">功能</a>

通讯系统间允许传递如下消息：  

## <a id="Interface">接口</a>

### Client （客户端信息管理器）  

* **addChatRoom** (ChaRoomInfo)
    > ChatRoomId 可选
    > ChatRoomName 可选
    > ChatRoomType 必填

    **return** ChaRoomInfo  
    > ChatRoomId 必填
    > ChatRoomName 可选
    > ChatRoomType 必填

    新增聊天室时，传入参数将明确指明聊天室权限。
    当聊天室为私聊时需要由数据库补全 ChatRoomId 信息。
    其中 ChatRoomTpye 为 flag，可扩展为 int 类型。

* **exitChatRoom** (ChatRoomInfo，UserInfo)
    > ChatRoomId 必填
    > UserId 必填

  **return** 退出聊天室状态
    > Boolean 类型即可。
    > 其他信息允许为空。

    注意此操作旨在在聊天室中删除成员，而非删除聊天室。数据库需依照提供的 UserId 删除聊天室成员。

* **findChatRoom** (ChatRoomInfo)
    > ChatRoomId 必填
    > 其他信息可能为空。

    **return** ChatRoomInfo
    > 返回完整信息（所有信息必填）。

* **getChatRoom** (UserId1 UserId2)
    > 提供完整信息（所有信息必填）。

  **return** ChatRoomInfo
    > 返回完整信息（所有信息必填）。

    注意，此次查询旨在返回私有聊天室。数据库方可只按好友关系与聊天室权限进行查询。
    强烈建议聊天权限按照标记查询。

* **getUserInfo** (UserInfo)
    > UserId 必填
    > 其他信息为空。

  **return** UserInfo
    > 返回完整信息（所有信息必填）。

* **addFriend** (UserInfo)
    > UserId 必填
    > 其他信息可能为空。

    **return** UserInfo
    > 返回完整信息。

* **delFriend** (UserInfo)
    > UserId 必填
    > 其他信息可能为空

    **return** 删除状态
    > Boolean 类型即可。

* **sendMessage** (Message)
    > 提供完整信息。

    **return** 发送状态
    > Boolean 类型即可。

* **pullMessages** (ChatRoomInfo)
    > ChatRoomId 为必填
    > 其他信息可能为空。

    为人类提供两种可行的返回值解决方案：  

    **列表方案**
    **return** MessageList

    > 返回完整信息。

    这种方案不需要定义结束标记，抽象化完整。

    **单一方案**
    **return** void
    在随后调用对方接口发送消息，结束后提供结束标记，表达没有更多消息。

    **猴子方案**
    **return** void
    在随后调用对方接口发送消息，结束后不提供结束标记，另由聊天系统与数据库系统实现量子纠缠通信，表达没有更多信息。

### Server （服务端通信管理器）

## <a id="Class">类图</a>

<!-- TODO_LviatYi -->

## <a id="Statu">状态图</a>

<!-- TODO_LviatYi -->

[UAS]:./userAuthenticationSystem.md
[CS]:./chatSystem.md
[CMS]:./communicationSystem.md
[DBS]:./databaseSystem.md
[ES]:./encryptionSystem.md
