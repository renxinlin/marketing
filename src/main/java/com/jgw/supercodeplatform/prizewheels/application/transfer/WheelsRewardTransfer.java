package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
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
}
