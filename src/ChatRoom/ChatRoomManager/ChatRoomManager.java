package ChatRoom.ChatRoomManager;

import ChatRoom.ChatRoomGui;
import ChatRoom.ChatRoomGuiControl;

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
    ChatRoomGui chatRoomGui;
    ChatRoomList chatRoomList;
    /**
     * 单例指针
     */
    private static ChatRoomManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private ChatRoomManager(ChatRoomGui parent) {
        this.chatRoomGui=parent;
        ChatRoomList serverChatRoomList = getServerChatRoomList();
        if (serverChatRoomList != null) {
            chatRoomList = serverChatRoomList;
        } else {
            chatRoomList = new ChatRoomList();
        }
    }

    /**
     * 单例模式
     *
     * @return 聊天室权限管理器
     */
    public static ChatRoomManager getChatRoomManager(ChatRoomGui parent) {
        if (instance == null) {
            instance = new ChatRoomManager(parent);
        }
        return instance;
    }

    private ChatRoomList getServerChatRoomList() {
        /*
         * TODO_LviatYi 向服务器请求该用户的聊天室列表
         * date 2021/6/7
         */
        return null;
    }

    /**
     * 加入聊天室
     *
     * @param chatRoomId 加入聊天室的 ChatRoomId
     * @return 加入聊天室状态.
     * 如果加入成功则返回 QUALIFIED.
     * 如果新建成功则返回 NEW.
     * 如果已经加入则返回 JOINED
     * 如果取消新建则返回 CANCEL.
     */
    public ChatRoomList.ChatRoomStatus join(String chatRoomId) {
        if (chatRoomList.findLocal(chatRoomId) != null) {
            //已加入
            return ChatRoomList.ChatRoomStatus.JOINED;
        } else {
            ChatRoomInfo chatRoomInfo = new ChatRoomInfo(null, null, null);
            /*
             * TODO_LviatYi 通知服务器查找聊天室.
             *  提供 ChatRoomId.
             *  返回聊天室信息.
             * date 2021/6/7
             */
            if (chatRoomInfo.getChatRoomId() == null) {
                if (chatRoomGui.confirmNewChatRoom()) {
                    chatRoomInfo = createChatRoom(chatRoomId, chatRoomGui.confirmChatRoomName(), ChatRoomInfo.ChatRoomType.PUBLIC);
                    if (chatRoomInfo == null) {
                        return ChatRoomList.ChatRoomStatus.ERROR;
                    }
                    return ChatRoomList.ChatRoomStatus.NEW;
                } else {
                    return ChatRoomList.ChatRoomStatus.CANCEL;
                }
            } else if (chatRoomInfo.getChatRoomType() == ChatRoomInfo.ChatRoomType.PRIVATE) {
                return ChatRoomList.ChatRoomStatus.PRIVATE;
            } else {
                chatRoomList.add(new ChatRoomInfo(chatRoomInfo));
                chatRoomGui.updateChatRoom();
                //加入成功
                return ChatRoomList.ChatRoomStatus.QUALIFIED;
            }
        }
    }

    /**
     * 创建一个聊天室.
     * 首先通知服务器.
     * 随后在本地创建.
     *
     * @param chatRoomId   ChatRoomId 当权限为私有时允许为空.
     * @param chatRoomName ChatRoomName 当权限为私有时允许为空.
     * @param chatRoomType ChatRoomType 不允许为空.扩展时需重构！需要将 ChatRoomType 更新为 Int 类型.
     * @return 返回聊天室信息.若新增失败则返回 null.
     */
    public ChatRoomInfo createChatRoom(String chatRoomId, String chatRoomName, ChatRoomInfo.ChatRoomType chatRoomType) {
        ChatRoomInfo chatRoomInfo = new ChatRoomInfo(chatRoomId, chatRoomName, chatRoomType);
        /*
         * TODO_LviatYi 通知服务器添加新的聊天室
         *  一定提供一个指定为私有权限的聊天室.
         *  若为私有，要求补全 ChatRoomId.且 ChatRoomName 可无视（允许为空）.
         *  最后请返回 ChatRoomInfo.
         * date 2021/6/7
         */
        if (chatRoomInfo.getChatRoomId() != null) {
            chatRoomList.add(chatRoomInfo);
        }
        return chatRoomInfo;
    }

    /**
     * 退出聊天室
     *
     * @param chatRoomId 退出聊天室的 ChatRoomId
     * @return 保证完全删除
     */
    public ChatRoomList.ChatRoomStatus delete(String chatRoomId) {
        chatRoomList.del(chatRoomId);
        chatRoomGui.updateChatRoom();
        return ChatRoomList.ChatRoomStatus.QUALIFIED;
    }

    /**
     * 根据好友关系 获取私人聊天室.
     * 若无私人聊天室则返回空.
     *
     * @param userId1 此用户的 Id
     * @param userId2 目标用户的 Id
     * @return 私人聊天室信息.
     * 若无则返回 null.
     */
    public ChatRoomInfo getPrivateChatRoom(String userId1, String userId2) {
        ChatRoomInfo chatRoomInfo = null;
        /*
         * TODO_LviatYi 通知服务器查找私聊聊天室信息
         * date 2021/6/9
         */
        if (chatRoomInfo != null) {
            chatRoomList.add(chatRoomInfo);
        }
        return chatRoomInfo;
    }

    /**
     * 获取 用户聊天室表
     *
     * @return chatRoomList
     */
    public ChatRoomList getChatRoomList() {
        return chatRoomList;
    }

    public boolean isEmpty() {
        if (this.getChatRoomList().getList().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}


