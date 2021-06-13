package DataBase;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Socket.tools.*;

public class Connect implements Database {

    private Connection con;
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
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=Datebase1";
        try {
            con = DriverManager.getConnection(url, "sa", "111111");
            Statement sta = con.createStatement();
            System.out.println("连接成功");
        } catch (SQLException e) {
            System.out.println("连接失败");
            e.printStackTrace();
        }
        return con;
    }

    int sui() {
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10)
        {
            num = (int) Math.floor(Math.random() * 11);
        }
        //System.out.println(num);
        int a = (int) Math.pow(10, (num - 1));
        int random6 = (int) ((Math.random() * 9 + 1) * a);
        // String a1 = String.valueOf(random6);
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select Id from User11 where Id='" + random6 + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sui();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return random6;
    }

    //TODO 改string
    int random() {
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10) {
            num = (int) Math.floor(Math.random() * 11);
        }
        //System.out.println(num);
        int a = (int) Math.pow(10, (num - 1));
        int random6 = (int) ((Math.random() * 9 + 1) * a);
        // String a1 = String.valueOf(random6);
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select ChatRoomId from ChatRoom where ChatRoomId='" + random6 + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sui();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return random6;
    }

    @Override
    public LoginStatus LogIn(String id, String password)   //登录  查询id和password是否与数据库匹配
    {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT Password FROM User11 where Id='" + id + "'";//and password ='" + password + "'"\;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                //System.out.println(rs.getString(2));
                //System.out.println(password);
                String m1 = rs.getString(1);
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
            String sql = "insert into User11(Id,Name,Password) values(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int a = sui();
            // String b = Tool.getTime();
            st.setInt(1, a);
            st.setString(2, name);
            st.setString(3, password);
            int rs = st.executeUpdate();
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    @Override
    public int ModifyPassword(String id, String password_now)//修改密码
    {
        con = getConnection();
        try {
            String sql = "update User11 set Password=? where Id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, password_now);
            st.setString(2, id);
            int rs = st.executeUpdate();
            // System.out.println("修改password成功");
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int ModifyName(String id, String name_now)//修改昵称
    {
        con = getConnection();
        try {
            String sql = "update User11 set Name=? where Id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name_now);
            st.setString(2, id);
            int rs = st.executeUpdate();
            return 1;
            //System.out.println("修改name成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //CLEAR
    @Override
    public boolean SetMessage(String sender, String message, String chatRoomID, Date datetime) {
        con = getConnection();
        try {
            String sql = "insert into Message(SenderId,ChatRoomId,Message,Time) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, sender);
            st.setString(2, chatRoomID);
            st.setString(3, message);
            st.setDate(4, (java.sql.Date) datetime);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //CLEAR
    @Override
    public MessageList GetGroupMessage(String chatRoomID) {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select * from Message where ChatRoomId='" + chatRoomID + "'";
            ResultSet rs = st.executeQuery(sql);
            Vector<Message> vector =new Vector<Message>();
            while (rs.next()) {
                String senderIDTemp= rs.getString(1);
                String chatRoomIDTemp= rs.getString(2);
                String messageTemp= rs.getString(3);
                Date sendTimeTemp= rs.getDate(4);
                Message message = new Message(messageTemp,senderIDTemp,chatRoomID,sendTimeTemp);
                vector.add(message);
            }
            return new MessageList(chatRoomID,vector);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //CLEAR
    //TODO 修改表中内容
    @Override
    public String CreateChatRoom(ChatroomInfo.ChatroomType chatroomType,String chatRoomName) {
        con = getConnection();
        try {
            String sql = "insert into ChatRoom(ChatRoomId,Property,ChatRoomName) values(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int IDTemp = random();
            st.setInt(1, IDTemp);
            if(chatroomType == ChatroomInfo.ChatroomType.PUBLIC)
            {
                st.setInt(2,0);
            }
            else if(chatroomType == ChatroomInfo.ChatroomType.PRIVATE)
            {
                st.setInt(2,1);
            }
            st.setString(3,chatRoomName);
            st.executeUpdate();
            return String.valueOf(IDTemp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    //TODO 删除聊天室，成功返回1失败返回0
    @Override
    public int  DelChatRoom(String chatroomID) {
        return 0;
    }

    //TODO 指定id人退出指定id聊天室,成功返回1，失败返回0
    @Override
    public int ExitChatRoom(String id, String chatRoomID) {
        return 0;
    }

    //TODO 更改返回值，先查看是否有好友再添加   -1已经有好友，0添加失败，1添加成功。
    @Override
    public int CreateFriend(String id, String id_friend) {
        con = getConnection();
        try {
            int id1 = Integer.parseInt(id);
            int id2 = Integer.parseInt(id_friend);
            String sql = "insert into UserRelationship(Id1,Id2)\n" +
                    "values(?,?),(?,?); ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id1);
            st.setInt(2, id2);
            st.setInt(3, id2);
            st.setInt(4, id1);
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //TODO 添加用户（ID）到聊天室(ID),成功返回1，失败返回0
    @Override
    public int JoinChatRoom(String id, String chatroomID) {
        return 0;
    }

    @Override
    public int DeleteFriend(String s, String id)//删除好友
    {
        con = getConnection();
        try {
            String sql = "delete from UserRelationship where( Id1=? and Id2=?) or (Id1=? and Id2=?)";
            PreparedStatement st = con.prepareStatement(sql);
            int id1 = Integer.parseInt(s);
            int id2 = Integer.parseInt(id);
            st.setInt(1, id1);
            st.setInt(2, id2);
            st.setInt(3, id2);
            st.setInt(4, id1);
            st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //TODO
    //修改返回类型
    @Override
    public ChatroomList getGroup(String groupId) {
        return null;
    }

    @Override
    public ChatroomList findChatRoomInfoThroughID(String id) {
        return null;
    }

    @Override
    public ChatroomInfo findChatRoomInfoThroughUser(String id, String friendID) {
        return null;
    }

    @Override
    public FriendList getUserFriendList(FriendInfo userInfo) {
        return null;
    }

    // public static void main(String[] args) {
    // Connect db=new Connect();
    // System.out.println(db.sui());//输出id
    //db.Register("nuist","南信大");//注册
    //db.ModifyPassword("707890", "nuist11");//修改密码
    //db.ModifyName("707890", "nuist11");//修改Name
    //db.SetMessage("707890", "你好", "2686631", "2021-1-1");
    // db.CreateFriend("123", "7859");//加好友
    //db.DeleteFriend("707890", "123456");//删除好友
    //String a[]=new String[20]; a=db.getFriend("123");
    //for(int i=0;i<3;i++) System.out.println(a[i]);
    //String a=db.AddGroup(false);//存入聊天室id
    //System.out.println(a);

}





