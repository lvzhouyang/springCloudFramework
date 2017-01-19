package com.hearglobal.msp.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

/**
 * 基础对象操作
 */
public class ObjectUtil {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 设置对象field的默认值(跳过id域及非空域)
     * <p/>
     * String = "", Integer=0, Long = 0, Boolean = false
     *
     * @param obj
     */
    public static void setDefault(Object obj) {
        try {
            for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(obj);
                // 跳过id，自增长主键
                if (!StringUtils.equals("id", field.getName()) && object == null) {
                    setDefault(field, obj);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 设置对象field的默认值(跳过id域、非空域及其excludeFields,)
     * <p/>
     * String = "", Integer=0, Long = 0, Boolean = false
     *
     * @param obj
     */
    public static void setDefaultExcludeFields(Object obj, String... excludeFields) {
        try {
            List<String> fieldNameList = Arrays.asList(excludeFields);
            for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(obj);
                // 跳过id，自增长主键
                if (!StringUtils.equals("id", field.getName()) && !fieldNameList.contains(field.getName()) && object == null) {
                    setDefault(field, obj);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private static void setDefault(java.lang.reflect.Field field, Object obj) throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(obj, "");
        }
        if (field.getType().equals(Long.class)) {
            field.set(obj, 0L);
        }
        if (field.getType().equals(Integer.class)) {
            field.set(obj, 0);
        }
        if (field.getType().equals(Byte.class)) {
            field.set(obj, (byte) 0);
        }
        if (field.getType().equals(Boolean.class)) {
            field.set(obj, false);
        }
        if (field.getType().equals(Date.class)) {
            field.set(obj, new Date());
        }
        if (field.getType().equals(Float.class)) {
            field.set(obj, 0f);
        }
        if (field.getType().equals(BigDecimal.class)) {
            field.set(obj, BigDecimal.ZERO);
        }
        if (field.getType().equals(Double.class)) {
            field.set(obj, 0.0d);
        }
        if (field.getType().equals(Timestamp.class)) {
            field.set(obj, new Timestamp(0));
        }
    }


    /**
     * 转换为字符串
     *
     * @param obj
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String toString(Object obj) {
        // 先拿反射实现
        StringBuilder sb = new StringBuilder();
        if (obj == null) {
            return "null";
        }
        try {
            if (obj.getClass().isPrimitive() || obj instanceof String || obj instanceof Integer
                    || obj instanceof Long || obj instanceof Boolean) {
                return obj.toString();
            }
            if (obj instanceof Map) {
                Map map = (Map) obj;
                return toString(map);
            } else if (obj instanceof Collection) {
                Collection cls = (Collection) obj;
                return toString(cls);
            } else if (obj instanceof Object[]) {
                for (Object o : (Object[]) obj) {
                    if (o.getClass().isPrimitive() || o instanceof String || o instanceof Integer
                            || o instanceof Long || o instanceof Boolean) {
                        sb.append(o).append(",");
                    } else {
                        sb.append(toString(o)).append(",");
                    }
                }
            }
            for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(obj);
                if (object != null) {
                    sb.append("\"").append(field.getName()).append("\":");
                    if (object instanceof String) {
                        sb.append("\"").append(object).append("\",");
                    } else {
                        sb.append(object).append(",");
                    }
                }
            }
            return sb.length() == 0 ? "{}" : "{" + sb.substring(0, sb.length() - 1) + "}";
        } catch (Exception e) {
            log.error("ObjectUtil toString error,{},{}", obj, e);
            return obj.toString();
        }
    }

    /**
     * 集合
     *
     * @param objs
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String toString(Collection objs) {
        // 先拿反射实现
        StringBuilder sb = new StringBuilder();
        if (objs == null) {
            return "null";
        }
        for (Object obj : objs) {
            if (obj.getClass().isPrimitive()) {
                sb.append(obj).append(",");
            } else {
                sb.append(toString(obj)).append(",");
            }
        }
        return sb.length() == 0 ? "[]" : "[" + sb.substring(0, sb.length() - 1) + "]";
    }

    public static boolean hasNonNullProperty(Object obj) {
        try {
            for (java.lang.reflect.Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object property = field.get(obj);
                if (property != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return false;

    }

    public static String toString(Map map) {
        // 先拿反射实现
        StringBuilder sb = new StringBuilder();
        if (map == null) {
            return "null";
        }
        for (Object key : map.entrySet()) {
            Object value = map.get(key);
            sb.append("\"").append(toString(key)).append("\":").append(toString(value)).append(",");
        }
        return sb.length() == 0 ? "{}" : "{" + sb.substring(0, sb.length() - 1) + "}";
    }

    /**
     * 判断两个对象是否相同，推荐使用在基本类型数据的判断上
     */
    public static boolean isSameObject(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return true;
        } else {
            return obj1 != null && obj2 != null && obj1.equals(obj2);
        }
    }

    /**
     * Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = Maps.newHashMap();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!StringUtils.equals("class", key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            log.error("transBean2Map Error " + e);
        }
        return map;
    }

    /**
     * 判断对象属性是否为空
     **/
    public static boolean isnullObj(Object rtn) {
        if (rtn == null ||
                (rtn instanceof Collection && ((Collection<?>) rtn).size() == 0) ||
                (rtn instanceof Map && ((Map<?, ?>) rtn).keySet().size() == 0) ||
                (rtn.getClass().isArray() && ((Object[]) rtn).length == 0)
                ) {
            return true;
        } else return false;
    }
}
