package com.sunflower.bulma.tools;

import org.apache.commons.lang3.StringUtils;

import static com.sunflower.bulma.tools.consts.PhonePattern.MOBILE_PATTERN;
import static com.sunflower.bulma.tools.consts.PhonePattern.TELEPHONE_PATTERN;

/**
 * @author fuyongde
 * @desc 电话号码检测
 * @date 2017/11/10 17:47
 */
public final class PhoneChecker {

    private PhoneChecker() {
    }

    /**
     * 验证手机号码
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param mobile 手机号码
     * @return true=是正确的手机号码|false=不是正确的手机号码
     */
    public static boolean isMobile(String mobile) {
        return StringUtils.isNotBlank(mobile) && mobile.matches(MOBILE_PATTERN);
    }

    /**
     * 验证固话号码
     *
     * @param telephone 固定电话的号码
     * @return true=是固定的电话号码|false=不是固定的电话号码
     */
    public static boolean isTelephone(String telephone) {
        return StringUtils.isNotBlank(telephone) && telephone.matches(TELEPHONE_PATTERN);
    }
}
