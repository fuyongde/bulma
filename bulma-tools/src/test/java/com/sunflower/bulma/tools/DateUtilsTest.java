package com.sunflower.bulma.tools;

import com.sunflower.bulma.tools.consts.DatePattern;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author fuyongde
 * @desc DateUtils的用法
 * @date 2017/11/7 11:40
 */
public class DateUtilsTest {


    private Date date;

    @Before
    public void before() throws Exception {
        date = new Date();
    }

    @After
    public void after() throws Exception {

    }

    @Test
    public void testIsSameDay() throws Exception {
        Date today = new Date();
        boolean isSameDay = DateUtils.isSameDay(date, today);
        assertTrue(isSameDay);

        Date yesterday = DateUtils.addDays(today, -1);
        isSameDay = DateUtils.isSameDay(date, yesterday);
        assertFalse(isSameDay);
    }

    @Test
    public void testParse() throws Exception {
        Date date = DateUtils.parseDate("2017-11-07 11:47:59", DatePattern.PATTERN_1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2017, calendar.get(Calendar.YEAR));
    }

    @Test
    public void duration() throws Exception {
        Date from = null;
        Date to = null;
        Duration duration = null;
        from = DateUtils.parseDate("2018-11-30 23:59:59", DatePattern.PATTERN_1);
        to = DateUtils.parseDate("2018-12-01 00:00:00", DatePattern.PATTERN_1);
        duration = DateUtils.duration(from, to);
        assertEquals(1000L, duration.toMillis());

        to = DateUtils.parseDate("2018-12-01 23:59:59", DatePattern.PATTERN_1);
        duration = DateUtils.duration(from, to);
        assertEquals(1L, duration.toDays());

        from = DateUtils.parseDate("2018-11-30 23:59:59", DatePattern.PATTERN_1);
        to = DateUtils.parseDate("2018-12-01 01:00:00", DatePattern.PATTERN_1);
        duration = DateUtils.duration(from, to);
        assertEquals(1L, duration.toHours());

        from = DateUtils.parseDate("2018-12-01 01:00:00", DatePattern.PATTERN_1);
        to = DateUtils.parseDate("2018-11-30 23:59:59", DatePattern.PATTERN_1);
        duration = DateUtils.duration(from, to);
        assertEquals(-1L, duration.toHours());
    }
}
