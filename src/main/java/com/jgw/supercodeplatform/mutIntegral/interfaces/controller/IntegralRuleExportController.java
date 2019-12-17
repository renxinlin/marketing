package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.mutIntegral.application.service.IntegralRuleApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.*;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonExportVo;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Controller
@RequestMapping("/marketing/mutiIntegral/integralRule/export")
@Api(value = "积分规则导出", tags = "积分规则导出")
public class IntegralRuleExportController extends SalerCommonController{
    private  static  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private IntegralRuleApplication application;

    // 通用表头设置
    static Map<String, String> fieldMap = new HashMap<>();
    static {
        fieldMap.put("type","类型");
        fieldMap.put("money","会员赠送红包");
        fieldMap.put("integral","会员赠送积分");
        fieldMap.put("expire","积分有效期");
        fieldMap.put("limit","每人获取上限");
    }


    @GetMapping("/common")
    @ApiOperation(value = "积分通用规则列表项导出", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public void list(HttpServletResponse response) throws ExcelException {
        // execl导出无须遵守设计模型
        List<IntegralRuleRewardCommonVo> integralRuleRewardCommonVos = application.commonIntegralRewardList();
        if(CollectionUtils.isEmpty(integralRuleRewardCommonVos)){
            ExcelUtils.listToExcel(new ArrayList<>(),fieldMap,"积分通用规则",response);
        }
        List<IntegralRuleRewardCommonExportVo> voList = new ArrayList<>(integralRuleRewardCommonVos.size());
        integralRuleRewardCommonVos.forEach(integralRuleRewardCommonVo -> {
            IntegralRuleRewardCommonExportVo vo = new IntegralRuleRewardCommonExportVo();

            // 类型
            setType(integralRuleRewardCommonVo, vo);
            // 金额
            setMoney(integralRuleRewardCommonVo, vo);
            // 积分
            setIntegral(integralRuleRewardCommonVo, vo);
            // 有效期
            setExpire(integralRuleRewardCommonVo, vo);
            // 积分上限
            setLimit(integralRuleRewardCommonVo, vo);
            voList.add(vo);

        });
        ExcelUtils.listToExcel(new ArrayList<>(),fieldMap,"积分通用规则",response);

    }

    private void setLimit(IntegralRuleRewardCommonVo integralRuleRewardCommonVo, IntegralRuleRewardCommonExportVo vo) {
        if(integralRuleRewardCommonVo.getIntegralLimitType() == IntegralLimitTypeConstants.limit){
            vo.setLimit(integralRuleRewardCommonVo.getIntegralLimit()+"");
        }

        if(integralRuleRewardCommonVo.getIntegralLimitType() == IntegralLimitTypeConstants.noLimit){
            vo.setLimit("无上限");

        }
    }

    private void setExpire(IntegralRuleRewardCommonVo integralRuleRewardCommonVo, IntegralRuleRewardCommonExportVo vo) {
        if(integralRuleRewardCommonVo.getExpiredType() == ExpiredTypeConstants.utilTime){
            vo.setExpire(sdf.format(integralRuleRewardCommonVo.getExpiredDate()));
        }
        if(integralRuleRewardCommonVo.getExpiredType() == ExpiredTypeConstants.forerver){
            vo.setExpire("永久有效");
        }
    }

    private void setIntegral(IntegralRuleRewardCommonVo integralRuleRewardCommonVo, IntegralRuleRewardCommonExportVo vo) {
        if(integralRuleRewardCommonVo.getChooseedIntegral() == ChooseedIntegralConsants.choose){
            vo.setIntegral(integralRuleRewardCommonVo.getSendIntegral()+"");
        }
        if(integralRuleRewardCommonVo.getChooseedIntegral() == ChooseedIntegralConsants.noChoose){
            vo.setIntegral("0");
        }
    }

    private void setMoney(IntegralRuleRewardCommonVo integralRuleRewardCommonVo, IntegralRuleRewardCommonExportVo vo) {
        if(integralRuleRewardCommonVo.getRewardMoneyType() == RewardMonryTypeConstants.fixed){
            vo.setMoney(integralRuleRewardCommonVo.getFixedMoney()+"元");
        }

        if(integralRuleRewardCommonVo.getRewardMoneyType() == RewardMonryTypeConstants.random){
            vo.setMoney(integralRuleRewardCommonVo.getLowerRandomMoney()+"元~"
                    +integralRuleRewardCommonVo.getHighRandomMoney()+"元");
        }
        if(integralRuleRewardCommonVo.getRewardMoneyType() == RewardMonryTypeConstants.nosend){
            vo.setMoney("0元");
        }
    }

    public static void main(String[] args) {
        Integer i = new Integer(1);
        Integer i1 = new Integer(1);
        System.out.println(i.equals(i1));
    }

    private void setType(IntegralRuleRewardCommonVo integralRuleRewardCommonVo, IntegralRuleRewardCommonExportVo vo) {
        Integer integralRuleType = integralRuleRewardCommonVo.getIntegralRuleType();
        if(integralRuleType.equals(IntegralRuleTypeConstant.newRegister)){
            vo.setType(IntegralRuleTypeConstant.newRegisterStr);
        }
        if(integralRuleType.equals(IntegralRuleTypeConstant.birthDaySend)){
            vo.setType(IntegralRuleTypeConstant.birthDaySendStr);
        }
        if(integralRuleType.equals(IntegralRuleTypeConstant.firstSend)){
            vo.setType(IntegralRuleTypeConstant.firstSendStr);
        }
        if(integralRuleType.equals(IntegralRuleTypeConstant.rewardNew)){
            vo.setType(IntegralRuleTypeConstant.rewardNewStr);
        }
        if(integralRuleType.equals(IntegralRuleTypeConstant.rewardOld)){
            vo.setType(IntegralRuleTypeConstant.rewardOldStr);

        }
    }


}

