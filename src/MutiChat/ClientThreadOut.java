package MutiChat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientThreadOut extends Thread{
    private Socket server;
    private OutputStream out = null;
    public ClientThreadOut(Socket socket){this.server=socket;}
    public void run()
    {
        Out();
    }
    public void sendMsg2Me(String msg) throws IOException {
        msg += "\r\n";
        out.write(msg.getBytes());
        out.flush();
    }
    private void Out(){
        try{
            while (true){
                out = server.getOutputStream();
                //输入文字，希望从控制台输入的
                Scanner san = new Scanner(System.in);
                String str = san.next();
                sendMsg2Me(str);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
                server.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
