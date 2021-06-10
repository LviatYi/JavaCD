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

    /**
     * 默认构造函数.
     * 将 sendTime 设置为系统当前时间.
     * @param content 聊天内容
     * @param senderId 发送者 Id
     * @param chatRoomId 聊天室 Id
     */
    public Message(String content,String senderId,String chatRoomId){
        this.content=content;
        this.senderId=senderId;
        this.chatRoomId=chatRoomId;
        this.sendTime=new Date();
    }
    public Message(String content,String senderId,String chatRoomId,Date date){
        this.content=content;
        this.senderId=senderId;
        this.chatRoomId=chatRoomId;
        this.sendTime=date;
    }
    public Message(Message message){
        this.content=message.getContent();
        this.senderId=message.getSenderId();
        this.chatRoomId=message.getChatRoomId();
        this.sendTime=message.getSendTime();
    }

    /**
     * 隐藏默认构造函数.
     * 不允许不完整的信息.
     */
    private Message(){};

    public String getChatRoomId(){
        return this.chatRoomId;
    }
    public String getContent(){
        return this.content;
    }
    public String getSenderId(){
        return this.senderId;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取发送时间
     * @return 发送时间
     */
    public Date getSendTime(){
        return this.getSendTime();
    }

    /**
     * 获取发送时间的时间戳
     * @return 发送时间的时间戳
     */
    public long getSendTimeLong(){
        return this.sendTime.getTime();
    }


}
