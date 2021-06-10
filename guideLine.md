# Java CD 开发风格规范

## 文档概述

此文档由 LviatYi 维护。其他开发者请勿直接进行改动。

文档参考：

[Alibaba Java Coding Guidelines][alijavaguidelines]

## 词汇标准

### 本文档中使用的词汇

本文档使用如下词汇描述规则的强制程度。

|  词汇  | 含义                         |
| :----: | :--------------------------- |
|  必须  | 只允许按照以下规则进行       |
|  禁止  | 不允许按照以下规则进行       |
|  建议  | 允许且应尽量按照以下规则进行 |
| 不建议 | 允许但应避免按照以下规则进行 |

本文档使用如下词汇描述命名大小写规则。

|   词汇    | 含义                                 |
| :-------: | :----------------------------------- |
| Pascal 法 | 首字母大写，其余构成单词的首字母大写 |
| Camel 法  | 首字母小写，其余构成单词的首字母大写 |
| 全大写法  | 所有字母大写，单词间使用 `_` 连接    |

### 代码命名中使用的词汇

#### 类必须使用 Pascal 法

**必须** 使用 Pascal 法命名。词汇优先参考 Wiki 中的词条。  
若无则可自定义，并主动通知文档管理者更新 Wiki 词条。

#### 方法必须使用 Camel 法

**必须** 使用 Camel 法命名。词汇优先参考下表：

|  前缀名   | 意义                                     | 举例                         |
| :-------: | :--------------------------------------- | :--------------------------- |
|  create   | 创建                                     | createOrder                  |
|  delete   | 删除                                     | deleteOrder                  |
|    add    | 创建，暗示新创建的对象属于某个集合       | addPaidOrder                 |
|  remove   | 删除                                     | removeOrder                  |
|   init    | 初始化，暗示会做些诸如获取资源等特殊动作 | initializeObjectPool         |
|  destroy  | 销毁，暗示会做些诸如释放资源的特殊动作   | destroyObjectPool            |
|   open    | 打开                                     | openConnection               |
|   close   | 关闭                                     | closeConnection              |
|   read    | 读取                                     | readUserName                 |
|   write   | 写入                                     | writeUserName                |
|    get    | 获得                                     | getName                      |
|    set    | 设置                                     | setName                      |
|  prepare  | 准备                                     | prepareOrderList             |
|   copy    | 复制                                     | copyCustomerList             |
|  modity   | 修改                                     | modifyActualTotalAmount      |
| calculate | 数值计算                                 | calculateCommission          |
|    do     | 执行某个过程或流程                       | doOrderCancelJob             |
| dispatch  | 判断程序流程转向                         | dispatchUserRequest          |
|   Socket.start   | 开始                                     | startOrderProcessing         |
|   stop    | 结束                                     | stopOrderProcessing          |
|   send    | 发送某个消息或事件                       | sendOrderPai dMessage        |
|  receive  | 接受消息或时间                           | receiveOrderPai dMessgae     |
|  respond  | 响应用户动作                             | responseOrderListItemClicked |
|   find    | 查找对象                                 | findObject                   |
|  update   | 更新对象                                 | updateCommission             |

**不建议** 在方法名中包含类名。

#### 字段必须使用 Camel 法

**必须** 使用 Camel 法。

#### 常量必须使用全大写法

**必须** 使用全大写法。

#### 接口必须使用 Pascal 法

接口 **必须** 使用 Pascal 法。
其实现类 **必须** 添加 `Impl` 后缀。  

#### 枚举必须使用 Pascal 法

枚举类型 **必须** 使用 Pascal 法，枚举值 **必须** 使用全大写法。  

枚举类型 **必须** 对非标记使用单数名词，对标记使用复数名词。

[什么是标记 (Flag) ？][FlagIntro]

## Git 使用指导

### 暂存时 必须 包含信息

**必须** 使用 `git commit -m [dataPacket]` 命令。

其中 `dataPacket` 包含工作内容的大致说明，请使用不带拼音的英文。

### `push` 命令 必须 包含分支

**必须** 使用 `git push [RemoteName] [Branch]` 命令。

[alijavaguidelines]: https://gitee.com/Will_Niu/Alibaba-Java-Coding-Guidelines
[FlagIntro]:https://blog.csdn.net/weixin_34352449/article/details/94444920
