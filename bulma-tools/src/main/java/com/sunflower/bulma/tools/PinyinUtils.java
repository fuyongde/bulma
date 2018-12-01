package com.sunflower.bulma.tools;

import com.google.common.collect.Lists;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.sunflower.bulma.tools.consts.PinyinPattern.*;

/**
 * @author fuyongde
 * @desc 汉字转拼音的工具类
 * @date 2017/11/1 17:30
 */
public final class PinyinUtils {

    private static final Logger logger = LoggerFactory.getLogger(PinyinUtils.class);

    private PinyinUtils() {
    }

    /**
     * 获取汉字的首字母
     *
     * @param chinese 汉字
     * @return 汉字的拼音首字母
     */
    public static String getAlpha(String chinese) {

        List<String> excludeRegexList = Lists.newArrayList(
                REGEX_CNTRL,
                REGEX_PUNCT,
                REGEX_SPACE,
                REGEX_ROMAN_NUMERALS
        );

        return getAlpha(chinese, excludeRegexList);
    }

    /**
     * 获取汉字的首字母
     *
     * @param chinese          汉字
     * @param excludeRegexList 需要剔除掉的字符的正则
     * @return 汉字的首字母
     */
    public static String getAlpha(String chinese, List<String> excludeRegexList) {

        Validate.notBlank(chinese);

        if (CollectionUtils.isNotEmpty(excludeRegexList)) {
            for (String regex : excludeRegexList) {
                chinese = chinese.replaceAll(regex, NON);
            }
        }

        char[] chars = chinese.trim().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char tmp : chars) {
            if (Character.toString(tmp).matches(REGEX_CHINESE)) {
                sb.append(PinyinHelper.toHanyuPinyinStringArray(tmp)[0].charAt(0));
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将汉语转为拼音字母，英文不变
     *
     * @param input 待处理的字符
     * @return 汉字的所有拼音
     */
    public static String getAllLetter(String input) {
        List<String> excludeRegexList = Lists.newArrayList(
                REGEX_CNTRL,
                REGEX_PUNCT,
                REGEX_SPACE,
                REGEX_ROMAN_NUMERALS
        );

        return getAllLetter(input, excludeRegexList);
    }

    /**
     * 将汉语转为拼音字母，英文不变
     *
     * @param input            待处理的字符
     * @param excludeRegexList 要过滤掉的特殊字符的正则
     * @return 汉字的所有拼音
     */
    public static String getAllLetter(String input, List<String> excludeRegexList) {
        Validate.notBlank(input);
        if (!CollectionUtils.isEmpty(excludeRegexList)) {
            for (String regex : excludeRegexList) {
                input = input.replaceAll(regex, NON);
            }
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] chars = input.trim().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char tmp : chars) {
            if (Character.toString(tmp).matches(REGEX_CHINESE)) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(tmp, format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    logger.error("getAllLetter error, input is : {}, bad char is : {}", input, tmp);
                }
            } else {
                sb.append(tmp);
            }
        }
        return sb.toString();
    }
}
