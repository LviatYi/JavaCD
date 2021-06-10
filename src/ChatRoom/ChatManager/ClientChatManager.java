package ChatRoom.ChatManager;

import ChatRoom.FriendManager.FriendInfo;

import java.util.Date;

/**
 * 向客户端发送消息
 *
 * @author LviatYi
 * @version 1.0
 * @interfaceName MessageSend
 * @date 2021/6/7
 */
public interface ClientChatManager {
    /**
     * 向对方线程上的客户端发送一条 Message
     * @param message 消息
     * @return 传输成功时 返回 true
     */
    boolean receiver(Message message);

    /**
     * 向对方线程上的客户端发送一条 Message
     * @param content 消息内容
     * @param senderId 发送方 Id
     * @param chatRoomId 聊天室 Id
     * @param date 日期
     * @return
     */
    boolean receiver(String content, String senderId, String chatRoomId, Date date);

}
