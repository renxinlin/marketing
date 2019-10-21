package com.jgw.supercodeplatform.prizewheels.domain.event;

import com.jgw.supercodeplatform.prizewheels.domain.model.ScanRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanRecordWhenRewardEvent {
    private ScanRecord scanRecord;



}
