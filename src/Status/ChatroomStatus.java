package Status;

/**
 * 聊天室状态
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @enumName ChatroomStatus
 * @date 2021/6/15
 */
public enum ChatroomStatus {

    /**
     * 合格
     */
    QUALIFIED,
    /**
     * 未知错误
     */
    ERROR,
    /**
     * 已加入
     */
    JOINED,
    /**
     * 待新建
     */
    NEW,
    /**
     * 不存在
     */
    NOT_EXIST,
    /**
     * 取消操作
     */
    CANCEL,
    /**
     * 私有聊天室
     */
    PRIVATE,
}
