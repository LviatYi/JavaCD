# JChat 使用说明书 Operate Manual

## 1.  软件使用

- **IDE**
  IntelliJ IDEA v2020.3.2 至最新
- **JDK**
  JDK 8
- **外部库**
  /lib/fastjson-1.2.53.jar
  /lib/sqljdbc4.jar

测试方法：

1. 将外部库导入项目结构中。  
2. 运行 \src\Socket\Server\MultiChatServer.java
3. 运行 \src\UserAuthenticate\UserAuthenticationGui.java

如程序无响应，请检查服务器可用性及防火墙状态。  

如发生其他未知错误，请联系 <a href="mailto:LviatYi@qq.com?subject=[加急]JChat%20运行失败&body=请简述您遇到的错误" rel="nofollow">开发人员</a> ，并留下您的联系方式。  

## 2.   登陆界面

进程名称：Jchat V1.60 alpha
界面标题：JChat 2021 | Login
![登陆界面](\pic\login.png "login")

### 2.1  输入框

- **ID**
默认“User ID”，光标选中时默认全选，User ID边框出现以示选中。
光标移开后，输入框下方显示 ID 输入的合法性，绿色 Right 合法，红色 empty 空，not exist 不存在。

- **Password**
默认“··········”，光标选中时默认全选。Password 边框出现以示选中。
光标移开后，输入框下方显示 password 输入的合法性，绿色 Right 合法，红色 empty 空，short 过短，long 过长。

### 2.2  交互按钮

- **Login**
在两个输入框中自上而下分别输入 User ID 和 User password ，单击按钮，成功则跳转至聊天界面，失败则跳出错误信息。

- **Register now!**
单击跳转至注册界面。

![测试登录界面](\pic\testlogin.png "test login")

## 3.  注册界面

进程名称：Jchat V1.60 alpha
界面标题：JChat 2021 | Register
![注册界面](\pic\register.png "register")

### 3.1  输入框

- **name**
默认“User name”，光标选中时默认全选，User name 边框出现以示选中。
光标移开后，输入框下方显示 ID 输入的合法性，绿色 Right 合法，红色 empty 空，long 过长。
- **Password**
默认“··········”，光标选中时默认全选，Password 边框出现以示选中。
光标移开后，输入框下方显示 password 输入的合法性，绿色 Right 合法，红色 empty 空，short 过短，long 过长，easy 过简。
- **Password again**
默认“··········”，光标选中时默认全选，Password 边框出现以示选中。
光标移开后，输入框下方显示 password 输入的合法性，绿色 Right 合法，红色 empty 空，short 过短，long 过长，inconsistent 不一致。

![测试注册界面](\pic\testregister.png "test register")

### 3.2  交互按钮

- **Register**
在三个输入框中自上而下分别输入 User name 和 User password，password again ，单击按钮，成功则跳转至聊天室界面，失败则跳出错误信息。
- **Already have an account...**
单击跳转至登录界面。

## 4.  聊天室界面

进程名称：Jchat Chatroon

![聊天室界面](\pic\chatroom.png "chat room")

### 4.1  多功能页签界面

#### 4.1.1 chat room

- **Add**
登陆成功后，输入框中默认为随机生成可供创建的新聊天室 id ，单击按钮创建新聊天室。
用户自己输入聊天室 id ，单击按钮若为已有聊天室则加入，若为新聊天室则新建。
新建完成后，在页签 chat room 界面生成该聊天室Pl。并于右侧聊天室界面显示该聊天室内容。
![新建确认界面](\pic\newchatroomconfirm.png "confirm")
![新聊天室名称界面](\pic\chatroomname.png "name")
![添加聊天室界面](\pic\addchatroom.png "add chat room")

- **Detele**
输入在页签 chat room 界面已有的聊天室 id ，单击按钮删除，该聊天室Pl消失。

![删除聊天室界面](\pic\detelechatroom.png "detele chat room")

- **其它**
单击已有聊天室Pl，右侧聊天室界面显示该聊天室内容。

![聊天界面](\pic\chatroomchat.png "chat room chat")

#### 4.1.2 Friends

- **Add**
输入框输入好友 id ，单击按钮添加，在页签 friend 界面新增该好友 Pl。

![添加好友界面](\pic\addfriend.png "add friend")

- **Detele**
输入框输入好友 id ，单击按钮删除，在页签 friend 界面删除该好友 Pl。

![删除好友界面](\pic\detelefriend.png "detele friend")

- **其它**
单击已有好友 Pl ，右侧聊天室界面显示与该好友的私聊。

![好友聊天界面](\pic\friendchat.png "friend chat")

#### 4.1.3 Me

显示个人 id 和 name。

![个人信息界面](\pic\me.png "ME")

#### 4.1.4 Settings

- **Confirm**
在三个输入框中自上而下分别输入 Update name 和 Update password ，Confirm again ，单击按钮，成功则更新用户昵称和密码。并于页签 ME 界面更改。

![设置界面](\pic\modifysettings.png "Settings")

### 4.2  聊天室界面

- **Exit**
单击按钮，退出该聊天室，并将该聊天室从页签 chat room 界面中删除。

- **Send**
在输入框中输入发送的信息，单击按钮，在聊天界面上附加时间和发送人 name 显示。

![发送信息界面](\pic\send.png "send message")

- **history**
在聊天界面单击按钮，显示该聊天室历史聊天记录，若无更多历史信息则显示No more history message.

![历史信息界面](\pic\nohistory.png "history message")
