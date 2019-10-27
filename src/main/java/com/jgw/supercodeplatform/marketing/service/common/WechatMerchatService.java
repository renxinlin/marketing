package com.jgw.supercodeplatform.marketing.service.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.common.UserOrgWechat;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatInfo;
import com.jgw.supercodeplatform.marketing.dto.common.UserWechatModel;
import com.jgw.supercodeplatform.marketing.mybatisplusdao.MarketingWxMerchantsExtMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchantsExt;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WechatMerchatService extends ServiceImpl<MarketingWxMerchantsMapper, MarketingWxMerchants>  {

    @Autowired
    private MarketingWxMerchantsExtMapper marketingWxMerchantsExtMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateMerchantList(MultipartFile files[], String detail) throws IOException {
        List<UserWechatModel> userWechatModelList = JSON.parseArray(detail, UserWechatModel.class);
        Map<String, byte[]> fileMap = new HashMap<>();
        if (ArrayUtils.isNotEmpty(files)) {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                byte[] fileBytes = file.getBytes();
                fileMap.put(fileName, fileBytes);
            }
        }
        List<MarketingWxMerchants> addMarketingWxMerchantsList = new ArrayList<>();
        List<MarketingWxMerchants> updateMarketingWxMerchantsList = new ArrayList<>();
        List<MarketingWxMerchantsExt> addMarketingWxMerchantsExtList = new ArrayList<>();
        List<MarketingWxMerchantsExt> updateMarketingWxMerchantsExtList = new ArrayList<>();
        userWechatModelList.stream().forEach(userWechatModel -> {
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
            //删掉无用的或者更改之前的默认使用为非默认
            Byte defaultUse = userWechatInfo.getDefaultUse();
            if (defaultUse != null && defaultUse.intValue() == 1) {
                String organizationId = userWechatInfo.getOrganizationId();
                MarketingWxMerchantsExt wxMerchantsExt = marketingWxMerchantsExtMapper.selectOne(Wrappers.<MarketingWxMerchantsExt>query().eq("organization_id", organizationId).eq("default_use", defaultUse));
                if (wxMerchantsExt != null) {
                    if (StringUtils.isBlank(wxMerchantsExt.getAppid())) {
                        marketingWxMerchantsExtMapper.deleteById(wxMerchantsExt.getId());
                    } else {
                        marketingWxMerchantsExtMapper.update(null, Wrappers.<MarketingWxMerchantsExt>update().set("default_use", (byte) 0).eq("organization_id", organizationId).eq("default_use", defaultUse));
                    }
                }
                MarketingWxMerchants wxMerchants = getOne(Wrappers.<MarketingWxMerchants>query().eq("organization_id", organizationId).eq("default_use", defaultUse));
                if (wxMerchants != null) {
                    if (StringUtils.isBlank(wxMerchants.getMchAppid())) {
                        removeById(wxMerchants.getId());
                    } else {
                        update(null, Wrappers.<MarketingWxMerchants>update().set("default_use", (byte) 0).eq("organization_id", organizationId).eq("default_use", defaultUse));
                    }
                }
            }
            if (userOrgWechat.getId() != null) {
                addMarketingWxMerchantsList.add(marketingWxMerchants);
            } else {
                updateMarketingWxMerchantsList.add(marketingWxMerchants);
            }
            MarketingWxMerchantsExt marketingWxMerchantsExt = new MarketingWxMerchantsExt();
            marketingWxMerchantsExt.setAppid(userOrgWechat.getAppId());
            marketingWxMerchantsExt.setBelongToJgw(userWechatInfo.getJgwType());
            marketingWxMerchantsExt.setAppid(userOrgWechat.getAppId());
            marketingWxMerchantsExt.setOrganizationId(userOrgWechat.getOrganizationId());
            marketingWxMerchantsExt.setOrganizatioIdlName(userOrgWechat.getOrganizationName());
            marketingWxMerchantsExt.setDefaultUse(userWechatInfo.getDefaultUse());
            marketingWxMerchantsExt.setCertificateInfo(fileMap.get(userWechatInfo.getCertificateAddress()));
            if (userOrgWechat.getId() != null) {
                addMarketingWxMerchantsExtList.add(marketingWxMerchantsExt);
            } else {
                updateMarketingWxMerchantsExtList.add(marketingWxMerchantsExt);
            }
            if (!addMarketingWxMerchantsList.isEmpty()) {
                saveBatch(addMarketingWxMerchantsList);
            }
            if (!updateMarketingWxMerchantsList.isEmpty()) {
                for (MarketingWxMerchants wxMerchants : updateMarketingWxMerchantsList) {
                    UpdateWrapper<MarketingWxMerchants> updateWrapper = Wrappers.<MarketingWxMerchants>update()
                            .eq("organization_id", wxMerchants.getOrganizationId()).eq("mch_appid", wxMerchants.getMchAppid());
                    update(wxMerchants, updateWrapper);
                }
            }
            if (!addMarketingWxMerchantsExtList.isEmpty()) {
                for (MarketingWxMerchantsExt wxMerchantsExt : addMarketingWxMerchantsExtList) {
                    marketingWxMerchantsExtMapper.insert(wxMerchantsExt);
                }
            }
            if (!updateMarketingWxMerchantsExtList.isEmpty()) {
                for (MarketingWxMerchantsExt wxMerchantsExt : updateMarketingWxMerchantsExtList) {
                    UpdateWrapper<MarketingWxMerchantsExt> updateWrapper = Wrappers.<MarketingWxMerchantsExt>update()
                            .eq("organization_id", wxMerchantsExt.getOrganizationId()).eq("appid", wxMerchantsExt.getAppid());
                    marketingWxMerchantsExtMapper.update(wxMerchantsExt, updateWrapper);
                }
            }
        });
    }

    public void useJgw(String organizationId, String organizationName, String jgwAppid){
        QueryWrapper<MarketingWxMerchants> merchantQuery = Wrappers.<MarketingWxMerchants>query()
                .eq("organization_id", organizationId).eq("default_use", (byte)1);
        MarketingWxMerchants marketingWxMerchants = getOne(merchantQuery);
        if (marketingWxMerchants != null) {
            UpdateWrapper<MarketingWxMerchants> merchantUpdate = Wrappers.<MarketingWxMerchants>update().set("merchantType", (byte)1)
                    .eq("organization_id", organizationId).eq("default_use", (byte)1);
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
                .eq("organization_id", organizationId).eq("default_use", (byte)1);
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
        remove(Wrappers.<MarketingWxMerchants>query().eq("organization_id", organizationId).eq("mch_appid", appid));
        marketingWxMerchantsExtMapper.delete(Wrappers.<MarketingWxMerchantsExt>query().eq("organization_id", organizationId).eq("appid", appid));
    }


}
