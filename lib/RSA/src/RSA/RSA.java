package RSA;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import RSA.binary.Base64;

public class RSA {
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 1024;

    public static KeyPair initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    private static String crypt(int mode, String data, KeyFactory keyFactory, Key key) throws Exception {
        /* If have some problem on Android, cipher use Cipher.getInstance(RSA/ECB/PKCS1Padding) */
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, key);

        String ret = null;
        if(mode == Cipher.ENCRYPT_MODE) ret = Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
        else if(mode == Cipher.DECRYPT_MODE) ret = new String(cipher.doFinal(Base64.decodeBase64(data)));
        return ret;
    }

    /** mode: Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE*/
    public static String privateCrypt(int mode, String data, String key) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return crypt(mode, data, keyFactory, privateKey);
    }

    public static String publicCrypt(int mode, String data, String key) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        return crypt(mode,data, keyFactory, publicKey);
    }

    public static String getPrivateKey(KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
    }

    public static String getPublicKey(KeyPair keyPair) {
        return Base64.encodeBase64String(keyPair.getPublic().getEncoded());
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSA.initKey();
        String publicKey = getPublicKey(keyPair);
        String privateKey = getPrivateKey(keyPair);

        System.out.println();
        System.out.println("public  key: " + publicKey);
        System.out.println("private key: " + privateKey);

        String testStr = "XUMUMI";
        String cont = RSA.privateCrypt(Cipher.ENCRYPT_MODE ,testStr, privateKey);

        System.out.println();
        System.out.println(cont);
        System.out.println(RSA.publicCrypt(Cipher.DECRYPT_MODE, cont, publicKey));

        cont = RSA.publicCrypt(Cipher.ENCRYPT_MODE, testStr, publicKey);
        System.out.println();
        System.out.println(cont);
        System.out.println(RSA.privateCrypt(Cipher.DECRYPT_MODE, cont, privateKey));
    }
}
