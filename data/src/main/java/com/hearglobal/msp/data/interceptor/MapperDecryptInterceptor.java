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

/**
 * mybatis 拦截器 进行更新操作的加密
 * User: lvzhouyang
 * Date: 15/9/15
 */
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}))
public class MapperDecryptInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(MapperDecryptInterceptor.class);

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行，后处理
        List<Object> objects = (List<Object>) invocation.proceed();
        //获取字段中包含Encrypt的注解
        objects.stream().filter(object -> object != null).forEach(object -> {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) {
                //获取字段中包含Encrypt的注解
                Encrypt meta = f.getAnnotation(Encrypt.class);
                if (meta != null) {
                    this.decrypt(object, f);
                }
            }
        });
        return objects;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void decrypt(Object object, Field field) {
        try {
            Object value = ReflectUtil.getValueByProperty(object, field.getName());
            field.setAccessible(true);
            String decrypt = EncryptUtil.decrypt(value + "");
            field.set(object, decrypt);
        } catch (Exception e) {
            logger.error("字段解密失败!", e);
        }
    }
}
