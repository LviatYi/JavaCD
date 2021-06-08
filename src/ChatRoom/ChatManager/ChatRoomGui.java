package ChatRoom.ChatManager;

/**
 * 为主程序提供用户反馈与消息通知.
 *
 * @author LviatYi
 * @version 1.0
 * @interfaceName ChatRoomGuiControl
 * @date 2021/6/7
 */
public interface ChatRoomGui {
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
     * 添加好友时确认没有此用户
     */
    void confirmWrongFriendId();
    /**
     * 添加好友时已有此好友
     */
    void confirmHavingFriend();
    /**
     * 删除好友时没有此好友
     */
    void confirmNoFriend();

    /**
     * 更新 ChatRoomListPl
     */
    void updateChatRoomListPl();
    /**
     * 更新 FriendListPl
     */
    void updateFriendListPl();
}
