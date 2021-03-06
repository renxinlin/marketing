package com.jgw.supercodeplatform.marketing.controller.platform;


import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.platform.ActivityDataParam;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import com.jgw.supercodeplatform.marketing.service.activity.PlatformStatisticsService;
import com.jgw.supercodeplatform.marketing.vo.platform.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marketing/activity/platform/data")
@Api(tags = "全平台活动数据")
public class PlatformActivityDataController {

    @Autowired
    private PlatformStatisticsService platformStatisticsService;

    @ApiOperation("获取扫码率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/scanCodeRate")
    public RestResult<List<PieChartVo>> scanCodeRate(@Valid ActivityDataParam activityDataParam) {
        List<PieChartVo> scanCodeDataVoList = platformStatisticsService.scanCodeRate(activityDataParam);
        return RestResult.successWithData(scanCodeDataVoList);
    }

    @ApiOperation("获取中奖率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/winningPrizeRate")
    public RestResult<List<PieChartVo>> winningPrize(@Valid ActivityDataParam activityDataParam){
        List<PieChartVo> scanCodeDataVoList = platformStatisticsService.winningPrize(activityDataParam);
        return RestResult.successWithData(scanCodeDataVoList);
    }

    @ApiOperation("获取活动参与率")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/activityJoinRate")
    public RestResult<List<PieChartVo>> activityJoin(@Valid ActivityDataParam activityDataParam){
        List<PieChartVo> scanCodeDataVoList = platformStatisticsService.activityJoin(activityDataParam);
        return RestResult.successWithData(scanCodeDataVoList);
    }

    @ApiOperation("获取活动企业排行")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/activityOrganization")
    public RestResult<List<ActivityOrganizationDataVo>> activityOrganization(@Valid ActivityDataParam activityDataParam){
        List<ActivityOrganizationDataVo> dataVos = platformStatisticsService.activityOrganization(activityDataParam);
        return RestResult.successWithData(dataVos);
    }

    @ApiOperation("获取日活动参与量")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/dayActivityJoin")
    public RestResult<DayActivityJoinQuantityVo> dayActivityJoin(@Valid ActivityDataParam activityDataParam){
        DayActivityJoinQuantityVo dayActivityJoinQuantityVo = platformStatisticsService.statiticsDayActivity(activityDataParam, 1);
        return RestResult.successWithData(dayActivityJoinQuantityVo);
    }

    @ApiOperation("获取日活动扫码量")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/dayActivityScan")
    public RestResult<DayActivityJoinQuantityVo> dayActivityScan(@Valid ActivityDataParam activityDataParam){
        DayActivityJoinQuantityVo dayActivityJoinQuantityVo = platformStatisticsService.statiticsDayActivity(activityDataParam, null);
        return RestResult.successWithData(dayActivityJoinQuantityVo);
    }

    @ApiOperation("扫码活跃会员")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/scanMemberRate")
    public RestResult<List<PieChartVo>> scanMemberRate(@Valid ActivityDataParam activityDataParam){
        List<PieChartVo> scanMemberList = platformStatisticsService.scanCodeActMember(activityDataParam);
        return RestResult.successWithData(scanMemberList);
    }

    @ApiOperation("会员画像")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/memberPortrait")
    public RestResult<MemberPortraitDataVo> memberPortrait(@Valid ActivityDataParam activityDataParam) {
        MemberPortraitDataVo memberPortraitDataVo = platformStatisticsService.memberPortrait(activityDataParam);
        return RestResult.successWithData(memberPortraitDataVo);
    }


    @ApiOperation("会员地域分布")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/memberRegion")
    public RestResult<MemberAreaVo> memberRegion(@Valid ActivityDataParam activityDataParam) {
        //传给前端时注意按照从大到小的顺序
        MemberAreaVo memberAreaVo = platformStatisticsService.memberRegion(activityDataParam);
        return RestResult.successWithData(memberAreaVo);
    }

}
