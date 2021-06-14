package Chatroom.SettingManager;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.LoginStatus;
import Status.RegisterStatus;
import UserAuthenticate.UserAuthenticationManager;

import java.util.Date;
import java.util.Objects;

/**
 * @author LviatYi
 * @author May_bebe
 * @version 1.6 alpha
 * @className SettingManager
 * @date 2021/6/8
 */
public class SettingManager implements ClientManager {
    // Field

    public UserAuthenticationManager userAuthenticationManager;
    private ChatroomGui parent;
    private String selfId;
    private String selfName;

    // Construct

    /**
     * 单例指针
     */
    private static SettingManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private SettingManager(ChatroomGui parent, String selfId, String selfName) {
        this.parent = parent;
        this.selfId = selfId;
        this.selfName = selfName;
        userAuthenticationManager = UserAuthenticationManager.getUserAuthenticationManager(null);
    }

    /**
     * 单例模式
     *
     * @return 设置权限管理器
     */
    public static SettingManager getSettingManager(ChatroomGui parent, String selfId, String selfName) {
        if (instance == null) {
            instance = new SettingManager(parent, selfId, selfName);
        }
        return instance;
    }

    // Getter Setter

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }

    public String getSelfId() {
        return selfId;
    }

    public String getSelfName() {
        return selfName;
    }

    // Function

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
    public boolean receiver(FriendInfo friendInfo) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomInfo chatroomInfo, boolean isFocus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendList friendList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomList chatroomList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(LoginStatus loginStatus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(RegisterStatus registerStatus) {
        return false;
    }

    @Override
    public boolean receiver(String userName) {
        if (!"".equals(userName)){
            this.selfName=userName;
            parent.updateUserInfo(userName);
        }
        return false;
    }
}
