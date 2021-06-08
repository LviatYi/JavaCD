package Socket.Server;

import DataBase.Connect;
import Encrypt.EncryptionImpl;
import Socket.tools.Message;
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

        while(JSON.parseObject(brd.readLine(),Message.class).type!= Message.transportType.EXIT)
        {
            Message message = JSON.parseObject(brd.readLine(), Message.class);
            switch (message.type)
            {
                //注册
                case REGISTER:
                {
                    int ID = database.Register(message.name,message.password);
                    Message temp = new Message();
                    if (ID == 0) {
                        temp.registerStatus = Message.MSGRegisterStatus.CONNECTION_FAILED;
                        temp.type = Message.transportType.REGISTER;
                    } else {
                        temp.registerStatus = Message.MSGRegisterStatus.SUCCESS;
                        temp.type = Message.transportType.REGISTER;
                        temp.id = String.valueOf(ID);
                    }
                    sendMsg(temp);
                    break;
                }
                //登录
                case LOGIN:
                {
                    Connect.LoginStatus loginStatus = database.LogIn(message.id,message.password);
                    switch (loginStatus)
                    {
                        case SUCCESS: {
                            Message temp= new Message();
                            temp.loginStatus = Message.MSGLoginStatus.SUCCESS;
                            temp.type = Message.transportType.LOGIN;
                            sendMsg(temp);
                            SocketID= message.id;
                            MultiThread.addClient(this);//认证成功，把这个用户加入服务器队列
                            List<Message> oldMsg = database.GetMessage(SocketID);
                            for (Message old:oldMsg) {
                                sendMsg(old);
                            }
                            break;
                        }
                        case ID_NOT_EXIST:
                        {
                            Message temp= new Message();
                            temp.loginStatus = Message.MSGLoginStatus.ID_NOT_EXIST;
                            temp.type = Message.transportType.LOGIN;
                            sendMsg(temp);
                            break;
                        }
                        case PASSWORD_ERROR:
                        {
                            Message temp= new Message();
                            temp.loginStatus = Message.MSGLoginStatus.PASSWORD_ERROR;
                            temp.type = Message.transportType.LOGIN;
                            sendMsg(temp);
                            break;
                        }
                    }
                    break;
                }
                //群发消息
                case SEND_GROUP_MESSAGE:
                {
                    database.SetMessage(message.senderId,null,encryption.decryptContent(message.message),message.groupID);
                    MultiThread.castGroupMsg(message,message.groupID);//群发给在线用户已经收到的群发消息
                    break;
                }
                //私聊消息
                case SEND_PRIVATE_MESSAGE:
                {
                    database.SetMessage(message.senderId,message.receiverId,encryption.decryptContent(message.message),null);
                    MultiThread.castPrivateMSG(message);
                    break;
                }
                //修改名字
                case MODIFY_NAME:
                {
                    database.ModifyName(message.name,message.id);
                    //TODO 等待数据库修改
                    break;
                }
                //修改密码
                case MODIFY_PASSWORD:
                {
                    database.ModifyPassword(message.password,message.id);
                    break;
                }
            }
        }
        //关闭连接
        database.UpdateLeftTime(SocketID);
        this.closeMe();
    }


    //输出消息类
    public void sendMsg(Message msg) throws IOException {
        String temp = JSONObject.toJSONString(msg);
        ous.writeUTF(temp);
        ous.flush();
    }
    //关闭当前客户机与服务器的连接。
    public void closeMe() throws IOException {
        client.close();
    }
}
