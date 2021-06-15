package Socket.Client;

import Chatroom.ChatManager.ChatManager;
import Chatroom.ChatroomGui;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSON;
import java.io.*;
import java.net.Socket;

public class ClientThreadIn extends Thread {
    private ChatroomGui parent;
    private Socket server;
    public boolean exit = false;

    public void setParent(ChatroomGui parent) {
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
                    case FIND_CHATROOM_INFO_THROUGH_USER:
                    case CREATE_CHATROOM:
                    case CREATE_PRIVATE_CHATROOM: {
                        parent.receiver(dp.chatRoomInfo,false);
                        break;
                    }
                    case RETURN_FRIEND_LIST:{
                        parent.receiver(dp.friendList);
                        break;
                    }
                    case RETURN_GROUP_LIST:{
                        parent.receiver(dp.chatRoomList);
                        break;
                    }
                    case REGISTER:{
                        parent.receiver(dp.registerStatus);
                        break;
                    }
                    case LOGIN:{
                        parent.receiver(dp.loginStatus);
                        break;
                    }
                    case GET_HISTORY_MESSAGE:{
                        parent.receiver(dp.historyMessageList,true);
                        break;
                    }
                    case SEND_MESSAGE:{
                        parent.receiver(dp.message,dp.senderId,dp.chatRoomID,dp.datetime);
                        break;
                    }
                }
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
