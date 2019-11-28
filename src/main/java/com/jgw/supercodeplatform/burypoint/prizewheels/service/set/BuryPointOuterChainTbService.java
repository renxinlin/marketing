package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTbDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointOuterChainTbMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointOuterChainTb;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/11/27 11:50
 */
@Service
public class BuryPointOuterChainTbService {
    @Autowired
    private BuryPointOuterChainTbMapper buryPointOuterChainTbMapper;

    @Autowired
    private CommonUtil commonUtil;

    public void buryPointOuterChainTb(BuryPointOuterChainTbDto buryPointOuterChainTbDto){
        BuryPointOuterChainTb buryPointOuterChainTb=new BuryPointOuterChainTb();
        buryPointOuterChainTb.setCreateUser(commonUtil.getUserLoginCache().getUserName());
        buryPointOuterChainTb.setCreateUserId(commonUtil.getUserLoginCache().getUserId());
        buryPointOuterChainTb.setOrganizationId(commonUtil.getOrganizationId());
        buryPointOuterChainTb.setOrganizationName(commonUtil.getOrganizationName());
        buryPointOuterChainTb.setThirdUrl(buryPointOuterChainTbDto.getThirdUrl());
        buryPointOuterChainTb.setCreateDate(new Date());
        buryPointOuterChainTb.setMobile(buryPointOuterChainTbDto.getMobile());
        buryPointOuterChainTb.setMobileModel(buryPointOuterChainTbDto.getMobileModel());
        buryPointOuterChainTb.setSystemModel(buryPointOuterChainTbDto.getSystemModel());
        buryPointOuterChainTb.setBrowser(buryPointOuterChainTbDto.getBrowser());
        buryPointOuterChainTb.setBrowserModel(buryPointOuterChainTbDto.getBrowserModel());
        try {
            buryPointOuterChainTbMapper.insert(buryPointOuterChainTb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
