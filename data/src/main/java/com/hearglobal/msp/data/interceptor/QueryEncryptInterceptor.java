package com.hearglobal.msp.data.interceptor;/**
 * Created by lvzhouyang on 17/2/8.
 */

import com.hearglobal.msp.data.util.ParamEncryptHelper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * The type Query encrypt interceptor.
 *
 * @author lvzhouyang
 * @version 1.0
 * @Description 当加密字段作为查询条件时
 * @create 2017 -02-08-上午10:43
 * @since 2017.02.08
 */
@Intercepts(
        {@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
                , @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class QueryEncryptInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(QueryEncryptInterceptor.class);

    /**
     * Intercept object.
     * 拦截逻辑
     *
     * @param invocation the invocation
     * @return the object
     * @throws Throwable the throwable
     * @since 2017.02.08
     */
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
