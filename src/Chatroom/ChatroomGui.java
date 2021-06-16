package Chatroom;

import Chatroom.ChatManager.ChatManager;
import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomManager.*;
import Chatroom.FriendManager.AddressManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Chatroom.SettingManager.SettingManager;
import Encrypt.Encryption;
import Socket.Client.ClientCommunication;
import Status.LoginStatus;
import Status.RegisterStatus;
import UserAuthenticate.UserAuthenticationGui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Jchat 聊天室 Gui.
 * Jchat 的主要 Gui.
 * 用于聊天、通讯管理、与个人信息设置.
 *
 * @author LviatYi
 * @version 1.6 alpha
 * @className ChatroomGui
 * @date 2021/6/4
 */
public class ChatroomGui extends JFrame implements ActionListener, FocusListener, ChatroomGuiControl, ClientManager {
    // Custom Panel

    /**
     * 聊天室信息面板.
     * 用于展示聊天室信息.
     */
    private class ChatroomPanel extends JPanel implements MouseListener {
        // Field

        final private ChatroomInfo chatroomInfo;
        final private JLabel nameLb;
        final private JLabel idLb;

        // Construct

        /**
         * @param chatroomInfo 聊天室信息
         */
        public ChatroomPanel(ChatroomInfo chatroomInfo) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.chatroomInfo = chatroomInfo;
            nameLb = new JLabel();
            idLb = new JLabel();
            this.add(nameLb);
            this.add(idLb);

            this.nameLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 16px;font-family: 'Trebuchet MS';\">\n" +
                    chatroomInfo.getChatroomName() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");
            this.idLb.setText("<html>\n" +
                    "    <body>\n" +
                    "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                    chatroomInfo.getChatroomId() +
                    "        </div>\n" +
                    "    </body>\n" +
                    "</html>\n");

            //修改样式
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            this.setBackground(Color.LIGHT_GRAY);
            this.addMouseListener(this);
        }

        // Getter Setter

        public ChatroomInfo getChatroomInfo() {
            return chatroomInfo;
        }
        // Interaction

        @Override
        public void mouseClicked(MouseEvent e) {
            //点击则进入该聊天室
            updateCurrentChatroom(this.chatroomInfo.getChatroomId(), false);
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
        // Field

        final private FriendInfo friendInfo;
        final private JLabel nameLb;
        final private JLabel idLb;

        // Construct

        /**
         * @param friendInfo 好友信息
         */
        public FriendPanel(FriendInfo friendInfo) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.friendInfo = friendInfo;
            nameLb = new JLabel();
            idLb = new JLabel();
            this.add(nameLb);
            this.add(idLb);

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

            //修改样式
            this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            this.setBackground(Color.LIGHT_GRAY);
            this.addMouseListener(this);
        }

        // Getter Setter

        public FriendInfo getFriendInfo() {
            return friendInfo;
        }

        // Interaction

        @Override
        public void mouseClicked(MouseEvent e) {
            //点击则进入私聊聊天室
            entryFriendChatroom(friendInfo.getFriendId());
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
        // Field

        Message message;

        // Gui Elements

        /**
         * 发送者 Pl，包含发送者信息
         */
        private JPanel senderInfoPl;
        private JPanel thisMsgPl;
        private JLabel senderLb;
        private JLabel sendTimeLb;

        /**
         * 信息内容 Pl 类，包含信息内容
         */
        private class MessageContentPanel extends JPanel {
            // Field

            private Image messagePanelBackgroundImg;
            private Document messageContent;
            private FontMetrics fontMetrics;
            private int messagePanelWidth = 200;
            private int messagePanelHeight = 40;

            // Constant

            final private int MAX_MESSAGE_PANEL_WIDTH = 350;

            /**
             * 用于填装信息文本的面板,自定义样式.
             */
            private class MessageContentTextPane extends JTextPane {
                //覆写默认构造函数
                public MessageContentTextPane() {
                    //背景色设置为透明，否则将不能加载背景图片
                    setOpaque(false);
                    //控制文本布局
                    this.setMargin(new Insets(0, 30, 0, 30));
                    this.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
                    this.setEditable(false);
                }

                //覆写绘制函数，以设置静态背景
                @Override
                public void paintComponent(Graphics g) {
                    //加载背景图
                    messagePanelBackgroundImg = new ImageIcon(getClass().getResource("./asset/JChatMessagePanelBg.png")).getImage();

                    //绘制背景图
                    g.drawImage(messagePanelBackgroundImg, 0, 0, getSize().width, getSize().height, this);
                    super.paintComponent(g);
                }
            }

            //用于显示文本的面板
            private MessageContentTextPane messageContentTp;

            //覆写默认构造函数
            public MessageContentPanel(String messageContent) {
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                this.messageContentTp = new MessageContentTextPane();
                this.messageContent = messageContentTp.getStyledDocument();

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

        // Construct

        public MessagePanel(Message message, boolean isSelf) {
            this.message = message;
            this.setLayout(new BorderLayout());
            thisMsgPl = new JPanel();
            thisMsgPl.setLayout(new BoxLayout(thisMsgPl, BoxLayout.Y_AXIS));
            senderInfoPl = new JPanel();
            senderInfoPl.setLayout(new BoxLayout(senderInfoPl, BoxLayout.X_AXIS));
            senderLb = new JLabel();
            sendTimeLb = new JLabel();
            messageContentPl = new MessageContentPanel(Encryption.decryptContent(this.message.getContent()));
            this.setPreferredSize(new Dimension(messageContentPl.getSize().width + 30, messageContentPl.getSize().height + 30));

            //Exist for DEBUG
//            this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//            thisMsgPl.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
//            senderPl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            senderLb.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//            messageContentPl.setBorder(BorderFactory.createLineBorder(Color.RED));
            //End

            //布局设置
            thisMsgPl.add(senderInfoPl);
            thisMsgPl.add(messageContentPl);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            if (isSelf) {
                senderInfoPl.add(sendTimeLb);
                senderInfoPl.add(senderLb);
                senderLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                        "            From me\n" +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n" +
                        "\n");
                sendTimeLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 10px; font-family: 'Trebuchet MS';color: gray;\">\n" +
                        formatter.format(message.getSendTime()) +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n");
                senderLb.setHorizontalAlignment(SwingConstants.RIGHT);
                this.add(thisMsgPl, BorderLayout.EAST);
            } else {
                senderInfoPl.add(senderLb);
                senderInfoPl.add(sendTimeLb);
                senderLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                        "           From " +
                        chatroomManager.getChatroom(message.getChatroomId()).getMember(message.getSenderId()).getFriendName() +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n" +
                        "\n");
                sendTimeLb.setText("<html>\n" +
                        "    <body>\n" +
                        "        <div style=\"font-size: 10px; font-family: 'Trebuchet MS';color: gray;\">\n" +
                        formatter.format(message.getSendTime()) +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>\n");
                senderLb.setHorizontalAlignment(SwingConstants.LEFT);
                this.add(thisMsgPl, BorderLayout.WEST);
            }
        }
    }

    // Field

    private UserAuthenticationGui userAuthenticationGui;
    private String newChatroomIdTmp;

    // Gui Elements

    private JPanel mainPl;
    private JPanel chatPl;
    private JPanel friendPl;
    private JPanel userInfoPl;
    private JPanel settingPl;
    private JPanel chatroomTitlePl;
    private JPanel chatroomPl;
    private JPanel historyMsgPl;
    private JPanel msgPl;
    private JPanel inputPl;
    private JPanel inputControlPl;
    private JPanel chatroomControlPl;
    private JPanel friendControlPl;
    private JPanel chatroomListPl;
    private JPanel friendListPl;
    private JPanel setNamePl;
    private JPanel setPasswordPl;
    private JPanel confirmPl;
    private JPanel setPassword2Pl;
    private JPanel leftContainerPl;
    private JLabel noMoreMsgLb;
    private JLabel nameStatusLb;
    private JLabel passwordStatusLb;
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
    private JButton addChatroomBtn;
    private JButton delChatroomBtn;
    private JButton confirmSetBtn;
    private JButton exitBtn;
    private JTextField chatroomIdTf;
    private JTextField friendIdTf;
    private JTextField nameTf;
    private JPasswordField passwordTf;
    private JPasswordField password2Tf;
    private JLabel chatroomListLoadingStatusLb;
    private JLabel addressListLoadingStatusLb;
    private JLabel chatroomTitleLb;
    private JTextPane inputTf;

    // Manager

    /**
     * 聊天室管理类
     */
    private ChatroomManager chatroomManager;
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
     * 通信管理类
     */
    private ClientCommunication clientCommunication;

    // Getter Setter

    public ClientCommunication getClientCommunication() {
        return clientCommunication;
    }

    public AddressManager getAddressManager() {
        return addressManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public ChatroomManager getChatroomManager() {
        return chatroomManager;
    }

    public SettingManager getSettingManager() {
        return settingManager;
    }

    // Display Text

    private String titleFrame = "Jchat" + " " + "Chatroom";
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
    private String confirmNewChatroomStr = "Do you want to create a NEW chat room?";
    private String confirmNewChatroomNameStr = "Confirm the name of the NEW chat room.";
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

    // Construct

    public ChatroomGui(String selfId, String selfName) {
        this.settingManager = SettingManager.getSettingManager(this, selfId, selfName);
        this.clientCommunication = ClientCommunication.getClientCommunicationInstance(this);
        this.clientCommunication.setParent1(this);
        this.clientCommunication.setSelfID(settingManager.getSelfId());
        this.chatroomManager = ChatroomManager.getChatroomManager(this);
        this.addressManager = AddressManager.getAddressManager(this);
        this.chatManager = ChatManager.getChatManager(this);

        prepareGui();
    }

    @Override
    protected void finalize() throws Throwable {
        clientCommunication.exit();
        super.finalize();
    }

    // Gui Prepare

    private void prepareGui() {
        //主窗体设置 标题 主布局 关闭事件 不允许控制窗口大小 设置大小 设置居中 设置可见性
        this.setTitle(titleFrame);
        this.setContentPane(mainPl);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(1200, 800));
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);

        chatroomListPl.setLayout(new BoxLayout(chatroomListPl, BoxLayout.Y_AXIS));
        friendListPl.setLayout(new BoxLayout(friendListPl, BoxLayout.Y_AXIS));
        msgPl.setLayout(new BoxLayout(msgPl, BoxLayout.Y_AXIS));

        /**
         * 加载提示文字
         */
        noMoreMsgLb.setText(noMoreMsgLbStr);
        setNamePl.setBorder(BorderFactory.createTitledBorder(setNameLbStr));
        setPasswordPl.setBorder(BorderFactory.createTitledBorder(setPasswordLbStr));
        setPassword2Pl.setBorder(BorderFactory.createTitledBorder(setPassword2LbStr));
        clearSettingPl();
        updateCurrentChatroom();
        updateMyInfoPl();

        addFriendBtn.setText(addBtnStr);
        delFriendBtn.setText(delBtnStr);
        addChatroomBtn.setText(addBtnStr);
        delChatroomBtn.setText(delBtnStr);
        moreMsgBtn.setText(moreMsgBtnStr);
        confirmSetBtn.setText(confirmSetBtnStr);
        sendMsgBtn.setText(sendMsgBtnStr);
        exitBtn.setText(exitBtnStr);


        // 添加侦听器
        addChatroomBtn.addActionListener(this);
        delChatroomBtn.addActionListener(this);
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
         * @className IntegerDocument
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

        chatroomIdTf.setDocument(new IntegerDocument());
        friendIdTf.setDocument(new IntegerDocument());

        prepareLoadingGui();

        this.setVisible(true);

        updateChatroomPl();
        updateFriendPl();

        //Exist for DEBUG
        updateChatPl(null);
        //End
        System.out.println("");
    }

    private void prepareLoadingGui() {
        chatroomListLoadingStatusLb.setText(loading);
        addressListLoadingStatusLb.setText(loading);
    }

    // Interaction

    @Override
    public void actionPerformed(ActionEvent event) {
        String input;
        switch (event.getActionCommand()) {
            case "addChatroom":
                input = chatroomIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (chatroomManager.join(input)) {
                    case QUALIFIED:
                        updateChatroomPl();
                        break;
                    case NEW:
                        updateChatroomPl();
                    case JOINED:
                        updateCurrentChatroom(input, false);
                        break;
                    case PRIVATE:
                        JOptionPane.showMessageDialog(this, "Access Denied : This chatroom is personal");
                        break;
                    case ERROR:
                    case CANCEL:
                    default:
                        break;
                }
                break;
            case "delChatroom":
                input = chatroomIdTf.getText();
                if ("".equals(input)) {
                    return;
                }
                switch (chatroomManager.exit(input)) {
                    case QUALIFIED:
                        updateCurrentChatroom();
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
                        updateCurrentChatroom(chatroomManager.getPrivateChatroom(settingManager.getSelfId(), input), false);
                        break;
                    case ADDED:
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
                        updateCurrentChatroom();
                        break;
                    case NOT_EXIST:
                    default:
                        break;
                }
                break;
            case "moreMsg":
                chatManager.pullChatroomMessageList(chatManager.getCurrentChatroomInfo().getChatroomId());
                updateChatPl(chatManager.getCurrentChatroomInfo().getChatroomId(), true);
                moreMsgBtn.setVisible(false);
                noMoreMsgLb.setVisible(true);
                break;
            case "sendMsg":
                if ("".equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
                    break;
                }
                sendMessage();
                break;
            case "exitRoom":
                if ("".equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
                    break;
                }
                chatroomManager.exit(chatManager.getCurrentChatroomInfo().getChatroomId());
                updateCurrentChatroom();
                break;
            case "confirmSet":
                if (settingManager.userAuthenticationManager.setNew()) {
                    clearSettingPl();
                    updateMyInfoPl();
                } else {
                    JOptionPane.showMessageDialog(this, "Unknown Error");
                }
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
                    passwordStatusLb.setText(qualifiedStatusStr);
                    break;
                //输入过长
                case TOO_LONG:
                    passwordStatusLb.setText(tooLongStatusStr);
                    break;
                //输入过短
                case TOO_SHORT:
                    passwordStatusLb.setText(tooShortStatusStr);
                    break;
                //输入过简
                case EASY:
                    passwordStatusLb.setText(easyStatusStr);
                    break;
                case EMPTY:
                default:
                    break;
            }
        } else if (event.getSource() == password2Tf) {
            checkTwicePasswordInput();
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

    // Function

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
     * 根据 本地缓存 刷新 聊天窗口 GUI.
     * 若聊天室信息为空则直接返回.
     *
     * @param chatroomId 需要的聊天室 ID
     */
    private void updateChatPl(String chatroomId, boolean isHistory) {
        msgPl.removeAll();

        if (chatroomId != null) {
            MessageList messageList= chatManager.getChatroomMessageListLocal(chatroomId);
            if (messageList==null){
                return;
            }
            for (Message message :messageList.getList()) {
                msgPl.add(new MessagePanel(message, message.getSenderId().equals(settingManager.getSelfId())));
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

            if (!isHistory) {
                //若非历史消息，则返回到底部.
                scrollToBottom(chatSp);
            }
            mainPl.updateUI();
        }
    }

    /**
     * 添加一条消息到 MsgPl 的最底部.
     *
     * @param message 待添加的消息.需要与当前所在的聊天室相同.
     */
    private void updateChatPl(Message message) {
        if (message != null && message.getChatroomId().equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
            msgPl.add(new MessagePanel(message, message.getSenderId().equals(settingManager.getSelfId())));

            pack();

            mainPl.updateUI();
            msgPl.revalidate();
            chatPl.revalidate();
            chatSp.revalidate();
            mainPl.revalidate();
            chatSp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            chatSp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            //若非历史消息，则返回到底部.
            scrollToBottom(chatSp);
            mainPl.updateUI();
        }
    }

    /**
     * 清空聊天页面
     */
    private void updateChatPl() {
        msgPl.removeAll();
    }

    /**
     * 从输入框获取并发送一条消息.
     * 并将此条消息更新至 MsgPl.
     */
    private void sendMessage() {
        Message message = new Message(Encryption.encryptContent(this.inputTf.getText()), settingManager.getSelfId(), chatManager.getCurrentChatroomInfo().getChatroomId());
        chatManager.send(message);
        this.inputTf.setText("");
        updateChatPl(message);
    }

    /**
     * 更新 当前聊天室信息 面板.
     *
     * @return 更改成功返回 true.若未更改则返回 false.
     */
    private boolean updateChatroomInfoPl() {
        String title = "<html>\n" +
                "    <body>\n" +
                "        <span style=\"font-size: 24px;font-family: 'Trebuchet MS';\">\n" +
                chatManager.getCurrentChatroomInfo().getChatroomName() +
                "        </span>\n" +
                "        <span style=\"font-size: 24px;font-family: 'Trebuchet MS';\">\n" +
                "|" +
                "        </span>\n" +
                "        <span style=\"font-size: 12px;font-family: 'Trebuchet MS';\">\n" +
                chatManager.getCurrentChatroomInfo().getChatroomId() +
                "        </span>\n" +
                "    </body>\n" +
                "</html>";

        chatroomTitleLb.setText(title);
        return true;
    }

    /**
     * 更新当前聊天界面到新的聊天室.
     * 同时会更改 ChatManager 中的当前聊天室 Info 与 Gui 的 chatPl 标题.
     *
     * @param chatroomInfo 一个本地存在的 chatroom 的 chatroomInfo.
     * @return 若成功则返回 true.若找不到相应聊天室则返回 false.
     */
    public boolean updateCurrentChatroom(ChatroomInfo chatroomInfo, boolean isHistory) {
        if (chatroomInfo.getChatroomId().equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
            return false;
        } else {
            moreMsgBtn.setVisible(true);
            noMoreMsgLb.setVisible(false);
            if (chatroomManager.getChatroomList().find(chatroomInfo.getChatroomId()) != null) {
                chatManager.setCurrentChatroomInfo(chatroomInfo);
                updateChatroomInfoPl();
                updateChatPl(chatroomInfo.getChatroomId(), isHistory);
                return true;
            } else {
                chatManager.setCurrentChatroomInfo(null);
                updateChatroomInfoPl();
                updateChatPl(null);
                return false;
            }
        }
    }

    /**
     * 更新当前聊天界面到新的聊天室.
     * 同时会更改 ChatManager 中的当前聊天室 Info 与 Gui 的 chatPl 标题.
     *
     * @param chatroomId 一个本地存在的 chatroom 的 chatroomId.
     * @return 若成功则返回 true.若找不到相应聊天室则返回 false.
     */
    public boolean updateCurrentChatroom(String chatroomId, boolean isHistory) {
        return updateCurrentChatroom(chatroomManager.getChatroom(chatroomId), isHistory);
    }

    /**
     * 清空 chatPl .
     * 同时会更改 chatPl 标题.
     *
     * @return 若成功则返回 true.
     */
    public boolean updateCurrentChatroom() {
        chatManager.setCurrentChatroomInfo(null);
        updateChatroomInfoPl();
        updateChatPl(null);
        return false;
    }

    /**
     * 更新用户信息
     */
    private void updateMyInfoPl() {
        userIdLb.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"font-size: 32px; font-family: 'Trebuchet MS'\">\n" +
                settingManager.getSelfId() +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");
        userNameLb.setText("<html>\n" +
                "    <body>\n" +
                "        <div style=\"font-size: 32px; font-family: 'Trebuchet MS'\">\n" +
                settingManager.getSelfName() +
                "        </div>\n" +
                "    </body>\n" +
                "</html>\n");
    }

    /**
     * 将 JScrollPane 控件下拉到底部.
     * 垃圾 swing .
     *
     * @param jScrollPane
     */
    private void scrollToBottom(JScrollPane jScrollPane) {
        JScrollBar verticalBar = jScrollPane.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    private void clearSettingPl() {
        nameTf.setText("");
        passwordTf.setText("");
        password2Tf.setText("");
        nameStatusLb.setText("");
        passwordStatusLb.setText("");
        password2StatusLb.setText("");
    }

    /**
     * 进入好友聊天室.
     * 若无聊天室则自动创建.
     *
     * @param friendId 好友 ID
     * @return 返回好友聊天室信息.若生成失败则返回 null.
     */
    private ChatroomInfo entryFriendChatroom(String friendId) {
        ChatroomInfo chatroomInfo = chatroomManager.getPrivateChatroom(settingManager.getSelfId(), friendId);
        if (chatroomInfo != null) {
            ChatroomInfo newPrivateChatroom=new ChatroomInfo("","", ChatroomInfo.ChatroomType.PRIVATE);
            clientCommunication.addChatRoom(newPrivateChatroom);
            chatroomManager.addChatroom(newPrivateChatroom);
            updateCurrentChatroom(chatroomInfo.getChatroomId(), false);
            return chatroomInfo;
        } else {
            return null;
        }
    }

    // Impl ChatroomGuiControl

    @Override
    public boolean confirmNewChatroom() {
        if (JOptionPane.showConfirmDialog(null, confirmNewChatroomStr, confirmStr, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            return true;
        }
        return false;
    }

    @Override
    public String confirmChatroomName() {
        return JOptionPane.showInputDialog(null, confirmNewChatroomNameStr, JOptionPane.OK_OPTION);
    }

    @Override
    public void updateChatroomPl() {
        chatroomListPl.removeAll();
        chatroomListPl.updateUI();
        try {
            ChatroomList chatroomList = chatroomManager.getChatroomList();
            for (ChatroomInfo chatroomInfo : chatroomList.getList()) {
                chatroomListPl.add(new ChatroomPanel(chatroomInfo));
            }
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(null, "You don't have any friends,loser.");
        }
        chatroomListPl.updateUI();
    }

    @Override
    public void updateFriendPl() {
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

        friendPl.updateUI();
    }

    @Override
    public void updateMessage(Message message) {
        if (!message.getChatroomId().equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
            updateChatPl(message);
        }
    }

    @Override
    public void updateMessage(ChatroomInfo chatroomInfo, boolean isHistory) {
        if (chatroomInfo != null && chatroomInfo.getChatroomId().equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
            updateChatPl(chatroomInfo.getChatroomId(), isHistory);
        }
    }

    @Override
    public void updateMessage(String chatroomId, boolean isHistory) {
        if (chatroomId.equals(chatManager.getCurrentChatroomInfo().getChatroomId())) {
            updateCurrentChatroom(chatroomId, isHistory);
        }
    }

    @Override
    public void updateMessage() {
        updateCurrentChatroom();
    }

    @Override
    public void updateUserInfo(String userName) {
        updateMyInfoPl();
    }

    // Impl ClientManager

    @Override
    @Deprecated
    public boolean receiver(Message message) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(MessageList messageList, boolean isHistory) {
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
    public boolean receiver(ChatroomInfo chatroomInfo, boolean isFocus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(FriendList friendList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(ChatroomList chatroomList) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(LoginStatus loginStatus) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(RegisterStatus registerStatus, String userId) {
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String userName) {
        return false;
    }

    @Override
    public boolean receiver(String chatroomId, boolean isNewChatroomId) {
        if (isNewChatroomId) {
            this.newChatroomIdTmp = chatroomId;
            chatroomIdTf.setText(newChatroomIdTmp);
        }
        return false;
    }
}
