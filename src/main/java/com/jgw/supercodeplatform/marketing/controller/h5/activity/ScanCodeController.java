package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.IpUtils;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
  import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/marketing/front/scan")
@Api(tags = "h5接收码管理跳转路径")
@Slf4j
public class ScanCodeController {
     //导购活动跳跳前端后缀
	private final static String salerUrlsuffix = "#/salesRedBag/index";
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MarketingActivitySetService mActivitySetService;

    @Autowired
    private MarketingWxMerchantsService mWxMerchantsService;
    
    @Autowired
    private RestTemplate asyncRestTemplate;

    @Value("${marketing.domain.url}")
    private String wxauthRedirectUri;

    @Value("${rest.antismashinggoods.url}")
    private String antismashinggoodsUrl;
    
    @Value("${marketing.activity.h5page.url}")
    private String h5pageUrl;

    @Value("${rest.user.url}")
    private String restUserUrl;

    @Value("${rest.user.domain}")
    private String restUserDomain;

    @Value("${marketing.integral.h5page.urls}")
    private String integralH5Pages;

    /**
     * 导购前端领奖页
     */
    @Value("${rest.user.url}")
    private String SALER_LOTTERY_URL;



    @Autowired
    private GlobalRamCache globalRamCache;


    @Autowired
    private CodeEsService es;
    /**
     * 客户扫码码平台跳转到营销系统地址接口
     * @param outerCodeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "码平台跳转营销系统路径", notes = "")
    public String bind(@RequestParam String outerCodeId,@RequestParam String codeTypeId,@RequestParam String productId,@RequestParam String productBatchId, @RequestParam String sBatchId, @RequestParam Integer businessType, HttpServletRequest request) throws Exception {
    	if (StringUtils.isBlank(productBatchId) || StringUtils.equalsIgnoreCase(productBatchId, "null")) {
            productBatchId = null;
        }
        Map<String, String> uriVariables = new HashMap<>();
    	uriVariables.put("judgeType", "2");
    	uriVariables.put("outerCodeId", outerCodeId);
    	uriVariables.put("codeTypeId",codeTypeId);
    	uriVariables.put("ipAddr",IpUtils.getClientIpAddr(request));
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        //body
        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(uriVariables), requestHeaders);
    	try {
    		asyncRestTemplate.postForEntity(antismashinggoodsUrl+CommonConstants.JUDGE_FLEE_GOOD, requestEntity, JSONObject.class);
    	} catch (Exception e) {
			log.error("窜货接口错误", e);
		}
    	String wxstate=commonUtil.getUUID();
        log.info("会员扫码接收到参数outerCodeId="+outerCodeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId+",sBatchId="+sBatchId);
        //    	String url=activityJudege(outerCodeId, codeTypeId, productId, productBatchId, wxstate,(byte)0, sBatchId, businessType);
        String errorUrl = activitySetJudege(outerCodeId, codeTypeId, productId, productBatchId, wxstate, (byte) 0, sBatchId, businessType);
        if (StringUtils.isNotBlank(errorUrl)) {
            return "redirect:" + errorUrl;
        }
    	ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(wxstate);
        if(scanCodeInfoMO != null ) {
            // 全部是活动
            scanCodeInfoMO.setScanCodeTime(new Date());
            es.indexScanInfo(scanCodeInfoMO);
        }
        String organizationId = scanCodeInfoMO.getOrganizationId();
        long activityId = scanCodeInfoMO.getActivityId();
        long activitySetId = scanCodeInfoMO.getActivitySetId();
        String redirectUrl = getUrlByActId(activityId);
        if (redirectUrl == null) {
            redirectUrl = h5pageUrl+"?success=0&msg="+URLEncoder.encode(URLEncoder.encode("找不到对应的活动信息","utf-8"),"utf-8");
        }
        redirectUrl = redirectUrl + "?success=1&wxstate=" + wxstate + "&activitySetId=" + activitySetId + "&organizationId=" +organizationId;

        return "redirect:"+redirectUrl;
    }


    /**
     * 导购扫码码平台跳转到营销系统地址接口
     * @param outerCodeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(value = "/saler",method = RequestMethod.GET)
    @ApiOperation(value = "码平台跳转营销系统导购路径", notes = "")
    public String daogou(@RequestParam String outerCodeId,@RequestParam String codeTypeId,@RequestParam String productId,@RequestParam String productBatchId,@RequestParam String sBatchId, @RequestParam String memberId) throws Exception {
        if (StringUtils.isBlank(productBatchId) || StringUtils.equalsIgnoreCase(productBatchId, "null")) {
            productBatchId = null;
        }
        log.info("导购扫码接收到参数outerCodeId="+outerCodeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId+"sBatchId="+sBatchId);
    	String	wxstate=commonUtil.getUUID();
    	String url=activityJudegeBySaler(outerCodeId, codeTypeId, productId, productBatchId,sBatchId, wxstate, ReferenceRoleEnum.ACTIVITY_SALER.getType());
        // 领取按钮对应的前端URL
        return "redirect:"+url+"&memberId="+memberId;
    }

    /**
     * 携带wxstate 并返回前端
     * @param outerCodeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @param wxstate
     * @param referenceRole
     * @return
     * @throws SuperCodeException
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    private String activityJudegeBySaler(String outerCodeId, String codeTypeId, String productId, String productBatchId,String sBatchId, String wxstate, byte referenceRole) throws SuperCodeException, UnsupportedEncodingException, ParseException {

        RestResult<ScanCodeInfoMO> restResult=mActivitySetService.judgeActivityScanCodeParam(outerCodeId,codeTypeId,productId,productBatchId,referenceRole);
        if (restResult.getState()==500) {
            log.info("扫码接口返回错误，错误信息为："+restResult.getMsg());
            return h5pageUrl+salerUrlsuffix+"?wxstate=0_"+URLEncoder.encode(restResult.getMsg(),"utf-8");
        }

        ScanCodeInfoMO sCodeInfoMO=restResult.getResults();
        sCodeInfoMO.setSbatchId(sBatchId);
        //在校验产品及产品批次时可以从活动设置表中获取组织id
        String organizationId=sCodeInfoMO.getOrganizationId();
        sCodeInfoMO.setOrganizationId(organizationId);
        globalRamCache.putScanCodeInfoMO(wxstate,sCodeInfoMO);

        log.info("扫码后sCodeInfoMO信息："+sCodeInfoMO);
        String url=h5pageUrl+salerUrlsuffix+"?wxstate="+wxstate+"&organizationId="+sCodeInfoMO.getOrganizationId();
        return url;


    }

    /**
     * 活动过期/产品批次是否参与活动
     * @param outerCodeId
     * @param codeTypeId
     * @param productId
     * @param productBatchId
     * @param wxstate
     * @param referenceRole
     * @return
     * @throws UnsupportedEncodingException
     * @throws ParseException
     * @throws SuperCodeException
     */
    public String activityJudege(String outerCodeId,String codeTypeId,String productId,String productBatchId,String wxstate, byte referenceRole,String sbatchId, Integer businessType) throws UnsupportedEncodingException, ParseException {
    	RestResult<ScanCodeInfoMO> restResult=mActivitySetService.judgeActivityScanCodeParam(outerCodeId,codeTypeId,productId,productBatchId,referenceRole, businessType);
    	if (restResult.getState()==500) {
    		log.info("扫码接口返回错误，错误信息为："+restResult.getMsg());
    		 return h5pageUrl+"?success=0&msg="+URLEncoder.encode(URLEncoder.encode(restResult.getMsg(),"utf-8"),"utf-8");
		}

    	ScanCodeInfoMO sCodeInfoMO=restResult.getResults();
    	//在校验产品及产品批次时可以从活动设置表中获取组织id
        String organizationId=sCodeInfoMO.getOrganizationId();
        MarketingWxMerchants mWxMerchants=mWxMerchantsService.selectByOrganizationId(organizationId);
        if (null==mWxMerchants) {
        	 return h5pageUrl+"?success=0&msg="+URLEncoder.encode(URLEncoder.encode("该产品对应的企业未进行公众号绑定或企业APPID未设置。企业id："+organizationId,"utf-8"),"utf-8");
		} else {
            organizationId = mWxMerchants.getOrganizationId();
            if (mWxMerchants.getMerchantType().intValue() == 1) {
                if (mWxMerchants.getJgwId() != null) {
                    mWxMerchants = mWxMerchantsService.getJgw(mWxMerchants.getJgwId());
                } else {
                    mWxMerchants = mWxMerchantsService.getDefaultJgw();
                }
            }
        }
        sCodeInfoMO.setSbatchId(sbatchId);
        sCodeInfoMO.setOrganizationId(organizationId);
        globalRamCache.putScanCodeInfoMO(wxstate,sCodeInfoMO);
        log.info("扫码后sCodeInfoMO信息："+sCodeInfoMO);

    	//微信授权需要对redirect_uri进行urlencode
        String wholeUrl=wxauthRedirectUri+"/marketing/front/auth/code";
    	String encoderedirectUri=URLEncoder.encode(wholeUrl, "utf-8");
        log.info("扫码唯一标识wxstate="+wxstate+"，授权跳转路径url="+encoderedirectUri+",appid="+mWxMerchants.getMchAppid()+",h5pageUrl="+h5pageUrl);
        String url=h5pageUrl+"?wxstate="+wxstate+"&appid="+mWxMerchants.getMchAppid()+"&redirect_uri="+encoderedirectUri+"&success=1"+"&organizationId="+organizationId;
        return url;
    }

    @GetMapping("/code/callback")
    @ApiOperation("微信静默授权")
    public String getWXCode(@RequestParam String code, @RequestParam String state) {
        log.info("微信授权回调获取code=" + code + ",state=" + state);
        if (StringUtils.isBlank(state)) {
            throw new SuperCodeExtException("state不能为空", 500);
        }
        try {
            return "redirect:" + restUserDomain + "/wechat/org/info?code=" + code + "&state=" + state;
        } catch (Exception e) {
            throw new SuperCodeExtException("回调参数不正确");
        }
    }


    private String activitySetJudege(String outerCodeId,String codeTypeId,String productId,String productBatchId,String wxstate, byte referenceRole,String sbatchId, Integer businessType) throws UnsupportedEncodingException, ParseException {
        RestResult<ScanCodeInfoMO> restResult=mActivitySetService.judgeActivityScanCodeParam(outerCodeId,codeTypeId,productId,productBatchId,referenceRole, businessType);
        if (restResult.getState()==500) {
            log.info("扫码接口返回错误，错误信息为："+restResult.getMsg());
            return h5pageUrl+"?success=0&msg="+URLEncoder.encode(URLEncoder.encode(restResult.getMsg(),"utf-8"),"utf-8");
        }

        ScanCodeInfoMO sCodeInfoMO=restResult.getResults();
        //在校验产品及产品批次时可以从活动设置表中获取组织id
        String organizationId=sCodeInfoMO.getOrganizationId();
        sCodeInfoMO.setSbatchId(sbatchId);
        sCodeInfoMO.setOrganizationId(organizationId);
        globalRamCache.putScanCodeInfoMO(wxstate,sCodeInfoMO);
        log.info("扫码后sCodeInfoMO信息："+sCodeInfoMO);
        return null;
    }

    private String getUrlByActId(long actId){
        String redirectUrl = null;
        if (actId == ActivityIdEnum.ACTIVITY_WX_RED_PACKAGE.getId().longValue() || actId == ActivityIdEnum.ACTIVITY_2.getId().longValue()) {
            redirectUrl = h5pageUrl;
        }
        if (actId == ActivityIdEnum.ACTIVITY_SALER.getId().longValue()) {
            redirectUrl = h5pageUrl + WechatConstants.SALER_LOGIN_URL;
        }
        if (actId == ActivityIdEnum.ACTIVITY_COUPON.getId().longValue()) {
            redirectUrl = integralH5Pages.split(",")[0];
        }
        return redirectUrl;
    }

}
