package com.jgw.supercodeplatform.marketing.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingWxMemberMapper;
import com.jgw.supercodeplatform.marketing.dto.common.LoginWithWechat;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMember;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class LoginWithWechatService {

    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    @Autowired
    private MarketingWxMemberMapper marketingWxMemberMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MarketingMembersMapper marketingMembersMapper;

    public H5LoginVO memberLogin(LoginWithWechat loginWithWechat){
        String openid = loginWithWechat.getOpenid();
        String openidKey = RedisKey.WECHAT_OPENID_INFO + openid;
        String userObjJson = redisUtil.get(openidKey);
        redisUtil.remove(openidKey);
        if (StringUtils.isBlank(userObjJson)) {
            throw new SuperCodeExtException("微信用户信息已过期，请重新发起登录");
        }
        JSONObject userObj = JSON.parseObject(userObjJson);
        String organizationId = loginWithWechat.getOrganizationId();
        if (!organizationId.equals(userObj.getString("organizationId"))) {
            throw new SuperCodeExtException("组织ID有误");
        }
        MarketingWxMerchants marketingWxMerchants = getMerchantsByOrgId(organizationId);
        QueryWrapper<MarketingWxMember> openidWrapper = Wrappers.<MarketingWxMember>query().eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.VIP.getType());
        MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(openidWrapper);
        if (marketingWxMember == null) {
            marketingWxMember = new MarketingWxMember();
            marketingWxMember.setOrganizationId(organizationId);
            marketingWxMember.setUpdateTime(new Date());
            marketingWxMember.setCreateTime(new Date());
            marketingWxMember.setCurrentUse((byte)0);
            marketingWxMember.setOpenid(openid);
            marketingWxMember.setOrganizationFullName(userObj.getString("organizationName"));
            marketingWxMember.setMemberType(MemberTypeEnums.VIP.getType());
            marketingWxMember.setAppid(userObj.getString("appid"));
            marketingWxMember.setJgwType(marketingWxMerchants.getBelongToJgw());
            marketingWxMember.setWxName(userObj.getString("nickname"));
            marketingWxMember.setWechatHeadImgUrl(userObj.getString("headimgurl"));
            marketingWxMember.setWxSex(userObj.getByte("sex"));
            marketingWxMemberMapper.insert(marketingWxMember);
            return null;
        }
        Long memberId = marketingWxMember.getMemberId();
        if (memberId == null) {
            return null;
        }
        MarketingMembers member = marketingMembersMapper.selectById(memberId);
        if (member == null) {
            throw new SuperCodeExtException("找不到对应的用户信息");
        }
        H5LoginVO h5LoginVO=new H5LoginVO();
        h5LoginVO.setMemberType(marketingWxMember.getMemberType());
        h5LoginVO.setCustomerId(member.getCustomerId());
        h5LoginVO.setCustomerName(member.getCustomerName());
        h5LoginVO.setHaveIntegral(member.getHaveIntegral());
        h5LoginVO.setMemberId(memberId);
        h5LoginVO.setMobile(member.getMobile());
        h5LoginVO.setWechatHeadImgUrl(marketingWxMember.getWechatHeadImgUrl());
        h5LoginVO.setMemberName(StringUtils.isEmpty(member.getUserName())?marketingWxMember.getWxName():member.getUserName());
        h5LoginVO.setOrganizationId(member.getOrganizationId());
        h5LoginVO.setOpenid(marketingWxMember.getOpenid());
        h5LoginVO.setOrganizationName(marketingWxMember.getOrganizationFullName());
        return h5LoginVO;
    }




    private MarketingWxMerchants getMerchantsByOrgId(String organizationId){
        MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsService.selectByOrganizationId(organizationId);
        if (marketingWxMerchants == null) {
            log.error("找不到组织id为" + organizationId + "对应的微信信息");
            throw new SuperCodeExtException("找不到该组织对应的微信信息");
        }
        String organizationName = marketingWxMerchants.getOrganizatioIdlName();
        if (marketingWxMerchants.getMerchantType() == 1) {
            marketingWxMerchants = marketingWxMerchantsService.getJgw();
        }
        marketingWxMerchants.setOrganizatioIdlName(organizationName);
        return marketingWxMerchants;
    }

}
