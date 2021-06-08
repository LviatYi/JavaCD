package ChatRoom.ChatManager;

import java.util.Date;

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
public class ChatManager implements ClientChatManager{
    @Override
    public boolean receiver(String content, String senderId, String chatRoomId, Date date) {
        return false;
    }

    @Override
    public boolean receiver(Message message) {
        return false;
    }


}
