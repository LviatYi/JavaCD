package ChatRoom.FriendManager;

import java.util.Vector;

/**
 * @author May_bebe
 * @version 1.0
 * @className FriendList
 * @date 2021/6/8
 */
public class FriendList {
    private Vector<FriendInfo> list;

    public enum FriendStatus{
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 已添加
         */
        ADDED,
        /**
         * 无此好友用户
         */
        WRONG_ID,
    }

    /**
     * 在缓存中按照 FriendID 寻找 Friend
     *
     * @param friendId 好友id
     * @return 好友id，找不到返回空
     */
    public FriendInfo findLocal(String friendId)
    {
        for(FriendInfo friendInfo:list)
        {
            if(friendInfo.getFriendId().equals((friendId)))
            {
                return friendInfo;
            }
        }
        return null;
    }

    public String add(FriendInfo friendInfo)
    {
        list.add(friendInfo);
        return friendInfo.getFriendId();
    }
    public void del(String friendId)
    {
        list.removeIf(friendInfo -> friendInfo.getFriendId().equals(friendId));
    }


}
