package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
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
public class OrganizationPortraitService extends CommonUtil {

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
    public RestResult<String> addOrgPortrait(Map<String, Object> params) throws Exception{
        ArrayList<String> portraitCodeList = (ArrayList<String>)params.get("portraitCodeList");
        String organizationId = params.get("organizationId").toString();
        //获取组织已选画像
        List<MarketingOrganizationPortrait> organizationPortraits =  organizationPortraitMapper.getSelectedPortrait(organizationId);
        ArrayList<String> oldPortraitCodeList = new ArrayList<>();
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            oldPortraitCodeList.add(portrait.getPortraitCode());
        }
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(oldPortraitCodeList);
        oldPortraitCodeList.removeAll(portraitCodeList);
        //删除组织画像关系
        for (String oldCode:oldPortraitCodeList){
            MarketingOrganizationPortrait oPor = new MarketingOrganizationPortrait();
            oPor.setOrganizationId(params.get("organizationId").toString());
            oPor.setPortraitCode(oldCode);
            MarketingOrganizationPortrait organizationPortrait1 = organizationPortraitMapper.getPortraitByPortraitCode(oPor);
            for (MarketingOrganizationPortrait portrait: organizationPortraits){
                if (portrait.getFieldWeight()>organizationPortrait1.getFieldWeight()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("organizationId",portrait.getOrganizationId());
                    map.put("portraitCode",portrait.getPortraitCode());
                    map.put("fieldWeight",portrait.getFieldWeight()-1);
                    organizationPortraitMapper.updatePortraits(map);
                }
            }
            int record = organizationPortraitMapper.deleOrgPortrait(oPor);
            if (record==0){
                return new RestResult(500, "删除组织画像关系失败", null);
            }
        }
        //添加新增的组织画像关系
        portraitCodeList.removeAll(list);
        for (String code:portraitCodeList){
            MarketingOrganizationPortrait organizationPortrait = new MarketingOrganizationPortrait();
            organizationPortrait.setOrganizationId(params.get("organizationId").toString());
            organizationPortrait.setOrganizationFullName(getOrganizationName());
            organizationPortrait.setPortraitCode(code);
            MarketingUnitcode marketingUnitcode = organizationPortraitMapper.getUnitcodeByCode(code);
            organizationPortrait.setPortraitName(marketingUnitcode.getCodeName());
            int fieldWeight = organizationPortraitMapper.getSelectedPortraitCount(params.get("organizationId").toString());
            organizationPortrait.setFieldWeight(fieldWeight+1);
            int record = organizationPortraitMapper.addOrgPortrait(organizationPortrait);
            if (record==0){
                return new RestResult(500, "添加组织画像关系失败", null);
            }
        }
        return new RestResult(200, "success", null);
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
        MarketingOrganizationPortrait organizationPortrait1 = organizationPortraitMapper.getPortraitByPortraitCode(organizationPortrait);
        List<MarketingOrganizationPortrait> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(params.get("organizationId").toString());
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            if (portrait.getFieldWeight()>organizationPortrait1.getFieldWeight()){
                Map<String, Object> map = new HashMap<>();
                map.put("organizationId",portrait.getOrganizationId());
                map.put("portraitCode",portrait.getPortraitCode());
                map.put("fieldWeight",portrait.getFieldWeight()-1);
                organizationPortraitMapper.updatePortraits(map);
            }
        }
        return organizationPortraitMapper.deleOrgPortrait(organizationPortrait);
    }
}
