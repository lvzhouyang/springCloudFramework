package com.hearglobal.msp.bean;


import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

    private Integer id;
    private String userName;
    public String password;
    private Date createTime;
    private Date updateTime;
    private Integer oplock;

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
}
