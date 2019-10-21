package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointWxMerchantsTcDto;
import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointWxMerchantsTcMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointWxMerchantsTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 15:51
 */
@Service
public class SignBuryPointWxMerchantsTcService {
    @Autowired
    private SignBuryPointWxMerchantsTcMapper signBuryPointWxMerchantsTcMapper;

    public void buryPointWxMerchantsTc(BuryPointWxMerchantsTcDto buryPointWxMerchantsTcDto, H5LoginVO user){
        BuryPointWxMerchantsTc buryPointWxMerchantsTc=new BuryPointWxMerchantsTc();
        buryPointWxMerchantsTc.setCreateUser(user.getMemberName());
        buryPointWxMerchantsTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointWxMerchantsTc.setCreateDate(new Date());
        buryPointWxMerchantsTc.setOrganizationId(user.getOrganizationId());
        buryPointWxMerchantsTc.setOrganizationName(user.getOrganizationName());
        buryPointWxMerchantsTc.setMchAppid(buryPointWxMerchantsTcDto.getMchAppid());
        buryPointWxMerchantsTc.setMerchantName(buryPointWxMerchantsTcDto.getMerchantName());
        try {
            signBuryPointWxMerchantsTcMapper.insert(buryPointWxMerchantsTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
