package Socket.Client;

import ChatRoom.ChatManager.Message;
import ChatRoom.ChatRoomManager.ChatRoomInfo;
import Encrypt.EncryptionImpl;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;



public class ClientImpl implements Client {

    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;
    private final EncryptionImpl encryption = new EncryptionImpl();

    public void run() throws IOException {
        client();
    }

    //增加好友
    public void addFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.ADD_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //删除好友
    public void deleteFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.DEL_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //创建聊天室
    public void addChatRoom(String chatRoomName, ChatRoomInfo.ChatRoomType chatRoomType){
        if(chatRoomType== ChatRoomInfo.ChatRoomType.PUBLIC){
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_CHATROOM;
            mes.chatRoomName = chatRoomName;
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
        }
        else if (chatRoomType== ChatRoomInfo.ChatRoomType.PRIVATE){
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_PRIVATE_CHATROOM;
            mes.chatRoomName = chatRoomName;
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
        }
    }

    //返回好友列表
    public void getFriendList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_FRIEND_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //返回群聊列表
    public void getGroupList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_GROUP_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    发送群聊消息
    public void sendGroup(Message msg) {
        DataPacket mes = new DataPacket();
        mes.message = encryption.encryptContent(msg.getContent());
        mes.senderId = msg.getSenderId();
        mes.chatRoomID = msg.getChatRoomId();
        mes.type = DataPacket.transportType.SEND_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }


    //    发送注册消息
    public void register(String name, String password) {
        DataPacket mes = new DataPacket();
        mes.name = name;
        mes.password = encryption.encryptPassword(password);
        mes.type = DataPacket.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    发送登录消息
    public void login(String id, String password) {
        DataPacket mes = new DataPacket();
        mes.id = id;
        mes.password = encryption.encryptPassword(password);
        mes.type = DataPacket.transportType.LOGIN;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //获取特定群的历史记录
    public void getHistoryMessage(String userID, String chatRoomID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
        mes.id = userID;
        mes.geyHistoryGroupID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

//退出聊天室
    public void exitChatRoom(String userID,String chatRoomID){
        DataPacket mes = new DataPacket();
        mes.type= DataPacket.transportType.EXIT_CHATROOM;
        mes.id=userID;
        mes.chatRoomID=chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //通过聊天室ID查找特定聊天室信息
    public void findChatRoomInfo(String chatRoomID){
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
        mes.chatRoomID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //查找私有聊天室信息
    public void findChatRoomInfo(String userID1,String userID2){
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_USER;
        mes.id = userID1;
        mes.friendRequestID = userID2;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    改名
    public void modifyName(String newName, String UserID) {
        DataPacket mes = new DataPacket();
        mes.id = UserID;
        mes.type = DataPacket.transportType.MODIFY_NAME;
        mes.name = newName;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    改密码
    public void modifyPassword(String newPassword, String UserID) {
        DataPacket mes = new DataPacket();
        mes.id = UserID;
        mes.type = DataPacket.transportType.MODIFY_PASSWORD;
        mes.password = encryption.encryptPassword(newPassword);
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    private void client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        co.setSocket(socket);
        ci.setSocket(socket);
        co.start();
        ci.start();
    }

    public void exit() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.EXIT;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }
}
