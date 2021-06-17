package Chatroom.ChatManager;

import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.LoginStatus;
import Status.RegisterStatus;

import java.util.Date;
import java.util.Vector;

/**
 * 聊天管理类.
 * 负责管理聊天界面与聊天信息缓存.
 * 单例模式.
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className ChatManager
 * @date 2021/6/7
 */
public class ChatManager implements ClientManager {
    // Field

    /**
     * 父级元素
     */
    private ChatroomGui parent;
    /**
     * 当前聊天页面的 ChatroomInfo
     */
    private ChatroomInfo currentChatroomInfo;
    /**
     * 缓存的聊天记录集合.
     * 与 chatroomList 长度相同.
     */
    private Vector<MessageList> chatroomMessageRepo;
    /**
     * 在缓存中的聊天记录的所有聊天室 Id.
     * 与 chatroomMessageRepo 长度相同.
     */
    private Vector<String> chatroomList;

    // Display Text

    private String noChatroomSelected = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            No chat room selected\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>";

    // Construct

    /**
     * 单例指针
     */
    private static ChatManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private ChatManager() {
        currentChatroomInfo = new ChatroomInfo("", noChatroomSelected, null, null);
        chatroomMessageRepo = new Vector<MessageList>();
        chatroomList = new Vector<String>();
    }

    private ChatManager(ChatroomGui parent) {
        this.parent = parent;
        currentChatroomInfo = new ChatroomInfo();
        chatroomMessageRepo = new Vector<MessageList>();
        chatroomList = new Vector<String>();
    }

    /**
     * 获取单例.
     *
     * @param parent 父级 Gui.
     * @return ChatManager
     */
    public static ChatManager getChatManager(ChatroomGui parent) {
        if (instance == null) {
            instance = new ChatManager(parent);
        }
        return instance;
    }

    // Getter Setter

    public ChatroomInfo getCurrentChatroomInfo() {
        return currentChatroomInfo;
    }

    public Vector<String> getChatroomList() {
        return chatroomList;
    }

    public ChatroomGui getParent() {
        return parent;
    }

    /**
     * 设置当前聊天页面的聊天室信息.
     * 当聊天页面发生聊天室更新时,请调用此函数.
     * 若当前聊天页面为空时,传入 null
     *
     * @param currentChatroomInfo 新的聊天室 Info
     */
    public void setCurrentChatroomInfo(ChatroomInfo currentChatroomInfo) {
        if (currentChatroomInfo == null) {
            this.currentChatroomInfo.setChatroomId("");
            this.currentChatroomInfo.setChatroomName(noChatroomSelected);
        } else {
            if (currentChatroomInfo.isPrivate()){
                for (FriendInfo friendInfo:currentChatroomInfo.getFriendList()){
                    if (friendInfo.getFriendId().equals(parent.getSettingManager().getSelfId())){
                        continue;
                    }
                this.currentChatroomInfo.setChatroomName(friendInfo.getFriendName());
                }
                this.currentChatroomInfo.setChatroomName("");
            }
            this.currentChatroomInfo.setChatroomId(currentChatroomInfo.getChatroomId());
        }
    }

    // Function

    /**
     * 从服务器拉取聊天记录.
     * 并尝试更新聊天界面.
     * 希望同步.
     *
     * @param chatroomId 聊天室 Id
     * @return 历史聊天记录组.拉取失败则返回 null.
     */
    public MessageList pullChatroomMessageList(String chatroomId) {
        MessageList historyMessageList = null;
        historyMessageList= parent.getClientCommunication().getHistoryMessage(chatroomId);
        if (historyMessageList != null) {
            recordNewMessage(historyMessageList);
            parent.updateMessage(parent.getChatroomManager().getChatroom(chatroomId), true);
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
            parent.getClientCommunication().getHistoryMessage(chatroomId);
            return null;
        } else {
            return pullChatroomMessageList(chatroomId);
        }
    }

    /**
     * 向服务器发送 Message .
     * 并尝试更新聊天界面.
     *
     * @param message Message
     * @return 发送状态.仅保证已发送.
     */
    public boolean send(Message message) {
        if (message == null|| "".equals(message.getChatroomId())) {
            return false;
        }
        parent.getClientCommunication().send(message);
        recordNewMessage(message);
        this.parent.updateMessage(message);
        return true;
    }

    /**
     * 将来自外部的新消息加入到聊天记录库中对应聊天室的记录表中.
     * Message 所附加的聊天室必须是用户已加入的聊天室.
     *
     * @param message Message
     * @return 对应的聊天室 Id.
     */
    public String recordNewMessage(Message message) {
        if (message == null) {
            return null;
        }
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(message.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).addMessage(message);
                return message.getChatroomId();
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().getChatroom(message.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
            chatroomMessageRepo.add(new MessageList(message));
            return newChatRoom.getChatroomId();
        }
        return null;
    }

    /**
     * 将来自外部的新消息加入到聊天记录库中对应聊天室的记录表中.
     * MessageList 所附加的聊天室必须是用户已加入的聊天室.
     *
     * @param messageList 聊天记录表
     * @return 对应的聊天室 Id.
     */
    public String recordNewMessage(MessageList messageList) {
        if (messageList == null) {
            return null;
        }
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(messageList.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).addMessage(messageList);
                return messageList.getChatroomId();
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().getChatroom(messageList.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
            chatroomMessageRepo.add(messageList);
            return newChatRoom.getChatroomId();
        }
        return null;
    }

    /**
     * 将来自外部的多条新消息覆写到对应本地聊天室的聊天记录中.
     * 若本地聊天室无此已加入的聊天室的聊天记录，则新增至缓存.
     *
     * @param messageList 聊天记录表
     * @return 对应的聊天室 Id.
     */
    public String refreshMessage(MessageList messageList) {
        for (int i = 0; i < chatroomList.size(); i++) {
            if (chatroomList.elementAt(i).equals(messageList.getChatroomId())) {
                chatroomMessageRepo.elementAt(i).clear();
                chatroomMessageRepo.elementAt(i).addMessage(messageList);
                return messageList.getChatroomId();
            }
        }
        ChatroomInfo newChatRoom = parent.getChatroomManager().getChatroom(messageList.getChatroomId());
        if (newChatRoom != null) {
            chatroomList.add(newChatRoom.getChatroomId());
            chatroomMessageRepo.add(messageList);
            return newChatRoom.getChatroomId();
        }
        return null;
    }

    /**
     * 获取指定聊天室的本地聊天记录.
     *
     * @param chatroomId 聊天室 Id
     * @return 聊天记录组.若无聊天记录则返回 null.
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
     * 根据 ChatroomId 从缓存中删除聊天室与其记录.
     *
     * @param chatroomId 聊天室 Id
     * @return 成功删除返回 true . 未找到则返回 false
     */
    public boolean delMessageRecord(String chatroomId) {
        int index = chatroomList.indexOf(chatroomId);
        if (index != -1) {
            chatroomList.remove(index);
            chatroomMessageRepo.remove(index);
            return true;
        }
        return false;
    }

    // Impl ClientManager

    @Override
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        if (content == null) {
            return false;
        }
        if (senderId == null) {
            return false;
        }
        if (chatroomId == null) {
            return false;
        }
        if (date == null) {
            return false;
        }
        return receiver(new Message(content, senderId, chatroomId, date));
    }

    @Override
    public boolean receiver(Message message) {
        if (message == null) {
            return false;
        }
        if (message.getSenderId().equals(parent.getSettingManager().getSelfId())){
            return true;
        }
        recordNewMessage(message);
        parent.updateMessage(message);
        return true;
    }

    @Override
    public boolean receiver(MessageList messageList, boolean isHistory) {
        if (messageList == null) {
            return false;
        }
        if (isHistory) {
            refreshMessage(messageList);
        } else {
           for(Message message:messageList.getList()){
               if (message.getSenderId().equals(parent.getSettingManager().getSelfId())){
                   return true;
               }
               recordNewMessage(message);
           }
        }
        parent.updateMessage(parent.getChatroomManager().getChatroom(messageList.getChatroomId()), isHistory);
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendInfo friendInfo) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomInfo chatroomInfo, boolean isFocus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendList friendList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomList chatroomList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(LoginStatus loginStatus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(RegisterStatus registerStatus,String userId) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String userName) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String chatroomId, boolean isNewChatroomId) {
        return false;
    }
}
