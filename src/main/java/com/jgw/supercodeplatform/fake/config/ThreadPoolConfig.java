package com.jgw.supercodeplatform.fake.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池config
 * @author liujianqiang
 * @date 2018年7月18日
 */
@Configuration
public class ThreadPoolConfig {
	
	@Bean(name="taskExecutor")
	public TaskExecutor taskExecutor(){
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(700);//线程池维护线程最小数量,线程池的大小
		executor.setMaxPoolSize(2000);//最大数量
		executor.setQueueCapacity(700);//持有等待执行的任务队列,当当前线程数超过CorePoolSize就会等待
		executor.setKeepAliveSeconds(5);//线程多久没执行关闭线程,单位秒,默认60秒
		executor.setThreadNamePrefix("my_thread");//线程名前缀
		executor.initialize();
		return executor;
	}
	
}
