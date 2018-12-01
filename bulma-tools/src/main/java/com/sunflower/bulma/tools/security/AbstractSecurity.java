package com.sunflower.bulma.tools.security;

import com.sunflower.bulma.tools.Exceptions;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc 加解密工共部分
 */
public abstract class AbstractSecurity {

    /**
     * 生成密钥
     *
     * @param keySize   密钥的长度
     * @param algorithm 算法
     * @return 密钥的byte数组
     */
    protected static byte[] generateKey(int keySize, String algorithm) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(keySize);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input     原始字节数组
     * @param key       符合要求的密钥
     * @param mode      Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @param algorithm 算法
     * @return
     */
    protected static byte[] deal(byte[] input, byte[] key, int mode, String algorithm) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(mode, secretKey);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input          原始字节数组
     * @param key            符合要求的密钥
     * @param iv             初始向量
     * @param mode           Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     * @param algorithm      算法
     * @param transformation 补码算法
     * @return
     */
    protected static byte[] deal(byte[] input, byte[] key, byte[] iv, int mode, String algorithm, String transformation) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, algorithm);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(mode, secretKey, ivSpec);
            return cipher.doFinal(input);
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }

}
