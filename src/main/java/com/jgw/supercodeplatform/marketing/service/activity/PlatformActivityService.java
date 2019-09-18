package com.jgw.supercodeplatform.marketing.service.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPlatformOrganizationMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithUser;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.JoinOrganization;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.PrizeType;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityUpdate;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformActivityVo;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformOrganizationDataVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlatformActivityService extends AbstractPageService<DaoSearchWithUser> {

    @Value("${rest.user.url}")
    private String userUrl;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

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

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected List<PlatformActivityVo> searchResult(DaoSearchWithUser searchParams) throws Exception {
        return mSetMapper.listPlatform(searchParams);
    }

    @Override
    protected int count(DaoSearchWithUser searchParams) throws Exception {
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

    public PageResults<List<PlatformOrganizationDataVo>> platformOrganization(DaoSearch daoSearch) {
        PageResults<List<PlatformOrganizationDataVo>> pageResults = new PageResults<>();
        Map<String, Object> params = new HashMap<>();
        params.put("current", daoSearch.getCurrent());
        params.put("pageSize", daoSearch.getPageSize());
        params.put("search", daoSearch.getSearch());
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("super-token", commonUtil.getSuperToken());
        ResponseEntity<String> entity = restTemplateUtil.getRequestAndReturnJosn(userUrl+ WechatConstants.ORG_LIST_PLATFORM, params, headerMap);
        if (entity != null && entity.getStatusCodeValue() == HttpStatus.SC_OK){
            String result = entity.getBody();
            if (org.apache.commons.lang.StringUtils.isNotBlank(result) && JSON.parseObject(result).getIntValue("state") == HttpStatus.SC_OK) {
                PageResults<List<Object>> pageMapResults = JSON.parseObject(result).getObject("results", PageResults.class);
                List<PlatformOrganizationDataVo> resList = pageMapResults.getList().stream().map(obj -> {
                    Map sorMap = (Map)obj;
                    String organizationId = (String) sorMap.get("organizationId");
                    String organizationName = (String)sorMap.get("organizationFullName");
                    PlatformOrganizationDataVo platformOrganizationDataVo = new PlatformOrganizationDataVo();
                    platformOrganizationDataVo.setOrganizationId(organizationId);
                    platformOrganizationDataVo.setOrganizationFullName(organizationName);
                    return platformOrganizationDataVo;
                }).collect(Collectors.toList());
                pageResults.setList(resList);
                pageResults.setPagination(pageMapResults.getPagination());
            }
        }
        return pageResults;
    }


    /**
     * 活动预览
     * @param platformActivityAdd
     * @return 预览的key
     */
    public String preView(PlatformActivityAdd platformActivityAdd){
        String key = commonUtil.getUUID();
        String value = JSON.toJSONString(platformActivityAdd);
        boolean flag = redisUtil.set(key, value, 60L);
        if (flag) {
           return key;
        }
        return null;
    }

    /**
     * 获取预览数据
     * @param key
     * @return
     */
    public PlatformActivityAdd getPreViewData(String key){
        String value = redisUtil.get(key);
        if (StringUtils.isNotBlank(value)) {
            PlatformActivityAdd paa = JSON.parseObject(value, PlatformActivityAdd.class);
            return paa;
        }
        return null;
    }

}
