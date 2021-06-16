package Socket.Server;

import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.FriendManager.FriendList;
import Socket.tools.DataPacket;
import Socket.tools.ThreadManager;

import java.io.IOException;

import java.util.Vector;

import static Socket.tools.DataPacket.transportType.CALL_ADDED_FRIEND;
import static Socket.tools.DataPacket.transportType.CHATROOM_NEW_MEMBER;

/**
 * 定义一个管理类，相当于一个中介，处理线程，转发消息
 * 这个只提供方法调用，不需要实例化对象，因此都是静态方法
 */

public class MultiThread {
    //保存线程处理的对象
    private static final Vector<ThreadManager> threadList =new Vector<>();
    //不需要实例化类，因此构造器为私有
    private MultiThread() {}
    //将这个线程处理对象加入到队列中
    public static void addClient(ServerThread st, ChatroomList chatroomList){
        ThreadManager temp = new ThreadManager();
        temp.thread =  st;
        temp.chatroomList = chatroomList;
        threadList.add(temp);
    }
    //在聊天室群转发消息
    public static void castGroupMsg(DataPacket dataPacket) throws IOException {
        for (ThreadManager temp: threadList) {
            for (ChatroomInfo roomTemp:temp.chatroomList.getList())
            {
                if(roomTemp.getChatroomId().equals(dataPacket.chatRoomID)&&!temp.thread.socketId.equals(dataPacket.senderId))
                {
                        temp.thread.sendMsg(dataPacket);
                }
            }
        }
    }
    //加入聊天室
    public static void addChatRoomInfo(String id,ChatroomInfo chatroomInfo) throws IOException {
        for (ThreadManager temp: threadList) {
            if(temp.thread.socketId.equals(id))
            {
                temp.chatroomList.add(chatroomInfo);
                break;
            }
        }
        DataPacket dataPacket = new DataPacket();
        dataPacket.chatRoomInfo = chatroomInfo;
        dataPacket.type = CHATROOM_NEW_MEMBER;
        for(ThreadManager temp:threadList){
            for (ChatroomInfo roomTemp:temp.chatroomList.getList())
            {
                if(roomTemp.getChatroomId().equals(chatroomInfo.getChatroomId()))
                {
                    temp.thread.sendMsg(dataPacket);
                }
            }
        }
    }
    //从聊天室退出
    public static void delChatRoomInfo(String id,ChatroomInfo chatroomInfo)
    {
        for (ThreadManager temp: threadList) {
            if(temp.thread.socketId.equals(id))
            {
                temp.chatroomList.del(chatroomInfo.getChatroomId());
                break;
            }
        }
    }
    //通知被加好友
    public static void callAddedFriend(String friendID, FriendList friendList) throws IOException {
        DataPacket dataPacket = new DataPacket();
        for(ThreadManager temp:threadList)
        {
            if(temp.thread.socketId.equals(friendID))
            {
                dataPacket.friendList = friendList;
                dataPacket.type =CALL_ADDED_FRIEND;
                temp.thread.sendMsg(dataPacket);
            }
        }
    }

    public static void exit(String threadID)
    {
        threadList.removeIf(threadManager -> threadManager.thread.socketId.equals(threadID));
    }

        
}