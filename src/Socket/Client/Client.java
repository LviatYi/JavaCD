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
     * @param UserID 用户ID
     */
    public void modifyName(String newName,String UserID);

    /**
     * 改密码
     * @param newPassword 新密码
     * @param UserID 用户ID
     */
    public void modifyPassword(String newPassword,String UserID);

    /**
     * 断开连接
     */
    public void exit();

    /**
     * 创建一个新的聊天室
     * @param chatRoomName 聊天室名
     * @param chatRoomType 聊天室类型
     */
    public void addChatRoom(String chatRoomName, ChatroomInfo.ChatroomType chatRoomType);

    /**
     * 添加好友
     * @param userID 自己的ID
     * @param receiverID 被添加者的ID
     */
    public void addFriend(String userID, String receiverID);

    /**
     * 删除好友
     * @param userID 自己的ID
     * @param receiverID 被删除者的ID
     */
    public void deleteFriend(String userID, String receiverID);

    /**
     * 获取好友列表
     * @param userID 自己的ID
     */
    public void getFriendList(String userID);

    /**
     * 获取群聊列表
     * @param userID 自己的ID
     */
    public void getGroupList(String userID);

    /**
     * 获取指定群的历史记录
     * @param chatRoomID 群聊的ID
     */
    public void getHistoryMessage(String chatRoomID);

    /**
     * 退出聊天室
     * @param userID 自己的ID
     * @param chatRoomID 群聊的ID
     */
    public void exitChatRoom(String userID,String chatRoomID);

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
