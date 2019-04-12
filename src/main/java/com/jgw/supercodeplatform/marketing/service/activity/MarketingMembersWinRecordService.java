package com.jgw.supercodeplatform.marketing.service.activity;

import java.util.LinkedHashMap;
import java.util.List;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.exception.base.ExcelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Service
public class MarketingMembersWinRecordService extends AbstractPageService<MarketingMembersWinRecordListParam> {

	@Autowired
	private MarketingMembersWinRecordMapper dao;

	@Override
	protected List<MarketingMembersWinRecordListReturn> searchResult(MarketingMembersWinRecordListParam searchParams) throws Exception {
		List<MarketingMembersWinRecordListReturn> list = dao.list(searchParams);
		return list;
	}

	@Override
	protected int count(MarketingMembersWinRecordListParam searchParams) throws Exception {

		return dao.count(searchParams);
	}


	public int add(MarketingMembersWinRecord winRecord) {
		return dao.addWinRecord(winRecord);
	}





	public PageResults<List<MarketingMembersWinRecordListReturn>> listWithOrganization(MarketingMembersWinRecordListParam winRecordListParam) throws Exception{
		// 根据token获取组织ID
		String organizationId = getOrganizationId();
		if(StringUtils.isEmpty(organizationId)){
			throw new SuperCodeException("未获取到当前用户组织信息",500);
		}
		// 结合查询条件查询组织下的所有会员的中奖记录
		winRecordListParam.setOrganizationId(organizationId);
		PageResults<List<MarketingMembersWinRecordListReturn>> pageResults = this.listSearchViewLike(winRecordListParam);
		return  pageResults;
	}

}
