package com.jgw.supercodeplatform.marketing.controller.platform;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.pojo.platform.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/marketing/activity/platform/set")
@Api(tags = "全平台活动")
public class PlatformActivityController {

    @ApiOperation("添加活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/add")
    public RestResult<?> add(@RequestBody @Valid PlatformActivityAdd platformActivityAdd) {
        return RestResult.success();
    }

    @ApiOperation("根据ID活动活动信息")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/get")
    public RestResult<PlatformActivityUpdate> getActivity(@RequestParam Long id) {
        return RestResult.success();
    }

    @ApiOperation("编辑修改活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/update")
    public RestResult<?> update(@RequestBody @Valid PlatformActivityUpdate platformActivityUpdate) {
        return RestResult.success();
    }

    @ApiOperation("查询活动列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/page")
    public RestResult<PageResults<List<PlatformActivityVo>>> page(@RequestBody @Valid DaoSearch daoSearch){
        return RestResult.success();
    }

    @ApiOperation("启用或停止活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/disOrEnable")
    public RestResult<?> disOrEnable(@RequestBody @Valid PlatformActivityDisable platformActivityDisable){
        return RestResult.success();
    }

    @ApiOperation("参与记录")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/joinResultPage")
    public RestResult<PageResults<List<JoinPrizeRecordVo>>> joinResultPage(@RequestBody @Valid JoinResultPage joinResultPage){
        return RestResult.success();
    }

    @ApiOperation("平台组织信息分页列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/platformOrganizationPage")
    public RestResult<PageResults<List<PlatformOrganizationDataVo>>> platformOrganization(@RequestBody @Valid DaoSearch daoSearch){
        return RestResult.success();
    }

}
