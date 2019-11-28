package com.jgw.supercodeplatform.marketing.controller.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingWxMemberMapper;
import com.jgw.supercodeplatform.marketing.dto.test.CodeUserDto;
import com.jgw.supercodeplatform.marketing.dto.test.SalerUserDto;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/marketing/test")
public class WxstateTestController {

    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    @Autowired
    private MarketingWxMemberMapper marketingWxMemberMapper;
    @Autowired
    private MarketingMembersMapper marketingMembersMapper;
    @Autowired
    private MarketingActivityProductMapper marketingActivityProductMapper;
    @Autowired
    private MarketingUserMapper marketingUserMapper;
    @Value("${cookie.domain}")
    private String cookieDomain;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private GlobalRamCache globalRamCache;

    @PostMapping("/jwtToken/member")
    public RestResult<String> getWxstate(CodeUserDto codeUserDto, HttpServletResponse response) throws SuperCodeException {

        String openid = codeUserDto.getOpenid();
        String organizationId = codeUserDto.getOrganizationId();
        QueryWrapper<MarketingWxMember> openidWrapper = Wrappers.<MarketingWxMember>query().eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.VIP.getType());
        MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(openidWrapper);
        if (marketingWxMember == null) {
            throw new SuperCodeExtException("找不到该openid对应的用户信息"+openid);
        }
        Long memberId = marketingWxMember.getMemberId();
        if (memberId == null) {
            throw new SuperCodeExtException("找不到该openid对应的用户信息"+openid);
        }
        MarketingMembers member = marketingMembersMapper.selectById(memberId);
        if (member == null) {
            throw new SuperCodeExtException("找不到对应的用户信息");
        }
        H5LoginVO h5LoginVO=new H5LoginVO();
        h5LoginVO.setMemberType((byte)0);
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
        String jwtToken= JWTUtil.createTokenWithClaim(h5LoginVO);
        Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
        // jwt有效期为2小时，保持一致
        jwtTokenCookie.setMaxAge(60*60*2);
        // 待补充： 其他参数基于传递状况
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain(cookieDomain);
        response.addCookie(jwtTokenCookie);
        response.addHeader("Access-Control-Allow-Origin", "");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");

        MarketingActivityProduct marketingActivityProduct = marketingActivityProductMapper.selectByProductAndProductBatchIdWithReferenceRole(codeUserDto.getProductId(), codeUserDto.getProductBatchId(), codeUserDto.getClientRole().byteValue());
        if (marketingActivityProduct == null) {
            throw new SuperCodeExtException("找不到该产品对应的活动" + JSON.toJSONString(marketingActivityProduct));
        }
        ScanCodeInfoMO scanCodeInfoMO = new ScanCodeInfoMO();
        scanCodeInfoMO.setCodeId(codeUserDto.getOuterCode());
        scanCodeInfoMO.setCodeTypeId(codeUserDto.getCodeType());
        scanCodeInfoMO.setOrganizationId(codeUserDto.getOrganizationId());
        scanCodeInfoMO.setActivitySetId(marketingActivityProduct.getActivitySetId());
        scanCodeInfoMO.setUserId(memberId);
        scanCodeInfoMO.setMobile(member.getMobile());
        scanCodeInfoMO.setOpenId(openid);
        scanCodeInfoMO.setProductBatchId(codeUserDto.getProductBatchId());
        scanCodeInfoMO.setProductId(codeUserDto.getProductId());
        scanCodeInfoMO.setSbatchId(codeUserDto.getSbatchId());
        Date nowDate = new Date();
        scanCodeInfoMO.setScanCodeTime(nowDate);
        scanCodeInfoMO.setCreateTime(DateUtil.dateFormat(nowDate, "yyyy-MM-dd HH:mm:ss"));
        scanCodeInfoMO.setProductName(codeUserDto.getProductName());
        String wxstate = commonUtil.getUUID();
        globalRamCache.putScanCodeInfoMO(wxstate, scanCodeInfoMO);

        return RestResult.successWithData(wxstate);

    }


    @PostMapping("/jwtToken/saler")
    public RestResult<Void> getWxstateSaler(SalerUserDto salerUserDto, HttpServletResponse response) throws SuperCodeException {
        String openid = salerUserDto.getOpenid();
        Long memberId = salerUserDto.getMemberId();
        MarketingUser user = marketingUserMapper.selectById(memberId);
        if (user == null) {
            throw new SuperCodeExtException("找不到对应的用户信息");
        }
        String organizationId = user.getOrganizationId();
        QueryWrapper<MarketingWxMember> openidWrapper = Wrappers.<MarketingWxMember>query().eq("MemberId", memberId).eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.SALER.getType());
        MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(openidWrapper);
        if (marketingWxMember == null) {
            throw new SuperCodeExtException("找不到该openid对应的用户信息"+openid);
        }
        H5LoginVO h5LoginVO=new H5LoginVO();
        h5LoginVO.setMemberType((byte)1);
        h5LoginVO.setCustomerId(user.getCustomerId());
        h5LoginVO.setCustomerName(user.getCustomerName());
        h5LoginVO.setHaveIntegral(user.getHaveIntegral());
        h5LoginVO.setMemberId(memberId);
        h5LoginVO.setMobile(user.getMobile());
        h5LoginVO.setWechatHeadImgUrl(marketingWxMember.getWechatHeadImgUrl());
        h5LoginVO.setMemberName(StringUtils.isEmpty(user.getUserName())?marketingWxMember.getWxName():user.getUserName());
        h5LoginVO.setOrganizationId(user.getOrganizationId());
        h5LoginVO.setOpenid(marketingWxMember.getOpenid());
        h5LoginVO.setOrganizationName(marketingWxMember.getOrganizationFullName());
        String jwtToken= JWTUtil.createTokenWithClaim(h5LoginVO);
        Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
        // jwt有效期为2小时，保持一致
        jwtTokenCookie.setMaxAge(60*60*2);
        // 待补充： 其他参数基于传递状况
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain(cookieDomain);
        response.addCookie(jwtTokenCookie);
        response.addHeader("Access-Control-Allow-Origin", "");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");
        return RestResult.success();

    }


//    private MarketingWxMerchants getMerchantsByOrgId(String organizationId){
//        MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsService.selectByOrganizationId(organizationId);
//        if (marketingWxMerchants == null) {
//            log.error("找不到组织id为" + organizationId + "对应的微信信息");
//            throw new SuperCodeExtException("找不到该组织对应的微信信息");
//        }
//        String organizationName = marketingWxMerchants.getOrganizatioIdlName();
//        if (marketingWxMerchants.getMerchantType() == 1) {
//            if (marketingWxMerchants.getJgwId() != null) {
//                marketingWxMerchants = marketingWxMerchantsService.getJgw(marketingWxMerchants.getJgwId());
//            } else {
//                marketingWxMerchants = marketingWxMerchantsService.getDefaultJgw();
//            }
//        }
//        marketingWxMerchants.setOrganizatioIdlName(organizationName);
//        return marketingWxMerchants;
//    }

}
