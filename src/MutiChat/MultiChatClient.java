package MutiChat;

import java.io.*;
import java.net.Socket;


public class MultiChatClient {


    public void run() throws IOException {
        client();
    }

    private void client() throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
            ClientThreadOut co =new ClientThreadOut(socket);
            ClientThreadIn ci =new ClientThreadIn(socket);
            co.start();
            ci.start();
    }

}
