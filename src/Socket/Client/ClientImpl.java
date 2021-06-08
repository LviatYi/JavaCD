package Socket.Client;

import Encrypt.EncryptionImpl;
import Socket.Client.Client;
import Socket.Client.ClientThreadIn;
import Socket.Client.ClientThreadOut;
import Socket.tools.Message;
import com.alibaba.fastjson.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientImpl implements Client {

    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;
    private final EncryptionImpl encryption = new EncryptionImpl();
    public List<Message> privateChat = new ArrayList<>();
    public List<Message> multiChat = new ArrayList<>();

    public void run() throws IOException {
        client();
    }

    //    发送群聊消息
    public void sendGroup(String text, String senderID, String groupID) {
        Message mes = new Message();
        mes.message = encryption.encryptContent(text);
        mes.senderId = senderID;
        mes.groupID = groupID;
        mes.type = Message.transportType.SEND_GROUP_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }



    //    发送注册消息
    public void register(String name, String password) {
        Message mes = new Message();
        mes.name = name;
        mes.password = encryption.encryptPassword(password);
        mes.type = Message.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    发送登录消息
    public void login(String id, String password) {
        Message mes = new Message();
        mes.id = id;
        mes.password = encryption.encryptPassword(password);
        mes.type = Message.transportType.LOGIN;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    public void getHistoryMessage(String userID,String groupID) {
        Message mes = new Message();
        mes.type = Message.transportType.GET_HISTORY_MESSAGE;
        mes.id = userID;
        mes.geyHistoryGroupID = groupID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }


    //    改名
    public void modifyName(String newName, String UserID) {
        Message mes = new Message();
        mes.id = UserID;
        mes.type = Message.transportType.MODIFY_NAME;
        mes.name = newName;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    改密码
    public void modifyPassword(String newPassword, String UserID) {
        Message mes = new Message();
        mes.id = UserID;
        mes.type = Message.transportType.MODIFY_PASSWORD;
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
        Message mes = new Message();
        mes.type = Message.transportType.EXIT;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }
}
