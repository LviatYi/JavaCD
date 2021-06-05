import java.sql.Connection;
import java.sql.SQLException;

public interface Database {


    /**
     * 连接数据库Book（默认)以下数据库调用均为Book
     */
     Connection getConnection();


     /**
      在数据库中建立四张表：
      sign(id varchar(10), password varchar(20),name varchar(10))");
      friend(id varchar(10), id_friend varchar(10),name_friend varchar(50)
      new(sender varchar(50), message text,datatime text)
      new3(sender varchar(50), reciver varchar(50),message text,datatime text)
      */
      void establish();

     /**
      * 登录  查询name和passwprd是否与数据库匹配
      * @param id 用户账号
      * @param password 用户密码
      */
      void signin(String id,String password) ;

    /**
     * 注册  添加数据至数据库
     * @param id 用户账号
     * @param password 用户密码
     * @param name 用户昵称
     */
     void register(String id,String password,String name);

    /**
     * 修改数据库中的密码
     * @param passwordoriginal 修改前的密码
     * @param passwordnow 修改后的密码
     */
     void modify_password(String passwordoriginal,String passwordnow);

    /**
     * 修改昵称
     * @param nameoriginal 修改前的昵称
     * @param namenow 修改后的昵称
     */
     void modify_name(String nameoriginal,String namenow);

    /**
     * 查询群聊表（new）发送者发送的信息情况
     * @param sender 发送者
     */
     void getnews(String sender);

    /**
     * 将群聊的消息添加至数据库
     * @param sender 发送者
     * @param message 消息内容
     * @param datatime 发送时间
     */
     void insertnews(String sender,String message,String datatime);

    /**
     * 查询私聊表（new3）发送者发送的信息情况
     * @param sender 发送者
     */
     void getnews1sender(String sender) ;

    /**
     * 查询私聊表（new3）接收者收到的信息情况
     * @param reciver 接收者
     */
     void getnews1reciver(String reciver);

    /**
     * 将私聊的消息添加至数据库
     * @param sender 发送者
     * @param reciver 接收者
     * @param message 消息内容
     * @param datatime 发送时间
     */
     void insertnews1(String sender,String reciver,String message,String datatime);

    /**
     * 添加好友信息至好友表（friend）
     * @param id 自己账号
     * @param id_friend 好友账号
     * @param name_friend 好友昵称
     */
     void  addfriend(String id,String id_friend,String name_friend);

    /**
     * 输入好友账号将信息从数据库中删除
     * @param id 好友账号
     */
     void deletefriend(String id);


}
