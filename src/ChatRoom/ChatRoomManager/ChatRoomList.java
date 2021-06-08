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
        CANCEL
    }

    /**
     * 在缓存中按照 ChatRoomID 寻找 ChatRoom .
     *
     * @param chatRoomId ChatRoomID.
     * @return ChatRoomID, 找不到则返回空
     */
    public ChatRoomInfo findLocal(String chatRoomId) {
        for (ChatRoomInfo chatRoomInfo : list) {
            if (chatRoomInfo.getChatRoomId().equals(chatRoomId)) {
                return chatRoomInfo;
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

    public Vector<ChatRoomInfo> getList(){
        return this.list;
    }


}
