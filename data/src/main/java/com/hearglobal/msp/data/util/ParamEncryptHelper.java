package com.hearglobal.msp.data.util;/**
 * Created by lvzhouyang on 17/2/8.
 */

import com.hearglobal.msp.data.annotation.Encrypt;
import com.hearglobal.msp.util.EncryptUtil;
import com.hearglobal.msp.util.ObjectUtil;
import com.hearglobal.msp.util.ReflectUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * The type Param encrypt helper.
 * Description
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.02.08
 */
public class ParamEncryptHelper {

    /**
     * The constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(ParamEncryptHelper.class);

    /**
     * Encrypt.
     * 遍历参数,对参数进行加密
     *
     * @param object the object
     * @since 2017.02.08
     */
    public static void encrypt(Object object) {

        if (ObjectUtil.isNullObj(object)) {
            return;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return;
        }
        Stream.of(fields)
                .filter(field -> field.getAnnotation(Encrypt.class) != null)
                .forEach(field -> encryptField(object, field));
    }

    /**
     * Encrypt field.
     * 属性加密
     *
     * @param object the object
     * @param field  the field
     * @since 2017.02.08
     */
    private static void encryptField(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            field.set(object, EncryptUtil.encrypt(value + ""));
        } catch (Exception e) {
            logger.debug("字段加密失败!");
        }
    }
}
