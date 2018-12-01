package com.sunflower.bulma.tools;

import io.mikael.urlbuilder.UrlBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author fuyongde
 * @desc URLBuilderTest
 * @date 2017/11/7 19:03
 */
public class UrlBuilderTest {

    public static final String URL_BAIDU = "http://www.baidu.com";

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testBuildURL() throws Exception {
        String baiduUrl = UrlBuilder.fromString(URL_BAIDU).toString();
        assertEquals(URL_BAIDU, baiduUrl);

        String baiduUrlWithParam = UrlBuilder.fromString(URL_BAIDU)
                .addParameter("name", "fuyongde").toString();
        assertEquals("http://www.baidu.com?name=fuyongde", baiduUrlWithParam);
    }
}
