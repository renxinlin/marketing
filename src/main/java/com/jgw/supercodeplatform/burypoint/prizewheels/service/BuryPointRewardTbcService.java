package com.jgw.supercodeplatform.burypoint.prizewheels.service;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointRewardTbcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointRewardTbc;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
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
    public void buryPointRewardTbc(BuryPointRewardTbcDto buryPointRewardTbcDto, H5LoginVO user){
        BuryPointRewardTbc buryPointRewardTbc=new BuryPointRewardTbc();
        buryPointRewardTbc.setCreateUser(user.getMemberName());
        buryPointRewardTbc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointRewardTbc.setCreateDate(new Date());
        buryPointRewardTbc.setOrganizationId(user.getOrganizationId());
        buryPointRewardTbc.setOrganizationName(user.getOrganizationName());
        buryPointRewardTbc.setActivityId(buryPointRewardTbcDto.getActivityId());
        buryPointRewardTbc.setRewardId(buryPointRewardTbcDto.getRewardId());
        buryPointRewardTbc.setRewardName(buryPointRewardTbcDto.getRewardName());
        try {
            buryPointRewardTbcMapper.insert(buryPointRewardTbc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
