package com.jgw.supercodeplatform.marketing.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;

@Service
public class MarketingActivityService extends AbstractPageService<MarketingMembersWinRecordListParam>{
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
	protected List<MarketingMembersWinRecord> searchResult(MarketingMembersWinRecordListParam searchParams) throws Exception {
		List<MarketingActivityListMO> list=dao.list(searchParams);
		return null;
	}


	@Override
	protected int count(MarketingMembersWinRecordListParam searchParams) throws Exception {
		return dao.count(searchParams);
	}
  

}
