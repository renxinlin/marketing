package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.GetBatchInfoDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlUnBindDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "${platform.codemanager.mircosoft.name:platform-codemanager-supercode-dev}",fallback = GetSbatchIdsByPrizeWheelsFallbackFeign.class )
public interface GetSbatchIdsByPrizeWheelsFeign {
    // object 会返回2种类型
   // @RequestMapping(value = "/code/relation/getBatchInfoWithoutType",method = RequestMethod.POST) 码管理业务bug修改
    @RequestMapping(value = "/code/relation/getRelationCodeList/batchInfoList",method = RequestMethod.POST)
    RestResult<Object> getSbatchIds(List<GetBatchInfoDto> getBatchInfoDtoList);


//    @RequestMapping(value = "/code/sbatchUrl/delete/one",method = RequestMethod.POST)
    @RequestMapping(value = "/code/manager/batch/url/delete/list",method = RequestMethod.POST)
    RestResult<Object> removeOldProduct(List<SbatchUrlUnBindDto> sbatchUrlDtoList);



//    @RequestMapping(value = "/code/sbatchUrl/addSbatchUrl",method = RequestMethod.POST)
    @RequestMapping(value = "/code/manager/batch/url/add/list",method = RequestMethod.POST)
    RestResult bindingUrlAndBizType(List<SbatchUrlDto> sbatchUrlDtoList);
}
