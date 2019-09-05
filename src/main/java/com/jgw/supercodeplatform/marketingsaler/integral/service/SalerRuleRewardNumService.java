package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleRewardNumMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleRewardNum;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@Service
public class SalerRuleRewardNumService extends SalerCommonService<SalerRuleRewardNumMapper, SalerRuleRewardNum> {


    public boolean exists(String outCodeId) {
        Integer integer = baseMapper.selectCount(query().eq("OutCodeId", outCodeId).getWrapper());
        if(integer == null || integer == 0 ){
            return false;
        }
        return true;

    }
}
