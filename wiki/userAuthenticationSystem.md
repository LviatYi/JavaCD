# Wiki - 用户认证系统

## 目录

* [介绍](#Description)
* [元素](#Component)
* [功能](#Function)
* [接口](#Interface)
* [类图](#Class)
* [状态图](#Statu)

## <a id="Description">介绍</a>

用户的注册与登录。  
主要提供用户认证的界面与认证程序。  

* 用户认证系统需要调用 [加密系统][ES] 中的密码加密。  
* 用户认证系统需要调用 [通信系统][CMS] 中的信息传输。  

## <a id="Component">元素</a>

包含如下信息：  

* 账号
* 密码
* 昵称

用户认证界面：

* 注册界面
* 登录界面
* 注册中界面
* 登录中界面

注册需要：

* 未注册的账号
  * 账号由系统自动分配，为 6-10 位数字构成。  
* 两次相同的密码
  * 密码由字母、数字、特殊符号中的任意两种组成。  
  * 密码最短为 8 ，最长为 20 。  
* 昵称
  * 任意字符，要求长度小于 16 。  

登录需要：

* 已经注册的账号。  
* 与账号适配密码。  

错误界面：

* 联网错误
  * 退出
* 注册失败（密码不相同）
  * 重输密码
* 注册失败（密码过简）
  * 重输密码
* 注册失败（密码过短）
  * 重输密码
* 注册失败（密码过长）
  * 重输密码
* 注册失败（昵称太长）
  * 重输昵称
* 登录失败（用户名不存在）
  * 重输用户名
  * 转到注册
* 登录失败（密码错误）
  * 重输密码

## <a id="Function">功能</a>

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
