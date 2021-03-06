package com.jgw.supercodeplatform.marketingsaler.base.config.aop;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.interceptor.InterceptorCommon;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.IpUtils;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class NoRepeatTransaction implements HandlerInterceptor {
    private static final String MARKETING_UNIQUE = "marketing:exists:";
    private static final String EXIST = "EXIST";
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uniqueKey = request.getRequestURI() + IpUtils.getClientIpAddr(request);
         log.info("防止重复...{}",uniqueKey);
        String exists = redisUtil.get(MARKETING_UNIQUE + uniqueKey);
        if(StringUtils.isEmpty(exists)){
            // todo 抽到外部配置
            boolean set = redisUtil.set(MARKETING_UNIQUE + uniqueKey, EXIST,1L);
            return true;
        }
        response.addHeader("Content-Type", "application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JSON.toJSONString(new RestResult( 500, "请勿重复提交", (Object)null)));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
