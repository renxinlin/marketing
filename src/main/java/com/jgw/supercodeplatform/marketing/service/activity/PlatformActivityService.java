package com.jgw.supercodeplatform.marketing.service.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPlatformOrganizationMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.*;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityUpdate;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformActivityVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlatformActivityService extends AbstractPageService<DaoSearchWithOrganizationIdParam> {

    @Autowired
    private MarketingActivitySetMapper mSetMapper;

    @Autowired
    private MarketingPrizeTypeMapper mPrizeTypeMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MarketingPlatformOrganizationMapper marketingPlatformOrganizationMapper;

    @Autowired
    private MarketingPlatformOrganizationService marketingPlatformOrganizationService;

    @Override
    protected List<PlatformActivityVo> searchResult(DaoSearchWithOrganizationIdParam searchParams) throws Exception {
        return mSetMapper.listPlatform(searchParams);
    }

    @Override
    protected int count(DaoSearchWithOrganizationIdParam searchParams) throws Exception {
        return mSetMapper.countPlatform(searchParams);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdatePlatformActivitySet(PlatformActivityAdd platformActivityAdd){
        MarketingActivitySet marketingActivitySet = new MarketingActivitySet();
        BeanUtils.copyProperties(platformActivityAdd, marketingActivitySet);
        JSONObject validConditionJson = new JSONObject();
        validConditionJson.put("eachDayNumber", platformActivityAdd.getMaxJoinNum());
        validConditionJson.put("sourceLink", platformActivityAdd.getSourceLink());
        marketingActivitySet.setValidCondition(validConditionJson.toJSONString());
        marketingActivitySet.setOrganizationId(commonUtil.getOrganizationId());
        marketingActivitySet.setOrganizatioIdlName(commonUtil.getOrganizationName());
        marketingActivitySet.setActivityStatus(1);
        marketingActivitySet.setActivityStartDate(DateFormatUtils.format(platformActivityAdd.getActivityStartDate(), "yyyy-MM-dd"));
        marketingActivitySet.setActivityEndDate(DateFormatUtils.format(platformActivityAdd.getActivityEndDate(), "yyyy-MM-dd"));
        marketingActivitySet.setUpdateUserId(commonUtil.getUserLoginCache().getUserId());
        marketingActivitySet.setUpdateUserName(commonUtil.getUserLoginCache().getUserName());
        if (platformActivityAdd instanceof PlatformActivityUpdate) {
            PlatformActivityUpdate platformActivityUpdate = (PlatformActivityUpdate)platformActivityAdd;
            Long activityId = platformActivityUpdate.getId();
            marketingActivitySet.setId(activityId);
            mSetMapper.update(marketingActivitySet);
            mPrizeTypeMapper.deleteByActivitySetId(activityId);
            marketingPlatformOrganizationMapper.delteByActivitySetId(activityId);
        } else {
            mSetMapper.insert(marketingActivitySet);
        }
        //中奖奖次列表转换
        List<MarketingPrizeType> marketingPrizeTypeList = platformActivityAdd.getPrizeTypeList()
                .stream().map(prizeType -> {
                    MarketingPrizeType marketingPrizeType = new MarketingPrizeType();
                    BeanUtils.copyProperties(prizeType, marketingPrizeType);
                    marketingPrizeType.setPrizeAmount(prizeType.getPrizeAmount().floatValue());
                    if (prizeType.getAwardGrade() == null) {
                        marketingPrizeType.setRealPrize((byte)0);
                    } else {
                        marketingPrizeType.setRealPrize((byte)1);
                    }
                    marketingPrizeType.setIsRrandomMoney((byte)0);
                    marketingPrizeType.setActivitySetId(marketingActivitySet.getId());
                    return marketingPrizeType;
                }).collect(Collectors.toList());
        //使用公司列表转换
        List<MarketingPlatformOrganization> platformOrganizationList = platformActivityAdd.getJoinOrganizationList()
                .stream().map(joinOrganization -> {
                    MarketingPlatformOrganization platformOrganization = new MarketingPlatformOrganization();
                    BeanUtils.copyProperties(joinOrganization, platformOrganization);
                    platformOrganization.setActivitySetId(marketingActivitySet.getId());
                    return platformOrganization;
                }).collect(Collectors.toList());
        mPrizeTypeMapper.batchInsert(marketingPrizeTypeList);
        marketingPlatformOrganizationService.insertPlatformOrganizationList(platformOrganizationList);
    }


    public PlatformActivityUpdate getActivityBySetId(Long activitySetId) throws ParseException {
        MarketingActivitySet marketingActivitySet = mSetMapper.selectById(activitySetId);
        if (marketingActivitySet == null) {
            throw new SuperCodeExtException("找到不到该ID对应的活动");
        }
        List<MarketingPrizeType> marketingPrizeTypeList = mPrizeTypeMapper.selectByActivitySetId(activitySetId);
        List<MarketingPlatformOrganization> marketingPlatformOrganizationList = marketingPlatformOrganizationMapper.selectByActivitySetId(activitySetId);
        PlatformActivityUpdate platformActivityUpdate = new PlatformActivityUpdate();
        BeanUtils.copyProperties(marketingActivitySet,platformActivityUpdate);
        platformActivityUpdate.setActivityStartDate(DateUtils.parseDate(marketingActivitySet.getActivityStartDate(), "yyyy-MM-dd"));
        platformActivityUpdate.setActivityEndDate(DateUtils.parseDate(marketingActivitySet.getActivityEndDate(), "yyyy-MM-dd"));
        String validation = marketingActivitySet.getValidCondition();
        if (StringUtils.isNotBlank(validation)) {
            JSONObject validConditionJson = JSON.parseObject(validation);
            platformActivityUpdate.setSourceLink(validConditionJson.getString("sourceLink"));
            platformActivityUpdate.setMaxJoinNum(validConditionJson.getInteger("eachDayNumber"));
        }
        List<PrizeType> prizeTypeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(marketingPrizeTypeList)) {
            prizeTypeList = marketingPrizeTypeList.stream().filter(pr -> pr.getAwardGrade() != null).map(marketingPrizeType -> {
                PrizeType prizeType = new PrizeType();
                BeanUtils.copyProperties(marketingPrizeType, prizeType);
                prizeType.setPrizeAmount(new BigDecimal(marketingPrizeType.getPrizeAmount()));
                return prizeType;
            }).collect(Collectors.toList());
        }
        platformActivityUpdate.setPrizeTypeList(prizeTypeList);
        List<JoinOrganization> joinOrganizationList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(marketingPlatformOrganizationList)) {
            joinOrganizationList = marketingPlatformOrganizationList.stream().map(marketingPlatformOrganization -> {
                JoinOrganization joinOrganization = new JoinOrganization();
                BeanUtils.copyProperties(marketingPlatformOrganization, joinOrganization);
                return joinOrganization;
            }).collect(Collectors.toList());
        }
        platformActivityUpdate.setJoinOrganizationList(joinOrganizationList);
        return platformActivityUpdate;
    }


}
