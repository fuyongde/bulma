package com.sunflower.bulma.tools;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author fuyongde
 * @date 2018-12-02
 * @desc 日期的工具类
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 计算两个date的持续时间
     *
     * @param start 开始时间
     * @param end 结束时间
     * @return
     */
    public static Duration duration(Date start, Date end) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime startDateTime = start.toInstant().atZone(zoneId).toLocalDateTime();
        LocalDateTime endDateTime = end.toInstant().atZone(zoneId).toLocalDateTime();
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration;
    }
}
