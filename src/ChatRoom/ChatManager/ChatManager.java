package ChatRoom.ChatManager;

import ChatRoom.ChatRoomGui;
import ChatRoom.ChatRoomGuiControl;
import ChatRoom.ChatRoomManager.ChatRoomInfo;
import ChatRoom.FriendManager.FriendInfo;

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
public class ChatManager implements ClientChatManager {
    ChatRoomGui chatRoomGui;
    ChatRoomInfo currentChatRoomInfo;

    private Vector<MessageList> chatRoomMessageRepo;

    /**
     * 单例指针
     */
    private static ChatManager instance=null;

    /**
     * 隐藏默认构造函数
     */
    private ChatManager(){}

    private ChatManager(ChatRoomGui parent){
        this.chatRoomGui=parent;
        currentChatRoomInfo =new ChatRoomInfo();
    }

    public static ChatManager getChatManager(ChatRoomGui parent){
        if(instance==null){
            instance=new ChatManager(parent);
        }
        return instance;
    }

    /**
     * 从服务器拉取聊天记录
     *
     * @param chatRoomId 聊天室 Id
     * @return 聊天记录组
     */
    public MessageList pullChatRoomMessageList(String chatRoomId) {
//        MessageList chatRoomMessageList=;
        /*
         * TODO_LviatYi 向服务器索要聊天记录
         * date 2021/6/9
         */
//        return chatRoomMessageList;
        return null;
    }

    /**
     * 从服务器拉取聊天记录（单条发送模式）.
     * 受通讯系统限制.
     * 可能是危险的.
     * @param chatRoomId
     * @param isSingle
     */
    public MessageList pullChatRoomMessageList(String chatRoomId, boolean isSingle) {
        if (isSingle) {
            /*
             * TODO_LviatYi 向服务器索要聊天记录
             * date 2021/6/9
             */
            return null;
        }else{
            return pullChatRoomMessageList(chatRoomId);
        }
    }

    public boolean send(Message message) {
        /*
         * TODO_LviatYi 向服务器发送聊天记录
         * date 2021/6/9
         */
        return true;
    }

    public Vector<MessageList> recordNewMessage(String chatRoomId, Message message) {
        return this.getChatRoomMessageRepo();
    }

    /**
     * 记录新的聊天室历史消息
     *
     * @param chatRoomId  聊天室 Id
     * @param messageList 返回聊天室历史信息
     * @return
     */
    public Vector<MessageList> recordNewChatRoomMessage(String chatRoomId, MessageList messageList) {
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

    /**
     * 获取本地聊天记录
     *
     * @param chatRoomId 聊天室 Id
     * @return 聊天记录组
     */
    public MessageList getChatRoomMessageList(String chatRoomId) {
        for (MessageList messageList : chatRoomMessageRepo) {
            if(messageList.getChatRoomId().equals(chatRoomId)){
                return messageList;
            }
        }
        return null;
    }

    public void setCurrentChatRoomInfo(ChatRoomInfo currentChatRoomInfo) {
        this.currentChatRoomInfo = currentChatRoomInfo;
    }

    public ChatRoomInfo getCurrentChatRoomInfo() {
        return currentChatRoomInfo;
    }
}
