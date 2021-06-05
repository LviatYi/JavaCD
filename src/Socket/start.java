package Socket;

import Socket.Client.ClientImpl;

import java.io.IOException;

public class start {
    public static void main(String[] args) throws IOException {
        ClientImpl client = new ClientImpl();
        client.run();
    }
}
