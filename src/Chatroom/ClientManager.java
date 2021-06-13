package Chatroom;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.LoginStatus;
import Status.RegisterStatus;

import java.util.Date;

/**
 * 向客户端发送消息
 *
 * @author LviatYi
 * @version 1.0
 * @interfaceName MessageSend
 * @date 2021/6/7
 */
public interface ClientManager {
    /**
     * 向客户端发送一条 Message.
     * @param message 消息
     * @return 传输成功时 返回 true
     */
    boolean receiver(Message message);

    /**
     * 向客户端发送多条 Message.
     * @param messageList 多条消息
     * @param isHistory 是否为历史消息
     * @return
     */
    boolean receiver(MessageList messageList,boolean isHistory);

    /**
     * 向客户端发送一条 Message.
     * @param content 消息内容
     * @param senderId 发送方 Id
     * @param chatroomId 聊天室 Id
     * @param date 日期
     * @return
     */
    boolean receiver(String content, String senderId, String chatroomId, Date date);

    /**
     * 向客户端发送一条来自外部的关于好友的更新.
     * 被添加为好友时更新.
     * @param friendInfo 外部好友的 Info.
     * @return
     */
    boolean receiver(FriendInfo friendInfo);

    /**
     * 向客户端发送一条来自外部的关于聊天室的更新.
     * 有新成员加入聊天室时更新.
     * @param chatroomInfo 待更新聊天室的 Info.
     * @param isFocus 如果希望用户聚焦到该聊天室,则为 true
     * @return
     */
    boolean receiver(ChatroomInfo chatroomInfo,boolean isFocus);

    /**
     * 向客户端发送来自外部的关于好友的所有更新.
     * @param friendList 多条好友 Info
     * @return
     */
    boolean receiver(FriendList friendList);

    /**
     * 向客户端发送来自外部的关于聊天室的所有更新.
     * @param chatroomList
     * @return
     */
    boolean receiver(ChatroomList chatroomList);

    /**
     * 向客户端发送登录状态.
     * @param loginStatus 登录状态.
     * @return
     */
    boolean receiver(LoginStatus loginStatus);

    /**
     * 向客户端发送注册状态.
     * @param registerStatus 注册状态.
     * @return
     */
    boolean receiver(RegisterStatus registerStatus);
}
