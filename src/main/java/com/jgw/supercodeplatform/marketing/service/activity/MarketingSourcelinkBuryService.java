package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.dao.activity.MarketingSourcelinkBuryMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingSourcelinkBury;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketingSourcelinkBuryService {

    @Autowired
    private MarketingSourcelinkBuryMapper marketingSourcelinkBuryMapper;

    public void addSourceLinkBury(MarketingSourcelinkBury marketingSourcelinkBury){
        marketingSourcelinkBuryMapper.insertSourceLinkBury(marketingSourcelinkBury);
    }

}
