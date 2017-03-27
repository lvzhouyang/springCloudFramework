package com.hearglobal.msp.data.base;

import java.io.Serializable;

/**
 * 定义实体基础类
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
public abstract class BaseEntity implements Serializable {
    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The Id.
     */
    private Integer id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
