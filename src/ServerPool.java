import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerPool {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            ServerSocket server= new ServerSocket(8088);
            Socket socket= null;
            int count= 0;
            ExecutorService execut= Executors.newFixedThreadPool(5);
            System.out.println("Server is already.");

            while(true) {
                socket= server.accept();
                execut.execute(new ServerThread(socket));//调用线程池
                count++;//统计客户端的数量
                System.out.println("connection count:"+count);
            }
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
