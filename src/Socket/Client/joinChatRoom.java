package Socket.Client;


import ChatRoom.ChatRoomManager.ChatRoomInfo;
import Socket.tools.DataPacket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class joinChatRoom {
    private ClientThreadOut co = null;
    private ClientThreadIn ci = null;

    public static boolean joinChatRoom(String userID,ChatRoomInfo chatRoom){
        try {
            Socket socket = new Socket("127.0.0.1", 9000);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataPacket mes = new DataPacket();
            mes.type = DataPacket.transportType.JOIN_CHATROOM;
            mes.id = userID;
            mes.chatRoomID = chatRoom.getChatRoomId();
            dos.writeUTF(JSONObject.toJSONString(mes));
            dos.flush();
            while (true){
                DataPacket dp = JSON.parseObject(dis.readUTF(),DataPacket.class);
                if(dp.type == DataPacket.transportType.JOIN_CHATROOM&&dp.systemTip==1){
                    socket.close();
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
