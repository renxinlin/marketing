package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrganizationPortraitService {

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;

    /**
     * 根据组织id获取已选画像关系
     * @param organizationId
     * @return
     */
    public List<MarketingOrganizationPortrait> getSelectedPortrait(Map<String, Object> params){
        String organizationId = params.get("organizationId").toString();
        return organizationPortraitMapper.getSelectedPortrait(organizationId);
    }

    /**
     * 根据组织id获取未选画像关系
     * @param organizationId
     * @return
     */
    public List<MarketingOrganizationPortrait> getUnselectedPortrait(Map<String, Object> params){
        String organizationId = params.get("organizationId").toString();
        return organizationPortraitMapper.getSelectedPortrait(organizationId);
    }

    /**
     * 添加组织画像关系
     * @param organizationPortrait
     * @return
     */
    public int addOrgPortrait(MarketingOrganizationPortrait organizationPortrait){
        return organizationPortraitMapper.addOrgPortrait(organizationPortrait);
    }

    /**
     * 删除组织画像关系
     * @param organizationPortrait
     * @return
     */
    public int deleOrgPortrait(MarketingOrganizationPortrait organizationPortrait){
        return organizationPortraitMapper.deleOrgPortrait(organizationPortrait);
    }
}
