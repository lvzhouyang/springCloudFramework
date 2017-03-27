package com.hearglobal.msp.data.base;

import java.util.List;

/**
 * 定义Mybatis的通用操作
 *
 * @param <T> the type parameter
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public interface BaseDao<T> {
    /**
     * Select by primary key t.
     *
     * @param id the id
     * @return the t
     * @since 2017.03.27
     */
    public T selectByPrimaryKey(Integer id);

    /**
     * Delete by primary key int.
     *
     * @param id the id
     * @return the int
     * @since 2017.03.27
     */
    public int deleteByPrimaryKey(Integer id);

    /**
     * Insert selective int.
     *
     * @param t the t
     * @return the int
     * @since 2017.03.27
     */
    public int insertSelective(T t);

    /**
     * Update by primary key selective int.
     *
     * @param t the t
     * @return the int
     * @since 2017.03.27
     */
    public int updateByPrimaryKeySelective(T t);

    /**
     * Gets list.
     *
     * @param t the t
     * @return the list
     */
    public List<T> getList(T t);


}
