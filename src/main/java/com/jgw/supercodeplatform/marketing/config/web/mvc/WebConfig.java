package com.jgw.supercodeplatform.marketing.config.web.mvc;


import com.jgw.supercodeplatform.marketingsaler.base.config.aop.NoRepeatTransaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置mvc参数解析器
 */
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter{
    @Autowired
    SecurityParamResolver securityParamResolver;
    @Autowired
    NoRepeatTransaction noRepeatTransaction;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(noRepeatTransaction).addPathPatterns("/marketing/h5/salerRuleExchange/exchange");


        super.addInterceptors(registry);
    }

    @Override
//    参数解析器，被框架回调
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(securityParamResolver);
    }

    // 添加对象转换器
    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
//                // 由默认的LOOSE松散策略策略改成STRICT策略，从而前缀匹配改成严格匹配
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}