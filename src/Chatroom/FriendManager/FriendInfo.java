package Chatroom.FriendManager;

/**
 * @author May_bebe
 * @version 1.0
 * @className FriendInfo
 * @date 2021/6/6
 */
public class FriendInfo {
    private String friendId;
    private String friendName;

    public FriendInfo(String friendId,String friendName)
    {
        this.friendId=friendId;
        this.friendName=friendName;
    }
    public FriendInfo(FriendInfo friendInfo)
    {
        this.friendId=friendInfo.getFriendId();
        this.friendName=friendInfo.getFriendName();
    }

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
