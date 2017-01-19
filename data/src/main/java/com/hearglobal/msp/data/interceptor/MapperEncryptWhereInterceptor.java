package com.hearglobal.msp.data.interceptor;

import com.hearglobal.msp.util.EncryptUtil;
import com.hearglobal.msp.util.ReflectUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * mybatis 拦截器 进行更新操作的解密
 * User: ccg
 * Date: 17/1/7
 */
@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class }),
        @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class MapperEncryptWhereInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(MapperEncryptWhereInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
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
