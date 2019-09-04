package com.jgw.supercodeplatform.marketing.controller.platform;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.pojo.platform.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marketing/activity/platform/data")
@Api(tags = "全平台活动数据")
public class PlatformActivityDataController {

    @ApiOperation("获取扫码率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/scanCodeRate")
    public RestResult<ScanCodeDataVo> scanCodeRate(@Valid ActivityDataParam activityDataParam) {
        return RestResult.success();
    }

    @ApiOperation("获取中奖率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/winningPrizeRate")
    public RestResult<WinningPrizeDataVo> winningPrize(@Valid ActivityDataParam activityDataParam){
        return RestResult.success();
    }

    @ApiOperation("获取活动参与率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/activityJoinRate")
    public RestResult<ActivityJoinDataVo> activityJoin(@Valid ActivityDataParam activityDataParam){
        return RestResult.success();
    }

    @ApiOperation("获取活动企业排行")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/activityOrganization")
    public RestResult<List<ActivityOrganizationDataVo>> activityOrganization(@Valid ActivityDataParam activityDataParam){
        return RestResult.success();
    }

    @ApiOperation("获取日活动参与量与扫码量")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/dayActivityJoin")
    public RestResult<List<DayActivityJoinQuantityVo>> dayActivityJoin(@Valid ActivityDataParam activityDataParam){
        return RestResult.success();
    }

    @ApiOperation("扫码活跃会员")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/scanMemberRate")
    public RestResult<ScanCodeMemberDataVo> scanMemberRate(@Valid ActivityDataParam activityDataParam){
        return RestResult.success();
    }

    @ApiOperation("会员画像")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/memberPortrait")
    public RestResult<MemberPortraitDataVo> memberPortrait(@Valid ActivityDataParam activityDataParam) {
        return RestResult.success();
    }


    @ApiOperation("会员地域分布")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/memberRegion")
    public RestResult<List<MemberRegionDataVo>> memberRegion(@Valid ActivityDataParam activityDataParam) {
        return RestResult.success();
    }

}
