package com.jgw.supercodeplatform.marketing.service.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPlatformOrganizationMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithUser;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.JoinOrganization;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.PrizeType;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityDisable;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityUpdate;
import com.jgw.supercodeplatform.marketing.dto.platform.ProductInfoDto;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.platform.AbandonPlatform;
import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformActivityVo;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformOrganizationDataVo;
import com.jgw.supercodeplatform.marketing.vo.platform.PlatformScanStatusVo;
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
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private CodeEsService odeEsService;

    @Override
    protected List<PlatformActivityVo> searchResult(DaoSearchWithUser searchParams) throws Exception {
        searchParams.setStartNumber((searchParams.getCurrent()-1)*searchParams.getPageSize());
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
        validConditionJson.put("maxJoinNum", platformActivityAdd.getMaxJoinNum());
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
                    marketingPrizeType.setRemainingStock(prizeType.getRemainingNumber());
                    if (prizeType.getAwardGrade().intValue() == 0) {
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
            platformActivityUpdate.setMaxJoinNum(validConditionJson.getInteger("maxJoinNum"));
        }
        List<PrizeType> prizeTypeList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(marketingPrizeTypeList)) {
            prizeTypeList = marketingPrizeTypeList.stream().filter(pr -> pr.getAwardGrade() != null && pr.getAwardGrade() > 0).map(marketingPrizeType -> {
                PrizeType prizeType = new PrizeType();
                BeanUtils.copyProperties(marketingPrizeType, prizeType);
                prizeType.setPrizeAmount(new BigDecimal(marketingPrizeType.getPrizeAmount().toString()));
                prizeType.setRemainingNumber(marketingPrizeType.getRemainingStock());
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
            if (StringUtils.isNotBlank(result) && JSON.parseObject(result).getIntValue("state") == HttpStatus.SC_OK) {
                PageResults<List<Object>> pageMapResults = JSON.parseObject(result).getObject("results", PageResults.class);
                int total = pageMapResults.getPagination().getTotal();
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

    /**
     * 放弃抽奖时调用
     * @param abandonPlatform
     */
    public void addAbandonPlatform(String innerCode, AbandonPlatform abandonPlatform, ProductInfoDto productInfoDto) {
        MarketingActivitySet marketingActivitySet = mSetMapper.getOnlyPlatformActivity();
        if (marketingActivitySet == null) {
            throw new SuperCodeExtException("当前暂无全网运营红包上线", 200);
        }
        MarketingPlatformOrganization marketingPlatformOrganization = marketingPlatformOrganizationMapper.selectByActivitySetIdAndOrganizationId(marketingActivitySet.getId(), abandonPlatform.getOrganizationId());
        if (marketingPlatformOrganization == null) {
            throw new SuperCodeExtException("当前组织没有参加全网运营活动", 200);
        }
        abandonPlatform.setOrganizationFullName(marketingPlatformOrganization.getOrganizationFullName());
        odeEsService.addAbandonPlatformScanCodeRecord(productInfoDto, innerCode, abandonPlatform.getProductId(), abandonPlatform.getProductBatchId(), abandonPlatform.getCodeId(),
                marketingActivitySet.getActivityId(),abandonPlatform.getCodeType(), marketingActivitySet.getId(),System.currentTimeMillis(),abandonPlatform.getOrganizationId(), abandonPlatform.getOrganizationFullName());
    }


    /**
     * 获取扫码状态
     * @param
     * @return
     */
    public PlatformScanStatusVo getScanStatus(String innerCode, String organizationId) {
        PlatformScanStatusVo platformScanStatusVo = new PlatformScanStatusVo(false, false);
        MarketingActivitySet marketingActivitySet = mSetMapper.getOnlyPlatformActivity();
        if (marketingActivitySet == null) {
            //当前不存在全网运营活动，不可用
            return platformScanStatusVo;
        }
        MarketingPlatformOrganization marketingPlatformOrganization = marketingPlatformOrganizationMapper.selectByActivitySetIdAndOrganizationId(marketingActivitySet.getId(), organizationId);
        if (marketingPlatformOrganization == null) {
            //当前组织未参加活动，不可用
            return platformScanStatusVo;
        }
        platformScanStatusVo.setPlatformStatus(true);
        long count = odeEsService.countPlatformScanCodeRecord(innerCode, null);
        if (count > 0) {
            //码已经被扫过，不可用
            return platformScanStatusVo;
        }
        platformScanStatusVo.setScanStatus(true);
        return platformScanStatusVo;

    }


    /**
     * 设置活动状态
     * @param platformActivityDisable
     * @return
     */
    public boolean updatePlatformStatus (PlatformActivityDisable platformActivityDisable){
        MarketingActivitySet mas = mSetMapper.selectById(platformActivityDisable.getId());
        if (mas == null) {
            throw new SuperCodeExtException("找不到该ID对应的活动");
        }
        MarketingActivitySet marketingActivitySet = mSetMapper.getOnlyPlatformActivity();
        if (platformActivityDisable.getActivityStatus().intValue() == 1 && marketingActivitySet != null){
            return false;
        }
        MarketingActivitySetStatusUpdateParam mUpdateStatus = new MarketingActivitySetStatusUpdateParam();
        mUpdateStatus.setActivitySetId(platformActivityDisable.getId());
        mUpdateStatus.setActivityStatus(platformActivityDisable.getActivityStatus());
        mSetMapper.updateActivitySetStatus(mUpdateStatus);
        return true;
    }

}
