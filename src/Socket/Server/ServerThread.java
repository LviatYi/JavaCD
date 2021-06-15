package Socket.Server;

import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.FriendManager.FriendInfo;
import DataBase.DatabaseManager;
import Socket.tools.DataPacket;
import Status.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * 每当有客户机和服务器连接时，都要定义一个接受对象来进行数据的传输
 *  从服务器的角度看，这个类就是客户端
 */
public class ServerThread extends Thread{
    /**
     * 线程中的处理对象
     */
    private final Socket client;

    private DataOutputStream ous ;
    public ServerThread(Socket client) {
        this.client=client;
    }
    public DatabaseManager databaseManager =new DatabaseManager();
    public String socketId;

    @Override
    public void run() {
        try {
            processSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processSocket() throws IOException {
        InputStream ins = client.getInputStream();
        ous = new DataOutputStream(client.getOutputStream());
        BufferedReader brd=new BufferedReader(new InputStreamReader(ins));

        while(JSON.parseObject(brd.readLine(), DataPacket.class).type!= DataPacket.transportType.EXIT)
        {
            DataPacket dataPacket = JSON.parseObject(brd.readLine(), DataPacket.class);
            switch (dataPacket.type)
            {
                //注册
                case REGISTER:
                {
                    String id = databaseManager.register(dataPacket.password,dataPacket.name);
                    DataPacket temp = new DataPacket();
                    temp.registerStatus = RegisterStatus.SUCCESS;
                    temp.type = DataPacket.transportType.REGISTER;
                    temp.id = id;
                    sendMsg(temp);
                    break;
                }
                //登录
                case LOGIN:
                {
                    LoginStatus loginStatus = databaseManager.login(dataPacket.id, dataPacket.password);
                    switch (loginStatus)
                    {
                        case SUCCESS: {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = LoginStatus.SUCCESS;
                            temp.type = DataPacket.transportType.LOGIN;
                            sendMsg(temp);
                            temp.id = databaseManager.returnChatRoomId();
                            socketId = dataPacket.id;
                            MultiThread.addClient(this,databaseManager.getUserChatroomList(socketId));//认证成功，把这个用户加入服务器队列
                            break;
                        }
                        case ID_NOT_EXIST:
                        {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = LoginStatus.ID_NOT_EXIST;
                            temp.type = DataPacket.transportType.LOGIN;
                            sendMsg(temp);
                            break;
                        }
                        case PASSWORD_ERROR:
                        {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = LoginStatus.PASSWORD_ERROR;
                            temp.type = DataPacket.transportType.LOGIN;
                            sendMsg(temp);
                            break;
                        }
                        default:
                            break;
                    }
                    break;
                }
                //保存数据并向聊天室转发
                case SEND_MESSAGE:
                {
                    databaseManager.recordMessage(dataPacket.senderId,dataPacket.message,dataPacket.chatRoomID, dataPacket.datetime);
                    MultiThread.castGroupMsg(dataPacket);//群发给在线用户已经收到的群发消息
                    break;
                }
                case CREATE_CHATROOM:
                {
                    ChatroomInfo chatroomInfoTemp;
                    DataPacket temp = new DataPacket();
                    FriendInfo friendInfo = new FriendInfo(socketId," ");
                    chatroomInfoTemp=databaseManager.createChatroom(dataPacket.chatRoomInfo,friendInfo);
                    temp.chatRoomInfo=chatroomInfoTemp;
                    MultiThread.addChatRoomInfo(socketId,dataPacket.chatRoomInfo);
                    temp.type = DataPacket.transportType.CREATE_CHATROOM;
                    temp.chatRoomID = databaseManager.returnChatRoomId();
                    sendMsg(temp);
                    break;
                }
                case EXIT_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip=databaseManager.exitChatroom(dataPacket.id,dataPacket.chatRoomID);
                    temp.type= DataPacket.transportType.EXIT_CHATROOM;
                    MultiThread.delChatRoomInfo(dataPacket.id,dataPacket.chatRoomInfo);
                    sendMsg(temp);
                    break;
                }
                case JOIN_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatroomStatus=databaseManager.joinChatroom(dataPacket.id,dataPacket.chatRoomID);
                    temp.type = DataPacket.transportType.JOIN_CHATROOM;
                    MultiThread.addChatRoomInfo(dataPacket.id,dataPacket.chatRoomInfo);
                    sendMsg(temp);
                    break;
                }
                //修改名字
                case MODIFY_NAME:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip= databaseManager.modifyName(dataPacket.id, dataPacket.name);
                    temp.type = DataPacket.transportType.MODIFY_NAME;
                    sendMsg(temp);
                    break;
                }
                //修改密码
                case MODIFY_PASSWORD:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip = databaseManager.modifyPassword(dataPacket.id, dataPacket.password);
                    temp.type = DataPacket.transportType.MODIFY_PASSWORD;
                    sendMsg(temp);
                    break;
                }
                //增加好友
                case ADD_FRIEND:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip=databaseManager.addFriend(dataPacket.id, dataPacket.friendRequestID);
                    temp.type = DataPacket.transportType.ADD_FRIEND;
                    sendMsg(temp);
                    break;
                }
                //删除好友
                case DEL_FRIEND:
                {
                    DataPacket temp = new DataPacket();
                    databaseManager.deleteFriend(dataPacket.id,dataPacket.friendRequestID);
                    temp.type = DataPacket.transportType.DEL_FRIEND;
                    sendMsg(temp);
                    break;
                }
                //返回好友列表
                case RETURN_FRIEND_LIST:
                {
                    DataPacket temp = new DataPacket();
                    temp.friendList = databaseManager.getUserFriendList(dataPacket.id);
                    temp.type = DataPacket.transportType.RETURN_FRIEND_LIST;
                    sendMsg(temp);
                    break;
                }
                //返回ID所在所有群ID
                case RETURN_GROUP_LIST:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomList = databaseManager.getUserChatroomList(dataPacket.id);
                    temp.type = DataPacket.transportType.RETURN_GROUP_LIST;
                    sendMsg(temp);
                    break;
                }
                //返回群聊历史记录
                case GET_HISTORY_MESSAGE:
                {
                    DataPacket temp = new DataPacket();
                    temp.historyMessageList= databaseManager.getHistoryMessage(dataPacket.geyHistoryGroupID);
                    temp.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
                    sendMsg(temp);
                    break;
                }
                case FIND_FRIEND_INFO:
                {
                    DataPacket temp = new DataPacket();
                    temp.friendInfo = databaseManager.returnUser(dataPacket.id);
                    temp.type= DataPacket.transportType.FIND_FRIEND_INFO;
                    sendMsg(temp);
                    break;
                }
                case FIND_CHATROOM_INFO_THROUGH_ID:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomInfo = databaseManager.getChatroomInfo(dataPacket.chatRoomID);
                    temp.type= DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
                    sendMsg(temp);
                    break;
                }
                case FIND_CHATROOM_INFO_THROUGH_USER:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomInfo = databaseManager.getPrivateChatroomInfo(dataPacket.id,dataPacket.friendRequestID);
                    temp.type= DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
                    sendMsg(temp);
                    break;
                }
                default:
                    break;
            }
        }
        //退出服务器线程池
        MultiThread.exit(socketId);
        //关闭连接
        this.closeMe();
    }

    //输出消息类
    public void sendMsg(DataPacket msg) throws IOException {
        String temp = JSONObject.toJSONString(msg);
        ous.writeUTF(temp);
        ous.flush();
    }

    //关闭当前客户机与服务器的连接。
    public void closeMe() throws IOException {
        client.close();
    }

}
