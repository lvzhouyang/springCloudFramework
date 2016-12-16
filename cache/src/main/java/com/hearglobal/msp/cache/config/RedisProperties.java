package com.hearglobal.msp.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Created by lvzhouyang on 16/12/14.
 */
@RefreshScope
@ConfigurationProperties(prefix = RedisProperties.DS, ignoreUnknownFields = false)
public class RedisProperties {
    //对应配置文件里的配置键
    public final static String DS = "spring.redis";

    private String host;
    private String port;
    private String timeout;
    private String password;
    private int database = 0;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
