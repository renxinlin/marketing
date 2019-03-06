package com.jgw.supercodeplatform.marketing.service.activity;

import java.util.LinkedHashMap;
import java.util.List;

import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;

import javax.servlet.http.HttpServletResponse;

@Service
public class MarketingMembersWinRecordService extends AbstractPageService<MarketingMembersWinRecordListParam>{
	
 @Autowired
 private MarketingMembersWinRecordMapper dao;

@Override
protected List<MarketingMembersWinRecordListReturn> searchResult(MarketingMembersWinRecordListParam searchParams) throws Exception {
	List<MarketingMembersWinRecordListReturn> list=dao.list(searchParams);
	return list;
}

@Override
protected int count(MarketingMembersWinRecordListParam searchParams) throws Exception {
	
	return dao.count(searchParams);
}


public int add(MarketingMembersWinRecord winRecord){
	return dao.addWinRecord(winRecord);
}


	public void littleGeneralWinRecordExcelOutToResponse(List<String> ids, HttpServletResponse response, LinkedHashMap filedMap) throws ExcelException {

		List<MarketingMembersWinRecordListReturn> membersWinRecord = dao.getWinRecordByidArray(ids);
		ExcelUtils.listToExcel(membersWinRecord, filedMap, "中奖记录", response);
	}
 
 
}
