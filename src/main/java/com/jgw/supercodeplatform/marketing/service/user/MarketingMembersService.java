package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.admincode.AdminstrativeCodeMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
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
    public int addMember(MarketingMembersAddParam marketingMembersAddParam) throws Exception{
        String userId = getUUID();
        marketingMembersAddParam.setUserId(userId);
        Map<String,Object> areaCode = new HashMap<>();
        areaCode.put("areaCode",marketingMembersAddParam.getCityCode());
        MarketingAdministrativeCode marketingAdministrativeCode = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setCityName(marketingAdministrativeCode.getCityName());
        areaCode.put("areaCode",marketingAdministrativeCode.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode2 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setCountyName(marketingAdministrativeCode2.getCityName());
        marketingMembersAddParam.setCountyCode(marketingAdministrativeCode2.getAreaCode());
        areaCode.put("areaCode",marketingAdministrativeCode2.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode3 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setProvinceName(marketingAdministrativeCode3.getCityName());
        marketingMembersAddParam.setProvinceCode(marketingAdministrativeCode3.getAreaCode());
        return marketingMembersMapper.addMembers(marketingMembersAddParam);
    }

    /**
     * 条件查询会员
     * @param map
     * @return
     */
    public List<MarketingMembers> getAllMarketingMembersLikeParams(Map<String,Object> map){
        List<MarketingOrganizationPortrait> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(map.get("organizationId").toString());
        List<String> portraitsList = new ArrayList<>();
        portraitsList.add("Mobile");
        portraitsList.add("WxName");
        portraitsList.add("Openid");
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            if (!"Mobile".equals(portrait.getPortraitCode())){
                if ("Birthday".equals(portrait.getPortraitCode())||"BabyBirthday".equals(portrait.getPortraitCode())){
                    StringBuilder sb = new StringBuilder();
                    sb.append(" DATE_FORMAT( ");
                    sb.append( portrait.getPortraitCode());
                    sb.append(",'%Y-%m-%d') as " );
                    sb.append(portrait.getPortraitCode() );
                    portraitsList.add(sb.toString());
                }else {
                    portraitsList.add(portrait.getPortraitCode());
                }
            }
        }
        portraitsList.add("State");
        String list = portraitsList.toString().replace("[","").replace("]","");;
        map.put("portraitsList",list);
        System.out.println(list);
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
    public int updateMembers(MarketingMembersUpdateParam membersUpdateParam){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",membersUpdateParam.getUserId());
        map.put("userName",membersUpdateParam.getUserName());
        map.put("sex",membersUpdateParam.getSex());
        map.put("birthday",membersUpdateParam.getBirthday());
        map.put("organizationId",membersUpdateParam.getOrganizationId());
        map.put("cityCode",membersUpdateParam.getCityCode());
        map.put("customerName",membersUpdateParam.getCustomerName());
        map.put("customerCode",membersUpdateParam.getCustomerCode());
        map.put("babyBirthday",membersUpdateParam.getBabyBirthday());
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
        return marketingMembersMapper.updateMembers(map);
    }

    /**
     * 修改会员状态
     * @param map
     * @return
     */
    public int updateMembersStatus(Map<String,Object> map){
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
}
