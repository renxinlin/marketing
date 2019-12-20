package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralRewardSettingAggDto;

public interface ReadSettingInfoRepository {
    String integralRewardSettingAggDtoPrefix = "marketing:mutiintegral-snapshot:";

    void updateSnapshotInfo(IntegralRewardSettingAggDto integralRewardSettingAggDto);

    IntegralRewardSettingAggDto  getSnapshotInfo();

}
