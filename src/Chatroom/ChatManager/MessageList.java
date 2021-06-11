package Chatroom.ChatManager;

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
    private String chatroomId;

    /**
     * 隐藏默认构造函数
     */
    private MessageList() {
        setList(new Vector<Message>());
    }

    public MessageList(String chatroomId) {
        setList(new Vector<Message>());
        this.setChatroomId(chatroomId);
    }

    public MessageList(String chatroomId, Vector<Message> list) {
        this.setChatroomId(chatroomId);
        this.setList(list);
    }

    public Vector<Message> getList() {
        return this.list;
    }

    String getChatroomId() {
        return this.chatroomId;
    }

    public void setList(Vector<Message> list) {
        this.list = list;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

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
        return null;
    }
    /**
     * 添加多条信息到 MessageList 的前端或末尾.
     *
     * @param messageList 待加入的多条信息.
     * @param isFront 是否插入到前端.
     * @return 插入成功的 ChatroomId.
     */
    String addMessage(MessageList messageList, boolean isFront) {
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
        if (chatroomMessageList.chatroomId.equals(chatroomId)) {
            addMessage(chatroomMessageList,true);
        }
        return this;
    }
    void clearMessage() {
        this.getList().clear();
    }

    boolean isEmpty() {
        return list.isEmpty();
    }


}
