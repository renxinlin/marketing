package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class GetSbatchIdsByPrizeWheelsFallbackFeign implements GetSbatchIdsByPrizeWheelsFeign {
    // 返回对象和boolean两种格式
    @Override
    public RestResult<Object> getSbatchIds(List<GetBatchInfoDto> getBatchInfoDtoLists) {
        log.error("GetSbatchIdsByPrizeWheelsFallbackFeign获取码管理信息失败");
        return null;
    }

    @Override
    public RestResult<Object> removeOldProduct(List<SbatchUrlDto> sbatchUrlDtoList) {
        log.error("removeOldProduct失败");
        return null;
    }

    @Override
    public RestResult bindingUrlAndBizType(List<SbatchUrlDto> sbatchUrlDtoList) {
        log.error("bindingUrlAndBizType失败");
        return null;
    }
}
