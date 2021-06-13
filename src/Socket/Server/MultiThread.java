package Socket.Server;

import Socket.tools.DataPacket;
import Socket.tools.ThreadManager;

import java.io.IOException;
import java.util.ArrayList;

/*
 * 定义一个管理类，相当于一个中介，处理线程，转发消息
 * 这个只提供方法调用，不需要实例化对象，因此都是静态方法
 */

public class MultiThread {
    //保存线程处理的对象
    private static ArrayList<ServerThread> stList = new ArrayList<>();
    private static ArrayList<ThreadManager> groupList = new ArrayList<>();

    //不需要实例化类，因此构造器为私有
    private MultiThread() {
    }

    //将这个线程处理对象加入到队列中
    public static void addClient(ServerThread st) throws IOException {
        stList.add(st);
    }

    /**
     * 添加群聊
     *
     * @param id      拉进群聊的人账号id
     * @param groupID 群聊id（服务器生成）
     */
    public static void addChatRoom(String id, String groupID) {

        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            if (id.equals(st.socketId)) {
//                temp.arrayList.add(st);
            }
        }
//        grouplist.add(temp);
    }

    /**
     * 获取群聊在线人数
     *
     * @param groupID 群聊ID
     * @return 返回人数
     * [警告]可能出现问题
     */
    public static int getOnlineNumber(String groupID) {
        ThreadManager threadManager = new ThreadManager();
        for (int i = 0; i < groupList.size(); i++) {
            threadManager = groupList.get(i);
            if (groupID.equals(threadManager.GroupID)) {
                break;
            }
        }
        return threadManager.MemberID.length;
    }

    /**
     * 聊天室在线转发
     *
     * @param dataPacket 消息包
     * @param groupID    聊天群号
     * @throws IOException 发送io
     */
    public static void castGroupMsg(DataPacket dataPacket, String groupID) throws IOException {
        ThreadManager threadManager = new ThreadManager();
        for (int i = 0; i < groupList.size(); i++) {
            threadManager = groupList.get(i);
            if (groupID.equals(threadManager.GroupID)) {
                break;
            }
        }
        for (int i = 0; i < threadManager.arrayList.size(); i++) {
            ServerThread st = threadManager.arrayList.get(i);
            st.sendMsg(dataPacket);//发消息给每一个客户机
        }
    }

        
}