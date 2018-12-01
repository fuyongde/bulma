package com.sunflower.bulma.tools;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * EmailChecker Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/09/2017</pre>
 */
public class EmailCheckerTest {

    private static final Logger logger = LoggerFactory.getLogger(EmailChecker.class);

    private List<String> emailList = Lists.newArrayList();
    private List<String> notEmailList = Lists.newArrayList();

    @Before
    public void before() throws Exception {
        emailList.add("fuyongde@foxmail.com");
        emailList.add("fu.yongde@163.com");
        emailList.add("fu_yongde@163.com");

        notEmailList.add("abc");
        notEmailList.add("abc@ddd");
        notEmailList.add("sdf-sfds@#dsf");
        notEmailList.add("傅永德@foxmail.com");
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: isEmail(String email)
     */
    @Test
    public void testIsEmail() throws Exception {

        for (String email : emailList) {
            boolean isEmail = EmailChecker.isEmail(email);
            logger.info("{} is email : {}", email, isEmail);
            assertTrue(isEmail);
        }

        for (String email : notEmailList) {
            boolean isEmail = EmailChecker.isEmail(email);
            logger.info("{} is email : {}", email, isEmail);
            assertFalse(isEmail);
        }
    }


} 
