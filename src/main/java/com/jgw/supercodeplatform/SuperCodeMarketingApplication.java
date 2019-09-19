package com.jgw.supercodeplatform;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableAsync//允许异步
@EnableScheduling
@EnableTransactionManagement
@EnableApolloConfig

//@EnableHystrix  //断路器，对服务的延迟和容错进行兜底处理|超时机制/信号灯，线程
public class SuperCodeMarketingApplication {


    public static void main(String[] args) {
        // 当netty不兼容的处理
     //   System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SuperCodeMarketingApplication.class, args);
    }

}
