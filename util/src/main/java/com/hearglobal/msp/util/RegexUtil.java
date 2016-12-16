package com.hearglobal.msp.util;

/**
 * 集中一些常用的正则
 * 待写测试用例
 */
public class RegexUtil {

    /**
     * 只能是字母a-z, A-Z
     */
    public static final String onlyAlphaRgx = "^[a-zA-Z]+$";

    /**
     * 只能是英文字母或是数字
     */
    public static final String alphaOrNumRgx = "^[a-zA-Z0-9]+$";

    /**
     * 只能是数字，不能有小数点
     */
    public static final String onlyIntegerRgx = "^[\\d]+$";

    /**
     * 只能输入整数(含负数)
     * valid 是指整体的，不是指单个的
     */
    public static final String onlyValidIntegerRgx = "^[-+]?[1-9]\\d*$|^0$/";

    /**
     * 只能输入汉字
     */
    public static final String onlyChineseRgx = "^[\u4e00-\u9fa5]+$";

    /**
     * 判断邮箱格式是否正确
     */
    public static final String emailRgx = "\\w{1,}[@][\\w\\-]{1,}([.]([\\w\\-]{1,})){1,3}$";

    /**
     * 检测ip地址
     */
    public static final String ipRgx = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    /**
     * 检查手机号
     */
    public static final String phoneNumRgx = "(^0?[1][35][0-9]{9}$)";

    /**
     * 固定电话
     * 0100-82839681
     */
    public static final String fixedPhoneNumRgx = "^((0[1-9]{3})?(0[12][0-9])?[-])?\\d{6,8}$";

    /**
     * 邮编 100083
     */
    public static final String postCodeRgx = "^[1-9]\\d{5}$";

}
