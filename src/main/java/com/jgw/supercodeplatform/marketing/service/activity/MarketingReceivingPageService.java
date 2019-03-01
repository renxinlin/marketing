package com.jgw.supercodeplatform.marketing.service.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.dao.activity.MarketingReceivingPageMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;

@Service
public class MarketingReceivingPageService {
@Autowired
private MarketingReceivingPageMapper mReceivingPageMapper;

public MarketingReceivingPage selectByActivitySetId(Long activitySetId) {
	return mReceivingPageMapper.getByActivityId(activitySetId);
}

}
