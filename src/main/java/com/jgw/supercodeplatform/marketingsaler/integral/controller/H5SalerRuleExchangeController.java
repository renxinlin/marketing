package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.alipay.api.domain.CodeInfo;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dto.integral.Product;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.Role;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.H5SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.outservice.group.CodeManagerService;
import com.jgw.supercodeplatform.marketingsaler.integral.outservice.group.dto.ProductInfoByCodeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.service.H5SalerRuleExchangeService;
import com.jgw.supercodeplatform.marketingsaler.integral.service.H5SalerRuleRewardService;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRecordService;
import com.jgw.supercodeplatform.marketingsaler.integral.transfer.H5SalerRuleExchangeTransfer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("marketing/h5/salerRuleExchange")
@Api(value = "", tags = "h5销售员积分兑换")
public class H5SalerRuleExchangeController extends SalerCommonController {

    @Autowired
    private H5SalerRuleExchangeService service;
    @Autowired
    private H5SalerRuleRewardService rewardService;
    @Autowired
    private SalerRecordService recordService;

    @Autowired
    private CodeManagerService codeManagerService;

    @CheckRole(role = Role.salerRole)
    @PostMapping("/exchange")
    @ApiOperation(value = "兑换", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult exchange(@Valid @RequestBody H5SalerRuleExchangeDto salerRuleExchangeDto, H5LoginVO user) throws CommonException {
        service.exchange(salerRuleExchangeDto,user);
        return success();
    }



    @PostMapping("/list")
    @ApiOperation(value = "兑换分页列表", notes = "")
    public RestResult<AbstractPageService.PageResults<List<SalerRuleExchange>>> list(DaoSearchWithOrganizationId daoSearch) throws CommonException {
        return success(service.h5PageList(daoSearch));
    }




    @CheckRole(role = Role.salerRole)
    @PostMapping("/reward")
    @ApiOperation(value = "积分领取", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult reward(@Valid @RequestBody OutCodeInfoDto codeInfo, H5LoginVO user) throws Exception {
        ProductInfoByCodeDto productByCode = codeManagerService.getProductByCode(codeInfo);
        Asserts.check(productByCode!=null ,"码关联信息查询数据失败...");
        rewardService.getIntegral(codeInfo.getCodeTypeId(),H5SalerRuleExchangeTransfer.getRewardValueObject(productByCode),user);
        return success();
    }


    @CheckRole(role = Role.salerRole)
    @PostMapping("/record")
    @ApiOperation(value = "积分记录 type 1 奖励 2 消耗 不传表示所有", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult record(int type,H5LoginVO user) throws CommonException {
        return success(recordService.seletLastThreeMonth(type,user));
    }




    @GetMapping(value = "/getIntegralStatusByH5")
    @ApiOperation(value = "H5查看开启页面领积分按钮", notes = "")
    public RestResult<String> getIntegralStatusByH5(@RequestParam String organizationId) throws Exception {
        String status = redisUtil.get(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON + organizationId);
        if( StringUtils.isEmpty(status)){
            // 默认状态
            return success(OpenIntegralStatus.open);
        }else {
            return success(status);

        }
    }



}

