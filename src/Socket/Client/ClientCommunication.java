package Socket.Client;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.FriendManager.FriendInfo;
import Socket.tools.DataPacket;
import UserAuthenticate.UserAuthenticationGui;
import com.alibaba.fastjson.*;

import java.io.*;
import java.net.Socket;

import Chatroom.*;

/**
 * 通讯管理类 —— 客户端.
 * 为客户端提供与服务器的通信功能.
 *
 * @author IMposter
 * @version 1.6 alpha
 * @className ClientCommunication
 */
public class ClientCommunication implements Client {
    private ChatroomGui parent1;
    private UserAuthenticationGui parent2;
    private String selfID;
    private ClientThreadIn ci = null;
    private Socket socket = null;


    /**
     * 单例指针
     */
    private static ClientCommunication instance;

    private ClientCommunication(ChatroomGui parent1) throws IOException {
        this.parent1 = parent1;
    }

    private ClientCommunication(UserAuthenticationGui parent2) {
        this.parent2 = parent2;
        try {
            this.client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回单例
     *
     * @return 单例
     */
    public static ClientCommunication getClientCommunicationInstance(ChatroomGui parent) {
        if (instance == null) {
            try {
                instance = new ClientCommunication(parent);
            } catch (IOException ignored) {
            }
        }
        return instance;
    }

    public static ClientCommunication getClientCommunicationInstance(UserAuthenticationGui parent) {
        if (instance == null) {
            instance = new ClientCommunication(parent);
        }
        return instance;
    }

    public void setParent1(ChatroomGui parent1) {
        this.ci.setParent1(parent1);
        this.parent1 = parent1;
        ci.setParent1(parent1);
    }

    public void setSelfID(String selfID) {
        this.selfID = selfID;
    }

    @Override
    public boolean addFriend(String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.ADD_FRIEND;
        mes.id = selfID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public boolean deleteFriend(String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.DEL_FRIEND;
        mes.id = selfID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public ChatroomInfo addChatRoom(ChatroomInfo chatroomInfo) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.CREATE_CHATROOM;
        mes.chatRoomInfo = chatroomInfo;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return chatroomInfo;
    }

    @Override
    public boolean getFriendList() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_FRIEND_LIST;
        mes.id = selfID;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public boolean getChatroomList() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_GROUP_LIST;
        mes.id = selfID;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public boolean send(Message msg) {
        DataPacket mes = new DataPacket();
        mes.senderId = msg.getSenderId();
        mes.message = msg.getContent();
        mes.chatRoomID = msg.getChatroomId();
        mes.datetime = msg.getSendTime();
        mes.type = DataPacket.transportType.SEND_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }


    @Override
    public boolean register(String name, String password) {
        System.out.println("发送注册");
        DataPacket mes = new DataPacket();
        mes.name = name;
        mes.password = password;
        mes.type = DataPacket.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public boolean login(String id, String password) {
        DataPacket mes = new DataPacket();
        mes.id = id;
        mes.password = password;
        mes.type = DataPacket.transportType.LOGIN;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public MessageList getHistoryMessage(String chatroomId) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, null, null);
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
        mes.geyHistoryGroupID = chatroomInfo.getChatroomId();
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return null;
    }

    @Override
    public boolean joinChatRoom(ChatroomInfo chatRoom) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.JOIN_CHATROOM;
        mes.id = selfID;
        mes.chatRoomID = chatRoom.getChatroomId();
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public FriendInfo findFriend(String userID) {
        try {
            Socket socket = new Socket("127.0.0.1", 9000);
            InputStream dis = new DataInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dis));
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.FIND_FRIEND_INFO;
            mes.id = userID;
            sendToServer(JSONObject.toJSONString(mes),socket);
            while (true) {
                DataPacket dp = JSON.parseObject(bufferedReader.readLine(), DataPacket.class);
                if (dp.type == DataPacket.transportType.FIND_FRIEND_INFO && dp.systemTip == true) {
                    return dp.friendInfo;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exitChatRoom(String chatroomId) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, null, null);
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.EXIT_CHATROOM;
        mes.id = selfID;
        mes.chatRoomID = chatroomInfo.getChatroomId();
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }


    @Override
    public ChatroomInfo findChatRoomInfo(String chatRoomID) {
        try {
            Socket socket = new Socket("127.0.0.1", 9000);
            InputStream dis = new DataInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dis));
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
            mes.chatRoomID = chatRoomID;
            sendToServer(JSONObject.toJSONString(mes),socket);
            while (true) {
                DataPacket dp = JSON.parseObject(bufferedReader.readLine(), DataPacket.class);
                if (dp.type == DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID) {
                    return dp.chatRoomInfo;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChatroomInfo findChatRoomInfo(String userID1, String userID2) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_USER;
        mes.id = userID1;
        mes.friendRequestID = userID2;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return null;
    }

    @Override
    public boolean modifyName(String newName) {
        DataPacket mes = new DataPacket();
        mes.id = selfID;
        mes.type = DataPacket.transportType.MODIFY_NAME;
        mes.name = newName;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    @Override
    public boolean modifyPassword(String newPassword) {
        DataPacket mes = new DataPacket();
        mes.id = selfID;
        mes.type = DataPacket.transportType.MODIFY_PASSWORD;
        mes.password = newPassword;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
        return true;
    }

    private boolean client() throws IOException {
        socket = new Socket("10.2.61.10", 9000);
        ci = new ClientThreadIn();
        ci.setSocket(socket);
        ci.setParent2(parent2);
        ci.start();
        return true;
    }

    @Override
    public void exit() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.EXIT;
        String temp = JSONObject.toJSONString(mes);
        sendToServer(temp,socket);
    }

    public void sendToServer(String str,Socket server){
        OutputStream out = null;
        try {
            out = server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(out);
        if (str!=null&&str!=""){
            ps.println(str);
            ps.flush();
        }
    }


    public ClientCommunication() {
        super();
    }
}
