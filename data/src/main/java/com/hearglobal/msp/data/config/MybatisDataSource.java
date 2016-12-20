package com.hearglobal.msp.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hearglobal.msp.core.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by lvzhouyang on 16/12/12.
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableTransactionManagement
//mybaits dao 搜索路径
@MapperScan("com.hearglobal.**.mapper")
public class MybatisDataSource {

    private final Logger logger = LoggerFactory.getLogger(MybatisDataSource.class);
    @Autowired
    private DataSourceProperties dataSourceProperties;
    //mybaits mapper xml搜索路径
    private static String mapperLocations = "classpath*:com/hearglobal/mapper/*.xml";

    private DruidDataSource pool;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSourceProperties config = dataSourceProperties;
        this.pool = new DruidDataSource();
        this.pool.setDriverClassName(config.getDriverClassName());
        // check
        if (StringUtils.isEmpty(config.getUrl())){
            logger.error("请配置数据库连接!");
            throw new BaseException("数据库连接初始化失败!请配置数据库连接!");
        }
        if (StringUtils.isEmpty(config.getUsername())){
            logger.error("请配置数据库用户!");
            throw new BaseException("数据库连接初始化失败!请配置数据库用户!");
        }
        this.pool.setUrl(config.getUrl());
        if (config.getUsername() != null) {
            this.pool.setUsername(config.getUsername());
        }
        if (config.getPassword() != null) {
            this.pool.setPassword(config.getPassword());
        }
        this.pool.setInitialSize(config.getInitialSize());
        this.pool.setMaxActive(config.getMaxActive());
        this.pool.setMinIdle(config.getMinIdle());
        this.pool.setValidationQuery(config.getValidationQuery());
        try {
            this.pool.setFilters("!stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("数据库连接初始化失败!,{}",e);
            throw new BaseException("数据库连接初始化失败!");
        }
        return this.pool;
    }

    @PreDestroy
    public void close() {
        if (this.pool != null) {
            this.pool.close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        if (StringUtils.isEmpty(dataSourceProperties.getMapperLocations())){
            logger.error("请配置Mapper文件扫描目录!");
            throw new BaseException("请配置Mapper文件扫描目录!");
        }
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(dataSourceProperties.getMapperLocations()));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
