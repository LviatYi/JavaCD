public interface Encryption {
    String doSignInHashEncryption(String password);
    String doChatKeyEncryption(String content, String key);
    String doChatKeyDecryption(String content, String key);
    String getKey(int keyLength);
}