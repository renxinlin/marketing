package com.jgw.supercodeplatform.marketing.controller.common;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.service.common.WechatMerchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/markering/merchant")
public class WechatMerchatController {

    @Autowired
    private WechatMerchatService wechatMerchatService;

//    @PostMapping("/addMerchantList")
//    public RestResult<?> addMerchantList(@RequestParam MultipartFile files[], @RequestParam String detail){
//
//    }

}
