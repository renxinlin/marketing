package com.jgw.supercodeplatform.prizewheels.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CdkEvent {
    private Long prizeRewardId;
    private String cdkUuid;
}
