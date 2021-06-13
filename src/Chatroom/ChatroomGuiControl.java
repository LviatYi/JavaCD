package Chatroom;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;

/**
 * 为主程序提供用户反馈与消息通知.
 *
 * @author LviatYi
 * @version 1.0
 * @interfaceName ChatroomGuiControl
 * @date 2021/6/7
 */
public interface ChatroomGuiControl {
    /**
     * 确认是否创建新的聊天室.
     *
     * @return boolean
     */
    boolean confirmNewChatroom();

    /**
     * 确认新聊天室的名称.
     *
     * @return ChatroomName
     */
    String confirmChatroomName();

    /**
     * 更新 ChatroomListPl.
     * 请在用户的聊天室列表信息发生更新时调用.
     */
    void updateChatroom();

    /**
     * 更新 FriendListPl.
     * 请在用户的好友列表信息发生更新时调用.
     */
    void updateFriend();

    /**
     * 更新 ChatPl.
     * 经过优化,仅在画布中添加一条新的消息.
     * 请在更新单条消息时调用.
     *
     * @param message 消息.
     */
    void updateMessage(Message message);

    /**
     * 更新 ChatPl.
     * 全部更新.
     * 若为历史消息,将会使画布置顶.
     *
     * @param messageList 待更新的消息列表.
     * @param isHistory   是否为历史消息.
     */
    void updateMessage(MessageList messageList, boolean isHistory);

    /**
     * 进入一个新的聊天室时调用.
     *
     * @param chatroomId
     */
    void updateMessage(String chatroomId);

    /**
     * 退出聊天时调用.
     */
    void updateMessage();
}