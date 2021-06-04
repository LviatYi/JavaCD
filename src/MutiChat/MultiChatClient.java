package MutiChat;

import java.io.*;
import java.net.Socket;


public class MultiChatClient implements Client{

    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;

    public void run() throws IOException {
        client();
    }

    /**
     *
     * @param text 传输的消息
     * @param type 类型1为群发
     */
    public void sendGroup(String text,int type)
    {
        type=1;
        co.setMessage(String.valueOf(type));
    }

    /**
     *
     * @param text 传输的消息
     * @param ID 私聊对象ID
     * @param type 类型2为私聊
     */
    public void sendTOID(String text,String ID,int type)
    {
        co.setMessage(String.valueOf(type));
    }

    /**
     *
     * @param name 登录名
     * @param password 登录密码
     * @param type 类型3为登录
     */
    public void send(String name,String password,int type)
    {
        co.setMessage(String.valueOf(type));
    }

    private void client() throws IOException {
            Socket socket = new Socket("127.0.0.1", 9000);

            co.setSocket(socket);
            ci.setSocket(socket);
            co.start();
            ci.start();
    }

}
