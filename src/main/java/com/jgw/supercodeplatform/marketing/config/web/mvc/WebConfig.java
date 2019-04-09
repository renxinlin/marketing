package com.jgw.supercodeplatform.marketing.config.web.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 配置mvc参数解析器
 */
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter{
    @Autowired
    SecurityParamResolver securityParamResolver;
    @Override
//    参数解析器，被框架回调
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(securityParamResolver);
    }

}