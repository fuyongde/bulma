package com.sunflower.bulma.tools.security;

import javax.crypto.Cipher;
import java.security.SecureRandom;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc AES加解密工具
 */
public final class AESUtils extends AbstractSecurity {

    private static final String AES = "AES";
    private static final int AES_KEY_SIZE_DEFAULT = 128;
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    private static final int AES_IV_SIZE_DEFAULT = 16;
    private static SecureRandom random = new SecureRandom();

    private AESUtils() {}

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     */
    public static byte[] encrypt(byte[] input, byte[] key) {
        return aes(input, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     */
    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv) {
        return aes(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     */
    public static String decrypt(byte[] input, byte[] key) {
        byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     */
    public static String decrypt(byte[] input, byte[] key, byte[] iv) {
        byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
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
