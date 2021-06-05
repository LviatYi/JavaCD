import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 加密接口的实现
 * @author May_bebe
 * @version 1.0
 * @className EncryptionImpl
 * @date 2021/6/2
 */
public class EncryptionImpl implements Encryption {

    private static int keyLength=10;

    @Override
    public String encryptPassword(String password) {
        String result = null;
        if (password != null && password.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                messageDigest.update(password.getBytes());
                byte[] byteBuffer = messageDigest.digest();
                StringBuilder strHexString = new StringBuilder();
                for (byte b : byteBuffer) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                result = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String encryptContent(String content) {
        String codeString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            int number = random.nextInt(62);
            key.append(codeString.charAt(number));
        }
        StringBuilder subString = new StringBuilder();
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                subString.append((int) chars[i]).append(",");
            } else {
                subString.append((int) chars[i]);
            }
        }
        byte[] contentBytes = subString.toString().getBytes();
        byte[] keyBytes = key.toString().getBytes();
        byte tempCalculation = 0;
        for (byte b : keyBytes) {
            tempCalculation ^= b;
        }
        byte salt = 0;
        byte[] result = new byte[contentBytes.length];
        for (int i = 0; i < contentBytes.length; i++) {
            salt = (byte) (contentBytes[i] ^ tempCalculation ^ salt);
            result[i] = salt;
        }
        return key.toString()+new String(result);
    }

    @Override
    public String decryptContent(String keyContent) {
        String key=keyContent.substring(0,keyLength);
        String content=keyContent.substring(keyLength);
        byte[] contentBytes = content.getBytes();
        byte[] keyBytes = key.getBytes();
        byte tempCalculation = 0;
        for (byte b : keyBytes) {
            tempCalculation ^= b;
        }
        byte salt;
        byte[] result = new byte[contentBytes.length];
        for (int i = contentBytes.length - 1; i >= 0; i--) {
            if (i == 0) {
                salt = 0;
            } else {
                salt = contentBytes[i - 1];
            }
            result[i] = (byte) (contentBytes[i] ^ tempCalculation ^ salt);
        }
        String str = new String(result);
        StringBuilder sbuString = new StringBuilder();
        String[] chars = str.split(",");
        for (String tempChar : chars) {
            sbuString.append((char) Integer.parseInt(tempChar));
        }
        return sbuString.toString();
    }
}

