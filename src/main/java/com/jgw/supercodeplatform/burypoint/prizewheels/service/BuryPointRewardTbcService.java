package com.jgw.supercodeplatform.burypoint.prizewheels.service;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointRewardTbcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointRewardTbc;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 14:14
 */
@Service
public class BuryPointRewardTbcService {
    private Logger logger = LoggerFactory.getLogger(BuryPointRewardTbcService.class);
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BuryPointRewardTbcMapper buryPointRewardTbcMapper;

    /**
     * 埋点奖励发放
     * @param buryPointRewardTbcDto
     */
    public void buryPointRewardTbc(BuryPointRewardTbcDto buryPointRewardTbcDto){
        BuryPointRewardTbc buryPointRewardTbc=new BuryPointRewardTbc();
        buryPointRewardTbc.setCreateUser(commonUtil.getUserLoginCache().getUserName());
        buryPointRewardTbc.setCreateUserId(commonUtil.getUserLoginCache().getUserId());
        buryPointRewardTbc.setCreateDate(new Date());
        buryPointRewardTbc.setOrganizationId(commonUtil.getOrganizationId());
        buryPointRewardTbc.setOrganizationName(commonUtil.getOrganizationName());
        buryPointRewardTbc.setActivityId(buryPointRewardTbcDto.getActivityId());
        buryPointRewardTbc.setRewardId(buryPointRewardTbcDto.getRewardId());
        buryPointRewardTbc.setRewardName(buryPointRewardTbcDto.getRewardName());
        buryPointRewardTbcMapper.insert(buryPointRewardTbc);
        logger.info("插入c端发放奖励埋点数据:"+buryPointRewardTbc.toString());
    }



}
