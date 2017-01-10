package com.hearglobal.msp.util;

import org.junit.Test;

/**
 * Created by lvzhouyang on 17/1/4.
 *
 * The tests for EncryptUtils
 *
 */
public class EncryptTest {


    @Test
    public void test() throws Exception {
        System.out.println(EncryptUtil.encrypt("123456"));
        System.out.println(EncryptUtil.decrypt("oqGiG3w2C/s4l945xI++My4Wpv2cCyLi"));
    }
}
