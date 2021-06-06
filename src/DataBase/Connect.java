package DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Socket.tools.*;

/*****************************/
public class Connect<date> implements Database
        {
    private Connection con;
    private Statement sta;
    private ResultSet rs;
    public static String[] list;
    public enum LoginStatus
    {
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
   public enum RegisterStatus
            {
                /**
                 * 注册成功
                 */
                SUCCESS,
                /**
                 * 网络未连接
                 */
                CONNECTION_FAILED
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
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=JavaCD";
        try {
            con = DriverManager.getConnection(url, "SA", "JavaCD2021");
            sta = con.createStatement();
            //System.out.println("连接成功");
        } catch (SQLException e) {
           // System.out.println("连接失败");
            e.printStackTrace();
        }
        return con;
    }


   /*public void Establish() throws SQLException//建立数据库的表
   {con=getConnection();//连接之前的数据库
       Statement stat = con.createStatement();
       //创建表test
       stat.executeUpdate("create table sign(id varchar(10), password varchar(20),name varchar(10))");
       stat.executeUpdate("create table friend(id varchar(10), id_friend varchar(10),name_friend varchar(50))");
       stat.executeUpdate("create table new(sender varchar(50), message text,datetime text)");
       stat.executeUpdate("create table new3(sender varchar(50), receiver varchar(50),message text,datetime text)");
       //System.out.println("建立表成功");

   }*/


    public LoginStatus LogIn(String id,String password)   //登录  查询name和password是否与数据库匹配
    {       con=getConnection();
        try{Statement st = con.createStatement();
            String sql = "SELECT * FROM userInfo where id='" + id + "'";//and password ='" + password + "'"\;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(password);
                String m1=rs.getString(2);
                if(password.equals(m1)) return LoginStatus.SUCCESS;
               else{ return LoginStatus.PASSWORD_ERROR;}}
        }catch(SQLException e){e.printStackTrace();}return LoginStatus.ID_NOT_EXIST;}

    public RegisterStatus Register(String password,String name)//注册   添加id，密码，账号
    {
        con=getConnection();
        try{String sql="insert into userInfo (password,name) values(?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(2,password);
            st.setString(3,name);
            int rs=st.executeUpdate();
            return RegisterStatus.SUCCESS;
            }catch (SQLException e){e.printStackTrace();return null;}
    }


    public void ModifyPassword(String password_original,String password_now)//修改密码
    {               con=getConnection();
        try{String sql="update userInfo set password=? where password=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,password_now);
            st.setString(2,password_original);
            int rs=st.executeUpdate();
           // System.out.println("修改password成功");
        }catch(SQLException e){e.printStackTrace();}

    }
    public void ModifyName(String name_original,String name_now)//修改昵称
    {               con=getConnection();
        try{String sql="update userInfo set name=? where name=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,name_now);
            st.setString(2,name_original);
            int rs=st.executeUpdate();
            System.out.println("修改name成功");
        }catch(SQLException e){e.printStackTrace();}

    }
public void UpdateLeftTime(String userId)
{
    con=getConnection();
    try{String sql="update userInfo set leftTime=? where id=userId?";
        PreparedStatement st=con.prepareStatement(sql);
        String datetime=Tool.getTime();
        st.setString(2,datetime);
        int rs=st.executeUpdate();
        //System.out.println("修改name成功");
    }catch(SQLException e){e.printStackTrace();}
}


public List<Message> GetMessage(String id) {
        List<Message> msg = new ArrayList<>();
        con = getConnection();

        try {   Statement st = con.createStatement();
            String sql = "SELECT sender,receiver,message" +
                    "FROM message a,userInfo b" +
                    " where b.leftTime > a.dateTime and a.receiver = '"+id+"'" +
                    "or b.leftTime > a.dateTime and a.receiver = NULL";

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Message message = new Message();
                message.senderId = rs.getString("sender");
                message.receiverId = rs.getString("receiver");
                message.message = rs.getString("message");

                msg.add(message);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
}



    //Tool.timeCompare();
    /*public String GetNews(String sender) {//查询与sender匹配的消息并输出(群)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM multiChat where sender='" + sender + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                return ("发送者："+m1+" 消息："+m2+" 时间："+m3);
                //System.out.println(m2);
                //System.out.println(m3)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String GetNews1Sender(String sender) {//查询与sender匹配的消息并输出(私聊)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM privateChat where sender='" + sender + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                String m4=rs.getString(4);
                return ("发送者："+m1+" 接收者："+m2+" 消息："+m3+" 时间： "+m4);
                //  System.out.println(m2);
                //System.out.println(m3);
                //System.out.println(m4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String GetNews1Receiver(String receiver) {//查询与receiver匹配的消息并输出(私聊)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM privateChat where  receiver='" + receiver + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                String m4 = rs.getString(4);
                return ("发送者："+m1+" 接收者："+m2+" 消息："+m3+" 时间： "+m4);
                // System.out.println(m2);
                //System.out.println(m3);
                // System.out.println(m4);}
            }} catch (SQLException e) {
            e.printStackTrace();

        }
    return null;}*/

   /* public void SetNews(String sender,String message)//增加消息("群聊")
    {
        con=getConnection();
        try{String sql="insert into multiChat (sender,message,datetime) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            String datetime=Tool.getTime();
            st.setString(1,sender);
            st.setString(2,message);
            st.setString(3,datetime);
            int rs=st.executeUpdate();
            }catch (SQLException e){e.printStackTrace();}
    }
    public void SetNews1(String sender,String receiver,String message)//增加消息（私聊）
    {
        con=getConnection();
        try{String sql="insert into privateChat (sender,receiver,message,datetime) values(?,?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            String datetime=Tool.getTime();
            st.setString(1,sender);
            st.setString(2,receiver);
            st.setString(3,message);
            st.setString(4,datetime);
            int rs=st.executeUpdate();
            }catch (SQLException e){e.printStackTrace();}
    }*/

            public void SetMessage(String sender,String receiver,String message,String type)
            {
                con=getConnection();
                try{String sql="insert into message (sender,receiver,message,dateTime,type) values(?,?,?,?,?)";
                    PreparedStatement st=con.prepareStatement(sql);
                    String datetime=Tool.getTime();
                    st.setString(1,sender);
                    st.setString(2,receiver);
                    st.setString(3,message);
                    st.setString(4,datetime);
                    st.setString(5,type);
                    int rs=st.executeUpdate();
                }catch (SQLException e){e.printStackTrace();}
            }

    public void  CreateFriend(String id,String id_friend,String name_friend)//添加好友数据
    {

        con=getConnection();
        try{String sql="insert into friend (id,id_friend,name_friend) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            st.setString(2,id_friend);
            st.setString(3,name_friend);
            int rs=st.executeUpdate();
            }catch (SQLException e){e.printStackTrace();}
    }
    public void DeleteFriend(String id)//删除好友
    {
        con=getConnection();
        try{String sql="delete from friend where id=? ";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            int rs=st.executeUpdate();
            System.out.println("删除好友成功");}catch(SQLException e){e.printStackTrace();}
    }

    //public static void main(String[] args) throws SQLException {//测试部分
     //DataBase.Connect db=new DataBase.Connect();
     //String id="290111";
     //String password="qwerasdf";
     //String name="jdasda";
    // String time="2021-06-06 19:35:33:110";
        //System.out.println(db.register(id,name,password));
       // System.out.println(db.LogIn(id,name));
       // String a=Tool.getTime();
        //System.out.println(a);
        //db.GetMessage(time);
       // System.out.println(Tool.getTime());

    }





