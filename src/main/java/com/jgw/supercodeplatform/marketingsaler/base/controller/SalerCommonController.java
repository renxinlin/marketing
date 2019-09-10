package com.jgw.supercodeplatform.marketingsaler.base.controller;

import com.jgw.supercodeplatform.common.util.AreaUtil;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.user.UserInfoUtil;
import com.jgw.supercodeplatform.utils.SpringContextUtil;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * renxinlin
 */
@Component
public class SalerCommonController {
    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected CommonUtil commonUtil;

    @Autowired
    protected RedisUtil redisUtil;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;


    @Autowired
    protected UserInfoUtil userInfoUtil;


    @Autowired
    protected AreaUtil areaUtil;



    public <T> RestResult<T> success(){
        return  RestResult.success(200,"success",null);
    }

    public <T> RestResult<T> success(T result){
        return  RestResult.success(200,"success",result);
    }


    public <T>  RestResult<T> fail(T result){
        return  RestResult.error("error",result,500);
    }

    public  <T> RestResult<T> fail(){
        return  RestResult.error("error",null,500);
    }

    public  <T> RestResult<T> fail(int status,String msg,T data){
        return  RestResult.error(msg,data,status);
    }
}
