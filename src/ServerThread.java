import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    private Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is=null;
        InputStreamReader isr=null;

        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;

        try {
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String info_in = null;

            while((info_in=br.readLine())!=null){
                System.out.println("客户端说："+info_in);
                DataBase dataBase = new DataBase();
                dataBase.saveString(info_in,"server");
                socket.shutdownInput();
            }
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("服务器欢迎你");

            pw.flush();
        } catch (Exception e) {
            // TODO: handle exception
        } finally{
            //关闭资源
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}