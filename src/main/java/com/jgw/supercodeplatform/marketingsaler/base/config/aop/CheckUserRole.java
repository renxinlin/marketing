package com.jgw.supercodeplatform.marketingsaler.base.config.aop;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.enums.market.SaleUserStatus;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 动态索引创建切面
 */
@Aspect
@Component
@Slf4j
public class CheckUserRole  implements Ordered {


    @Pointcut("@annotation(com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole)")
    public void pointCut() {

    }
    @Autowired
    private UserService userService;

    @Around(value = "pointCut()")
    public Object beforeBiz(ProceedingJoinPoint pj) throws Throwable {
        Signature signature = pj.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        boolean annotationPresent = targetMethod.isAnnotationPresent(CheckRole.class);
        Object[] args = pj.getArgs();
        H5LoginVO user = null;
        for (Object arg : args) {
            if (arg instanceof H5LoginVO) {
                user = (H5LoginVO) arg;
            }
        }
        if (user == null) {
            throw new RuntimeException("角色鉴定失败");
        }
        try {
            if (annotationPresent) {
                CheckRole annotation = targetMethod.getAnnotation(CheckRole.class);
                if (!StringUtils.isEmpty(annotation.role())) {
                    log.info("annotation.role().equals(user.getMemberType().toString())==>{},{}",annotation.role(),user.getMemberType().toString());
                    if (!annotation.role().equals(user.getMemberType().toString())) {
                        throw new RuntimeException("角色鉴定失败");
                    }
                }else {
                    throw new RuntimeException("角色鉴定失败");
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("角色鉴定失败");
        }
        // 只校验状态，存在性于业务校验
        User exists = userService.exists(user);
        if(exists!=null){
            Asserts.check(exists.getState()!=null&&exists.getState().intValue() == SaleUserStatus.ENABLE.getStatus().intValue(),"导购员未启用");
        }
        return pj.proceed();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
