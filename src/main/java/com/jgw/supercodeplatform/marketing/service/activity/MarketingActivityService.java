package com.jgw.supercodeplatform.marketing.service.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;

@Service
public class MarketingActivityService extends AbstractPageService<MarketingActivityListParam>{
    @Autowired
    private MarketingActivityMapper dao;
    
    
	public RestResult<List<MarketingActivity>> selectAll() {
		List<MarketingActivity> list=dao.selectAll();
		RestResult<List<MarketingActivity>> restResult=new RestResult<List<MarketingActivity>>();
		restResult.setState(200);
		restResult.setResults(list);
		restResult.setMsg("成功");
		return restResult;
	}


	@Override
	protected List<MarketingActivityListMO> searchResult(MarketingActivityListParam marketingActivityListParam) throws Exception {
		List<MarketingActivityListMO> list=dao.list(marketingActivityListParam);
		return list;
	}


	@Override
	protected int count(MarketingActivityListParam marketingActivityListParam) throws Exception {
		return dao.count(marketingActivityListParam);
	}
  

}
