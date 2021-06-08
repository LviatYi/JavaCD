package ChatRoom.ChatRoomManager;

/**
 * @author May_bebe
 * @version 1.0
 * @className ChatRoom
 * @date 2021/6/6
 */

public class ChatRoomInfo
{
    private String chatRoomId;
    private String chatRoomName;

    public ChatRoomInfo(String chatRoomId,String chatRoomName){
        this.chatRoomId=chatRoomId;
        this.chatRoomName=chatRoomName;
    }

    public ChatRoomInfo(ChatRoomInfo chatRoomInfo){
        this.chatRoomName=chatRoomInfo.getChatRoomName();
        this.chatRoomId=chatRoomInfo.getChatRoomId();
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setChatRoomName(String chatRoomName) { this.chatRoomName = chatRoomName; }

}
