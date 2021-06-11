package Chatroom.SettingManager;

import Chatroom.ChatroomGui;
import UserAuthenticate.UserAuthenticationManager;

/**
 * @author May_bebe
 * @version 1.0
 * @className SettingManager
 * @date 2021/6/8
 */
public class SettingManager {
    private ChatroomGui chatroomGui;
    private String selfId;
    private String selfName;
    public UserAuthenticationManager userAuthenticationManager;


    /**
     * 隐藏默认构造函数
     */
    private SettingManager(ChatroomGui parent, String selfId, String selfName) {
        this.chatroomGui=parent;
        this.selfId=selfId;
        this.selfName=selfName;
        userAuthenticationManager=UserAuthenticationManager.getUserAuthenticationManager();
    }

    /**
     * 单例模式
     *
     * @return 设置权限管理器
     */
    public static SettingManager getSettingManager(ChatroomGui parent, String selfId, String selfName) {
        if (instance == null) {
            instance = new SettingManager(parent,selfId,selfName);
        }
        return instance;
    }
    /**
     * 修改密码状态
     */
    enum ModifyPasswordStatus {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY,
        /**
         * 过短
         */
        TOO_SHORT,
        /**
         * 过长
         */
        TOO_LONG,
        /**
         * 过简
         */
        EASY,
        /**
         * 不同
         */
        DIFFERENT
    }

    /**
     * 昵称状态
     */
    enum ModifyNameStatus {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY,
        /**
         * 过长
         */
        TOO_LONG
    }

    /**
     * 最短 password
     */
    final int PASSWORD_MIN = 8;
    /**
     * 最长 password
     */
    final int PASSWORD_MAX = 20;
    /**
     * 最长 name
     */
    final int NAME_MAX = 16;
    /**
     * 合法正则表达式
     */
    final String PASSWORD_PATTERN = "^(?![A-Za-z]+$)(?![0-9]+$)(?![\\W]+$)[A-Za-z0-9\\W].*$";
    /**
     * 单例指针
     */
    private static SettingManager instance = null;


    /**
     * 修改昵称
     *
     * @param name 昵称
     * @return 修改昵称结果
     * 空昵称 EMPTY
     * 过长昵称 TOO_LONG
     * 成功修改昵称 QUALIFIED
     */
    public ModifyNameStatus modifyName(String name) {

        return ModifyNameStatus.QUALIFIED;
    }

    /**
     * 修改密码
     *
     * @param password 密码
     * @return 修改密码结果
     * 空密码 EMPTY
     * 密码过短 TOO_SHORT
     * 密码过长 TOO_LONG
     * 密码过简 EASY
     * 密码和确认密码不相同 DIFFERENT
     * 成功修改密码 QUALIFIED
     */
    public ModifyPasswordStatus setModifyPassword(String password) {

        return ModifyPasswordStatus.QUALIFIED;
    }

    public String getSelfId() {
        return selfId;
    }
    public String getSelfName(){
        return selfName;
    }
    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    public void setSelfName(String selfName) {
        this.selfName = selfName;
    }
}
