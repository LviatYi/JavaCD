package UserAuthenticate;

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
public class UserAuthenticationManager
{
    /**
     * 密码状态
     */
    enum PasswordStatus
    {
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
    enum NameStatus
    {
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
    enum IdStatus
    {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY
    }



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

    /**
     * 账号
     */
    private String id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 密码
     */
    private String password;

    /**
     * 单例指针
     */
    private static UserAuthenticationManager instance = null;

    /**
     * 加密接口
     */
    private Encryption encryption;
    /**
     * 通信接口
     */
    /**
     * TODO_LviatYi 通信接口
     * date 2021/6/4
     */


    /**
     * 隐藏默认构造函数
     */
    private UserAuthenticationManager()
    {
    }

    /**
     * 单例模式 获得用户权限管理器
     *
     * @return 用户权限管理器
     */
    public static UserAuthenticationManager getUserAuthenticationManager()
    {
        if (instance == null)
        {
            instance = new UserAuthenticationManager();
        }

        return instance;
    }

    /**
     * 检测账号合法性并尝试设置账号
     *
     * @param id 账号
     * @return id 设置结果
     */
    public IdStatus setId(String id)
    {
        if (id.length() == 0)
        {
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
    public NameStatus setName(String name)
    {
        if (name.length() == 0)
        {
            return NameStatus.EMPTY;
        }
        if (name.length() > NAME_MAX)
        {
            return NameStatus.TOO_LONG;
        }
        this.name = name;
        return NameStatus.QUALIFIED;
    }

    /**
     * 检测密码合法性并尝试设置密码
     *
     * @param password 密码
     * @return 设置结果
     */
    public PasswordStatus setPassword(String password)
    {
        if (password.length() == 0)
        {
            return PasswordStatus.EMPTY;
        }
        if (password.length() < PASSWORD_MIN)
        {
            return PasswordStatus.TOO_SHORT;
        }
        if (password.length() > PASSWORD_MAX)
        {
            return PasswordStatus.TOO_LONG;
        }
        if (!password.matches(PASSWORD_PATTERN))
        {
            return PasswordStatus.EASY;
        }
        this.password = password;
        return PasswordStatus.QUALIFIED;
    }

    /**
     * 尝试登录
     *
     * @return 登录状态
     */
    public LoginStatus login()
    {
        /**
         * TODO_LviatYi 登录函数
         * date 2021/6/4
         */
        return LoginStatus.CONNECTION_FAILED;
    }

    /**
     * 尝试注册
     *
     * @return 注册状态
     */
    public RegisterStatus register()
    {
        /**
         * TODO_LviatYi 尝试注册
         * date 2021/6/4
         */
        return RegisterStatus.CONNECTION_FAILED;
    }

}
