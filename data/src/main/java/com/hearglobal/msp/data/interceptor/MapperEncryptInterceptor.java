package com.hearglobal.msp.data.interceptor;

import com.hearglobal.msp.data.util.ParamEncryptHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * mybatis 拦截器 进行更新操作的加密
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class})})
public class MapperEncryptInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(MapperEncryptInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getArgs().length > 1) {
            Object object = invocation.getArgs()[1];
            ParamEncryptHelper.encrypt(object);
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
}
