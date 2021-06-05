package ChatRoom;

/**
 * @author May_bebe
 * @version 1.0
 * @className SettingManager
 * @date 2021/6/6
 */
public class SettingManager {
    /**
     * 修改密码状态
     */
    enum ModifyPasswordStatus
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
         * 相同
         */
        SAME,
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
    enum ModifyNameStatus
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
         * 相同
         */
        SAME,
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
    private static SettingManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private SettingManager()
    {
    }

    /**
     * 单例模式
     *
     * @return 设置权限管理器
     */
    public static SettingManager getSettingManager()
    {
        if(instance==null)
        {
            instance=new SettingManager();
        }
        return instance;
    }
    /**
     * 检测修改昵称合法性并尝试修改昵称
     *
     * @param name 昵称
     * @return 修改昵称结果
     */
    public ModifyNameStatus modifyName(String name)
    {
        if (name.length() == 0)
        {
            return ModifyNameStatus.EMPTY;
        }
        if (name.length() > NAME_MAX)
        {
            return ModifyNameStatus.TOO_LONG;
        }
        if(this.name==name)
        {
            return ModifyNameStatus.SAME;
        }
        this.name = name;
        return ModifyNameStatus.QUALIFIED;
    }

    /**
     * 检测修改密码合法性并尝试修改密码
     *
     * @param password 密码
     * @return 修改密码结果
     */
    public ModifyPasswordStatus modifyPassword(String password)
    {
        if (password.length() == 0)
        {
            return ModifyPasswordStatus.EMPTY;
        }
        if (password.length() < PASSWORD_MIN)
        {
            return ModifyPasswordStatus.TOO_SHORT;
        }
        if (password.length() > PASSWORD_MAX)
        {
            return ModifyPasswordStatus.TOO_LONG;
        }
        if (!password.matches(PASSWORD_PATTERN))
        {
            return ModifyPasswordStatus.EASY;
        }
        if(this.password==password)
        {
            return ModifyPasswordStatus.SAME;
        }
        this.password = password;
        return ModifyPasswordStatus.QUALIFIED;
    }
}
