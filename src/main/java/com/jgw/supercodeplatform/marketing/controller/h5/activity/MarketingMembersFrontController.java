package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/marketing/front/members")
@Api(tags = "h5用户注册登录信息完善点击领奖")
public class MarketingMembersFrontController extends CommonUtil {
	private static Logger logger = LoggerFactory.getLogger(MarketingMembersFrontController.class);
	@Autowired
	private MarketingMembersService marketingMembersService;

	@Autowired
	private RestTemplateUtil restTemplateUtil;


	/**
	 * 用户服务地址
	 */
	@Value("${rest.user.url}")
	private String USER_SERVICE;


	@RequestMapping(value = "/login",method = RequestMethod.GET)
    @ApiOperation(value = "h5登录", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "手机号",name="mobile"),
    		@ApiImplicitParam(paramType="query",value = "扫产品标签码时才需要传，由前端保存",name="wxstate"),
    		@ApiImplicitParam(paramType="query",value = "用户中心登录时才需要传",name="openid"),
    		@ApiImplicitParam(paramType="query",value = "用户中心登录时才需要传",name="organizationId"),
    		@ApiImplicitParam(paramType="query",value = "手机验证码",name="verificationCode")
    		})
    public RestResult<H5LoginVO> login(@RequestParam String mobile,@RequestParam(required=false) String wxstate,@RequestParam String verificationCode,@RequestParam(required=false) String openid,@RequestParam(required=false) String organizationId,HttpServletResponse response) throws Exception {
        return marketingMembersService.login(mobile,wxstate,verificationCode,openid,organizationId,response);
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
    public RestResult<String> lottery(String wxstate) throws Exception {
        return marketingMembersService.lottery(wxstate);
    }
    
    @RequestMapping(value = "/userInfo",method = RequestMethod.GET)
    @ApiOperation(value = "根据主键获取用户信息", notes = "")
    @ApiImplicitParams(value= {
    		@ApiImplicitParam(paramType="query",value = "用户主键",name="id"),
    		@ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息")
    })
    public RestResult<H5LoginVO> userInfo(@RequestParam Long id,@ApiIgnore H5LoginVO h5LoginVO) throws Exception {
    	RestResult<H5LoginVO> restResult=new RestResult<H5LoginVO>();
    	try {
    		Long memberId=h5LoginVO.getMemberId();
			if (id.intValue()!=memberId.intValue()) {
				restResult.setState(500);
				restResult.setMsg("登录用户与参数id不符合不是同一个用户");
				return restResult;
			}
			
			MarketingMembers marketingMembers=marketingMembersService.selectById(id);
			if (null==marketingMembers) {
				restResult.setState(500);
				restResult.setMsg("根据id="+id+"无法查找到用户");
				return restResult;
			}
			
			H5LoginVO hVo=new H5LoginVO();
			hVo.setMemberId(id);
			String userName=marketingMembers.getUserName();
			hVo.setMemberName(userName==null?marketingMembers.getWxName():userName);
			hVo.setMobile(marketingMembers.getMobile());
			hVo.setRegistered(1);

			// todo 非登录状态下基于组织ID获取组织名称
			// TODO 此方式是否合适？如果合适，需要开接口
			String organizationId = marketingMembers.getOrganizationId();
			Map<String, Object> organizationIdMap = new HashMap<>();
			organizationIdMap.put("organizationId",organizationId);
			try {
				ResponseEntity<String> requestAndReturnJosn = restTemplateUtil.getRequestAndReturnJosn(USER_SERVICE+ CommonConstants.ORGANIZATION_NAME, organizationIdMap, null);
				if(JSONObject.parseObject(requestAndReturnJosn.getBody()).getInteger("state") == 200){
					hVo.setOrganizationName(JSONObject.parseObject(requestAndReturnJosn.getBody()).getJSONObject("results").getString("organizationName"));
				}
			} catch (SuperCodeException e) {
				e.printStackTrace();
				logger.error("[基于组织id{}获取组织信息失败]",organizationId);
				// xx企业欢迎你回来！
				hVo.setOrganizationName("");
			}


			hVo.setHaveIntegral(marketingMembers.getHaveIntegral());
			hVo.setWechatHeadImgUrl(marketingMembers.getWechatHeadImgUrl());
			restResult.setState(200);
			restResult.setResults(hVo);
		} catch (Exception e) {
			restResult.setState(500);
			restResult.setMsg("请求用户详情报错："+e.getMessage());
		}
        return restResult;
    }
}
