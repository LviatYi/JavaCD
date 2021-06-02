package MutiChat;

import java.io.IOException;

public class start {
    public static void main(String[] args) throws IOException {
        SocketClient client = new SocketClient();
        client.run();
    }
}
