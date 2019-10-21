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

    
    @Bean("测试使用模块")
    public Docket testApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.test"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }
    
    @Bean("营销活动模块")
    public Docket actApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("导购活动模块")
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

    @Bean("营积分h5模块")
    public Docket integralh5Apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("营积分h5模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.h5.integral"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("活动h5模块")
    public Docket activityh5Apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("活动h5模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.h5.activity"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("积分模块")
    public Docket integralApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("积分模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.integral"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }


    @Bean("会员H5模块")
    public Docket memberApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("会员H5模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.h5.member"))
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


    @Bean("统计模块")
    public Docket diagramApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("统计模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.diagram"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }



    @Bean("销售员积分模块")
    public Docket salerIntegralApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("销售员积分模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketingsaler.integral"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("订货模块")
    public Docket orderApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("订货模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketingsaler.order"))
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

    @Bean("全平台活动模块")
    public Docket platformApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("全平台活动模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.platform"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("活动显示开关模块")
    public Docket dispacherApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("活动显示开关模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.marketing.controller.dispacther"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }



    @Bean("大转盘")
    public Docket prizeWheels() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("大转盘")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.prizewheels.interfaces"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }

    @Bean("大转盘埋点")
    public Docket prizeWheelsBuryPoint() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("大转盘埋点")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jgw.supercodeplatform.burypoint.prizewheels.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enable);
    }





}