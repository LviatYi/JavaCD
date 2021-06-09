package DataBase;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import Socket.tools.*;

public class Connect implements Database {
    private Connection con;
    private Statement sta;
    private ResultSet rs;
    public static String[] list;

    public enum LoginStatus {
        /**
         * 登录成功
         */
        SUCCESS,
        /**
         * 网络未连接
         */
        CONNECTION_FAILED,
        /**
         * 账号不存在
         */
        ID_NOT_EXIST,
        /**
         * 密码错误
         */
        PASSWORD_ERROR
    }

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载驱动程序
     */
    public Connection getConnection() {
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=test";
        try {
            con = DriverManager.getConnection(url, "sa", "111111");
            sta = con.createStatement();
            System.out.println("连接成功");
        } catch (SQLException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }
        return con;
    }

    String sui() {
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10)
            num = (int) Math.floor(Math.random() * 11);
        //System.out.println(num);
        int a = (int) Math.pow(10, (num - 1));
        int random6 = (int) ((Math.random() * 9 + 1) * a);
        String a1 = String.valueOf(random6);
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select id from userInfo where id='" + a1 + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sui();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return a1;
    }

    @Override
    public LoginStatus LogIn(String id, String password)   //登录  查询id和password是否与数据库匹配
    {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM userInfo where id='" + id + "'";//and password ='" + password + "'"\;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
                //System.out.println(password);
                String m1 = rs.getString(2);
                if (password.equals(m1)) return LoginStatus.SUCCESS;
                else {
                    return LoginStatus.PASSWORD_ERROR;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LoginStatus.ID_NOT_EXIST;
    }

    @Override
    public int Register(String password, String name)//注册   添加id，密码，账号
    {
        con = getConnection();
        try {
            String sql = "insert into userInfo (id,password,name,;leftTime) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            String a = sui();
            String b = Tool.getTime();
            st.setString(1, a);
            st.setString(2, password);
            st.setString(3, name);
            st.setString(4, b);
            int rs = st.executeUpdate();
            int c = Integer.parseInt(a);
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    @Override
    public boolean ModifyPassword(String id, String password_now)//修改密码
    {
        con = getConnection();
        try {
            String sql = "update userInfo set password=? where id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, password_now);
            st.setString(2, id);
            int rs = st.executeUpdate();
            // System.out.println("修改password成功");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean ModifyName(String id, String name_now)//修改昵称
    {
        con = getConnection();
        try {
            String sql = "update userInfo set name=? where id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name_now);
            st.setString(2, id);

            int rs = st.executeUpdate();
            return true;
            //System.out.println("修改name成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO
    //增加消息至消息表
    //重新构建消息表，其中属性有(发送者，发送内容，发送聊天室ID，发送时间)
    @Override
    public boolean SetMessage(String sender, String message, String groupId, Date datetime) {
        return false;
    }

    //TODO
    //删除好友
    //在发起人的好友表中删除旧好友并且在旧好友的好友表中删除发起人
    //好友表为索引
    @Override
    public boolean DeleteFriend(String s, String id)//删除好友
    {
        return true;
    }

    //TODO
    //返回传入ID的好友列表
    //好友列表使用索引在数据库创建
    @Override
    public String[] getFriend(String id) {
        return null;
    }

    //TODO
    //返回传入聊天室的组员
    @Override
    public String[] getGroup(String groupId) {
        return null;
    }

    //TODO
    //返回传入聊天室的历史记录
    @Override
    public List<DataPacket> GetGroupMessage(String groupID) {
        return null;
    }

    //TODO
    //增加聊天室
    //返回聊天室ID
    //ID为随机并存入数据库聊天室表
    //聊天室表为索引，存储聊天室组员ID和属性聊天室ID
    @Override
    public String CreateChatRoom(boolean isPrivate) {
        return null;
    }

    //TODO
    //增加好友
    //在发起人的好友表中添加新增好友并且在新增好友的好友表中增加发起人
    //好友表为索引
    @Override
    public boolean CreateFriend(String id, String id_friend) {
        return false;
    }

    //TODO
    //添加ID所有者到指定聊天室
    @Override
    public boolean AddChatRoom(String id, String chatroomID) {
        return false;
    }

    //TODO
    //从指定聊天室删除ID所有者
    @Override
    public boolean DelChatRoom(String id, String chatroomID) {
        return false;
    }
}





