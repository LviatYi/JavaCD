package Socket.tools;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: topkang
 * @Date: 2021/06/05/11:24
 * @Description: Message class for json
 */
public class Message {
    public String id;
    public String name;
    public String password;
    public String senderId;
    public String receiverId;
    public String message;
    public String groupID;
    public transportType type;
    public MSGLoginStatus loginStatus;
    public MSGRegisterStatus registerStatus;

    public enum transportType{
        EXIT,
        LOGIN,
        REGISTER,
        SEND_GROUP_MESSAGE,
        SEND_PRIVATE_MESSAGE,
        MODIFY_NAME,
        MODIFY_PASSWORD
    }
    public enum MSGLoginStatus{
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
    public enum MSGRegisterStatus
    {
        /**
         * 注册成功
         */
        SUCCESS,
        /**
         * 网络未连接
         */
        CONNECTION_FAILED
    }
}