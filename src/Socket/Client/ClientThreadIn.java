package Socket.Client;

import Chatroom.ChatManager.ChatManager;
import Chatroom.ChatroomGui;
import Socket.tools.DataPacket;
import UserAuthenticate.UserAuthenticationGui;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.Socket;

public class ClientThreadIn extends Thread {
    private ChatroomGui parent1;
    private UserAuthenticationGui parent2;
    private Socket server;
    public boolean exit = false;

    public void setParent1(ChatroomGui parent) {
        this.parent1 = parent;
    }

    public void setParent2(UserAuthenticationGui parent2) {
        this.parent2 = parent2;
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
            InputStream in = new DataInputStream(server.getInputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            while (!exit) {
                String str = bf.readLine();
                System.out.println(str);
                DataPacket dp = JSON.parseObject(str, DataPacket.class);
                switch (dp.type) {
                    case CREATE_CHATROOM: {
                        parent1.getChatroomManager().receiver(dp.chatRoomInfo, true);
                        parent1.receiver(dp.chatRoomID, true);
                        break;
                    }
                    case ADD_FRIEND:
                    case RETURN_FRIEND_LIST:
                    case CALL_ADDED_FRIEND: {
                        parent1.getAddressManager().receiver(dp.friendList);
                        break;
                    }
                    case RETURN_GROUP_LIST: {
                        parent1.getChatroomManager().receiver(dp.chatRoomList);
                        break;
                    }
                    case REGISTER: {
                        parent2.receiver(dp.registerStatus, dp.id);
                        break;
                    }
                    case LOGIN: {
                        switch (dp.loginStatus){
                            case SUCCESS:{
                                parent2.receiver(dp.loginStatus);
                                parent1.receiver(dp.id, true);
                                parent1.getSettingManager().receiver(dp.name);
                                break;
                            }
                            case ID_NOT_EXIST:
                            case PASSWORD_ERROR: {
                                parent2.receiver(dp.loginStatus);
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                        break;
                    }
                    case GET_HISTORY_MESSAGE: {
                        parent1.getChatManager().receiver(dp.historyMessageList, true);
                        break;
                    }
                    case SEND_MESSAGE: {
                        parent1.getChatManager().receiver(dp.message, dp.senderId, dp.chatRoomID, dp.datetime);
                        break;
                    }
                    default:{
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
