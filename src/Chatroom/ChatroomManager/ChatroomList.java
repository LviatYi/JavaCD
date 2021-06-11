package Chatroom.ChatroomManager;

import java.util.Vector;

/**
 * 缓存用户已加入的聊天室
 *
 * @author LviatYi
 * @version 1.0
 * @className ChatroomList
 * @date 2021/6/7
 */
public class ChatroomList {
    private Vector<ChatroomInfo> list;

    public enum ChatroomStatus {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 未知错误
         */
        ERROR,
        /**
         * 已加入
         */
        JOINED,
        /**
         * 待新建
         */
        NEW,
        /**
         * 不存在
         */
        NOT_EXIST,
        /**
         * 取消操作
         */
        CANCEL,
        /**
         * 私有聊天室
         */
        PRIVATE,
    }

    public Vector<ChatroomInfo> getList() {
        return this.list;
    }
    public void setList(Vector<ChatroomInfo> list) {
        this.list = list;
    }

    /**
     * 在缓存中按照 ChatroomID 寻找 Chatroom .
     *
     * @param chatroomId ChatroomID.
     * @return ChatroomID, 找不到则返回空
     */
    public ChatroomInfo find(String chatroomId) {
        if(list.isEmpty()){
            return null;
        }
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.getChatroomId().equals(chatroomId)) {
                return chatroomInfo;
            }
        }
        return null;
    }

    /**
     * 在缓存中按照 好友关系 寻找 Chatroom.
     *
     * @param userId1
     * @param userId2
     * @return ChatroomInfo 若返回 null 则无好友聊天室.
     */
    public ChatroomInfo find(String userId1, String userId2) {
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.getFriendList() == null) {
                ChatroomInfo chatroomInfoServer = new ChatroomInfo(null);
                /*
                 * TODO_LviatYi 从服务器获得私聊聊天室
                 * date 2021/6/9
                 */
                return chatroomInfoServer;
            } else {
                if (chatroomInfo.getChatroomType() == ChatroomInfo.ChatroomType.PRIVATE) {
                    if (chatroomInfo.hasMember(userId1)!=null && chatroomInfo.hasMember(userId2)!=null) {
                        return chatroomInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 在缓存中按照 ChatroomName 寻找 Chatroom .
     *
     * @param chatroomName ChatroomName.
     * @return ChatroomID, 找不到则返回空
     */
    public ChatroomInfo find(String chatroomName, boolean isName) {
        if (isName != true) {
            return null;
        }
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.getChatroomName().equals(chatroomName)) {
                return chatroomInfo;
            }
        }
        return null;
    }

    /**
     * 在服务器中按照 ChatroomID 寻找 Chatroom .
     *
     * @param chatroomId ChatroomID.
     * @return ChatroomID, 找不到则返回空
     */
    public ChatroomInfo findServer(String chatroomId) {
        /*
         * TODO_LviatYi 向服务器按照 ChatroomID 查询 Chatroom
         * date 2021/6/7
         */
        return null;
    }

    /**
     * 在服务器中按照 ChatroomName 寻找 Chatroom .
     *
     * @param chatroomName ChatroomName.
     * @return ChatroomID, 找不到则返回空
     */
    public ChatroomInfo findServer(String chatroomName, boolean isName) {
        if (isName != true) {
            return null;
        }
        /*
         * TODO_LviatYi 向服务器按照 ChatroomName 查询 Chatroom
         * date 2021/6/7
         */
        return null;
    }

    /**
     * 向聊天室列表记录一条新的聊天室信息.
     * @param chatroomInfo 待记录的聊天室信息.
     * @return chatroomInfo
     */
    public ChatroomInfo add(ChatroomInfo chatroomInfo) {
        if(chatroomInfo ==null){
            return null;
        }
        list.add(0, chatroomInfo);
        return chatroomInfo;
    }

    public String del(String chatroomId) {
        list.removeIf(chatroomInfo -> chatroomInfo.getChatroomId().equals(chatroomId));
        return chatroomId;
    }
}
