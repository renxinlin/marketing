package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 会员积分记录列表
 *
 */
@RestController
@RequestMapping("/marketing/h5/record")
@Api(tags = "H5会员积分记录列表")
public class H5IntegralRecordController   {
    @Autowired
    private IntegralRecordService integralRecordService;
    /**
     *  会员积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memberList",method = RequestMethod.GET)
    @ApiOperation(value = "会员积分记录列表", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "积分类型|null所有,0奖励,1消耗",name="integralType"),
            @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> memberList(@RequestParam("integralType") String integralType, @ApiIgnore H5LoginVO jwtuser) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRecord>>>();

        Integer integralTypeInt = null;

        if("null".equals(integralType)){
            // 前端格式
        }else if ("0".equals(integralType)){
            integralTypeInt = Integer.parseInt(integralType);
        }else if ("1".equals(integralType)){
            integralTypeInt = Integer.parseInt(integralType);
        }else{
            throw new SuperCodeException("积分记录类型错误",500);
        }
        IntegralRecord integralRecord = new  IntegralRecord();
        Long memberId = jwtuser.getMemberId();
        integralRecord.setMemberId(memberId);
        integralRecord.setOrganizationId(null);
        integralRecord.setIntegralType(integralTypeInt);
        integralRecord.setMemberType(MemberTypeEnums.VIP.getType());
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(integralRecord);
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(pages);
        return restResult;
    }
}
