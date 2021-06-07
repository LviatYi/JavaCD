package DataBase;

import Socket.tools.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Database {


     /**
      * 登录  查询name和password是否与数据库匹配
      * @param id 用户账号
      * @param password 用户密码
      * @return SUCCESS  账号存在&密码正确
      *         PASSWORD_ERROR  账号存在&密码错误
      *         ID_NOT_EXIST   账号不存在
      */
     //public DataBase.Connect.LoginStatus Login(String id, String password);


    /**
     * 注册  添加数据至数据库
     *
     * @param password 用户密码
     * @param name 用户昵称
     * @return SUCCESS 录入信息
     */
    //public DataBase.Connect.RegisterStatus Register( String password, String name);

   /**
     //* 修改数据库中的密码
     * @param password_original 修改前的密码
     * @param password_now 修改后的密码
     */
     void ModifyPassword(String password_original,String password_now);

    /**
     * 修改昵称
     * @param name_original 修改前的昵称
     * @param name_now 修改后的昵称
     */
     void ModifyName(String name_original,String name_now);

 /**
  * 添加消息至数据库
  * @param sender 发送者
  * @param receiver 接收者
  * @param message 内容
  * @param type 消息种类
  * 时间自动给出
  *
  */
      void SetMessage(String sender,String receiver,String message,String type);

    /**
     * 将对应用户的离开时间改为当前系统时间
     * @param userId 用户id
     */
     void UpdateLeftTime(String userId);

    /**
     * 选择dateTime时间以后的信息
     * @param id
     * @return 消息集
     */
     List<Message> GetMessage(String id);

    /**
     * 添加好友信息至好友表（friend）
     * @param id 自己账号
     * @param id_friend 好友账号
     * @param name_friend 好友昵称
     */
     void  CreateFriend(String id,String id_friend,String name_friend);

    /**
     * 输入好友账号将信息从数据库中删除
     * @param id 好友账号
     */
     void DeleteFriend(String id);


}
