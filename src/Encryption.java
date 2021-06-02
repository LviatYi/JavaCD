/**
 *
 * @interfaceName ${NAME}
 * @author May_bebe
 * @date ${DATE}
 * @version TODO
 */
public interface Encryption {
    /**
     * 对密码文本进行加密
     * @param password 密码
     * @return 加密后的密码
     */
    String encryptPassword(String password);

    /**
     *
     * @param content 聊天内容
     * @param key
     * @return
     */
    String encryptContent(String content, String key);


    /**
     *
     * @param content
     * @param key
     * @return
     */
    String decryptContent(String content, String key);

    /**
     *
     * @param keyLength
     * @return
     */
    String getKey(int keyLength);
}