package DataBase;

import Socket.tools.DataPacket;

import java.util.Date;
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
     * @return  录入信息并返回一个6-10的随机数字id（不重复）
     */
    int  Register( String password, String name);

   /**
     //* 修改数据库中的密码
     * @param id 用户id
     * @param password_now 修改后的密码
    * @return  true/false
     */
     boolean ModifyPassword(String id,String password_now);

    /**
     * 修改昵称
     * @param id 用户id
     * @param name_now 修改后的昵称
     *                 @return  true/false
     */
     boolean ModifyName(String id,String name_now);

 /**
  * 添加消息至数据库
  * @param groupId 账号
  * @param sender 发送者
  * @param message 内容
  * @param datetime 客户端发送时间
  * 时间自动给出
  *@return  true/false
  */
    boolean SetMessage(String sender, String message, String groupId, Date datetime);

    /**
     * 将对应用户的离开时间改为当前系统时间
     * @param userId 用户id
     *               @return  true/false
     */
     boolean UpdateLeftTime(String userId);

    /**
     * 选择dateTime时间以后的信息
     * @param id
     * @return 消息集
     */
     List<DataPacket> GetMessage(String id);

    /**
     * 添加好友信息至好友表（friend）
     * @param id 自己账号
     * @param id_friend 好友账号
     *@return  true/false
     */
     boolean  CreateFriend(String id,String id_friend);

    /**
     * 输入好友账号将信息从数据库中删除
     * @param id 自己ID
     * @param id_friend 好友ID
     *           @return  true/false
     */
     boolean DeleteFriend(String id,String id_friend);

    /**
     * 返回Friend 数组
     * @return String 数组
     */
     String[] getFriend();
}
