package com.sunflower.bulma.tools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PinyinUtils Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/02/2017</pre>
 */
public class PinyinUtilsTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getAlpha(String chinese)
     */
    @Test
    public void testGetAlphaChinese() throws Exception {
        String s = "傅永德,lasfiL(）Ⅰ";
        String alpha = PinyinUtils.getAlpha(s);
        assertEquals("FYD", alpha);
    }

    /**
     * Method: getAllLetter(String input)
     */
    @Test
    public void testGetAllLetterInput() throws Exception {
        String s = "傅永德,lasfiL(）Ⅰ";
        String alpha = PinyinUtils.getAllLetter(s);
        assertEquals("fuyongdelasfiL", alpha);
    }


} 
