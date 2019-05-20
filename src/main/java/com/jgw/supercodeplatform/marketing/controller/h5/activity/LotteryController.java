package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.service.LotteryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/lottery")
@Api(tags = "h5用户领奖方法")
public class LotteryController extends CommonUtil {
	private static Logger logger = LoggerFactory.getLogger(LotteryController.class);
	@Autowired
	private LotteryService service;
	
	@Value("${cookie.domain}")
	private String cookieDomain;
	/**
	 * 用户服务地址
	 */
	@Value("${rest.user.url}")
	private String USER_SERVICE;

    @RequestMapping(value = "/lottery",method = RequestMethod.POST)
    @ApiOperation(value = "用户点击领奖方法", notes = "")
    public RestResult<String> lottery(String wxstate) throws Exception {
        return service.lottery(wxstate, request);
    }
    
    @RequestMapping(value = "/guideLottery",method = RequestMethod.POST)
    @ApiOperation(value = "用户点击领奖方法", notes = "")
    public RestResult<String> guideLottery(String wxstate,HttpServletRequest request) throws Exception {
        return service.guideLottery(wxstate);
    }
    
   
}
