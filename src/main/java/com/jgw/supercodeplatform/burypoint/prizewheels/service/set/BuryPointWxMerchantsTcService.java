package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointWxMerchantsTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointWxMerchantsTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointWxMerchantsTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 15:51
 */
@Service
public class BuryPointWxMerchantsTcService {
    @Autowired
    private BuryPointWxMerchantsTcMapper buryPointWxMerchantsTcMapper;

    public void buryPointWxMerchantsTc(BuryPointWxMerchantsTcDto buryPointWxMerchantsTcDto, H5LoginVO user){
        BuryPointWxMerchantsTc buryPointWxMerchantsTc=new BuryPointWxMerchantsTc();
        if (user !=null ){
            buryPointWxMerchantsTc.setCreateUser(user.getMemberName());
            buryPointWxMerchantsTc.setCreateUserId(String.valueOf(user.getMemberId()));
            buryPointWxMerchantsTc.setOrganizationId(user.getOrganizationId());
            buryPointWxMerchantsTc.setOrganizationName(user.getOrganizationName());
            buryPointWxMerchantsTc.setMemberType(user.getMemberType());
            buryPointWxMerchantsTc.setMobile(user.getMobile());
        }

        buryPointWxMerchantsTc.setCreateDate(new Date());

        buryPointWxMerchantsTc.setMchAppid(buryPointWxMerchantsTcDto.getMchAppid());
        buryPointWxMerchantsTc.setMerchantName(buryPointWxMerchantsTcDto.getMerchantName());

        buryPointWxMerchantsTc.setMobileModel(buryPointWxMerchantsTcDto.getMobileModel());
        buryPointWxMerchantsTc.setSystemModel(buryPointWxMerchantsTcDto.getSystemModel());
        buryPointWxMerchantsTc.setBrowser(buryPointWxMerchantsTcDto.getBrowser());
        buryPointWxMerchantsTc.setBrowserModel(buryPointWxMerchantsTcDto.getBrowserModel());
        try {
            buryPointWxMerchantsTcMapper.insert(buryPointWxMerchantsTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
