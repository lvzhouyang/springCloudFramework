package com.hearglobal.msp.util;

import com.hearglobal.msp.bean.User;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ccg on 2017/1/9.
 *
 * The tests for ReflectUtils
 *
 */
public class ReflectTest {

    User user = new User();

    @Before
    public void init() {
        user.setUserName("ccg123");
    }

    @Test
    //得到一个类中声明的get方法
    public void getPublicGetMethodsTest() {
        System.out.println(ObjectUtil.toString(ReflectUtil.getPublicGetMethods(User.class)));
    }

    @Test
    //得到一个类中声明的set方法
    public void getPublicSetMethodTest() {
        System.out.println(ObjectUtil.toString(ReflectUtil.getPublicSetMethods(User.class)));
    }

    @Test
    //根据类中的某一个get方法得到他的set方法
    public void getSetMethod4GetMethodTest() throws NoSuchMethodException {
        Method setmethod = ReflectUtil.getSetMethod4GetMethod(
                //declaredMethod 包含私有方法
                //getSetMethod4GetMethod(所需类中属性的get方法,所需类.class)
                user.getClass().getDeclaredMethod("getUpdateTime"), user.getClass()
        );
        System.out.println(ObjectUtil.toString(setmethod));
    }

    @Test
    //根据类中的某一个set方法得到他的get方法
    public void getGetMethod4SetMethodTest() throws NoSuchMethodException {
        Method getMethod = ReflectUtil.getGetMethod4SetMethod(
                //declaredMethod 包含私有方法
                //getGetMethod4SetMethodTest(所需类中属性的set方法,所需类.class)
                user.getClass().getDeclaredMethod("setPassword", String.class), user.getClass()
        );
        System.out.println(ObjectUtil.toString(getMethod));
    }

    @Test
    //根据指定 Field 名字取得反射的属性 get方法名字
    public void getGetMethodName4FieldTest() throws NoSuchFieldException {
        String fieldName = user.getClass().getDeclaredField("userName").getName();
        System.out.println(ObjectUtil.toString(ReflectUtil.getGetMethodName4Field(fieldName)));
    }

    @Test
    //根据指定 Field 名字取得反射的属性 set方法名字
    public void getSetMethodName4FieldTest() throws NoSuchFieldException {
        String fieldName = user.getClass().getDeclaredField("userName").getName();
        System.out.println(ObjectUtil.toString(ReflectUtil.getSetMethodName4Field(fieldName)));
    }

    @Test
    //根据指定 Field 名字取得反射的属性 get方法
    public void getGetMethod4FieldTest() throws NoSuchFieldException, NoSuchMethodException {
        String fieldName = user.getClass().getDeclaredField("userName").getName();
        System.out.println(ObjectUtil.toString(ReflectUtil.getGetMethod4Field(user.getClass(), fieldName)));
    }

    @Test
    //根据指定对象的某个属性获取该对象的属性值
    public void getValueByPropertyTest() throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(ObjectUtil.toString(ReflectUtil.getValueByProperty(user, "userName")));
    }
}

