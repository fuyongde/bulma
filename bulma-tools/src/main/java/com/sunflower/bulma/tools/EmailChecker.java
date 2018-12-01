package com.sunflower.bulma.tools;

import org.apache.commons.lang3.StringUtils;

import static com.sunflower.bulma.tools.consts.EmailPattern.EMAIL_PATTERN;

/**
 * @author fuyongde
 * @desc 邮箱验证的工具类
 * @date 2017/11/9 19:32
 */
public final class EmailChecker {

    private EmailChecker() {
    }

    /**
     * 是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        return StringUtils.isNotBlank(email) && email.matches(EMAIL_PATTERN);
    }
}
