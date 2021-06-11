package DataBase;

import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Socket.tools.DataPacket;

import java.util.Date;
import java.util.List;

public interface Database {


     /**
      * 登录  查询name和password是否与数据库匹配
      * @param id 用户账号
      * @param password 用户密码
      * @return SUCCESS  账号存在&密码正确
      *         PASSWORD_ERROR  账号存在&密码错误
      *         ID_NOT_EXIST   账号不存在
      */
     Connect.LoginStatus LogIn(String id, String password);


    /**
     * 注册  添加数据至数据库
     *
     * @param password 用户密码
     * @param name 用户昵称
     * @return  录入信息并返回一个6-10的随机数字id（不重复）
     */
    int  Register( String password, String name);

   /**
     //* 修改数据库中的密码
     * @param id 用户id
     * @param password_now 修改后的密码
    * @return  true/false
     */
     boolean ModifyPassword(String id,String password_now);

    /**
     * 修改昵称
     * @param id 用户id
     * @param name_now 修改后的昵称
     *                 @return  true/false
     */
     boolean ModifyName(String id,String name_now);

    /**
     * 添加消息至数据库
     * @param groupId 账号
     * @param sender 发送者
     * @param message 内容
     * @param datetime 客户端发送时间
     *
     *@return  true/false
     */
    boolean SetMessage(String sender, String message, String groupId, Date datetime);

    /**
     * 返回聊天室历史消息
     * @param groupID 聊天室ID
     * @return DataPacket list
     */
     List<DataPacket> GetGroupMessage(String groupID);

    /**
     * 添加聊天室进聊天室表（）
     * @param isPrivate 是否是私有，在数据库聊天室属性添加私有为true
     * @return 返回随机群号（不重复）
     */
     String CreateChatRoom(boolean isPrivate,String chatroomName);

    /**
     * 删除指定聊天室
     * @param chatRoomId 聊天室
     * @return 成功
     */
    boolean DelChatRoom(String chatRoomId);

    /**
     * 添加好友信息至好友表（friend）
     * @param id 自己账号
     * @param id_friend 好友账号
     *@return  true/false
     */
     boolean  CreateFriend(String id,String id_friend);

    /**
     * 添加ID所有者到指定聊天室
     * @param id 添加进聊天室的人
     * @param chatRoomId 添加进的聊天室
     * @return 成功
     */
     boolean AddChatRoom(String id,String chatRoomId);

    /**
     * 输入好友账号将信息从数据库中删除
     * @param id 自己ID
     * @param id_friend 好友ID
     *           @return  true/false
     */
     boolean DeleteFriend(String id,String id_friend);

    /**
     * 返回Friend 数组
     * @param id 获取ID账号的好友列表
     * @return String 数组
     */
     FriendInfo getFriend(String id);

    /**
     * 返回group成员
     * @param groupId  聊天室ID
     * @return String 数组
     */
     String[] getGroup(String groupId);


    /**
     * 获得用户的好友列表.
     * @param userInfo 用户 Info
     * @return 目标好友的好友列表.
     */
    FriendList getUserFriendList(FriendInfo userInfo);
}
