package com.jgw.supercodeplatform.marketing.controller.user;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marketing/members")
@Api(tags = "会员管理")
public class MarketingMembersController extends CommonUtil {

    @Autowired
    private MarketingMembersService marketingMembersService;


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "会员列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<Map<String, Object>>>> memberList(MarketingMembersListParam param) throws Exception {
        return new RestResult(200, "success", marketingMembersService.listSearchViewLike(param));
    }


    @RequestMapping(value = "/getMenberById",method = RequestMethod.GET)
    @ApiOperation(value = "根据会员id获取会员详细信息", notes = "返回会员详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
    })
    public RestResult<String> getUserMember(Long id) throws Exception {
        return new RestResult(200, "success",marketingMembersService.getMemberById(id));
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "编辑会员", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> updateMember(@Valid @RequestBody MarketingMembersUpdateParam marketingMembersUpdateParam) throws Exception {
        marketingMembersService.updateMembers(marketingMembersUpdateParam);
        return new RestResult(200, "success",null );
    }




    @RequestMapping(value = "/enable/status", method = RequestMethod.GET)
    @ApiOperation(value = "启用会员", notes = "是否启用成功")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
        @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
})
    public RestResult<String> enableStatus(Long id) throws Exception {
    	if (null==id) {
			throw new SuperCodeException("id不能为空", 500);
		}
        marketingMembersService.updateMembersStatus(id,1);
        return new RestResult(200, "success", null);
    }


    @RequestMapping(value = "/disable/status", method = RequestMethod.GET)
    @ApiOperation(value = "禁用会员", notes = "是否禁用成功")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
        @ApiImplicitParam(name = "id", paramType = "query", defaultValue = "1", value = "用户Id,必需", required = true),
})
    public RestResult<String> disableStatus(Long id ) throws Exception {
    	if (null==id) {
			throw new SuperCodeException("id不能为空", 500);
		}
        marketingMembersService.updateMembersStatus(id,0);
        return new RestResult(200, "success", null);
    }



}
