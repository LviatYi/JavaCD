package ChatRoom;

import java.util.Vector;

/**
 * 聊天室管理类
 * 单例模式
 *
 * @author May_bebe
 * @version TODO
 * @className ChatRoomManager
 * @date 2021/6/6
 */
public class ChatRoomManager {
    Vector<ChatRoomInfo> chatRoomList;
    ChatRoomInfo chatRoom=new ChatRoomInfo();
    /**
     * 单例指针
     */
    private static ChatRoomManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private ChatRoomManager()
    {
    }
    public boolean getChatRoomInfo()
    {
        if(true)
        {
            /**
             * TODO 数据库快给我聊天室信息
             */
            return true;
        }
        return false;

    }
    /**
     * 单例模式
     *
     * @return 聊天室权限管理器
     */
    public static ChatRoomManager getChatRoomManager()
    {
        if(instance==null)
        {
            instance=new ChatRoomManager();
        }
        return instance;
    }

}
