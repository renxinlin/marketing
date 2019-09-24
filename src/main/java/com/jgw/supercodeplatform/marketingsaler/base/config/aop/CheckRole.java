package com.jgw.supercodeplatform.marketingsaler.base.config.aop;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface CheckRole {
//0会员，1导购员
    String role() default "";
}
