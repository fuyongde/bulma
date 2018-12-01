package com.sunflower.bulma.tools.security;

import com.sunflower.bulma.tools.Exceptions;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc RSA算法工具类
 */
public class RSAUtils {

    private static final String RSA = "RSA";
    /**
     * 默认的签名算法
     **/
    private static final String SIGNATURE_ALGORITHM_DEFAULT = "SHA1withRSA";
    private static final String TRANSFORMATION_DEFAULT = "RSA/ECB/PKCS1Padding";
    private static final String PKCS12 = "PKCS12";
    private static final String X509 = "X.509";
    private static int RSA_KEY_SIZE_DEFAULT = 1024;

    /**
     * RSA签名
     *
     * @param source     原文
     * @param privateKey 私钥
     * @return
     */
    public static String sign(byte[] source, byte[] privateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 取私钥匙对象
            PrivateKey key = keyFactory.generatePrivate(pkcs8KeySpec);
            return sign(source, key, SIGNATURE_ALGORITHM_DEFAULT);
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 验签
     *
     * @param source    原文
     * @param target    签名值
     * @param publicKey 公钥
     * @return
     */
    public static boolean verify(byte[] source, byte[] target, byte[] publicKey) {
        try {
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 取公钥匙对象
            PublicKey key = keyFactory.generatePublic(keySpec);
            return verify(source, target, key, SIGNATURE_ALGORITHM_DEFAULT);
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 生成密钥对
     *
     * @return
     */
    public static KeyPair generateKeyPair() {
        return generateKeyPair(RSA, RSA_KEY_SIZE_DEFAULT);
    }

    /**
     * 生成密钥对
     *
     * @return
     */
    public static KeyPair generateKeyPair(String algorithm, int keySize) {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw Exceptions.unchecked(e);
        }
        keyPairGen.initialize(keySize);
        return keyPairGen.generateKeyPair();
    }

    /**
     * 取得私钥
     *
     * @param keyPair
     * @return
     */
    public static byte[] getPrivateKey(KeyPair keyPair) {
        return keyPair.getPrivate().getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyPair
     * @return
     */
    public static byte[] getPublicKey(KeyPair keyPair) {
        return keyPair.getPublic().getEncoded();
    }

    /**
     * RSA公钥加密
     *
     * @param source    原文
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(byte[] source, byte[] publicKey) {
        return encrypt(source, publicKey, TRANSFORMATION_DEFAULT, RSA);
    }

    /**
     * RSA私钥解密
     *
     * @param cipherData 密文
     * @param privateKey 密钥
     * @return
     */
    public static String decrypt(byte[] cipherData, byte[] privateKey) {
        return decrypt(cipherData, privateKey, TRANSFORMATION_DEFAULT, RSA);
    }

    /**
     * 公钥加密
     *
     * @param source 原文
     * @param key    公钥
     * @return 密文
     */
    private static String encrypt(byte[] source, byte[] key, String transformation, String algorithm) {
        try {
            // 取得公钥
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64String(cipher.doFinal(source));
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param cipherData     密文
     * @param privateKeyData 私钥
     * @param transformation 补码
     * @param algorithm
     * @return
     */
    private static String decrypt(byte[] cipherData, byte[] privateKeyData, String transformation, String algorithm) {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyData);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(cipherData));
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 签名
     *
     * @param source     原文
     * @param privateKey 私钥
     * @param algorithm  签名算法
     * @return
     */
    private static String sign(byte[] source, PrivateKey privateKey, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(source);
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 验签
     *
     * @param source    原文
     * @param target    签名值
     * @param publicKey 公钥
     * @param algorithm 签名算法
     * @return
     */
    private static boolean verify(byte[] source, byte[] target, PublicKey publicKey, String algorithm) {
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(publicKey);
            signature.update(source);
            return signature.verify(Base64.decodeBase64(target));
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 获取pkcs12私钥
     *
     * @param certFile     证书文件
     * @param certPassword 证书密码
     * @return
     */
    public static byte[] pkcs12PrivateKey(File certFile, String certPassword) {
        return getPrivateKey(certFile, certPassword, PKCS12).getEncoded();
    }

    /**
     * 获取x509公钥证书
     *
     * @param certFile 公钥证书文件
     * @return
     */
    public static byte[] x509PublicKey(File certFile) {
        return getPublicKey(certFile, X509).getEncoded();
    }

    /**
     * 获取x509公钥证书
     *
     * @param certFile 公钥证书文件
     * @return
     */
    public static byte[] pkcs12PublicKey(File certFile, String certPassword) {
        return getPublicKey(certFile, certPassword, PKCS12).getEncoded();
    }


    /**
     * 获取私钥
     *
     * @param certFile     证书文件
     * @param certPassword 密码
     * @param type         KeyStore类型
     * @return
     */
    private static PrivateKey getPrivateKey(File certFile, String certPassword, String type) {
        try {
            KeyStore keyStore = KeyStore.getInstance(type);
            FileInputStream inputStream = new FileInputStream(certFile);
            keyStore.load(inputStream, certPassword.toCharArray());
            inputStream.close();

            Enumeration<String> enumeration = keyStore.aliases();
            String keyAlias = null;
            if (enumeration.hasMoreElements()) {
                keyAlias = enumeration.nextElement();
            }

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, certPassword.toCharArray());
            return privateKey;
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 获取私钥
     *
     * @param certFile     证书文件
     * @param certPassword 密码
     * @param type         KeyStore类型
     * @return
     */
    private static PublicKey getPublicKey(File certFile, String certPassword, String type) {
        try {
            KeyStore keyStore = KeyStore.getInstance(type);
            FileInputStream inputStream = new FileInputStream(certFile);
            keyStore.load(inputStream, certPassword.toCharArray());
            inputStream.close();

            Enumeration<String> enumeration = keyStore.aliases();
            String keyAlias = null;
            if (enumeration.hasMoreElements()) {
                keyAlias = enumeration.nextElement();
            }

            PublicKey publicKey = keyStore.getCertificate(keyAlias).getPublicKey();
            return publicKey;
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 获取公钥
     *
     * @param certFile 证书文件
     * @param type     证书工厂类型
     * @return 公钥
     */
    private static PublicKey getPublicKey(File certFile, String type) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(type);
            FileInputStream inputStream = new FileInputStream(certFile);
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            inputStream.close();
            return certificate.getPublicKey();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }
}
