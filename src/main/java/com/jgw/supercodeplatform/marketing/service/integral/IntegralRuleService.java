package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 积分记录
 */
@Service
public class IntegralRuleService  extends AbstractPageService<IntegralRecord > {
    private Logger logger  = LoggerFactory.getLogger(IntegralRuleService.class);

    
    
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private IntegralRuleMapperExt dao;

	public void edit(IntegralRule integralRule) throws SuperCodeException {
       String organizationId=commonUtil.getOrganizationId();
       IntegralRule eRule=dao.selectByOrgId(organizationId);
       if (null==integralRule.getId()) {
    	   if (null!=eRule) {
    		   throw new SuperCodeException("当前企业已经设置积分通用规则id不能为空", 500);
		   }
    	   integralRule.setOrganizationId(organizationId);
    	   integralRule.setOrganizationName(commonUtil.getOrganizationName());
    	  dao.insert(integralRule); 
	   }else {
		   dao.updateByPrimaryKeySelective(integralRule);
	   }
	}

	public IntegralRule getCurrOrgRule() throws SuperCodeException {
		String organizationId=commonUtil.getOrganizationId();
		IntegralRule eRule=dao.selectByOrgId(organizationId);
		return eRule;
	}

	public IntegralRule selectByOrgId(String organizationId) {
		return dao.selectByOrgId(organizationId);
	}


}
