package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MarketingMembersService {

    @Autowired
    private MarketingMembersMapper marketingMembersMapper;

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;


    /**
     * 会员注册
     * @param map
     * @return
     * @throws Exception
     */
    public int addMember(Map<String,Object> map) throws Exception{
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
        int id = Integer.valueOf(map.get("id").toString());
        String organizationId = map.get("organizationId").toString();
        return marketingMembersMapper.getMemberById(id,organizationId);
    }
}
