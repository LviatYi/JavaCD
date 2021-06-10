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
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=Datebase1";
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

    int sui() {
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10)
            num = (int) Math.floor(Math.random() * 11);
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
    int sui1() {
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10)
            num = (int) Math.floor(Math.random() * 11);
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
    public boolean ModifyPassword(String id, String password_now)//修改密码
    {
        con = getConnection();
        try {
            String sql = "update User11 set Password=? where Id=?";
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
            String sql = "update User11 set Name=? where Id=?";
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
        con = getConnection();
        try {
            String sql = "insert into Message(SenderId,ChatRoomId,Message,Time) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int senderid=Integer.parseInt(sender);
            int groupid=Integer.parseInt(groupId);
            st.setInt(1, senderid);
            st.setInt(2,groupid );
            st.setString(3,message);
            st.setDate(4, (java.sql.Date) datetime);
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO
    //删除好友
    //在发起人的好友表中删除旧好友并且在旧好友的好友表中删除发起人
    //好友表为索引
    @Override
    public boolean DeleteFriend(String s, String id)//删除好友
    { con = getConnection();
        try {
            String sql = "delete from UserRelationship where( Id1=? and Id2=?) or (Id1=? and Id2=?)";
            PreparedStatement st = con.prepareStatement(sql);
            int id1=Integer.parseInt(s);
            int id2=Integer.parseInt(id);
            st.setInt(1, id1);
            st.setInt(2,id2 );
            st.setInt(3, id2);
            st.setInt(4,id1 );
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO
    //返回传入ID的好友列表
    //好友列表使用索引在数据库创建
    @Override
    public String[] getFriend(String id) {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select Id2 from UserRelationship where Id1='" + id + "'";;
           ResultSet rs=st.executeQuery(sql);
            //int id1=Integer.parseInt(id);
            //st.setInt(1, id1);
           int i=0;
           String id_friend[] = new String[20];
            while(rs.next())
            {
             id_friend[i++]= rs.getString(1);
            }
            return id_friend;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO
    //返回传入聊天室的组员
    @Override
    public String[] getGroup(String groupId)
    {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select distinct SenderId from Message where ChatRoomId='" + groupId + "'";
            ResultSet rs=st.executeQuery(sql);
            //int id1=Integer.parseInt(id);
            //st.setInt(1, id1);
            int i=0;
            String id_room[] = new String[20];
            while(rs.next())
            {
                id_room[i++]= rs.getString(1);
            }
            return id_room;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO
    //返回传入聊天室的历史记录
    @Override
    public List<DataPacket> GetGroupMessage(String groupID) {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select * from Message where ChatRoomId='" + groupID + "'";
            ResultSet rs = st.executeQuery(sql);
            List<DataPacket> aa=new ArrayList<DataPacket>();
            while(rs.next())
            {
                String senderId= rs.getString(1);
                String groupId=rs.getString(2);
                String message=rs.getString(3);
                Date datetime=rs.getDate(4);
            aa.add();

            }
        }catch (SQLException e){
            e.printStackTrace();}

        return null;
    }

    //TODO
    //增加聊天室
    //返回聊天室ID
    //ID为随机并存入数据库聊天室表
    //聊天室表为索引，存储聊天室组员ID和属性聊天室ID
    @Override
    public String AddGroup(boolean isPrivate) {
        con = getConnection();
        try {
            String sql = "insert into ChatRoom(ChatRoomId,Property) values(?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int a = sui1();
            String property  ;
            if(isPrivate){property="true";}
            else {property="false";}
            st.setInt(1, a);
            st.setString(2, property);
            int rs = st.executeUpdate();
            String aa=String.valueOf(a);
            return aa;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //TODO
    //增加好友
    //在发起人的好友表中添加新增好友并且在新增好友的好友表中增加发起人
    //好友表为索引
    @Override
    public boolean CreateFriend(String id, String id_friend) {
        con = getConnection();
        try {
            int id1=Integer.parseInt(id);
            int id2=Integer.parseInt(id_friend);
            String sql = "insert into UserRelationship(Id1,Id2)\n" +
                    "values(?,?),(?,?); ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id1);
            st.setInt(2,id2 );
            st.setInt(3, id2);
            st.setInt(4,id1 );
            int rs = st.executeUpdate();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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





