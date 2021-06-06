package Socket.tools;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: topkang
 * @Date: 2021/06/05/11:24
 * @Description: Message class for json
 */
public class Message {
    public String id;
    public String name;
    public String password;
    public String senderId;
    public String receiverId;
    public String message;
    public transportType type;

    public enum transportType{
        LOGIN,
        REGISTER,
        SEND_GROUP_MESSAGE,
        SEND_PRIVATE_MESSAGE,
    }
}