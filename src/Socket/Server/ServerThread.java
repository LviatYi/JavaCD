package Socket.Server;

import DataBase.Connect;
import Encrypt.EncryptionImpl;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.List;

/*
 * 每当有客户机和服务器连接时，都要定义一个接受对象来进行数据的传输
 * 从服务器的角度看，这个类就是客户端
 */
public class ServerThread extends Thread{

    private final Socket client;//线程中的处理对象
    private DataOutputStream ous ;
    public ServerThread(Socket client) {
        this.client=client;
    }
    public Connect database = new Connect();
    public EncryptionImpl encryption= new EncryptionImpl();
    public String SocketID;

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
                    int ID = database.Register(dataPacket.name, dataPacket.password);
                    DataPacket temp = new DataPacket();
                    if (ID == 0) {
                        temp.registerStatus = DataPacket.MSGRegisterStatus.CONNECTION_FAILED;
                        temp.type = DataPacket.transportType.REGISTER;
                    } else {
                        temp.registerStatus = DataPacket.MSGRegisterStatus.SUCCESS;
                        temp.type = DataPacket.transportType.REGISTER;
                        temp.id = String.valueOf(ID);
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
                            SocketID= dataPacket.id;
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
                    }
                    break;
                }
                //保存数据并向聊天室转发
                case SEND_MESSAGE:
                {
                    database.SetMessage(dataPacket.senderId,encryption.decryptContent(dataPacket.message),dataPacket.chatRoomID, dataPacket.datetime);
                    MultiThread.castGroupMsg(dataPacket, dataPacket.chatRoomID);//群发给在线用户已经收到的群发消息
                    break;
                }
                case CREATE_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomID =database.CreateChatRoom(false);
                    temp.type = DataPacket.transportType.CREATE_CHATROOM;
                    MultiThread.addGroup(dataPacket.id,temp.chatRoomID);
                    sendMsg(temp);
                    break;
                }
                case CREATE_PRIVATE_CHATROOM:
                {
                    DataPacket temp = new DataPacket();
                    temp.chatRoomID =database.CreateChatRoom(true);
                    temp.type = DataPacket.transportType.CREATE_PRIVATE_CHATROOM;
                    MultiThread.addGroup(dataPacket.id,temp.chatRoomID);
                    sendMsg(temp);
                    break;
                }
                case ADD_CHATROOM:
                {
                    database.AddChatRoom(dataPacket.id,dataPacket.chatRoomID);
                    //TODO
                    break;
                }
                case DEL_CHATROOM:
                {
                    database.DelChatRoom(dataPacket.id,dataPacket.chatRoomID);
                    //TODO
                    break;
                }
                //修改名字
                case MODIFY_NAME:
                {
                    database.ModifyName(dataPacket.id, dataPacket.name);
                    break;
                }
                //修改密码
                case MODIFY_PASSWORD:
                {
                    database.ModifyPassword(dataPacket.id, dataPacket.password);
                    break;
                }
                //增加好友
                case ADD_FRIEND:
                {
                    database.CreateFriend(dataPacket.id, dataPacket.friendRequestID);
                    break;
                    //TODO
                }
                //删除好友
                case DEL_FRIEND:
                {
                    database.DeleteFriend(dataPacket.id,dataPacket.friendRequestID);
                    break;
                }
                //返回好友列表
                case RETURN_FRIEND_LIST:
                {
                    String[] friend1 = database.getFriend(dataPacket.id);
                    for (String friend:friend1)
                    {
                        DataPacket temp = new DataPacket();
                        temp.friendRequestID =friend;
                        temp.type = DataPacket.transportType.RETURN_FRIEND_LIST;
                        sendMsg(temp);
                    }
                    break;
                }
                //返回群聊成员列表
                case RETURN_GROUP_LIST:
                {
                    String[] group1 = database.getGroup(dataPacket.chatRoomID);
                    for (String group:group1)
                    {
                        DataPacket temp = new DataPacket();
                        temp.friendRequestID =group;
                        temp.type = DataPacket.transportType.RETURN_GROUP_LIST;
                        sendMsg(temp);
                    }
                    break;
                }
                //返回群聊历史记录
                case GET_HISTORY_MESSAGE:
                {
                    List<DataPacket> temp = database.GetGroupMessage(dataPacket.chatRoomID);
                    for(DataPacket dataPacket1:temp)
                    {
                        dataPacket1.type = DataPacket.transportType.GET_HISTORY_MESSAGE;
                        sendMsg(dataPacket1);
                    }
                    break;
                }
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
