package com.jgw.supercodeplatform.marketing.controller.common;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(tags = "公共接口")
public class WriteAuthFileController {
	
    @RequestMapping(value = "/MP_verify_dSYneurbj349sTas.txt")
    @ApiOperation(value = "下载文件", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "手机号", required = true)
    public void sendPhoneCode(HttpServletResponse response) throws Exception {
    	 InputStream in=this.getClass().getResourceAsStream("/MP_verify_dSYneurbj349sTas.txt");
    	 byte[] buf=new byte[1024];
    	 int len=0;
    	 while((len=in.read(buf))!=-1) {
    		 response.getOutputStream().write(buf, 0, len);
    		 response.getOutputStream().flush();
    	 }
    	 in.close();
    }

}
