package com.jgw.supercodeplatform.marketingsaler.base.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketingsaler.base.mapper.CommonMapper;
import com.jgw.supercodeplatform.user.UserInfoUtil;
import com.jgw.supercodeplatform.utils.SpringContextUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  基础sevice
 *  目前规范暂不需要接口
 * </p>
 * @author renxinlin
 * @since 2019-07-10
 */
@Component
public class SalerCommonService<M extends CommonMapper<T>, T>   extends ServiceImpl<CommonMapper<T>, T> {

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    protected UserInfoUtil userInfoUtil;


    @Autowired
    protected CommonUtil commonUtil;




}
