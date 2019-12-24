package com.jgw.supercodeplatform.marketing.config.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * 多数据源数据库连接池配置1
 * @author liujianqiang
 * @date 2017年12月13日
 */
@Configuration
@MapperScan(basePackages ={ "com.jgw.supercodeplatform.marketingsaler.integral",
        "com.jgw.supercodeplatform.marketingsaler.base",
        "com.jgw.supercodeplatform.marketing.mybatisplusdao",
        "com.jgw.supercodeplatform.marketingsaler.order",
        // 大转盘
        "com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper",
        //大转盘埋点
        "com.jgw.supercodeplatform.burypoint.prizewheels.mapper",
        //签到埋点
        "com.jgw.supercodeplatform.burypoint.signin.mapper"},
        sqlSessionFactoryRef="marketingsalerSqlSessionFactory")
public class DataSourceConfig01 {

    @Autowired
    private Environment env;
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    @Bean("marketingsalerSqlSessionFactory")
    public SqlSessionFactory getMarketingsalerSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPlugins(new Interceptor[]{paginationInterceptor()});
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        factory.setConfiguration(mybatisConfiguration);
        return factory.getObject();
    }




//
//
//    @Bean
//    public PlatformTransactionManager fakeTransactionManager(@Qualifier("dataSource") DataSource prodDataSource) {
//        return new DataSourceTransactionManager(prodDataSource);
//    }


}
