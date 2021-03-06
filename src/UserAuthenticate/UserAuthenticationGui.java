package UserAuthenticate;

import Chatroom.ChatManager.Message;
import Chatroom.ChatManager.MessageList;
import Chatroom.ChatroomGui;
import Chatroom.ChatroomManager.ChatroomInfo;
import Chatroom.ChatroomManager.ChatroomList;
import Chatroom.ClientManager;
import Chatroom.FriendManager.FriendInfo;
import Chatroom.FriendManager.FriendList;
import Socket.Client.ClientCommunication;
import Status.LoginStatus;
import Status.RegisterStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

/**
 * 用户认证系统 GUI.
 * 管理用户的注册与登录.
 *
 * @author LVIAT.cn
 * @version 1.6 alpha
 * @className UserAuthenticationGUI
 * @date 2021/6/2
 */
public class UserAuthenticationGui extends JFrame implements ActionListener, FocusListener, ClientManager {
    // Manager

    private UserAuthenticationManager userAuthenticationManager = UserAuthenticationManager.getUserAuthenticationManager(this);
    private ClientCommunication clientCommunication;
    private ChatroomGui parent;

    private String userNameTmp = "";

    // Gui Elements

    private JPanel mainPl;
    private JPanel titlePl;
    private JPanel bottomPl;
    private JPanel namePl;
    private JPanel passwordPl;
    private JPanel password2Pl;
    private JPanel inputPl;
    private JPanel controlPl;
    private JPanel idPl;
    private JLabel titleLb;
    private JLabel idStatusLb;
    private JLabel nameStatusLb;
    private JLabel passwordStatusLb;
    private JLabel password2StatusLb;
    private JTextField idTf;
    private JTextField nameTf;
    private JPasswordField passwordTf;
    private JPasswordField password2Tf;
    private JButton loginBtn;
    private JButton registerBtn;
    private JButton toRegisterBtn;
    private JButton toLoginBtn;

    // Getter Setter

    public ClientCommunication getClientCommunication() {
        return this.clientCommunication;
    }

    // Display Text

    /**
     * 版本号
     */
    private String version = "1.60 alpha";
    /**
     * 窗口标题文本
     */
    private String titleFrame = "Jchat" + " " + "V" + version;
    /**
     * 界面标题文本 登录
     */
    private String titleLogin = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 24px; font-family:'Trebuchet MS'\">\n" +
            "            <div>\n" +
            "                JChat 2021 | Login\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 界面标题文本 注册
     */
    private String titleRegister = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 24px; font-family:'Trebuchet MS'\">\n" +
            "            <div>\n" +
            "                JChat 2021 | Register\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * ID 输入框提示与输入框默认文本
     */
    private String idInputStr = "User ID";
    /**
     * User name 输入框提示与输入框默认文本
     */
    private String nameInputStr = "User name";
    /**
     * Password 输入框提示文本
     */
    private String passwordInputStr = "Password";
    /**
     * Password2 输入框提示与输入框默认文本
     */
    private String password2InputStr = "Password again";
    /**
     * 切换到注册按钮文本
     */
    private String changeToRegisterBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: 0080ff;\">\n" +
            "            <div>\n" +
            "                Register now!\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 切换到登录按钮文本
     */
    private String changeToLoginBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: 0080ff;\">\n" +
            "            <div>\n" +
            "                Already have an account...\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 登录按钮文本
     */
    private String loginBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px; font-family:'Trebuchet MS';\">\n" +
            "            <div>\n" +
            "                Login\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    /**
     * 注册按钮文本
     */
    private String registerBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px; font-family:'Trebuchet MS';\">\n" +
            "            <div>\n" +
            "                Register\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
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
     * 不能连接到服务器警告文本
     */
    private String connectionErrorStr = "Can't connect to server!";
    /**
     * 不能连接到服务器建议文本
     */
    private String connectionErrorAdviceStr = "Please check the connection status.";
    /**
     * 账号不存在警告文本
     */
    private String idNotExistStr = "ID not exist!";
    /**
     * 账号不存在建议文本
     */
    private String idNotExistAdviceStr = "Please check the id you entered.";
    /**
     * 密码错误警告文本
     */
    private String passwordWrongStr = "Password is wrong";
    /**
     * 密码错误建议文本
     */
    private String passwordWrongAdviceStr = "Please check the password you entered.";
    /**
     * 等待文本
     */
    private String pleaseWaitStr = "Please wait...";

    // Function

    /**
     * 更新 认证 按钮可用状态
     */
    private void updateAuthenticationBtn() {
        updateRegisterBtn();
        updateLoginBtn();
    }

    /**
     * 更新 注册 按钮可用状态
     */
    private void updateRegisterBtn() {
        if (registerBtn.isVisible()) {
            registerBtn.setEnabled(nameStatusLb.getText().equals(qualifiedStatusStr) && passwordStatusLb.getText().equals(qualifiedStatusStr) && password2StatusLb.getText().equals(qualifiedStatusStr));
        }
    }

    /**
     * 更新 登录 按钮可用状态
     */
    private void updateLoginBtn() {
        if (loginBtn.isVisible()) {
            loginBtn.setEnabled(idStatusLb.getText().equals(qualifiedStatusStr) && passwordStatusLb.getText().equals(qualifiedStatusStr));
        }
    }

    public String getUserId() {
        return userAuthenticationManager.getUserId();
    }

    public String getUserName() {
        return userAuthenticationManager.getUserName();
    }

    /**
     * 进入主界面
     */
    private void entryMainWindow() {
        ChatroomGui chatroomGui = new ChatroomGui(this.getUserId(), "".equals(userNameTmp) ? this.getUserName() : userNameTmp);
        this.dispose();
    }

    // Construction

    /**
     * 覆写默认构造函数。
     */
    public UserAuthenticationGui() {
        this.clientCommunication = ClientCommunication.getClientCommunicationInstance(this);
        prepareGui();
    }

    // Gui Prepare

    /**
     * 准备此次 Gui
     */
    private void prepareGui() {
        //主窗体设置 标题 主布局 关闭事件 不允许控制窗口大小 设置大小 设置居中 设置可见性
        this.setTitle(titleFrame);
        this.setContentPane(mainPl);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(640, 480);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);

        //设置窗口标题文字
        titleLb.setText(titleLogin);
        //准备默认提示文字
        this.idTf.setText(idInputStr);
        this.nameTf.setText(nameInputStr);
        this.passwordTf.setText(password2InputStr);
        this.password2Tf.setText(password2InputStr);
        //设置按钮文字
        this.toRegisterBtn.setText(changeToRegisterBtnStr);
        this.toLoginBtn.setText(changeToLoginBtnStr);
        this.loginBtn.setText(loginBtnStr);
        this.registerBtn.setText(registerBtnStr);

        //添加键绑定 使输入可以以回车 <code>\n<\code> 结束
        idTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setId");
        idTf.getActionMap().put("setId", setId);
        nameTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setName");
        nameTf.getActionMap().put("setName", setName);
        passwordTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setPassword");
        passwordTf.getActionMap().put("setPassword", setPassword);
        password2Tf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setPassword2");
        password2Tf.getActionMap().put("setPassword2", setPassword2);
        mainPl.getInputMap().put(KeyStroke.getKeyStroke('\n'), "toControlBtn");
        mainPl.getActionMap().put("toControlBtn", toControlBtn);
        loginBtn.getInputMap().put(KeyStroke.getKeyStroke('\n'), "login");
        loginBtn.getActionMap().put("login", login);
        registerBtn.getInputMap().put(KeyStroke.getKeyStroke('\n'), "register");
        registerBtn.getActionMap().put("register", register);

        //添加监听器
        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        toLoginBtn.addActionListener(this);
        toRegisterBtn.addActionListener(this);
        idTf.addFocusListener(this);
        nameTf.addFocusListener(this);
        passwordTf.addFocusListener(this);
        password2Tf.addFocusListener(this);

        //准备第一幕
        prepareFirstGui();
    }

    /**
     * 准备第一幕
     */
    private void prepareFirstGui() {
        idTf.selectAll();
        loginBtn.setEnabled(false);
        registerBtn.setEnabled(false);
        nameTf.setVisible(false);
        nameStatusLb.setVisible(false);
        password2Tf.setVisible(false);
        password2StatusLb.setVisible(false);
        registerBtn.setVisible(false);
        toLoginBtn.setVisible(false);
    }

    // Exist for DEBUG
    public static void main(String[] args) {
        UserAuthenticationGui uam = new UserAuthenticationGui();
    }
    // End

    // Interaction

    private Action setId = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordTf.requestFocus();
            passwordTf.selectAll();
            updateAuthenticationBtn();
        }
    };
    private Action setName = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            passwordTf.requestFocus();
            passwordTf.selectAll();
            updateAuthenticationBtn();
        }
    };
    private Action setPassword = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginBtn.isVisible()) {
                mainPl.requestFocus();
                updateAuthenticationBtn();
            } else {
                password2Tf.requestFocus();
                password2Tf.selectAll();
                updateAuthenticationBtn();
            }
        }
    };
    private Action setPassword2 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPl.requestFocus();
            updateAuthenticationBtn();
        }
    };

    private Action toControlBtn = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (loginBtn.isVisible()) {
                loginBtn.requestFocus();
            } else if (registerBtn.isVisible()) {
                registerBtn.requestFocus();
            }
        }
    };
    private Action login = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginBtn.doClick();
        }
    };
    private Action register = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            registerBtn.doClick();
        }
    };

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            //登录按钮
            case "login":
                loginBtn.setEnabled(false);
                toRegisterBtn.setEnabled(false);
                loginBtn.setText(pleaseWaitStr);
                userAuthenticationManager.login();
                break;
            //注册按钮
            case "register":
                registerBtn.setEnabled(false);
                toLoginBtn.setEnabled(false);
                registerBtn.setText(pleaseWaitStr);
                userAuthenticationManager.register();
                break;
            //转到登录按钮
            case "toLogin":
                titleLb.setText(titleLogin);
                idStatusLb.setText("");
                idTf.setText(idInputStr);
                passwordStatusLb.setText("");
                passwordTf.setText(password2InputStr);

                nameTf.setVisible(false);
                nameStatusLb.setVisible(false);
                idTf.setVisible(true);
                idStatusLb.setVisible(true);
                password2Tf.setVisible(false);
                password2StatusLb.setVisible(false);
                loginBtn.setVisible(true);
                registerBtn.setVisible(false);
                toLoginBtn.setVisible(false);
                toRegisterBtn.setVisible(true);

                idTf.requestFocus();
                idTf.selectAll();
                break;
            //转到注册按钮
            case "toRegister":
                titleLb.setText(titleRegister);
                nameStatusLb.setText("");
                nameTf.setText(nameInputStr);
                passwordStatusLb.setText("");
                passwordTf.setText(password2InputStr);
                password2StatusLb.setText("");
                password2Tf.setText(password2InputStr);
                idTf.setVisible(false);
                idStatusLb.setVisible(false);
                nameTf.setVisible(true);
                nameStatusLb.setVisible(true);
                password2Tf.setVisible(true);
                password2StatusLb.setVisible(true);
                registerBtn.setVisible(true);
                loginBtn.setVisible(false);
                toRegisterBtn.setVisible(false);
                toLoginBtn.setVisible(true);
                nameTf.requestFocus();
                nameTf.selectAll();
                break;
            default:
                break;
        }
        nameTf.selectAll();
    }

    //点击全选

    @Override
    public void focusGained(FocusEvent event) {
        if (event.getSource() == idTf) {
            idPl.setBorder(BorderFactory.createTitledBorder(idInputStr));
            idTf.selectAll();
        } else if (event.getSource() == nameTf) {
            namePl.setBorder(BorderFactory.createTitledBorder(nameInputStr));
            nameTf.selectAll();
        } else if (event.getSource() == passwordTf) {
            passwordPl.setBorder(BorderFactory.createTitledBorder(passwordInputStr));
            passwordTf.selectAll();
        } else if (event.getSource() == password2Tf) {
            password2Pl.setBorder(BorderFactory.createTitledBorder(password2InputStr));
            password2Tf.selectAll();
        }

    }

    @Override
    public void focusLost(FocusEvent event) {
        if (event.getSource() == idTf) {
            //关闭提示文本
            idPl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager(this).setId(idTf.getText())) {
                //id 输入正确
                case QUALIFIED:
                    idStatusLb.setText(qualifiedStatusStr);
                    break;
                //输入为空
                case EMPTY:
                    idStatusLb.setText(emptyStatusStr);
                    break;
                default:
                    break;
            }
        } else if (event.getSource() == nameTf) {
            namePl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager(this).setName(nameTf.getText())) {

                //name 输入正确
                case QUALIFIED:
                    nameStatusLb.setText(qualifiedStatusStr);
                    break;
                //输入过长
                case TOO_LONG:
                    nameStatusLb.setText(tooLongStatusStr);
                    break;
                //输入为空
                case EMPTY:
                    nameStatusLb.setText(emptyStatusStr);
                    break;
                default:
                    break;
            }
        } else if (event.getSource() == passwordTf) {
            passwordPl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager(this).setPassword(new String(passwordTf.getPassword()))) {
                //password 输入正确
                case QUALIFIED:
                    passwordStatusLb.setText(qualifiedStatusStr);
                    checkTwicePasswordInput();
                    break;
                //输入为空
                case EMPTY:
                    passwordStatusLb.setText(emptyStatusStr);
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
                default:
                    break;
            }
        } else if (event.getSource() == password2Tf) {
            password2Pl.setBorder(BorderFactory.createCompoundBorder());
            checkTwicePasswordInput();
        }

        updateAuthenticationBtn();
    }

    private void checkTwicePasswordInput() {
        if ((new String(password2Tf.getPassword())).equals(new String(passwordTf.getPassword()))) {
            //password2 输入一致
            password2StatusLb.setText(qualifiedStatusStr);
            updateAuthenticationBtn();
        } else {
            //password2 输入不一致
            password2StatusLb.setText(differentStatusStr);
        }
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
    public boolean receiver(LoginStatus loginStatus) {
        switch (loginStatus) {
            case SUCCESS:
                entryMainWindow();
                return true;
            case PASSWORD_ERROR:
                JOptionPane.showMessageDialog(null, passwordWrongAdviceStr, passwordWrongStr, JOptionPane.ERROR_MESSAGE);
                break;
            case ID_NOT_EXIST:
                JOptionPane.showMessageDialog(null, idNotExistAdviceStr, idNotExistStr, JOptionPane.ERROR_MESSAGE);
                break;
            default:
                break;
        }
        loginBtn.setEnabled(true);
        loginBtn.setText(loginBtnStr);
        toRegisterBtn.setEnabled(true);
        return false;
    }

    @Override
    public boolean receiver(RegisterStatus registerStatus, String userId) {
        switch (registerStatus) {
            case SUCCESS:
                this.userAuthenticationManager.setId(userId);
                entryMainWindow();
                return true;
            default:
                registerBtn.setEnabled(true);
                registerBtn.setText(registerBtnStr);
                toLoginBtn.setEnabled(true);
                return false;
        }
    }

    @Override
    public boolean receiver(String userName) {
        this.userNameTmp = userName;
        return false;
    }

    @Override
    @Deprecated
    public boolean receiver(String chatroomId, boolean isNewChatroomId) {
        return false;
    }
}


