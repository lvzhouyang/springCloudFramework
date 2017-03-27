package com.hearglobal.msp.data.interceptor;

import com.hearglobal.msp.core.context.ApplicationContextHolder;
import com.hearglobal.msp.util.ArrayUtil;
import com.hearglobal.msp.util.ObjectUtil;
import com.hearglobal.msp.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * sql执行性能监控
 * 只在debug环境生效
 *
 * @author lvzhouyang
 * @version 1.0
 * @Description SQL执行性能监控  只用于dev环境
 * @create 2017 -02-08-上午10:11
 * @since 2017.03.27
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class PerformanceInterceptor implements Interceptor {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 容器没初始化成功前不执行
        if (ObjectUtil.isNullObj(ApplicationContextHolder.context)) {
            return invocation.proceed();
        }

        // 获取生效的profile
        String[] actProfile = ApplicationContextHolder.context.getEnvironment().getActiveProfiles();
        // 对于dev环境 增加性能跟踪拦截器
        if (actProfile.length > 0 && !ArrayUtil.getUniqueSqlString(actProfile).contains("dev")) {
            return invocation.proceed();
        }

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1) {
            parameterObject = invocation.getArgs()[1];
        }
        // 获取语句
        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(boundSql, parameterObject, configuration);
        // 计时 执行
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = invocation.proceed();
        stopWatch.split();
        logger.info("PerformanceInterceptor SQL耗时:{}ms, - id:{} - Sql:{}", stopWatch.getSplitTime(), statementId, sql);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (CollectionUtils.isEmpty(parameterMappings)) {
            return StringUtil.EMPTY;
        }
        for (ParameterMapping parameterMapping : parameterMappings) {
            if (ParameterMode.OUT.equals(parameterMapping.getMode())) {
                continue;
            }
            Object value = fillInValue(boundSql, parameterMapping, parameterObject, configuration);
            sql = replacePlaceholder(sql, value);
        }
        return sql;
    }

    private Object fillInValue(BoundSql boundSql, ParameterMapping parameterMapping, Object parameterObject, Configuration configuration) {
        String propertyName = parameterMapping.getProperty();
        if (boundSql.hasAdditionalParameter(propertyName)) {
            return boundSql.getAdditionalParameter(propertyName);
        } else if (parameterObject == null) {
            return null;
        } else if (configuration.getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
            return parameterObject;
        } else {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            return metaObject.getValue(propertyName);
        }
    }


    private String replacePlaceholder(String sql, Object propertyValue) {
        StringBuilder result = new StringBuilder();
        if (ObjectUtil.isNullObj(propertyValue)) {
            return "null";
        }

        if (propertyValue instanceof String) {
            result.append("'").append(propertyValue).append("'");
        } else if (propertyValue instanceof Date) {
            result.append("'").append(DATE_FORMAT.format(propertyValue)).append("'");
        } else {
            result.append(propertyValue.toString());
        }

        return sql.replaceFirst("\\?", result.toString());
    }
}

