package ChatRoom.SettingManager;

import ChatRoom.ChatRoomGuiControl;

/**
 * @author May_bebe
 * @version 1.0
 * @className SettingManager
 * @date 2021/6/8
 */
public class SettingManager {

    ChatRoomGuiControl chatRoomGuiControl;

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
     * 隐藏默认构造函数
     */
    private SettingManager() {
    }

    /**
     * 单例模式
     *
     * @return 设置权限管理器
     */
    public static SettingManager getSettingManager() {
        if (instance == null) {
            instance = new SettingManager();
        }
        return instance;
    }

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

        if (name.length() == 0) {
            //弹出窗口 失败修改昵称
            chatRoomGuiControl.confirmWrongModifyName();
            return ModifyNameStatus.EMPTY;
        }
        if (name.length() > NAME_MAX) {
            chatRoomGuiControl.confirmWrongModifyName();
            return ModifyNameStatus.TOO_LONG;
        }
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


        if (password.length() < PASSWORD_MIN) {
            chatRoomGuiControl.confirmWrongModifyPassword();
            return ModifyPasswordStatus.TOO_SHORT;
        }
        if (password.length() > PASSWORD_MAX) {
            chatRoomGuiControl.confirmWrongModifyPassword();
            return ModifyPasswordStatus.TOO_LONG;
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            chatRoomGuiControl.confirmWrongModifyPassword();
            return ModifyPasswordStatus.EASY;
        }
        return ModifyPasswordStatus.QUALIFIED;
    }

}
