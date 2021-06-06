package Socket.Server;

import Socket.tools.Message;

import java.io.IOException;
import java.util.ArrayList;

/*
 * 定义一个管理类，相当于一个中介，处理线程，转发消息
 * 这个只提供方法调用，不需要实例化对象，因此都是静态方法
 */

public class MultiThread {
    //保存线程处理的对象
    private static ArrayList<ServerThread> stList=new ArrayList();
    //不需要实例化类，因此构造器为私有
    private MultiThread() {}

    //将一个客户对应的线程处理对象加入到队列中
    public static void addClient(ServerThread st) throws IOException {
        stList.add(st);//将这个线程处理对象加入到队列中
    }

    public static void castGroupMsg(Message message) throws IOException {
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            st.sendMsg(message);//发消息给每一个客户机
        }
    }

    public static void castPrivateMSG(Message message) throws IOException{
        for (int i = 0; i < stList.size(); i++) {
            ServerThread st = stList.get(i);
            if (st.SocketID.equals(message.id)){
                st.sendMsg(message);
                //TODO
            }
        }
    }
}