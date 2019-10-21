package com.jgw.supercodeplatform.marketing.controller.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商户公众号绑定
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/snBind")
@Api(tags = "公众号绑定")
public class WeixinSNBindController extends CommonUtil {
    
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;

    @Value("${weixin.certificate.path}")
    private String path;

	/**
	 * 上传文件
	 * @author liujianqiang
	 * @data 2018年9月6日
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	@ApiOperation(value = "上传文件",notes = "文件唯一id")
	@ApiImplicitParams({		
		@ApiImplicitParam(name = "super-token",paramType ="header",defaultValue = "64b379cd47c843458378f479a115c322",value="token信息",required=true),
		@ApiImplicitParam(name = "uploadFile",paramType ="file",required=true)
	})
	public RestResult<String> uploadFile(
			@RequestParam(value="uploadFile") MultipartFile file) throws Exception{
	    if (null==file) {
	    	throw new SuperCodeException("文件不能为空");
		}
	    RestResult<String> restResult=new RestResult<String>();
	    restResult.setState(200);
	    String name=marketingWxMerchantsService.uploadFile(file);
	    restResult.setResults(name);
	    restResult.setMsg("成功");
		return restResult;
	}
	
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	@ApiOperation(value = "微信商户信息绑定", notes = "")
	@ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
	public RestResult<String> bind(@RequestBody @Validated MarketingWxMerchantsParam wxMerchantsParam, BindingResult bindingResult) throws Exception {
		// 校验组织ID
		String organizationId = getOrganizationId();
		if(StringUtils.isEmpty(organizationId)){
			return new RestResult<String>(500, "获取组织信息失败", null);
		}
		if (wxMerchantsParam.getMerchantType() != 0){
			marketingWxMerchantsService.setUseType(wxMerchantsParam.getMerchantType());
			return RestResult.success();
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> allErrors = bindingResult.getFieldErrors();
			List<String> collect = allErrors.stream().map(error -> error.getDefaultMessage()).collect(Collectors.toList());
			return RestResult.fail(collect.toString(), null);
		}
		// 新增还是更新基于数据库是否存在
		MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsService.selectByOrganizationId(organizationId, wxMerchantsParam.getMchAppid());
		// 补充组织参数
		wxMerchantsParam.setOrganizationId(organizationId);
		wxMerchantsParam.setOrganizatioIdlName(getOrganization().getOrganizationFullName());
		// 存储商户公众号信息
		if (StringUtils.isEmpty(marketingWxMerchants)) {
			marketingWxMerchantsService.addWxMerchants(wxMerchantsParam);
		} else {
			marketingWxMerchantsService.updateWxMerchants(wxMerchantsParam);
		}
		return new RestResult<String>(200, "success", null);
	}




/*    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息修改", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> update(@RequestBody MarketingWxMerchantsParam wxMerchantsParam) throws Exception {
        marketingWxMerchantsService.updateWxMerchants(wxMerchantsParam);
        return new RestResult(200, "success", null);
    }*/
    
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ApiOperation(value = "获取微信商户信息", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<MarketingWxMerchants> get() throws Exception {
        return marketingWxMerchantsService.get();
    }

}
