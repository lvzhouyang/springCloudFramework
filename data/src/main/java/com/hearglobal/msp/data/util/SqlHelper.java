package com.hearglobal.msp.data.util;

import com.google.common.collect.Maps;
import com.hearglobal.msp.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * The type Sql helper.
 * 获取Mybatis查询sql工具
 * 如果你想在代码中获取Mybatis方法的sql，你可以使用本工具
 * 这个工具不需要你实际去执行Mybatis的查询方法就能得到sql
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.02.08
 */
@Component
public class SqlHelper {

    /**
     * The Sql session factory.
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 通过接口获取sql
     *
     * @param mapper     the mapper
     * @param methodName the method name
     * @param args       the args
     * @return mapper sql
     */
    public String getMapperSql(Object mapper, String methodName, Object... args) {
        MetaObject metaObject = SystemMetaObject.forObject(mapper);
        Class mapperInterface = (Class) metaObject.getValue("h.mapperInterface");
        String fullMethodName = mapperInterface.getCanonicalName() + "." + methodName;
        if (ArrayUtils.isEmpty(args)) {
            return getNamespaceSql(fullMethodName, null);
        } else {
            return getMapperSql(mapperInterface, methodName, args);
        }
    }

    /**
     * 通过Mapper方法名获取sql
     *
     * @param fullMapperMethodName the full mapper method name
     * @param args                 the args
     * @return mapper sql
     */
    public String getMapperSql(String fullMapperMethodName, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return getNamespaceSql(fullMapperMethodName, null);
        }
        String methodName = fullMapperMethodName.substring(fullMapperMethodName.lastIndexOf('.') + 1);
        Class mapperInterface = null;
        try {
            mapperInterface = Class.forName(fullMapperMethodName.substring(0, fullMapperMethodName.lastIndexOf('.')));
            return getMapperSql(mapperInterface, methodName, args);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("参数" + fullMapperMethodName + "无效！");
        }
    }

    /**
     * 通过Mapper接口和方法名
     *
     * @param mapperInterface the mapper interface
     * @param methodName      the method name
     * @param args            the args
     * @return mapper sql
     */
    public String getMapperSql(Class mapperInterface, String methodName, Object... args) {
        String fullMapperMethodName = mapperInterface.getCanonicalName() + "." + methodName;
        if (ArrayUtils.isEmpty(args)) {
            return getNamespaceSql(fullMapperMethodName, null);
        }
        Method method = getDeclaredMethods(mapperInterface, methodName);
        Map params = Maps.newHashMap();
        final Class<?>[] argTypes = method.getParameterTypes();

        for (int i = 0; i < argTypes.length; i++) {
            if (!RowBounds.class.isAssignableFrom(argTypes[i]) && !ResultHandler.class.isAssignableFrom(argTypes[i])) {
                String paramName = "param" + String.valueOf(params.size() + 1);
                paramName = getParamNameFromAnnotation(method, i, paramName);
                params.put(paramName, i >= args.length ? null : args[i]);
            }
        }
        if (args.length == 1) {
            Object _params = wrapCollection(args[0]);
            if (_params instanceof Map) {
                params.putAll((Map) _params);
            }
        }
        return getNamespaceSql(fullMapperMethodName, params);
    }


    /**
     * 通过命名空间方式获取sql
     *
     * @param namespace the namespace
     * @return namespace sql
     */
    public String getNamespaceSql(String namespace) {
        return getNamespaceSql(namespace, null);
    }

    /**
     * 通过命名空间方式获取sql
     *
     * @param namespace the namespace
     * @param params    the params
     * @return namespace sql
     */
    public String getNamespaceSql(String namespace, Object params) {
        params = wrapCollection(params);
        Configuration configuration = sqlSessionFactory.getConfiguration();
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
        BoundSql boundSql = mappedStatement.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        if (CollectionUtils.isEmpty(parameterMappings)) {
            return StringUtil.EMPTY;
        }

        String sql = boundSql.getSql();
        parameterMappings = parameterMappings.stream()
                .filter(parameterMapping -> ParameterMode.OUT != parameterMapping.getMode())
                .collect(Collectors.toList());

        for (ParameterMapping parameterMapping : parameterMappings) {
            Object value = fillValue(namespace, parameterMapping, params, configuration);
            JdbcType jdbcType = parameterMapping.getJdbcType();
            if (value == null && jdbcType == null) {
                jdbcType = configuration.getJdbcTypeForNull();
            }
            sql = replaceParameter(sql, value, jdbcType, parameterMapping.getJavaType());
        }
        return sql;
    }

    private Object fillValue(String namespace, ParameterMapping parameterMapping, Object params, Configuration configuration) {
        MappedStatement mappedStatement = configuration.getMappedStatement(namespace);
        BoundSql boundSql = mappedStatement.getBoundSql(params);
        if (boundSql.hasAdditionalParameter(parameterMapping.getProperty())) {
            return boundSql.getAdditionalParameter(parameterMapping.getProperty());
        } else if (params == null) {
            return null;
        } else if (mappedStatement.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(params.getClass())) {
            return params;
        } else {
            MetaObject metaObject = configuration.newMetaObject(params);
            return metaObject.getValue(parameterMapping.getProperty());
        }
    }

    /**
     * 根据类型替换参数
     * 仅作为数字和字符串两种类型进行处理，需要特殊处理的可以继续完善这里
     *
     * @param sql      the sql
     * @param value    the value
     * @param jdbcType the jdbc type
     * @param javaType the java type
     * @return string string
     * @since 2017.02.08
     */
    private String replaceParameter(String sql, Object value, JdbcType jdbcType, Class javaType) {
        String strValue = String.valueOf(value);
        if (jdbcType != null) {
            switch (jdbcType) {
                //数字
                case BIT:
                case TINYINT:
                case SMALLINT:
                case INTEGER:
                case BIGINT:
                case FLOAT:
                case REAL:
                case DOUBLE:
                case NUMERIC:
                case DECIMAL:
                    break;
                //日期
                case DATE:
                case TIME:
                case TIMESTAMP:
                    //其他，包含字符串和其他特殊类型
                default:
                    strValue = "'" + strValue + "'";


            }
        } else if (Number.class.isAssignableFrom(javaType)) {
            //不加单引号
        } else {
            strValue = "'" + strValue + "'";
        }
        return sql.replaceFirst("\\?", strValue);
    }

    /**
     * 获取指定的方法
     *
     * @param clazz      the clazz
     * @param methodName the method name
     * @return declared methods
     */
    private Method getDeclaredMethods(Class clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new IllegalArgumentException("方法" + methodName + "不存在！");
    }

    /**
     * 获取参数注解名
     *
     * @param method    the method
     * @param i         the
     * @param paramName the param name
     * @return param name from annotation
     */
    private String getParamNameFromAnnotation(Method method, int i, String paramName) {
        final Object[] paramAnnos = method.getParameterAnnotations()[i];
        for (Object paramAnno : paramAnnos) {
            if (paramAnno instanceof Param) {
                paramName = ((Param) paramAnno).value();
            }
        }
        return paramName;
    }

    /**
     * 简单包装参数
     *
     * @param object the object
     * @return object object
     * @since 2017.02.08
     */
    private Object wrapCollection(final Object object) {
        if (object instanceof List) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", object);
            return map;
        } else if (object != null && object.getClass().isArray()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("array", object);
            return map;
        }
        return object;
    }
}