package com.sunflower.bulma.tools;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Money2ChineseUtils Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/17/2017</pre>
 */
public class Money2ChineseUtilsTest {

    private BigDecimal moneyA;
    private BigDecimal moneyB;

    @Before
    public void before() throws Exception {
        moneyA = new BigDecimal(10000);
        moneyB = new BigDecimal(1000);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: toChinese(BigDecimal money)
     */
    @Test
    public void testToCN() throws Exception {
        String moneyACN = Money2ChineseUtils.toChinese(moneyA);
        String moneyBCN = Money2ChineseUtils.toChinese(moneyB);
        Assert.assertEquals("壹万元整", moneyACN);
        Assert.assertEquals("壹仟元整", moneyBCN);
    }

} 
