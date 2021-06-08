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

## <a id="Class">类图</a>

<!-- TODO_LviatYi -->

## <a id="Statu">状态图</a>

<!-- TODO_LviatYi -->

[UAS]:./userAuthenticationSystem.md
[CS]:./chatSystem.md
[CMS]:./communicationSystem.md
[DBS]:./databaseSystem.md
[ES]:./encryptionSystem.md
