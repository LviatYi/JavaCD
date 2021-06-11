package Socket.Client;

import ChatRoom.ChatManager.ChatManager;
import ChatRoom.ChatManager.Message;
import ChatRoom.ChatRoomManager.ChatRoomInfo;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.net.Socket;


public class ClientImpl implements Client {
    private ChatManager parent;
    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;
    public void run() throws IOException {
        client();
    }

    public ClientImpl(ChatManager parent) throws IOException{
        this.parent = parent;
        this.run();
    }

    //增加好友
    public void addFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.ADD_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo -1已有好友 0添加失败 1添加成功 systemTip
    }

    //删除好友
    public void deleteFriend(String userID, String receiverID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.DEL_FRIEND;
        mes.id = userID;
        mes.friendRequestID = receiverID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0失败 1成功
    }

    //创建聊天室
    public void addChatRoom(String chatRoomName, ChatRoomInfo.ChatRoomType chatRoomType){
        if(chatRoomType== ChatRoomInfo.ChatRoomType.PUBLIC){
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_CHATROOM;
            mes.chatRoomName = chatRoomName;
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
            //todo 返回chatRoomID 0失败 1成功
        }
        else if (chatRoomType== ChatRoomInfo.ChatRoomType.PRIVATE){
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.CREATE_PRIVATE_CHATROOM;
            String temp = JSONObject.toJSONString(mes);
            co.setMessage(temp);
            //todo 返回chatRoomID 0失败 1成功
        }
    }

    //返回好友列表
    public void getFriendList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_FRIEND_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //TODO 返回friendInfo List
    }

    //返回群聊列表
    public void getGroupList(String userID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.RETURN_GROUP_LIST;
        mes.id = userID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //TODO 返回ChatRoomInfo List
    }

    //    发送群聊消息
    public void sendGroup(Message msg) {
        DataPacket mes = new DataPacket();
        mes.senderId = msg.getSenderId();
        mes.message = msg.getContent();
        mes.chatRoomID = msg.getChatRoomId();
        mes.datetime = msg.getSendTime();
        mes.type = DataPacket.transportType.SEND_MESSAGE;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //    发送注册消息
    public void register(String name, String password) {
        DataPacket mes = new DataPacket();
        mes.name = name;
        mes.password = password;
        mes.type = DataPacket.transportType.REGISTER;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回枚举类 和 id
    }

    //    发送登录消息
    public void login(String id, String password) {
        DataPacket mes = new DataPacket();
        mes.id = id;
        mes.password = password;
        mes.type = DataPacket.transportType.LOGIN;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回枚举类
    }

    //获取特定群的历史记录
    public void getHistoryMessage(String chatRoomID) {
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
        mes.geyHistoryGroupID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回Message List
    }

    //退出聊天室
    public void exitChatRoom(String userID,String chatRoomID){
        DataPacket mes = new DataPacket();
        mes.type= DataPacket.transportType.EXIT_CHATROOM;
        mes.id=userID;
        mes.chatRoomID=chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);

        //todo 0/1
    }

    //todo join chatRoom id chatRoomID type 0/1
    public void joinChatRoom(String userID, String chatRoomID){
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.JOIN_CHATROOM;
        mes.id = userID;
        mes.chatRoomID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
    }

    //通过聊天室ID查找特定聊天室信息
    public void findChatRoomInfo(String chatRoomID){
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
        mes.chatRoomID = chatRoomID;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回ChatRoomInfo
    }

    //查找私有聊天室信息
    public void findChatRoomInfo(String userID1,String userID2){
        DataPacket mes = new DataPacket();
        mes.type = DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_USER;
        mes.id = userID1;
        mes.friendRequestID = userID2;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 返回ChatRoomInfo
    }

    //    改名
    public void modifyName(String newName, String UserID) {
        DataPacket mes = new DataPacket();
        mes.id = UserID;
        mes.type = DataPacket.transportType.MODIFY_NAME;
        mes.name = newName;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0/1
    }

    //    改密码
    public void modifyPassword(String newPassword, String UserID) {
        DataPacket mes = new DataPacket();
        mes.id = UserID;
        mes.type = DataPacket.transportType.MODIFY_PASSWORD;
        mes.password = newPassword;
        String temp = JSONObject.toJSONString(mes);
        co.setMessage(temp);
        //todo 0/1
    }

    private void client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        co.setSocket(socket);
        ci.setSocket(socket);
        ci.setParent(parent);
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
