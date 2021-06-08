package Socket.Client;

import Encrypt.EncryptionImpl;
import Socket.Client.Client;
import Socket.Client.ClientThreadIn;
import Socket.Client.ClientThreadOut;
import Socket.tools.Message;
import com.alibaba.fastjson.JSONObject;

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
    public void sendGroup(String text,String senderID,String groupID) {
        Message mes = new Message();
        mes.message = encryption.encryptContent(text);
        mes.senderId = senderID;
        mes.groupID =groupID;
        mes.type = Message.transportType.SEND_GROUP_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

//    发送私聊消息
    public void sendPrivate(String text, String receiverID, String senderID) {
        Message mes = new Message();
        mes.receiverId = receiverID;
        mes.senderId = senderID;
        mes.message = encryption.encryptContent(text);
        mes.type = Message.transportType.SEND_PRIVATE_MESSAGE;
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

    //将ci中的Message列表拆分成群聊/私聊列表
    public void splitList() {
        for (Message a : ci.msgList)
        {
            if (a.type == Message.transportType.SEND_GROUP_MESSAGE) {
                multiChat.add(a);
            } else if (a.type == Message.transportType.SEND_PRIVATE_MESSAGE) {
                privateChat.add(a);
            }
        }
    }

//    改名
    public void modifyName(String newName,String UserID)
    {
        Message mes = new Message();
        mes.id =UserID;
        mes.type= Message.transportType.MODIFY_NAME;
        mes.name=newName;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

//    改密码
    public void modifyPassword(String newPassword,String UserID)
    {
        Message mes = new Message();
        mes.id =UserID;
        mes.type= Message.transportType.MODIFY_PASSWORD;
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
