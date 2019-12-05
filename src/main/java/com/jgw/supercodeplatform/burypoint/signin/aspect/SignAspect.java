package com.jgw.supercodeplatform.burypoint.signin.aspect;

import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointOuterChainTbMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointOuterChainTb;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import org.aspectj.lang.JoinPoint;
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
public class SignAspect {
    private Logger logger = LoggerFactory.getLogger(SignAspect.class);
    @Autowired
    private SignBuryPointOuterChainTbMapper signBuryPointOuterChainTbMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ModelMapper modelMapper;
    /**
     * controller层切点
     */
    @Pointcut("execution(* com.jgw.supercodeplatform.signin.controller.SignInController.save(..))")
    public void signControllerAspect(){}


    /**
     * 目标方法正常返回时的通知方法
     * @param joinPoint
     */
    @AfterReturning(value = "signControllerAspect()")
    public void afterReturn(JoinPoint joinPoint){
        logger.info("进入切面--------------切点："+joinPoint.getSignature());
        //获取参数
        Object[] argsList=joinPoint.getArgs();
        WheelsDto wheelsDto= (WheelsDto) argsList[0];
        Publisher publisher=new Publisher();
        publisher.initUserInfoWhenFirstPublish(commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        BuryPointOuterChainTb buryPointOuterChainTb=modelMapper.map(publisher, BuryPointOuterChainTb.class);
        buryPointOuterChainTb.setThirdUrl(wheelsDto.getThirdUrl());
        buryPointOuterChainTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointOuterChainTb.setOrganizationName(commonUtil.getOrganizationName());
        try {
            signBuryPointOuterChainTbMapper.insert(buryPointOuterChainTb);
        } catch (Exception e) {
            logger.info("插入b端签到链接埋点数据出错："+buryPointOuterChainTb.toString());
            e.printStackTrace();
        }
        logger.info("成功插入b端签到链接埋点数据："+buryPointOuterChainTb.toString());
    }
}
