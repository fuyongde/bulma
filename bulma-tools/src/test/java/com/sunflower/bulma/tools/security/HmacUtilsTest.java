package com.sunflower.bulma.tools.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author fuyongde
 * @date 2018-12-01
 * @desc HMAC签名验签
 */
public class HmacUtilsTest {

    private byte[] key;
    private String source = "fuyongde";

    @Before
    public void setUp() throws Exception {
        key = HmacUtils.generateHmacSha1Key();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sign() {
        byte[] sign = HmacUtils.sign(source.getBytes(), key);
        boolean verify = HmacUtils.isMacValid(sign, source.getBytes(), key);
        assertTrue(verify);
    }

}