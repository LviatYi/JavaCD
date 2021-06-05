package Status;

/**
 * 登录状态
 *
 * @author LviatYi
 * @version 1.0
 * @enumName LoginStatus
 * @date 2021/6/5
 */
public enum  LoginStatus
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
