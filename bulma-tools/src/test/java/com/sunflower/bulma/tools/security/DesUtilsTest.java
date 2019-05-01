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
public class DesUtilsTest {

    private byte[] key;
    private String source = "fuyongde";

    @Before
    public void setUp() throws Exception {
        key = DesUtils.generateDesKey();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encryptAndDecrypt() {
        byte[] cipherData = DesUtils.encrypt(source.getBytes(), key);
        String sourceData = new String(DesUtils.decrypt(cipherData, key));
        assertEquals(sourceData, source);
    }

}