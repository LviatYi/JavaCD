package Chatroom.ChatManager;

import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomGui;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import com.sun.istack.internal.NotNull;

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
public class ChatManager implements ClientManager {
    ChatroomGui chatroomGui;
    ChatroomInfo currentChatroomInfo;

    /**
     * 聊天记录集合.
     */
    private Vector<MessageList> chatroomMessageRepo;
    /**
     * 在缓存中的聊天记录的所有聊天室 Id.
     */
    private Vector<String> chatroomList;

    /**
     * 单例指针
     */
    private static ChatManager instance=null;

    /**
     * 隐藏默认构造函数
     */
    private ChatManager(){
        currentChatroomInfo=new ChatroomInfo(null,null, null,null);
        chatroomMessageRepo=new Vector<MessageList>();
        chatroomList=new Vector<String>();
    }

    private ChatManager(@NotNull ChatroomGui parent){
        this.chatroomGui =parent;
        currentChatroomInfo =new ChatroomInfo();
    }

    /**
     * 获取单例.
     * @param parent 父级 Gui.
     * @return ChatManager
     */
    public static ChatManager getChatManager(@NotNull ChatroomGui parent){
        if(instance==null){
            instance=new ChatManager(parent);
        }
        return instance;
    }

    /**
     * 从服务器拉取聊天记录.
     *
     * @param chatroomId 聊天室 Id
     * @return 聊天记录组
     */
    public MessageList pullChatroomMessageList(String chatroomId) {
        MessageList chatroomMessageList=null;
        /*
         * TODO_LviatYi 向服务器索要聊天记录
         * date 2021/6/9
         */
        int index=chatroomList.indexOf(chatroomId);
        if (index!=-1){
            chatroomMessageRepo.elementAt(index).addHistoryMessage(chatroomMessageList);
        }
        return chatroomMessageList;
    }

    /**
     * 从服务器拉取聊天记录（异步模式）.
     * 受通讯系统限制.
     * 可能是危险的.
     * @param chatroomId 聊天室 Id
     * @param isSingle 是否为异步模式.
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

    /**
     * 向服务器发送 Message .
     * @param message Message
     * @return 发送状态.仅保证已发送.
     */
    public boolean send(Message message) {
        /*
         * TODO_LviatYi 向服务器发送聊天记录
         * date 2021/6/9
         */
        return true;
    }

    /**
     * 将来自外部的新消息加入到对应本地聊天室的聊天记录中.
     * @param message Message
     * @return 新的聊天室记录.
     */
    public Vector<MessageList> recordNewMessage(Message message) {
        for (MessageList messageList:chatroomMessageRepo){
            if (!messageList.isEmpty()){
                if (messageList.getList().elementAt(0).getChatroomId().equals(message.getChatroomId())){

                }

            }
        }
        return this.getChatroomMessageRepo();
    }

    /**
     * 记录新的聊天室历史消息
     *
     * @param chatroomId  聊天室 Id
     * @param messageList 返回聊天室历史信息
     * @return
     */
    public Vector<MessageList> recordNewChatroomMessage(String chatroomId, MessageList messageList) {
        this.chatroomMessageRepo.add(messageList);
        return chatroomMessageRepo;
    }

// 不允许建立空表
//    public Vector<MessageList> addNewChatroomMessage(String chatroomId){
//        this.chatroomMessageRepo.add(new MessageList(chatroomId));
//        return chatroomMessageRepo;
//    }

    @Override
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        /*
        * TODO_LviatYi 接受消息操作
        * date 2021/6/11
        */
        chatroomGui.updateMessage(new Message(content,senderId,chatroomId,date));
        return false;
    }

    @Override
    public boolean receiver(Message message) {
        chatroomGui.updateMessage(message);
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendInfo friendInfo) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomInfo chatroomInfo) {
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
    public MessageList getChatroomMessageListLocal(String chatroomId) {
        for (MessageList messageList : chatroomMessageRepo) {
            if(messageList.getChatroomId().equals(chatroomId)){
                return messageList;
            }
        }
        return null;
    }

    public void setCurrentChatroomInfo(ChatroomInfo currentChatroomInfo) {
        this.currentChatroomInfo = currentChatroomInfo;
    }

    public ChatroomInfo getCurrentChatroomInfo() {
        return currentChatroomInfo;
    }
}
