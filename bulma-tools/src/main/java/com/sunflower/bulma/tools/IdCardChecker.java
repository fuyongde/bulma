package com.sunflower.bulma.tools;

import com.google.common.collect.Lists;
import com.sunflower.bulma.tools.consts.DatePattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.sunflower.bulma.tools.consts.IdcardPattern.*;

/**
 * @author fuyongde
 * @desc 身份证的校验工具类
 * @date 2017/11/10 14:13
 */
public final class IdCardChecker {

    private IdCardChecker() {
    }

    /**
     * 验证所有的身份证的合法性
     *
     * @param idCard 身份证
     * @return 合法返回true，否则返回false
     */
    public static boolean isIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        if (idCard.length() == FIRST_IDCARD_LENGTH) {
            return is15IdCard(idCard);
        }
        return is18IdCard(idCard);
    }

    /**
     * 判断18位身份证的合法性 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
     * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
     *
     * <p>顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
     *
     * <p>1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码； 4.第7~14位数字表示：出生年、月、日；
     * 5.第15、16位数字表示：所在地的派出所的代码； 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
     * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
     *
     * <p>第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8
     * 4 2
     *
     * <p>2.将这17位数字和系数相乘的结果相加。
     *
     * <p>3.用加出来和除以11，看余数是多少 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
     * 2。
     *
     * <p>5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
     *
     * @param idCard 身份证号
     * @return true=是身份证号|false=不是身份证号
     */
    public static boolean is18IdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        // 非18位为假
        if (idCard.length() != SECOND_IDCARD_LENGTH) {
            return false;
        }
        // 获取前17位
        String idCard17 = idCard.substring(0, 17);

        // 前17位全部为数字
        if (!NumberChecker.isDigital(idCard17)) {
            return false;
        }

        String provinceCode = idCard.substring(0, 2);
        // 校验省份
        if (!checkProvinceCode(provinceCode)) {
            return false;
        }

        // 校验出生日期
        String birthday = idCard.substring(6, 14);

        try {
            // 若出生日期转化不了Date，则说明不是身份证
            DateUtils.parseDate(birthday, DatePattern.PATTERN_4);
        } catch (ParseException e) {
            return false;
        }

        // 获取第18位
        String idCard18Code = idCard.substring(17, 18);

        char[] idCard17Array = idCard17.toCharArray();

        int[] bit = convertCharToInt(idCard17Array);

        int sum17 = getPowerSum(bit);

        // 将和值与11取模得到余数进行校验码判断
        String checkCode = getCheckCodeBySum(sum17);
        if (null == checkCode) {
            return false;
        }
        // 将身份证的第18位与算出来的校码进行匹配，不相等就为假
        return idCard18Code.equalsIgnoreCase(checkCode);
    }

    /**
     * 校验15位身份证
     *
     * <p>
     *
     * <pre>
     * 只校验省份和出生年月日
     * </pre>
     *
     * @param idCard 身份证号
     * @return true=是身份证号|false=不是身份证号
     */
    public static boolean is15IdCard(String idCard) {
        if (idCard == null) {
            return false;
        }
        // 非15位为假
        if (idCard.length() != FIRST_IDCARD_LENGTH) {
            return false;
        }

        // 15全部为数字
        if (!NumberChecker.isDigital(idCard)) {
            return false;
        }

        String provinceCode = idCard.substring(0, 2);
        // 校验省份
        if (!checkProvinceCode(provinceCode)) {
            return false;
        }

        String birthday = idCard.substring(6, 12);

        try {
            // 若出生日期转化不了Date，则说明不是身份证
            DateUtils.parseDate(birthday, DatePattern.PATTERN_5);
        } catch (ParseException e1) {
            return false;
        }

        return true;
    }

    /**
     * 将15位的身份证转成18位身份证
     *
     * @param idCard 15位身份证号
     * @return 18位身份证号
     */
    public static String convertIdCardBy15bit(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return null;
        }

        // 非15位身份证
        if (idCard.length() != FIRST_IDCARD_LENGTH) {
            return null;
        }

        // 15全部为数字
        if (!NumberChecker.isDigital(idCard)) {
            return null;
        }

        String provinceCode = idCard.substring(0, 2);
        // 校验省份
        if (!checkProvinceCode(provinceCode)) {
            return null;
        }

        String birthday = idCard.substring(6, 12);

        Date birthDate;
        try {
            birthDate = DateUtils.parseDate(birthday, DatePattern.PATTERN_5);
        } catch (ParseException e1) {
            return null;
        }

        Calendar birthdayCalendar = Calendar.getInstance();
        birthdayCalendar.setTime(birthDate);
        String year = String.valueOf(birthdayCalendar.get(Calendar.YEAR));

        String idCard17 = idCard.substring(0, 6) + year + idCard.substring(8);

        char[] idCard17Array = idCard17.toCharArray();

        // 将字符数组转为整型数组
        int[] bit = convertCharToInt(idCard17Array);

        int sum17 = getPowerSum(bit);

        // 获取和值与11取模得到余数进行校验码
        String checkCode = getCheckCodeBySum(sum17);

        // 获取不到校验位
        if (null == checkCode) {
            return null;
        }
        // 将前17位与第18位校验码拼接
        idCard17 += checkCode;
        return idCard17;
    }

    /**
     * 校验省份
     *
     * @param provinceCode 省份编码
     * @return 合法返回TRUE|否则返回FALSE
     */
    private static boolean checkProvinceCode(String provinceCode) {
        return StringUtils.isNotBlank(provinceCode)
                && Lists.newArrayList(CITY_CODE).contains(provinceCode);
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit 身份证前17位
     * @return 和值
     */
    private static int getPowerSum(int[] bit) {

        int sum = 0;

        if (POWER.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < POWER.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * POWER[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17 前17位的和值
     * @return 校验位
     */
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
            default:
                break;
        }
        return checkCode;
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param chars 字符串数组
     * @return 整形数组
     * @throws NumberFormatException 该方法会抛出数字转化的异常
     */
    private static int[] convertCharToInt(char[] chars) throws NumberFormatException {
        int[] a = new int[chars.length];
        int k = 0;
        for (char temp : chars) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }
}
