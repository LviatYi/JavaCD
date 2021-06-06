package ChatRoom;

import java.util.Vector;

/**
 * 通讯录管理类
 * 单例模式
 *
 * @author May_bebe
 * @version TODO
 * @className AddressManager
 * @date 2021/6/6
 */
public class AddressManager {

    Vector<FriendInfo> friendList;
    /**
     * 添加好友状态
     */
    enum AddFriendStatus
    {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY,
        /**
         * 过长
         */
        TOO_LONG,
        /**
         * 过短
         */
        TOO_SHORT,
        /**
         * 好友已存在
         */
        EXISTED,
        /**
         * 无此ID
         */
        WRONG_ID
    }

    /**
     * 删除好友
     */
    enum DeleteFriendStatus
    {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 空
         */
        EMPTY,
        /**
         * 过长
         */
        TOO_LONG,
        /**
         * 过短
         */
        TOO_SHORT,
        /**
         * 无此好友ID
         */
        WRONG_ID
    }

    enum PrivateChatRoomStatus
    {
        /**
         * 合格
         */
        QUALIFIED,
        /**
         * 已存在
         */
        EXISTED,
    }
    FriendInfo friendInfo=new FriendInfo();
    ChatRoomInfo chatRoom=new ChatRoomInfo();


    /**
     * 最短 ID
     */
    final int ID_MIN = 6;
    /**
     * 最长 ID
     */
    final int ID_MAX = 10;
    /**
     * 单例指针
     */
    private static AddressManager instance=null;
    /**
     * 隐藏默认构造函数
     */
    private AddressManager()
    {
    }

    /**
     * 单例模式
     *
     * @return 通讯录权限管理器
     */
    public static AddressManager getAddressManager()
    {
        if(instance==null)
        {
            instance=new AddressManager();
        }
        return instance;
    }

    public boolean compareFriendId(String compareId)
    {
        if(true) {
            /**
             * TODO 数据库在该用户好友中找到相同id
             */
            return true;
        }
       return false;

    }

    /**
     * 检测添加好友合法性并尝试添加
     *
     * @param id 好友id
     *
     * @return 添加好友结果
     */
    public AddFriendStatus addFriend(String id)
    {
        if(id.length()==0)
        {
            return AddFriendStatus.EMPTY;
        }
        if(id.length()<ID_MIN)
        {
            return AddFriendStatus.TOO_SHORT;
        }
        if(id.length()>ID_MAX)
        {
            return AddFriendStatus.TOO_LONG;
        }
        for (FriendInfo info : friendList) {

            if (id.equals(info.getId())) {
                return AddFriendStatus.EXISTED;
            }
        }
        if(!compareFriendId(id))
        {
            return AddFriendStatus.WRONG_ID;
        }
        /**
         * TODO friendInfo.id and name ++++++++++
         */
        return AddFriendStatus.QUALIFIED;
    }

    /**
     * 检测删除好友合法性并尝试删除
     *
     * @param id 好友id
     *
     * @return 删除好友结果
     */
    public DeleteFriendStatus deleteFriend(String id)
    {
        if(id.length()==0)
        {
            return DeleteFriendStatus.EMPTY;
        }
        if(id.length()<ID_MIN)
        {
            return DeleteFriendStatus.TOO_SHORT;
        }
        if(id.length()>ID_MAX)
        {
            return DeleteFriendStatus.TOO_LONG;
        }
        for(int i = 0; i< friendList.size(); i++)
        {
            /**
             * TODO 向数据库要个该用户的所有好友id
             **/
            if(compareFriendId(id))
            {
                /**
                 * TODO friendInfo.id and name ----------
                 */
                return DeleteFriendStatus.QUALIFIED;
            }
        }
        return DeleteFriendStatus.WRONG_ID;
    }

    public boolean compareChatRoomId(String chatRoomId)
    {
        if(true)
        {
            /**
             * TODO 数据库找到该用户的现有聊天室列表与形参相同
             */
            return true;
        }
        return false;
    }
    /**
     * 创建私聊
     *
     * @param chatRoomId 聊天室id
     *
     * @return 创建私聊结果
     */
    public PrivateChatRoomStatus getPrivateChatRoom(String chatRoomId)
    {

        if(compareChatRoomId(chatRoomId))
        {
            return PrivateChatRoomStatus.EXISTED;
        }


        /**
         * TODO 添加一个PL？
         */
        /**
         * TODO chatRoom.id and title ++++++++++
         */
        return PrivateChatRoomStatus.QUALIFIED;
    }


    /**
     * 创建群聊获取新聊天室id
     *
     * @return 新聊天室id
     */
    public String getNewChatRoomId()
    {
        String newChatRoomId="";
        /**
         * TODO 向数据库请求新房间号id
         */
        return newChatRoomId;
    }

    public void setGroupChatRoom()
    {
        String id=getNewChatRoomId();
        /**
         * TODO chatRoom.id and title ++++++++++
         */
        /**
         * TODO 新加个PL？
         */
    }
}

