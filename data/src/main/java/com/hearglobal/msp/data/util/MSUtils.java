package com.hearglobal.msp.data.util;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type Ms utils.
 * 创建mybatis的mapperState
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.03.27
 */
@Component
class MSUtils {


    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    /**
     * 创建MSID
     *
     * @param sql            执行的sql
     * @param sqlCommandType the sql command type
     * @return string string
     * @since 2017.02.08
     */
    private String newMsId(String sql, SqlCommandType sqlCommandType) {
        return sqlCommandType.toString() + "." + sql.hashCode();
    }

    /**
     * 是否已经存在该ID
     *
     * @param msId the ms id
     * @return boolean boolean
     * @since 2017.02.08
     */
    private boolean hasMappedStatement(String msId) {
        return sqlSessionFactory.getConfiguration().hasStatement(msId, false);
    }

    /**
     * 创建一个查询的MS
     *
     * @param msId       the ms id
     * @param sqlSource  执行的sqlSource
     * @param resultType 返回的结果类型
     * @since 2017.02.08
     */
    private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
        MappedStatement ms = new MappedStatement.Builder(sqlSessionFactory.getConfiguration(), msId, sqlSource, SqlCommandType.SELECT)
                .resultMaps(new ArrayList<ResultMap>() {
                    {
                        add(new ResultMap.Builder(sqlSessionFactory.getConfiguration(), "defaultResultMap", resultType, new ArrayList<ResultMapping>(0)).build());
                    }
                })
                .build();
        //缓存
        sqlSessionFactory.getConfiguration().addMappedStatement(ms);
    }

    /**
     * 创建一个简单的MS
     *
     * @param msId           the ms id
     * @param sqlSource      执行的sqlSource
     * @param sqlCommandType 执行的sqlCommandType
     * @since 2017.02.08
     */
    private void newUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType) {
        MappedStatement ms = new MappedStatement.Builder(sqlSessionFactory.getConfiguration(), msId, sqlSource, sqlCommandType)
                .resultMaps(new ArrayList<ResultMap>() {
                    {
                        add(new ResultMap.Builder(sqlSessionFactory.getConfiguration(), "defaultResultMap", int.class, new ArrayList<ResultMapping>(0)).build());
                    }
                })
                .build();
        //缓存
        sqlSessionFactory.getConfiguration().addMappedStatement(ms);
    }

    /**
     * Select string.
     *
     * @param sql the sql
     * @return the string
     * @since 2017.02.08
     */
    public String select(String sql) {
        String msId = newMsId(sql, SqlCommandType.SELECT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource sqlSource = new StaticSqlSource(sqlSessionFactory.getConfiguration(), sql);
        newSelectMappedStatement(msId, sqlSource, Map.class);
        return msId;
    }

    /**
     * Select dynamic string.
     *
     * @param sql           the sql
     * @param parameterType the parameter type
     * @return the string
     * @since 2017.02.08
     */
    public String selectDynamic(String sql, Class<?> parameterType) {
        String msId = newMsId(sql + parameterType, SqlCommandType.SELECT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        SqlSource sqlSource = sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(sqlSessionFactory.getConfiguration(), sql, parameterType);
        newSelectMappedStatement(msId, sqlSource, Map.class);
        return msId;
    }

    /**
     * Select string.
     *
     * @param sql        the sql
     * @param resultType the result type
     * @return the string
     * @since 2017.02.08
     */
    public String select(String sql, Class<?> resultType) {
        String msId = newMsId(resultType + sql, SqlCommandType.SELECT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource sqlSource = new StaticSqlSource(sqlSessionFactory.getConfiguration(), sql);
        newSelectMappedStatement(msId, sqlSource, resultType);
        return msId;
    }

    /**
     * Select dynamic string.
     *
     * @param sql           the sql
     * @param parameterType the parameter type
     * @param resultType    the result type
     * @return the string
     * @since 2017.02.08
     */
    public String selectDynamic(String sql, Class<?> parameterType, Class<?> resultType) {
        String msId = newMsId(resultType + sql + parameterType, SqlCommandType.SELECT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        SqlSource sqlSource = sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(sqlSessionFactory.getConfiguration(), sql, parameterType);
        newSelectMappedStatement(msId, sqlSource, resultType);
        return msId;
    }

    /**
     * Insert string.
     *
     * @param sql the sql
     * @return the string
     * @since 2017.02.08
     */
    public String insert(String sql) {
        String msId = newMsId(sql, SqlCommandType.INSERT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource sqlSource = new StaticSqlSource(sqlSessionFactory.getConfiguration(), sql);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
        return msId;
    }

    /**
     * Insert dynamic string.
     *
     * @param sql           the sql
     * @param parameterType the parameter type
     * @return the string
     * @since 2017.02.08
     */
    public String insertDynamic(String sql, Class<?> parameterType) {
        String msId = newMsId(sql + parameterType, SqlCommandType.INSERT);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        SqlSource sqlSource = sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(sqlSessionFactory.getConfiguration(), sql, parameterType);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
        return msId;
    }

    /**
     * Update string.
     *
     * @param sql the sql
     * @return the string
     * @since 2017.02.08
     */
    public String update(String sql) {
        String msId = newMsId(sql, SqlCommandType.UPDATE);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource sqlSource = new StaticSqlSource(sqlSessionFactory.getConfiguration(), sql);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
        return msId;
    }

    /**
     * Update dynamic string.
     *
     * @param sql           the sql
     * @param parameterType the parameter type
     * @return the string
     * @since 2017.02.08
     */
    public String updateDynamic(String sql, Class<?> parameterType) {
        String msId = newMsId(sql + parameterType, SqlCommandType.UPDATE);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        SqlSource sqlSource = sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(sqlSessionFactory.getConfiguration(), sql, parameterType);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
        return msId;
    }

    /**
     * Delete string.
     *
     * @param sql the sql
     * @return the string
     * @since 2017.02.08
     */
    public String delete(String sql) {
        String msId = newMsId(sql, SqlCommandType.DELETE);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        StaticSqlSource sqlSource = new StaticSqlSource(sqlSessionFactory.getConfiguration(), sql);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
        return msId;
    }

    /**
     * Delete dynamic string.
     *
     * @param sql           the sql
     * @param parameterType the parameter type
     * @return the string
     * @since 2017.02.08
     */
    public String deleteDynamic(String sql, Class<?> parameterType) {
        String msId = newMsId(sql + parameterType, SqlCommandType.DELETE);
        if (hasMappedStatement(msId)) {
            return msId;
        }
        SqlSource sqlSource = sqlSessionFactory.getConfiguration().getDefaultScriptingLanuageInstance().createSqlSource(sqlSessionFactory.getConfiguration(), sql, parameterType);
        newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
        return msId;
    }
}
