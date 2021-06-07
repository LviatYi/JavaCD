package ChatRoom.ChatRoomManager;

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
    ChatRoomList chatRoomList;
    ChatRoomGuiControl chatRoomGuiControl;
    /**
     * 单例指针
     */
    private static ChatRoomManager instance = null;

    /**
     * 隐藏默认构造函数
     */
    private ChatRoomManager() {
        getServerChatRoomList();
    }

    /**
     * 单例模式
     *
     * @return 聊天室权限管理器
     */
    public static ChatRoomManager getChatRoomManager() {
        if (instance == null) {
            instance = new ChatRoomManager();
        }
        return instance;
    }

    private void getServerChatRoomList(){
        /*
        * TODO_LviatYi 向服务器请求该用户的聊天室列表
        * date 2021/6/7
        */
    }

    /**
     * 加入聊天室
     * @param chatRoomId 加入聊天室的 ChatRoomId
     * @return 加入聊天室状态.
     *  如果加入成功则返回 QUALIFIED.
     *  如果新建成功则返回 NEW.
     *  如果取消新建则返回 CANCEL.
     */
    public ChatRoomList.ChatRoomStatus join(String chatRoomId) {
        if (chatRoomList.findLocal(chatRoomId) != null) {
            //已加入
            return ChatRoomList.ChatRoomStatus.JOINED;
        } else {
            ChatRoomInfo chatRoomInfo=new ChatRoomInfo(null ,null);
            /*
             * TODO_LviatYi 通知服务器查找聊天室
             * date 2021/6/7
             */
            //不存在聊天室
            if (chatRoomInfo.getChatRoomId()==null)
            {
                if (chatRoomGuiControl.confirmNewChatRoom()) {
                    String chatRoomName=chatRoomGuiControl.confirmChatRoomName();
                    /*
                     * TODO_LviatYi 确认聊天室名
                     * date 2021/6/7
                     */
                    /*
                     * TODO_LviatYi 通知服务器添加新的聊天室
                     * date 2021/6/7
                     */
                    chatRoomList.add(new ChatRoomInfo(chatRoomId,chatRoomName));
                    //新建聊天室
                    return ChatRoomList.ChatRoomStatus.NEW;
                }else
                {
                    return ChatRoomList.ChatRoomStatus.CANCEL;
                }
            }else{
                chatRoomList.add(new ChatRoomInfo(chatRoomInfo));
                return ChatRoomList.ChatRoomStatus.QUALIFIED;
            }
        }
    }

    /**
     * 退出聊天室
     * @param chatRoomId 退出聊天室的 ChatRoomId
     * @return 保证完全删除
     */
    public ChatRoomList.ChatRoomStatus delete(String chatRoomId){
        chatRoomList.del(chatRoomId);
        return ChatRoomList.ChatRoomStatus.QUALIFIED;
    }

    /**
     * 获取 用户聊天室表
     * @return chatRoomList
     */
    public ChatRoomList getChatRoomList(){
        return chatRoomList;
    }
}


