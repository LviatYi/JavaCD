/**
 * @author May_bebe
 * @version 1.0
 * @interfaceName Encryption
 * @date 2021/6/2
 */

public interface Encryption {
    /**
     * 对密码文本进行加密
     * @param password 密码
     * @return 加密后的密码
     */
    String encryptPassword(String password);

    /**
     * 对聊天内容加密，确保每一聊天内容文本的加密解密使用同一密钥
     * @param content 聊天内容
     * @param key 密钥
     * @return 加密后的聊天内容
     */
    String encryptContent(String content, String key);

    /**
     * 对加密后的聊天内容解密，确保每一聊天内容文本的加密解密使用同一密钥
     * @param content 加密后的聊天内容
     * @param key 密钥
     * @return 聊天内容
     */
    String decryptContent(String content, String key);

    /**
     * 获取密钥
     * @param keyLength 密钥长度
     * @return 密钥
     */
    String getKey(int keyLength);
}
