package com.hearglobal.msp.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 封装数组/list 与string的各种转换操作
 */
final public class ArrayUtil {

    /**
     * 获得整数数组和
     *
     * @param arr
     * @return
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
     * @param list
     * @return
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
            } else {
                buf.append("," + "\"" + id + "\"");
                idList.add(id);
            }
        }

        String bufs = " (" + buf.toString().substring(1) + ") ";
        return bufs;
    }

    /**
     * 获取元素唯一的格式为(1,2,3,4)的string
     * refer to Restrictions.in
     *
     * @param arr
     * @return
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
            } else {
                buf.append("," + "\"" + id + "\"");
                idList.add(id);
            }
        }

        String bufs = " (" + buf.toString().substring(1) + ") ";
        return bufs;
    }

    /**
     * 获取反射元素list
     * refer to Restrictions.in
     *
     * @param elements
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
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

    public static <T> String getFieldSqlString(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        List<Object> objectlist = getFieldValueList(elements, fieldName);
        String sql = getUniqueSqlString(objectlist);
        return sql;
    }

    /**
     * 获取反射元素list
     * refer to Restrictions.in
     *
     * @param elements
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Object> getFieldValueList(List<T> elements, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }

        List<Object> list = new ArrayList<Object>();
        for (T e : elements) {
            Class ownerClass = e.getClass();
            Field field = ownerClass.getField(fieldName);
            Object property = field.get(e);
            list.add(property);
        }
        return list;
    }

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

    public static <T> String list2UniqStr(List<T> list, String split) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Set<String> set = new HashSet<String>();
        for (T e : list) {
            if (e != null) {
                set.add(e.toString());
            }
        }
        StringBuilder buf = new StringBuilder();
        for (String e : set) {
            buf.append(split + e);
        }
        return buf.substring(split.length());
    }

}
