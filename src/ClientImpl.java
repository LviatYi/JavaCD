import Socket.Client.Client;
import Socket.Client.ClientThreadIn;
import Socket.Client.ClientThreadOut;
import Socket.tools.Message;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;


public class ClientImpl implements Client {

    private ClientThreadOut co =null;
    private ClientThreadIn ci = null;
    private final EncryptionImpl encryption =new EncryptionImpl();

    public void run() throws IOException {
        client();
    }

    public void sendGroup(String text)
    {
        Message mes = new Message();
        mes.message=encryption.encryptContent(text);
        mes.type= Message.transportType.SEND_GROUP_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(String.valueOf(temp));
    }

    public void sendPrivate(String text,String receiverID,String senderID)
    {
        Message mes = new Message();
        mes.receiverId =receiverID;
        mes.senderId =senderID;
        mes.message=encryption.encryptContent(text);
        mes.type=Message.transportType.SEND_PRIVATE_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(String.valueOf(temp));
    }

    public void register(String name,String password)
    {
        Message mes = new Message();
        mes.name=name;
        mes.password=encryption.encryptPassword(password);
        mes.type=Message.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(String.valueOf(temp));
    }

    public void login(String id,String password)
    {
        Message mes = new Message();
        mes.id = id;
        mes.password=encryption.encryptPassword(password);
        mes.type= Message.transportType.LOGIN;
    }

    private void client() throws IOException {
            Socket socket = new Socket("127.0.0.1", 9000);
            co.setSocket(socket);
            ci.setSocket(socket);
            co.start();
            ci.start();
    }
}
