package com.hearglobal.msp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by lvzhouyang on 16/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo {
    private Integer id;
    private String userName;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Integer oplock;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOplock() {
        return oplock;
    }

    public void setOplock(Integer oplock) {
        this.oplock = oplock;
    }
}
