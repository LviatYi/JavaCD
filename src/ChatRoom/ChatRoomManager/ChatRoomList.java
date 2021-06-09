package ChatRoom.ChatRoomManager;

import java.util.Vector;

/**
 * 缓存用户已加入的聊天室
 *
 * @author LviatYi
 * @version 1.0
 * @className ChatRoomList
 * @date 2021/6/7
 */
public class ChatRoomList {
    private Vector<ChatRoomInfo> list;

    public enum ChatRoomStatus {
        /**
         * 合格
         */
        QUALIFIED,
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

    /**
     * 在缓存中按照 ChatRoomID 寻找 ChatRoom .
     *
     * @param chatRoomId ChatRoomID.
     * @return ChatRoomID, 找不到则返回空
     */
    public ChatRoomInfo findLocal(String chatRoomId) {
        if(list.isEmpty()){
            return null;
        }
        for (ChatRoomInfo chatRoomInfo : list) {
            if (chatRoomInfo.getChatRoomId().equals(chatRoomId)) {
                return chatRoomInfo;
            }
        }
        return null;
    }

    /**
     * 在缓存中按照 好友关系 寻找 ChatRoom.
     *
     * @param userId1
     * @param userId2
     * @return ChatRoomInfo 若返回 null 则无好友聊天室.
     */
    public ChatRoomInfo findLocal(String userId1, String userId2) {
        for (ChatRoomInfo chatRoomInfo : list) {
            if (chatRoomInfo.getFriendList() == null) {
                ChatRoomInfo chatRoomInfoServer = new ChatRoomInfo(null);
                /*
                 * TODO_LviatYi 从服务器获得私聊聊天室
                 * date 2021/6/9
                 */
                return chatRoomInfoServer;
            } else {
                if (chatRoomInfo.getChatRoomType() == ChatRoomInfo.ChatRoomType.PRIVATE) {
                    if (chatRoomInfo.hasMember(userId1) && chatRoomInfo.hasMember(userId2)) {
                        return chatRoomInfo;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 在缓存中按照 ChatRoomName 寻找 ChatRoom .
     *
     * @param chatRoomName ChatRoomName.
     * @return ChatRoomID, 找不到则返回空
     */
    public ChatRoomInfo findLocal(String chatRoomName, boolean isName) {
        if (isName != true) {
            return null;
        }
        for (ChatRoomInfo chatRoomInfo : list) {
            if (chatRoomInfo.getChatRoomName().equals(chatRoomName)) {
                return chatRoomInfo;
            }
        }
        return null;
    }

    /**
     * 在服务器中按照 ChatRoomID 寻找 ChatRoom .
     *
     * @param chatRoomId ChatRoomID.
     * @return ChatRoomID, 找不到则返回空
     */
    public ChatRoomInfo findServer(String chatRoomId) {
        /*
         * TODO_LviatYi 向服务器按照 ChatRoomID 查询 ChatRoom
         * date 2021/6/7
         */
        return null;
    }

    /**
     * 在服务器中按照 ChatRoomName 寻找 ChatRoom .
     *
     * @param chatRoomName ChatRoomName.
     * @return ChatRoomID, 找不到则返回空
     */
    public ChatRoomInfo findServer(String chatRoomName, boolean isName) {
        if (isName != true) {
            return null;
        }
        /*
         * TODO_LviatYi 向服务器按照 ChatRoomName 查询 ChatRoom
         * date 2021/6/7
         */
        return null;
    }

    public String add(ChatRoomInfo chatRoomInfo) {
        list.add(0, chatRoomInfo);
        return chatRoomInfo.getChatRoomId();
    }

    public String del(String chatRoomId) {
        list.removeIf(chatRoomInfo -> chatRoomInfo.getChatRoomId().equals(chatRoomId));
        return chatRoomId;
    }

    public Vector<ChatRoomInfo> getList() {
        return this.list;
    }


}
