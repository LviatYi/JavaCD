package ChatRoom.ChatRoomManager;

import ChatRoom.FriendManager.FriendInfo;

import java.util.Vector;

/**
 * @author May_bebe
 * @version 1.0
 * @className ChatRoom
 * @date 2021/6/6
 */

public class ChatRoomInfo {
    private String chatRoomId;
    private String chatRoomName;
    private ChatRoomType chatRoomType;
    private Vector<FriendInfo> friendList;


    public enum ChatRoomType {
        /**
         * 公共聊天室
         */
        PUBLIC(0),
        /**
         * 私有聊天室
         */
        PRIVATE(1);

        final private int flag;

        private ChatRoomType(int flag) {
            this.flag = flag;
        }
    }

    public ChatRoomInfo(String chatRoomId, String chatRoomName, ChatRoomType chatRoomType) {
        this.setChatRoomId(chatRoomId);
        this.setChatRoomName(chatRoomName);
        this.chatRoomType = chatRoomType;
        this.setFriendList(null);
    }

    public ChatRoomInfo(String chatRoomId, String chatRoomName, ChatRoomType chatRoomType, Vector<FriendInfo> friendList) {
        this.setChatRoomId(chatRoomId);
        this.setChatRoomName(chatRoomName);
        this.setChatRoomType(chatRoomType);
        this.setFriendList(friendList);
    }

    public ChatRoomInfo(ChatRoomInfo chatRoomInfo) {
        this.setChatRoomName(chatRoomInfo.getChatRoomName());
        this.setChatRoomId(chatRoomInfo.getChatRoomId());
        this.setChatRoomType(chatRoomInfo.getChatRoomType());
        this.setFriendList(null);
    }

    public ChatRoomInfo() {
        this.setChatRoomName("");
        this.setChatRoomId("");
        this.chatRoomType = null;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public ChatRoomType getChatRoomType() {
        return this.chatRoomType;
    }

    /**
     * 受通信系统所限制，可能未赋值 FriendList
     *
     * @return 返回聊天室成员信息.
     * 当未赋值时，返回 null.请留意此情况并做专门处理.
     */
    public Vector<FriendInfo> getFriendList() {
        return friendList;
    }

    public void setFriendList(Vector<FriendInfo> friendList) {
        this.friendList = friendList;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public void setChatRoomType(ChatRoomType chatRoomType) {
        this.chatRoomType = chatRoomType;
    }

    public boolean hasMember(String friendId) {
        for (FriendInfo friendInfo : getFriendList()        ) {
            if(friendInfo.getFriendId().equals(friendId)){
                return true;
            }
        }
        return false;
    }

}
