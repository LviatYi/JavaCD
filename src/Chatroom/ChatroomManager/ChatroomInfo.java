package Chatroom.ChatroomManager;

import Chatroom.FriendManager.FriendInfo;

import java.util.Vector;

/**
 * @author LVIAT.cn
 * @author May_bebe
 * @version 1.6 alpha
 * @className Chatroom
 * @date 2021/6/6
 */
public class ChatroomInfo {
    // Field

    private String chatroomId;
    private String chatroomName;
    private ChatroomType chatroomType;
    private Vector<FriendInfo> friendList;

    public enum ChatroomType {
        /**
         * 公共聊天室
         */
        PUBLIC(0),
        /**
         * 私有聊天室
         */
        PRIVATE(1);

        final private int flag;

        private ChatroomType(int flag) {
            this.flag = flag;
        }
    }

    // Construct

    public ChatroomInfo(String chatroomId, String chatroomName, ChatroomType chatroomType, Vector<FriendInfo> friendList) {
        this.chatroomId = chatroomId;
        this.chatroomName = chatroomName;
        this.chatroomType = chatroomType;
        this.friendList = friendList;
    }

    public ChatroomInfo(String chatroomId, String chatroomName, ChatroomType chatroomType) {
        this.chatroomId = chatroomId;
        this.chatroomName = chatroomName;
        this.chatroomType = chatroomType;
        this.friendList = new Vector<FriendInfo>();
    }

    public ChatroomInfo(ChatroomInfo chatroomInfo) {
        this.chatroomName = chatroomInfo.getChatroomName();
        this.chatroomId = chatroomInfo.getChatroomId();
        this.chatroomType = chatroomInfo.getChatroomType();
        this.friendList = new Vector<FriendInfo>();
    }

    public ChatroomInfo() {
        this.chatroomName = "";
        this.chatroomId = "";
        this.chatroomType = null;
        this.friendList = new Vector<FriendInfo>();
    }

    // Getter Setter

    public String getChatroomId() {
        return chatroomId;
    }

    /**
     * 获得 ChatroomName.
     *
     * @return ChatroomName.可能为空字符串.
     */
    public String getChatroomName() {
        return chatroomName;
    }

    /**
     * 获得 ChatroomName.
     * 若为私聊聊天室则设置为对方 Name.
     *
     * @return ChatroomName
     */
    public String getChatroomName(String thisUserId) {
        if (this.getChatroomType() == ChatroomType.PRIVATE) {
            for (FriendInfo friendInfo : friendList) {
                if (!friendInfo.getFriendId().equals(thisUserId)) {
                    return friendInfo.getFriendName();
                }
            }
        }
        return chatroomName;
    }

    public ChatroomType getChatroomType() {
        return this.chatroomType;
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

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setChatroomName(String chatroomName) {
        this.chatroomName = chatroomName;
    }

    public void setChatroomType(ChatroomType chatroomType) {
        this.chatroomType = chatroomType;
    }

    public void setFriendList(Vector<FriendInfo> friendList) {
        this.friendList = friendList;
    }

    // Function

    /**
     * 查找是否该聊天室含有对象
     *
     * @param friendId 对象 Id
     * @return FriendInfo
     */
    public boolean hasMember(String friendId) {
        if (friendId != null) {
            for (FriendInfo friendInfo : getFriendList()) {
                if (friendInfo.getFriendId().equals(friendId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 按照 FriendId 获得该聊天室中的 FriendInfo.
     * @param friendId FriendId
     * @return FriendInfo.若不存在则返回 null.
     */
    public FriendInfo getMember(System friendId){
        if (friendId != null) {
            for (FriendInfo friendInfo : getFriendList()) {
                if (friendInfo.getFriendId().equals(friendId)) {
                    return friendInfo;
                }
            }
        }
        return null;
    }

    public boolean isPrivate() {
        if (this.chatroomType == ChatroomType.PRIVATE) {
            return true;
        } else {
            return false;
        }
    }
}
