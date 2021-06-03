import javafx.embed.swing.SwingFXUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author LVIAT.cn
 * @version 1.0
 * @className UserAuthenticationGUI
 * @date 2021/6/2
 */
public class UserAuthenticationGui extends JFrame implements ActionListener, FocusListener
{
    private static UserAuthenticationManager userAuthenticationManager = UserAuthenticationManager.getUserAuthenticationManager();
    private JFrame authenticationFrame;
    private JPanel mainPanel;
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

    //按钮、提示文本

    private String titleFrame = "Jchat" + " " + "v " + "1.00";

    private String titleLogin = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 24px; font-family:'Trebuchet MS'\">\n" +
            "            <div>\n" +
            "                JChat 2021 | Login\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";

    private String titleRegister = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 24px; font-family:'Trebuchet MS'\">\n" +
            "            <div>\n" +
            "                JChat 2021 | Register\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String idInputStr = "User ID";
    private String nameInputStr = "User name";
    private String passwordInputStr = "Password";
    private String password2InputStr = "Password again";
    private String changeToRegisterStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: 0080ff;\">\n" +
            "            <div>\n" +
            "                Register now!\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String changeToLoginStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: 0080ff;\">\n" +
            "            <div>\n" +
            "                Already have an account...\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String loginBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px; font-family:'Trebuchet MS';\">\n" +
            "            <div>\n" +
            "                Login\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String registerBtnStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 16px; font-family:'Trebuchet MS';\">\n" +
            "            <div>\n" +
            "                Register\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String qualifiedStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(154, 205, 50)\">\n" +
            "            <div>Right</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String emptyStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px; font-family:'Trebuchet MS';color: rgb(245, 66, 66);\">\n" +
            "            <div>\n" +
            "                Cannot be empty\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String tooLongStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too long</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String tooShortStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too short</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String easyStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>That's too easy</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
    private String differentStatusStr = "<html>\n" +
            "    <body>\n" +
            "        <div style=\"font-size: 10px;font-family: 'Trebuchet MS';color: rgb(245, 66, 66)\">\n" +
            "            <div>Two passwords are inconsistent</div>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";

    private String connectionErrorTitleStr="Can't connect to server!";
    private String connectionErrorStr="Please check the connection status.";


    // 键绑定调用函数

    private Action setId = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            passwordTf.requestFocus();
            passwordTf.selectAll();
        }
    };
    private Action setName = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            passwordTf.requestFocus();
            passwordTf.selectAll();
        }
    };
    private Action setPassword = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (loginBtn.isVisible())
            {
                mainPanel.requestFocus();
            } else
            {
                password2Tf.requestFocus();
                password2Tf.selectAll();
            }
        }
    };
    private Action setPassword2 = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            mainPanel.requestFocus();
        }
    };

    private Action toControlBtn = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (loginBtn.isVisible())
            {
                loginBtn.requestFocus();
            } else if (registerBtn.isVisible())
            {
                registerBtn.requestFocus();
            }
        }
    };
    private Action login = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            loginBtn.doClick();
        }
    };
    private Action register = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            registerBtn.doClick();
        }
    };

    /**
     * 更新 认证 按钮可用状态
     */
    private void updateAuthenticationBtn()
    {
        updateRegisterBtn();
        updateLoginBtn();
    }

    /**
     * 更新 注册 按钮可用状态
     */
    private void updateRegisterBtn()
    {
        if (registerBtn.isVisible())
        {
            if (nameStatusLb.getText().equals(qualifiedStatusStr) && passwordStatusLb.getText().equals(qualifiedStatusStr) && password2StatusLb.getText().equals(qualifiedStatusStr))
            {
                registerBtn.setEnabled(true);
            } else
            {
                registerBtn.setEnabled(false);
            }
        }
    }

    /**
     * 更新 登录 按钮可用状态
     */
    private void updateLoginBtn()
    {
        if (loginBtn.isVisible())
        {
            if (idStatusLb.getText().equals(qualifiedStatusStr) && passwordStatusLb.getText().equals(qualifiedStatusStr))
            {
                loginBtn.setEnabled(true);
            } else
            {
                loginBtn.setEnabled(false);
            }
        }
    }

    public UserAuthenticationGui()
    {
        prepareGui();
    }


    private void prepareGui()
    {
        /*
         * 主窗体设置
         */

        this.setTitle(titleFrame);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        /*
         * 居中显示
         */

        this.setSize(640, 480);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);

        /*
         * 提示文字布局
         */

        this.idTf.setText(idInputStr);
        this.nameTf.setText(nameInputStr);
        this.passwordTf.setText(password2InputStr);
        this.password2Tf.setText(password2InputStr);

        this.toRegisterBtn.setText(changeToRegisterStr);
        this.toLoginBtn.setText(changeToLoginStr);

        this.loginBtn.setText(loginBtnStr);
        this.registerBtn.setText(registerBtnStr);

        titleLb.setText(titleLogin);

        /*
         * 添加键绑定
         */

        idTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setId");
        idTf.getActionMap().put("setId", setId);
        nameTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setName");
        nameTf.getActionMap().put("setName", setName);
        passwordTf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setPassword");
        passwordTf.getActionMap().put("setPassword", setPassword);
        password2Tf.getInputMap().put(KeyStroke.getKeyStroke('\n'), "setPassword2");
        password2Tf.getActionMap().put("setPassword2", setPassword2);
        mainPanel.getInputMap().put(KeyStroke.getKeyStroke('\n'), "toControlBtn");
        mainPanel.getActionMap().put("toControlBtn", toControlBtn);
        loginBtn.getInputMap().put(KeyStroke.getKeyStroke('\n'), "login");
        loginBtn.getActionMap().put("login", login);
        registerBtn.getInputMap().put(KeyStroke.getKeyStroke('\n'), "register");
        registerBtn.getActionMap().put("login", register);

        /*
         * 添加监听器类
         */

        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        toLoginBtn.addActionListener(this);
        toRegisterBtn.addActionListener(this);

        idTf.addFocusListener(this);
        nameTf.addFocusListener(this);
        passwordTf.addFocusListener(this);
        password2Tf.addFocusListener(this);

        /*
         * 初始状态
         */
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


    public static void main(String[] args)
    {
        UserAuthenticationGui uam = new UserAuthenticationGui();
    }

    /*
     * 按钮、回车时间监听实现
     */

    @Override
    public void actionPerformed(ActionEvent event)
    {
        switch (event.getActionCommand())
        {
            case "login":
                JOptionPane.showMessageDialog(null, connectionErrorStr, connectionErrorTitleStr, JOptionPane.ERROR_MESSAGE);
                /**
                * TODO_LviatYi
                * login btn
                * date 2021/6/4
                */
                break;
            case "register":
               /**
               * TODO_LviatYi
               * register btn
               * date 2021/6/4
               */
                break;
            case "toLogin":
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
            case "toRegister":
                nameStatusLb.setText("");
                nameTf.setText(nameInputStr);
                passwordStatusLb.setText("");
                passwordTf.setText(password2InputStr);
                password2StatusLb.setText("");
                password2Tf.setText(password2InputStr);

                idTf.setVisible(false);
                idStatusLb.setVisible(false);
                /**
                 * TODO_LviatYi
                 * Get remote ID
                 * date 2021/6/4
                 */
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

    /*
     * 点击全选
     */

    @Override
    public void focusGained(FocusEvent event)
    {
        if (event.getSource() == idTf)
        {
            idPl.setBorder(BorderFactory.createTitledBorder(idInputStr));
            idTf.selectAll();
        } else if (event.getSource() == nameTf)
        {
            namePl.setBorder(BorderFactory.createTitledBorder(nameInputStr));
            nameTf.selectAll();
        } else if (event.getSource() == passwordTf)
        {
            passwordPl.setBorder(BorderFactory.createTitledBorder(passwordInputStr));
            passwordTf.selectAll();
        } else if (event.getSource() == password2Tf)
        {
            password2Pl.setBorder(BorderFactory.createTitledBorder(password2InputStr));
            password2Tf.selectAll();
        }

    }

    @Override
    public void focusLost(FocusEvent event)
    {
        if (event.getSource() == idTf)
        {
            idPl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager().setId(idTf.getText()))
            {
                case QUALIFIED:
                    idStatusLb.setText(qualifiedStatusStr);
                    updateAuthenticationBtn();
                    break;
                case EMPTY:
                    idStatusLb.setText(emptyStatusStr);
                    break;
                default:
                    break;
            }
        } else if (event.getSource() == nameTf)
        {
            namePl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager().setName(nameTf.getText()))
            {
                case QUALIFIED:
                    nameStatusLb.setText(qualifiedStatusStr);
                    updateAuthenticationBtn();
                    break;
                case TOO_LONG:
                    nameStatusLb.setText(tooLongStatusStr);
                    break;
                case EMPTY:
                    nameStatusLb.setText(emptyStatusStr);
                    break;
                default:
                    break;
            }
        } else if (event.getSource() == passwordTf)
        {
            passwordPl.setBorder(BorderFactory.createCompoundBorder());
            switch (UserAuthenticationManager.getUserAuthenticationManager().setPassword(new String(passwordTf.getPassword())))
            {
                case QUALIFIED:
                    passwordStatusLb.setText(qualifiedStatusStr);
                    updateAuthenticationBtn();
                    break;
                case EMPTY:
                    passwordStatusLb.setText(emptyStatusStr);
                    break;
                case TOO_LONG:
                    passwordStatusLb.setText(tooLongStatusStr);
                    break;
                case TOO_SHORT:
                    passwordStatusLb.setText(tooShortStatusStr);
                    break;
                case EASY:
                    passwordStatusLb.setText(easyStatusStr);
                    break;
                default:
                    break;
            }
        } else if (event.getSource() == password2Tf)
        {
            password2Pl.setBorder(BorderFactory.createCompoundBorder());
            if ((new String(password2Tf.getPassword())).equals(new String(passwordTf.getPassword())))
            {
                password2StatusLb.setText(qualifiedStatusStr);
                updateAuthenticationBtn();
            } else
            {
                password2StatusLb.setText(differentStatusStr);
            }
        }
    }
}


