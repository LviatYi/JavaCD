import javax.swing.*;
import java.awt.*;

/**
 * @className UserAuthenticationGUI
 * @author LVIAT.cn
 * @date 2021/6/2
 * @version 1.0
 */
public class UserAuthenticationGui extends JFrame
{
    private JFrame authenticationFrame;
    private JPanel mainPanel;
    private JLabel titleLb;
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

    public UserAuthenticationGui()
    {
        prepareGui();
    }

    private void prepareGui()
    {
        //主窗体设置
        this.setTitle(titleFrame);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //居中显示
        this.setSize(640, 480);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);

        titleLb.setText(titleLogin);
    }


    public static void main(String[] args)
    {
        UserAuthenticationGui uag = new UserAuthenticationGui();
    }
}


