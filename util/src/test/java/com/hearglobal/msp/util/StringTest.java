package com.hearglobal.msp.util;
import org.junit.Test;

import static com.hearglobal.msp.util.StringUtil.camelhumpToUnderline;

/**
 * Created by ccg on 2017/1/8.
 */
public class StringTest {
    @Test
    //测试正则匹配
    public void regexMatchTest(){
        String match = RegexUtil.alphaOrNumRgx;
        String regex = "123456";
        System.out.println(StringUtil.regexMatch(regex,match));
    }
    
    @Test
    //测试替换固定格式的字符串
    public void replaceAllWithRegexTest(){
        String str = "123456";
        String regex = "34";
        System.out.println(StringUtil.replaceAllWithRegex(str,regex,"3+4"));
    }
    
    @Test
    //测试驼峰规则的字符串转换成下划线的
    public void camelhumpToUnderlineTest(){
        String str = "adminGuest";
        System.out.println(camelhumpToUnderline(str));
    }
    
    @Test
    //测试下划线的字符串转换成驼峰规则的
    public void underlineToCamelhumpTest(){
        String str = "admin_guest";
        System.out.println(StringUtil.underlineToCamelhump(str));
    }
    
    @Test
    //测试首字母大写
    public void firstToUpperTest(){
        String str = "admin_guest";
        System.out.println(StringUtil.firstToUpper(str));
    }
      @Test
    //测试首字母小写
    public void firstToLower(){
        String str = "Admin_guest";
        System.out.println(StringUtil.firstToLower(str));
    }
}

