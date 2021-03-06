package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装数组/list 与string的各种转换操作
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.21
 */
final public class ArrayUtil {

    /**
     * 获得整数数组和
     *
     * @param arr the arr
     * @return sum
     */
    public static Integer getSum(Integer[] arr) {
        int sum = 0;
        if (arr == null || arr.length < 1) {
            return sum;
        }
        for (Integer num : arr) {
            sum += num;
        }
        return sum;
    }

    /**
     * 获取元素唯一的格式为(1,2,3,4)的string
     * refer to Restrictions.in
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return unique sql string
     */
    public static <T> String getUniqueSqlString(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<Object> idList = Lists.newArrayList();
        StringBuilder buf = new StringBuilder();
        for (Object id : list) {
            if (idList.indexOf(id) >= 0) {
                continue;
            }

            buf.append("," + "\"").append(id).append("\"");
            idList.add(id);
        }

        String bufs = " (" + buf.toString().substring(1) + ") ";
        return bufs;
    }

    /**
     * 获取元素唯一的格式为(1,2,3,4)的string
     * refer to Restrictions.in
     *
     * @param arr the arr
     * @return unique sql string
     */
    public static String getUniqueSqlString(Object[] arr) {
        if (arr == null || ArrayUtils.isEmpty(arr)) {
            return null;
        }
        List<Object> idList = Lists.newArrayList();
        StringBuilder buf = new StringBuilder();
        for (Object id : arr) {
            if (idList.indexOf(id) >= 0) {
                continue;
            }
            buf.append("," + "\"").append(id).append("\"");
            idList.add(id);
        }

        return " (" + buf.toString().substring(1) + ") ";
    }

    /**
     * 获取反射元素list
     * refer to Restrictions.in
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return declared field value list
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> List<Object> getDeclaredFieldValueList(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }

        List<Object> list = Lists.newArrayList();
        for (T e : elements) {
            Class ownerClass = e.getClass();
            Field field = ownerClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object property = field.get(e);
            list.add(property);
        }
        return list;
    }

    /**
     * Gets field sql string.
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return the field sql string
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> String getFieldSqlString(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        List<Object> objectList = getFieldValueList(elements, fieldName);
        return getUniqueSqlString(objectList);
    }

    /**
     * 获取反射元素list
     * refer to Restrictions.in
     *
     * @param <T>       the type parameter
     * @param elements  the elements
     * @param fieldName the field name
     * @return field value list
     * @throws SecurityException        the security exception
     * @throws NoSuchFieldException     the no such field exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException   the illegal access exception
     */
    public static <T> List<Object> getFieldValueList(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }

        List<Object> list = Lists.newArrayList();
        for (T e : elements) {
            Class ownerClass = e.getClass();
            Field field = ownerClass.getField(fieldName);
            Object property = field.get(e);
            list.add(property);
        }
        return list;
    }

    /**
     * String 数组去重
     *
     * @param arr the arr
     * @return the uniq list
     */
    public static List<String> getUniqList(String[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }

        List<String> list = Lists.newArrayList();
        Set<String> set = Sets.newHashSet();
        for (String e : arr) {
            if (e.length() > 0) {
                set.add(e);
            }
        }
        list.addAll(set);
        return list;
    }

    /**
     * List去重 按指定符号拼接
     *
     * @param <T>   the type parameter
     * @param list  the list
     * @param split the split
     * @return the string
     * @since 2017.03.21
     */
    public static <T> String list2UniqStr(List<T> list, String split) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Set<String> set = Sets.newHashSet();
        set.addAll(list.stream().filter(e -> e != null).map(Object::toString).collect(Collectors.toList()));
        StringBuilder buf = new StringBuilder();
        for (String e : set) {
            buf.append(split).append(e);
        }
        return buf.substring(split.length());
    }

}
