package com.sunflower.bulma.tools;

import org.apache.commons.lang3.StringUtils;

import static com.sunflower.bulma.tools.consts.NumberPattern.ALL_DIGITAL_PATTERN;

/**
 * @author fuyongde
 * @desc 数字校验
 * @date 2017/11/10 15:38
 */
public final class NumberChecker {

    private NumberChecker() {
    }

    /**
     * 数字验证
     *
     * @param number 字符串类型的数字
     * @return true=是数字|false=不是数字
     */
    public static boolean isDigital(String number) {
        return StringUtils.isNotBlank(number) && number.matches(ALL_DIGITAL_PATTERN);
    }
}
