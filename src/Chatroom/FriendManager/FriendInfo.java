package Chatroom.FriendManager;

/**
 * @author LviatYi
 * @author May_bebe
 * @version 1.6 alpha
 * @className FriendInfo
 * @date 2021/6/6
 */
public class FriendInfo {
    // Field

    private String friendId;
    private String friendName;

    // Construct

    public FriendInfo(String friendId, String friendName) {
        this.friendId = friendId;
        this.friendName = friendName;
    }

    public FriendInfo(FriendInfo friendInfo) {
        this.friendId = friendInfo.getFriendId();
        this.friendName = friendInfo.getFriendName();
    }

    // Getter Setter

    public String getFriendId() {
        return friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
