package Socket.Server;

import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Socket.tools.DataPacket;
import Socket.tools.ThreadManager;

import java.io.IOException;

import java.util.Vector;

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

    public static void castGroupMsg(DataPacket dataPacket) throws IOException {
        for (ThreadManager temp: threadList) {
            for (ChatroomInfo roomTemp:temp.chatroomList.getList())
            {
                if(roomTemp.getChatroomId().equals(dataPacket.chatRoomID))
                {
                    temp.thread.sendMsg(dataPacket);
                }
            }
        }
    }

    public static void addChatRoomInfo(String id,ChatroomInfo chatroomInfo)
    {
        for (ThreadManager temp: threadList) {
            if(temp.thread.socketId.equals(id))
            {
                temp.chatroomList.add(chatroomInfo);
                break;
            }
        }
    }

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

    public static void exit(String threadID)
    {
        threadList.removeIf(threadManager -> threadManager.thread.socketId.equals(threadID));
    }
}