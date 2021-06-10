package Socket.tools;

import ChatRoom.ChatManager.Message;
import ChatRoom.ChatManager.MessageList;
import ChatRoom.ChatRoomManager.ChatRoomInfo;
import ChatRoom.ChatRoomManager.ChatRoomList;
import ChatRoom.FriendManager.FriendInfo;
import ChatRoom.FriendManager.FriendList;

import java.util.Date;
import java.util.List;

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
    public String chatRoomID;
    public String chatRoomName;
    public transportType type;
    public MSGLoginStatus loginStatus;
    public String geyHistoryGroupID;
    public MSGRegisterStatus registerStatus;
    public Message msg;
    public Date datetime;
    public int systemTip;
    public ChatRoomInfo chatRoomInfo;
    public FriendList friendList;
    public ChatRoomList groupList;
    public MessageList historyMessageList;

    public enum transportType{
        EXIT,
        LOGIN,
        REGISTER,
        SEND_MESSAGE,
        MODIFY_NAME,
        MODIFY_PASSWORD,
        GET_HISTORY_MESSAGE,
        CREATE_CHATROOM,
        CREATE_PRIVATE_CHATROOM,
        JOIN_CHATROOM,
        EXIT_CHATROOM,
        FIND_CHATROOM_INFO_THROUGH_ID,
        FIND_CHATROOM_INFO_THROUGH_USER,
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