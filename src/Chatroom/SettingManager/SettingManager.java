package Chatroom.SettingManager;

import Chatroom.ChatroomGui;
import UserAuthenticate.UserAuthenticationManager;

/**
 * @author LviatYi
 * @author May_bebe
 * @version 1.6 alpha
 * @className SettingManager
 * @date 2021/6/8
 */
public class SettingManager {
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
        userAuthenticationManager = UserAuthenticationManager.getUserAuthenticationManager();
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
}
