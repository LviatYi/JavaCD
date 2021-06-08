package ChatRoom.FriendManager;

import ChatRoom.ChatRoomGuiControl;

/**
 * 通讯录管理类.
 * 主管好友系统.
 * 单例模式.
 *
 * @author May_bebe
 * @version 1.0
 * @className AddressManager
 * @date 2021/6/6
 */
public class AddressManager {

    FriendList friendList;
    ChatRoomGuiControl chatRoomGuiControl;

    /**
     * 单例指针
     */
    private static AddressManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private AddressManager()
    {
    }

    /**
     * 单例模式
     *
     * @return 通讯录权限管理器
     */
    public static AddressManager getAddressManager()
    {
        if(instance==null)
        {
            instance=new AddressManager();
        }
        return instance;
    }

    /**
     * 添加好友
     *
     * @param friendId 添加的好友的id
     * @return 添加好友的状态
     * 添加成功 QUALIFIED
     * 已存在此好友 ADDED
     * 没有这个id WRONG_ID
     */
    public FriendList.FriendStatus addFriend(String friendId)
    {
        if(friendList.findLocal(friendId)!=null)
        {
            // TODO: 2021/6/8
            return FriendList.FriendStatus.ADDED;
/**
 * TODO May_bebe
 */
        }
        else
        {
            FriendInfo friendInfo=new FriendInfo(null,null);
            /**
             * TODO 通知服务器查找用户
             */
            if(friendInfo.getFriendId()!=null)
            {
                /**
                 * TODO 通知服务器要该用户的昵称
                 */
                friendList.add(new FriendInfo(friendId,friendInfo.getFriendName()));
                chatRoomGuiControl.updateFriendListPl();
                return FriendList.FriendStatus.QUALIFIED;
            }
            else
            {
                chatRoomGuiControl.confirmWrongFriendId();
                return FriendList.FriendStatus.LIST_NOT_EXIST;
            }
        }
    }

    /**
     * 删除好友
     *
     * @param friendId 好友id
     * @return 删除好友结果
     * 成功删除好友 QUALIFIED
     * 没有该好友 WRONG_ID
     */
    public FriendList.FriendStatus delFriend(String friendId)
    {
        if(friendList.findLocal(friendId)!=null)
        {
            friendList.del(friendId);
            chatRoomGuiControl.updateFriendListPl();
            return FriendList.FriendStatus.QUALIFIED;
        }
        else
        {
            chatRoomGuiControl.confirmNoFriend();
            return FriendList.FriendStatus.LIST_NOT_EXIST;
        }

    }





}

