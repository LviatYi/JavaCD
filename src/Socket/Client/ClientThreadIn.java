package Socket.Client;

import ChatRoom.ChatManager.ClientChatManager;
import ChatRoom.ChatManager.Message;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSON;
import ChatRoom.ChatManager.ClientChatManager;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientThreadIn extends Thread {

    private Socket server;
    public boolean exit = false;

    public ClientThreadIn() {
    }

    public void setSocket(Socket socket) {
        this.server = socket;
    }


    @Override
    public void run() {
        In();
    }


    private void In() {
        try {
            while (!exit) {
                DataInputStream in = new DataInputStream(server.getInputStream());
                String str = in.readUTF();
                DataPacket dp = JSON.parseObject(str, DataPacket.class);
                ClientChatManager a = null;
                a.receiver(dp.msg);
                //TODO 拆分服务器发来的消息
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
