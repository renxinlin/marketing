package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPlatformOrganizationMapper;
import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MarketingPlatformOrganizationService {

    @Autowired
    private MarketingPlatformOrganizationMapper marketingPlatformOrganizationMapper;


    /**
     * 批量添加活动组织信息
     * @param platformOrganizationList
     */
    public void insertPlatformOrganizationList(List<MarketingPlatformOrganization> platformOrganizationList){
        int size = platformOrganizationList.size();
        int pagSize = size%1000 == 0? size/1000 : size/1000 + 1;
        for (int i = 0;i < pagSize; i++) {
            int start = i*1000;
            int end = (i+1)*1000;
            if (end > size) {
                end = size;
            }
            List<MarketingPlatformOrganization> subList = platformOrganizationList.subList(start, end);
            marketingPlatformOrganizationMapper.insertList(subList);
        }
    }

    public


}
