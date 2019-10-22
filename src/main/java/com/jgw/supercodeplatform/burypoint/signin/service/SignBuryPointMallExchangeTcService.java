package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.dto.mall.BuryPointMallExchangeTcDto;
import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointMallExchangeTcMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointMallExchangeTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/21 16:07
 */
@Service
public class SignBuryPointMallExchangeTcService {
    @Autowired
    private SignBuryPointMallExchangeTcMapper signBuryPointMallExchangeTcMapper;

    public void signBuryPointMallExchangeTc(BuryPointMallExchangeTcDto buryPointMallExchangeTcDto, H5LoginVO user){
        BuryPointMallExchangeTc buryPointMallExchangeTc=new BuryPointMallExchangeTc();
        buryPointMallExchangeTc.setCreateUser(user.getMemberName());
        buryPointMallExchangeTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointMallExchangeTc.setOrganizationId(user.getOrganizationId());
        buryPointMallExchangeTc.setOrganizationName(user.getOrganizationName());
        buryPointMallExchangeTc.setMallId(buryPointMallExchangeTcDto.getMallId());
        buryPointMallExchangeTc.setMallName(buryPointMallExchangeTcDto.getMallName());
        buryPointMallExchangeTc.setGoodsCondition(buryPointMallExchangeTcDto.getGoodsCondition());
        buryPointMallExchangeTc.setActivityId(buryPointMallExchangeTcDto.getActivityId());
        buryPointMallExchangeTc.setCreateDate(new Date());
        try {
            signBuryPointMallExchangeTcMapper.insert(buryPointMallExchangeTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
