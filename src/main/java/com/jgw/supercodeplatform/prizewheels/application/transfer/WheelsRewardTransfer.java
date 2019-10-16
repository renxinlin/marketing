package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.CdkKey;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
public class WheelsRewardTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<WheelsReward> transferUpdateDtoToDomain(List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos, Long prizeWheelsid,double loseAwardProbability) {
        List<WheelsReward> rewards = wheelsRewardUpdateDtos
                .stream()
                .map(wheelsRewardUpdateDto -> {
                    WheelsReward wheelsReward = modelMapper.map(wheelsRewardUpdateDto, WheelsReward.class);
                    try {
                        // 有且仅有一个,前端格式如此
                        wheelsReward.setCdkName(wheelsRewardUpdateDto.getCdkKey().get(0).getName());
                        wheelsReward.setCdkUuid(wheelsRewardUpdateDto.getCdkKey().get(0).getUuid());
                    } catch (Exception e) {
                        log.info("暂无cdk");
                    }
                    wheelsReward.setPrizeWheelId(prizeWheelsid);


                    return wheelsReward;
                })
                .collect(Collectors.toList());
        WheelsReward wheelsReward = new WheelsReward();
        wheelsReward.setName("_未中奖_");
        wheelsReward.setPicture("");
        wheelsReward.setPrizeWheelId(prizeWheelsid);
        wheelsReward.setProbability(loseAwardProbability);
        wheelsReward.setLoseAward(LoseAwardConstant.yes);
        rewards.add(wheelsReward);
        return rewards;
    }

    public List<WheelsReward> transferDtoToDomain(List<WheelsRewardDto> wheelsRewardDtos, double loseAwardProbability) {
        List<WheelsReward> list = wheelsRewardDtos
                .stream()
                .map(wheelsRewardDto -> {
                    WheelsReward wheelsReward = modelMapper.map(wheelsRewardDto, WheelsReward.class);
                    try {
                        // 有且仅有一个,前端格式如此
                        wheelsReward.setCdkName(wheelsRewardDto.getCdkKey().get(0).getName());
                        wheelsReward.setCdkUuid(wheelsRewardDto.getCdkKey().get(0).getUuid());
                    } catch (Exception e) {
                         log.info("暂无cdk");
                    }
                    wheelsReward.setLoseAward(LoseAwardConstant.no);
                    return wheelsReward;
                })
                .collect(Collectors.toList());

        WheelsReward wheelsReward = new WheelsReward();
        wheelsReward.setName("_未中奖_");
        wheelsReward.setPicture("");
        wheelsReward.setProbability(loseAwardProbability);
        wheelsReward.setLoseAward(LoseAwardConstant.yes);
        list.add(wheelsReward);
        return list;
    }
    public List<WheelsRewardUpdateDto> transferRewardToDomain(List<WheelsRewardPojo> wheelsRewardPojos){
        return wheelsRewardPojos
                .stream()
                .map(wheelsRewardPojo -> {
                    WheelsRewardUpdateDto wheelsRewardUpdateDto=modelMapper.map(wheelsRewardPojo,WheelsRewardUpdateDto.class);
                    // 有且仅有一个,前端格式如此
                    List<CdkKey> cdkkey = new ArrayList<>();
                    CdkKey element = new CdkKey();
                    element.setName(wheelsRewardPojo.getCdkName());
                    element.setUuid(wheelsRewardPojo.getCdkUuid());
                    cdkkey.add(element);
                    wheelsRewardUpdateDto.setCdkKey(cdkkey);
                    return wheelsRewardUpdateDto;
                }).collect(Collectors.toList());
    }
}
