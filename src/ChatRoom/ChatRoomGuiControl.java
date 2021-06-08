package ChatRoom;

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
     * 更新 ChatRoomListPl
     */
    void updateChatRoomListPl();
    /**
     * 更新 FriendListPl
     */
    void updateFriendListPl();

    /**
     *
     */

    void confirmWrongModifyPassword();
}