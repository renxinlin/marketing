package com.jgw.supercodeplatform.SyncNetData.controller;

import com.jgw.supercodeplatform.SyncNetData.pojo.dto.MarketingMemberDto;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "2.0同步3.0接口")
@RequestMapping("/syncData")
@RestController
public class SyncDataController {

    @PostMapping("/member/add")
    public RestResult<Void> addMember(MarketingMemberDto marketingMemberDto){
        return null;
    }

}
