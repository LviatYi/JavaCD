package Socket.Client;

import Chatroom.ChatManager.ChatManager;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSON;
import java.io.*;
import java.net.Socket;

public class ClientThreadIn extends Thread {
    private ChatManager parent;
    private Socket server;
    public boolean exit = false;

    public void setParent(ChatManager parent) {
        this.parent = parent;
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
                switch (dp.type){
                    case ADD_FRIEND:{
                        //todo
                    }
                    case DEL_FRIEND:{
                        //todo
                    }
                }
                parent.receiver(dp.msg);
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
