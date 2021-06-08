package ChatRoom;

import ChatRoom.ChatRoomManager.*;
import ChatRoom.FriendManager.AddressManager;
import ChatRoom.SettingManager.SettingManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
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
public class ChatRoomGui extends JFrame implements ActionListener, ChatRoom.ChatManager.ChatRoomGui {
    /**
     * 聊天室信息面板.
     * 用于展示聊天室信息.
     */
    private class ChatRoomPanel extends JPanel {
        @Override
        public void setBorder(Border border) {
            super.setBorder(BorderFactory.createEtchedBorder());
        }

        private String chatRoomId;
        private String chatRoomName;
        private JLabel nameLb;
        private JLabel idLb;

        /**
         * @param chatRoomId   聊天室 ID
         * @param chatRoomName 聊天室 Name
         */
        public ChatRoomPanel(String chatRoomId, String chatRoomName) {
            nameLb = new JLabel();
            idLb = new JLabel();
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(nameLb);
            this.add(idLb);
            this.chatRoomName = chatRoomName;
            this.chatRoomId = chatRoomId;
            this.nameLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomName +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.idLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomId +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
        }
    }

    /**
     * 好友信息面板.
     * 用于展示好友信息.
     */
    private class FriendPanel extends JPanel {
        @Override
        public void setBorder(Border border) {
            super.setBorder(BorderFactory.createEtchedBorder());
        }

        private String friendId;
        private String friendName;
        private JLabel nameLb;
        private JLabel idLb;

        /**
         * @param friendId   好友 ID
         * @param friendName 好友 Name
         */
        public FriendPanel(String friendId, String friendName) {
            nameLb = new JLabel();
            idLb = new JLabel();
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(nameLb);
            this.add(idLb);
            this.friendName = friendName;
            this.friendId = friendId;
            this.nameLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
                    friendName +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.idLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    friendId +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }

        public void setFriendName(String friendName) {
            this.friendName = friendName;
        }
    }

    /**
     * 聊天消息气泡
     */
    private class MessagePanel extends JPanel {
        /**
         * 信息内容 Pl 类，包含信息内容
         */
        private class MessageContentPanel extends JPanel {
            private Image messagePanelBackgroundImg;
            private Document messageContent;
            private FontMetrics fontMetrics;
            private int messagePanelWidth = 200;
            private int messagePanelHeight = 40;
            final private int MAX_MESSAGE_PANEL_WIDTH = 350;

            private class MessageContentTextPane extends JTextPane {
                public MessageContentTextPane() {
                    setOpaque(false);
                }

                @Override
                public void paintComponent(Graphics g) {
                    //加载背景图
                    messagePanelBackgroundImg = new ImageIcon(getClass().getResource("./asset/JChatMessagePanelBg.png")).getImage();

                    super.paintComponents(g);
                    g.drawImage(messagePanelBackgroundImg, 0, 0, getSize().width, getSize().height, this);

                    super.paintComponent(g);
                }
            }

            private MessageContentTextPane messageContentTp;

            public MessageContentPanel(String messageContent) {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                messageContentTp = new MessageContentTextPane();
                this.messageContent = messageContentTp.getStyledDocument();

                //控制文本布局
                messageContentTp.setMargin(new Insets(0, 30, 0, 30));
                messageContentTp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
                messageContentTp.setEditable(false);
                fontMetrics = messageContentTp.getFontMetrics(messageContentTp.getFont());
                int fontHeight = fontMetrics.getHeight();
                int lineWidth = 0;
                try {
                    for (int i = 0; i < messageContent.length(); i++) {
                        if (messageContent.charAt(i) == '\n' || (lineWidth += fontMetrics.charWidth(messageContent.charAt(i))) >= MAX_MESSAGE_PANEL_WIDTH) {
                            messagePanelWidth = Math.max(lineWidth, messagePanelWidth);
                            messagePanelHeight += fontHeight;
                            lineWidth = 0;
                            this.messageContent.insertString(this.messageContent.getLength(), "\n", new SimpleAttributeSet());
                        }
                        this.messageContent.insertString(this.messageContent.getLength(), String.valueOf(messageContent.charAt(i)), new SimpleAttributeSet());
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                messageContentTp.setDocument(this.messageContent);

                this.setMaximumSize(new Dimension(messagePanelWidth + 60, messagePanelHeight));

                this.add(messageContentTp);
            }
        }

        /**
         * 信息内容 Pl，包含信息内容
         */
        private MessageContentPanel messageContentPl;
        /**
         * 发送者 Pl，包含发送者信息
         */
        private JPanel senderPl;
        private JPanel senderDirPl;
        private JPanel thisMsgPl;
        private JLabel senderLb;

        public MessagePanel(String msgContent, boolean isSelf) {
            this.setLayout(new BorderLayout());
            thisMsgPl = new JPanel();
            thisMsgPl.setLayout(new BoxLayout(thisMsgPl, BoxLayout.Y_AXIS));
            senderPl = new JPanel();
            senderPl.setLayout(new BoxLayout(senderPl, BoxLayout.X_AXIS));
            senderLb = new JLabel();
            messageContentPl = new MessageContentPanel(msgContent);
            this.setPreferredSize(new Dimension(messageContentPl.getSize().width + 30, messageContentPl.getSize().height + 30));

//            //Border for Debug
//            this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//            thisMsgPl.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
//            senderPl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            senderLb.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//            messageContentPl.setBorder(BorderFactory.createLineBorder(Color.RED));

            //布局设置
            thisMsgPl.add(senderPl);
            thisMsgPl.add(messageContentPl);
            senderPl.add(senderLb);
            if (isSelf) {
                senderLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                        "            From me\n" +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n" +
                        "\n");
                senderLb.setHorizontalAlignment(SwingConstants.RIGHT);
                this.add(thisMsgPl, BorderLayout.EAST);

            } else {
                senderLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                        "           From" +/*
                 * TODO_LviatYi senderId
                 * date 2021/6/7
                 */
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n" +
                        "\n");
                senderLb.setHorizontalAlignment(SwingConstants.LEFT);
                this.add(thisMsgPl, BorderLayout.WEST);
            }

        }
    }

    /**
     * 聊天管理类
     */
    private ChatRoomManager chatRoomManager = ChatRoomManager.getChatRoomManager();
    /**
     * 通讯录管理类
     */
    private AddressManager addressManager = AddressManager.getAddressManager();
    /**
     * 设置管理类
     */
    private SettingManager settingManager = SettingManager.getSettingManager();

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
     * 附加线程
     */
    private LoadChatRoomThread loadChatRoomThread = new LoadChatRoomThread();
    private LoadFriendThread loadFriendThread = new LoadFriendThread();
    private LoadMessageThread loadMessageThread = new LoadMessageThread();

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
    private String confirmStr = "Please Confirm";
    private String confirmNewChatRoomStr = "Do you want to create a NEW chat room?";
    private String confirmNewChatRoomNameStr = "Confirm the name of the NEW chat room.";
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

    /**
     * 覆写默认构造函数。
     */
    public ChatRoomGui() {
        prepareGui();

    }

    /**
     * 准备此次 Gui
     */
    private void prepareGui() {
        //主窗体设置 标题 主布局 关闭事件 不允许控制窗口大小 设置大小 设置居中 设置可见性
        this.setTitle(titleFrame);
        this.setContentPane(mainPl);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(1200, 800));
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);

        chatRoomListPl.setLayout(new BoxLayout(chatRoomListPl, BoxLayout.Y_AXIS));
        friendListPl.setLayout(new BoxLayout(friendListPl, BoxLayout.Y_AXIS));
        msgPl.setLayout(new BoxLayout(msgPl, BoxLayout.Y_AXIS));

        /**
         * 加载提示文字
         */
        noMoreMsgLb.setText(noMoreMsgLbStr);
        setNameStatusLb.setText(setNameLbStr);
        setPasswordStatusLb.setText("");
        setPassword2StatusLb.setText("");
        userNameLb.setText(loading);
        userIdLb.setText(loading);
        setNamePl.setBorder(BorderFactory.createTitledBorder(setNameLbStr));
        setPasswordPl.setBorder(BorderFactory.createTitledBorder(setPasswordLbStr));
        setPassword2Pl.setBorder(BorderFactory.createTitledBorder(setPassword2LbStr));

        addFriendBtn.setText(addBtnStr);
        delFriendBtn.setText(delBtnStr);
        addChatRoomBtn.setText(addBtnStr);
        delChatRoomBtn.setText(delBtnStr);
        moreMsgBtn.setText(moreMsgBtnStr);
        confirmSetBtn.setText(confirmSetBtnStr);
        sendMsgBtn.setText(sendMsgBtnStr);
        exitBtn.setText(exitBtnStr);

        // 添加侦听器
        addChatRoomBtn.addActionListener(this);
        addFriendBtn.addActionListener(this);
        delChatRoomBtn.addActionListener(this);
        delFriendBtn.addActionListener(this);
        moreMsgBtn.addActionListener(this);
        sendMsgBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        confirmSetBtn.addActionListener(this);

        //限制 仅数字输入
        class IntegerDocument extends PlainDocument {
            @Override
            public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
                try {
                    Integer.parseInt(s);
                } catch (Exception ex) {
                    Toolkit.getDefaultToolkit().beep();
                    return;
                }
                super.insertString(offset, s, attributeSet);
            }
        }
        chatRoomIdTf.setDocument(new IntegerDocument());
        friendIdTf.setDocument(new IntegerDocument());

        prepareLoadingGui();

        this.setVisible(true);

        loadChatRoomThread.start();
        loadFriendThread.start();
        loadMessageThread.start();

    }

    private void prepareLoadingGui() {
        chatRoomListLoadingStatusLb.setText(loading);
        addressListLoadingStatusLb.setText(loading);
        userNameLb.setText(loading);
        userIdLb.setText(loading);
    }

    private class LoadChatRoomThread extends Thread {
        @Override
        public void run() {
            super.run();
            chatRoomListPl.removeAll();
            try{
                ChatRoomList chatRoomList = chatRoomManager.getChatRoomList();
                for (ChatRoomInfo chatRoomInfo : chatRoomList.getList()) {
                    chatRoomListPl.add(new ChatRoomPanel(chatRoomInfo.getChatRoomId(), chatRoomInfo.getChatRoomName()));
                }
            }catch (NullPointerException exception){
                JOptionPane.showMessageDialog(null,"You don't have any friends,loser.");
            }

            chatRoomListPl.updateUI();
        }
    }

    private class LoadFriendThread extends Thread {
        @Override
        public void run() {
            super.run();
            /*
             * TODO_LviatYi 加载 FriendList 并绘制 FriendListPanel
             * date 2021/6/6
             */
//            chatRoomListPl.add(new JButton("Hello"));
            chatRoomListPl.updateUI();
        }
    }

    private class LoadMessageThread extends Thread {
        @Override
        public void run() {
            super.run();
            /*
             * TODO_LviatYi 加载 new message
             * date 2021/6/6
             */
            msgPl.add(new MessagePanel("Hello World!AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", true));
            /**
             * 2021.06.07 1:30-2:20
             * 持续尝试解决控件不显示问题
             * 最终得知 Swing 的 Panel 不刷新 bug
             * 真他妈的气人
             * 特此留念
             */

            msgPl.updateUI();
        }
    }

    /*
     * TODO_LviatYi 结束后需删除
     * date 2021/6/6
     */

    /**
     * Exist for debug
     *
     * @param args 传入参数
     */
    public static void main(String[] args) {
        ChatRoomGui chatRoomGui = new ChatRoomGui();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "addChatRoom":
                if (chatRoomIdTf.getText().equals(new String(""))) {
                    return;
                }
                    switch (chatRoomManager.join(chatRoomIdTf.getText())){
                        case QUALIFIED:
                        case NEW:
                        case JOINED:

                        case CANCEL:
                            break;
                        case NOT_EXIST:
                        default:
                            break;
                    }
                break;
            case "delChatRoom":
                break;
            case "addFriend":
                break;
            case "delFriend":
                break;
        }
    }

    @Override
    public boolean confirmNewChatRoom() {
        if (JOptionPane.showConfirmDialog(null, confirmNewChatRoomStr, confirmStr, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            return true;
        }
        return false;
    }

    @Override
    public String confirmChatRoomName() {
        return JOptionPane.showInputDialog(null, confirmNewChatRoomNameStr, JOptionPane.OK_OPTION);
    }

    @Override
    public void updateChatRoomListPl() {
        LoadMessageThread loadMessageThread = new LoadMessageThread();
        loadMessageThread.start();
    }

    @Override
    public void updateFriendListPl() {
        LoadFriendThread loadFriendThread = new LoadFriendThread();
        loadFriendThread.start();
    }
}
