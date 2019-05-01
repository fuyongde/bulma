package com.sunflower.bulma.tools.security;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc AES加解密工具
 */
public final class AesUtils extends AbstractSecurity {

    private static final String AES = "AES";
    private static final int AES_KEY_SIZE_DEFAULT = 128;
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    private static final Charset CHARSET_DEFAULT = Charsets.UTF_8;

    private static final int AES_IV_SIZE_DEFAULT = 16;
    private static SecureRandom random = new SecureRandom();

    private AesUtils() {
    }


    /**
     * 使用AES加密原始字符串.
     *
     * @param plaintext 明文
     * @param key   符合AES要求的密钥
     * @return 密文的
     */
    public static String encrypt(String plaintext, String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(plaintext), "plaintext is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes key is blank");
        byte[] cipherData = encrypt(plaintext.getBytes(CHARSET_DEFAULT), Base64.decodeBase64(key));
        return Base64.encodeBase64String(cipherData);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param plaintext 明文
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 密文
     */
    public static String encrypt(String plaintext, String key, String iv) {
        Preconditions.checkArgument(StringUtils.isNotBlank(plaintext), "plaintext is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes key is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes iv is blank");
        byte[] cipherData = encrypt(plaintext.getBytes(CHARSET_DEFAULT), Base64.decodeBase64(key), Base64.decodeBase64(iv));
        return Base64.encodeBase64String(cipherData);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param cipherText 密文byte数组
     * @param key   符合AES要求的密钥
     * @return 明文byte数组
     */
    public static String decrypt(String cipherText, String key) {
        Preconditions.checkArgument(StringUtils.isNotBlank(cipherText), "ciphertext is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes key is blank");
        byte[] plainData = decrypt(Base64.decodeBase64(cipherText), Base64.decodeBase64(key));
        return new String(plainData);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param cipherText 密文byte数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 解密后的byte数组
     */
    public static String decrypt(String cipherText, String key, String iv) {
        Preconditions.checkArgument(StringUtils.isNotBlank(cipherText), "ciphertext is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes key is blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "aes iv is blank");
        byte[] plainData = decrypt(Base64.decodeBase64(cipherText), Base64.decodeBase64(key), Base64.decodeBase64(iv));
        return new String(plainData);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param plaintext 明文
     * @param key   符合AES要求的密钥
     * @return 密文的byte数组
     */
    public static byte[] encrypt(byte[] plaintext, byte[] key) {
        return aes(plaintext, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param plaintext 明文
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 密文byte数组
     */
    public static byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv) {
        return aes(plaintext, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param ciphertext 密文byte数组
     * @param key   符合AES要求的密钥
     * @return 明文byte数组
     */
    public static byte[] decrypt(byte[] ciphertext, byte[] key) {
        return aes(ciphertext, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param ciphertext 密文byte数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @return 解密后的byte数组
     */
    public static byte[] decrypt(byte[] ciphertext, byte[] key, byte[] iv) {
        return aes(ciphertext, key, iv, Cipher.DECRYPT_MODE);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合AES要求的密钥
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     */
    private static byte[] aes(byte[] input, byte[] key, int mode) {
        return deal(input, key, mode, AES);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     */
    private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
        return deal(input, key, iv, mode, AES, AES_CBC);
    }

    /**
     * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
     */
    public static byte[] generateAesKey() {
        return generateAesKey(AES_KEY_SIZE_DEFAULT);
    }

    /**
     * 生成AES密钥
     *
     * @param keySize 长度，可选长度为128,192,256位
     * @return 密钥
     */
    public static byte[] generateAesKey(int keySize) {
        return generateKey(keySize, AES);
    }

    /**
     * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
     */
    public static byte[] generateIV() {
        byte[] bytes = new byte[AES_IV_SIZE_DEFAULT];
        random.nextBytes(bytes);
        return bytes;
    }
}
