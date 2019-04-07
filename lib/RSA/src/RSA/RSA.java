package RSA;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import RSA.binary.Base64;
import org.jetbrains.annotations.NotNull;

public class RSA {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String INSTANCE = "RSA/ECB/PKCS1Padding";
    private static final int KEY_SIZE = 1024;
    private static final int CIPHER_SIZE = KEY_SIZE / 8;
    private static final int CLEAR_SIZE = CIPHER_SIZE - 11;

    public static KeyPair initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    private static String crypt(int mode, String data, Key key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(INSTANCE);
        cipher.init(mode, key);

        String ret = null;
        if(mode == Cipher.ENCRYPT_MODE) {
            ret = Base64.encodeBase64String(codeCrypt(cipher, data.getBytes(), 0, CLEAR_SIZE));
        }
        else if(mode == Cipher.DECRYPT_MODE) {
            ret = new String(codeCrypt(cipher, Base64.decodeBase64(data), 0, CIPHER_SIZE));
        }
        return ret;
    }

    private static byte[] codeCrypt(Cipher cipher, @NotNull byte[] data, int offset, int len)
            throws BadPaddingException, IllegalBlockSizeException, IOException {
        byte[] ret;
        if (offset + len < data.length){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            out.write(cipher.doFinal(data, offset, len));
            out.write(codeCrypt(cipher, data, offset + len, len));
            ret = out.toByteArray();
        } else ret = cipher.doFinal(data, offset, data.length - offset);
        return ret;
    }

    /** mode: Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE */
    public static String privateCrypt(int mode, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return crypt(mode, data, privateKey);
    }

    public static String publicCrypt(int mode, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return crypt(mode, data, publicKey);
    }

    public static String getPrivateKey(@NotNull KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }

    public static String getPublicKey(@NotNull KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSA.initKey();
        String publicKey = getPublicKey(keyPair);
        String privateKey = getPrivateKey(keyPair);

        System.out.println();
        System.out.println("public  key: " + publicKey);
        System.out.println("private key: " + privateKey);

        String testStr = "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试";

        String cont = RSA.privateCrypt(Cipher.ENCRYPT_MODE ,testStr, privateKey);
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++)RSA.publicCrypt(Cipher.DECRYPT_MODE, cont, publicKey);
        long endTime = System.currentTimeMillis();
        System.out.println("公钥解密 10,000 次共耗时: " + (endTime - startTime) / 1000.0 + " 秒");

        cont = RSA.publicCrypt(Cipher.ENCRYPT_MODE, testStr, publicKey);
        System.out.println();
        System.out.println(cont);
        System.out.println(RSA.privateCrypt(Cipher.DECRYPT_MODE, cont, privateKey));
    }
}
