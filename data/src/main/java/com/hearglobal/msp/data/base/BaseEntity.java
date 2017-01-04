package com.hearglobal.msp.data.base;

import java.io.Serializable;

/**
 * Created by lvzhouyang on 16/12/15.
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
