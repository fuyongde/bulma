package com.sunflower.bulma.tools.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc DES加解密
 */
public class DESUtilsTest {

    private byte[] key;
    private String source = "fuyongde";

    @Before
    public void setUp() throws Exception {
        key = DESUtils.generateDesKey();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encryptAndDecrypt() {
        byte[] cipherData = DESUtils.encrypt(source.getBytes(), key);
        String sourceData = DESUtils.decrypt(cipherData, key);
        assertEquals(sourceData, source);
    }

}