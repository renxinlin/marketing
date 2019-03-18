package com.jgw.supercodeplatform.marketing.service.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingOrganizationPortraitParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;

@Service
public class OrganizationPortraitService extends CommonUtil {

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;


    /**
     * 根据组织id获取已选画像关系
     * @param organizationId2 
     * @param params
     * @return
     * @throws SuperCodeException 
     */
    public List<MarketingOrganizationPortraitListParam> getSelectedPortrait(String organizationId) throws SuperCodeException{
    	if (StringUtils.isBlank(organizationId)) {
    		 organizationId =getOrganizationId();
		}
        return organizationPortraitMapper.getSelectedPortrait(organizationId);
    }


    /**
     * 根据组织id获取未选画像关系
     * @param organizationId 
     * @param params
     * @return
     * @throws SuperCodeException 
     */
    public List<MarketingUnitcode> getUnselectedPortrait(String organizationId) throws SuperCodeException{
    	
    	if (StringUtils.isBlank(organizationId)) {
   		 organizationId =getOrganizationId();
		}

        List<MarketingUnitcode>unselectList=organizationPortraitMapper.getUnselectedPortrait(organizationId);

     return unselectList;
    }


    /**
     * 添加组织画像关系
     * @param params
     * @return
     */
    public RestResult<String> addOrgPortrait(List<MarketingOrganizationPortraitParam> params) throws Exception{
      if (null==params || params.isEmpty()) {
		throw new SuperCodeException("参数不能为空", 500);
	  }
      String organizationId=getOrganizationId();
      String organizationName=getOrganizationName();
      organizationPortraitMapper.deleOrgPortrait(organizationId);
      List<MarketingOrganizationPortrait>mPortraits=new ArrayList<MarketingOrganizationPortrait>();
      for (MarketingOrganizationPortraitParam marketingOrganizationPortraitParam : params) {
    	 Long unitCodeId= marketingOrganizationPortraitParam.getUnitCodeId();
    	 MarketingOrganizationPortrait mPortrait=new MarketingOrganizationPortrait();
    	 mPortrait.setOrganizationFullName(organizationName);
    	 mPortrait.setOrganizationId(organizationId);
    	 mPortrait.setUnitCodeId(unitCodeId);
    	 mPortrait.setFieldWeight(marketingOrganizationPortraitParam.getFieldWeight());
    	 mPortraits.add(mPortrait);
	  }
      organizationPortraitMapper.batchInsert(mPortraits);
      RestResult<String> restResult=new RestResult<String>();
      restResult.setState(200);
      restResult.setMsg("成功");
      return restResult;
    }


}
