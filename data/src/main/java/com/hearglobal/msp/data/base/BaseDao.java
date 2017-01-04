package com.hearglobal.msp.data.base;

import java.util.List;

/**
 * Created by lvzhouyang on 16/12/15.
 */
public interface BaseDao<T> {
    public T selectByPrimaryKey(Integer id);

    public int deleteByPrimaryKey(Integer id);

    public int insertSelective(T t);

    public int updateByPrimaryKeySelective(T t);

    public List<T> getList(T t);


}
