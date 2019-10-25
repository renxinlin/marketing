package com.jgw.supercodeplatform.marketing.service.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatModel;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class WechatMerchatService extends ServiceImpl<MarketingWxMerchantsMapper, MarketingWxMerchants>  {

    @Autowired
    private MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper;

    public void addMerchantList(MultipartFile files[], String detail) throws IOException {
        List<UserWechatModel> userWechatModelList = JSON.parseArray(detail, UserWechatModel.class);
        Map<String, byte[]> fileMap = new HashMap<>();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            fileMap.put(fileName, fileBytes);
        }
        List<MarketingWxMerchants> marketingWxMerchantsList = new ArrayList<>();
        List<MarketingWxMerchantsExt> marketingWxMerchantsExtList = new ArrayList<>();
        userWechatModelList.stream().forEach(userWechatModel -> {

        });


    }


}
