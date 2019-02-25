package com.jgw.supercodeplatform.marketing.service.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingReceivingPageMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.vo.activity.ReceivingAndWinningPageVO;

@Service
public class MarketingActivityService {
   @Autowired
   private MarketingWinningPageMapper marWinningPageMapper;
	
   @Autowired
   private MarketingReceivingPageMapper maReceivingPageMapper;
   
   
	public RestResult<ReceivingAndWinningPageVO> getPageInfo(Long activityId) {
		MarketingWinningPage marWinningPage=marWinningPageMapper.getByActivityId(activityId);
		MarketingReceivingPage mReceivingPage=maReceivingPageMapper.getByActivityId(activityId);
		return null;
	}

}
