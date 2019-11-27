package com.jgw.supercodeplatform.burypoint.prizewheels.service.set;

import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointRewardTbcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward.BuryPointWheelsClickTcDto;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointRewardTbcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.mapper.BuryPointWheelsClickTcMapper;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointRewardTbc;
import com.jgw.supercodeplatform.burypoint.prizewheels.model.BuryPointWheelsClickTc;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 14:14
 */
@Service
public class BuryPointRewardTbcService {

    @Autowired
    private BuryPointRewardTbcMapper buryPointRewardTbcMapper;

    @Autowired
    private BuryPointWheelsClickTcMapper buryPointWheelsClickTcMapper;

    /**
     * 大转盘点击次数埋点
     * @param buryPointWheelsClickTcDto
     * @param user
     */
    public void buryPointWheelsClickTc(BuryPointWheelsClickTcDto buryPointWheelsClickTcDto,H5LoginVO user){
        BuryPointWheelsClickTc buryPointWheelsClickTc=new BuryPointWheelsClickTc();
        buryPointWheelsClickTc.setCreateUser(user.getMemberName());
        buryPointWheelsClickTc.setCreateUserId(String.valueOf(user.getMemberId()));
        buryPointWheelsClickTc.setCreateDate(new Date());
        buryPointWheelsClickTc.setOrganizationId(user.getOrganizationId());
        buryPointWheelsClickTc.setOrganizationName(user.getOrganizationName());
        buryPointWheelsClickTc.setWheelsId(buryPointWheelsClickTcDto.getWheelsId());
        buryPointWheelsClickTc.setMemberType(user.getMemberType());
        buryPointWheelsClickTc.setMobile(buryPointWheelsClickTcDto.getMobile());
        buryPointWheelsClickTc.setMobileModel(buryPointWheelsClickTcDto.getMobileModel());
        buryPointWheelsClickTc.setSystemModel(buryPointWheelsClickTcDto.getSystemModel());
        buryPointWheelsClickTc.setBrowser(buryPointWheelsClickTcDto.getBrowser());
        buryPointWheelsClickTc.setBrowserModel(buryPointWheelsClickTcDto.getBrowserModel());
        try{
            buryPointWheelsClickTcMapper.insert(buryPointWheelsClickTc);
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
        buryPointRewardTbc.setWheelsId(buryPointRewardTbcDto.getWheelsId());
        buryPointRewardTbc.setRewardId(buryPointRewardTbcDto.getRewardId());
        buryPointRewardTbc.setRewardName(buryPointRewardTbcDto.getRewardName());
        buryPointRewardTbc.setThirdUrl(buryPointRewardTbcDto.getThirdUrl());
        buryPointRewardTbc.setMemberType(user.getMemberType());
        buryPointRewardTbc.setMobile(buryPointRewardTbcDto.getMobile());
        buryPointRewardTbc.setMobileModel(buryPointRewardTbcDto.getMobileModel());
        buryPointRewardTbc.setSystemModel(buryPointRewardTbcDto.getSystemModel());
        buryPointRewardTbc.setBrowser(buryPointRewardTbcDto.getBrowser());
        buryPointRewardTbc.setBrowserModel(buryPointRewardTbcDto.getBrowserModel());
        try {
            buryPointRewardTbcMapper.insert(buryPointRewardTbc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
