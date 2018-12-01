package com.sunflower.bulma.tools;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * PageUtils Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>11/17/2017</pre>
 */
public class PageUtilsTest {

    private List<Integer> list;

    @Before
    public void before() throws Exception {
        list = Lists.newArrayList();
        for (int i = 1; i < 110; i++) {
            list.add(i);
        }
    }

    @After
    public void after() throws Exception {
        list = null;
    }

    /**
     * Method: getByPage(List<T> list, int pageIndex, int pageSize)
     */
    @Test
    public void testGetByPage() throws Exception {
        List<Integer> firstPage = PageUtils.getByPage(list, 0, 10);
        assertEquals("1,2,3,4,5,6,7,8,9,10", StringUtils.join(firstPage, ","));

        List<Integer> lastPage = PageUtils.getByPage(list, 10, 10);
        assertEquals("101,102,103,104,105,106,107,108,109", StringUtils.join(lastPage, ","));

        boolean hasMore = PageUtils.hasMore(list.size(), 10, 10);
        assertFalse(hasMore);

        hasMore = PageUtils.hasMore(list.size(), 9, 10);
        assertTrue(hasMore);

    }


} 
