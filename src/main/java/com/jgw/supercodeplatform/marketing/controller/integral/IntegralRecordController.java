package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.JwtUser;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/integral/record")
@Api(tags = "积分记录")
public class IntegralRecordController extends CommonUtil {
    @Autowired
    private  IntegralRecordService integralRecordService;

    /**
     *  积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "积分记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> list(@RequestBody IntegralRecord integralRecord) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRecord>>>();
        String organizationId = getOrganizationId();
        // 获取组织id
        if(StringUtils.isBlank(organizationId)){
            restResult.setState(500);
            restResult.setMsg("组织id获取失败");
            restResult.setResults(null);
            return restResult;
        }
        integralRecord.setOrganizationId(organizationId);
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(integralRecord);
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(pages);
        return restResult;
    }



    /**
     *  会员积分记录列表
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memberList",method = RequestMethod.POST)
    @ApiOperation(value = "会员积分记录列表", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),
            @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> memberList(@RequestBody IntegralRecord integralRecord, @ApiIgnore JwtUser jwtuser) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRecord>>>();
        if(integralRecord.getIntegralType() != null && integralRecord.getIntegralType() > 1 && integralRecord.getIntegralType() < 0){
             throw new SuperCodeException("积分记录类型错误",500);
        }
        Long memberId = jwtuser.getMemberId();
        integralRecord.setMemberId(memberId);
        integralRecord.setOrganizationId(null);
        // 获取积分记录分页结果
        AbstractPageService.PageResults<List<IntegralRecord>> pages = integralRecordService.listSearchViewLike(integralRecord);
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(pages);
        return restResult;
    }
}
