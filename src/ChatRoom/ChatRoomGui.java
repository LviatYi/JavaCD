package ChatRoom;

import ChatRoom.ChatManager.ChatManager;
import ChatRoom.ChatManager.Message;
import ChatRoom.ChatRoomManager.*;
import ChatRoom.FriendManager.AddressManager;
import ChatRoom.FriendManager.FriendInfo;
import ChatRoom.FriendManager.FriendList;
import ChatRoom.SettingManager.SettingManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

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
public class ChatRoomGui extends JFrame implements ActionListener, FocusListener, ChatRoomGuiControl {
    //自定义面板

    /**
     * 聊天室信息面板.
     * 用于展示聊天室信息.
     */
    private class ChatRoomPanel extends JPanel implements MouseListener {
        @Override
        public void setBorder(Border border) {
            super.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }

        private ChatRoomInfo chatRoomInfo;
        private JLabel nameLb;
        private JLabel idLb;

        /**
         * @param chatRoomInfo 聊天室信息
         */
        public ChatRoomPanel(ChatRoomInfo chatRoomInfo) {

            this.chatRoomInfo = chatRoomInfo;
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(nameLb);
            this.add(idLb);

            this.chatRoomInfo.setChatRoomName(chatRoomInfo.getChatRoomName());
            this.chatRoomInfo.setChatRoomId(chatRoomInfo.getChatRoomId());

            this.nameLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomInfo.getChatRoomName() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.idLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomInfo.getChatRoomId() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");

            this.setBackground(Color.LIGHT_GRAY);
            this.addMouseListener(this);

        }

        public ChatRoomInfo getChatRoomInfo() {
            return chatRoomInfo;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            updateChatPl(getChatRoomInfo());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }


        @Override
        public void mouseEntered(MouseEvent e) {
            if (getVisibleRect().contains(e.getPoint())) {
                this.setBackground(new Color(250, 250, 250));
                super.paintChildren(super.getGraphics());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!getVisibleRect().contains(e.getPoint())) {
                this.setBackground(Color.LIGHT_GRAY);
                super.paintChildren(this.getGraphics());
            }

        }

    }

    /**
     * 好友信息面板.
     * 用于展示好友信息.
     */
    private class FriendPanel extends JPanel implements MouseListener {
        @Override
        public void setBorder(Border border) {
            super.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        }


        private FriendInfo friendInfo;
        private JLabel nameLb;
        private JLabel idLb;

        /**
         * @param friendInfo 好友信息
         */
        public FriendPanel(FriendInfo friendInfo) {
            nameLb = new JLabel();
            idLb = new JLabel();
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(nameLb);
            this.add(idLb);
            this.friendInfo = friendInfo;
            this.nameLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
                    this.friendInfo.getFriendName() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.idLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    this.friendInfo.getFriendId() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.setBackground(Color.LIGHT_GRAY);
            this.addMouseListener(this);
        }

        public void setFriendInfo(FriendInfo friendInfo) {
            this.friendInfo = friendInfo;
        }

        public FriendInfo getFriendInfo() {
            return friendInfo;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            entryFriendChatRoom(friendInfo.getFriendId());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (getVisibleRect().contains(e.getPoint())) {
                this.setBackground(new Color(250, 250, 250));
                super.paintChildren(super.getGraphics());
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!getVisibleRect().contains(e.getPoint())) {
                this.setBackground(Color.LIGHT_GRAY);
                super.paintChildren(this.getGraphics());
            }

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

            //Exist for DEBUG
//            this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//            thisMsgPl.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
//            senderPl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            senderLb.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//            messageContentPl.setBorder(BorderFactory.createLineBorder(Color.RED));
            //End

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
     * 聊天室管理类
     */
    private ChatRoomManager chatRoomManager;
    /**
     * 通讯录管理类
     */
    private AddressManager addressManager;
    /**
     * 设置管理类
     */
    private SettingManager settingManager;
    /**
     * 聊天管理类
     */
    private ChatManager chatManager;

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
    private JPanel friendControlPl;
    private JPanel chatRoomListPl;
    private JPanel friendListPl;
    private JPanel setNamePl;
    private JPanel setPasswordPl;
    private JPanel confirmPl;
    private JPanel setPassword2Pl;
    private JPanel leftContainerPl;
    private JLabel noMoreMsgLb;
    private JLabel nameStatusLb;
    private JLabel PasswordStatusLb;
    private JLabel password2StatusLb;
    private JLabel userNameLb;
    private JLabel userIdLb;
    private JTabbedPane toolTabs;
    private JScrollPane leftSp;
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
    private JTextField nameTf;
    private JPasswordField passwordTf;
    private JPasswordField password2Tf;
    private JLabel chatRoomListLoadingStatusLb;
    private JLabel addressListLoadingStatusLb;
    private JLabel chatRoomTitleLb;
    private JTextPane inputTf;

    /**
     * 附加线程
     */
    private LoadChatRoomThread loadChatRoomThread = new LoadChatRoomThread();
    private LoadFriendThread loadFriendThread = new LoadFriendThread();

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
    private String noChatRoomSelected = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
            "            No chat room selected\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>";
    /**
     * 正确状态文本
     */
    private String qualifiedStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(154, 205, 50)\">\n" +
            "            <div>Right</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 空状态文本
     */
    private String emptyStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: rgb(245, 66, 66);\">\n" +
            "            <div>\n" +
            "                Cannot be empty\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 过长状态文本
     */
    private String tooLongStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too long</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 过短状态文本
     */
    private String tooShortStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too short</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 过简状态文本
     */
    private String easyStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too easy</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 不同状态文本
     */
    private String differentStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>Two passwords are inconsistent</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";

    /**
     * 覆写默认构造函数。
     */
    public ChatRoomGui(String selfId, String selfName) {
        this.chatRoomManager = ChatRoomManager.getChatRoomManager();
        this.addressManager = AddressManager.getAddressManager();
        this.settingManager = SettingManager.getSettingManager(selfId, selfName);
        this.chatManager = ChatManager.getChatManager();

        this.settingManager.setSelfId(selfId);
        this.settingManager.setSelfName(selfName);

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
        chatRoomTitleLb.setText(noChatRoomSelected);
        noMoreMsgLb.setText(noMoreMsgLbStr);
        nameStatusLb.setText("");
        PasswordStatusLb.setText("");
        password2StatusLb.setText("");
        userNameLb.setText(settingManager.getSelfName());
        userIdLb.setText(settingManager.getSelfId());
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
        delChatRoomBtn.addActionListener(this);
        addFriendBtn.addActionListener(this);
        delFriendBtn.addActionListener(this);
        moreMsgBtn.addActionListener(this);
        sendMsgBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        confirmSetBtn.addActionListener(this);
        nameTf.addFocusListener(this);
        passwordTf.addFocusListener(this);
        password2Tf.addFocusListener(this);

        //添加快捷键
        inputTf.getInputMap().put(KeyStroke.getKeyStroke('\n', InputEvent.CTRL_MASK), "sendMsg");
        inputTf.getActionMap().put("sendMsg", sendMsg);


        noMoreMsgLb.setVisible(false);
        moreMsgBtn.setVisible(false);

        /**
         * Document:仅限数字
         */
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

        updateChatPl();
    }

    private void prepareLoadingGui() {
        chatRoomListLoadingStatusLb.setText(loading);
        addressListLoadingStatusLb.setText(loading);
    }

    private class LoadChatRoomThread extends Thread {
        @Override
        public void run() {
            super.run();
            updateChatRoomPl();
        }
    }

    private class LoadFriendThread extends Thread {
        @Override
        public void run() {
            super.run();
            updateFriendPl();
        }
    }


    /*
     * TODO_LviatYi 结束后需删除
     * date 2021/6/6
     */

    //Exist for DEBUG
    public static void main(String[] args) {
        ChatRoomGui chatRoomGui = new ChatRoomGui("123456", "Abc");
    }
    //End


    /**
     * action 触发事件
     *
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String input;
        switch (event.getActionCommand()) {
            case "addChatRoom":
                input = chatRoomIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (chatRoomManager.join(input)) {
                    case QUALIFIED:
                    case NEW:
                        updateChatRoom();
                    case JOINED:
                        updateCurrentChatRoom(input);
                        break;
                    case CANCEL:
                    default:
                        break;
                }
                break;
            case "delChatRoom":
                input = chatRoomIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (chatRoomManager.delete(input)) {
                    case QUALIFIED:
                        updateChatPl();
                        break;
                    default:
                        break;
                }
                break;
            case "addFriend":
                input = friendIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (addressManager.addFriend(input)) {
                    case QUALIFIED:
                    case ADDED:
                        updateChatPl(chatRoomManager.getPrivateChatRoom(settingManager.getSelfId(), input));
                        break;
                    default:
                        break;
                }
                break;
            case "delFriend":
                input = friendIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (addressManager.delFriend(input)) {
                    case QUALIFIED:
                        updateChatPl();
                        break;
                    case NOT_EXIST:
                    default:
                        break;
                }
                break;
            case "moreMsg":
                chatManager.pullChatRoomMessageList(chatManager.getCurrentChatRoomInfo().getChatRoomId());
                updateChatPl(chatManager.getCurrentChatRoomInfo());
                noMoreMsgLb.setVisible(true);
                break;
            case "sendMsg":
                if ("".equals(chatManager.getCurrentChatRoomInfo().getChatRoomId())) {
                    break;
                }
                sendMessage();
                break;
            case "exitRoom":
                if (chatManager.getCurrentChatRoomInfo().getChatRoomId().equals("")) {
                    break;
                }
                chatRoomManager.delete(chatManager.getCurrentChatRoomInfo().getChatRoomId());
                updateChatPl();
                break;
            default:
                break;
        }
    }

    @Override
    public void focusGained(FocusEvent event) {
        if (event.getSource() == nameTf) {
            nameTf.selectAll();
        } else if (event.getSource() == passwordTf) {
            passwordTf.selectAll();
        } else if (event.getSource() == password2Tf) {
            password2Tf.selectAll();
        }

    }

    @Override
    public void focusLost(FocusEvent event) {
        if (event.getSource() == nameTf) {
            switch (settingManager.userAuthenticationManager.setName(new String((nameTf.getText())))) {
                case QUALIFIED:
                    nameStatusLb.setText(qualifiedStatusStr);
                    break;
                case TOO_LONG:
                    nameStatusLb.setText(tooLongStatusStr);
                    break;
                case EMPTY:
                default:
                    break;

            }
        } else if (event.getSource() == passwordTf) {
            switch (settingManager.userAuthenticationManager.setPassword(new String(passwordTf.getPassword()))) {
                //password 输入正确
                case QUALIFIED:
                    checkTwicePasswordInput();
                    break;
                //输入过长
                case TOO_LONG:
                    PasswordStatusLb.setText(qualifiedStatusStr);
                    break;
                //输入过短
                case TOO_SHORT:
                    PasswordStatusLb.setText(tooShortStatusStr);
                    break;
                //输入过简
                case EASY:
                    PasswordStatusLb.setText(easyStatusStr);
                    break;
                case EMPTY:
                default:
                    break;
            }
        } else if (event.getSource() == password2Tf) {
            checkTwicePasswordInput();
        }
    }

    private void checkTwicePasswordInput() {
        if ((new String(password2Tf.getPassword())).equals(new String(passwordTf.getPassword()))) {
            //password2 输入一致
            password2StatusLb.setText(qualifiedStatusStr);
        } else {
            //password2 输入不一致
            password2StatusLb.setText(differentStatusStr);
        }
    }

    /**
     * 快捷键事件
     */
    private Action sendMsg = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
    };

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
    public void updateChatRoom() {
        LoadChatRoomThread loadChatRoomThread = new LoadChatRoomThread();
        loadChatRoomThread.start();
    }

    @Override
    public void updateFriend() {
        LoadFriendThread loadFriendThread = new LoadFriendThread();
        loadFriendThread.start();
    }

    @Override
    public void updateMessage() {
        updateChatPl();
    }

    /**
     * 根据 本地缓存 刷新 聊天窗口 GUI.
     * 若聊天室信息为空则直接返回.
     *
     * @param chatRoomInfo 需要的聊天室信息
     */
    private void updateChatPl(ChatRoomInfo chatRoomInfo) {

        //Exist for DEBUG
        msgPl.add(new MessagePanel("Hello World!\nHere's a MessagePanel for test.", true));
        //End

        if (chatRoomInfo == null) {
            return;
        }

        updateCurrentChatRoom(chatRoomInfo.getChatRoomId());
        for (Message message : chatManager.getChatRoomMessageList(chatRoomInfo.getChatRoomId()).getList()) {
            msgPl.add(new MessagePanel(message.getContent(), message.getSenderId().equals(settingManager.getSelfId())));
        }

        //Exist for COMPLAIN
        /**
         * 2021.06.07 1:30-2:20
         * 持续尝试解决控件不显示问题
         * 最终得知 Swing 的 Panel 不刷新 bug
         * 真他妈的气人
         * 特此留念
         */
        //End

        pack();

        mainPl.updateUI();
        msgPl.revalidate();
        chatPl.revalidate();
        chatSp.revalidate();
        mainPl.revalidate();
        chatSp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        chatSp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollToBottom(chatSp);
    }

    /**
     * 清空聊天页面
     */
    private void updateChatPl() {
        msgPl.removeAll();
    }

    private void sendMessage() {
        Message message = new Message(this.inputTf.getText(), settingManager.getSelfId(), chatManager.getCurrentChatRoomInfo().getChatRoomId());
        chatManager.send(message);
        this.inputTf.setText("");
        updateChatPl();
    }

    /**
     * 将 JScrollPane 控件下拉到底部.
     * 垃圾 swing ,并不能到最底部,比较看脸.
     *
     * @param jScrollPane
     */
    private void scrollToBottom(JScrollPane jScrollPane) {
        jScrollPane.revalidate();
        jScrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
    }

    /**
     * 更新当前聊天界面.
     * 同时会更改 chatPl 标题.
     *
     * @param chatRoomId 一个本地存在的 chatRoomId.
     * @return 若成功则返回 true.若找不到相应聊天室则返回 false.
     */
    private boolean updateCurrentChatRoom(String chatRoomId) {
        if (chatRoomManager.getChatRoomList().findLocal(chatRoomId) != null) {
            String title = "<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 24px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomManager.getChatRoomList().findLocal(chatRoomId).getChatRoomName() +
                    "        </div>\n" +
                    "        <div style=\"font-size: 24px;font-family: 'Trebuchet MS';\">\n" +
                    "            \" | \"\n" +
                    "        </div>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    chatRoomId +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>";

            chatRoomTitleLb.setText(title);
            updateChatPl();
            return true;
        } else {
            chatRoomTitleLb.setText("");
            updateChatPl(null);
            return false;
        }
    }

    /**
     * 根据 本地缓存 刷新 聊天室列表 GUI
     */
    private void updateChatRoomPl() {
        chatRoomListPl.removeAll();
        try {
            ChatRoomList chatRoomList = chatRoomManager.getChatRoomList();
            for (ChatRoomInfo chatRoomInfo : chatRoomList.getList()) {
                chatRoomListPl.add(new ChatRoomPanel(chatRoomInfo));
            }
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(null, "You don't have any friends,loser.");
        }

        chatRoomListPl.updateUI();
    }

    /**
     * 根据 本地缓存 刷新 好友列表 GUI
     */
    private void updateFriendPl() {
        friendListPl.removeAll();
        try {
            FriendList friendList = addressManager.getFriendList();
            for (FriendInfo friendInfo : friendList.getList()) {
                FriendPanel friendPanel = new FriendPanel(friendInfo);
                friendListPl.add(friendPanel);
            }
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(null, "You don't have any friends,loser.");
        }

        chatRoomListPl.updateUI();
    }

    /**
     * 进入好友聊天室.
     * 若无聊天室则自动创建.
     *
     * @param friendId 好友 ID
     * @return 返回好友聊天室信息.若生成失败则返回 null.
     */
    private ChatRoomInfo entryFriendChatRoom(String friendId) {
        ChatRoomInfo chatRoomInfo = chatRoomManager.getPrivateChatRoom(settingManager.getSelfId(), friendId);
        if (chatRoomInfo == null) {
            chatRoomInfo = chatRoomManager.createChatRoom(null, null, ChatRoomInfo.ChatRoomType.PRIVATE);
        }
        if (chatRoomInfo == null) {
            return null;
        } else {
            updateChatPl(chatRoomInfo);
            return chatRoomInfo;
        }

    }
}
