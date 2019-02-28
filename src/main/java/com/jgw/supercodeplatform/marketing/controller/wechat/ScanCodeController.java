package com.jgw.supercodeplatform.marketing.controller.wechat;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/marketing/scan")
public class ScanCodeController {
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private MarketingActivitySetService mActivitySetService;
    
    @Value("${marketing.wxauth.redirect.uri}")
    private String redirectUri;
    
    
    @Value("${marketing.activity.h5page.url}")
    private String h5pageUrl;
    
    
    /**
     * 
     * @param codeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "码平台跳转营销系统路径", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public String bind(String codeId,String codeTypeId,String productId,String productBatchId) throws Exception {
    	ScanCodeInfoMO sCodeInfoMO=mActivitySetService.judgeActivityScanCodeParam(codeId,codeTypeId,productId,productBatchId);
    	String	wxstate=commonUtil.getUUID();
    	GlobalRamCache.scanCodeInfoMap.put(wxstate, sCodeInfoMO);
    	//微信授权需要对redirect_uri进行urlencode
    	String encoderedirectUri=URLEncoder.encode(redirectUri, "utf-8");
        return "redirect:"+h5pageUrl+"?state="+wxstate+"&appid="+WechatConstants.APPID+"&redirect_uri="+encoderedirectUri;
    }

}
