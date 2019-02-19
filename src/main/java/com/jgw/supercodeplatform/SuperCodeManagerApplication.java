package com.jgw.supercodeplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableEurekaClient
@EnableAsync//允许异步
@EnableScheduling
public class SuperCodeManagerApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(SuperCodeManagerApplication.class, args);
    }

}
