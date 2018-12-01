package com.sunflower.bulma.tools.consts;

/**
 * @author fuyongde
 * @desc 电话号码正则
 * @date 2017/11/10 17:48
 */
public interface PhonePattern {

    /**
     * 手机号码正则
     */
    String MOBILE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9])|(17[0-1,6-8]))\\d{8}$";

    /**
     * 固定电话正则
     */
    String TELEPHONE_PATTERN = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
}
