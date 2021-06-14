package Socket.Client;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Socket.tools.DataPacket;
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
    private ChatroomGui parent;
    String selfID;
    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;

    public void run() throws IOException {
        client();
    }

    /**
     * 单例指针
     */
    private static ClientCommunication instance;

    private ClientCommunication(ChatroomGui parent) throws IOException {
        this.parent = parent;
        selfID = parent.getSettingManager().getSelfId();
        this.run();
    }

    /**
     * 返回单例
     *
     * @return 单例
     */
    public static ClientCommunication getClientCommunication() {
        if (instance == null) {
            instance = new ClientCommunication();
        }
        return instance;
    }

    @Override
    public boolean addFriend(String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.ADD_FRIEND;
        mes.id = selfID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo -1已有好友 0添加失败 1添加成功 systemTip

        return true;
    }

    @Override
    public boolean deleteFriend(String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.DEL_FRIEND;
        mes.id = selfID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0失败 1成功
        return true;
    }

    @Override
    public ChatroomInfo addChatRoom(ChatroomInfo chatroomInfo) {
        if (chatroomInfo.getChatroomType() == ChatroomInfo.ChatroomType.PUBLIC) {
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_CHATROOM;
            mes.chatRoomName = chatroomInfo.getChatroomName();
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
            //todo 返回chatRoomID 0失败 1成功
        } else if (chatroomInfo.getChatroomType() == ChatroomInfo.ChatroomType.PRIVATE) {
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_PRIVATE_CHATROOM;
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
            //todo 返回chatRoomID 0失败 1成功
        }
        return chatroomInfo;
    }

    @Override
    public boolean getFriendList() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_FRIEND_LIST;
        mes.id = selfID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //TODO 返回friendInfo List
        return true;
    }

    @Override
    public boolean getChatroomList() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_GROUP_LIST;
        mes.id = selfID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //TODO 返回ChatRoomInfo List
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
        co.setMessage(temp);
        return true;
    }

    @Override
    public boolean register(String name, String password) {
        DataPacket mes = new DataPacket();
        mes.name = name;
        mes.password = password;
        mes.type = DataPacket.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回枚举类 和 id
        return true;
    }

    @Override
    public boolean login(String id, String password) {
        DataPacket mes = new DataPacket();
        mes.id = id;
        mes.password = password;
        mes.type = DataPacket.transportType.LOGIN;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回枚举类
        return true;
    }

    @Override
    public MessageList getHistoryMessage(String chatroomId) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, null, null);
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
        mes.geyHistoryGroupID = chatroomInfo.getChatroomId();
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回Message List

        return null;
    }

    @Override
    public boolean joinChatRoom(ChatroomInfo chatRoom) {
        try {
            /*
             * TODO_LviatYi 不需要同步
             * date 2021/6/14
             */
            Socket socket = new Socket("127.0.0.1", 9000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.JOIN_CHATROOM;
            mes.id = selfID;
            mes.chatRoomID = chatRoom.getChatroomId();
            dos.writeUTF(JSONObject.toJSONString(mes));
            dos.flush();
            while (true) {
                DataPacket dp = JSON.parseObject(dis.readUTF(), DataPacket.class);
                if (dp.type == DataPacket.transportType.JOIN_CHATROOM && dp.systemTip == 1) {
                    socket.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exitChatRoom(String chatroomId) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, null, null);
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.EXIT_CHATROOM;
        mes.id = selfID;
        mes.chatRoomID = chatroomInfo.getChatroomId();
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);

        //todo 0/1
        return true;
    }


    @Override
    public ChatroomInfo findChatRoomInfo(String chatRoomID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
        mes.chatRoomID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回ChatRoomInfo
        return null;
        /*
         * TODO_LviatYi 希望同步
         * date 2021/6/14
         */
    }

    @Override
    public ChatroomInfo findChatRoomInfo(String userID1, String userID2) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_USER;
        mes.id = userID1;
        mes.friendRequestID = userID2;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回ChatRoomInfo
        return null;
    }

    @Override
    public boolean modifyName(String newName) {
        DataPacket mes = new DataPacket();
        mes.id = selfID;
        mes.type = DataPacket.transportType.MODIFY_NAME;
        mes.name = newName;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0/1
        return true;
    }

    @Override
    public boolean modifyPassword(String newPassword) {
        DataPacket mes = new DataPacket();
        mes.id = selfID;
        mes.type = DataPacket.transportType.MODIFY_PASSWORD;
        mes.password = newPassword;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0/1
        return true;
    }

    private boolean client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        co.setSocket(socket);
        ci.setSocket(socket);
        ci.setParent(parent);
        co.start();
        ci.start();
        return true;
    }

    @Override
    public void exit() {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.EXIT;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    public ClientCommunication() {
        super();
    }
}
