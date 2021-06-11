package Chatroom.FriendManager;

import java.util.Vector;

/**
 * @author May_bebe
 * @version 1.0
 * @className FriendList
 * @date 2021/6/8
 */
public class FriendList {
    private Vector<FriendInfo> list;

    public enum FriendStatus {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 已添加
         */
        ADDED,
        /**
         * 账号不存在
         */
        NOT_EXIST,
        /**
         * 好友列表中不存在
         */
        LIST_NOT_EXIST,

    }

    public FriendList(){
        list=new Vector<FriendInfo>();
        list.add(new FriendInfo("123","123"));
        list.add(new FriendInfo("456","456"));
        list.add(new FriendInfo("789","789"));
    }

    public Vector<FriendInfo> getList() {
        return list;
    }
    public void setList(Vector<FriendInfo> list) {
        this.list = list;
    }

    /**
     * 在缓存中按照 FriendID 寻找 Friend
     *
     * @param friendId 好友 id
     * @return 好友 info,找不到返回空.
     */
    public FriendInfo findList(String friendId) {
        for (FriendInfo friendInfo : list) {
            if (friendInfo.getFriendId().equals((friendId))) {
                return friendInfo;
            }
        }
        return null;
    }

    /**
     * 添加一条 FriendInfo 记录.
     * @param friendInfo FriendInfo 记录.
     * @return
     */
    public String add(FriendInfo friendInfo) {
        list.add(friendInfo);
        return friendInfo.getFriendId();
    }

    /**
     * 按照 FriendId 删除一条记录.
     * @param friendId 好友 ID
     */
    public void del(String friendId) {
        list.removeIf(friendInfo -> friendInfo.getFriendId().equals(friendId));
    }

}
