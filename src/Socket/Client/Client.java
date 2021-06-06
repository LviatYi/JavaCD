package Socket.Client;

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
     * @param senderID 发送者id
     */
    public void sendGroup(String text,String senderID);

    /**
     *
     * @param text 消息
     * @param receiverID 接受者id
     * @param senderID 发送者id
     */
    public void sendPrivate(String text,String receiverID,String senderID);

    /**
     *
     * @param name 昵称
     * @param password 登录密码
     */
    public void register(String name,String password);

    /**
     *
     * @param id 用户名ID
     * @param password 登录密码
     */
    public void login(String id,String password);

    /**
     * 断开连接
     */
    public void exit();
}
