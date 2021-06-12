package Socket.Client;

import Chatroom.ChatManager.Message;
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
     */
    public void sendGroup(Message msg);


    /**
     * 注册
     * @param name 昵称
     * @param password 登录密码
     */
    public void register(String name,String password);

    /**
     * 登录
     * @param id 用户名ID
     * @param password 登录密码
     */
    public void login(String id,String password);

    /**
     * 改名
     * @param newName 新名字
     */
    public void modifyName(String newName);

    /**
     * 改密码
     * @param newPassword 新密码
     */
    public void modifyPassword(String newPassword);

    /**
     * 断开连接
     */
    public void exit();

    /**
     * 创建一个新的聊天室
     * @param chatroomInfo 聊天室
     */
    public void addChatRoom(ChatroomInfo chatroomInfo);

    /**
     * 添加好友
     * @param receiverID 被添加者的ID
     */
    public void addFriend(String receiverID);

    /**
     * 删除好友
     * @param receiverID 被删除者的ID
     */
    public void deleteFriend(String receiverID);

    /**
     * 获取好友列表
     */
    public void getFriendList();

    /**
     * 获取群聊列表
     */
    public void getGroupList();

    /**
     * 获取指定群的历史记录
     * @param chatroomInfo 群聊
     */
    public void getHistoryMessage(ChatroomInfo chatroomInfo);

    /**
     * 退出聊天室
     * @param chatroomInfo 聊天室
     */
    public void exitChatRoom(ChatroomInfo chatroomInfo);

    /**
     * 通过群号查找聊天室信息
     * @param chatRoomID 查询的聊天室ID
     */
    public void findChatRoomInfo(String chatRoomID);

    /**
     * 通过两个人的ID查找私有聊天室的信息
     * @param userID1 成员ID1
     * @param userID2 成员ID2
     */
    public void findChatRoomInfo(String userID1,String userID2);

    /**
     * 加入指定的聊天室
     * @param chatRoom 聊天室
     */
    public boolean joinChatRoom(ChatroomInfo chatRoom);

}
