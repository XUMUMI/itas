## RSA  库



#### 包名

RSA

```
import RSA.*;
```



#### 密钥生成

Static 方法 `initKey` 将创建一对密钥, 类型为 `KeyPair `,  可通过  `getPrivateKey`  获取私钥, 通过  `getPublicKey` 获取公钥

```
函数名 initKey
返回值 (KeyPair 密钥对)
异常 生成失败
```

```
函数名 getPrivateKey, getPublicKey
传入参 (KeyPair 密钥对)
返回值 (String 密钥值)
```

示例

```java
KeyPair keyPair = RSA.initKey();
String publicKey = getPublicKey(keyPair);
String privateKey = getPrivateKey(keyPair);
```



#### 签名、加密与解密

调用 `privateCrypt` 、 `publicCrypt` 函数进行签名或解密

```
函数名 privateCrypt, publicCrypt
传入参 (int 模式, String 明文或密文, String 密钥)
返回值 (String 密文或明文)
异常 加密、解密或签名失败

模式枚举值
	加密、签名: Cipher.ENCRYPT_MODE
	解密: 	 Cipher.DECRYPT_MODE
```

示例

```java
String cipherText = RSA.privateCrypt(Cipher.ENCRYPT_MODE, clearText,  privateKey);
String clearText  = RSA.privateCrypt(Cipher.DECRYPT_MODE, cipherText, privateKey);
String cipherText = RSA.publicCrypt (Cipher.ENCRYPT_MODE, clearText,  publicKey);
String clearText  = RSA.publicCrypt (Cipher.DECRYPT_MODE, cipherText, publicKey);
```

