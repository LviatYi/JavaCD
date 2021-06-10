package ChatRoom.ChatManager;

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
    private String chatRoomId;

    /**
     * 隐藏默认构造函数
     */
    private MessageList() {
        setList(new Vector<Message>());
    }

    public MessageList(String chatRoomId) {
        setList(new Vector<Message>());
        this.setChatRoomId(chatRoomId);
    }
    public MessageList(String chatRoomId, Vector<Message> list) {
        this.setChatRoomId(chatRoomId);
        this.setList(list);
    }

    public Vector<Message> getList() {
        return this.list;
    }
    String getChatRoomId() {
        return this.chatRoomId;
    }
    public void setList(Vector<Message> list) {
        this.list = list;
    }
    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    String addMessage(Message message) {
        if (message.getChatRoomId().equals(this.getChatRoomId())) {
            getList().add(message);
            return this.getChatRoomId();
        } else {
            return null;
        }
    }
    String addMessage(MessageList messageList) {
        if (messageList.getChatRoomId().equals(this.getChatRoomId())) {
            for (Message message : messageList.getList()) {
                this.addMessage(message);
            }
            return this.getChatRoomId();
        }
        return null;
    }
    void clearMessage(){
        this.getList().clear();
    }
}
