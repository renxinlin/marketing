package com.jgw.supercodeplatform.marketing.controller.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息绑定", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> bind(@RequestBody MarketingWxMerchantsParam wxMerchantsParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
        	Long id=wxMerchantsParam.getId();
            if (null==id) {
            	 marketingWxMerchantsService.addWxMerchants(wxMerchantsParam);
			}else {
				 marketingWxMerchantsService.updateWxMerchants(wxMerchantsParam);
			}

        } catch (Exception e) {
            e.printStackTrace();
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
