package com.hearglobal.msp.util;

import org.junit.Test;

/**
 * Created by ccg on 2017/1/8.
 *
 * The tests for RandomUtils
 *
 */
public class RandomTest {
    @Test
//    测试产生四位随机数据 数字加字母
    public void getShuffleTest(){
        System.out.println(RandomUtil.getShuffle());
    }
    
    
    @Test
//    测试产生六位随机数据 数字
    public void getDataShuffleTest(){
        System.out.println(RandomUtil.getDataShuffle());
    }
}
