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

    public UserAuthenticationGui()
    {
        prepareGui();
    }

    private void prepareGui()
    {
        /**
         * 主窗体设置
         */
        this.setTitle(titleFrame);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        /**
         * 居中显示
         */
        this.setSize(640, 480);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);

        /**
         * 提示文字布局
         */
        this.idTf.setText(idInputStr);
        this.nameTf.setText(nameInputStr);
        this.passwordTf.setText(password2InputStr);
        this.password2Tf.setText(password2InputStr);

        this.toRegisterBtn.setText(changeToRegisterStr);
        this.toLoginBtn.setText(changeToLoginStr);

        this.loginBtn.setText(loginBtnStr);

        titleLb.setText(titleLogin);

        /**
         * 添加监听器类
         */
        idTf.setActionCommand("inputId");
        nameTf.setActionCommand("inputName");
        passwordTf.setActionCommand("inputPassword");
        password2Tf.setActionCommand("inputPassword2");

        idTf.addActionListener(this);
//        nameTf.addActionListener(this);
//        passwordTf.addActionListener(this);
//        password2Tf.addActionListener(this);
//        loginBtn.addActionListener(this);
//        registerBtn.addActionListener(this);
//        toRegisterBtn.addActionListener(this);
        idTf.addFocusListener(this);
        nameTf.addFocusListener(this);
        passwordTf.addFocusListener(this);
        password2Tf.addFocusListener(this);


        /**
         * 初始选中状态
         */
        idTf.selectAll();
    }


    public static void main(String[] args)
    {
        UserAuthenticationGui uam = new UserAuthenticationGui();
    }

    /**
     * 按钮、回车时间监听实现
     */

    @Override
    public void actionPerformed(ActionEvent event)
    {
//        switch (event.getActionCommand())
//        {
//            case "inputId":
//                nameTf.selectAll();
//                break;
//            case "inputName":
//            case "inputPassword":
//            case "inputPassword2":
//            case "login":
//            case "register":
//            case "changeMode":
//            default:
//        }
        nameTf.selectAll();
    }

    @Override
    public void focusGained(FocusEvent event)
    {
        if (event.getSource() == idTf)
        {
            idTf.selectAll();
        }
        if (event.getSource() == nameTf)
        {
            nameTf.selectAll();
        }
        if (event.getSource() == passwordTf)
        {
            passwordTf.selectAll();
        }
        if (event.getSource() == password2Tf)
        {
            password2Tf.selectAll();
        }

    }

    @Override
    public void focusLost(FocusEvent event)
    {
        if (event.getSource() == idTf)
        {
        }
        if (event.getSource() == nameTf)
        {
        }
        if (event.getSource() == passwordTf)
        {
        }
        if (event.getSource() == password2Tf)
        {
        }
    }
}


