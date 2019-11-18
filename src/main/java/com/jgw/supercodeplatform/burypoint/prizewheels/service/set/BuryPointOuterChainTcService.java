package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointOuterChainTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointOuterChainTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 13:18
 */
@Service
public class BuryPointOuterChainTcService {

    @Autowired
    private BuryPointOuterChainTcMapper buryPointOuterChainTcMapper;

    public void buryPointOuterChainTc(BuryPointOuterChainTcDto buryPointOuterChainTcDto, H5LoginVO user){
        BuryPointOuterChainTc buryPointOuterChainTc=new BuryPointOuterChainTc();
        buryPointOuterChainTc.setCreateUser(user.getMemberName());
        buryPointOuterChainTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointOuterChainTc.setOrganizationId(user.getOrganizationId());
        buryPointOuterChainTc.setOrganizationName(user.getOrganizationName());
        buryPointOuterChainTc.setThirdUrl(buryPointOuterChainTcDto.getThirdUrl());
        buryPointOuterChainTc.setWheelsId(buryPointOuterChainTcDto.getWheelsId());
        buryPointOuterChainTc.setCreateDate(new Date());
        buryPointOuterChainTc.setMemberType(user.getMemberType());
        try {
            buryPointOuterChainTcMapper.insert(buryPointOuterChainTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
