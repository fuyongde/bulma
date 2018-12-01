package com.sunflower.bulma.tools;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * PhoneChecker Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/10/2017</pre>
 */
public class PhoneCheckerTest {

    private static final Logger logger = LoggerFactory.getLogger(PhoneChecker.class);

    private List<String> mobileList = Lists.newArrayList();
    private List<String> notMobileList = Lists.newArrayList();

    private List<String> telephoneList = Lists.newArrayList();
    private List<String> notTelephoneList = Lists.newArrayList();

    @Before
    public void before() throws Exception {
        mobileList.add("18538182601");
        mobileList.add("17688972601");
        mobileList.add("18538182603");

        notMobileList.add("11010000001");
        notMobileList.add("11010000002");
        notMobileList.add("11010000003");

        telephoneList.add("0371-62601215-0001");
        telephoneList.add("0371-62601216-0001");
        telephoneList.add("0371-62601217-0001");

        notTelephoneList.add("1234");
        notTelephoneList.add("5678");
        notTelephoneList.add("1298");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: isMobile(String mobile)
     */
    @Test
    public void testIsMobile() throws Exception {
        logger.info("-----{}-----", "正确的手机号");
        for (String mobile : mobileList) {
            boolean isMobile = PhoneChecker.isMobile(mobile);
            logger.info("{} is mobile : {}", mobile, isMobile);
            assertTrue(isMobile);
        }

        logger.info("-----{}-----", "错误的身份证");
        for (String mobile : notMobileList) {
            boolean isMobile = PhoneChecker.isMobile(mobile);
            logger.info("{} is mobile : {}", mobile, isMobile);
            assertFalse(isMobile);
        }
    }

    /**
     * Method: isTelephone(String telephone)
     */
    @Test
    public void testIsTelephone() throws Exception {
        logger.info("-----{}-----", "正确的电话号码");
        for (String telephone : telephoneList) {
            boolean isTelephone = PhoneChecker.isTelephone(telephone);
            logger.info("{} is telephone : {}", telephone, isTelephone);
            assertTrue(isTelephone);
        }

        logger.info("-----{}-----", "错误的电话号码");
        for (String telephone : notTelephoneList) {
            boolean isTelephone = PhoneChecker.isTelephone(telephone);
            logger.info("{} is telephone : {}", telephone, isTelephone);
            assertFalse(isTelephone);
        }
    }

} 
