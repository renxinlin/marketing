package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarketingWinningPageService {
 @Autowired
 private MarketingWinningPageMapper mWinningPageMapper;

public MarketingWinningPage selectByActivitySetId(Long activitySetId) {
	return mWinningPageMapper.getByActivityId(activitySetId);
}
 
 
}
