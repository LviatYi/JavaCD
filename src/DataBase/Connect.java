package DataBase;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Socket.tools.*;

public class Connect implements Database
 {

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
        String url = "jdbc:sqlserver://119.3.175.137:1433;DatabaseName=JavaCD";
        try {
            con = DriverManager.getConnection(url, "SA", "JavaCD2021");
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
            String sql = "select UserID from UserInfo where UserID='" + random6 + "'";
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
            String sql = "select ChatRoomID from ChatRoomInfo where ChatRoomID='" + random6 + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sui1();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return random6;
    }



    public LoginStatus LogIn(String id, String password)
    {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT Password FROM UserInfo where UserID='" + id + "'";//and password ='" + password + "'"\;
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



    public int Register(String password, String name)
    {
        con = getConnection();
        try {
            String sql = "insert into UserInfo(UserID,UserName,Password) values(?,?,?)";
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
     void CreateFriend(int FriendID)
     {String a="Friend";
         String ID=a+FriendID;
         con = getConnection();
         try {
             String sql = "create table ?(FriendID int not null)";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1,ID);
             int rs = st.executeUpdate();
         }catch (SQLException e){e.printStackTrace();}
     }



    public int ModifyPassword(String id, String password_now)//修改密码
    {
        con = getConnection();
        try {
            String sql = "update UserInfo set Password=? where UserID=?";
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



    public int ModifyName(String id, String name_now)//修改昵称
    {
        con = getConnection();
        try {
            String sql = "update UserInfo set UserName=? where UserID=?";
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



    public boolean SetMessage(String SenderId, String Content, String ChatRoomId, Date SendTime) {
        con = getConnection();
        try {
            String sql = "insert into Message(SenderId,ChatRoomId,Content,SendTime) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int SenderID1 = Integer.parseInt(SenderId);
            int ChatRoomID1 = Integer.parseInt(ChatRoomId);
            st.setInt(1, SenderID1);
            st.setInt(2,ChatRoomID1);
            st.setString(3, Content);
            st.setDate(4, (java.sql.Date) SendTime);
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


     //TODO
     //返回更新
     @Override
     public MessageList GetGroupMessage(String groupID) {
         con = getConnection();
         try {
             Statement st = con.createStatement();
             String sql = "select * from Message where ChatRoomId='" + groupID + "'";
             ResultSet rs = st.executeQuery(sql);
             int i=0;
             while (rs.next()) {
                 String senderId = rs.getString(1);//发送者
                 String groupId = rs.getString(2);//接收者
                 String message = rs.getString(3);//内容
                 Date datetime = rs.getDate(4);//时间

                 //返回

             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }



     public String CreateChatRoom(boolean isPrivate,String chatRoomName) {
         con = getConnection();
         try {
             String sql = "insert into ChatRoomInfo(ChatRoomID,ChatRoomName,IsPrivate) values(?,?,?)";
             PreparedStatement st = con.prepareStatement(sql);
             int a = sui1();
             String property;
             if (isPrivate) {
                 property = "true";
             } else {
                 property = "false";
             }
             st.setInt(1, a);
             st.setString(2,chatRoomName);
             st.setString(3, property);
             int rs = st.executeUpdate();
             CreateChatRoom1(a);
             String aa = String.valueOf(a);
             return aa;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }
     void CreateChatRoom1(int ChatRoomID)
     {String a="ChatRoom";
         String ID=a+ChatRoomID;
         con = getConnection();
         try {
             String sql = "create table ?(UserID int not null)";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1,ID);
             int rs = st.executeUpdate();
         }catch (SQLException e){e.printStackTrace();}
     }



     public int  DelChatRoom(String chatroomId) {
         con = getConnection();
         try {
             String sql = "delete from ChatRoomInfo where ChatRoomID=?";
             PreparedStatement st = con.prepareStatement(sql);
             int id ;id= Integer.parseInt(chatroomId);
             st.setInt(1,id);
             int rs = st.executeUpdate();
             Delchatroom1(chatroomId);
             return 1;
         }catch(SQLException e){e.printStackTrace();}
         return 0;
     }
     void Delchatroom1(String chatroomId)
     {String a="ChatRoom";
         String id=a+chatroomId;
         con = getConnection();
         try {
             String sql = "drop table ?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1,id);
             int rs = st.executeUpdate();
         }catch (SQLException e){e.printStackTrace();}
     }



     public int ExitChatRoom(String id,String chatRoomID)
     {String a="ChatRoom";
         String Id=a+chatRoomID;
         con = getConnection();
         try {
             String sql = "delete from ? where UserID=?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1,Id);
             st.setString(2,id);
             int rs = st.executeUpdate();
             return 1;
         }catch (SQLException e){e.printStackTrace();}
         return 0;
     }




     public int CreateFriend(String id, String id_friend) {
         con = getConnection();
         String a="Friend";
         String ID=a+id;
         boolean rs=CreateFriend1(id,id_friend);
         if(rs==true)
         {
             return -1;//有这个好友
         }
         else
         {
             CreateFriend2(id,id_friend);//添加好友
             CreateFriend2(id_friend,id);
             return 1;
         }
     }
     boolean CreateFriend1(String id, String id_friend)//查找有无这个好友
     {
         con = getConnection();
         String a="Friend";
         String ID=a+id;
         try {
             String sql = "select * from ? where FriendId=?";
             PreparedStatement st = con.prepareStatement(sql);
             int idf=Integer.parseInt(id_friend);
             st.setString(1,ID);
             st.setInt(2,idf);
             ResultSet rs = st.executeQuery();
             while(rs.next()){return true;}
             return false;
         }catch (SQLException e) {
             e.printStackTrace();
         }
         return false;
     }
     void CreateFriend2(String id,String id_friend)//添加好友
     { con = getConnection();
         String a="Friend";
         String ID=a+id;
         try {
             String sql = "insert into ?(FriendId) values(?)";
             PreparedStatement st = con.prepareStatement(sql);
             int idf=Integer.parseInt(id_friend);
             st.setString(1,ID);
             st.setInt(2,idf);
             int rs1 = st.executeUpdate();
         }catch (SQLException e) {
             e.printStackTrace(); }
     }



     public int JoinChatRoom(String id, String chatroomID) {
         con = getConnection();
         String a="ChatRoom";
         String ID=a+chatroomID;
         try {
             String sql = "insert into ?(UserID) values(?)";
             PreparedStatement st = con.prepareStatement(sql);
             int idf=Integer.parseInt(id);
             st.setString(1,ID);
             st.setInt(2,idf);
             int rs = st.executeUpdate();
             return  1;
         }catch (SQLException e) {
             e.printStackTrace();
         }
         return 0;
     }




     public int DeleteFriend(String id,String id_friend)
    {con=getConnection();
        DeleteFriend1(id,id_friend);
        DeleteFriend1(id_friend,id);
        return 1;
    }
    public int DeleteFriend1(String id,String id_friend)//删除好友
    {
        con = getConnection();
        String a="Friend";
        String Friend=a+id;
        try {
            String sql = "delete from ? where Id1=?";
            PreparedStatement st = con.prepareStatement(sql);
            int idfriend;
            idfriend=Integer.parseInt(id_friend);
            st.setString(1, Friend);
            st.setInt(2, idfriend);
            int rs = st.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




    public String[] getFriend(String friendId) {
        con = getConnection();
        String a="Friend";
        String ID=a+friendId;
        String rs1[]=new String[20];
        try {
            String sql = "select * from ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ID);
            ResultSet rs = st.executeQuery();
            int i=0;
            while(rs.next())
            {
                rs1[i]=rs.getString(1);
                i++;
            }
           return rs1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs1;
    }



     //TODO
     //修改返回类型
     @Override
     public ChatroomList getGroup(String id) {
         con = getConnection();
         String rs1[]=new String[20];
         try {
             String sql = "select ChatRoomID from Message where SenderID=?";
             PreparedStatement st = con.prepareStatement(sql);
             int Id=Integer.parseInt(id);
             st.setInt(1,Id );
             ResultSet rs = st.executeQuery();
             int i=0;
             List list=new ArrayList();
             while(rs.next())
             {
                 String ChatRoomID=rs.getString(1);//聊天室ID

                 //返回
             }
             return ;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }





     //TODO
    @Override
    public FriendInfo getUserFriendList(FriendInfo userInfo) {
        con = getConnection();
        String a="Friend";
        String b= userInfo.getFriendId();
        String ID=a+b;
        String rs1[]=new String[20];
        try {
            String sql = "select UserInfo.UserName,?.FriendID from UserInfo,?\n" +
                    "where UserInfo.UserID=?.FriendID";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, ID);
            st.setString(2,ID);
            st.setString(3,ID);
            ResultSet rs = st.executeQuery();
            int i=0;
            List list=new ArrayList();
            while(rs.next())
            {
               // rs1[i]=rs.getString(1);
                String Name=rs.getString(1);//好友昵称
                String Id=rs.getString(2);//好友ID
                //返回

            }
            return ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




//TODO
     public ChatroomInfo findChatRoomInfoThroughID(String chatroomID)
     {
         con = getConnection();
         String a="ChatRoom";
         String ID=a+chatroomID;
         String rs1[]=new String[20];
         try {
             String sql = "select * from ?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1,ID);
             ResultSet rs = st.executeQuery();
             int i=0;
             List list=new ArrayList();//聊天室列表成员
             while(rs.next())
             {
                 list.add(i,rs.getString(1));
                 i++;
             }
             String RS[]=new String[3];
                     RS=findChatRoomInfoThroughID1(chatroomID);
             String ChatRoomId=RS[0];//聊天室ID
             String ChatRoomName=RS[1];//聊天室昵称
             String ChatRoomType=RS[2];//聊天室属性
             //返回

             return ;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }
     public String[] findChatRoomInfoThroughID1(String chatroomID)
     {
         con = getConnection();
         String a="Friend";
         String ID=a+chatroomID;
         String rs1[]=new String[20];
         try {
             String sql = "select * from ChatRoomInfo where ChatRoomID=?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1, chatroomID);
             ResultSet rs = st.executeQuery();
             int i=0;
             while(rs.next())
             {
                 // rs1[i]=rs.getString(1);
                 rs1[1]=rs.getString(1);
                 rs1[2]=rs.getString(2);
                 rs1[3]=rs.getString(3);
             }
             return rs1;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }





//TODO
     ChatroomInfo findChatRoomInfoThroughUser(String id,String friendID)
     {
         con = getConnection();
         String Id[]=new String[10];
         String RS[]=new String[10];
         Id=findChatRoomID();//所有私聊室
         int i=0;int j=0;
         while(Id[i]!=null)
         {
             String a="ChatRoom";
             String ID=a+Id[i];
             boolean rs1=findUserID(id,ID);
             boolean rs2=findUserID(friendID,ID);
             if((rs1=true)&&(rs2=true))
             {
                 RS[j]=Id[i];j++;
             }
             i++;
         }
         int k=0;
        while(RS[k]!=null)
        {
            con = getConnection();
            try {
                String sql = "select * from ChatRoomInfo where ChatRoomID=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, RS[k]);
                ResultSet rs = st.executeQuery();
                while(rs.next())
                {
                   String ChatRoomID=rs.getString(1);//聊天室ID
                   String ChatRoomName=rs.getString(2);//聊天室名称
                   String IsPrivate=rs.getString(3);//聊天室属性
                          //返回
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            k++;
        }
     }
     public String[] findChatRoomID()
     {
         con = getConnection();
         try {
             Statement st = con.createStatement();
             String sql = "select ChatRoomID from ChatRoomInfo where IsPrivate='true'";
             ResultSet rs = st.executeQuery(sql);
             String []rs1=new String[10];
             int i=0;
             while(rs.next())
             {
                 // rs1[i]=rs.getString(1);
                 rs1[i]=rs.getString(1);
                 i++;
             }
             return rs1;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }
     public boolean findUserID(String id,String chatRoomId)//聊天室中是否有id
     {
         con = getConnection();
         try {
             String sql = "select UserID from ? where UserID=?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1, id);
             st.setString(2, chatRoomId);
             ResultSet rs = st.executeQuery();
             while(rs.next())
             {
                 return true;//存在这个人
             }
             return false;
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return false;
     }




   /*  public static void main(String[] args) {
         Connect db=new Connect();
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
    boolean a=db.findUserID(1,"nihao");
         System.out.println(a);
    }*/
}





