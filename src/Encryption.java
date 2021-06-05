/**
 * @author May_bebe
 * @version 1.0
 * @interfaceName Encryption
 * @date 2021/6/2
 */

public interface Encryption {
    /**
     * 对密码文本进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    String encryptPassword(String password);
    /**
     * 对聊天内容加密
     * @param content 聊天内容
     * @return 加密后的聊天内容
     */
    String encryptContent(String content);

    /**
     * 对加密后的聊天内容解密
     * @param keyContent 密钥和加密后的聊天内容的合并字符串
     * @return 聊天内容
     */
    String decryptContent(String keyContent);


}