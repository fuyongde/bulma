package com.sunflower.bulma.tools.security;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author fuyongde
 * @date 2018-12-04
 * @desc 散列单元测试
 */
public class DigestsTest {

    private static Logger logger = LoggerFactory.getLogger(DigestsTest.class);

    private String source = "fuyongde";

    @Test
    public void sha1() {
        String sha1 = Digests.sha1Hex(source);
        assertEquals("14b4e01f06df29a2389654f52f301043250a5f85", sha1);
    }

    @Test
    public void md5() {
        String md5 = Digests.md5Hex(source);
        assertEquals("45feb88317626c811a9ad0d049598d77", md5);
    }
}