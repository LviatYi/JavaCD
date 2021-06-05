package ChatRoom;

import java.util.ArrayList;

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
    ArrayList existedFriend=new ArrayList();
    ArrayList allUserId=new ArrayList();
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
    private AddressManager instance=null;
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
    public AddressManager getAddressManager()
    {
        if(instance==null)
        {
            instance=new AddressManager();
        }
        return instance;
    }
    /**
     * 检测添加好友合法性并尝试添加
     *
     * @param id 好友id
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
        for(int i=0;i< existedFriend.size();i++)
        {
            if(id==existedFriend.get(i).toString())
            {
                return AddFriendStatus.EXISTED;
            }
        }
        for(int i=0;i<allUserId.size();i++)
        {
            if(id==allUserId.get(i).toString())
            {
                break;
            }
            return AddFriendStatus.WRONG_ID;
        }
        existedFriend.add(id);
        return AddFriendStatus.QUALIFIED;
    }

    /**
     * 检测删除好友合法性并尝试删除
     *
     * @param id 好友id
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
        for(int i=0;i< existedFriend.size();i++)
        {
            if(id==existedFriend.get(i).toString())
            {
                existedFriend.remove(i);
                return DeleteFriendStatus.QUALIFIED;
            }
        }
        return DeleteFriendStatus.WRONG_ID;

    }
}
