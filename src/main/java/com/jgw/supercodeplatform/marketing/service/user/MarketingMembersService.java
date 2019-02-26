package com.jgw.supercodeplatform.marketing.service.user;

import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarketingMembersService {

    @Autowired
    private MarketingMembersMapper marketingMembersMapper;


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
