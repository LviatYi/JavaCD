package ChatRoom;

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
    /**
     * 单例指针
     */
    private ChatRoomManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private ChatRoomManager()
    {
    }

    /**
     * 单例模式
     *
     * @return 聊天室权限管理器
     */
    public ChatRoomManager getChatRoomManager()
    {
        if(instance==null)
        {
            instance=new ChatRoomManager();
        }
        return instance;
    }
}
