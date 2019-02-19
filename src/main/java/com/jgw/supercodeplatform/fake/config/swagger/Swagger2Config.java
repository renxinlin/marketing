package com.jgw.supercodeplatform.fake.config.swagger;

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

    
    @Bean("防伪模块")
    public Docket orgApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("防伪模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.fake.controller.relation"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("新超级码系统平台接口文档")
                .description("")
                .termsOfServiceUrl("")
                .version("0.1")
                .build();
    }
    
}