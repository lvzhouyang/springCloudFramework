package com.hearglobal.msp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * domain vo对象属性转换
 * Created by lvzhouyang on 16/12/15.
 */
public class Assembler {
    private static final Logger logger = LoggerFactory.getLogger(Assembler.class);

    /**
     * 从源对象 copy属性到目标对象
     * @param fromObject
     * @param toObject
     */
    public static void assemble(Object fromObject, Object toObject) {
        BeanUtils.copyProperties(fromObject, toObject);
    }

    /**
     * 从源对象 copy属性到目标对象
     * ignoreProperties 忽略的属性
     * @param fromObject
     * @param toObject
     * @param ignoreProperties
     */
    public static void assemble(Object fromObject, Object toObject, String... ignoreProperties) {
        BeanUtils.copyProperties(fromObject, toObject, ignoreProperties);
    }

    /**
     * 从源List装配一个符合目标class类型的List.可以触发toObject的名称为
     * assembleTrigger的方法
     *
     * @param fromList      源list
     * @param toClass       目标class
     * @param excludeFields 不装配的属性名称
     * @return
     */
    public static <T> List<T> assembleList2NewList(List<?> fromList,
                                                   Class<T> toClass,
                                                   String... excludeFields) {
        List<T> toList = new ArrayList<T>(fromList.size());
        try {
            for (Iterator<?> iterator = fromList.iterator(); iterator.hasNext(); ) {
                Object fromObject = iterator.next();
                T toObject = toClass.newInstance();
                if (excludeFields.length > 0)
                    assemble(fromObject, toObject, excludeFields);
                else
                    assemble(fromObject, toObject);
                toList.add(toObject);
            }
        } catch (Exception e) {
            logger.error("assember error",e);
        }
        return toList;
    }

    /**
     * 从源List装配一个符合目标class类型的List,可以触发toObject的名称为
     * assembleTrigger的方法
     *
     * @param fromList      源List
     * @param toList        目标List
     * @param excludeFields 不需要装配的属性
     * @return
     * @author zhaolei
     * @created 2011-4-28
     */
    public static <T> List<T> assembleList2List(List<?> fromList,
                                                List<T> toList,
                                                Class<T> toClass,
                                                String... excludeFields) {
        try {
            for (int i = 0; i < fromList.size(); i++) {
                Object fromObject = fromList.get(i);
                T toObject = i >= toList.size() ? toClass.newInstance() : toList.get(i);
                if (excludeFields.length > 0)
                    assemble(fromObject, toObject, excludeFields);
                else
                    assemble(fromObject, toObject);
                toList.add(toObject);
            }
        } catch (Exception e) {
            logger.error("assember error",e);
        }
        return toList;
    }

}
