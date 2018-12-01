package com.sunflower.bulma.tools.security;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc RSA加解密算法测试
 */
public class RSAUtilsTest {

    private byte[] privateKey;
    private byte[] publicKey;

    private String source = "fuyongde";

    @Before
    public void setUp() throws Exception {
        KeyPair keyPair = RSAUtils.generateKeyPair();
        privateKey = RSAUtils.getPrivateKey(keyPair);
        publicKey = RSAUtils.getPublicKey(keyPair);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sign() {
        String sign = RSAUtils.sign(source.getBytes(), privateKey);
        boolean verify = RSAUtils.verify(source.getBytes(), sign.getBytes(), publicKey);
        assertTrue(verify);
    }

    @Test
    public void encrypt() {
        String cipherData = RSAUtils.encrypt(source.getBytes(), publicKey);
        String sourceData = RSAUtils.decrypt(Base64.decodeBase64(cipherData), privateKey);
        assertEquals(sourceData, source);
    }

}