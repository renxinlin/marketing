package com.jgw.supercodeplatform.mutIntegral.infrastructure.forkjoin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ForkJoinPool;
@Configuration
public class ForkJoinPoolConfig {

    @Bean
    public ForkJoinPool getForkJoinPool() {
        // 暂用默认配置
        return new ForkJoinPool();
    }


}
