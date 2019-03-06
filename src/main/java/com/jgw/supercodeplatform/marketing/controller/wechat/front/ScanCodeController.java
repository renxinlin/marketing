package com.jgw.supercodeplatform.marketing.controller.wechat.front;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/marketing/front/scan")
public class ScanCodeController {
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Autowired
    private MarketingActivitySetService mActivitySetService;
    
    @Autowired
    private MarketingWxMerchantsService mWxMerchantsService;
    
    @Value("${marketing.wxauth.redirect.uri}")
    private String wxauthRedirectUri;
    
    
    @Value("${marketing.activity.h5page.url}")
    private String h5pageUrl;
    
    @Value("${rest.user.url}")
    private String restUserUrl;
    
    /**
     * 客户扫码码平台跳转到营销系统地址接口
     * @param codeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "码平台跳转营销系统路径", notes = "")
    public String bind(String codeId,String codeTypeId,String productId,String productBatchId) throws Exception {
    	ScanCodeInfoMO sCodeInfoMO=mActivitySetService.judgeActivityScanCodeParam(codeId,codeTypeId,productId,productBatchId);
    	String	wxstate=commonUtil.getUUID();
    	
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("productId",productId);
        ResponseEntity<String> responseEntity=restTemplateUtil.getRequestAndReturnJosn(restUserUrl, params, null);
        String body=responseEntity.getBody();
        if (StringUtils.isBlank(body)) {
			throw new SuperCodeException("获取商品基础信息失败", 500);
		}
        JSONObject bodyObj=JSONObject.parseObject(body);
        int state=bodyObj.getIntValue("state");
        if (500==state) {
        	throw new SuperCodeException("获取商品基础信息失败，错误信息："+bodyObj.getString("msg"), 500);
		}
        String organizationId=bodyObj.getJSONObject("results").getString("organizationId");
        MarketingWxMerchants mWxMerchants=mWxMerchantsService.selectByOrganizationId(organizationId);
        if (null==mWxMerchants || StringUtils.isBlank(mWxMerchants.getMchAppid())) {
        	throw new SuperCodeException("该产品对应的企业未进行公众号绑定或企业APPID未设置。企业id："+organizationId, 500);
		}
        sCodeInfoMO.setOrganizationId(organizationId);
        GlobalRamCache.scanCodeInfoMap.put(wxstate, sCodeInfoMO);
        
    	//微信授权需要对redirect_uri进行urlencode
    	String encoderedirectUri=URLEncoder.encode(wxauthRedirectUri, "utf-8");
        return "redirect:"+h5pageUrl+"?state="+wxstate+"&appid="+mWxMerchants.getMchAppid()+"&redirect_uri="+encoderedirectUri;
    }

}
