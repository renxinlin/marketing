package com.jgw.supercodeplatform.marketing.service.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;

@Service
public class MarketingMembersWinRecordService extends AbstractPageService<MarketingMembersWinRecordListParam>{
	
 @Autowired
 private MarketingMembersWinRecordMapper dao;

@Override
protected List<MarketingMembersWinRecord> searchResult(MarketingMembersWinRecordListParam searchParams) throws Exception {
	List<MarketingMembersWinRecord> list=dao.list(searchParams);
	return list;
}

@Override
protected int count(MarketingMembersWinRecordListParam searchParams) throws Exception {
	
	return dao.count(searchParams);
}
 
 
}
