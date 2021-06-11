package Chatroom.ChatManager;

import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import com.sun.istack.internal.NotNull;

import javax.print.DocFlavor;
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
    ChatroomGui parent;
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
    private static ChatManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private ChatManager() {
        currentChatroomInfo = new ChatroomInfo(null, null, null, null);
        chatroomMessageRepo = new Vector<MessageList>();
        chatroomList = new Vector<String>();
    }

    private ChatManager(@NotNull ChatroomGui parent) {
        this.parent = parent;
        currentChatroomInfo = new ChatroomInfo();
    }

    /**
     * 获取单例.
     *
     * @param parent 父级 Gui.
     * @return ChatManager
     */
    public static ChatManager getChatManager(@NotNull ChatroomGui parent) {
        if (instance == null) {
            instance = new ChatManager(parent);
        }
        return instance;
    }

    public void setCurrentChatroomInfo(ChatroomInfo currentChatroomInfo) {
        this.currentChatroomInfo = currentChatroomInfo;
    }
    public Vector<MessageList> getChatroomMessageRepo() {
        return chatroomMessageRepo;
    }
    public ChatroomInfo getCurrentChatroomInfo() {
        return currentChatroomInfo;
    }

    /**
     * 从服务器拉取聊天记录.
     *
     * @param chatroomId 聊天室 Id
     * @return 历史聊天记录组
     */
    public MessageList pullChatroomMessageList(String chatroomId) {
        MessageList historyMessageList = null;
        /*
         * TODO_LviatYi 向服务器索要聊天记录
         * date 2021/6/9
         */
        int index = chatroomList.indexOf(chatroomId);
        if (index != -1) {
            chatroomMessageRepo.elementAt(index).addHistoryMessage(historyMessageList);
        }
        return historyMessageList;
    }

    /**
     * 从服务器拉取聊天记录（异步模式）.
     * 受通讯系统限制.
     * 可能是危险的.
     *
     * @param chatroomId 聊天室 Id
     * @param isSingle   是否为异步模式.
     */
    public MessageList pullChatroomMessageList(String chatroomId, boolean isSingle) {
        if (isSingle) {
            /*
             * TODO_LviatYi 向服务器索要聊天记录
             * date 2021/6/9
             */
            return null;
        } else {
            return pullChatroomMessageList(chatroomId);
        }
    }

    /**
     * 向服务器发送 Message .
     *
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
     * 若本地聊天室无此已加入的聊天室的聊天记录，则新增至缓存.
     *
     * @param message Message
     * @return 对应的聊天室 Id.
     */
    public String recordNewMessage(Message message) {
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(message.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).addMessage(message);
                return message.getChatroomId();
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().findLocalChatroom(message.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
        }
        return null;
    }

    /**
     * 将来自外部的多条新消息加入到对应本地聊天室的聊天记录中.
     * 若本地聊天室无此已加入的聊天室的聊天记录，则新增至缓存.
     *
     * @param messageList 聊天记录表
     */
    public void recordNewMessage(MessageList messageList) {
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(messageList.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).addMessage(messageList);
                return ;
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().findLocalChatroom(messageList.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
        }
        for (Message message : messageList.getList()) {
            recordNewMessage(message);
        }
    }

    /**
     * 将来自外部的多条新消息覆写到对应本地聊天室的聊天记录中.
     * 若本地聊天室无此已加入的聊天室的聊天记录，则新增至缓存.
     * @param messageList 聊天记录表
     */
    public void refreshMessage(MessageList messageList){
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(messageList.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).clear();
                chatroomMessageRepo.elementAt(i).addMessage(messageList);
                return ;
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().findLocalChatroom(messageList.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
        }
        for (Message message : messageList.getList()) {
            recordNewMessage(message);
        }
    }

    /**
     * 获取本地聊天记录
     *
     * @param chatroomId 聊天室 Id
     * @return 聊天记录组
     */
    public MessageList getChatroomMessageListLocal(String chatroomId) {
        for (MessageList messageList : chatroomMessageRepo) {
            if (messageList.getChatroomId().equals(chatroomId)) {
                return messageList;
            }
        }
        return null;
    }

    /**
     * 根据 ChatroomId 新增聊天室与其记录至缓存.
     *
     * @param chatroomId  聊天室 Info
     * @param messageList 消息记录
     * @return 消息记录
     */
    public MessageList addNewChatroomRecord(String chatroomId, MessageList messageList) {
        chatroomList.add(chatroomId);
        chatroomMessageRepo.add(messageList);

        return messageList;
    }

    /**
     * 根据 ChatroomId 从缓存中删除聊天室与其记录.
     *
     * @param chatroomId 聊天室 Id
     * @return 成功删除返回 true . 未找到则返回 false
     */
    public boolean delChatroomRecord(String chatroomId) {
        int index = chatroomList.indexOf(chatroomId);
        if (index != -1) {
            chatroomList.remove(index);
            chatroomMessageRepo.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        /*
         * TODO_LviatYi 接受消息操作
         * date 2021/6/11
         */
        parent.updateMessage(new Message(content, senderId, chatroomId, date));
        return false;
    }

    @Override
    public boolean receiver(Message message) {
        parent.updateMessage(message);
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
}
