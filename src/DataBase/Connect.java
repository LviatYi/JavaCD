package DataBase;

import java.sql.*;
import java.util.*;
import java.util.List;

import Socket.tools.*;

/*****************************/
public class Connect //implements Database
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


    /*public void Establish() throws SQLException//建立数据库的表
    {con=getConnection();//连接之前的数据库
        Statement stat = con.createStatement();
        //创建表test
        stat.executeUpdate("create table userInfo(id varchar(10), password varchar(20),name varchar(10),leftTime datetime)");
        stat.executeUpdate("create table message(sender varchar(50),receiver varchar(50) ,message text,dateTime datetime,id varchar(50))");
        stat.executeUpdate("create table friend(id varchar(50), id_friend varchar(50),name_friend varchar(50))");
        //System.out.println("建立表成功");

    }*/
    String sui(){
        int num= (int) Math.floor(Math.random()*11);
        while(num<6||num>10)
            num= (int) Math.floor(Math.random()*11);
        //System.out.println(num);
        int a= (int) Math.pow(10,(num-1));
        int random6=(int)((Math.random()*9+1)*a);
        String a1=String.valueOf(random6);
        con=getConnection();
        try{
            Statement st=con.createStatement();
            String sql="select id from userInfo where id='"+a1+"'";
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){sui();}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return a1;
    }

    public LoginStatus LogIn(String id,String password)   //登录  查询id和password是否与数据库匹配
    {       con=getConnection();
        try{Statement st = con.createStatement();
            String sql = "SELECT * FROM userInfo where id='" + id + "'";//and password ='" + password + "'"\;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                //System.out.println(rs.getString(2));
                //System.out.println(password);
                String m1=rs.getString(2);
                if(password.equals(m1)) return LoginStatus.SUCCESS;
                else{ return LoginStatus.PASSWORD_ERROR;}}
        }catch(SQLException e){e.printStackTrace();}return LoginStatus.ID_NOT_EXIST;}

    public int Register(String password, String name)//注册   添加id，密码，账号
    {
        con=getConnection();
        try{String sql="insert into userInfo (id,password,name,;leftTime) values(?,?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            String a=sui();
            String b=Tool.getTime();
            st.setString(1,a);
            st.setString(2,password);
            st.setString(3,name);
            st.setString(4,b);
            int rs=st.executeUpdate();
            int c=Integer.parseInt(a);
            return c;
        }catch (SQLException e){e.printStackTrace();}

        return 0;
    }


    public boolean ModifyPassword(String id,String password_now)//修改密码
    {               con=getConnection();
        try{String sql="update userInfo set password=? where id=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,password_now);
            st.setString(2,id);
            int rs=st.executeUpdate();
            // System.out.println("修改password成功");
            return true;
        }catch(SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean ModifyName(String id,String name_original,String name_now)//修改昵称
    {               con=getConnection();
        try{String sql="update userInfo set name=? where id=? and name=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,name_now);
            st.setString(2,id);
            st.setString(3,name_original);
            int rs=st.executeUpdate();
            return true;
            //System.out.println("修改name成功");
        }catch(SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean UpdateLeftTime(String userId)
    {
        con=getConnection();
        try{String sql="update userInfo set leftTime=? where id=?";
            PreparedStatement st=con.prepareStatement(sql);
            String datetime=Tool.getTime();
            st.setString(1,datetime);
            st.setString(2,userId);
            int rs=st.executeUpdate();
            return true;
            //System.out.println("修改name成功");
        }catch(SQLException e){e.printStackTrace();}
        return false;
    }


    public List<Message> GetMessage(String id) {
        List<Message> msg = new ArrayList<>();
        con = getConnection();

        try {   Statement st = con.createStatement();
            String sql = "SELECT message.sender,message.receiver,message.message" +
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





    public boolean SetMessage(String sender,String receiver,String message,String id)
    {
        con=getConnection();
        try{String sql="insert into message (sender,receiver,message,dateTime,id) values(?,?,?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            String datetime=Tool.getTime();
            st.setString(1,sender);
            st.setString(2,receiver);
            st.setString(3,message);
            st.setString(4,datetime);
            // st.setString(5,type);
            st.setString(5,id);
            int rs=st.executeUpdate();
            return true;
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }

    public boolean  CreateFriend(String id,String id_friend,String name_friend)//添加好友数据
    {

        con=getConnection();
        try{String sql="insert into friend (id,id_friend,name_friend) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            st.setString(2,id_friend);
            st.setString(3,name_friend);
            int rs=st.executeUpdate();
            return true;
        }catch (SQLException e){e.printStackTrace();}
        return false;
    }
    public boolean DeleteFriend(String id)//删除好友
    {
        con=getConnection();
        try{String sql="delete from friend where id_friend=? ";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            int rs=st.executeUpdate();
            // System.out.println("删除好友成功");
            return true;
        }catch(SQLException e){e.printStackTrace();}
        return false;
    }

   // public static void main(String[] args) throws SQLException {//测试部分
       // DataBase.Connect db=new DataBase.Connect();
        //System.out.println(db.Register("11","11"));
       // String id="test";
       // String password="123";
       // String name="jdasda";
        // String time="2021-06-06 19:35:33:110";
        //System.out.println(db.register(id,name,password));
        // System.out.println(db.LogIn(id,name));
        // String a=Tool.getTime();
        //System.out.println(a);
        //db.GetMessage(time);
        // System.out.println(Tool.getTime());
        //System.out.println(db.Register(password,name));

    }





