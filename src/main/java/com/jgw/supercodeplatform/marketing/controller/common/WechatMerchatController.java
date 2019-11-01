package com.jgw.supercodeplatform.marketing.controller.common;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.common.DelMerchantModel;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatModel;
import com.jgw.supercodeplatform.marketing.service.common.WechatMerchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/markering/merchant")
public class WechatMerchatController {

    @Autowired
    private WechatMerchatService wechatMerchatService;

    @PostMapping("/addOrUpdateMerchantList")
    public RestResult<?> addOrUpdateMerchantList(@RequestParam String detail) throws IOException {
        wechatMerchatService.addOrUpdateMerchantList(detail);
        return RestResult.success();
    }

    @PostMapping("/useJgw")
    public RestResult<?> useJgw(@RequestParam String organizationId,@RequestParam String organizationName,@RequestParam String jgwAppid){
        wechatMerchatService.useJgw(organizationId, organizationName, jgwAppid);
        return RestResult.success();
    }

    @DeleteMapping("/delMerchant")
    public RestResult<?> delMerchant(@RequestBody @Valid DelMerchantModel delMerchantModel){
        wechatMerchatService.delMerchant(delMerchantModel.getOrganizationId(), delMerchantModel.getAppid());
        return RestResult.success();
    }

}
