package com.jgw.supercodeplatform.marketing.controller.platform;


import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.marketing.check.activity.platform.PlatformActivityCheck;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.*;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.platform.JoinResultPage;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityDisable;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityUpdate;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingPlatformOrganizationService;
import com.jgw.supercodeplatform.marketing.service.activity.PlatformActivityService;
import com.jgw.supercodeplatform.marketing.vo.platform.JoinPrizeRecordVo;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformActivityVo;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformOrganizationDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marketing/activity/platform/set")
@Api(tags = "全平台活动")
public class PlatformActivityController {

    private final static String DATE_FORMATE = "yyyy-MM-dd";

    @Value("${rest.user.url}")
    private String userUrl;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private PlatformActivityCheck platformActivityCheck;
    @Autowired
    private PlatformActivityService platformActivityService;

    @ApiOperation("添加活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/add")
    public RestResult<?> add(@RequestBody @Valid PlatformActivityAdd platformActivityAdd) {
        platformActivityCheck.platformActivityAddCheck(platformActivityAdd);
        platformActivityService.createOrUpdatePlatformActivitySet(platformActivityAdd);
        return RestResult.success();
    }

    @ApiOperation("根据ID活动活动信息")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/get")
    public RestResult<PlatformActivityUpdate> getActivity(@RequestParam Long id) throws ParseException {
        PlatformActivityUpdate platformActivityUpdate = platformActivityService.getActivityBySetId(id);
        return RestResult.successWithData(platformActivityUpdate);
    }

    @ApiOperation("编辑修改活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/update")
    public RestResult<?> update(@RequestBody @Valid PlatformActivityUpdate platformActivityUpdate) {
        platformActivityCheck.platformActivityAddCheck(platformActivityUpdate);
        platformActivityService.createOrUpdatePlatformActivitySet(platformActivityUpdate);
        return RestResult.success();
    }

    @ApiOperation("查询活动列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/page")
    public RestResult<PageResults<List<PlatformActivityVo>>> page(@Valid DaoSearchWithOrganizationIdParam daoSearch) throws Exception {
        daoSearch.setOrganizationId(commonUtil.getOrganizationId());
        PageResults<List<PlatformActivityVo>> platformActivityVoResult = platformActivityService.listSearchViewLike(daoSearch);
        platformActivityVoResult.getList().stream().forEach(platformActivityVo -> {
            Date startDate = platformActivityVo.getActivityStartDate();
            Date endDate = platformActivityVo.getActivityEndDate();
            platformActivityVo.setActivityDate(DateFormatUtils.format(startDate,DATE_FORMATE) + " ~ " + DateFormatUtils.format(endDate,DATE_FORMATE));
        });
        return RestResult.successWithData(platformActivityVoResult);
    }


    @ApiOperation("平台组织信息分页列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/platformOrganizationPage")
    public RestResult<PageResults<List<PlatformOrganizationDataVo>>> platformOrganization(@Valid DaoSearch daoSearch){
        PageResults<List<PlatformOrganizationDataVo>> pageResults = new PageResults<>();
        Map<String, Object> params = new HashMap<>();
        params.put("current", daoSearch.getCurrent());
        params.put("pageSize", daoSearch.getPageSize());
        params.put("search", daoSearch.getSearch());
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("super-token", commonUtil.getSuperToken());
        ResponseEntity<String> entity = restTemplateUtil.getRequestAndReturnJosn(userUrl+ WechatConstants.ORG_LIST_PLATFORM, params, headerMap);
        if (entity != null && entity.getStatusCodeValue() == HttpStatus.SC_OK){
            String result = entity.getBody();
            if (StringUtils.isNotBlank(result) && JSON.parseObject(result).getIntValue("state") == HttpStatus.SC_OK) {
                PageResults<List<Object>> pageMapResults = JSON.parseObject(result).getObject("results", PageResults.class);
                List<PlatformOrganizationDataVo> resList = pageMapResults.getList().stream().map(obj -> {
                    Map sorMap = (Map)obj;
                    String organizationId = (String) sorMap.get("organizationId");
                    String organizationName = (String)sorMap.get("organizationFullName");
                    PlatformOrganizationDataVo platformOrganizationDataVo = new PlatformOrganizationDataVo();
                    platformOrganizationDataVo.setOrganizationId(organizationId);
                    platformOrganizationDataVo.setOrganizationFullName(organizationName);
                    return platformOrganizationDataVo;
                }).collect(Collectors.toList());
                pageResults.setList(resList);
                pageResults.setPagination(pageMapResults.getPagination());
            }
        }
        return RestResult.successWithData(pageResults);
    }


    @ApiOperation("启用或停止活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @PostMapping("/disOrEnable")
    public RestResult<?> disOrEnable(@RequestBody @Valid PlatformActivityDisable platformActivityDisable){
        return RestResult.success();
    }

    @ApiOperation("参与记录")
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true)
    @GetMapping("/joinResultPage")
    public RestResult<PageResults<List<JoinPrizeRecordVo>>> joinResultPage(@Valid JoinResultPage joinResultPage){
        return RestResult.success();
    }

}
