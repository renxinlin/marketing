package com.jgw.supercodeplatform.marketing.config.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 多数据源数据库连接池配置1
 * @author liujianqiang
 * @date 2017年12月13日
 */
@Configuration
@MapperScan(basePackages = "com.jgw.supercodeplatform.marketingsaler.dynamic.mapper",sqlSessionFactoryRef="marketingsalerdynamicSqlSessionFactory")
public class DataSourceConfig02 {

    @Autowired
    private Environment env;
    @Autowired
    private PaginationInterceptor paginationInterceptor;

    @Bean("marketingsalerdynamicSqlSessionFactory")
    public SqlSessionFactory getMarketingsalerSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPlugins(new Interceptor[]{paginationInterceptor});
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        factory.setConfiguration(mybatisConfiguration);
        return factory.getObject();
    }



    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("dynamic.spring.datasource.url"));
        dataSource.setUsername(env.getProperty("dynamic.spring.datasource.username"));
        dataSource.setPassword(env.getProperty("dynamic.spring.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("dynamic.spring.datasource.driver-class-name"));
        dataSource.setInitialSize(Integer.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.initial-size")));
        dataSource.setMinIdle(Integer.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.min-idle")));
        dataSource.setMaxActive(Integer.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.max-active")));
        dataSource.setMaxWait(Long.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.max-wait")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.time-between-eviction-runs-millis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.min-evictable-idle-time-millis")));
        dataSource.setValidationQuery(env.getProperty("dynamic.spring.datasource.dbcp.validation-query"));
        dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.test-while-idle")));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.test-on-borrow")));
        dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("dynamic.spring.datasource.dbcp.test-on-return")));
        return dataSource;
    }


//
//
//    @Bean
//    public PlatformTransactionManager fakeTransactionManager(@Qualifier("dataSource") DataSource prodDataSource) {
//        return new DataSourceTransactionManager(prodDataSource);
//    }


}
