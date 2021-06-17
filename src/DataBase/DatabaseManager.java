package DataBase;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.ChatroomStatus;
import Status.LoginStatus;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.*;


/**
 * 数据库管理程序
 *
 * @author sjl
 * @version 1.0
 */
public class DatabaseManager implements DatabaseControl {
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
        String url = "jdbc:sqlserver://119.3.175.137:1433;DatabaseName=JavaCD";
        try {
            con = DriverManager.getConnection(url, "SA", "JavaCD2021");
            sta = con.createStatement();
            //System.out.println("连接成功");
        } catch (SQLException e) {
            //System.out.println("连接失败");
            e.printStackTrace();
        }
        return con;
    }

    String random1() {    //返回随机数，UserID
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10) {
            num = (int) Math.floor(Math.random() * 11);
        }
        //System.out.println(num);
        int a = (int) Math.pow(10, (num - 1));
        int random6 = (int) ((Math.random() * 9 + 1) * a);
        String random = Integer.toString(random6);
        // String a1 = String.valueOf(random6);
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select ID from UserInfo where ID='" + random + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                DatabaseManager DB = new DatabaseManager();
                random = DB.random1();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return random;
    }

    String random2() {    //返回随机数，ChatroomID
        int num = (int) Math.floor(Math.random() * 11);
        while (num < 6 || num > 10) {
            num = (int) Math.floor(Math.random() * 11);
        }
        //System.out.println(num);
        int a = (int) Math.pow(10, (num - 1));
        int random6 = (int) ((Math.random() * 9 + 1) * a);
        String random = Integer.toString(random6);
        // String a1 = String.valueOf(random6);
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select ID from ChatroomInfo where ID='" + random + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                DatabaseManager DB = new DatabaseManager();
                random = DB.random2();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(a1);
        return random;
    }


    @Override
    public String returnChatRoomId() {
        DatabaseManager DB = new DatabaseManager();
        String Id = DB.random2();
        return Id;
    }

    @Override
    public FriendInfo returnUser(String Id) {
        con = getConnection();
        String ID = new String();
        String Name = new String();
        FriendInfo friendInfo = null;
        try {
            String sql = "select * from UserInfo where ID=? ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, Id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ID = rs.getString(1);
                Name = rs.getString(3);
                friendInfo = new FriendInfo(ID, Name);
                return friendInfo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public LoginStatus login(String id, String password) {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "SELECT Password FROM UserInfo where ID='" + id + "'";//and password ='" + password + "'"\;
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String m1 = rs.getString(1);
                if (password.equals(m1)) {
                    return LoginStatus.SUCCESS;
                } else {
                    return LoginStatus.PASSWORD_ERROR;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return LoginStatus.ID_NOT_EXIST;
    }


    @Override
    public String register(String password, String name) {
        con = getConnection();
        DatabaseManager DB = new DatabaseManager();
        String id = DB.random1();
        try {
            String sql = "insert into UserInfo(ID,Password,Name) values(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, password);
            st.setString(3, name);
            int rs1 = st.executeUpdate();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean modifyPassword(String id, String password_now) {
        con = getConnection();
        try {
            String sql = "update UserInfo set Password=? where ID=?";
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
    public boolean modifyName(String id, String name_now) {
        con = getConnection();
        try {
            String sql = "update UserInfo set Name=? where ID=?";
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


    @Override
    public boolean recordMessage(String senderId, String content, String chatroomId, Date sendTime) {
        con = getConnection();
        try {
            String sql = "insert into Message(SenderId,ChatRoomId,Content,SendTime) values(?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, senderId);
            st.setString(2, chatroomId);
            st.setString(3, content);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(sendTime);
            st.setString(4, dateString);
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean recordMessage(Message message) {
        con = getConnection();
        String senderId = message.getSenderId();
        String content = message.getContent();
        String chatroomId = message.getChatroomId();
        Date sendTime = message.getSendTime();
        DatabaseManager DB = new DatabaseManager();
        DB.recordMessage(senderId, content, chatroomId, sendTime);
        return true;
    }


    @Override
    public MessageList getHistoryMessage(String chatroomId) {
        con = getConnection();
        MessageList messageList = new MessageList(chatroomId);
        try {
            Statement st = con.createStatement();
            String sql = "select * from Message where ChatRoomId='" + chatroomId + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String senderId = rs.getString(1);
                String chatRoomId = rs.getString(2);
                String content = rs.getString(3);
                Date sendTime = rs.getDate(4);

                messageList.addMessage(new Message(content, senderId, chatRoomId, sendTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageList;
    }

    @Override
    public String findNameThroughID(String ID) {
        con = getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select * from UserInfo where ID='" + ID + "'";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String name = rs.getString(2);
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "没找到这个人呢？";
    }

    @Override
    public ChatroomInfo createChatroom(ChatroomInfo chatroomInfo, FriendInfo creatorInfo) {
        con = getConnection();
        DatabaseManager DB = new DatabaseManager();
        String chatRoomId = chatroomInfo.getChatroomId();//参数传的ID
        if (chatRoomId == null||chatRoomId.equals("")) {
            chatRoomId = DB.random2();//随机获得的ID
        }
        String chatroomName = chatroomInfo.getChatroomName();
        ChatroomInfo.ChatroomType chatroomType = chatroomInfo.getChatroomType();
        String userId = creatorInfo.getFriendId();
        try {
            String sql = "insert into ChatroomInfo(ID,Name,Authentic) values(?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            int authentic;
            if (chatroomType == ChatroomInfo.ChatroomType.PUBLIC) {
                authentic = 0;
            } else {
                authentic = 1;
            }
            st.setString(1, chatRoomId);
            st.setString(2, chatroomName);
            st.setInt(3, authentic);
            st.executeUpdate();
            Vector<FriendInfo> friend = new Vector<FriendInfo>();
            if (authentic == 1) {
                String num1 = chatroomInfo.getFriendList().elementAt(0).getFriendId();
                String num2 = chatroomInfo.getFriendList().elementAt(1).getFriendId();
                DB.insertChatroom(num1, chatRoomId);
                DB.insertChatroom(num2, chatRoomId);
                DB.findNameThroughID(num2);
                FriendInfo friendInfoTemp1 = new FriendInfo(num1, DB.findNameThroughID(num1));
                FriendInfo friendInfoTemp2 = new FriendInfo(num2, DB.findNameThroughID(num2));
                friend.add(friendInfoTemp1);
                friend.add(friendInfoTemp2);
            } else {
                DB.insertChatroom(userId, chatRoomId);
                friend.add(creatorInfo);
            }
            ChatroomInfo chatroomInfo1 = new ChatroomInfo(chatRoomId, chatroomName, chatroomType, friend);
            return chatroomInfo1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    void insertChatroom(String id, String chatroomID)//将id和chatroom插入到聊天室成员表
    {
        con = getConnection();
        try {
            String sql = "insert into Chatroom(ChatroomID,UserID) values(?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomID);
            st.setString(2, id);
            int rs = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean delChatroom(String chatroomId) {
        con = getConnection();
        try {
            String sql = "delete from ChatroomInfo where ID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            int rs = st.executeUpdate();
            DatabaseManager DB = new DatabaseManager();
            DB.exitChatroom(chatroomId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void exitChatroom(String chatroomId) {//删除聊天室与用户的关系
        con = getConnection();
        try {
            String sql = "delete from Chatroom where ChatroomID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            int rs = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean exitChatroom(String userId, String chatroomId) {
        con = getConnection();
        try {
            String sql = "delete from Chatroom where ChatroomID=? and UserID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            st.setString(2, userId);
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean addFriend(String id, String id_friend) {
        con = getConnection();
        if (id_friend != null) {
            try {

                String sql = "insert into FriendRelationship(ID1,ID2) values(?,?),(?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, id);
                st.setString(2, id_friend);
                st.setString(3, id_friend);
                st.setString(4, id);
                int rs1 = st.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    @Override
    public ChatroomStatus joinChatroom(String id, String chatroomId) {
        DatabaseManager DB = new DatabaseManager();
        boolean result1 = DB.exist_chatroom(chatroomId);
        boolean result2 = DB.isPrivate(chatroomId);
        boolean result3 = DB.exist_user(id, chatroomId);
        if (result1 == false) {
            return ChatroomStatus.NOT_EXIST;
        } else if (result3 == true) {
            return ChatroomStatus.JOINED;
        } else if (result2 == true) {
            return ChatroomStatus.PRIVATE;
        } else {
            con = getConnection();
            try {
                String sql = "insert into Chatroom(ChatroomID,UserID) values(?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, chatroomId);
                st.setString(2, id);
                int rs1 = st.executeUpdate();
                return ChatroomStatus.QUALIFIED;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    boolean exist_chatroom(String chatroomId)//检测是否存在聊天室
    {
        con = getConnection();

        try {
            String sql = "select * from ChatroomInfo where ID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            ResultSet rs1 = st.executeQuery();
            while (rs1.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean isPrivate(String chatroomId)//检测聊天室是否私有
    {
        con = getConnection();

        try {
            String sql = "select * from ChatroomInfo where ID=? and Authentic=1";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            ResultSet rs1 = st.executeQuery();
            while (rs1.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    boolean exist_user(String usrId, String chatroomId)//检测user是否在聊天室中
    {
        con = getConnection();

        try {
            String sql = "select * from Chatroom where ChatroomID=? and UserID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            st.setString(2, usrId);
            ResultSet rs1 = st.executeQuery();
            while (rs1.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteFriend(String id, String id_friend) {
        con = getConnection();
        try {

            String sql = "delete from FriendRelationship where (ID1=? and ID2=?) or (ID2=? and ID1=?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, id_friend);
            st.setString(3, id);
            st.setString(4, id_friend);
            int rs = st.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public FriendList getUserFriendList(String userId) {
        con = getConnection();
        String friendName = new String();
        String friendID = new String();
        FriendList friendList = new FriendList();
        try {
            String sql = "select DISTINCT UserInfo.Name,FriendRelationship.ID2 from UserInfo,FriendRelationship where FriendRelationship.ID1=? and UserInfo.ID=FriendRelationship.ID2";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                friendName = rs.getString(1);
                friendID = rs.getString(2);

                friendList.add(new FriendInfo(friendID, friendName));
            }
            return friendList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChatroomList getUserChatroomList(String userId) {
        con = getConnection();
        String ID = new String();
        String Name = new String();
        int Authentic1;
        ChatroomList chatroomList = new ChatroomList();
        ChatroomInfo.ChatroomType Authentic;
        DatabaseManager DB = new DatabaseManager();
        try {
            String sql = "select DISTINCT ChatroomInfo.ID,ChatroomInfo.Name,ChatroomInfo.Authentic from ChatroomInfo,Chatroom where Chatroom.UserID=? and Chatroom.ChatroomID=ChatroomInfo.ID";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ID = rs.getString(1);
                Name = rs.getString(2);
                Authentic1 = rs.getInt(3);
                if (Authentic1 == 1) {
                    Authentic = ChatroomInfo.ChatroomType.PRIVATE;
                } else {
                    Authentic = ChatroomInfo.ChatroomType.PUBLIC;
                }
                Vector<FriendInfo> User = new Vector<FriendInfo>();
                User = DB.getChatRoomUser(ID);
                chatroomList.add(new ChatroomInfo(ID, Name, Authentic, User));
            }
            return chatroomList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChatroomInfo getChatroomInfo(String chatroomID) {
        con = getConnection();
        String ID = new String();
        String Name = new String();
        int Authentic1;
        ChatroomInfo chatroomInfo = null;
        ChatroomInfo.ChatroomType Authentic = null;
        try {
            String sql = "select * from ChatroomInfo where ID=? ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ID = rs.getString(1);
                Name = rs.getString(2);
                Authentic1 = rs.getInt(3);
                if (Authentic1 == 1) {
                    Authentic = ChatroomInfo.ChatroomType.PRIVATE;
                } else {
                    Authentic = ChatroomInfo.ChatroomType.PUBLIC;
                }
            }
            DatabaseManager DB = new DatabaseManager();
            Vector<FriendInfo> User = new Vector<FriendInfo>();
            User = DB.getChatRoomUser(chatroomID);
            chatroomInfo = new ChatroomInfo(ID, Name, Authentic, User);
            return chatroomInfo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Vector<FriendInfo> friend = new Vector<FriendInfo>();
    //      friend.add(creatorInfo);
    //ChatroomInfo chatroomInfo1 = new ChatroomInfo(chatRoomId, chatroomName, chatroomType, friend);
    Vector<FriendInfo> getChatRoomUser(String chatroomId) {
        Vector<FriendInfo> User = new Vector<FriendInfo>();
        con = getConnection();
        try {
            String sql = "select Chatroom.UserID,UserInfo.Name from Chatroom,UserInfo where Chatroom.ChatroomID=? and Chatroom.UserID=UserInfo.ID ";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, chatroomId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String userId = rs.getString(1);
                String userName = rs.getString(2);
                User.add(new FriendInfo(userId, userName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return User;
    }


    @Override
    public ChatroomList getChatroomInfo(String userId1, String userId2) {
        con = getConnection();
        String ID = new String();
        String Name = new String();
        int Authentic1;
        ChatroomList chatroomList = new ChatroomList();
        ChatroomInfo.ChatroomType Authentic;
        DatabaseManager DB = new DatabaseManager();
        String[] chatroomID = new String[20];
        chatroomID = DB.getSameChatroom(userId1, userId2);
        int i = 0;
        while (chatroomID[i] != null) {
            try {
                String sql = "select * from ChatroomInfo where ID=?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, chatroomID[i]);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    ID = rs.getString(1);
                    Name = rs.getString(2);
                    Authentic1 = rs.getInt(3);
                    if (Authentic1 == 1) {
                        Authentic = ChatroomInfo.ChatroomType.PRIVATE;
                    } else {
                        Authentic = ChatroomInfo.ChatroomType.PUBLIC;
                    }
                    Vector<FriendInfo> User = new Vector<FriendInfo>();
                    User = DB.getChatRoomUser(ID);
                    chatroomList.add(new ChatroomInfo(ID, Name, Authentic, User));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            i++;
        }
        return chatroomList;
    }

    public String[] getSameChatroom(String userId1, String userId2) {//选择两个人都在的群ID
        con = getConnection();
        String ID = new String();
        String Name = new String();
        int Authentic1;
        ChatroomInfo chatroomInfo = null;
        ChatroomInfo.ChatroomType Authentic;
        String[] same = new String[10];
        try {
            String sql = "select DISTINCT a.ChatroomID from Chatroom a,Chatroom b where a.UserID=? and b.UserID=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, userId1);
            st.setString(2, userId2);
            ResultSet rs = st.executeQuery();
            int i = 0;
            while (rs.next()) {
                ID = rs.getString(1);
                same[i] = ID;
                i++;
            }
            return same;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ChatroomInfo getPrivateChatroomInfo(String userId1, String userId2) {
        ChatroomList chatroomList = getChatroomInfo(userId1, userId2);
        for (ChatroomInfo chatroomInfo : chatroomList.getList()
        ) {
            if (chatroomInfo.getChatroomType().equals(ChatroomInfo.ChatroomType.PRIVATE)) {
                 return chatroomInfo;
            }
        }
        return null;
    }


//  public static void main(String[] args) {
//        DatabaseManager db=new DatabaseManager();
//      System.out.println(db.exist_chatroom("1231"));
//      //System.out.println(db.returnChatRoomId());
//        //db.getUserChatroomList("1");
//        //System.out.println(db.random2());
//    }
}

