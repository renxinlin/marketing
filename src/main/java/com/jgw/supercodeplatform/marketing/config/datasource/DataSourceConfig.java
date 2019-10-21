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
@MapperScan(basePackages = "com.jgw.supercodeplatform.marketing.dao",sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfig {
	
	@Autowired
	private Environment env;
	@Autowired
	private PaginationInterceptor paginationInterceptor;
	
	/**
	 * 数据源dataSource配置
	 * @author liujianqiang
	 * @data 2017年12月13日
	 * @return
	 */
	@Bean(name = "dataSource")
	public DataSource dataSource(){
		 DruidDataSource dataSource = new DruidDataSource();  
		 dataSource.setUrl(env.getProperty("spring.datasource.url"));
		 dataSource.setUsername(env.getProperty("spring.datasource.username"));
		 dataSource.setPassword(env.getProperty("spring.datasource.password"));
		 dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		 dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.dbcp.initial-size")));
		 dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.dbcp.min-idle")));
		 dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.dbcp.max-active")));
		 dataSource.setMaxWait(Long.valueOf(env.getProperty("spring.datasource.dbcp.max-wait")));
		 dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("spring.datasource.dbcp.time-between-eviction-runs-millis")));
		 dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("spring.datasource.dbcp.min-evictable-idle-time-millis")));
		 dataSource.setValidationQuery(env.getProperty("spring.datasource.dbcp.validation-query"));
		 dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("spring.datasource.dbcp.test-while-idle")));
		 dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("spring.datasource.dbcp.test-on-borrow")));
		 dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("spring.datasource.dbcp.test-on-return")));
		 return dataSource;
	}
	
	/**
	 * 数据源sqlSessionFactory配置
	 * @author liujianqiang
	 * @data 2017年12月13日
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource);
//        return factoryBean.getObject();
		MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPlugins(new Interceptor[]{paginationInterceptor});
		MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
		factory.setConfiguration(mybatisConfiguration);
		return factory.getObject();
    }
	
	/**
	 * 数据源sqlSessionTemplate配置
	 * @author liujianqiang
	 * @data 2017年12月13日
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory(dataSource));
        return template;
    }

    @Bean
    public PlatformTransactionManager fakeTransactionManager(@Qualifier("dataSource") DataSource prodDataSource) {
        return new DataSourceTransactionManager(prodDataSource);
    }
     

}
