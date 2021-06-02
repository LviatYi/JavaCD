import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) {
            try {
                // 和服务器创建连接
                Socket socket = new Socket("localhost",8088);
                while (true)
                {
                    // 要发送给服务器的信息
                    OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    String message;
                    Scanner in = new Scanner(System.in);
                    message=in.next();
                    pw.write(message);
                    pw.flush();

                    socket.shutdownOutput();

                    // 从服务器接收的信息
                    try (InputStream is = socket.getInputStream()) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String info = null;
                        while ((info = br.readLine()) != null) {
                            System.out.println("我是客户端，服务器返回信息：" + info);
                        }
                        br.close();
                        //is.close();
                    }
                    os.close();
                    pw.close();
                    //socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}