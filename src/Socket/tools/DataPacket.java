package Socket.tools;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ChatroomManager.ChatroomManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: topkang,IMposter
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
    public LoginStatus loginStatus;
    public String geyHistoryGroupID;
    public RegisterStatus registerStatus;
    public Message msg;
    public Date datetime;
    public boolean systemTip;
    public ChatroomInfo chatRoomInfo;
    public FriendList friendList;
    public FriendInfo friendInfo;
    public ChatroomList chatRoomList;
    public MessageList historyMessageList;
    public ChatroomStatus chatroomStatus;

    public enum transportType{
        EXIT,
        LOGIN,
        REGISTER,
        SEND_MESSAGE,
        MODIFY_NAME,
        MODIFY_PASSWORD,
        GET_HISTORY_MESSAGE,
        CREATE_CHATROOM,
        CHATROOM_NEW_MEMBER,
        JOIN_CHATROOM,
        EXIT_CHATROOM,
        FIND_CHATROOM_INFO_THROUGH_ID,
        FIND_CHATROOM_INFO_THROUGH_USER,
        FIND_FRIEND_INFO,
        ADD_FRIEND,
        DEL_FRIEND,
        RETURN_FRIEND_LIST,
        RETURN_GROUP_LIST,
        CALL_ADDED_FRIEND
    }

}