package com.jgw.supercodeplatform.marketing.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingOrganizationPortraitParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.enums.portrait.PortraitTypeEnum;
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
    	String organizationName = null;
    	if (StringUtils.isBlank(organizationId)) {
    		 organizationId = getOrganizationId();
    		 organizationName = getOrganizationName();
		}
    	List<MarketingOrganizationPortraitListParam> portraitList = organizationPortraitMapper.getSelectedPortrait(organizationId, PortraitTypeEnum.PORTRAIT.getTypeId());
    	MarketingUnitcode marketingUnitcode = organizationPortraitMapper.getMobilePortrait();
    	if(marketingUnitcode != null) {
    		if(!portraitList.stream().anyMatch(portrait -> ObjectUtils.equals(marketingUnitcode.getCodeId(), portrait.getCodeId()))) {
    			MarketingOrganizationPortrait organizationPortrait = new MarketingOrganizationPortrait();
    	    	organizationPortrait.setFieldWeight(0);
    	    	organizationPortrait.setOrganizationFullName(organizationName);
    	    	organizationPortrait.setOrganizationId(organizationId);
    	    	organizationPortrait.setUnitCodeId((long)marketingUnitcode.getId());
    	    	organizationPortraitMapper.addOrgPortrait(organizationPortrait);
    	    	MarketingOrganizationPortraitListParam portraitParam = new MarketingOrganizationPortraitListParam();
    	    	BeanUtils.copyProperties(marketingUnitcode, portraitParam);
    	    	BeanUtils.copyProperties(organizationPortrait, portraitParam);
    	    	portraitList.add(0, portraitParam);
    		}
    	}
        return portraitList;
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
        List<MarketingUnitcode> unselectList = organizationPortraitMapper.getUnselectedPortrait(organizationId,PortraitTypeEnum.PORTRAIT.getTypeId());
        return unselectList.stream().filter(predicate -> !"Mobile".equals(predicate.getCodeId())).collect(Collectors.toList());
    }


    /**
     * 添加组织画像关系|以及标签
     * @param params
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public RestResult<String> addOrgPortrait(List<MarketingOrganizationPortraitParam> params) throws Exception{
      if (null==params || params.isEmpty()) {
		throw new SuperCodeException("参数不能为空", 500);
	  }


      String organizationId=getOrganizationId();
      String organizationName=getOrganizationName();
      organizationPortraitMapper.deleOrgPortrait(organizationId);


      // 手机号画像添加
      MarketingUnitcode mobileUnitCode = organizationPortraitMapper.getMobilePortrait();
      Set<MarketingOrganizationPortrait> mPortraitSets = new HashSet<>();
      MarketingOrganizationPortrait mobilePortrait=new MarketingOrganizationPortrait();
      mobilePortrait.setOrganizationFullName(organizationName);
      mobilePortrait.setOrganizationId(organizationId);
      int mobilePortraitId = mobileUnitCode.getId();
      mobilePortrait.setUnitCodeId((long) mobilePortraitId);
      // 手机的默认排序[优先级高]
      mobilePortrait.setFieldWeight(0);
      mPortraitSets.add(mobilePortrait);

      for (MarketingOrganizationPortraitParam marketingOrganizationPortraitParam : params) {
          Long unitCodeId= marketingOrganizationPortraitParam.getUnitCodeId();
          if(mobilePortraitId == marketingOrganizationPortraitParam.getUnitCodeId()){
              // set中的对象都是画像元素都是手机的时候,对象不一定相等【权重】
              continue;
          }
          MarketingOrganizationPortrait mPortrait=new MarketingOrganizationPortrait();
          mPortrait.setOrganizationFullName(organizationName);
          mPortrait.setOrganizationId(organizationId);
          mPortrait.setUnitCodeId(unitCodeId);
          mPortrait.setFieldWeight(marketingOrganizationPortraitParam.getFieldWeight());
          mPortraitSets.add(mPortrait);
      }




      List<MarketingOrganizationPortrait> mPortraits= new ArrayList<>(mPortraitSets);
      organizationPortraitMapper.batchInsert(mPortraits);
      RestResult<String> restResult=new RestResult<String>();
      restResult.setState(200);
      restResult.setMsg("成功");
      return restResult;
    }

    /**
     * 获取已经选择的标签
     * @param organizationId
     * @return
     */
    public List<MarketingOrganizationPortraitListParam> getSelectedLabel(String organizationId) throws SuperCodeException{
        if (StringUtils.isBlank(organizationId)) {
            throw new SuperCodeException("获取组织信息失败...");
        }
        return  organizationPortraitMapper.getSelectedLabel(organizationId,PortraitTypeEnum.LABEL.getTypeId());
    }

    public List<MarketingUnitcode> getUnSelectedLabel(String organizationId) throws SuperCodeException{
        if (StringUtils.isBlank(organizationId)) {
            throw new SuperCodeException("获取组织信息失败...");
        }
        return organizationPortraitMapper.getUnSelectedLabel(organizationId,PortraitTypeEnum.LABEL.getTypeId());
    }


}
