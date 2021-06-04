import java.sql.*;
import java.text.SimpleDateFormat;


/*
 public Connection getConnection();连接数据库
  public void establish();//建立数据库中的表
表一：sign
账号（id）
昵称（name）
密码（password）
操作：
登录  public void signin(String id,String password) ; //查询id和passwprd是否与数据库匹配
注册:  public void register(String id,String password,String name) ; //添加id，密码，账号
修改name/password:  public void modify_password(String passwordoriginal,String passwordnow) ;
                 public void modify_name(String nameoriginal,String namenow);  //original代表未修改的，now代表修改后的

表二：friend
自己id（id）
好友id（id_friend）
好友昵称/备注（name_friend）
操作：
添加好友  public void  addfriend(String id,String id_friend,String name_friend)；//添加好友数据
删除好友   public void deletefriend(String id)；//删除好友

表三：new（私聊）
发送者Sender
接收者Reciver
内容Message
发送时间Datatime
查询内容  public void getnews1sender(String sender) ;//查询与sender发送的消息并输出
        public void getnews1reciver(String reciver) ;//查询与reciver接受的消息并输出
增加消息   public void insertnews1(String sender,String reciver,String message,String datatime);//增加消息至数据库（私聊）

表四：news3（群聊）
发送者Sender
接收者Reciver
内容Message
发送时间Datatime
查询内容 public void getnews(String sender); //查询与sender发出的消息并输出  样式：发送者：1 接收者：2 消息：1 时间： 2021-06-03 at 21:16:16 CST
增加消息  public void insertnews(String sender,String message,String datatime);  //增加消息至数据库("群聊")

*//*****************************/
public class Connect<date> {
    private Connection con;
    private Statement sta;
    private ResultSet rs;
    public static String[] list;

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
        String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=Book";
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


   public void establish() throws SQLException//建立数据库和表
   {con=getConnection();//连接之前的数据库
       Statement stat = con.createStatement();

       //创建数据库hello
      // stat.executeUpdate("create database hello");

       //打开创建的数据库
      // stat.close();
      // con.close();
       //String url = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=Book";
               // "jdbc:mysql://localhost:3306/hello?useUnicode=true&characterEncoding=utf-8";
      // con = DriverManager.getConnection(url, "sa", "111111");
     //  stat = con.createStatement();

       //创建表test
       stat.executeUpdate("create table sign(id varchar(10), password varchar(20),name varchar(10))");
       stat.executeUpdate("create table friend(id varchar(10), id_friend varchar(10),name_friend varchar(50))");
       stat.executeUpdate("create table new(sender varchar(50), message text,datatime text)");
       stat.executeUpdate("create table new3(sender varchar(50), reciver varchar(50),message text,datatime text)");
       System.out.println("建立表成功");


      /* //查询数据
       ResultSet result = stat.executeQuery("select * from test");
       while (result.next())
       {
           System.out.println(result.getInt("id") + " " + result.getString("name"));
       }

       //关闭数据库
       result.close();
       stat.close();
       conn.close();*/
   }





    public void signin(String id,String password)   //登录  查询name和passwprd是否与数据库匹配
    {       con=getConnection();
        try{Statement st = con.createStatement();
            String sql = "SELECT * FROM sign where id='" + id + "' and password ='" + password + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                System.out.println("登陆成功");}
        }catch(SQLException e){e.printStackTrace();}}

    public void register(String id,String password,String name)//注册   添加id，密码，账号
    {
        con=getConnection();
        try{String sql="insert into sign (id,password,name) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            st.setString(2,password);
            st.setString(3,name);
            int rs=st.executeUpdate();
            System.out.println("插入账号密码成功");}catch (SQLException e){e.printStackTrace();}
    }


    public void modify_password(String passwordoriginal,String passwordnow)//修改密码
    {               con=getConnection();
        try{String sql="update sign set password=? where password=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,passwordnow);
            st.setString(2,passwordoriginal);
            int rs=st.executeUpdate();
            System.out.println("修改password成功");
        }catch(SQLException e){e.printStackTrace();}

    }
    public void modify_name(String nameoriginal,String namenow)//修改昵称
    {               con=getConnection();
        try{String sql="update sign set name=? where name=?";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,namenow);
            st.setString(2,nameoriginal);
            int rs=st.executeUpdate();
            System.out.println("修改name成功");
        }catch(SQLException e){e.printStackTrace();}

    }

    public void getnews(String sender) {//查询与sender匹配的消息并输出(群)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM new where sender='" + sender + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                System.out.println("发送者："+m1+" 消息："+m2+" 时间："+m3);
                //System.out.println(m2);
                //System.out.println(m3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getnews1sender(String sender) {//查询与sender匹配的消息并输出(私聊)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM news3 where sender='" + sender + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                String m4=rs.getString(4);
                System.out.println("发送者："+m1+" 接收者："+m2+" 消息："+m3+" 时间： "+m4);
                //  System.out.println(m2);
                //System.out.println(m3);
                //System.out.println(m4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getnews1reciver(String reciver) {//查询与reciver匹配的消息并输出(私聊)
        con = getConnection();
        try {   Statement st = con.createStatement();
            String sql = "SELECT * FROM news3 where  reciver='" + reciver + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                String m1 = rs.getString(1);
                String m2 = rs.getString(2);
                String m3 = rs.getString(3);
                String m4 = rs.getString(4);
                System.out.println("发送者："+m1+" 接收者："+m2+" 消息："+m3+" 时间： "+m4);
                // System.out.println(m2);
                //System.out.println(m3);
                // System.out.println(m4);}
            }} catch (SQLException e) {
            e.printStackTrace();

        }}
    public void insertnews(String sender,String message,String datatime)//增加消息("群聊")
    {
        con=getConnection();
        try{String sql="insert into new (sender,message,datatime) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,sender);
            st.setString(2,message);
            st.setString(3,datatime);
            int rs=st.executeUpdate();
            System.out.println("插入聊天消息成功");}catch (SQLException e){e.printStackTrace();}
    }
    public void insertnews1(String sender,String reciver,String message,String datatime)//增加消息（私聊）
    {
        con=getConnection();
        try{String sql="insert into news3 (sender,reciver,message,datatime) values(?,?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,sender);
            st.setString(2,reciver);
            st.setString(3,message);
            st.setString(4,datatime);
            int rs=st.executeUpdate();
            System.out.println("插入聊天消息成功");}catch (SQLException e){e.printStackTrace();}
    }

    /* public void deletenews(String sender,String reciver)//删除消息
     {
             con=getConnection();
             try{String sql="delete from news where sender=? and reciver=?";
                     PreparedStatement st=con.prepareStatement(sql);
                     st.setString(1,sender);
                     st.setString(2,reciver);
                     int rs=st.executeUpdate();
                     System.out.println("删除成功");}catch(SQLException e){e.printStackTrace();}
     }*/
    public void  addfriend(String id,String id_friend,String name_friend)//添加好友数据
    {

        con=getConnection();
        try{String sql="insert into friend (id,id_friend,name_friend) values(?,?,?)";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            st.setString(2,id_friend);
            st.setString(3,name_friend);
            int rs=st.executeUpdate();
            System.out.println("添加好友成功");}catch (SQLException e){e.printStackTrace();}
    }
    public void deletefriend(String id)//删除好友
    {
        con=getConnection();
        try{String sql="delete from friend where id=? ";
            PreparedStatement st=con.prepareStatement(sql);
            st.setString(1,id);
            int rs=st.executeUpdate();
            System.out.println("删除好友成功");}catch(SQLException e){e.printStackTrace();}
    }
    /*public String timeout(){//获取时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        return (  formatter.format(date));
        // System.out.println(formatter.format(date));
    }*/


}
