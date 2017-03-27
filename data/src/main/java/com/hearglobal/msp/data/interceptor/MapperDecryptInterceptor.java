package com.hearglobal.msp.data.interceptor;

import com.hearglobal.msp.data.annotation.Encrypt;
import com.hearglobal.msp.util.EncryptUtil;
import com.hearglobal.msp.util.ReflectUtil;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * mybatis 拦截器 进行更新操作的解密
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}))
public class MapperDecryptInterceptor implements Interceptor {

    /**
     * The Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(MapperDecryptInterceptor.class);

    /**
     * Intercept object.
     *
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     * @since 2017.03.27
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行，后处理
        List<Object> objects = (List<Object>) invocation.proceed();
        //获取字段中包含Encrypt的注解
        objects.stream()
                .filter(object -> object != null)
                .forEach(object -> {
                    Field[] fields = object.getClass().getDeclaredFields();
                    Stream.of(fields)
                            .filter(field -> field.getAnnotation(Encrypt.class) != null)
                            .forEach(field -> decrypt(object, field));
                });
        return objects;
    }

    /**
     * Plugin object.
     *
     * @param target the target
     * @return the object
     * @since 2017.03.27
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * Sets properties.
     *
     * @param properties the properties
     */
    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * Decrypt.
     *
     * @param object the object
     * @param field  the field
     * @since 2017.03.27
     */
    private void decrypt(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            String decrypt = EncryptUtil.decrypt(value + "");
            field.set(object, decrypt);
        } catch (Exception e) {
            logger.debug("字段解密失败!");
        }
    }
}
