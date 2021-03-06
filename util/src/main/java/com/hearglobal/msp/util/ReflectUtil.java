/*
 * Copyright (c) 2010-2011 meituan.com
 * All rights reserved.
 * 
 */
package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射工具类
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
public class ReflectUtil {
    /**
     * The constant log.
     */
    private final static Logger log = LoggerFactory.getLogger(ReflectUtil.class);

    /**
     * 得到一个类的所有get方法.不包含get()
     *
     * @param clazz the clazz
     * @return method [ ]
     * @since 2017.03.21
     */
    public static Method[] getPublicGetMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methodList = Lists.newArrayList();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName != null && !StringUtils.equals("get",methodName) && methodName.startsWith("get")) {
                methodList.add(method);
            }
        }
        return methodList.toArray(new Method[methodList.size()]);
    }

    /**
     * 得到一个类的所有set方法.不包含set()
     *
     * @param clazz the clazz
     * @return method [ ]
     * @since 2017.03.21
     */
    public static Method[] getPublicSetMethods(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methodList = Lists.newArrayList();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName != null && !StringUtils.equals("set",methodName) && methodName.startsWith("set")) {
                methodList.add(method);
            }
        }
        return methodList.toArray(new Method[methodList.size()]);
    }

    /**
     * 得到get方法对应的set方法,get的返回值和set方法的参数必须一样
     *
     * @param getMethod the get method
     * @param clazz     the clazz
     * @return set method 4 get method
     */
    public static Method getSetMethod4GetMethod(Method getMethod, Class<?> clazz) {
        String setMethodName = getMethod.getName().replaceFirst("get", "set");
        return ReflectionUtils.findMethod(clazz, setMethodName,
                getMethod.getReturnType());
    }

    /**
     * 得到set方法对应的get方法,get方法没有参数
     *
     * @param setMethod the set method
     * @param clazz     the clazz
     * @return get method 4 set method
     */
    public static Method getGetMethod4SetMethod(Method setMethod, Class<?> clazz) {
        String getMethodName = setMethod.getName().replaceFirst("set", "get");
        return ReflectionUtils.findMethod(clazz, getMethodName);
    }

    /**
     * 得到属性的get方法名称
     *
     * @param fieldName the field name
     * @return get method name 4 field
     */
    public static String getGetMethodName4Field(String fieldName) {
        if (fieldName.length() == 0) {
            return "get";
        } else if (fieldName.length() == 1) {
            return "get" + fieldName.toUpperCase();
        } else {
            return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
    }

    /**
     * 得到属性的set方法名称
     *
     * @param fieldName the field name
     * @return set method name 4 field
     */
    public static String getSetMethodName4Field(String fieldName) {
        if (fieldName.length() == 0) {
            return "set";
        } else if (fieldName.length() == 1) {
            return "set" + fieldName.toUpperCase();
        } else {
            return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
    }

    /**
     * 得到对象某个属性的get方法
     *
     * @param clazz     the clazz
     * @param fieldName the field name
     * @return get method 4 field
     * @throws SecurityException     the security exception
     * @throws NoSuchMethodException the no such method exception
     */
    public static Method getGetMethod4Field(Class<?> clazz, String fieldName)
            throws SecurityException, NoSuchMethodException {
        return clazz.getDeclaredMethod(getGetMethodName4Field(fieldName));
    }


    /**
     * 通过对象查询对象的属性值
     *
     * @param <T>      the type parameter
     * @param obj      the obj
     * @param property the property
     * @return value by property
     * @throws SecurityException         the security exception
     * @throws NoSuchMethodException     the no such method exception
     * @throws IllegalArgumentException  the illegal argument exception
     * @throws IllegalAccessException    the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    public static <T> Object getValueByProperty(T obj, String property) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        return getGetMethod4Field(obj.getClass(), property).invoke(obj);
    }


    /**
     * 获得切面截取的方法对象
     *
     * @param j the j
     * @return method
     * @throws ClassNotFoundException the class not found exception
     * @throws SecurityException      the security exception
     * @throws NoSuchMethodException  the no such method exception
     */
    public static Method getMethod(ProceedingJoinPoint j) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
        // 方法签名对象
        Signature s = j.getSignature();
        // 方法声明的参数类型
        Class<?>[] paramClazz = getParamClass(s);
        // 方法所属类
        Class<?> clazz = s.getDeclaringType();
        // 方法对象
        return clazz.getMethod(s.getName(), paramClazz);
    }

    /**
     * 根据签名对象获得其声明的参数类型数组
     *
     * @param s the s
     * @return class [ ]
     * @throws ClassNotFoundException the class not found exception
     * @since 2017.03.21
     */
    private static Class<?>[] getParamClass(Signature s) throws ClassNotFoundException {
        Class<?>[] paramClass;
        Pattern p = Pattern.compile("(?<=\\()\\S+(?=\\))");
        Matcher m = p.matcher(s.toLongString());
        String[] classNames = null;
        if (m.find()) {
            classNames = m.group().split(",");
        }
        if (classNames != null) {
            paramClass = new Class<?>[classNames.length];
            for (int i = 0; i < paramClass.length; i++) {
                paramClass[i] = getClass(classNames[i]);
            }
        } else {
            paramClass = new Class<?>[]{};
        }
        return paramClass;
    }

    /**
     * 根据字符串转换为对应的类对象
     *
     * @param className the class name
     * @return class
     * @throws ClassNotFoundException the class not found exception
     */
    private static Class<?> getClass(String className) throws ClassNotFoundException {
        Class<?> clazz = null;
        Class<?>[] classes = new Class<?>[]{byte.class, short.class, int.class, long.class, char.class, boolean.class, float.class, double.class};
        for (Class<?> c : classes) {
            if (className.equals(c.toString())) {
                clazz = c;
                break;
            }
        }
        if (className.indexOf("[]") > 0) {
            int s = className.indexOf("[]");
            int e = className.lastIndexOf("[]");
            clazz = Class.forName(className.replace("[]", ""));
            Object arr = Array.newInstance(clazz, new int[(e - s) / 2 + 1]);
            clazz = arr.getClass();
        }
        if (clazz == null) {
            clazz = Class.forName(className);
        }
        return clazz;
    }
}
