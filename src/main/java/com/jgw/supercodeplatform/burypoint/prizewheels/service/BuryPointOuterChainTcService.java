package com.jgw.supercodeplatform.burypoint.prizewheels.service;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain.BuryPointOuterChainTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointOuterChainTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointOuterChainTc;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 13:18
 */
@Service
public class BuryPointOuterChainTcService {
    private Logger logger = LoggerFactory.getLogger(BuryPointOuterChainTcService.class);
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuryPointOuterChainTcMapper buryPointOuterChainTcMapper;

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
            buryPointOuterChainTcMapper.insert(buryPointOuterChainTc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
