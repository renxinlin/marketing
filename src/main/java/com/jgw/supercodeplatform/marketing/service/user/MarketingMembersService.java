package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.admincode.AdminstrativeCodeMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.admincode.MarketingAdministrativeCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketingMembersService extends CommonUtil {

    @Autowired
    private MarketingMembersMapper marketingMembersMapper;

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;

    @Autowired
    private AdminstrativeCodeMapper adminstrativeCodeMapper;


    /**
     * 会员注册
     * @param map
     * @return
     * @throws Exception
     */
    public int addMember(Map<String,Object> map) throws Exception{
        String userId = getUUID();
        map.put("userId",userId);
        Map<String,Object> areaCode = new HashMap<>();
        areaCode.put("areaCode",map.get("cityCode").toString());
        MarketingAdministrativeCode marketingAdministrativeCode = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("cityName",marketingAdministrativeCode.getCityName());
        areaCode.put("areaCode",marketingAdministrativeCode.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode2 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("countyName",marketingAdministrativeCode2.getCityName());
        map.put("countyCode",marketingAdministrativeCode2.getAreaCode());
        areaCode.put("areaCode",marketingAdministrativeCode2.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode3 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("provinceName",marketingAdministrativeCode3.getCityName());
        map.put("provinceCode",marketingAdministrativeCode3.getAreaCode());
        return marketingMembersMapper.addMembers(map);
    }

    /**
     * 条件查询会员
     * @param map
     * @return
     */
    public List<MarketingMembers> getAllMarketingMembersLikeParams(Map<String,Object> map){
        List<MarketingOrganizationPortrait> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(map.get("organization").toString());
        List<String> portraitsList = new ArrayList<>();
        portraitsList.add("Mobile");
        portraitsList.add("WxName");
        portraitsList.add("Openid");
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            if (!"Mobile".equals(portrait.getPortraitCode())){
                portraitsList.add(portrait.getPortraitCode());
            }
        }
        portraitsList.add("State");
        String list = portraitsList.toString().replace("[","").replace("]","");;
        map.put("portraitsList",list);
        return marketingMembersMapper.getAllMarketingMembersLikeParams(map);
    }

    /**
     * 条件查询会员数量
     * @param map
     * @return
     */
    public Integer getAllMarketingMembersCount(Map<String,Object> map){
        return marketingMembersMapper.getAllMarketingMembersCount(map);
    }

    /**
     * 修改会员信息
     * @param map
     * @return
     */
    public int updateMembers(Map<String,Object> map){
        return marketingMembersMapper.updateMembers(map);
    }

    /**
     * 获取单个会员信息
     * @param map
     * @return
     */
    public MarketingMembers getMemberById(Map<String,Object> map){
        String userId = map.get("userId").toString();
        String organizationId = map.get("organizationId").toString();
        return marketingMembersMapper.getMemberById(userId,organizationId);
    }

	public MarketingMembers selectByOpenIdAndOrgId(String openid, String organizationId) {
		return marketingMembersMapper.selectByOpenIdAndOrgId(openid,organizationId);
	}
}
