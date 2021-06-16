package Socket.Client;

import java.io.*;
import java.net.Socket;

public class ClientThreadOut extends Thread {
    private String message = null;
    private Socket server;

    private OutputStream out;

    public ClientThreadOut() {
    }

    public void setSocket(Socket socket) {
        this.server = socket;
    }

    @Override
    public void run() {
        Out();
    }




    public void setMessage(String s) {
        this.message = s;
    }

    private void Out() {
        try {
            out = new DataOutputStream(server.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            while (true) {
                if (message != null) {
                    bw.write(message+"\n");
                    bw.flush();
                    message = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}