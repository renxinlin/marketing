package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WheelsRewardTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<WheelsReward> transferUpdateDtoToDomain(List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos, Long prizeWheelsid) {
        return wheelsRewardUpdateDtos
                .stream()
                .map(wheelsRewardUpdateDto -> {
                    WheelsReward wheelsReward = modelMapper.map(wheelsRewardUpdateDto, WheelsReward.class);
                    wheelsReward.setPrizeWheelId(prizeWheelsid);
                    return wheelsReward;})
                .collect(Collectors.toList());
    }

    public List<WheelsReward> transferDtoToDomain(List<WheelsRewardDto> wheelsRewardDtos, double loseAwardProbability) {
        List<WheelsReward> list = wheelsRewardDtos
                .stream()
                .map(wheelsRewardDto -> {
                    WheelsReward wheelsReward = modelMapper.map(wheelsRewardDto, WheelsReward.class);
                    wheelsReward.setLoseAward(LoseAwardConstant.no);
                    return wheelsReward;
                })
                .collect(Collectors.toList());

        WheelsReward wheelsReward = new WheelsReward();
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
                    return wheelsRewardUpdateDto;
                }).collect(Collectors.toList());
    }
}
