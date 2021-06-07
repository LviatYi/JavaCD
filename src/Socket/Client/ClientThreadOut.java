package Socket.Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThreadOut extends Thread{
    private String message = null;
    private Socket server;

    private DataOutputStream out = null;

    public ClientThreadOut(){}

    public void setSocket(Socket socket){this.server =socket;}

    public void run()
    {
        Out();
    }


    public void sendToServer(String msg) throws IOException {
        out.writeUTF(msg);
        out.flush();
    }

    public void setMessage(String s){this.message = s;}

    private void Out(){
        try{
            while (true){
                out = new DataOutputStream(server.getOutputStream());
                //输入文字，希望从控制台输入的
                if(message !=null){
                    sendToServer(message);
                    message =null;
                }
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