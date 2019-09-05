package com.jgw.supercodeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableAsync//允许异步
@EnableScheduling
@EnableTransactionManagement
@EnableApolloConfig
@EnableFeignClients
@EnableHystrix
public class SuperCodeMarketingApplication {


    public static void main(String[] args) {
        // 当netty不兼容的处理
     //   System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SuperCodeMarketingApplication.class, args);
    }

}
