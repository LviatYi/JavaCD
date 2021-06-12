package Socket.Server;

import DataBase.Connect;
import Socket.tools.DataPacket;
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
    public Connect database = new Connect();
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
                    int id = database.Register(dataPacket.name, dataPacket.password);
                    DataPacket temp = new DataPacket();
                    if (id == 0) {
                        temp.registerStatus = DataPacket.MSGRegisterStatus.CONNECTION_FAILED;
                        temp.type = DataPacket.transportType.REGISTER;
                    } else {
                        temp.registerStatus = DataPacket.MSGRegisterStatus.SUCCESS;
                        temp.type = DataPacket.transportType.REGISTER;
                        temp.id = String.valueOf(id);
                    }
                    sendMsg(temp);
                    break;
                }
                //登录
                case LOGIN:
                {
                    Connect.LoginStatus loginStatus = database.LogIn(dataPacket.id, dataPacket.password);
                    switch (loginStatus)
                    {
                        case SUCCESS: {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = DataPacket.MSGLoginStatus.SUCCESS;
                            temp.type = DataPacket.transportType.LOGIN;
                            sendMsg(temp);
                            socketId = dataPacket.id;
                            //TODO
                            MultiThread.addClient(this);//认证成功，把这个用户加入服务器队列
                            break;
                        }
                        case ID_NOT_EXIST:
                        {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = DataPacket.MSGLoginStatus.ID_NOT_EXIST;
                            temp.type = DataPacket.transportType.LOGIN;
                            sendMsg(temp);
                            break;
                        }
                        case PASSWORD_ERROR:
                        {
                            DataPacket temp= new DataPacket();
                            temp.loginStatus = DataPacket.MSGLoginStatus.PASSWORD_ERROR;
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
                    database.SetMessage(dataPacket.senderId,dataPacket.message,dataPacket.chatRoomID, dataPacket.datetime);
                    MultiThread.castGroupMsg(dataPacket, dataPacket.chatRoomID);//群发给在线用户已经收到的群发消息
                    break;
                }
                case CREATE_CHATROOM:
                {
                    String ID;
                    DataPacket temp = new DataPacket();
                    ID=database.CreateChatRoom(false,temp.chatRoomName);
                    if(ID.equals("-1"))
                    {
                        temp.systemTip =0;
                    }
                    else
                    {
                        temp.chatRoomID = ID;
                        temp.systemTip = 1;
                    }
                    temp.type = DataPacket.transportType.CREATE_CHATROOM;
                    MultiThread.addChatRoom(dataPacket.id,temp.chatRoomID);//TODO
                    sendMsg(temp);
                    break;
                }
                case CREATE_PRIVATE_CHATROOM:
                {
                    String ID;
                    DataPacket temp = new DataPacket();
                    ID=temp.chatRoomID =database.CreateChatRoom(true,"");
                    if(ID.equals("-1"))
                    {
                        temp.systemTip =0;
                    }
                    else
                    {
                        temp.chatRoomID = ID;
                        temp.systemTip = 1;
                    }
                    temp.type = DataPacket.transportType.CREATE_PRIVATE_CHATROOM;
                    MultiThread.addChatRoom(dataPacket.id,temp.chatRoomID);//TODO
                    sendMsg(temp);
                    break;
                }
                case EXIT_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip=database.ExitChatRoom(dataPacket.id,dataPacket.chatRoomID);
                    temp.type= DataPacket.transportType.EXIT_CHATROOM;
                    sendMsg(temp);
                    //TODO 线程聊天池中删除此人
                }
                case JOIN_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip =  database.JoinChatRoom(dataPacket.id,dataPacket.chatRoomID);
                    temp.type = DataPacket.transportType.JOIN_CHATROOM;
                    sendMsg(temp);
                    break;
                }
                //修改名字
                case MODIFY_NAME:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip= database.ModifyName(dataPacket.id, dataPacket.name);
                    temp.type = DataPacket.transportType.MODIFY_NAME;
                    sendMsg(temp);
                    break;
                }
                //修改密码
                case MODIFY_PASSWORD:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip = database.ModifyPassword(dataPacket.id, dataPacket.password);
                    temp.type = DataPacket.transportType.MODIFY_PASSWORD;
                    sendMsg(temp);
                    break;
                }
                //增加好友
                case ADD_FRIEND:
                {
                    DataPacket temp = new DataPacket();
                    temp.systemTip=database.CreateFriend(dataPacket.id, dataPacket.friendRequestID);
                    temp.type = DataPacket.transportType.ADD_FRIEND;
                    sendMsg(temp);
                    break;
                }
                //删除好友
                case DEL_FRIEND:
                {
                    DataPacket temp = new DataPacket();
                    database.DeleteFriend(dataPacket.id,dataPacket.friendRequestID);
                    temp.type = DataPacket.transportType.DEL_FRIEND;
                    sendMsg(temp);
                    break;
                }
                //返回好友列表
                case RETURN_FRIEND_LIST:
                {
                    DataPacket temp = new DataPacket();
                    temp.friendList = database.getUserFriendList(dataPacket.friendInfo);
                    temp.type = DataPacket.transportType.RETURN_FRIEND_LIST;
                    sendMsg(temp);
                    break;
                }
                //返回ID所在所有群ID
                case RETURN_GROUP_LIST:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomList = database.getGroup(dataPacket.id);
                    temp.type = DataPacket.transportType.RETURN_GROUP_LIST;
                    sendMsg(temp);
                    break;
                }
                //返回群聊历史记录
                case GET_HISTORY_MESSAGE:
                {
                    DataPacket temp = new DataPacket();
                    temp.historyMessageList= database.GetGroupMessage(dataPacket.chatRoomID);
                    temp.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
                    sendMsg(temp);
                    break;
                }
                case FIND_CHATROOM_INFO_THROUGH_ID:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomInfo = database.findChatRoomInfoThroughID(dataPacket.chatRoomID);
                    temp.type= DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
                    sendMsg(temp);
                }
                case FIND_CHATROOM_INFO_THROUGH_USER:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomInfo = database.findChatRoomInfoThroughUser(dataPacket.id,dataPacket.friendRequestID);
                    temp.type= DataPacket.transportType.FIND_CHATROOM_INFO_THROUGH_ID;
                    sendMsg(temp);
                }
                default:
                    break;
            }
        }

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
