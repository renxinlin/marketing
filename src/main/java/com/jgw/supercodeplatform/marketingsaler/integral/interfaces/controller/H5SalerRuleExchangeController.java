package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.config.aop.CheckRole;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.Role;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.UserService;
import com.jgw.supercodeplatform.marketingsaler.integral.infrastructure.ErrorMsg;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.H5SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.CodeManagerService;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.dto.ProductInfoByCodeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.H5SalerRuleExchangeService;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.H5SalerRuleRewardService;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.SalerRecordService;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.H5SalerRuleExchangeTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.SaleRuleRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * facede
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
    private UserService userService;

    @Autowired
    private CodeManagerService codeManagerService;

    @CheckRole(role = Role.salerRole)
    @PostMapping("/exchange")
    @ApiOperation(value = "兑换", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult exchange(@Valid @RequestBody H5SalerRuleExchangeDto salerRuleExchangeDto,@ApiIgnore H5LoginVO user) throws CommonException {
        return service.exchange(salerRuleExchangeDto,user);


    }



    @GetMapping("/list")
    @ApiOperation(value = "兑换分页列表", notes = "")
    public RestResult<AbstractPageService.PageResults<List<SalerRuleExchange>>> list(DaoSearchWithOrganizationId daoSearch) throws CommonException {
        daoSearch.setCurrent(1);
        daoSearch.setPageSize(Integer.MAX_VALUE);
        return success(service.h5PageList(daoSearch));
    }




    @CheckRole(role = Role.salerRole)
    @PostMapping("/reward")
    @ApiOperation(value = "积分领取", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult reward(@Valid @RequestBody OutCodeInfoDto codeInfo, @ApiIgnore H5LoginVO user)   {
        // TODO 拉到application层
        ProductInfoByCodeDto productByCode = codeManagerService.getProductByCode(codeInfo);
        Asserts.check(productByCode!=null , ErrorMsg.no_setting_integral);
        Integer integral = rewardService.getIntegral(productByCode.getMarketingCode().getOuterCodeId(), productByCode.getMarketingCode().getCodeTypeId(),H5SalerRuleExchangeTransfer.getRewardValueObject(productByCode), user);
        return success("恭喜获得"+integral+"积分");
    }


    @CheckRole(role = Role.salerRole)
    @GetMapping("/record")
    @ApiOperation(value = "积分记录 type 1 奖励 2 消耗 3表示所有", notes = "")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult record(int type,@ApiIgnore  H5LoginVO user)   {
        User userPojo = userService.exists(user);
        int haveIntegral = 0;
        if( userPojo!= null){
            haveIntegral =userPojo.getHaveIntegral();
        }
        List<SalerRecord> salerRecords = recordService.seletLastThreeMonth(type, user);
        SaleRuleRecordVo saleRuleRecordVo = new SaleRuleRecordVo();
        saleRuleRecordVo.setHaveIntegral(haveIntegral);
        saleRuleRecordVo.setSalerRecord(salerRecords);
        return success(saleRuleRecordVo);
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

