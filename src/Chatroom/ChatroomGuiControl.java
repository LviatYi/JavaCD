package Chatroom;

import Chatroom.ChatManager.Message;

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
     * @return boolean
     */
    boolean confirmNewChatroom();

    /**
     * 确认新聊天室的名称.
     * @return ChatroomName
     */
    String confirmChatroomName();

    /**
     * 更新 ChatroomListPl.
     * 请在更新聊天室时调用.
     */
    void updateChatroom();
    /**
     * 更新 FriendListPl.
     * 请在更新好友时调用.
     */
    void updateFriend();

    /**
     * 更新 ChatPl.
     * 请在更新单条消息时调用.
     * @param message 消息.
     */
    void updateMessage(Message message);

}