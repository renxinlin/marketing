package com.jgw.supercodeplatform.burypoint.prizewheels.aspect;

import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointOuterChainTbMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointOuterChainTb;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fangshiping
 * @date 2019/10/16 10:26
 */
@Aspect
@Component
public class WheelsAspect {
    private Logger logger = LoggerFactory.getLogger(WheelsAspect.class);
    @Autowired
    private BuryPointOuterChainTbMapper buryPointOuterChainTbMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ModelMapper modelMapper;
    /**
     * controller层切点
     */
    @Pointcut("execution(* com.jgw.supercodeplatform.prizewheels.interfaces.WheelsController.add(..))")
    public void wheelsControllerAspect(){}

    /**
     * 后置通知，用于插入埋点数据
     *//*
    @After("wheelsControllerAspect()")
    public void after(JoinPoint joinPoint){
        //获取参数
        System.out.println("进入切面--------------");
        Object[] argsList=joinPoint.getArgs();
        WheelsDto wheelsDto= (WheelsDto) argsList[0];
        Publisher publisher=new Publisher();
        publisher.initUserInfoWhenFirstPublish(commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        BuryPointOuterChainTb buryPointOuterChainTb=modelMapper.map(publisher,BuryPointOuterChainTb.class);
        buryPointOuterChainTb.setThirdUrl(wheelsDto.getThirdUrl());
        buryPointOuterChainTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointOuterChainTb.setOrganizationName(commonUtil.getOrganizationName());
        logger.info("插入b端大转盘链接埋点数据："+buryPointOuterChainTb.toString());
        buryPointOuterChainTbMapper.insert(buryPointOuterChainTb);
    }*/

    /**
     * 目标方法正常返回时的通知方法
     * @param joinPoint
     */
    @AfterReturning(value = "wheelsControllerAspect()")
    public void afterReturn(JoinPoint joinPoint){
        logger.info("进入切面--------------切点："+joinPoint.getSignature());
        //获取参数
        Object[] argsList=joinPoint.getArgs();
        WheelsDto wheelsDto= (WheelsDto) argsList[0];
        Publisher publisher=new Publisher();
        publisher.initUserInfoWhenFirstPublish(commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        BuryPointOuterChainTb buryPointOuterChainTb=modelMapper.map(publisher,BuryPointOuterChainTb.class);
        buryPointOuterChainTb.setThirdUrl(wheelsDto.getThirdUrl());
        buryPointOuterChainTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointOuterChainTb.setOrganizationName(commonUtil.getOrganizationName());
        logger.info("插入b端大转盘链接埋点数据："+buryPointOuterChainTb.toString());
        buryPointOuterChainTbMapper.insert(buryPointOuterChainTb);
    }
}
