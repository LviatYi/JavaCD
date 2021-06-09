package Socket.Client;

import Encrypt.EncryptionImpl;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientImpl implements Client {

    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;
    private final EncryptionImpl encryption = new EncryptionImpl();
    public List<DataPacket> privateChat = new ArrayList<>();
    public List<DataPacket> multiChat = new ArrayList<>();

    public void run() throws IOException {
        client();
    }


    public void addFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.ADD_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    public void deleteFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.DEL_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    public void getFriendList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_FRIEND_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    public void getGroupList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_GROUP_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    发送群聊消息
    public void sendGroup(String text, String senderID, String groupID) {
        DataPacket mes = new DataPacket();
        mes.message = encryption.encryptContent(text);
        mes.senderId = senderID;
        mes.chatRoomID = groupID;
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
    public void getHistoryMessage(String userID, String groupID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
        mes.id = userID;
        mes.geyHistoryGroupID = groupID;
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
