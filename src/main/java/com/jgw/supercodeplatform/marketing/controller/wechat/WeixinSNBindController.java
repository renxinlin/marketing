package com.jgw.supercodeplatform.marketing.controller.wechat;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 商户公众号绑定
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/snBind")
public class WeixinSNBindController extends CommonUtil {
    
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;

    @Value("${weixin.certificate.path}")
    private String path;

    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息绑定", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> bind(@RequestBody MarketingWxMerchantsParam wxMerchantsParam, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            MultipartFile file = wxMerchantsParam.getFile();
            //获取上传文件的名称
            String fileName = file.getOriginalFilename();
            //截取参数之后剩余的字符串并返回（返回文件名中“.”的索引值），获取上传图片的后缀名
            String newFileName = getUUID();
            wxMerchantsParam.setFileName(newFileName);
            String ext = fileName.substring(fileName.indexOf("."));
            List<String> list = new ArrayList<>();
            list.add(".DER");
            list.add(".PEM");
            list.add(".CER");
            list.add(".CRT");
            File newFile1 = new File(path + File.separator + getOrganizationId() + File.separator);
            MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsService.get(getOrganizationId());
            if (marketingWxMerchants.getFileName()!=null){
                for (File f : newFile1.listFiles()) {
                    if (f.getName().contains(marketingWxMerchants.getFileName())) {
                        //将指定的文件删除
                        f.delete();
                    }
                }
            }

            if (list.contains(ext)) {
                File newFile = new File(path + File.separator + getOrganizationId() + File.separator, newFileName + ext);
                file.transferTo(newFile);
                //上传成功发送给前台的提示信息
                response.getWriter().write("true");
                if(null==marketingWxMerchants){
                    marketingWxMerchantsService.addWxMerchants(wxMerchantsParam);
                }else{
                    marketingWxMerchantsService.updateWxMerchants(wxMerchantsParam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败，有异常发送给前台的提示信息
            response.getWriter().write("false");
        }


        return new RestResult(200, "success", null);
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
