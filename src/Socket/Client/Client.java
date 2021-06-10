package Socket.Client;

import ChatRoom.ChatRoomManager.ChatRoomInfo;

import java.io.IOException;

/**
 * @author topkang
 * @date 2021/6/3 23:18
 * @version 1.0
 */
public interface Client {

    /**
     * 启动客户端线程组
     * @throws IOException 对哩
     */
    public void run() throws IOException;

    /**
     *
     * @param text 传输的消息
     * @param senderID 发送者id
     * @param chatRoomID 聊天群号
     */
    public void sendGroup(String text,String senderID,String chatRoomID);


    /**
     *
     * @param name 昵称
     * @param password 登录密码
     */
    public void register(String name,String password);

    /**
     *
     * @param id 用户名ID
     * @param password 登录密码
     */
    public void login(String id,String password);

    /**
     *
     * @param newName 新名字
     * @param UserID 用户ID
     */
    public void modifyName(String newName,String UserID);

    /**
     *
     * @param newPassword 新密码
     * @param UserID 用户ID
     */
    public void modifyPassword(String newPassword,String UserID);
    /**
     * 断开连接
     */
    public void exit();

    /**
     *
     * @param chatRoomName 聊天室名
     * @param chatRoomType 聊天室类型
     */
    public void addChatRoom(String chatRoomName, ChatRoomInfo.ChatRoomType chatRoomType);

    /**
     *
     * @param userID 自己的ID
     * @param receiverID 被添加者的ID
     */
    public void addFriend(String userID, String receiverID);

    /**
     *
     * @param userID 自己的ID
     * @param receiverID 被删除者的ID
     */
    public void deleteFriend(String userID, String receiverID);

    /**
     *
     * @param userID 自己的ID
     */
    public void getFriendList(String userID);

    /**
     *
     * @param userID 自己的ID
     */
    public void getGroupList(String userID);

    /**
     *
     * @param userID 自己的ID
     * @param chatRoomID 群聊的ID
     */
    public void getHistoryMessage(String userID, String chatRoomID);

    /**
     *
     * @param userID 自己的ID
     * @param chatRoomID 群聊的ID
     */
    public void exitChatRoom(String userID,String chatRoomID);


}
