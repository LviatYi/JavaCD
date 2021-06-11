package Chatroom.ChatManager;

import java.util.Vector;

/**
 * 聊天室的聊天记录
 *
 * @author LviatYi
 * @version 1.0
 * @className MessageList
 * @date 2021/6/8
 */
public class MessageList {
    private Vector<Message> list;
    private String chatroomId;

    /**
     * 隐藏默认构造函数
     */
    private MessageList() {
        setList(new Vector<Message>());
    }

    public MessageList(String chatroomId) {
        setList(new Vector<Message>());
        this.setChatroomId(chatroomId);
    }
    public MessageList(String chatroomId, Vector<Message> list) {
        this.setChatroomId(chatroomId);
        this.setList(list);
    }

    public Vector<Message> getList() {
        return this.list;
    }
    String getChatroomId() {
        return this.chatroomId;
    }
    public void setList(Vector<Message> list) {
        this.list = list;
    }
    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    String addMessage(Message message) {
        if (message.getChatroomId().equals(this.getChatroomId())) {
            getList().add(message);
            return this.getChatroomId();
        } else {
            return null;
        }
    }
    String addMessage(MessageList messageList) {
        if (messageList.getChatroomId().equals(this.getChatroomId())) {
            for (Message message : messageList.getList()) {
                this.addMessage(message);
            }
            return this.getChatroomId();
        }
        return null;
    }
    void clearMessage(){
        this.getList().clear();
    }
}
