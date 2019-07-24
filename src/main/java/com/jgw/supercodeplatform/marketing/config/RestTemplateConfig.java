package com.jgw.supercodeplatform.marketing.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置类
 * @author jgw136
 *
 */
@Configuration
public class RestTemplateConfig {
	
	@SuppressWarnings("deprecation")
	@LoadBalanced
	@Bean
	public AsyncRestTemplate asyncRestTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		//设置链接超时时间
		factory.setConnectTimeout(1000);
		//设置读取资料超时时间
		factory.setReadTimeout(2000);
		//设置异步任务（线程不会重用，每次调用时都会重新启动一个新的线程）
		factory.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return new AsyncRestTemplate(factory);
	}

	@LoadBalanced
    @Bean
	public RestTemplate restTemplate() {
		/*RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));//解决中文乱码
*/		return new RestTemplate();
	}
}
