package com.jgw.supercodeplatform.marketingsaler.base.config;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微服务
 * 跨域请求头处理
 */
@Slf4j
@Component
public class FeignHeaderCross implements RequestInterceptor {

    @Override
    public void apply(feign.RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if(null == httpServletRequest){
            return;
        }
        String superToken = getHeaders(getHttpServletRequest()).get(UserConstants.SUPER_TOKEN);
        log.info("服务调用请求头header{}", JSONObject.toJSONString(superToken));

        if(StringUtils.isEmpty(superToken)){
            Cookie[] cookies = getHttpServletRequest().getCookies();
            for(Cookie cookie : cookies){
                if(UserConstants.SUPER_TOKEN.equals(cookie.getName())){
                    superToken = cookie.getValue();
                }
            }
        log.info("服务调用请求头cookie{}", JSONObject.toJSONString(cookies));
        }

        requestTemplate.header(UserConstants.SUPER_TOKEN, superToken);
    }

    private HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = null;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request  = requestAttributes.getRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }




    /**
     * Feign拦截器拦截请求获取Token对应的值
     * @param request
     * @return
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
