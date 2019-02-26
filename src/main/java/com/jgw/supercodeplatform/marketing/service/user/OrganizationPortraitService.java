package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationPortraitService {

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;


    /**
     * 根据组织id获取已选画像关系
     * @param params
     * @return
     */
    public List<MarketingOrganizationPortrait> getSelectedPortrait(Map<String, Object> params){
        String organizationId = params.get("organizationId").toString();
        return organizationPortraitMapper.getSelectedPortrait(organizationId);
    }


    /**
     * 根据组织id获取未选画像关系
     * @param params
     * @return
     */
    public List<MarketingUnitcode> getUnselectedPortrait(Map<String, Object> params){
        //获取组织已选择的画像
        List<MarketingOrganizationPortrait> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(params.get("organizationId").toString());
        List<MarketingUnitcode> unitcodes = organizationPortraitMapper.getAllUnitcode();
        List<String> selectPortraitsCode = new ArrayList<>();
        List<String> codeIdList = new ArrayList<>();
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            selectPortraitsCode.add(portrait.getPortraitCode());
        }
        for (MarketingUnitcode unitcode:unitcodes){
            codeIdList.add(unitcode.getCodeId());
        }
        codeIdList.removeAll(selectPortraitsCode);
        if (codeIdList.size()!=0){
            Map<String, Object> map = new HashMap<>();
            map.put("codeIdList",codeIdList);
            map.put("typeId",params.get("typeId").toString());
            return organizationPortraitMapper.getPortraitsList(map);
        }else {
            return null;
        }

    }


    /**
     * 添加组织画像关系
     * @param params
     * @return
     */
    public int addOrgPortrait(Map<String, Object> params){
        MarketingOrganizationPortrait organizationPortrait = new MarketingOrganizationPortrait();
        organizationPortrait.setOrganizationId(params.get("organizationId").toString());
        organizationPortrait.setOrganizationFullName(params.get("organizationFullName").toString());
        organizationPortrait.setPortraitCode(params.get("portraitCode").toString());
        organizationPortrait.setPortraitName(params.get("portraitName").toString());
        int fieldWeight = organizationPortraitMapper.getSelectedPortraitCount(params.get("organizationId").toString());
        organizationPortrait.setFieldWeight(fieldWeight+1);
        return organizationPortraitMapper.addOrgPortrait(organizationPortrait);
    }


    /**
     * 删除组织画像关系
     * @param params
     * @return
     */
    public int deleOrgPortrait(Map<String, Object> params){
        MarketingOrganizationPortrait organizationPortrait = new MarketingOrganizationPortrait();
        organizationPortrait.setOrganizationId(params.get("organizationId").toString());
        organizationPortrait.setPortraitCode(params.get("portraitCode").toString());
        return organizationPortraitMapper.deleOrgPortrait(organizationPortrait);
    }
}
