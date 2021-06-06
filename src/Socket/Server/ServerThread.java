package Socket.Server;

import Socket.tools.Message;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 每当有客户机和服务器连接时，都要定义一个接受对象来进行数据的传输
 * 从服务器的角度看，这个类就是客户端
 */
public class ServerThread extends Thread{

    private final Socket client;//线程中的处理对象
    private OutputStream ous;//输出流对象
    private Message message;
    private String input;
    public ServerThread(Socket client) {
        this.client=client;
    }

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
        ous=client.getOutputStream();
        BufferedReader brd=new BufferedReader(new InputStreamReader(ins));

        while(JSON.parseObject(input = brd.readLine(),Message.class).type!= Message.transportType.EXIT)
        {
            message=JSON.parseObject(input = brd.readLine(),Message.class);
            switch (message.type)
            {
                //注册
                case REGISTER:
                {
                }
                //登录
                case LOGIN:
                {
                }
                //群发消息
                case SEND_GROUP_MESSAGE:
                {
                }
                //私聊消息
                case SEND_PRIVATE_MESSAGE:
                {
                }
            }
        }

        //调用数据库，验证用户是否存在
        //database();
//        if(!loginState) {
//            //如果不存在这个账号则关闭
//            this.closeMe();
//            return;
//        }
        MultiThread.addClient(this);//认证成功，把这个用户加入服务器队列
        input=brd.readLine();
        //关闭连接
        this.closeMe();
    }


    //输出消息类
    public void sendMsg(Message msg) throws IOException {
        String temp = JSONObject.toJSONString(msg);
        ous.write(temp.getBytes());
        ous.flush();
    }
    //关闭当前客户机与服务器的连接。
    public void closeMe() throws IOException {
        client.close();
    }


}
