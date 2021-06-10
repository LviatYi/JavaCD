package ChatRoom;

import ChatRoom.ChatManager.Message;
import ChatRoom.ChatRoomManager.ChatRoomInfo;
import ChatRoom.FriendManager.FriendInfo;
import ChatRoom.FriendManager.FriendList;

/**
 * 为主程序提供用户反馈与消息通知.
 *
 * @author LviatYi
 * @version 1.0
 * @interfaceName ChatRoomGuiControl
 * @date 2021/6/7
 */
public interface ChatRoomGuiControl {
    /**
     * 确认是否创建新的聊天室.
     * @return boolean
     */
    boolean confirmNewChatRoom();

    /**
     * 确认新聊天室的名称.
     * @return ChatRoomName
     */
    String confirmChatRoomName();

    /**
     * 更新 ChatRoomListPl.
     * 请在更新聊天室时调用.
     */
    void updateChatRoom();
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