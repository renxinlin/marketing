package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.soap.Addressing;
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
}
