package Chatroom.FriendManager;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ClientManager;
import Status.LoginStatus;
import Status.RegisterStatus;

import java.util.Date;

/**
 * 通讯录管理类.
 * 主管好友系统.
 * 单例模式.
 *
 * @author LviatYi
 * @author May_bebe
 * @version 1.6 alpha
 * @className AddressManager
 * @date 2021/6/6
 */
public class AddressManager implements  ClientManager {
    // Field

    ChatroomGui parent;
    /**
     * 缓存的本地通讯录
     */
    FriendList friendList;

    // Construct

    /**
     * 单例指针
     */
    private static AddressManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private AddressManager(ChatroomGui parent) {
        this.parent = parent;
        FriendList serverFriendList = getFriendListServer();
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

    // Getter Setter

    public FriendList getFriendList() {
        return friendList;
    }

    // Function

    /**
     * 在本地缓存中添加好友.
     * @param friendInfo 好友 Info
     * @return 好友添加状态.
     */
    private boolean add(FriendInfo friendInfo) {
        if(this.friendList.add(friendInfo)!=null){
            return true;
        }
        return false;
    }

    /**
     * 在本地缓存中添加好友.
     * @param friendId 待添加的好友 ID
     * @param friendName 待添加的好友 Name
     * @return 好友添加状态.
     */
    private boolean add(String friendId, String friendName) {
        if(this.friendList.add(new FriendInfo(friendId, friendName))!=null)
        {
            return true;
        }
        return false;
    }

    private boolean del(String friendId){
        return this.friendList.del(friendId);
    }

    /**
     * 添加好友.
     * 希望同步.
     *
     * @param friendId 添加的好友的id
     * @return 添加好友的状态
     * 添加成功 QUALIFIED
     * 已存在此好友 ADDED
     */
    public FriendList.FriendStatus addFriend(String friendId) {
        if (friendList.find(friendId) != null) {
            return FriendList.FriendStatus.ADDED;
        } else {
            FriendInfo friendInfo = new FriendInfo(friendId, null);
            parent.getClientCommunication().addFriend(friendId);
            friendList.add(friendInfo);
            parent.updateFriend();
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
        if (friendList.find(friendId) != null) {
            del(friendId);
            parent.getClientCommunication().deleteFriend(friendId);
            parent.updateFriend();
            return FriendList.FriendStatus.QUALIFIED;
        } else {
            return FriendList.FriendStatus.NOT_EXIST;
        }
    }

    /**
     * 在本地通讯录中查找好友.
     *
     * @param friendId 好友 Id
     * @return 好友 Info.
     */
    public FriendInfo getFriendLocal(String friendId) {
        return friendList.find(friendId);
    }

    /**
     * 在本地通讯录中查找好友.
     *
     * @param friendId 好友 Id
     * @return 好友 Info.
     */
    public FriendInfo getFriendServer(String friendId) {
        return friendList.find(friendId);
    }

    /**
     * 从服务器获得该用户的好友列表
     * 希望同步.
     * @return 好友列表
     */
    private FriendList getFriendListServer() {
        parent.getClientCommunication().getFriendList();
        return null;
    }

    // Impl ClientManager

    @Override
    @Deprecated
    public boolean receiver(Message message) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(MessageList messageList, boolean isHistory) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomInfo chatroomInfo, boolean isFocus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomList chatroomList) {
        return false;
    }

    @Override
    public boolean receiver(FriendInfo friendInfo) {
        if (friendInfo != null) {
            FriendInfo localFriend = getFriendLocal(friendInfo.getFriendId());
            if (localFriend != null) {
                delFriend(localFriend.getFriendId());
                addFriend(friendInfo.getFriendId());
                return false;
            }
            addFriend(friendInfo.getFriendId());
        }
        return false;
    }

    @Override
    public boolean receiver(FriendList friendList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(LoginStatus loginStatus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(RegisterStatus registerStatus,String userId) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String userName) {
        return false;
    }
}

