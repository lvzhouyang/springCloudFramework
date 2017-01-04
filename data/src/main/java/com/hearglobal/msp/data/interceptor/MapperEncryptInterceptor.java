package com.hearglobal.msp.data.interceptor;

import com.hearglobal.msp.data.annotation.Encrypt;
import com.hearglobal.msp.util.EncryptUtil;
import com.hearglobal.msp.util.ObjectUtil;
import com.hearglobal.msp.util.ReflectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * mybatis 拦截器 进行更新操作的加密
 * User: lvzhouyang
 * Date: 15/9/15
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class})})
public class MapperEncryptInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(MapperEncryptInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object object = invocation.getArgs()[1];
        if (object != null) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) {
                //获取字段中包含Encrypt的注解
                Encrypt meta = f.getAnnotation(Encrypt.class);
                if (meta != null) {
                    this.encrypt(object, f);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void encrypt(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            field.set(object, EncryptUtil.encrypt(value + ""));
        } catch (Exception e) {
            logger.debug("字段加密失败!");
        }
    }
}
