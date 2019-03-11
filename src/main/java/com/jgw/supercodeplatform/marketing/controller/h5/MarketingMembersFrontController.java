package com.jgw.supercodeplatform.marketing.controller.h5;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/members")
@Api(tags = "会员管理")
public class MarketingMembersFrontController extends CommonUtil {

    @Autowired
    private MarketingMembersService marketingMembersService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "h5登录", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "手机号",name="mobile"),
    		@ApiImplicitParam(paramType="query",value = "活动设置id",name="wxstate"),
    		@ApiImplicitParam(paramType="query",value = "手机验证码",name="verificationCode")
    		})
    public RestResult<H5LoginVO> login(@RequestParam String mobile,@RequestParam String wxstate,@RequestParam String verificationCode) throws Exception {
        return marketingMembersService.login(mobile,wxstate,verificationCode);
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ApiOperation(value = "招募会员（注册）", notes = "")
    public RestResult<String> addMember(@Valid@RequestBody MarketingMembersAddParam marketingMembersAddParam) throws Exception {
        checkPhoneFormat(marketingMembersAddParam.getMobile());
        marketingMembersService.addMember(marketingMembersAddParam);
        return new RestResult<String>(200, "success",null );
    }
    
    @RequestMapping(value = "/infoImprove",method = RequestMethod.POST)
    @ApiOperation(value = "h5信息完善", notes = "")
    public RestResult<String> register(@Valid@RequestBody MarketingMembersUpdateParam marketingMembersUpdateParam) throws Exception {
    	marketingMembersService.updateMembers(marketingMembersUpdateParam);
        return new RestResult<String>(200, "成功", null);
    }
    
    @RequestMapping(value = "/lottery",method = RequestMethod.POST)
    @ApiOperation(value = "用户点击领奖方法", notes = "")
    public RestResult<String> lottery(String wxstate,String mobile) throws Exception {
        return marketingMembersService.lottery(wxstate,mobile);
    }
}
