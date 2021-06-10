package ChatRoom.FriendManager;

/**
 * 向客户端发送通讯录相关消息
 *
 * @author LviatYi
 * @version TODO_LviatYi
 * @interfaceName ClientAddressManager
 * @date 2021/6/10
 */
public interface ClientAddressManager {
    /**
     * 向对方线程上的客户端发送一条被添加为好友的信息.
     * 同时会更新 GUI 中的好友列表.
     * @param friendInfo 好友信息
     * @return 好友添加状态
     */
    boolean addFriend(FriendInfo friendInfo);

    /**
     * 向对方线程上的客户端发送一条被添加为好友的信息.
     * 同时会更新 GUI 中的好友列表.
     * 不规范的，请使用 addFriend(FriendInfo friendInfo).
     * @param friendId 好友 Id
     * @param friendName 好友昵称
     * @return 好友添加状态
     */
    boolean addFriend(String friendId,String friendName);
}
