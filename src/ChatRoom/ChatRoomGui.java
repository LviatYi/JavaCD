package ChatRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Jchat 聊天室 Gui.
 * Jchat 的主要 Gui.
 * 用于聊天、通讯管理、与个人信息设置.
 *
 * @author LviatYi
 * @version 1.0
 * @className ChatRoomGui
 * @date 2021/6/4
 */
public class ChatRoomGui extends JFrame implements ActionListener
{
    /*
     * TODO_LviatYi 添加聊天管理类接口
     * =JchatManager.getJchatManager();
     * date 2021/6/6
     */

    private class ChatRoomPanel extends JPanel{
        private String ChatRoomID;
    }

    /**
     * 聊天管理类
     */
    private ChatRoomManager chatRoomManager=ChatRoomManager.getChatRoomManager();
    /**
     * 通讯录管理类
     */
    private AddressManager addressManager=AddressManager.getAddressManager();
    /**
     *
     */
    private SettingManager settingManager=SettingManager.getSettingManager();

    /**
     * GUI 元素
     */
    private JPanel mainPl;
    private JPanel chatPl;
    private JPanel friendPl;
    private JPanel userInfoPl;
    private JPanel settingPl;
    private JPanel chatRoomTitlePl;
    private JPanel chatRoomPl;
    private JPanel historyMsgPl;
    private JPanel msgPl;
    private JPanel inputPl;
    private JPanel inputControlPl;
    private JPanel chatRoomControlPl;
    private JPanel chatRoomListPl;
    private JPanel friendListPl;
    private JPanel setNamePl;
    private JPanel setPasswordPl;
    private JPanel confirmPl;
    private JPanel setPassword2Pl;
    private JPanel leftContainerPl;
    private JPanel friendControlPl;
    private JLabel noMoreMsgLb;
    private JLabel setNameLb;
    private JLabel setPasswordLb;
    private JLabel setPassword2Lb;
    private JLabel setNameStatusLb;
    private JLabel setPasswordStatusLb;
    private JLabel setPassword2StatusLb;
    private JLabel userNameLb;
    private JLabel userIdLb;
    private JTabbedPane toolTabs;
    private JScrollPane friendSp;
    private JScrollPane chatSp;
    private JButton addFriendBtn;
    private JButton delFriendBtn;
    private JButton moreMsgBtn;
    private JButton sendMsgBtn;
    private JButton addChatRoomBtn;
    private JButton delChatRoomBtn;
    private JButton confirmSetBtn;
    private JButton exitBtn;
    private JTextField chatRoomIdTf;
    private JTextField friendIdTf;
    private JTextField setNameTf;
    private JPasswordField setPasswordTf;
    private JPasswordField setPassword2Tf;
    private JLabel chatRoomListLoadingStatusLb;
    private JLabel addressListLoadingStatusLb;

    /**
     * 窗口标题文本
     */
    private String titleFrame = "Jchat" + " " + "ChatRoom";
    private String loading = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            Loading...\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String addBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            Add\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String delBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            Delete\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String moreMsgBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            More history message...\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String sendMsgBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            Send\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String exitBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            Exit\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String confirmSetBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            Confirm\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";

    private String noMoreMsgLbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            No more history message.\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String setNameLbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            Update Name\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String setPasswordLbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            Update Password\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String setPassword2LbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
            "            Confirm again\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /*
     * TODO_LviatYi 用户名状态
     * date 2021/6/6
     */
    /*
     * TODO_LviatYi 密码状态
     * date 2021/6/6
     */

    /*
     * TODO_LviatYi UserId UserName 显示
     * date 2021/6/6
     */

    private String userNameLbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 18px;font-family: 'Trebuchet MS';\">\n" +
            "            Confirm again\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String userIdLbStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 18px;font-family: 'Trebuchet MS';\">\n" +
            "            Confirm again\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";


    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    /**
     * 覆写默认构造函数。
     */
    public ChatRoomGui(){
        prepareGui();
    }

    /**
     * 准备此次 Gui
     */
    private void prepareGui()
    {
        //主窗体设置 标题 主布局 关闭事件 不允许控制窗口大小 设置大小 设置居中 设置可见性
        this.setTitle(titleFrame);
        this.setContentPane(mainPl);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(1400, 800));
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);

        noMoreMsgLb.setText(noMoreMsgLbStr);
        setNameLb.setText(setNameLbStr);
        setPasswordLb.setText(setPasswordLbStr);
        setPassword2Lb.setText(setPassword2LbStr);
        setNameStatusLb.setText(setNameLbStr);
        setPasswordStatusLb.setText("");
        setPassword2StatusLb.setText("");
        userNameLb.setText(loading);
        userIdLb.setText(loading);

        addFriendBtn.setText(addBtnStr);
        delFriendBtn.setText(delBtnStr);
        addChatRoomBtn.setText(addBtnStr);
        delChatRoomBtn.setText(delBtnStr);
        moreMsgBtn.setText(moreMsgBtnStr);
        confirmSetBtn.setText(confirmSetBtnStr);
        sendMsgBtn.setText(sendMsgBtnStr);
        exitBtn.setText(exitBtnStr);

        prepareFirstGui();
    }

    private void prepareFirstGui()
    {
        chatRoomListLoadingStatusLb.setText(loading);
        addressListLoadingStatusLb.setText(loading);
        userNameLb.setText(loading);
        userIdLb.setText(loading);
    }
}
