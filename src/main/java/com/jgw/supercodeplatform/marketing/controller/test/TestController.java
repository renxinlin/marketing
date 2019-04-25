package com.jgw.supercodeplatform.marketing.controller.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "测试接口")
@RequestMapping(value = "/marketing/test",method=RequestMethod.POST)
public class TestController extends CommonUtil {

	@Value("${rest.user.url}")
	private String restUserUrl;

	@Value("${cookie.domain}")
	private String domain;
	
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @RequestMapping(value = "/tt",method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "测试", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "codeTypeId", paramType = "query", defaultValue = "12", value = "每页记录数,不传默认10条,非必需"),
        @ApiImplicitParam(name = "outCodeId", paramType = "query", defaultValue = "676049377640368083593", value = "当前页,不传默认第一页,非必需")
    })
    public void dd(String codeTypeId,String outCodeId) throws Exception {
    	
		String url="http://PLATFORM-MS-CODETEST/code-application/oc/v1/list";
		Map<String, String> headerMap=new HashMap<String, String>();
		headerMap.put("token", getCodePlatformToken());
		JSONObject obj=new JSONObject();
		obj.put("codeTypeId", codeTypeId);
		obj.put("outCodeId", outCodeId);
		JSONArray arr=new JSONArray();
		arr.add(obj);
		String json=arr.toJSONString();
		ResponseEntity<String> responseEntity=restTemplateUtil.postJsonDataAndReturnJosn(url, json, headerMap);
		String body=responseEntity.getBody();
		System.out.println(body);
    }

    @RequestMapping(value = "/cookie",method=RequestMethod.GET)
    @ApiOperation(value = "cookie测试", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "url", paramType = "query", defaultValue = "12", value = "每页记录数,不传默认10条,非必需"),
    })
    public String cookie(String url,HttpServletResponse resonse) throws Exception {
    	Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,"哈哈");
		// jwt有效期为2小时，保持一致
		jwtTokenCookie.setMaxAge(60*60*2);
		// 待补充： 其他参数基于传递状况
		// jwtTokenCookie.setPath();
		jwtTokenCookie.setPath("/");
		jwtTokenCookie.setDomain(domain);
		response.addCookie(jwtTokenCookie);
		
		return "redirect:"+url;
    }



}
