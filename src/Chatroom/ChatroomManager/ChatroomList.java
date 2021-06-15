package Chatroom.ChatroomManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 聊天室信息表.
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className ChatroomList
 * @date 2021/6/7
 */
public class ChatroomList {
    // Field

    private Vector<ChatroomInfo> list;


    // Construct

    /**
     * 默认构造函数.
     * list 设置为长度为 0 的空表.
     */
    public ChatroomList() {
        this.list = new Vector<ChatroomInfo>();
    }

    public ChatroomList(Vector<ChatroomInfo> list) {
        this.list = list;
    }

    // Getter Setter

    public Vector<ChatroomInfo> getList() {
        return this.list;
    }

    public void setList(Vector<ChatroomInfo> list) {
        this.list = list;
    }

    // Function

    /**
     * 在 list 中按照 ChatroomID 寻找 Chatroom .
     *
     * @param chatroomId ChatroomID.
     * @return ChatroomID, 找不到则返回空.
     */
    public ChatroomInfo find(String chatroomId) {
        if (list.isEmpty()) {
            return null;
        }
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.getChatroomId().equals(chatroomId)) {
                return chatroomInfo;
            }
        }
        return null;
    }

    /**
     * 在 list 中按照 好友关系 寻找私聊 Chatroom.
     *
     * @param userId1 User1 Id
     * @param userId2 User2 Id
     * @return ChatroomInfo. 若返回 null 则无好友聊天室.
     */
    public ChatroomInfo find(String userId1, String userId2) {
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.isPrivate()) {
                if (chatroomInfo.hasMember(userId1) && chatroomInfo.hasMember(userId2)) {
                    return chatroomInfo;
                }
            }
        }
        return null;
    }

    /**
     * 在 list 中按照 ChatroomName 寻找 Chatroom .
     *
     * @param giveString ChatroomName.
     * @return 符合条件的所有 ChatroomInfo, 找不到则返回长度为 0 的 list .
     */
    public ChatroomList find(String giveString, boolean isName) {
        ChatroomList chatroomList = new ChatroomList();
        if (!isName) {
            chatroomList.add(this.find(giveString));
            return chatroomList;
        }
        for (ChatroomInfo chatroomInfo : list) {
            if (chatroomInfo.getChatroomName().equals(giveString)) {
                chatroomList.add(chatroomInfo);
            }
        }
        return chatroomList;
    }

    /**
     * 向 list 的首部记录一条新的聊天室信息.
     * 重复添加以 ChatroomId 为主键的 ChatroomInfo 时,会删除旧的信息.
     *
     * @param chatroomInfo 待记录的聊天室信息.
     * @return chatroomInfo
     */
    public ChatroomInfo add(ChatroomInfo chatroomInfo) {
        if (chatroomInfo != null && !"".equals(chatroomInfo.getChatroomId())) {
            if (this.find(chatroomInfo.getChatroomId()) != null) {
                del(chatroomInfo.getChatroomId());
            }
            list.add(0, chatroomInfo);
            return chatroomInfo;
        } else {
            return null;
        }
    }

    /**
     * 按照 ChatroomId 删除 list 中的一条记录.
     *
     * @param chatroomId 目标 ChatroomId
     * @return 删除状态.
     */
    public boolean del(String chatroomId) {
        return list.removeIf(chatroomInfo -> chatroomInfo.getChatroomId().equals(chatroomId));
    }
}
