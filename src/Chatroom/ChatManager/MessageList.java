package Chatroom.ChatManager;

import java.util.Vector;

/**
 * 聊天室的聊天记录
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className MessageList
 * @date 2021/6/8
 */
public class MessageList {
    // Field

    /**
     * 用于记录聊天记录文本数据的 list.
     */
    private Vector<Message> list;
    /**
     * 其下数据所在的聊天室 ID.
     * 这意味着 MessageList 记录了来自同一聊天室的聊天记录.
     */
    private String chatroomId;

    // Construct

    /**
     * 隐藏默认构造函数
     */
    private MessageList() {
        this.list = new Vector<Message>();
        chatroomId = "";
    }

    /**
     * 规定 chatroomId 的初始化构造函数.
     *
     * @param chatroomId chatroomId
     */
    public MessageList(String chatroomId) {
        list = new Vector<Message>();
        this.chatroomId = chatroomId;
    }

    /**
     * 可直接通过一条 Message 判断信息的初始化构造函数.
     * 会将 Message 记录为第一条.
     * @param message 待记录信息.
     */
    public MessageList(Message message){
        list=new Vector<Message>();
        list.add(message);
        this.chatroomId=message.getChatroomId();
    }

    /**
     * 规定 chatroomId 且带有聊天记录的初始化构造函数.
     *
     * @param chatroomId ChatroomId
     * @param list       聊天记录
     */
    public MessageList(String chatroomId, Vector<Message> list) {
        this.setChatroomId(chatroomId);
        this.setList(list);
    }

    // Getter Setter

    public Vector<Message> getList() {
        return this.list;
    }

    public String getChatroomId() {
        return this.chatroomId;
    }

    public void setList(Vector<Message> list) {
        this.list = list;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    // Function

    /**
     * 添加信息到 MessageList 的末尾.
     *
     * @param message 待加入的信息.
     * @return 插入成功的 ChatroomId.
     */
    String addMessage(Message message) {
        return addMessage(message, false);
    }

    /**
     * 添加多条信息到 MessageList 的末尾.
     *
     * @param messageList 待加入的多条信息.
     * @return 插入成功的 ChatroomId.
     */
    String addMessage(MessageList messageList) {
        return addMessage(messageList, false);
    }

    /**
     * 添加信息到 MessageList 的前端或末尾.
     *
     * @param message 待加入的信息.
     * @param isFront 是否插入到前端.
     * @return 插入成功的 ChatroomId.
     */
    String addMessage(Message message, boolean isFront) {
        if (message != null) {
            if (isFront) {
                if (message.getChatroomId().equals(this.getChatroomId())) {
                    list.add(0, message);
                    return this.getChatroomId();
                }
            } else {
                if (message.getChatroomId().equals(this.getChatroomId())) {
                    list.add(message);
                    return this.getChatroomId();
                }
            }
        }
        return null;
    }

    /**
     * 添加多条信息到 MessageList 的前端或末尾.
     *
     * @param messageList 待加入的多条信息.
     * @param isFront     是否插入到前端.
     * @return 插入成功的 ChatroomId.
     */
    String addMessage(MessageList messageList, boolean isFront) {
        if (messageList != null) {
            if (isFront) {
                if (messageList.getChatroomId().equals(this.getChatroomId())) {
                    for (Message message : messageList.getList()) {
                        list.addAll(0, messageList.getList());
                    }
                    return this.getChatroomId();
                }
            } else {
                if (messageList.getChatroomId().equals(this.getChatroomId())) {
                    for (Message message : messageList.getList()) {
                        list.addAll(messageList.getList());
                    }
                    return this.getChatroomId();
                }
            }
        }
        return null;
    }

    /**
     * 加入历史记录.
     * 假设都早于现有记录，且按时间排序.
     *
     * @param chatroomMessageList 待插入的记录表.
     * @return 插入记录后的信息表.
     */
    public MessageList addHistoryMessage(MessageList chatroomMessageList) {
        if (chatroomMessageList != null) {
            if (chatroomMessageList.chatroomId.equals(chatroomId)) {
                addMessage(chatroomMessageList, true);
            }
            return this;
        }
        return null;
    }

    /**
     * 清空聊天记录.
     */
    void clear() {
        this.getList().clear();
    }

    /**
     * 检查此表是否为空.
     * @return 是否存在的状态.
     */
    boolean isEmpty() {
        return list.isEmpty();
    }
}
