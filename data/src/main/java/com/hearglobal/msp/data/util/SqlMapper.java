package com.hearglobal.msp.data.util;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * The type Sql mapper.
 * 实现mybatis直接执行sql语句
 * MyBatis执行sql工具，在写SQL的时候建议使用参数形式的可以是${}或#{}
 * 不建议将参数直接拼到字符串中，当大量这么使用的时候由于缓存MappedStatement而占用更多的内存
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.02.08
 */
@Component
public class SqlMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private MSUtils msUtils;

    /**
     * 获取List中最多只有一个的数据
     *
     * @param <T>  泛型类型
     * @param list List结果
     * @return one one
     */
    private <T> T getOne(List<T> list) {
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql 执行的sql
     * @return map map
     * @since 2017.02.08
     */
    public Map<String, Object> selectOne(String sql) {
        List<Map<String, Object>> list = selectList(sql);
        return getOne(list);
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return map map
     * @since 2017.02.08
     */
    public Map<String, Object> selectOne(String sql, Object value) {
        List<Map<String, Object>> list = selectList(sql, value);
        return getOne(list);
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param <T>        泛型类型
     * @param sql        执行的sql
     * @param resultType 返回的结果类型
     * @return t t
     * @since 2017.02.08
     */
    public <T> T selectOne(String sql, Class<T> resultType) {
        List<T> list = selectList(sql, resultType);
        return getOne(list);
    }

    /**
     * 查询返回一个结果，多个结果时抛出异常
     *
     * @param <T>        泛型类型
     * @param sql        执行的sql
     * @param value      参数
     * @param resultType 返回的结果类型
     * @return t t
     * @since 2017.02.08
     */
    public <T> T selectOne(String sql, Object value, Class<T> resultType) {
        List<T> list = selectList(sql, value, resultType);
        return getOne(list);
    }

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql 执行的sql
     * @return list list
     * @since 2017.02.08
     */
    public List<Map<String, Object>> selectList(String sql) {
        String msId = msUtils.select(sql);
        return sqlSessionFactory.openSession().selectList(msId);
    }

    /**
     * 查询返回List<Map<String, Object>>
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return list list
     * @since 2017.02.08
     */
    public List<Map<String, Object>> selectList(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = msUtils.selectDynamic(sql, parameterType);
        return sqlSessionFactory.openSession().selectList(msId, value);
    }

    /**
     * 查询返回指定的结果类型
     *
     * @param <T>        泛型类型
     * @param sql        执行的sql
     * @param resultType 返回的结果类型
     * @return list list
     * @since 2017.02.08
     */
    public <T> List<T> selectList(String sql, Class<T> resultType) {
        String msId;
        if (resultType == null) {
            msId = msUtils.select(sql);
        } else {
            msId = msUtils.select(sql, resultType);
        }
        return sqlSessionFactory.openSession().selectList(msId);
    }

    /**
     * 查询返回指定的结果类型
     *
     * @param <T>        泛型类型
     * @param sql        执行的sql
     * @param value      参数
     * @param resultType 返回的结果类型
     * @return list list
     * @since 2017.02.08
     */
    public <T> List<T> selectList(String sql, Object value, Class<T> resultType) {
        String msId;
        Class<?> parameterType = value != null ? value.getClass() : null;
        if (resultType == null) {
            msId = msUtils.selectDynamic(sql, parameterType);
        } else {
            msId = msUtils.selectDynamic(sql, parameterType, resultType);
        }
        return sqlSessionFactory.openSession().selectList(msId, value);
    }

    /**
     * 插入数据
     *
     * @param sql 执行的sql
     * @return int int
     * @since 2017.02.08
     */
    public int insert(String sql) {
        String msId = msUtils.insert(sql);
        return sqlSessionFactory.openSession().insert(msId);
    }

    /**
     * 插入数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return int int
     * @since 2017.02.08
     */
    public int insert(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = msUtils.insertDynamic(sql, parameterType);
        return sqlSessionFactory.openSession().insert(msId, value);
    }

    /**
     * 更新数据
     *
     * @param sql 执行的sql
     * @return int int
     * @since 2017.02.08
     */
    public int update(String sql) {
        String msId = msUtils.update(sql);
        return sqlSessionFactory.openSession().update(msId);
    }

    /**
     * 更新数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return int int
     * @since 2017.02.08
     */
    public int update(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = msUtils.updateDynamic(sql, parameterType);
        return sqlSessionFactory.openSession().update(msId, value);
    }

    /**
     * 删除数据
     *
     * @param sql 执行的sql
     * @return int int
     * @since 2017.02.08
     */
    public int delete(String sql) {
        String msId = msUtils.delete(sql);
        return sqlSessionFactory.openSession().delete(msId);
    }

    /**
     * 删除数据
     *
     * @param sql   执行的sql
     * @param value 参数
     * @return int int
     * @since 2017.02.08
     */
    public int delete(String sql, Object value) {
        Class<?> parameterType = value != null ? value.getClass() : null;
        String msId = msUtils.deleteDynamic(sql, parameterType);
        return sqlSessionFactory.openSession().delete(msId, value);
    }
}

