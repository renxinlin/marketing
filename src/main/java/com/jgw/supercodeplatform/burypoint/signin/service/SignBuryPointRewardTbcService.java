package com.jgw.supercodeplatform.burypoint.signin.service;

import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.signin.dto.reward.BuryPointSignClickTcDto;
import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointRewardTbcMapper;
import com.jgw.supercodeplatform.burypoint.signin.mapper.SignBuryPointSignClickTcMapper;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointRewardTbc;
import com.jgw.supercodeplatform.burypoint.signin.model.BuryPointSignClickTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 14:14
 */
@Service
public class SignBuryPointRewardTbcService {

    @Autowired
    private SignBuryPointRewardTbcMapper signBuryPointRewardTbcMapper;

    @Autowired
    private SignBuryPointSignClickTcMapper signBuryPointSignClickTcMapper;

    /**
     * 大转盘点击次数埋点
     * @param buryPointSignClickTcDto
     * @param user
     */
    public void buryPointWheelsClickTc(BuryPointSignClickTcDto buryPointSignClickTcDto, H5LoginVO user){
        BuryPointSignClickTc buryPointSignClickTc =new BuryPointSignClickTc();
        buryPointSignClickTc.setCreateUser(user.getMemberName());
        buryPointSignClickTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointSignClickTc.setCreateDate(new Date());
        buryPointSignClickTc.setOrganizationId(user.getOrganizationId());
        buryPointSignClickTc.setOrganizationName(user.getOrganizationName());
        buryPointSignClickTc.setActivityId(buryPointSignClickTcDto.getActivityId());
        try{
            signBuryPointSignClickTcMapper.insert(buryPointSignClickTc);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

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
        buryPointRewardTbc.setThirdUrl(buryPointRewardTbcDto.getThirdUrl());
        try {
            signBuryPointRewardTbcMapper.insert(buryPointRewardTbc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
