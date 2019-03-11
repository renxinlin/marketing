package com.jgw.supercodeplatform.marketing.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
   * 功能描述：Swagger2Config 配置功能模块
   * @Author corbett
   * @Description //TODO
   * @Date 15:37 2018/10/19
   **/
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {
    @Value("${swagger2.enable}") private boolean enable;

    
    @Bean("营销用户模块")
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.user"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("营销活动模块")
    public Docket actApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("活动模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.activity"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
    
    @Bean("营销微信模块")
    public Docket weixApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.wechat"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("营销h5模块")
    public Docket h5Apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("h5模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.h5"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
    @Bean("公共接口模块")
    public Docket commonApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("公共模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.common"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新超级码系统平台营销系统接口文档")
                .description("")
                .termsOfServiceUrl("")
                .version("0.1")
                .build();
    }
    
}