package com.jgw.supercodeplatform.marketing.service.activity.coupon;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.ActivityDefaultConstant;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.coupon.MarketingCouponMapperExt;
import com.jgw.supercodeplatform.marketing.dto.codemanagerservice.CouponActivity;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponAddParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingCouponAmoutAndDateVo;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingCouponVo;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityStatusEnum;
import com.jgw.supercodeplatform.marketing.enums.market.AutoGetEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.*;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 优惠券活动新增查看service
 */
@Service
public class CouponService {
    /**
     * 批次ID中的分隔符
     */
    private static final String SPILT ="," ;
    private static Logger logger = LoggerFactory.getLogger(CouponService.class);

    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

    @Value("${rest.codemanager.url}")
    private String codeManagerUrl;

    @Value("${marketing.domain.url}")
    private String marketingDomain;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private MarketingActivitySetMapper setMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CommonService commonService;

    @Autowired
    private MarketingActivityProductMapper productMapper;

    @Autowired
    private MarketingCouponMapperExt couponMapper;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MarketingChannelMapper channelMapper;


    /**
     * 抵扣券查看详情
     * @param id
     * @return
     */
    public RestResult<MarketingActivityCouponUpdateParam> detail(Long activitySetId) throws SuperCodeException{
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            throw new SuperCodeException("活动id不存在...");
        }

        MarketingActivityCouponUpdateParam marketingActivityCouponUpdateParam = new MarketingActivityCouponUpdateParam();
        // 活动设置信息
        setActivitySetInfoToVo(activitySetId,marketingActivityCouponUpdateParam);
        // 规则信息
        setCouponRuleInfoToVo(activitySetId,marketingActivityCouponUpdateParam);
        // 渠道信息
        setChannelInfoToVo(activitySetId,marketingActivityCouponUpdateParam);
        // 产品信息
        setProductInfoToVo(activitySetId,marketingActivityCouponUpdateParam);

        return RestResult.success("success",marketingActivityCouponUpdateParam);
    }

    /**
     * 优惠券活动新增
     * @param addVO
     * @return
     * @throws SuperCodeException
     */
    @Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
    public RestResult<String> add(MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        // 组织信息
        // 入参校验
        validateBasicByAdd(addVO);
        // 业务校验以及保存前处理
        validateBizByAdd(addVO);

        // 返回主键
        MarketingActivitySet activitySet = changeVoToDtoForMarketingActivitySet(addVO);
        setMapper.insert(activitySet);

        // 保存渠道 TODO copy 以前活动的代码 检查有没有坑
        saveChannels(addVO.getChannelParams(),activitySet.getId());

        // 保存产品 TODO copy 以前活动的代码 检查有没有坑 以及处理如何保存到码管理来确保领券问题
        boolean send = false;
        if(addVO.getAcquireCondition().intValue() == CouponAcquireConditionEnum.SHOPPING.getCondition().intValue() ){
            send = true;
        }
        saveProductBatchs(addVO.getProductParams(),null,activitySet.getId(),addVO.getAutoFetch(),send);
        // 保存抵扣券规则
        saveCouponRules(addVO.getCouponRules(),activitySet.getId());

        return RestResult.success();
    }

    private void saveCouponRules(MarketingCouponVo couponRules, Long activitySetId) {
        List<MarketingCoupon> toDbEntitys = new ArrayList<>(5);
        List<MarketingCouponAmoutAndDateVo> couponAmoutAndDateVo = couponRules.getCouponAmoutAndDateVo();
        for(MarketingCouponAmoutAndDateVo vo: couponAmoutAndDateVo){
            MarketingCoupon toDbEntity = new MarketingCoupon();
            toDbEntity.setActivitySetId(activitySetId);
            toDbEntity.setCouponAmount(vo.getCouponAmount());
            toDbEntity.setDeductionEndDate(vo.getDeductionEndDate());
            toDbEntity.setDeductionStartDate(vo.getDeductionStartDate());
            toDbEntity.setDeductionChannelType(couponRules.getDeductionChannelType());
            toDbEntity.setDeductionProductType(couponRules.getDeductionProductType());
             toDbEntitys.add(toDbEntity);
        }
        couponMapper.batchInsert(toDbEntitys);


    }


    private void saveProductBatchs(List<MarketingActivityProductParam> maProductParams, List<Map<String,Object>> deleteProductBatchList, Long activitySetId, int autoFecth,boolean send) throws SuperCodeException {
        List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = new ArrayList<ProductAndBatchGetCodeMO>();
        List<MarketingActivityProduct> mList = new ArrayList<MarketingActivityProduct>();
        for (MarketingActivityProductParam marketingActivityProductParam : maProductParams) {
            String productId = marketingActivityProductParam.getProductId();
            List<ProductBatchParam> batchParams = marketingActivityProductParam.getProductBatchParams();
            if (null != batchParams && !batchParams.isEmpty()) {
                ProductAndBatchGetCodeMO productAndBatchGetCodeMO = new ProductAndBatchGetCodeMO();
                List<Map<String, String>> productBatchList = new ArrayList<Map<String, String>>();
                for (ProductBatchParam prBatchParam : batchParams) {
                    String productBatchId = prBatchParam.getProductBatchId();
                    MarketingActivityProduct mActivityProduct = new MarketingActivityProduct();
                    mActivityProduct.setActivitySetId(activitySetId);
                    mActivityProduct.setProductBatchId(productBatchId);
                    mActivityProduct.setProductBatchName(prBatchParam.getProductBatchName());
                    mActivityProduct.setProductId(marketingActivityProductParam.getProductId());
                    mActivityProduct.setProductName(marketingActivityProductParam.getProductName());
                    mActivityProduct.setReferenceRole(ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
                    mList.add(mActivityProduct);
                    // 拼装请求码管理批次信息接口商品批次参数
                    Map<String, String> batchmap = new HashMap<String, String>();
                    batchmap.put("productBatchId", prBatchParam.getProductBatchId());
                    productBatchList.add(batchmap);
                }
                // 拼装请求码管理批次信息接口商品参数
                productAndBatchGetCodeMO.setProductBatchList(productBatchList);
                productAndBatchGetCodeMO.setProductId(productId);
                productAndBatchGetCodeMOs.add(productAndBatchGetCodeMO);
            }
        }
        // 营销绑定生码批次
        if (autoFecth == AutoGetEnum.BY_NOT_AUTO.getAuto()) {
            String superToken = commonUtil.getSuperToken();
            String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,
                    WechatConstants.code_relation_getBatchInfoWithoutType);
            JSONObject obj = JSONObject.parseObject(body);
            int state = obj.getInteger("state");
            if (200 == state) {
                // 生码批次数组
                Map<String,Set<String> > productSbathIds = new HashMap<>();
                JSONArray arr = obj.getJSONArray("results");

                mList.forEach(marketingActivityProduct -> {
                    Set<String> sbathIds = new HashSet();
                    for(int i=0;i<arr.size();i++) {
                        String globalBacthId = arr.getJSONObject(i).getString("globalBacthId");
                        String productId = arr.getJSONObject(i).getString("productId");
                        String productBatchId = arr.getJSONObject(i).getString("productBatchId");
                        sbathIds.add(globalBacthId);
                        if(marketingActivityProduct.getProductId().equals(productId)
                                && marketingActivityProduct.getProductBatchId().equals(productBatchId)){
                            sbathIds.add(globalBacthId);
                            productSbathIds.put(productId+productBatchId,sbathIds);
                        }
                    }
                    Set<String> sbathIdsDto = productSbathIds.get(marketingActivityProduct.getProductId() + marketingActivityProduct.getProductBatchId());
                    if(!CollectionUtils.isEmpty(sbathIdsDto)){
                        String[] sbathIdsDtoArray = new String[sbathIdsDto.size()];
                        //Set-->数组
                        sbathIdsDto.toArray(sbathIdsDtoArray);
                        String sbatchId = StringUtils.join(sbathIdsDtoArray, SPILT);
                        marketingActivityProduct.setSbatchId(sbatchId);
                    }

                });
            } else {
                throw new SuperCodeException("通过产品及产品批次获取码信息错误：" + body, 500);
            }
        }

        // TODO 等待建强那边处理交互协议
       if(send){
       // TODO 处理优惠券获取
           /**
            * 删除原来覆盖的产品存于码管理的绑定信息
            * 在新增绑定
            * 由于覆盖式可忽略删除
            */
          List<CouponActivity>  couponActivitys = new ArrayList<>();
           mList.forEach(product ->{
               CouponActivity couponActivity = new CouponActivity();
               couponActivity.setProductId(product.getProductId());
               couponActivity.setProductBatchId(product.getProductBatchId());
               couponActivity.setStatus(BindCouponRelationToCodeManagerEnum.BIND.getBinding());
               couponActivitys.add(couponActivity);
           });
           String jsonData=JSONObject.toJSONString(couponActivitys);
           Map<String,String> headerMap=new HashMap<>();
           headerMap.put(ActivityDefaultConstant.superToken, commonUtil.getSuperToken());
           restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl, jsonData, headerMap);

       }
        //插入对应活动产品数据
        productMapper.batchDeleteByProBatchsAndRole(mList, ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
        productMapper.activityProductInsert(mList);
    }
    private void saveChannels(List<MarketingChannelParam> channelParams, Long activitySetId) {
        List<MarketingChannel> mList=new ArrayList<MarketingChannel>();
        //遍历顶层
        for (MarketingChannelParam marketingChannelParam : channelParams) {
            Byte customerType=marketingChannelParam.getCustomerType();
            // 将基础信息的customerId插入customerCode
            MarketingChannel mChannel=new MarketingChannel();
            mList.add(mChannel);
            mChannel.setCustomerType(customerType);
            mChannel.setActivitySetId(activitySetId);
            String customerId=marketingChannelParam.getCustomerId();
            mChannel.setCustomerId(marketingChannelParam.getCustomerId());
            mChannel.setCustomerName(marketingChannelParam.getCustomerName());
            mChannel.setCustomerSuperior(marketingChannelParam.getCustomerSuperior());
            List<MarketingChannelParam> childrens=marketingChannelParam.getChildrens();
            mChannel.setCustomerSuperiorType(marketingChannelParam.getCustomerSuperiorType());
            recursiveCreateChannel(customerId,customerType,activitySetId,childrens,mList);
        }
        channelMapper.batchInsert(mList);

    }

    /**
     * 递归创建渠道实体
     * @param parentCustomerCode
     * @param parentCustomerType
     * @param activitySetId
     * @param childrens
     * @param mList
     */
    private void recursiveCreateChannel(String parentCustomerCode, Byte parentCustomerType, Long activitySetId,
                                        List<MarketingChannelParam> childrens, List<MarketingChannel> mList) {
        if (null==childrens || childrens.isEmpty()) {
            return;
        }

        //遍历顶层
        for (MarketingChannelParam marketingChannelParam : childrens) {
            Byte customerType=marketingChannelParam.getCustomerType();
            // 基础信息的CustomerId对应营销的customerCode
            String customerId=marketingChannelParam.getCustomerId();
            MarketingChannel mChannel=new MarketingChannel();
            mChannel.setActivitySetId(activitySetId);
            mChannel.setCustomerId(customerId);
            mChannel.setCustomerName(marketingChannelParam.getCustomerName());
            mChannel.setCustomerSuperior(parentCustomerCode);
            mChannel.setCustomerSuperiorType(parentCustomerType);
            mChannel.setCustomerType(customerType);
            mList.add(mChannel);
            List<MarketingChannelParam> childrens2=marketingChannelParam.getChildrens();
            recursiveCreateChannel(customerId,customerType,activitySetId,childrens2,mList);
        }
    }


    private MarketingActivitySet changeVoToDtoForMarketingActivitySet(MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        // 前置参数
        Date date = new Date();
        String formatDate          = CouponService.format.format(date);
        String organizationId         = commonUtil.getOrganizationId();
        String organizationName     = commonUtil.getOrganizationName();
        String userId     = commonUtil.getUserLoginCache().getUserId();
        MarketingActivitySet activitySet  = new MarketingActivitySet();
        String username = commonUtil.getUserLoginCache().getUserName();

        // 活动设置参数
        activitySet                                                        .setUpdateUserId(userId);
        activitySet                                                      .setCreateDate(formatDate);
        activitySet                                                      .setUpdateDate(formatDate);
        activitySet                                                    .setUpdateUserName(username);
        activitySet                                              .setOrganizationId(organizationId);
        activitySet                                             .setAutoFetch(addVO.getAutoFetch());
        activitySet                                        .setOrganizatioIdlName(organizationName);
        activitySet                                     .setActivityTitle(addVO.getActivityTitle());
        activitySet                             .setActivityStatus(ActivityStatusEnum.UP.getType());
        activitySet              .setActivityId(ActivityIdEnum.ACTIVITY_COUPON.getId().longValue());
        activitySet    .setActivityEndDate(CouponService.format.format(addVO.getActivityEndDate()));
        activitySet.setActivityStartDate(CouponService.format.format(addVO.getActivityStartDate()));
        // 门槛
        MarketingActivitySetCondition validCondition = new MarketingActivitySetCondition();
        validCondition                         .setEachDayNumber(addVO.getEachDayNumber());
        validCondition                   .setAcquireCondition(addVO.getAcquireCondition());
        validCondition         .setAllChannels(CouponWithAllChannelEnum.NOT_ALL.getType());
        validCondition   .setAcquireConditionIntegral(addVO.getAcquireConditionIntegral());

        if(!CollectionUtils.isEmpty(addVO.getChannelParams())){
            validCondition.setAllChannels(CouponWithAllChannelEnum.ALL.getType());
        }

        activitySet.setValidCondition(validCondition.toJsonString());
        return activitySet;
    }


    /**
     * 新建活动业务校验
     * @param addVO
     */
    private void validateBizByAdd(MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        String organizationId = commonUtil.getOrganizationId();

        // 标题不可重复校验
        String activityTitle = addVO.getActivityTitle();
        MarketingActivitySet marketingActivitySet = setMapper.selectByTitleOrgIdWithActivityId(activityTitle, organizationId, ActivityIdEnum.ACTIVITY_COUPON.getId());
        if(marketingActivitySet != null){
            throw new SuperCodeException("标题已创建");
        }
//        原来的代码在保存时会进行覆盖
//        if(!CollectionUtils.isEmpty(addVO.getProductParams())){
//            List<MarketingActivityProduct> batchProductInfos = new ArrayList<>();
//            for(MarketingActivityProductParam product : addVO.getProductParams()){
//                for(ProductBatchParam batch : product.getProductBatchParams()){
//                    MarketingActivityProduct p = new MarketingActivityProduct();
//                    p.setProductId(product.getProductId());
//                    p.setProductBatchId(batch.getProductBatchId());
//                    batchProductInfos.add(p);
//                }
//            }
//            // 对活动的产品进行覆盖
//            productMapper.batchDeleteByProBatchsAndRole(batchProductInfos, RoleTypeEnum.MEMBER.getMemberType());
//        }

    }


    private void validateBasicByAdd(MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        if(addVO == null){
            throw new SuperCodeException("优惠券新建参数异常001");
        }

        // 活动标题
        if(addVO.getActivityTitle() == null){
            throw new SuperCodeException("优惠券新建参数异常002");
        }

        // 每人每天限量
        if(addVO.getEachDayNumber() == null){
            addVO.setEachDayNumber(ActivityDefaultConstant.eachDayNum);
        }else if(addVO.getEachDayNumber() <= 0){
            throw new SuperCodeException("限量需大于0...");
        }

        // 自动追加
        if(addVO.getAutoFetch() == null  ){
            throw new SuperCodeException("优惠券新建参数异常003");
        }

        if(addVO.getAutoFetch() != AutoGetEnum.BY_AUTO.getAuto() && addVO.getAutoFetch() != AutoGetEnum.BY_NOT_AUTO.getAuto()){
            throw new SuperCodeException("活动码追加方式不在系统定义范围...");
        }

        // 获得条件
        if(addVO.getAcquireCondition() == null){
            throw new SuperCodeException("优惠券新建参数异常004...");
        }

        if(addVO.getAcquireCondition().intValue() != CouponAcquireConditionEnum.FIRST.getCondition().intValue()
                && addVO.getAcquireCondition().intValue() != CouponAcquireConditionEnum.ONCE_LIMIT.getCondition().intValue()
                && addVO.getAcquireCondition().intValue() != CouponAcquireConditionEnum.LIMIT.getCondition().intValue()
                && addVO.getAcquireCondition().intValue() != CouponAcquireConditionEnum.SHOPPING.getCondition().intValue()){
            throw new SuperCodeException("获得条件选择错误...");
        }

        // 获得条件所需积分
        if((addVO.getAcquireCondition().intValue() == CouponAcquireConditionEnum.ONCE_LIMIT.getCondition().intValue()
                || addVO.getAcquireCondition().intValue() == CouponAcquireConditionEnum.LIMIT.getCondition().intValue())
            && (addVO.getAcquireConditionIntegral() ==null || addVO.getAcquireConditionIntegral() <= 0  )){
            throw new SuperCodeException("一次积分数输入错误...");
        }

        // 活动时间
        if(addVO.getActivityStartDate() == null && addVO.getActivityEndDate() != null){
            throw new SuperCodeException("新建活动时间填写001...");
        }

        if(addVO.getActivityStartDate() != null && addVO.getActivityEndDate() == null){
            throw new SuperCodeException("新建活动时间填写002...");
        }

//        if(addVO.getActivityStartDate() == null && addVO.getActivityEndDate() == null){
//            throw new SuperCodeException("优惠券新建参数异常001");
//        }

        if(addVO.getActivityStartDate() == null && addVO.getActivityEndDate() == null){
            addVO.setActivityStartDate(new Date());
            try {
                addVO.setActivityEndDate(format.parse(ActivityDefaultConstant.activityEndDate));
            } catch (ParseException e) {
                if(logger.isErrorEnabled()){
                    logger.error("新建优惠券系统默认结束时间解析失败{}",e.getMessage());
                }
                throw new SuperCodeException("新建优惠券活动时间解析失败");
            }
        }else{
            // 校验用户时间合法性
            if(addVO.getActivityStartDate().after( addVO.getActivityEndDate() )){
                throw new SuperCodeException("岂止时间错误...");
            }
        }
        // 产品校验
        validateBasicByAddForProducts(addVO.getProductParams());
        // 渠道校验
        validateBasicByAddForChannels(addVO.getChannelParams());
        // 优惠券规则校验
        validateBasicByAddForCouponRules(addVO.getCouponRules());
    }


    private void validateBasicByAddForCouponRules(MarketingCouponVo couponRules) throws SuperCodeException {
        if(couponRules == null){
            throw new SuperCodeException("优惠券规则未指定");
        }

        if(CollectionUtils.isEmpty(couponRules.getCouponAmoutAndDateVo())){
            throw new SuperCodeException("优惠券规则不可为空...");
        }

        // 是否支持抵扣渠道
        if(couponRules.getDeductionChannelType() == null
        || (couponRules.getDeductionChannelType() != DeductionChannelTypeEnum.ONLY_CHANNELS.getType()
               && couponRules.getDeductionChannelType() != DeductionChannelTypeEnum.NO_LIMIT.getType())){
            throw new SuperCodeException("是否支持抵扣渠道类型错误");
        }

        // 是否支持被抵扣产品
        if(couponRules.getDeductionProductType() == null
            || couponRules.getDeductionProductType() != DeductionProductTypeEnum.NO_LIMIT.getType()){
            throw new SuperCodeException("优惠券规则未指定...");
        }

        Date date = new Date();
        for(MarketingCouponAmoutAndDateVo couponAmoutAndDateVo : couponRules.getCouponAmoutAndDateVo()){
            if(couponAmoutAndDateVo.getCouponAmount() == null || couponAmoutAndDateVo.getCouponAmount() <= 0 ){
                throw new SuperCodeException("金额非法...");
            }

            if(couponAmoutAndDateVo.getDeductionStartDate() == null
                    || couponAmoutAndDateVo.getDeductionEndDate() == null
                    || couponAmoutAndDateVo.getDeductionEndDate().before(date)
                    || couponAmoutAndDateVo.getDeductionEndDate().before(couponAmoutAndDateVo.getDeductionStartDate())){
                throw new SuperCodeException("时间录入错误..."); // 时间精度暂时没有处理
            }
        }
    }


    /**
     * 优惠券渠道校验
     * @param channelParams
     * @throws SuperCodeException
     */
    private void validateBasicByAddForChannels(List<MarketingChannelParam> channelParams) throws SuperCodeException {
        if(CollectionUtils.isEmpty(channelParams)){
            //   选择全部渠道 [在activitysetjson字符串字段保存一个特殊参数allchannels]
         }else{
            for(MarketingChannelParam channelParam:channelParams){
                if(channelParam.getCustomerType() == null){
                    throw new SuperCodeException("渠道参数非法001");
                }

                if(StringUtils.isBlank(channelParam.getCustomerId())){
                    throw new SuperCodeException("渠道参数非法002");
                }

                if(StringUtils.isBlank(channelParam.getCustomerName())){
                    throw new SuperCodeException("渠道参数非法003");
                }
//              顶级可以为null
//                if(channelParam.getCustomerSuperiorType() == null){
//                    throw new SuperCodeException("渠道参数非法004");
//                }
//
//                if(StringUtils.isBlank(channelParam.getCustomerSuperior())){
//                    throw new SuperCodeException("渠道参数非法005");
//                }
//


            }
        }
    }


    /**
     * 新建活动产品批次校验
     * @param productParams
     * @throws SuperCodeException
     */
    private void validateBasicByAddForProducts(List<MarketingActivityProductParam> productParams) throws SuperCodeException {
        if (CollectionUtils.isEmpty(productParams)) {
            throw new SuperCodeException("产品参数非法");
        } else {
            for (MarketingActivityProductParam productParam : productParams) {
                if (StringUtils.isBlank(productParam.getProductId()) || StringUtils.isBlank(productParam.getProductName())) {
                    throw new SuperCodeException("产品参数非法001");
                }
                if (!CollectionUtils.isEmpty(productParam.getProductBatchParams())) {
                    for (ProductBatchParam batchParam : productParam.getProductBatchParams()) {
                        if (StringUtils.isBlank(batchParam.getProductBatchId()) || StringUtils.isBlank(batchParam.getProductBatchName())) {
                            throw new SuperCodeException("产品批次参数非法001");
                        }
                    }
                }else{
                    throw new SuperCodeException("请选择批次信息...");
                }
            }
        }
    }


    /**
     * 查看活动设置
     * @param activitySetId
     * @param vo
     * @return
     * @throws SuperCodeException
     */
    public MarketingActivityCouponUpdateParam setActivitySetInfoToVo(Long activitySetId, MarketingActivityCouponUpdateParam vo) throws SuperCodeException {
        MarketingActivitySet marketingActivitySet = setMapper.selectById(activitySetId);
        if(marketingActivitySet == null){
            throw new SuperCodeException("未查询成功...");
        }
        vo = copyField(vo,marketingActivitySet);
        return vo;

    }

    private MarketingActivityCouponUpdateParam copyField(MarketingActivityCouponUpdateParam vo, MarketingActivitySet dto) throws SuperCodeException {
        vo.setId(dto.getId());
        try {
            if( dto.getActivityEndDate()!=null ){
                vo.setActivityEndDate(format.parse(dto.getActivityEndDate()));
            }
            if( dto.getActivityStartDate()!=null ){
                vo.setActivityStartDate(format.parse(dto.getActivityStartDate()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SuperCodeException("查看详解解析活动时间失败");
        }
        vo.setActivityTitle(dto.getActivityTitle());
        vo.setAutoFetch(dto.getAutoFetch());

        String validCondition = dto.getValidCondition();
        MarketingActivitySetCondition validConditionObj = JSONObject.parseObject(validCondition, MarketingActivitySetCondition.class);
        vo.setEachDayNumber(validConditionObj.getEachDayNumber());
        vo.setAcquireCondition(validConditionObj.getAcquireCondition());
        vo.setAcquireConditionIntegral(validConditionObj.getAcquireConditionIntegral());
        return vo;
    }


    /**
     * 查看抵扣券规则
     * @param activitySetId
     * @param vo
     * @return
     */
    public MarketingActivityCouponUpdateParam setCouponRuleInfoToVo(Long activitySetId, MarketingActivityCouponUpdateParam vo) {
        // 规则VO
        // TODO 抽取成公共方法 start
        MarketingCouponVo couponRules = new MarketingCouponVo();
        List<MarketingCouponAmoutAndDateVo> couponAmoutAndDateVos= new ArrayList<>(5);
        List<MarketingCoupon> marketingCoupons = couponMapper.selectByActivitySetId(activitySetId);
        if(CollectionUtils.isEmpty(marketingCoupons)){
            return null;
        }
        for(MarketingCoupon rule : marketingCoupons){
            MarketingCouponAmoutAndDateVo ruleVo = new MarketingCouponAmoutAndDateVo();
            ruleVo.setCouponAmount(rule.getCouponAmount());
            ruleVo.setDeductionStartDate(rule.getDeductionStartDate());
            ruleVo.setDeductionEndDate(rule.getDeductionEndDate());
            couponAmoutAndDateVos.add(ruleVo);
        }
        couponRules.setCouponAmoutAndDateVo(couponAmoutAndDateVos);
        couponRules.setDeductionChannelType(marketingCoupons.get(0).getDeductionChannelType());
        couponRules.setDeductionProductType(marketingCoupons.get(0).getDeductionProductType());
        // TODO 抽取成公共方法 end
        vo.setCouponRules(couponRules);
        return vo;
    }


    /**
     * 产品参数查询  TODO 交给产品service
     * @param activitySetId
     * @param vo
     * @return
     */
    public MarketingActivityCouponUpdateParam setProductInfoToVo(Long activitySetId, MarketingActivityCouponUpdateParam vo) {
        // 获取中奖规则-奖次信息
        List<MarketingActivityProduct> marketingActivityProducts = productMapper.selectByActivitySetId(activitySetId);
        if(CollectionUtils.isEmpty(marketingActivityProducts)){
            return null;
        }
        // 产品批次转换成网页格式数据转换
        Set<MarketingActivityProductParam> transferDatas = new HashSet<MarketingActivityProductParam>();
        for (MarketingActivityProduct marketingActivityProduct : marketingActivityProducts) {
            // 数据转换 产品去重
            MarketingActivityProductParam transferData = new MarketingActivityProductParam();
            transferData.setProductId(marketingActivityProduct.getProductId());
            transferData.setProductName(marketingActivityProduct.getProductName());
            // 产品信息集合
            transferDatas.add(transferData);
        }
        // 产品批次转换成网页格式数据转换2==产品关联相关批次
        for (MarketingActivityProductParam transferData : transferDatas) {
            // 产品批次对象
            List<ProductBatchParam> productParams = new ArrayList<>();
            for (MarketingActivityProduct marketingActivityProduct : marketingActivityProducts) {
                // 校验该批次是否属于该产品
                if (transferData.getProductId().equals(marketingActivityProduct.getProductId())) {
                    ProductBatchParam batch = new ProductBatchParam();
                    batch.setProductBatchId(marketingActivityProduct.getProductBatchId());
                    batch.setProductBatchName(marketingActivityProduct.getProductBatchName());
                    productParams.add(batch);
                }
            }
            transferData.setProductBatchParams(productParams);
        }
        vo.setProductParams(new ArrayList<>(transferDatas));
        return vo;
    }

    /**
     * 获取渠道信息 TODO 交给渠道service
     * @param activitySetId
     * @param vo
     * @return
     */
    public MarketingActivityCouponUpdateParam setChannelInfoToVo(Long activitySetId, MarketingActivityCouponUpdateParam vo) {
        List<MarketingChannel> marketingChannelList  = channelMapper.selectByActivitySetId(activitySetId);
        if(!CollectionUtils.isEmpty(marketingChannelList)) {
            Map<String, MarketingChannelParam> MarketingChannelParamMap = marketingChannelList.stream()
                    .collect(Collectors.toMap(
                            MarketingChannel::getCustomerId, marketingChannel -> {
                                MarketingChannelParam marketingChannelParam = new MarketingChannelParam();
                                BeanUtils.copyProperties(marketingChannel, marketingChannelParam);
                                return marketingChannelParam;
                            }));
            Set<MarketingChannelParam> marketingChannelParam = getSonByFatherWithAllData(MarketingChannelParamMap);
            vo.setChannelParams(new ArrayList<>(marketingChannelParam));
        }
        return vo;
    }


    //******************************************************tree start***********************************************************
    //遍历渠道数据并添加为树形结构
    private Set<MarketingChannelParam> getSonByFatherWithAllData(Map<String, MarketingChannelParam> marketingChannelMap) {
        Set<MarketingChannelParam> channelSet = new HashSet<>();
        Collection<MarketingChannelParam> channelCollection = marketingChannelMap.values();
        for(MarketingChannelParam marketingChannel : channelCollection) {
            MarketingChannelParam channel = putChildrenChannel(marketingChannelMap, marketingChannel);
            if(channel != null) {
                channelSet.add(channel);
            }
        }
        return channelSet;
    }

    //将渠道数据递归添加到子项中
    private MarketingChannelParam putChildrenChannel(Map<String, MarketingChannelParam> marketingChannelMap, MarketingChannelParam channel) {
        MarketingChannelParam reChannel = null;
        if(marketingChannelMap.containsKey(channel.getCustomerSuperior())) {
            MarketingChannelParam parentChannel = marketingChannelMap.get(channel.getCustomerSuperior());
            List<MarketingChannelParam> childList = parentChannel.getChildrens();
            //如果父级的children为空，则说明第一次添加，需递归调用，如果不为空，则说明不是第一次添加，
            //以前已经递归调用过，父级以上的关系已添加过，不用再次递归，也无需返回实例。
            if(childList == null) {
                parentChannel.setChildrens(Lists.newArrayList(channel));
                reChannel = putChildrenChannel(marketingChannelMap, parentChannel);
            } else {
                if(!childList.contains(channel))
                    childList.add(channel);
            }
        } else {
            reChannel = channel;
        }
        return reChannel;
    }

    //******************************************************tree end*************************************************************



}
