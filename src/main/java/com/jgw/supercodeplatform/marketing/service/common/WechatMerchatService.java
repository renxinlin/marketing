package com.jgw.supercodeplatform.marketing.service.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.common.UserOrgWechat;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatInfo;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatModel;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WechatMerchatService extends ServiceImpl<MarketingWxMerchantsMapper, MarketingWxMerchants>  {

    @Autowired
    private MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper;
    @Autowired
    private GlobalRamCache globalRamCache;

    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateMerchantList(String detail) throws IOException {
        List<UserWechatModel> userWechatModelList = JSON.parseArray(detail, UserWechatModel.class);
//        List<MarketingWxMerchants> addMarketingWxMerchantsList = new ArrayList<>();
//        List<MarketingWxMerchants> updateMarketingWxMerchantsList = new ArrayList<>();
//        List<MarketingWxMerchantsExt> addMarketingWxMerchantsExtList = new ArrayList<>();
//        List<MarketingWxMerchantsExt> updateMarketingWxMerchantsExtList = new ArrayList<>();
        for (UserWechatModel userWechatModel : userWechatModelList) {
            UserOrgWechat userOrgWechat = userWechatModel.getUserOrgWechat();
            UserWechatInfo userWechatInfo = userWechatModel.getUserWechatInfo();
            MarketingWxMerchants marketingWxMerchants = new MarketingWxMerchants();
            marketingWxMerchants.setOrganizatioIdlName(userOrgWechat.getOrganizationName());
            marketingWxMerchants.setDefaultUse(userWechatInfo.getDefaultUse());
            marketingWxMerchants.setMchAppid(userOrgWechat.getAppId());
            marketingWxMerchants.setBelongToJgw(userWechatInfo.getJgwType());
            marketingWxMerchants.setCertificateAddress(userWechatInfo.getCertificateAddress());
            marketingWxMerchants.setCertificatePassword(userWechatInfo.getCertificatePassword());
            marketingWxMerchants.setMchid(userWechatInfo.getMchId());
            marketingWxMerchants.setMerchantKey(userWechatInfo.getMerchantKey());
            marketingWxMerchants.setMerchantName(userWechatInfo.getMerchantName());
            marketingWxMerchants.setMerchantType(userWechatInfo.getMerchantType());
            marketingWxMerchants.setMerchantSecret(userOrgWechat.getSecret());
            marketingWxMerchants.setOrganizationId(userOrgWechat.getOrganizationId());
            globalRamCache.delWXMerchants(userOrgWechat.getOrganizationId());
            //删掉无用的或者更改之前的默认使用为非默认
            String organizationId = userWechatInfo.getOrganizationId();
//            Byte merchatType = userWechatInfo.getMerchantType();
            Byte defaultUse = userWechatInfo.getDefaultUse();
            if (defaultUse.intValue() == 1) {
//                MarketingWxMerchantsExt wxMerchantsExt = marketingWxMerchantsExtMapper.selectOne(Wrappers.<MarketingWxMerchantsExt>query().eq("organizationId", organizationId).eq("defaultUse", defaultUse));
                update(null, Wrappers.<MarketingWxMerchants>update().set("defaultUse", (byte) 0).eq("organizationId", organizationId).eq("defaultUse", defaultUse));
                marketingWxMerchantsExtMapper.update(null, Wrappers.<MarketingWxMerchantsExt>update().set("defaultUse", (byte) 0).eq("organizationId", organizationId).eq("defaultUse", defaultUse));
//                if (wxMerchantsExt != null) {
//                    if (StringUtils.isBlank(wxMerchantsExt.getAppid())) {
//                        marketingWxMerchantsExtMapper.deleteById(wxMerchantsExt.getId());
//                    } else {
//                        marketingWxMerchantsExtMapper.update(null, Wrappers.<MarketingWxMerchantsExt>update().set("defaultUse", (byte) 0).eq("organizationId", organizationId).eq("defaultUse", defaultUse));
//                    }
//                }
//                MarketingWxMerchants wxMerchants = getOne(Wrappers.<MarketingWxMerchants>query().eq("organizationId", organizationId).eq("defaultUse", defaultUse));
//                if (wxMerchants != null) {
//                    if (StringUtils.isBlank(wxMerchants.getMchAppid())) {
//                        removeById(wxMerchants.getId());
//                    } else {
//                        update(null, Wrappers.<MarketingWxMerchants>update().set("defaultUse", (byte) 0).eq("organizationId", organizationId).eq("defaultUse", defaultUse));
//                    }
//                }
            }
//            if (merchatType.intValue() == 1) {
//                MarketingWxMerchants wxMerchants = getOne(Wrappers.<MarketingWxMerchants>query().eq("organizationId", organizationId).eq("defaultUse", (byte)1));
//                if (wxMerchants == null) {
//                    save(marketingWxMerchants);
//                } else {
//                    marketingWxMerchants.setId(wxMerchants.getId());
//                    updateById(marketingWxMerchants);
//                }
//                MarketingWxMerchantsExt wxMerchantsExt = marketingWxMerchantsExtMapper.selectOne(Wrappers.<MarketingWxMerchantsExt>query().eq("organizationId", organizationId).eq("defaultUse", (byte)1));
//            }
//            if (userOrgWechat.getId() != null) {
//                addMarketingWxMerchantsList.add(marketingWxMerchants);
//            } else {
//                updateMarketingWxMerchantsList.add(marketingWxMerchants);
//            }
            MarketingWxMerchantsExt marketingWxMerchantsExt = new MarketingWxMerchantsExt();
            marketingWxMerchantsExt.setAppid(userOrgWechat.getAppId());
            marketingWxMerchantsExt.setBelongToJgw(userWechatInfo.getJgwType());
            marketingWxMerchantsExt.setAppid(userOrgWechat.getAppId());
            marketingWxMerchantsExt.setOrganizationId(userOrgWechat.getOrganizationId());
            marketingWxMerchantsExt.setOrganizatioIdlName(userOrgWechat.getOrganizationName());
            marketingWxMerchantsExt.setDefaultUse(userWechatInfo.getDefaultUse());
            String fileName = userWechatInfo.getCertificateAddress();
            if (StringUtils.isNotBlank(fileName)) {
                byte[] fileBytes = FileUtils.readFileToByteArray(new File(fileName));
                marketingWxMerchantsExt.setCertificateInfo(fileBytes);
            }
            MarketingWxMerchants merchants = getOne(Wrappers.<MarketingWxMerchants>query().eq("platform_id", userOrgWechat.getId()));
            if (merchants == null) {
                save(marketingWxMerchants);
                marketingWxMerchantsExtMapper.insert(marketingWxMerchantsExt);
            } else {
                marketingWxMerchants.setId(merchants.getId());
                updateById(marketingWxMerchants);
                MarketingWxMerchantsExt merchantsExt = marketingWxMerchantsExtMapper.selectOne(Wrappers.<MarketingWxMerchantsExt>query().eq("OrganizationId", merchants.getOrganizationId()).eq("Appid", merchants.getMchAppid()));
                if (merchantsExt == null) {
                    marketingWxMerchantsExtMapper.insert(marketingWxMerchantsExt);
                } else {
                    marketingWxMerchantsExt.setId(merchantsExt.getId());
                    marketingWxMerchantsExtMapper.updateById(marketingWxMerchantsExt);
                }
            }
//
//
//
//
//            if (userOrgWechat.getId() != null) {
//                addMarketingWxMerchantsExtList.add(marketingWxMerchantsExt);
//            } else {
//                updateMarketingWxMerchantsExtList.add(marketingWxMerchantsExt);
//            }
//            if (!addMarketingWxMerchantsList.isEmpty()) {
//                saveBatch(addMarketingWxMerchantsList);
//            }
//            if (!updateMarketingWxMerchantsList.isEmpty()) {
//                for (MarketingWxMerchants wxMerchants : updateMarketingWxMerchantsList) {
//                    UpdateWrapper<MarketingWxMerchants> updateWrapper = Wrappers.<MarketingWxMerchants>update()
//                            .eq("organizationId", wxMerchants.getOrganizationId()).eq("mchAppid", wxMerchants.getMchAppid());
//                    update(wxMerchants, updateWrapper);
//                }
//            }
//            if (!addMarketingWxMerchantsExtList.isEmpty()) {
//                for (MarketingWxMerchantsExt wxMerchantsExt : addMarketingWxMerchantsExtList) {
//                    marketingWxMerchantsExtMapper.insert(wxMerchantsExt);
//                }
//            }
//            if (!updateMarketingWxMerchantsExtList.isEmpty()) {
//                for (MarketingWxMerchantsExt wxMerchantsExt : updateMarketingWxMerchantsExtList) {
//                    UpdateWrapper<MarketingWxMerchantsExt> updateWrapper = Wrappers.<MarketingWxMerchantsExt>update()
//                            .eq("organizationId", wxMerchantsExt.getOrganizationId()).eq("appid", wxMerchantsExt.getAppid());
//                    marketingWxMerchantsExtMapper.update(wxMerchantsExt, updateWrapper);
//                }
//            }
        }
    }

    public void useJgw(String organizationId, String organizationName, String jgwAppid){
        globalRamCache.delWXMerchants(organizationId);
        QueryWrapper<MarketingWxMerchants> merchantQuery = Wrappers.<MarketingWxMerchants>query()
                .eq("organizationId", organizationId).eq("defaultUse", (byte)1);
        MarketingWxMerchants marketingWxMerchants = getOne(merchantQuery);
        if (marketingWxMerchants != null) {
            UpdateWrapper<MarketingWxMerchants> merchantUpdate = Wrappers.<MarketingWxMerchants>update().set("merchantType", (byte)1)
                    .eq("organizationId", organizationId).eq("defaultUse", (byte)1);
            update(null, merchantUpdate);
        } else {
            marketingWxMerchants = new MarketingWxMerchants();
            marketingWxMerchants.setOrganizationId(organizationId);
            marketingWxMerchants.setOrganizatioIdlName(organizationName);
            marketingWxMerchants.setMchAppid("");
            marketingWxMerchants.setDefaultUse((byte)1);
            marketingWxMerchants.setBelongToJgw((byte)0);
            marketingWxMerchants.setMerchantType((byte)1);
            save(marketingWxMerchants);
        }

        QueryWrapper<MarketingWxMerchantsExt> merchantExtQuery = Wrappers.<MarketingWxMerchantsExt>query()
                .eq("organizationId", organizationId).eq("defaultUse", (byte)1);
        MarketingWxMerchantsExt marketingWxMerchantsExt = marketingWxMerchantsExtMapper.selectOne(merchantExtQuery);
        if (marketingWxMerchantsExt == null) {
            marketingWxMerchantsExt = new MarketingWxMerchantsExt();
            marketingWxMerchantsExt.setOrganizationId(organizationId);
            marketingWxMerchantsExt.setOrganizatioIdlName(organizationName);
            marketingWxMerchantsExt.setAppid("");
            marketingWxMerchantsExt.setDefaultUse((byte)1);
            marketingWxMerchantsExt.setBelongToJgw((byte)0);
            save(marketingWxMerchants);
        }

    }

    public void delMerchant(String organizationId, String appid) {
        globalRamCache.delWXMerchants(organizationId);
        remove(Wrappers.<MarketingWxMerchants>query().eq("organizationId", organizationId).eq("mchAppid", appid));
        marketingWxMerchantsExtMapper.delete(Wrappers.<MarketingWxMerchantsExt>query().eq("organizationId", organizationId).eq("appid", appid));
    }

}
