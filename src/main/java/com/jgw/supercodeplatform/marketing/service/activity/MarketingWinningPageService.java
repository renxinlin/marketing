package com.jgw.supercodeplatform.marketing.service.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;

@Service
public class MarketingWinningPageService {
 @Autowired
 private MarketingWinningPageMapper mWinningPageMapper;

public MarketingWinningPage selectByActivitySetId(Long activitySetId) {
	return mWinningPageMapper.getByActivityId(activitySetId);
}
 
 
}
