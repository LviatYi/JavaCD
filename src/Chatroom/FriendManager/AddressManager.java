package Chatroom.FriendManager;

import Chatroom.ChatroomGui;

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
public class AddressManager implements ClientAddressManager {
    ChatroomGui chatRoomGui;
    FriendList friendList;

    /**
     * 单例指针
     */
    private static AddressManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private AddressManager(ChatroomGui parent){
        this.chatRoomGui=parent;
        FriendList serverFriendList = getServerFriendList();
        if (serverFriendList != null) {
            friendList = serverFriendList;
        } else {
            friendList = new FriendList();
        }
    }

    /**
     * 单例模式
     *
     * @return 通讯录权限管理器
     */
    public static AddressManager getAddressManager(ChatroomGui parent) {
        if (instance == null) {
            instance = new AddressManager(parent);
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
     */
    public FriendList.FriendStatus addFriend(String friendId) {
        if (friendList.findLocal(friendId) != null) {
            return FriendList.FriendStatus.ADDED;
        } else {
            FriendInfo friendInfo = new FriendInfo(friendId, null);
            /*
             * TODO_LviatYi 请求 FriendInfo
             *  并修改 friendInfo 的 friendName 字段
             * date 2021/6/9
             */
            /*
             * TODO_LviatYi 通知服务器添加好友
             * date 2021/6/9
             */
            friendList.add(friendInfo);
            chatRoomGui.updateFriend();
            //添加成功
            return FriendList.FriendStatus.QUALIFIED;
        }
    }

    /**
     * 删除好友
     *
     * @param friendId 好友id
     * @return 删除好友结果
     * 成功删除好友 QUALIFIED
     * 没有该好友 NOT_EXIST
     */
    public FriendList.FriendStatus delFriend(String friendId) {
        if (friendList.findLocal(friendId) != null) {
            friendList.del(friendId);
            /*
             * TODO_LviatYi 通知数据库删除好友
             * date 2021/6/8
             */
            chatRoomGui.updateFriend();
            return FriendList.FriendStatus.QUALIFIED;
        } else {
            return FriendList.FriendStatus.NOT_EXIST;
        }
    }

    public FriendList getFriendList(){
        return friendList;
    }

    private FriendList getServerFriendList() {
        /*
         * TODO_LviatYi 向服务器请求该用户的好友列表
         * date 2021/6/7
         */
        return null;
    }
    @Override
    public boolean addFriend(FriendInfo friendInfo) {
        this.friendList.add(friendInfo);
        chatRoomGui.updateFriend();
        return false;
    }

    @Override
    public boolean addFriend(String friendId, String friendName) {
        this.friendList.add(new FriendInfo(friendId,friendName));
        chatRoomGui.updateFriend();
        return false;
    }
}

