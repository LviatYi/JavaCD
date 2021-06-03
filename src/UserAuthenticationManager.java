/**
 * Manager of User Authentication
 * Singleton mode
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
     * 登录结果
     */
    enum LoginStatus
    {
        /**
         * 登录成功
         */
        SUCCESS,
        /**
         * 网络未连接
         */
        CONNECTION_FAILED,
        /**
         * 账号不存在
         */
        ID_NOT_EXIST,
        /**
         * 密码错误
         */
        PASSWORD_ERROR
    }

    /**
     * 注册结果
     */
    enum RegisterStatus
    {
        /**
         * 注册成功
         */
        SUCCESS,
        /**
         * 网络未连接
         */
        CONNECTION_FAILED,
        /**
         * 密码不一致
         */
        PASSWORD_DIFFERENT,
        /**
         * 密码过短
         */
        PASSWORD_SHORT,
        /**
         * 密码过长
         */
        PASSWORD_L0NG,
        /**
         * 密码非法
         */
        PASSWORD_ILLEGAL,
        /**
         * 昵称过长
         */
        NAME_LONG
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


    private static UserAuthenticationManager instance = null;
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
     * 加密接口
     */
    private Encryption encryption;

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
     * 设置并检测账号合法性
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
     * 设置并检测昵称合法性
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
     * 设置并检测密码合法性
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
     * TODO_LviatYi
     * 需要与服务器通信
     * date 2021/6/2
     */

}
