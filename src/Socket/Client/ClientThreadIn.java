package Socket.Client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientThreadIn extends Thread {

    private Socket server;
    public boolean exit = false;

    public ClientThreadIn(){}
    public void setSocket(Socket socket){this.server =socket;}


    public void run(){
        In();
    }

    private void In(){
        try{
            while (!exit)
            {
                InputStream in = server.getInputStream();
                //不仅仅是起到勺子舀输入流里面数据的作用，也是存储数据
                byte[] b = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len = 0;
                String s = null;
                if ((len = in.read(b)) != -1) {
                    s = new String(b);
                    System.out.println(s);
                    sb.append(s);
                }
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
