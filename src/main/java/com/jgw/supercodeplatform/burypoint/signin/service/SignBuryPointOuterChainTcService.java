package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointOuterChainTcMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointOuterChainTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 13:18
 */
@Service
public class SignBuryPointOuterChainTcService {

    @Autowired
    private SignBuryPointOuterChainTcMapper signBuryPointOuterChainTcMapper;

    public void buryPointOuterChainTc(BuryPointOuterChainTcDto buryPointOuterChainTcDto, H5LoginVO user){
        BuryPointOuterChainTc buryPointOuterChainTc=new BuryPointOuterChainTc();
        buryPointOuterChainTc.setCreateUser(user.getMemberName());
        buryPointOuterChainTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointOuterChainTc.setOrganizationId(user.getOrganizationId());
        buryPointOuterChainTc.setOrganizationName(user.getOrganizationName());
        buryPointOuterChainTc.setThirdUrl(buryPointOuterChainTcDto.getThirdUrl());
        buryPointOuterChainTc.setActivityId(buryPointOuterChainTcDto.getActivityId());
        buryPointOuterChainTc.setCreateDate(new Date());
        try {
            signBuryPointOuterChainTcMapper.insert(buryPointOuterChainTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
