package Chatroom.FriendManager;

import java.util.Vector;

/**
 * @author LviatYi
 * @author May_bebe
 * @version 1.6 alpha
 * @className FriendList
 * @date 2021/6/8
 */
public class FriendList {
    // Field

    private Vector<FriendInfo> list;

    // Tool Status

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

    // Construct

    /**
     * 默认构造函数.
     */
    public FriendList() {
        list = new Vector<FriendInfo>();
        //Exist for DEBUG
        list.add(new FriendInfo("123", "123"));
        list.add(new FriendInfo("456", "456"));
        list.add(new FriendInfo("789", "789"));
        //End
    }

    // Getter Setter

    public Vector<FriendInfo> getList() {
        return list;
    }

    public void setList(Vector<FriendInfo> list) {
        this.list = list;
    }

    // Function

    /**
     * 在 list 中按照 FriendID 寻找 FriendInfo.
     *
     * @param friendId 好友 id
     * @return 好友 info,找不到返回空.
     */
    public FriendInfo findList(String friendId) {
        if (friendId != null && !"".equals(friendId)) {
            for (FriendInfo friendInfo : list) {
                if (friendInfo.getFriendId().equals((friendId))) {
                    return friendInfo;
                }
            }
        }
        return null;
    }

    /**
     * 添加一条 FriendInfo 记录.
     *
     * @param friendInfo FriendInfo 记录.
     * @return
     */
    public String add(FriendInfo friendInfo) {
        if (friendInfo != null && !"".equals(friendInfo.getFriendId())) {
            list.add(friendInfo);
            return friendInfo.getFriendId();
        }
        return null;
    }

    /**
     * 按照 FriendId 删除一条记录.
     *
     * @param friendId 好友 ID
     */
    public boolean del(String friendId) {
        if (friendId != null && !"".equals(friendId)) {
            list.removeIf(friendInfo -> friendInfo.getFriendId().equals(friendId));
            return true;
        }
        return false;
    }
}
