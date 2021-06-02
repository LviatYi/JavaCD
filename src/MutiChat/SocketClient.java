package MutiChat;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClient {


    private Socket socket = null;


    public void run() throws IOException {
        client();
    }

    private void client() throws IOException {
            socket = new Socket("127.0.0.1", 9000);
            ClientThreadOut co =new ClientThreadOut(socket);
            ClientThreadIn ci =new ClientThreadIn(socket);
            co.start();
            ci.start();
    }

}
