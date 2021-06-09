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
     *
     * @param newName 新名字
     * @param UserID 用户ID
     */
    public void modifyName(String newName,String UserID);

    /**
     *
     * @param newPassword 新密码
     * @param UserID 用户ID
     */
    public void modifyPassword(String newPassword,String UserID);
    /**
     * 断开连接
     */
    public void exit();
}
