package Chatroom.ChatManager;

import java.util.Date;

/**
 * 聊天记录类.
 * 由本地决定用于记录聊天记录相关信息的类.
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className Message
 * @date 2021/6/7
 */
public class Message {
    //field

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
    private String chatroomId;
    /**
     * 发送时间
     */
    private Date sendTime;

    //construct

    /**
     * 隐藏默认构造函数.
     * 不允许不完整的信息.
     */
    private Message() {
    }

    /**
     * 默认构造函数.
     * 将 sendTime 设置为系统当前时间.
     *
     * @param content    聊天内容
     * @param senderId   发送者 Id
     * @param chatroomId 聊天室 Id.指明了发送的目标.
     */
    public Message(String content, String senderId, String chatroomId) {
        this.content = content;
        this.senderId = senderId;
        this.chatroomId = chatroomId;
        this.sendTime = new Date();
    }

    public Message(String content, String senderId, String chatroomId, Date date) {
        this.content = content;
        this.senderId = senderId;
        this.chatroomId = chatroomId;
        this.sendTime = date;
    }

    public Message(Message message) {
        this.content = message.getContent();
        this.senderId = message.getSenderId();
        this.chatroomId = message.getChatroomId();
        this.sendTime = message.getSendTime();
    }

    //getter setter

    public String getChatroomId() {
        return this.chatroomId;
    }

    public String getContent() {
        return this.content;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    //function

    /**
     * 获取发送时间的时间戳.按毫秒计.
     *
     * @return 发送时间的时间戳
     */
    public long getSendTimeLong() {
        return this.sendTime.getTime();
    }
}
