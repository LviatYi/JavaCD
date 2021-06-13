package Chatroom.ChatroomManager;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomGui;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.LoginStatus;
import Status.RegisterStatus;

import java.util.Date;

/**
 * 聊天室管理类
 * 单例模式
 *
 * @author LviatYi
 * @version 1.0
 * @className ChatroomManager
 * @date 2021/6/7
 */
public class ChatroomManager implements ClientManager {
    // Field
    
    ChatroomGui parent;
    ChatroomList chatroomList;
    
    // Construct
    
    /**
     * 单例指针
     */
    private static ChatroomManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private ChatroomManager(ChatroomGui parent) {
        this.parent = parent;
        ChatroomList serverChatroomList = getServerChatroomList();
        /*
        * TODO_LviatYi 向服务器索要 ChatroomList
        * date 2021/6/13
        */
        if (serverChatroomList != null) {
            chatroomList = serverChatroomList;
        } else {
            chatroomList = new ChatroomList();
        }
    }

    /**
     * 单例模式
     *
     * @return 聊天室权限管理器
     */
    public static ChatroomManager getChatroomManager(ChatroomGui parent) {
        if (instance == null) {
            instance = new ChatroomManager(parent);
        }
        return instance;
    }
    
    // Getter Setter

    private ChatroomList getServerChatroomList() {
        /*
         * TODO_LviatYi 向服务器请求该用户的聊天室列表
         * date 2021/6/7
         */
        return null;
    }

    // Function

    /**
     * 加入聊天室
     *
     * @param chatroomId 加入聊天室的 ChatroomId
     * @return 加入聊天室状态.
     * 如果加入成功则返回 QUALIFIED.
     * 如果新建成功则返回 NEW.
     * 如果已经加入则返回 JOINED
     * 如果取消新建则返回 CANCEL.
     */
    public ChatroomList.ChatroomStatus join(String chatroomId) {
        if (chatroomList.find(chatroomId) != null) {
            //已加入
            return ChatroomList.ChatroomStatus.JOINED;
        } else {
            ChatroomInfo chatroomInfo = new ChatroomInfo(null, null, null);
            /*
             * TODO_LviatYi 通知服务器查找聊天室.
             *  提供 ChatroomId.
             *  返回聊天室信息.
             * date 2021/6/7
             */
            if (chatroomInfo.getChatroomId() == null) {
                if (parent.confirmNewChatroom()) {
                    chatroomInfo = createChatroom(chatroomId, parent.confirmChatroomName(), ChatroomInfo.ChatroomType.PUBLIC);
                    if (chatroomInfo == null) {
                        return ChatroomList.ChatroomStatus.ERROR;
                    }else {
                        chatroomList.add(chatroomInfo);
                        parent.updateChatroom();
                        return ChatroomList.ChatroomStatus.NEW;
                    }
                } else {
                    return ChatroomList.ChatroomStatus.CANCEL;
                }
            } else if (chatroomInfo.getChatroomType() == ChatroomInfo.ChatroomType.PRIVATE) {
                return ChatroomList.ChatroomStatus.PRIVATE;
            } else {
                chatroomList.add(new ChatroomInfo(chatroomInfo));
                parent.updateChatroom();
                //加入成功
                return ChatroomList.ChatroomStatus.QUALIFIED;
            }
        }
    }

    /**
     * 向 ChatroomList 中添加一条 ChatroomInfo
     *
     * @param chatroomInfo 待添加的 ChatroomInfo
     * @return 添加完成的 ChatroomInfo
     */
    public ChatroomInfo addChatroom(ChatroomInfo chatroomInfo){
        return chatroomList.add(chatroomInfo);
    }

    /**
     * 从 ChatroomList 中删除一条指定 ID 的 ChatroomInfo
     * @param chatroomId 待删除的 Chatroom 的 ID
     * @return 删除状态
     */
    public boolean delChatroom(String chatroomId){
        return chatroomList.del(chatroomId);
    }

    /**
     * 创建一个聊天室.
     * 首先通知服务器.
     * 随后在本地创建.
     *
     * @param chatroomId   ChatroomId 当权限为私有时允许为空.
     * @param chatroomName ChatroomName 当权限为私有时允许为空.
     * @param chatroomType ChatroomType 不允许为空.扩展时需重构！需要将 ChatroomType 更新为 Int 类型.
     * @return 返回聊天室信息.若新增失败则返回 null.
     */
    public ChatroomInfo createChatroom(String chatroomId, String chatroomName, ChatroomInfo.ChatroomType chatroomType) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, chatroomName, chatroomType);
        /*
         * TODO_LviatYi 通知服务器添加新的聊天室
         *  一定提供一个指定为私有权限的聊天室.
         *  若为私有，要求补全 ChatroomId.且 ChatroomName 可无视（允许为空）.
         *  最后请返回 ChatroomInfo.
         * date 2021/6/7
         */
        if (chatroomInfo.getChatroomId() != null) {
            chatroomList.add(chatroomInfo);
        }
        return chatroomInfo;
    }

    /**
     * 退出聊天室
     *
     * @param chatroomId 退出聊天室的 ChatroomId
     * @return 保证完全删除
     */
    public ChatroomList.ChatroomStatus delete(String chatroomId) {
        chatroomList.del(chatroomId);
        parent.updateChatroom();
        return ChatroomList.ChatroomStatus.QUALIFIED;
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
    public ChatroomInfo getPrivateChatroom(String userId1, String userId2) {
        ChatroomInfo chatroomInfo = null;
        /*
         * TODO_LviatYi 通知服务器查找私聊聊天室信息
         * date 2021/6/9
         */
        if (chatroomInfo != null) {
            chatroomList.add(chatroomInfo);
        }
        return chatroomInfo;
    }

    /**
     * 获取 用户聊天室表
     *
     * @return chatroomList
     */
    public ChatroomList getChatroomList() {
        return chatroomList;
    }

    /**
     * 按照 ChatroomId 查找缓存中的聊天室信息.
     *
     * @param chatroomId 聊天室 Id.
     * @return chatroomInfo, 找不到则返回空.
     */
    public ChatroomInfo findLocalChatroom(String chatroomId) {
        return chatroomList.find(chatroomId);
    }

    /**
     * 检查缓存的聊天室列表是否有聊天室.
     *
     * @return 是则返回 true
     */
    public boolean isEmpty() {
        return this.getChatroomList().getList().isEmpty();
    }

//    /**
//     * 在服务器中按照 ChatroomID 寻找 Chatroom .
//     *
//     * @param chatroomId ChatroomID.
//     * @return ChatroomID, 找不到则返回空
//     */
//    public ChatroomInfo findServer(String chatroomId) {
//        /*
//         * TODO_LviatYi 向服务器按照 ChatroomID 查询 Chatroom
//         * date 2021/6/7
//         */
//        return null;
//    }
//
//    /**
//     * 在服务器中按照 ChatroomName 寻找 Chatroom .
//     *
//     * @param chatroomName ChatroomName.
//     * @return ChatroomID, 找不到则返回空
//     */
//    public ChatroomInfo findServer(String chatroomName, boolean isName) {
//        if (!isName) {
//            return null;
//        }
//        /*
//         * TODO_LviatYi 向服务器按照 ChatroomName 查询 Chatroom
//         * date 2021/6/7
//         */
//        return null;
//    }
//
//    public ChatroomInfo findServer(String userId1,String userId2){
//
//    }

    @Override
    @Deprecated
    public boolean receiver(Message message) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String content, String senderId, String chatroomId, Date date) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendInfo friendInfo) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendList friendList) {
        return false;
    }

    @Override
    public boolean receiver(ChatroomInfo chatroomInfo) {
        if (chatroomInfo !=null){
            ChatroomInfo localChatroom = findLocalChatroom(chatroomInfo.getChatroomId());
            if (localChatroom != null) {
                this.chatroomList.del(localChatroom.getChatroomId());
                this.chatroomList.add(chatroomInfo);
            }
            this.chatroomList.add(chatroomInfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean receiver(ChatroomList chatroomList) {
        return false;
    }
    
    @Override
    @Deprecated
    public boolean receiver(MessageList messageList, boolean isHistory) {
        return false;
    }
    
    @Override
    @Deprecated
    public boolean receiver(LoginStatus loginStatus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(RegisterStatus registerStatus) {
        return false;
    }
}


