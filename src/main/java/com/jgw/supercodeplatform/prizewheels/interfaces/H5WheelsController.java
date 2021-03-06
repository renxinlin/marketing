package com.jgw.supercodeplatform.prizewheels.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.Role;
import com.jgw.supercodeplatform.prizewheels.application.service.GetWheelsRewardApplication;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.H5RewardInfo;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRewardCdk;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsOrderDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("marketing/front/prizeWheels")
@Api(value = "", tags = "大转盘H5")
public class H5WheelsController extends SalerCommonController {

    @Autowired
    private GetWheelsRewardApplication application;

    @CheckRole(role = Role.vip)
    @ResponseBody
    @PostMapping("/reward")
    @ApiOperation(value = "H5领奖", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<H5RewardInfo> reward(@Valid @RequestBody PrizeWheelsRewardDto prizeWheelsRewardDto, @ApiIgnore H5LoginVO user) throws CommonException {
        // 暂时只有虚拟wu
        return success(application.reward(prizeWheelsRewardDto, user));
    }


    @GetMapping("/redrict")
    @ApiOperation(value = "大转盘码管理重定向", notes = "")
    public String reward(String productId, String productBatchId, String outerCodeId, String codeTypeId, String organizationId, String uuid) {
        // 扫码重定向到前端 基于产品批次找到活动
        StringBuffer urlparame = new StringBuffer("redirect:");
        urlparame.append(CallBackConstant.TO_WEB_URL)
                .append("?productBatchId=").append(productBatchId)
                .append("&outerCodeId=").append(outerCodeId)
                .append("&codeTypeId=").append(codeTypeId)
                .append("&template=10") // 业务标志字段
                .append("&organizationId=").append(organizationId)
                .append("&productId=").append(productId)
                .append("&uuid=").append(uuid);
        String url = urlparame.toString();
        log.info("==> 大转盘扫码重定向到前端{} ", url);
        return url;
    }


    @ResponseBody
    @GetMapping("/detail")
    @ApiOperation(value = "H5大转盘详情", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult detail(@RequestParam String productBatchId, String productId) {
        WheelsDetailsVo wheelsDetailsVO = application.detail(productBatchId,productId);
        return success(wheelsDetailsVO);
    }

    @ResponseBody
    @PostMapping("/setAdddress")
    @ApiOperation(value = "实物领奖地址", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult setAdddress(@RequestBody @Valid PrizeWheelsOrderDto prizeWheelsOrderDto,@ApiIgnore H5LoginVO user) {
        application.setAdddress(prizeWheelsOrderDto, user);
        return success( );
    }

    @ResponseBody
    @PostMapping("/preview/add")
    @ApiOperation(value = "预览add", notes = "")
    public RestResult add( @RequestBody WheelsDto wheelsDto)   {
        String uuid = UUID.randomUUID().toString();
        // 1天
        redisUtil.set(CallBackConstant.PRIZE_WHEELS_PREIEW + uuid, JSONObject.toJSONString(wheelsDto),60 * 60 *24L);
        return success(uuid);
    }

    @ResponseBody
    @GetMapping("/preview")
    @ApiOperation(value = "预览", notes = "")
    public RestResult preview( String uuid)   {
        String wheelsDtoStr = redisUtil.get(CallBackConstant.PRIZE_WHEELS_PREIEW + uuid);
        Asserts.check(!StringUtils.isEmpty(wheelsDtoStr),"重新点击预览生成!");
        WheelsDto wheelsDto = JSONObject.parseObject(wheelsDtoStr, WheelsDto.class);
        return success(wheelsDto);
    }

}
