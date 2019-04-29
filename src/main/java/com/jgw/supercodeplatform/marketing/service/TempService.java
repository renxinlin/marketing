package com.jgw.supercodeplatform.marketing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.marketing.dao.integral.ExchangeStatisticsMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
/**
 * 临时使用
 * @author czm
 *
 */
@Service
public class TempService {

	@Autowired
	private IntegralRecordMapperExt recoredDao;
	
	@Autowired
	private IntegralOrderMapperExt orderDao;
	
	
	@Autowired
	private ExchangeStatisticsMapperExt exchangeStatisticsDao;
	
	
	@Transactional
	public void updateMemberId(Long oldMemberId,Long newMemberId) {
		recoredDao.updateMemberId(oldMemberId,newMemberId);
		orderDao.updateMemberId(oldMemberId,newMemberId);
		exchangeStatisticsDao.updateMemberId(oldMemberId,newMemberId);
	}
	
	
}
