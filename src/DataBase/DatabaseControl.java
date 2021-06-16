package DataBase;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ChatroomManager.ChatroomManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Socket.tools.DataPacket;
import Status.ChatroomStatus;
import Status.LoginStatus;

import java.util.Date;
import java.util.List;




/**
 * 向 Socket_Server 提供数据库控制函数
 * @author topkang
 * @author LviatYi
 * @author stiker
 * @date 2021/6/15
 */
public interface DatabaseControl {

 /**
  * 通过ID找姓名
  * @param ID 人ID
  * @return 返回姓名
  */
    String findNameThroughID(String ID);
    /**
     *
     *@return 返回聊天室Id
     */
     String returnChatRoomId();


/**
 *
 * 通过一个人的ID查找所有信息
 * @Param Id 用户 Id
 * @return 好友信息
 */
   FriendInfo returnUser(String Id);


    /**
     *
     * @param id 用户 ID
     * @param password 用户 Password
     * @return 登录状态
     */
    LoginStatus login(String id, String password);


    /**
     * 注册 添加数据至数据库
     *
     * @param password 用户密码
     * @param name 用户昵称
     * @return 录入信息并返回一个 6-10 的随机数字 id（不重复）
     */
    String register(String password, String name);

    /**
     * //* 修改数据库中的密码
     *
     * @param id           用户id
     * @param password_now 修改后的密码
     * @return 密码修改状态
     */
    boolean modifyPassword(String id, String password_now);

    /**
     * 修改昵称
     *
     * @param id       用户id
     * @param name_now 修改后的昵称
     * @return 姓名更改状态
     */
    boolean modifyName(String id, String name_now);

    /**
     * 添加 Message 至数据库
     *
     * @param chatroomId Message 所在的聊天室 ID
     * @param senderId 发送者 ID
     * @param content 内容
     * @param sendTime 客户端发送时间
     * @return 记录状态
     */
    boolean recordMessage(String senderId, String content, String chatroomId, Date sendTime);

    /**
     * 添加 Message 至数据库
     *
     * @param message 待记录 Message
     * @return 记录状态
     */
    boolean recordMessage(Message message);

    /**
     * 返回指定聊天室的历史消息
     *
     * @param chatroomId 聊天室ID
     * @return 聊天室历史消息 MessageList
     */
    MessageList getHistoryMessage(String chatroomId);

    /**
     * 创建聊天室
     *
     * @return 返回完整的 ChatroomInfo
     */
    ChatroomInfo createChatroom(ChatroomInfo chatroomInfo,FriendInfo creatorInfo);

    /**
     * 删除指定聊天室
     *
     * @param chatroomId 聊天室 ID
     * @return 删除状态
     */
    boolean delChatroom(String chatroomId);

    /**
     * 退群
     *
     * @param userId 退群人 ID
     * @param chatroomId 退出聊天室 ID
     * @return 退出状态
     */
    boolean exitChatroom(String userId, String chatroomId);

    /**
     * 添加好友关系
     *
     * @param id 添加人 ID
     * @param id_friend 被添加人 ID
     * @return 添加状态
     */
    boolean addFriend(String id, String id_friend);

    /**
     * 添加指定 用户 到指定聊天室.
     *
     * @param id 进入者 ID
     * @param chatroomId 添加进的聊天室
     * @return 加入状态.
     *   若加入聊天室为 私人聊天室 则返回 false。
     */
    ChatroomStatus joinChatroom(String id, String chatroomId);

    /**
     * 删除好友关系
     *
     * @param id 自己ID
     * @param id_friend 好友ID
     * @return 删除状态
     */
    boolean deleteFriend(String id, String id_friend);

    /**
     * 获得指定用户的好友列表
     *
     * @param userId 指定的用户 ID
     * @return 好友列表
     */
    FriendList getUserFriendList(String userId);

    /**
     * 获取id所在所有聊天室
     *
     * @param userId id人所在所有聊天室
     * @return chatRoomList对象
     */
    ChatroomList getUserChatroomList(String userId);

    /**
     * 根据指定 ID 返回聊天室信息.
     * @param chatroomID 指定的聊天室 ID
     * @return ChatroomInfo
     */
    ChatroomInfo getChatroomInfo(String chatroomID);

    /**
     *  根据指定 好友关系返回聊天室 Info
     * @param userId1 用户 1 ID
     * @param userId2 用户 2 ID
     * @return ChatroomList
     */
    ChatroomList getChatroomInfo(String userId1, String userId2);

    /**
     *  根据指定 好友关系返回私有聊天室 Info
     * @param userId1 用户 1 ID
     * @param userId2 用户 2 ID
     * @return ChatroomInfo
     */
    ChatroomInfo getPrivateChatroomInfo(String userId1, String userId2);

}
