package MutiChat;

import java.io.IOException;

/**
 * @author topkang
 * @date 2021/6/3 23:18
 * @version 1.0
 */
public interface Client {

    /**
     * 启动客户端线程组
     * @throws IOException 对哩
     */
    public void run() throws IOException;

    /**
     *
     * @param text 传输的消息
     * @param type 类型1为群发
     */
    public void sendGroup(String text,int type);

    /**
     *
     * @param text 传输的消息
     * @param ID 私聊对象ID
     * @param type 类型2为私聊
     */
    public void sendTOID(String text,String ID,int type);

    /**
     *
     * @param name 登录名
     * @param password 登录密码
     * @param type 类型3为登录
     */
    public void send(String name,String password,int type);

}
