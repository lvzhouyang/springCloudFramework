package com.hearglobal.msp.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 随机验证码生成器
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
public class RandomUtil {
    /**
     * The Before shuffle.
     */
    static String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",    
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",    
            "W", "X", "Y", "Z" };

    /**
     * The Data shuffle.
     */
    static String[] dataShuffle = new String[] { "0","1","2", "3", "4", "5", "6", "7",
            "8", "9"};

    /**
     * 4位 字母和数字组合随机验证码生成器
     *
     * @return the shuffle
     */
    public static String getShuffle() {
	     List<String> list = Arrays.asList(beforeShuffle);
	     Collections.shuffle(list);
	     StringBuilder sb = new StringBuilder();
	     for (int i = 0; i < list.size(); i++) {    
	         sb.append(list.get(i));    
	     }    
	     String afterShuffle = sb.toString();
	     return  afterShuffle.substring(5, 9);    
	}

    /**
     * 6位 纯数字组合随机验证码生成器
     *
     * @return the data shuffle
     */
    public static String getDataShuffle() {
        List<String> list = Arrays.asList(dataShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        return  afterShuffle.substring(3, 9);
    }
}
