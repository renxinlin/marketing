package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardCommonDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.IntegralLimitTypeConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.ExpiredTypeConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Slf4j
public class IntegralRuleDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 积分有效期1永久有效 2到期时间
     */
    private Integer expiredType;

    /**
     * 过期时间
     */
    private Date expiredDate;

    /**
     * 积分上限
     */
    private Integer integralLimit;

    /**
     * 1无上限2 有上限
     */
    private Integer integralLimitType;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;

    private String updateUserId;

    private Date updateDate;

    private String updateUserName;

    private List<IntegralRuleRewardCommonDomain> integralRuleRewardCommonDomains;


    public void checkWhenSetting() {
        checkexpire();
        checkLimit();
        // 通用积分设置校验
        integralRuleRewardCommonDomains.forEach(
                integralRuleRewardCommonDomain->integralRuleRewardCommonDomain.checkWhenSetting());

    }

    private void checkLimit() {
        log.info("积分上限设置{}",integralLimitType);
        if(integralLimitType == IntegralLimitTypeConstants.limit ){
            Asserts.check(integralLimit != null,IntegralLimitTypeConstants.limitError);
            Asserts.check(integralLimit > 0 ,IntegralLimitTypeConstants.limitError);
        }
    }

    private void checkexpire() {
        log.info("积分设置时间检查{}",expiredDate);
         if(expiredType == ExpiredTypeConstants.utilTime ){
             Asserts.check(expiredDate != null,ExpiredTypeConstants.utilTimeError);
             Asserts.check(expiredDate.after(new Date()),ExpiredTypeConstants.utilTimeError);
         }
    }



    @Test
    public  void test() {
    }
}
