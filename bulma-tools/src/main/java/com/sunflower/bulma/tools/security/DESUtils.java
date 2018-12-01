package com.sunflower.bulma.tools.security;

import javax.crypto.Cipher;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc DES加解密
 */
public class DESUtils extends AbstractSecurity {

    private static final String DES = "DES";
    private static final int DES_KEY_SIZE_DEFAULT = 56;

    private static final String DES_CBC = "DES/CBC/PKCS5Padding";

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     */
    public static byte[] encrypt(byte[] input, byte[] key) {
        return des(input, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES加密原始字符串.
     *
     * @param input 原始输入字符数组
     * @param key   符合AES要求的密钥
     * @param iv    初始向量
     */
    public static byte[] encrypt(byte[] input, byte[] key, byte[] iv) {
        return des(input, key, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 使用AES解密字符串, 返回原始字符串.
     *
     * @param input Hex编码的加密字符串
     * @param key   符合AES要求的密钥
     */
    public static String decrypt(byte[] input, byte[] key) {
        byte[] decryptResult = des(input, key, Cipher.DECRYPT_MODE);
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
        byte[] decryptResult = des(input, key, iv, Cipher.DECRYPT_MODE);
        return new String(decryptResult);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合DES要求的密钥
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     */
    private static byte[] des(byte[] input, byte[] key, int mode) {
        return deal(input, key, mode, DES);
    }

    /**
     * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
     *
     * @param input 原始字节数组
     * @param key   符合DES要求的密钥
     * @param iv    初始向量
     * @param mode  Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
     */
    private static byte[] des(byte[] input, byte[] key, byte[] iv, int mode) {
        return deal(input, key, iv, mode, DES, DES_CBC);
    }

    /**
     * 生成DES密钥,返回字节数组, 默认长度为56位
     */
    public static byte[] generateDesKey() {
        return generateDesKey(DES_KEY_SIZE_DEFAULT);
    }

    /**
     * 生成AES密钥
     *
     * @param keySize 长度，可选长度为128,192,256位
     * @return 密钥
     */
    public static byte[] generateDesKey(int keySize) {
        return generateKey(keySize, DES);
    }
}
