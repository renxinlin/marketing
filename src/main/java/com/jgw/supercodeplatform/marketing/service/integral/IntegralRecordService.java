package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 积分记录
 */
@Service
public class IntegralRecordService  extends AbstractPageService<IntegralRecord > {
    private Logger logger  = LoggerFactory.getLogger(IntegralRecordService.class);

    @Autowired
    private IntegralRecordMapperExt recordMapper;

    @Override
    protected List<IntegralRecord> searchResult(IntegralRecord integralRecord) throws Exception {
        // 0会员1导购2 其他
        integralRecord.setStartNumber((integralRecord.getCurrent()-1)*integralRecord.getPageSize());
        List<IntegralRecord> list=recordMapper.list(integralRecord);
        return list;
    }


    @Override
    protected int count(IntegralRecord integralRecord) throws Exception {

        return recordMapper.count(integralRecord);
    }


	public List<IntegralRecord> selectByMemberIdAndIntegralReasonCode(Long memberId, Integer integralReasonCode, String organizationId) {
		return recordMapper.selectByMemberIdAndIntegralReasonCode(memberId,integralReasonCode,organizationId);
	}


	public void batchInsert(List<IntegralRecord> inRecords) {
		recordMapper.batchInsert(inRecords);
	}

    /**
     * 组织积分发放金额
     * @param organizationId
     * @param date
     * @param date1
     */
    public Integer sumOrganizationUsingIntegralByDate(String organizationId, Date startDate, Date endDate) {
        return recordMapper.sumOrganizationUsingIntegralByDate( organizationId,  startDate,  endDate);
    }

    /**
     * 组织金额兑换金额
     * @param organizationId
     * @param date
     * @param date1
     */
    public Integer sumOrganizationIntegralExchangeByDate(String organizationId, Date startDate, Date endDate) {
        return recordMapper.sumOrganizationIntegralExchangeByDate( organizationId,  startDate,  endDate);

    }

    /**
     * 获取top6产品积分
     * @param organizationId
     * @param date
     * @param date1
     * @return
     */
    public List<IntegralRecord> getOrganizationTop6IntegralProduct(String organizationId, Date startDate, Date endDate) {
        return recordMapper.getOrganizationTop6IntegralProduct( organizationId,  startDate,  endDate);

    }

    public Integer getOrganizationAllIntegralProduct(String organizationId, Date startDate, Date endDate) {
        return recordMapper.getOrganizationAllIntegralProduct( organizationId,  startDate,  endDate);

    }


    public List<IntegralRecord> getOrganizationAllSalePrice(String organizationId, Date startDate, Date endDate) {
        return recordMapper.getOrganizationAllSalePrice( organizationId,  startDate,  endDate);

    }

    /**
     * 获取导购员中奖金额和中奖次数
     * @param memberId
     * @param memberType
     * @param organizationId
     */
    public Map getAcquireMoneyAndAcquireNums(Long memberId, Byte memberType, String organizationId) throws SuperCodeException{
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("获取组织信息失败...");
        }
        if(MemberTypeEnums.SALER.getType().intValue() != memberType){
            throw new SuperCodeException("非导购用户");
        }
        if(memberId ==null || memberId<=0){
            throw new SuperCodeException("会员不存在");
        }
        // key count sum
        Map<String, Object> statisticMap = recordMapper.getAcquireMoneyAndAcquireNums(memberId,memberType,organizationId);
        int scanNum = recordMapper.countScanCodeNum(memberId,memberType,organizationId);
        statisticMap.put("scanNum", scanNum);
        return statisticMap;
    }
}
