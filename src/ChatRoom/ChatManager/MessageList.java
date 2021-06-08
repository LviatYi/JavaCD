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

    private MessageList(){
        list=new Vector<Message>();
    }
    public MessageList(String chatRoomId){
        list=new Vector<Message>();
        this.chatRoomId=chatRoomId;
    }

    String addMessage(Message message){
        if (message.getChatRoomId().equals(this.chatRoomId)){
            list.add(message);
            return message.getContent();
        }else
        {
            return null;
        }
    }
}
