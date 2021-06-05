package Socket.Client;

import Socket.tools.Message;
import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThreadIn extends Thread {

    private Socket server;
    public boolean exit = false;
    public List<Message> msgList = new ArrayList<>();
    public ClientThreadIn(){}
    public void setSocket(Socket socket){this.server =socket;}


    public void run(){
        In();
    }

    private void In(){
        try{
            while (!exit)
            {
                DataInputStream in = new DataInputStream(server.getInputStream());
                String str = in.readUTF();
                msgList.add(JSON.parseObject(str,Message.class));


            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                server.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
