package Socket.Server;

import DataBase.Connect;
import Encrypt.EncryptionImpl;
import Socket.tools.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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

    public void run() {
        try {
            processSocket();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    private void processSocket() throws IOException {
        // TODO Auto-generated method stub

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
                    Connect.RegisterStatus registerStatus = database.Register(message.name,message.password);
                    switch (registerStatus)
                    {
                        case SUCCESS:
                        {
                            Message temp= new Message();
                            temp.registerStatus= Message.MSGRegisterStatus.SUCCESS;
                            temp.type = Message.transportType.REGISTER;
                            //TODO 获取服务器分配ID
//                            temp.id = getidfromdatabase
                            sendMsg(temp);
                            break;
                        }
                        case CONNECTION_FAILED:
                        {
                            Message temp= new Message();
                            temp.registerStatus= Message.MSGRegisterStatus.CONNECTION_FAILED;
                            temp.type = Message.transportType.REGISTER;
                            sendMsg(temp);
                            break;
                        }
                    }
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
                    //TODO 删除type
                    database.SetMessage(message.senderId,null,message.message,"群发");
                    MultiThread.castGroupMsg(message);//群发给在线用户已经收到的群发消息
                    break;
                }
                //私聊消息
                case SEND_PRIVATE_MESSAGE:
                {
                    database.SetMessage(message.senderId,message.receiverId,message.message,"私聊");
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
