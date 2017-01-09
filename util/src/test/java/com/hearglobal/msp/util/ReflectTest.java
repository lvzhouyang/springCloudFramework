package com.hearglobal.msp.util;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import org.junit.Test;

/**
 * Created by Administrator on 2017/1/9.
 */
public class ReflectTest {
    @Test
    //得到一个类中声明的公共get方法
    public void getPublicGetMethodsTest() {
        StringTest stringTest = new StringTest();
        System.out.println(
                ObjectUtil.toString(
                        ReflectUtil.getPublicGetMethods(
                                stringTest.getClass()
                        )
                )
        );
    }
    @Test
    //得到一个类中声明的公共set方法
    public void getPublicSetMethodTest(){
        StringTest stringTest = new StringTest();
        System.out.println(ObjectUtil.toString(
                ReflectUtil.getPublicSetMethods(stringTest.getClass())
        ));
    }
}

