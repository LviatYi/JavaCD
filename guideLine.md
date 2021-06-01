# Java CD 开发风格规范

## 文档概述

此文档由 LviatYi 维护。其他开发者请勿直接进行改动。  

文档参考：

[Alibaba Java Coding Guidelines][AliJavaGuidelines]
[]

## 词汇标准

本文档中的规则使用如下词汇描述规则的强制程度。  

|  词汇  | 含义                         |
| :----: | :--------------------------- |
|  必须  | 只允许按照以下规则进行       |
|  禁止  | 不允许按照以下规则进行       |
|  建议  | 允许且应尽量按照以下规则进行 |
| 不建议 | 允许但应避免按照以下规则进行 |

## Git 使用指导

---

### 暂存时 必须 包含信息

**必须** 使用 `git commit -m [message]` 命令。  

其中 `message` 包含工作内容的大致说明，请使用不带拼音的英文。  

### `push` 命令 必须 包含分支

**必须** 使用 `git push [RemoteName] [Branch]` 命令。  

[AliJavaGuidelines]:https://gitee.com/Will_Niu/Alibaba-Java-Coding-Guidelines