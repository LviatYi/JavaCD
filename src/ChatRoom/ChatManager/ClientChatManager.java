package ChatRoom.ChatManager;

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
    boolean receiver(String content, String senderId, String chatRoomId, Date date);
//    boolean addFriend();
}
