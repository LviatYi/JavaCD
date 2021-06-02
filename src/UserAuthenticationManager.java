/**
 * Manager of User Authentication
 * Singleton mode
 * 单例模式，保证只有一个单例
 * @author LVIAT.cn
 * @version 1.0
 * @className UserAuthenticationManager
 * @date 2021/6/2
 */
public class UserAuthenticationManager
{
    private static UserAuthenticationManager instance=null;
    private String ID;
    private String name;
    private String password;
    private UserAuthenticationManager(){
    }

    /**
     * 单例模式 获得用户权限管理器
     * @return 用户权限管理器
     */
    public static UserAuthenticationManager getUserAuthenticationManager(){
        if(instance==null)
        {
            instance=new UserAuthenticationManager();
        }

        return instance;
    }


}
