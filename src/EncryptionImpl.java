import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

public class EncryptionImpl implements Encryption {

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
    public String encryptContent(String content, String key) {
        StringBuilder sbu = new StringBuilder();
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        byte[] contentBytes = sbu.toString().getBytes();
        byte[] keyBytes = key.getBytes();
        byte temp = 0;
        for (byte b : keyBytes) {
            temp ^= b;
        }
        byte salt = 0;
        byte[] result = new byte[contentBytes.length];
        for (int i = 0; i < contentBytes.length; i++) {
            salt = (byte) (contentBytes[i] ^ temp ^ salt);
            result[i] = salt;
        }
        return new String(result);
    }

    @Override
    public String decryptContent(String content, String key) {
        byte[] contentBytes = content.getBytes();
        byte[] keyBytes = key.getBytes();
        byte temp = 0;
        for (byte b : keyBytes) {
            temp ^= b;
        }
        byte salt;
        byte[] result = new byte[contentBytes.length];
        for (int i = contentBytes.length - 1; i >= 0; i--) {
            if (i == 0) {
                salt = 0;
            } else {
                salt = contentBytes[i - 1];
            }
            result[i] = (byte) (contentBytes[i] ^ temp ^ salt);
        }
        String temp1 = new String(result);
        StringBuilder sbu = new StringBuilder();
        String[] chars = temp1.split(",");
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        return sbu.toString();
    }


    @Override
    public String getKey(int keyLength) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}


