package ChatRoom;

/**
 * 聊天室管理类
 * 单例模式
 *
 * @author LviatYi
 * @version 1.0
 * @className ChatRoomManager
 * @date 2021/6/7
 */
public class ChatRoomManager {
    /**
     * 单例指针
     */
    private static ChatRoomManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private ChatRoomManager(){}

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
