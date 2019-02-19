//package com.jgw.supercodeplatform.marketing.config.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
///**
// * 多数据源数据库连接池配置1
// * @author corbett
// * @date 2017年12月13日
// */
//@Configuration
//@EnableTransactionManagement(order = 1)
//@MapperScan(basePackages = "com.jgw.supercodeplatform.fake.dao.dao1",sqlSessionFactoryRef="sqlSessionFactory1")
//public class DataSourceConfig01 {
//	
//	@Autowired
//	private Environment env;
//	
//	/**
//	 * 数据源dataSource配置
//	 * @author corbett
//	 * @data 2017年12月13日
//	 * @return
//	 */
//	@Bean(name = "dataSource01")
//	public DataSource dataSource(){
//		 DruidDataSource dataSource = new DruidDataSource();  
//		 dataSource.setUrl(env.getProperty("spring.datasource1.url"));
//		 dataSource.setUsername(env.getProperty("spring.datasource1.username"));
//		 dataSource.setPassword(env.getProperty("spring.datasource1.password"));
//		 dataSource.setDriverClassName(env.getProperty("spring.datasource1.driver-class-name"));
//		 dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource1.dbcp.initial-size")));
//		 dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource1.dbcp.min-idle")));
//		 dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource1.dbcp.max-active")));
//		 dataSource.setMaxWait(Long.valueOf(env.getProperty("spring.datasource1.dbcp.max-wait")));
//		 dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("spring.datasource1.dbcp.time-between-eviction-runs-millis")));
//		 dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("spring.datasource1.dbcp.min-evictable-idle-time-millis")));
//		 dataSource.setValidationQuery(env.getProperty("spring.datasource1.dbcp.validation-query"));
//		 dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("spring.datasource1.dbcp.test-while-idle")));
//		 dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("spring.datasource1.dbcp.test-on-borrow")));
//		 dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("spring.datasource1.dbcp.test-on-return")));
//		 return dataSource;
//	}
//	
//	/**
//	 * 数据源sqlSessionFactory配置
//	 * @author corbett
//	 * @data 2017年12月13日
//	 * @param dataSource1
//	 * @return
//	 * @throws Exception
//	 */
//	@Bean
//    public SqlSessionFactory sqlSessionFactory1(@Qualifier("dataSource01") DataSource dataSource1) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dataSource1);
//        return factoryBean.getObject();
//
//    }
//	
//	/**
//	 * 数据源sqlSessionTemplate配置
//	 * @author corbett
//	 * @data 2017年12月13日
//	 * @param dataSource1
//	 * @return
//	 * @throws Exception
//	 */
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate1(@Qualifier("dataSource01") DataSource dataSource1) throws Exception {
//        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory1(dataSource1));
//        return template;
//    }
//    
//    @Bean
//    public PlatformTransactionManager fake1ProdTransactionManager(@Qualifier("dataSource01") DataSource prodDataSource) {
//        return new DataSourceTransactionManager(prodDataSource);
//    }
//     
//
//}
