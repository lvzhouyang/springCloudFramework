package com.hearglobal.msp.core.remote;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author lvzhouyang
 * @Description 用以做api请求的类型转换传递
 * @create 2017-02-10-上午9:29
 */
public class TypeReference<T> extends com.alibaba.fastjson.TypeReference {
    private final Type type;

    public TypeReference() {
        Class clazz = getClass();
        Type superClass = clazz.getGenericSuperclass();

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }


    public Type getType() {
        return type;
    }


}
