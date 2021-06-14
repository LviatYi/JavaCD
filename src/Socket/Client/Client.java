package Socket.Client;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;

import java.io.IOException;

/**
 * @author topkang
 * @date 2021/6/3 23:18
 * @version 1.0
 */
public interface Client {

    /**
     * 发送消息给聊天室
     * @param msg 传输的消息对象
     * @return 发送状态
     */
    public boolean send(Message msg);


    /**
     * 注册
     * @param name 昵称
     * @param password 登录密码
     * @return 命令发送状态
     */
    public boolean register(String name,String password);

    /**
     * 登录
     * @param id 用户名ID
     * @param password 登录密码
     * @return 命令发送状态
     */
    public boolean login(String id,String password);

    /**
     * 改名
     * @param newName 新名字
     * @return 命令发送状态
     */
    public boolean modifyName(String newName);

    /**
     * 改密码
     * @param newPassword 新密码
     * @return 命令发送状态
     */
    public boolean modifyPassword(String newPassword);

    /**
     * 断开连接
     */
    public void exit();

    /**
     * 创建一个新的聊天室
     * @param chatroomInfo 聊天室
     */
    public ChatroomInfo addChatRoom(ChatroomInfo chatroomInfo);

    /**
     * 添加好友
     * @param receiverID 被添加者的ID
     * @return 命令发送状态
     */
    public boolean addFriend(String receiverID);

    /**
     * 删除好友
     * @param receiverID 被删除者的ID
     * @return 命令发送状态
     */
    public boolean deleteFriend(String receiverID);

    /**
     * 获取好友列表
     * @return 命令发送状态
     */
    public boolean getFriendList();

    /**
     * 获取群聊列表
     * @return 命令发送状态
     */
    public boolean getChatroomList();

    /**
     * 获取指定群的历史记录
     * @param chatroomId 聊天室 ID
     * @return 返回历史消息.
     *   非同步则返回 null.
     */
    public MessageList getHistoryMessage(String  chatroomId);

    /**
     * 退出聊天室
     * @param chatroomId 聊天室 ID
     * @return 命令发送状态
     */
    public boolean exitChatRoom(String  chatroomId);

    /**
     * 通过聊天室 ID 查找聊天室信息
     * @param chatRoomID 查询的聊天室ID
     */
    public ChatroomInfo findChatRoomInfo(String chatRoomID);

    /**
     * 通过好友关系查找聊天室 Info
     * @param userID1 成员ID1
     * @param userID2 成员ID2
     */
    public ChatroomInfo findChatRoomInfo(String userID1,String userID2);

    /**
     * 加入指定的聊天室
     * @param chatRoom 聊天室
     * @return 命令发送状态
     */
    public boolean joinChatRoom(ChatroomInfo chatRoom);

}
