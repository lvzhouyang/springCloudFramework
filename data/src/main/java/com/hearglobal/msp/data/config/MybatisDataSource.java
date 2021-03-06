package com.hearglobal.msp.data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.hearglobal.msp.core.exception.BaseException;
import com.hearglobal.msp.data.interceptor.MapperDecryptInterceptor;
import com.hearglobal.msp.data.interceptor.MapperEncryptInterceptor;
import com.hearglobal.msp.data.interceptor.PerformanceInterceptor;
import com.hearglobal.msp.data.interceptor.QueryEncryptInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.plugin.Interceptor;
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
import java.sql.SQLException;
import java.util.List;

/**
 * Mybatis配置
 * no comments for you
 * it was hard to write
 * so it should be hard to read
 *
 * @author lvzhouyang.
 * @version 1.0
 * @since 2017.03.27
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableTransactionManagement
//mybaits dao 搜索路径
@MapperScan("com.**.**.mapper")
public class MybatisDataSource {

    private final Logger logger = LoggerFactory.getLogger(MybatisDataSource.class);
    @Autowired
    private DataSourceProperties dataSourceProperties;

    private DruidDataSource pool;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSourceProperties config = dataSourceProperties;
        this.pool = new DruidDataSource();
        this.pool.setDriverClassName(config.getDriverClassName());
        //基本属性 url、user、password
        if (StringUtils.isEmpty(config.getUrl())) {
            logger.error("请配置数据库连接!");
            throw new BaseException("数据库连接初始化失败!请配置数据库连接!");
        }
        if (StringUtils.isEmpty(config.getUsername())) {
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
        // 配置初始化大小、最小、最大
        this.pool.setInitialSize(config.getInitialSize());
        this.pool.setMinIdle(config.getMinIdle());
        this.pool.setMaxActive(config.getMaxActive());
        // 配置获取连接等待超时的时间
        this.pool.setMaxWait(config.getMaxWait());
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        this.pool.setTimeBetweenEvictionRunsMillis(config.getTimeBetweenEvictionRunsMillis());
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        this.pool.setMinEvictableIdleTimeMillis(config.getMinEvictableIdleTimeMillis());
        this.pool.setTestWhileIdle(true);
        this.pool.setTestOnBorrow(false);
        this.pool.setValidationQuery(config.getValidationQuery());
        // 支持utf8mb4编码
        if (config.getSupportUtf8mb4().equals(NumberUtils.INTEGER_ONE)) {
            List<String> initSqls = Lists.newArrayList();
            initSqls.add("SET NAMES utf8mb4");
            this.pool.setConnectionInitSqls(initSqls);
            logger.info("数据库连接支持Utf8mb4编码");
        } else {
            logger.info("数据库连接不支持Utf8mb4编码,如需要支持请配置supportUtf8mb4属性为1");
        }

        try {
            this.pool.setFilters("!stat,wall,log4j");
        } catch (SQLException e) {
            logger.error("数据库连接初始化失败!,{}", e);
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
        if (StringUtils.isEmpty(dataSourceProperties.getMapperLocations())) {
            logger.error("请配置Mapper文件扫描目录!");
            throw new BaseException("请配置Mapper文件扫描目录!");
        }
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(dataSourceProperties.getMapperLocations()));
        // 在这里添加默认拦截器
        List<Interceptor> interceptors = Lists.newArrayList();
        interceptors.add(new MapperEncryptInterceptor());
        interceptors.add(new MapperDecryptInterceptor());
        interceptors.add(new QueryEncryptInterceptor());
        interceptors.add(new PerformanceInterceptor());
        interceptors.add(new PageHelper());

        Interceptor[] interceptorArray = new Interceptor[interceptors.size()];
        sqlSessionFactoryBean.setPlugins(interceptors.toArray(interceptorArray));

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setDefaultTimeout(5);
        return transactionManager;
    }
}
