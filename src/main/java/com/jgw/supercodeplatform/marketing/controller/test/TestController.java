package com.jgw.supercodeplatform.marketing.controller.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "测试接口")
public class TestController extends CommonUtil {

	@Value("${rest.user.url}")
	private String restUserUrl;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @RequestMapping(value = "/tt")
    @ApiOperation(value = "测试", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "手机号", required = true)
    public void dd(HttpServletResponse response) throws Exception {
    }

	



}
