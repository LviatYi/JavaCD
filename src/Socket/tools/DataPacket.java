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
        /**
         * 退出
         */
        EXIT,
        /**
         * 登录
         */
        LOGIN,
        /**
         * 注册
         */
        REGISTER,
        /**
         * 发送消息
         */
        SEND_MESSAGE,
        /**
         * 修改名称
         */
        MODIFY_NAME,
        /**
         * 修改密码
         */
        MODIFY_PASSWORD,
        /**
         * 获取历史记录
         */
        GET_HISTORY_MESSAGE,
        /**
         * 新建聊天室
         */
        CREATE_CHATROOM,
        /**
         * 加入聊天室
         */
        JOIN_CHATROOM,
        /**
         * 退出聊天室
         */
        EXIT_CHATROOM,
        /**
         * 根据 ChatroomId 查询 ChatroomInfo
         */
        FIND_CHATROOM_INFO_THROUGH_ID,
        /**
         * 根据好友关系查询 PRIVATE ChatroomInfo
         */
        FIND_PRIVATE_CHATROOM_INFO_THROUGH_USER,
        /**
         * 查找 FriendInfo
         */
        FIND_FRIEND_INFO,
        /**
         * 添加好友
         */
        ADD_FRIEND,
        /**
         * 删除好友
         */
        DEL_FRIEND,
        /**
         * 更新好友列表
         */
        RETURN_FRIEND_LIST,
        /**
         * 更新聊天室列表
         */
        RETURN_GROUP_LIST,
        /**
         *
         */
        CALL_ADDED_FRIEND
    }

}