package UserAuthenticate;

import Chatroom.ChatroomGui;
import Encrypt.*;
import Status.*;

/**
 * Manager of User Authentication.
 * Designed by SINGLETON mode.
 * 单例模式，保证只有一个单例
 *
 * @author LVIAT.cn
 * @version 1.0
 * @className UserAuthenticationManager
 * @date 2021/6/2
 */
public class UserAuthenticationManager {

    //Tool Status

    /**
     * 密码状态
     */
    public enum PasswordStatus {
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
        EASY
    }

    /**
     * 昵称状态
     */
    public enum NameStatus {
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
        TOO_LONG,
    }

    /**
     * 账号状态
     */
    public enum IdStatus {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY
    }

    // Field

    private UserAuthenticationGui parent1;
    private ChatroomGui parent2;
    /**
     * 账号
     */
    private String id = "";
    /**
     * 昵称
     */
    private String name = "";
    private boolean nameIsChanged;
    /**
     * 密码
     */
    private String password = "";
    private boolean passwordIsChanged;

    // Constant

    /**
     * 最短 ID
     */
    final int ID_MIN = 6;
    /**
     * 最长 ID
     */
    final int ID_MAX = 10;
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

    // Construct

    /**
     * 单例指针
     */
    private static UserAuthenticationManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private UserAuthenticationManager(UserAuthenticationGui parent) {
        this.setParent1(parent);
    }

    private UserAuthenticationManager(ChatroomGui parent) {
        this.setParent2(parent);
    }

    /**
     * 单例模式 获得用户权限管理器
     *
     * @return 用户权限管理器
     */
    public static UserAuthenticationManager getUserAuthenticationManager(ChatroomGui parent) {
        if (instance == null) {
            instance = new UserAuthenticationManager(parent);
        }
        if (instance.getParent2() == null) {
            instance.setParent2(parent);
        }
        return instance;
    }

    public static UserAuthenticationManager getUserAuthenticationManager(UserAuthenticationGui parent) {
        if (instance == null) {
            instance = new UserAuthenticationManager(parent);
        }
        if (instance.getParent1() == null) {
            instance.setParent1(parent);
        }
        return instance;
    }

    // Getter Setter

    /**
     * 检测账号合法性并尝试设置账号
     *
     * @param id 账号
     * @return id 设置结果
     */
    public IdStatus setId(String id) {
        if (id.length() == 0) {
            return IdStatus.EMPTY;
        }
        this.id = id;
        return IdStatus.QUALIFIED;
    }

    /**
     * 检测昵称合法性并尝试设置昵称
     *
     * @param name 昵称
     * @return 设置结果
     */
    public NameStatus setName(String name) {
        if (name.length() == 0) {
            return NameStatus.EMPTY;
        }
        if (name.length() > NAME_MAX) {
            return NameStatus.TOO_LONG;
        }
        this.name = name;
        this.nameIsChanged = true;
        return NameStatus.QUALIFIED;
    }

    /**
     * 检测密码合法性并尝试设置密码
     *
     * @param password 密码
     * @return 设置结果
     */
    public PasswordStatus setPassword(String password) {
        if (password.length() == 0) {
            return PasswordStatus.EMPTY;
        }
        if (password.length() < PASSWORD_MIN) {
            return PasswordStatus.TOO_SHORT;
        }
        if (password.length() > PASSWORD_MAX) {
            return PasswordStatus.TOO_LONG;
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            return PasswordStatus.EASY;
        }
        this.password = Encryption.encryptPassword(password);
        this.passwordIsChanged = true;
        return PasswordStatus.QUALIFIED;
    }

    public String getUserId() {
        return id;
    }

    public String getUserName() {
        return name;
    }

    public UserAuthenticationGui getParent1() {
        return parent1;
    }

    public ChatroomGui getParent2() {
        return parent2;
    }

    public void setParent1(UserAuthenticationGui parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(ChatroomGui parent2) {
        this.parent2 = parent2;
    }

    // Function

    /**
     * 尝试登录
     *
     * @return 登录状态
     */
    public LoginStatus login() {
        getParent1().getClientCommunication().login(this.id, this.password);
        this.passwordIsChanged = false;
        this.nameIsChanged = false;
        return LoginStatus.SUCCESS;
    }

    /**
     * 尝试注册
     *
     * @return 注册状态
     */
    public RegisterStatus register() {
        getParent1().getClientCommunication().register(this.name, this.password);
        this.passwordIsChanged = false;
        this.nameIsChanged = false;
        return RegisterStatus.SUCCESS;
    }

    /**
     * 设置新的 UserName 或 Password
     *
     * @return 用户名设置状态
     */
    public boolean setNew() {
        if (this.nameIsChanged) {
            getParent2().getClientCommunication().modifyName(name);
            getParent2().getSettingManager().setSelfName(name);
            this.nameIsChanged = false;
        }
        if (this.passwordIsChanged) {
            getParent2().getClientCommunication().modifyPassword(password);
            this.passwordIsChanged = false;
        }
        getParent2().updateMyInfoPl();
        return true;
    }
}
