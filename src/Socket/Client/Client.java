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
     * @param groupID 聊天群号
     */
    public void sendGroup(String text,String senderID,String groupID);

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
     * 拆分获得的消息包到客户端类
     * 解包调用message时记得调用解密！！！！！！！！！！
     */
    public void splitList();

    /**
     * 断开连接
     */
    public void exit();
}
