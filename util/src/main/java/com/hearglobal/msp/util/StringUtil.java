package com.hearglobal.msp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 扩展StringUtil 工具类
 * Created by lvzhouyang on 16/12/20.
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils{

    /**
     * 正则匹配
     *
     * @param originStr the origin str
     * @param matchStr  the match str
     * @return boolean
     * @since 2017.03.21
     */
    public static boolean regexMatch(String originStr, String matchStr){
        if (StringUtil.isEmpty(originStr)
                && StringUtil.isNotEmpty(matchStr)){
            return false;
        }

        if (StringUtil.isEmpty(matchStr)){
            return false;
        }

        return originStr.matches(matchStr);
    }

    /**
     * 替换固定格式的字符串（支持正则表达式）
     *
     * @param str         the str
     * @param regex       the regex
     * @param replacement the replacement
     * @return string
     * @since 2017.03.21
     */
    public static String replaceAllWithRegex(String str, String regex, String replacement) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 将驼峰风格替换为下划线风格
     *
     * @param str the str
     * @return string
     * @since 2017.03.21
     */
    public static String camelhumpToUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() + i, matcher.end() + i, "_" + matcher.group().toLowerCase());
        }
        if (builder.charAt(0) == '_') {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }

    /**
     * 将下划线风格替换为驼峰风格
     *
     * @param str the str
     * @return string
     * @since 2017.03.21
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    /**
     * 将字符串首字母大写
     *
     * @param str the str
     * @return string
     * @since 2017.03.21
     */
    public static String firstToUpper(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 将字符串首字母大写
     *
     * @param str the str
     * @return string
     * @since 2017.03.21
     */
    public static String firstToLower(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
