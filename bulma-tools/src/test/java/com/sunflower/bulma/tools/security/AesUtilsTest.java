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
public class AesUtilsTest {

    private byte[] key;
    private byte[] iv;

    private static final String SOURCE = "fuyongde";

    private static final String KEY_DEFAULT = "oyuFjIl7vFGlfL6YPdqQAA==";
    private static final String IV_DEFAULT = "yQF7Zxh6nlQb3eA1icji1g==";

    @Before
    public void setUp() throws Exception {
        key = AesUtils.generateAesKey();
        iv = AesUtils.generateIV();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encryptAndDecrypt() {
        byte[] cipherData = AesUtils.encrypt(SOURCE.getBytes(), key);
        String sourceData = new String(AesUtils.decrypt(cipherData, key));
        assertEquals(sourceData, SOURCE);

        String miwen = AesUtils.encrypt(SOURCE, KEY_DEFAULT);
        String mingwen = AesUtils.decrypt(miwen, KEY_DEFAULT);
        assertEquals(SOURCE, mingwen);
    }

    @Test
    public void encryptAndDecrypt2() {
        byte[] cipherData = AesUtils.encrypt(SOURCE.getBytes(), key, iv);
        String sourceData = new String(AesUtils.decrypt(cipherData, key, iv));
        assertEquals(sourceData, SOURCE);

        String miwen = AesUtils.encrypt(SOURCE, KEY_DEFAULT, IV_DEFAULT);
        String mingwen = AesUtils.decrypt(miwen, KEY_DEFAULT, IV_DEFAULT);
        assertEquals(SOURCE, mingwen);
    }

}