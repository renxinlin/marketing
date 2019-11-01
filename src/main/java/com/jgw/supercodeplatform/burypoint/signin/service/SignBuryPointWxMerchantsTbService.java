package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointWxMerchantsTbMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointWxMerchantsTb;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/18 16:10
 */
@Service
public class SignBuryPointWxMerchantsTbService {
    @Autowired
    private SignBuryPointWxMerchantsTbMapper signBuryPointWxMerchantsTbMapper;
    
    public void buryPointWxMerchantsTb(String wxPicture, H5LoginVO user){
        BuryPointWxMerchantsTb buryPointWxMerchantsTb=new BuryPointWxMerchantsTb();
        buryPointWxMerchantsTb.setCreateUser(user.getMemberName());
        buryPointWxMerchantsTb.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointWxMerchantsTb.setCreateDate(new Date());
        buryPointWxMerchantsTb.setOrganizationId(user.getOrganizationId());
        buryPointWxMerchantsTb.setOrganizationName(user.getOrganizationName());
        buryPointWxMerchantsTb.setWxPicture(wxPicture);
        try {
            signBuryPointWxMerchantsTbMapper.insert(buryPointWxMerchantsTb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
