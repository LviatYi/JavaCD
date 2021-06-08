package ChatRoom.ChatManager;


import java.util.Date;
import java.time.*;
import java.util.Timer;

/**
 * 聊天记录类
 *
 * @author LviatYi
 * @version 1.0
 * @className Message
 * @date 2021/6/7
 */
public class Message {
    /**
     * 主要内容
     */
    private String content;
    /**
     * 发送者 ID
     */
    private String senderId;
    /**
     * 聊天记录所在的聊天室 ID
     */
    private String chatRoomId;
    /**
     * 发送时间
     */
    private Date sendTime;


    Message(String content,String senderId,String chatRoomId){
        this.content=content;
        this.senderId=senderId;
        this.chatRoomId=chatRoomId;
        this.sendTime=new Date();
    }
    Message(String content,String senderId,String chatRoomId,Date date){
        this.content=content;
        this.senderId=senderId;
        this.chatRoomId=chatRoomId;
        this.sendTime=date;
    }
    Message(Message message){
        this.content=message.getContent();
        this.senderId=message.getSenderId();
        this.chatRoomId=message.getChatRoomId();
        this.sendTime=message.getSendTime();
    }

    String getChatRoomId(){
        return this.chatRoomId;
    }

    String getContent(){
        return this.content;
    }
    String getSenderId(){
        return this.senderId;
    }
    Date getSendTime(){
        return this.getSendTime();
    }
}
