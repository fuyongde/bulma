package com.sunflower.bulma.tools.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc AES加解密工具类
 */
public class AESUtilsTest {

    private byte[] key;
    private byte[] iv;

    private String source = "fuyongde";

    @Before
    public void setUp() throws Exception {
        key = AESUtils.generateAesKey();
        iv = AESUtils.generateIV();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encryptAndDecrypt() {
        byte[] cipherData = AESUtils.encrypt(source.getBytes(), key);
        String sourceData = new String(AESUtils.decrypt(cipherData, key));
        assertEquals(sourceData, source);
    }

    @Test
    public void encryptAndDecrypt2() {
        byte[] cipherData = AESUtils.encrypt(source.getBytes(), key, iv);
        String sourceData = new String(AESUtils.decrypt(cipherData, key, iv));
        assertEquals(sourceData, source);
    }

}