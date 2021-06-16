package Chatroom.ChatroomManager;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomGui;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Status.ChatroomStatus;
import Status.LoginStatus;
import Status.RegisterStatus;

import java.util.Date;
import java.util.Vector;

/**
 * 聊天室管理类
 * 单例模式
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className ChatroomManager
 * @date 2021/6/7
 */
public class ChatroomManager implements ClientManager {
    // Field

    ChatroomGui parent;
    /**
     * 缓存的本地聊天室列表
     */
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
        ChatroomList serverChatroomList = getChatroomListServer();
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

    /**
     * 获取 用户聊天室表
     *
     * @return chatroomList
     */
    public ChatroomList getChatroomList() {
        return chatroomList;
    }

    // Function

    /**
     * 向缓存的 ChatroomList 中添加一条 ChatroomInfo
     *
     * @param chatroomInfo 待添加的 ChatroomInfo
     * @return 添加完成的 ChatroomInfo
     */
    public ChatroomInfo addChatroom(ChatroomInfo chatroomInfo) {
        return chatroomList.add(chatroomInfo);
    }

    /**
     * 从缓存的 ChatroomList 中删除一条指定 ID 的 ChatroomInfo
     *
     * @param chatroomId 待删除的 Chatroom 的 ID
     * @return 删除状态
     */
    public boolean delChatroom(String chatroomId) {
        return chatroomList.del(chatroomId);
    }

    /**
     * 按照 ChatroomId 查找缓存中的聊天室信息.
     *
     * @param chatroomId 聊天室 Id.
     * @return chatroomInfo, 找不到则返回空.
     */
    public ChatroomInfo getChatroom(String chatroomId) {
        return chatroomList.find(chatroomId);
    }

    /**
     * 根据好友关系 从缓存的聊天室列表获取聊天室信息.
     * 若无私人聊天室则返回空.
     *
     * @param userId1 此用户的 Id
     * @param userId2 目标用户的 Id
     * @return 私人聊天室信息.
     * 若无则返回 null.
     */
    public ChatroomInfo getChatroom(String userId1, String userId2) {
        return chatroomList.find(userId1, userId2);
    }

    /**
     * 加入聊天室.
     * 希望同步.
     *
     * @param chatroomId 加入聊天室的 ChatroomId
     * @return 加入聊天室状态.
     * 如果加入成功则返回 QUALIFIED.
     * 如果新建成功则返回 NEW.
     * 如果已经加入则返回 JOINED
     * 如果取消新建则返回 CANCEL.
     */
    public ChatroomStatus join(String chatroomId) {
        if (chatroomList.find(chatroomId) != null) {
            //已加入
            return ChatroomStatus.JOINED;
        } else {
            ChatroomInfo chatroomInfo = new ChatroomInfo(null, null, null);
            chatroomInfo = getChatroomServer(chatroomId);
            if (chatroomInfo.getChatroomId() == null|| "".equals(chatroomInfo.getChatroomId())) {
                // 此 Chatroom 不存在
                if (parent.confirmNewChatroom()) {
                    String newChatroomName= parent.confirmChatroomName();
                    if (!"".equals(newChatroomName)){
                        //用户确认新建并命名
                        chatroomInfo = create(chatroomId,newChatroomName, ChatroomInfo.ChatroomType.PUBLIC);
                        if (chatroomInfo == null) {
                            return ChatroomStatus.ERROR;
                        } else {
                            return ChatroomStatus.NEW;
                        }
                    }else {
                        return ChatroomStatus.CANCEL;
                    }
                } else {
                    return ChatroomStatus.CANCEL;
                }
            } else if (chatroomInfo.getChatroomType() == ChatroomInfo.ChatroomType.PRIVATE) {
                return ChatroomStatus.PRIVATE;
            } else {
                chatroomList.add(new ChatroomInfo(chatroomInfo));
                parent.getClientCommunication().joinChatRoom(chatroomInfo);
                parent.updateChatroomPl();
                //加入成功
                return ChatroomStatus.QUALIFIED;
            }
        }
    }

    /**
     * 退出聊天室
     *
     * @param chatroomId 退出聊天室的 ChatroomId
     * @return 保证完全删除
     */
    public ChatroomStatus exit(String chatroomId) {
        delChatroom(chatroomId);
        parent.getClientCommunication().exitChatRoom(chatroomId);
        parent.updateChatroomPl();
        parent.updateMessage();
        return ChatroomStatus.QUALIFIED;
    }

    /**
     * 创建一个聊天室.
     * 首先通知服务器.
     * 随后在本地创建.
     * 希望同步.
     *
     * @param chatroomId   ChatroomId 当权限为私有时允许为空.
     * @param chatroomName ChatroomName 当权限为私有时允许为空.
     * @param chatroomType ChatroomType 不允许为空.扩展时需重构！需要将 ChatroomType 更新为 Int 类型.
     * @return 返回聊天室信息.若新增失败则返回 null.
     */
    public ChatroomInfo create(String chatroomId, String chatroomName, ChatroomInfo.ChatroomType chatroomType) {
        ChatroomInfo chatroomInfo = new ChatroomInfo(chatroomId, chatroomName, chatroomType);
        parent.getClientCommunication().addChatRoom(chatroomInfo);
        if (chatroomInfo.getChatroomId() != null) {
            chatroomInfo=getChatroomServer(chatroomId);
            chatroomList.add(chatroomInfo);
            parent.updateChatroomPl();
        }
        return chatroomInfo;
    }

    /**
     * 根据好友关系 从服务器获取聊天室信息.
     * 若无私人聊天室则返回空.
     * 希望同步.
     *
     * @param userId1 此用户的 Id
     * @param userId2 目标用户的 Id
     * @return 私人聊天室信息.
     * 若无则返回 null.
     */
    public ChatroomInfo getChatroomServer(String userId1, String userId2) {
        ChatroomInfo chatroomInfo = null;
        chatroomInfo = parent.getClientCommunication().findChatRoomInfo(userId1, userId2);
        return chatroomInfo;
    }

    /**
     * 根据 ChatroomID 从服务器获取聊天室信息.
     * 希望同步.
     *
     * @param chatroomId 此用户的 Id
     * @return 聊天室信息.
     * 若无则返回 null.
     */
    public ChatroomInfo getChatroomServer(String chatroomId) {
        ChatroomInfo chatroomInfo = null;
        chatroomInfo = parent.getClientCommunication().findChatRoomInfo(chatroomId);
        return chatroomInfo;
    }

    /**
     * 根据好友关系 获取私人聊天室信息.
     * 希望同步.
     *
     * @param userId1 此用户的 Id
     * @param userId2 目标用户的 Id
     * @return 私人聊天室信息.
     * 若无则返回 null.
     */
    public ChatroomInfo getPrivateChatroom(String userId1, String userId2) {
        ChatroomInfo chatroomInfo = getChatroom(userId1, userId2);
        if (chatroomInfo == null) {
            chatroomInfo = getChatroomServer(userId1, userId2);
            if (chatroomInfo==null||"".equals(chatroomInfo.getChatroomId())){
                Vector<FriendInfo> friendInfos=new Vector<FriendInfo>();
                friendInfos.add(new FriendInfo(userId1,""));
                friendInfos.add(new FriendInfo(userId2,""));
                chatroomInfo=new ChatroomInfo(parent.getNewChatroomIdTmp(),"", ChatroomInfo.ChatroomType.PRIVATE,friendInfos);
                parent.getClientCommunication().addChatRoom(chatroomInfo);
                chatroomInfo=getChatroomServer(userId1,userId2);
                addChatroom(chatroomInfo);
            }
        }
        return chatroomInfo;
    }

    /**
     * 从服务器拉取 该用户的 ChatroomList 列表.
     *
     * @return 该用户的聊天室列表.
     */
    private ChatroomList getChatroomListServer() {
        parent.getClientCommunication().getChatroomList();
        return null;
    }

    /**
     * 检查缓存的聊天室列表是否有聊天室.
     *
     * @return 是则返回 true
     */
    public boolean isEmpty() {
        return this.getChatroomList().getList().isEmpty();
    }

    // Impl ClientManager

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
    public boolean receiver(ChatroomInfo chatroomInfo, boolean isFocus) {
        if (chatroomInfo != null) {
            ChatroomInfo localChatroom = getChatroom(chatroomInfo.getChatroomId());
            if (localChatroom != null) {
                this.chatroomList.del(localChatroom.getChatroomId());
            }
            this.chatroomList.add(chatroomInfo);
            if (isFocus) {
                parent.updateCurrentChatroom(chatroomInfo, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean receiver(ChatroomList chatroomList) {
        if(chatroomList!=null){
            this.chatroomList=chatroomList;
        }
        parent.updateChatroomPl();
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
    public boolean receiver(RegisterStatus registerStatus,String userId) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String userName) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String chatroomId, boolean isNewChatroomId) {
        return false;
    }
}


