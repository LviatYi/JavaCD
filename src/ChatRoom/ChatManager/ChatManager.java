package ChatRoom.ChatManager;

import ChatRoom.ChatRoomGuiControl;

import javax.management.loading.MLet;
import java.util.Date;
import java.util.Vector;

/**
 * 聊天管理类.
 * 负责管理聊天界面与聊天信息缓存.
 * 单例模式.
 *
 * @author LviatYi
 * @version TODO_LviatYi
 * @className ChatManager
 * @date 2021/6/7
 */
public class ChatManager implements ClientChatManager{
    ChatRoomGuiControl chatRoomGuiControl;
    private Vector<MessageList> chatRoomMessageRepo;

    public MessageList getChatRoomMessageList(String chatRoomId){
//        MessageList chatRoomMessageList=;
        /*
        * TODO_LviatYi 向服务器索要聊天记录
        * date 2021/6/9
        */
//        return chatRoomMessageList;
        return null;
    }
    
    public boolean send(Message message){
        /*
        * TODO_LviatYi 向服务器发送聊天记录
        * date 2021/6/9
        */
        return false;
    }

    public  Vector<MessageList> recordNewMessage(String chatRoomId, Message message){
        return this.getChatRoomMessageRepo();
    }

    /**
     * 记录新的聊天室历史消息
     * @param chatRoomId 聊天室 Id
     * @param messageList 返回聊天室历史信息
     * @return
     */
    public Vector<MessageList> recordNewChatRoomMessage(String chatRoomId,MessageList messageList) {
        this.chatRoomMessageRepo.add(messageList);
        return chatRoomMessageRepo;
    }

// 不允许建立空表
//    public Vector<MessageList> addNewChatRoomMessage(String chatRoomId){
//        this.chatRoomMessageRepo.add(new MessageList(chatRoomId));
//        return chatRoomMessageRepo;
//    }

    @Override
    public boolean receiver(String content, String senderId, String chatRoomId, Date date) {
            return false;
    }
    @Override
    public boolean receiver(Message message) {
        return false;
    }

    public Vector<MessageList> getChatRoomMessageRepo() {
        return chatRoomMessageRepo;
    }
}
