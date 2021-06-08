package ChatRoom.ChatManager;

/**
 * 聊天记录类
 *
 * @author LviatYi
 * @version 1.0
 * @className Message
 * @date 2021/6/7
 */
public class Message {
    private String content;
    private String senderId;
    private String recordId;
    private String dateTime;

    Message(String content,String senderId,String recordId,String dateTime){
        this.content=content;
        this.senderId=senderId;
        this.recordId=recordId;
        this.dateTime=dateTime;
    }
}
