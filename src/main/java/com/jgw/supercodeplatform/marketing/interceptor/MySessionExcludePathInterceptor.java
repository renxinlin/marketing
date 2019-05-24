package com.jgw.supercodeplatform.marketing.interceptor;

import com.jgw.supercodeplatform.web.WebMvcSessionInterceptorPathConfigurer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 描述：用户不用登入的接口放行
 * <p>
 * Created by corbett on 2018/12/27.
 */
@Component
public class MySessionExcludePathInterceptor implements WebMvcSessionInterceptorPathConfigurer {
    @Override
    public List<String> excludePathPatterns() {
        String[] add = new String[]{
                //swagger
                "/swagger-resources/configuration/ui",
                "/swagger-resources",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/v2/**",
                "/doc.html",
                "/marketing/front/scan",
                "/marketing/front/**",//h5接口不拦截
                "/marketing/common/**",
                "/marketing/test/**",//测试
                "/MP_verify_dSYneurbj349sTas.txt",
                //静态资源
                "/webjars/**",
                "/marketing/h5/**",
                "/marketing/address/**",
                "/marketing/jwt/**",
                "/marketing/front/saler/**",
                "/marketing/saleMember/**"


        };
        return Arrays.asList(add);
    }
}
