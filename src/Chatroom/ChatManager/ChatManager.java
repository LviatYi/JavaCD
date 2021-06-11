package Chatroom.ChatManager;

import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomInfo;

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
    ChatroomGui chatRoomGui;
    ChatroomInfo currentChatroomInfo;

    private Vector<MessageList> chatroomMessageRepo;

    /**
     * 单例指针
     */
    private static ChatManager instance=null;

    /**
     * 隐藏默认构造函数
     */
    private ChatManager(){}

    private ChatManager(ChatroomGui parent){
        this.chatRoomGui=parent;
        currentChatroomInfo =new ChatroomInfo();
    }

    public static ChatManager getChatManager(ChatroomGui parent){
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
    public MessageList pullChatroomMessageList(String chatRoomId) {
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
     * @param chatroomId
     * @param isSingle
     */
    public MessageList pullChatroomMessageList(String chatroomId, boolean isSingle) {
        if (isSingle) {
            /*
             * TODO_LviatYi 向服务器索要聊天记录
             * date 2021/6/9
             */
            return null;
        }else{
            return pullChatroomMessageList(chatroomId);
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
        return this.getChatroomMessageRepo();
    }

    /**
     * 记录新的聊天室历史消息
     *
     * @param chatroomId  聊天室 Id
     * @param messageList 返回聊天室历史信息
     * @return
     */
    public Vector<MessageList> recordNewChatRoomMessage(String chatroomId, MessageList messageList) {
        this.chatroomMessageRepo.add(messageList);
        return chatroomMessageRepo;
    }

// 不允许建立空表
//    public Vector<MessageList> addNewChatRoomMessage(String chatroomId){
//        this.chatroomMessageRepo.add(new MessageList(chatroomId));
//        return chatroomMessageRepo;
//    }

    @Override
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        /*
        * TODO_LviatYi 接受消息操作
        * date 2021/6/11
        */
        chatRoomGui.updateMessage(new Message(content,senderId,chatroomId,date));
        return false;
    }

    @Override
    public boolean receiver(Message message) {
        chatRoomGui.updateMessage(message);
        return false;
    }

    public Vector<MessageList> getChatroomMessageRepo() {
        return chatroomMessageRepo;
    }

    /**
     * 获取本地聊天记录
     *
     * @param chatroomId 聊天室 Id
     * @return 聊天记录组
     */
    public MessageList getChatRoomMessageList(String chatroomId) {
        for (MessageList messageList : chatroomMessageRepo) {
            if(messageList.getChatroomId().equals(chatroomId)){
                return messageList;
            }
        }
        return null;
    }

    public void setCurrentChatRoomInfo(ChatroomInfo currentChatroomInfo) {
        this.currentChatroomInfo = currentChatroomInfo;
    }

    public ChatroomInfo getCurrentChatroomInfo() {
        return currentChatroomInfo;
    }
}
