package ChatRoom.ChatManager;

import java.util.Vector;

/**
 * 聊天室的聊天记录
 *
 * @author LviatYi
 * @version TODO_LviatYi
 * @className MessageList
 * @date 2021/6/8
 */
public class MessageList {
    Vector<Message> list;
    String chatRoomId;

    private MessageList() {
        list = new Vector<Message>();
    }

    public MessageList(String chatRoomId) {
        list = new Vector<Message>();
        this.chatRoomId = chatRoomId;
    }

    public MessageList(String chatRoomId, Vector<Message> list) {
        this.chatRoomId = chatRoomId;
        this.list = list;
    }

    String addMessage(Message message) {
        if (message.getChatRoomId().equals(this.chatRoomId)) {
            list.add(message);
            return this.getChatRoomId();
        } else {
            return null;
        }
    }
    String addMessage(MessageList messageList) {
        if (messageList.getChatRoomId().equals(this.chatRoomId)) {
            for (Message message : messageList.getList()) {
                this.addMessage(message);
            }
            return this.chatRoomId;
        }
        return null;
    }
    void clearMessage(){
        this.list.clear();
    }
    String getChatRoomId() {
        return this.chatRoomId;
    }

    Vector<Message> getList() {
        return this.list;
    }
}
