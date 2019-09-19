package com.jgw.supercodeplatform.marketing.controller.platform;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.pojo.platform.AbandonPlatform;
import com.jgw.supercodeplatform.marketing.service.activity.PlatformActivityService;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformScanStatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/marketing/activity/platform/h5")
@Api(tags = "全平台活动H5页面")
public class PlatformH5Controller {

    @Autowired
    private CommonService commonService;

    @Autowired
    private CodeEsService odeEsService;

    @Autowired
    private PlatformActivityService platformActivityService;

    @ApiOperation("获取该码是否被扫过<true表示被扫过，false表示没有被扫过>")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeId", paramType = "query", value = "码", required = true),
            @ApiImplicitParam(name = "codeTypeId", paramType = "query", value = "码制", required = true)})
    @GetMapping("/scanStatus")
    public RestResult<PlatformScanStatusVo> getScanStatus(@RequestParam String codeId, @RequestParam String codeTypeId){
        commonService.checkCodeValid(codeId, codeTypeId);
        commonService.checkCodeTypeValid(Long.valueOf(codeTypeId));
        PlatformScanStatusVo platformScanStatusVo = platformActivityService.getScanStatus(codeId);
        return RestResult.successWithData(platformScanStatusVo);
    }

    @ApiOperation("放弃抽奖")
    @PostMapping("/abandonLottery")
    public RestResult<?> abandonLottery(@RequestBody @Valid AbandonPlatform abandonPlatform){
        platformActivityService.addAbandonPlatform(abandonPlatform);
        return RestResult.success();
    }

//    public RestResult<?> lottery(){
//
//    }

}
