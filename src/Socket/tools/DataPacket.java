package Socket.tools;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: topkang
 * @Date: 2021/06/05/11:24
 * @Description: Message class for json
 */
public class DataPacket {
    public String id;
    public String friendRequestID;
    public String name;
    public String password;
    public String senderId;
    public String message;
    public String groupID;
    public transportType type;
    public MSGLoginStatus loginStatus;
    public String geyHistoryGroupID;
    public MSGRegisterStatus registerStatus;
    public Date datetime;




    public enum transportType{
        EXIT,
        LOGIN,
        REGISTER,
        SEND_MESSAGE,
        MODIFY_NAME,
        MODIFY_PASSWORD,
        GET_HISTORY_MESSAGE,
        ADD_GROUP,
        ADD_PRIVATE_GROUP,
        ADD_FRIEND,
        DEL_FRIEND,
        RETURN_FRIEND_LIST,
        RETURN_GROUP_LIST
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