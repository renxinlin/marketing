package com.jgw.supercodeplatform.marketing.service.activity.coupon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.constants.ActivityDefaultConstant;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.coupon.MarketingCouponMapperExt;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponAddParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingActivityCouponUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingCouponAmoutAndDateVo;
import com.jgw.supercodeplatform.marketing.dto.coupon.MarketingCouponVo;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityStatusEnum;
import com.jgw.supercodeplatform.marketing.enums.market.AutoGetEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponWithAllChannelEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.DeductionChannelTypeEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.DeductionProductTypeEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingCoupon;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;

@Service
public class CouponUpdateService {


    private static Logger logger = LoggerFactory.getLogger(CouponService.class);

    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

    @Value("${rest.codemanager.url}")
    private String codeManagerUrl;

    @Value("${marketing.domain.url}")
    private String marketingDomain;

    @Autowired
    private MarketingActivitySetMapper setMapper;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MarketingActivityProductMapper productMapper;

    @Autowired
    private MarketingCouponMapperExt couponMapper;

    @Autowired
    private CommonService commonService;
    
	@Autowired
	private MarketingActivityProductMapper mProductMapper;

    @Autowired
    private MarketingChannelMapper channelMapper;

    /**
     * 复制抵扣券活动
     * @param copyVO
     * @return
     * @throws SuperCodeException
     */
    @Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
    public RestResult<String> copy(MarketingActivityCouponUpdateParam copyVO) throws SuperCodeException {
        validateBasicByUpdate(copyVO);
        validateBizByUpdate(copyVO);
        // 更新活动设置表
        MarketingActivitySet activitySet = changeVoToDtoForMarketingActivitySet(copyVO);
        // setMapper.deleteById(activitySet.getId());
        // 更新了Id
        setMapper.insert(activitySet);


        // 保存渠道 TODO copy 以前活动的代码 检查有没有坑
        saveChannelsWhenUpdate(copyVO.getChannelParams(),activitySet.getId());

        // 保存产品 TODO copy 以前活动的代码 检查有没有坑 以及处理如何保存到码管理来确保领券问题
        boolean send = false;
        if(copyVO.getAcquireCondition().intValue() == CouponAcquireConditionEnum.SHOPPING.getCondition().intValue() ){
            send = true;
        }
        // 覆盖: 去除重复
        saveProductBatchsWhenUpdate(copyVO.getProductParams(),null,activitySet.getId(), copyVO.getAutoFetch(),send);
        // 保存抵扣券规则
        saveCouponRulesWhenUpdate(copyVO.getCoupon(),activitySet.getId());

        return RestResult.success();
    }


    /**
     * 修改抵扣券活动
     * @param updateVo
     * @return
     * @throws SuperCodeException
     */
    @Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
    public RestResult<String> update(MarketingActivityCouponUpdateParam updateVo) throws SuperCodeException {
        validateBasicByUpdate(updateVo);
        validateBizByUpdate(updateVo);
        // 删除子表信息
        doBizBeforeUpdate(updateVo);
        // 更新活动设置表
        MarketingActivitySet activitySet = changeVoToDtoForMarketingActivitySet(updateVo);
        activitySet.setId(updateVo.getId());
        setMapper.update(activitySet);
		
		//获取活动实体
		List<MarketingActivityProduct> marketActivityProductList = mProductMapper.selectByActivitySetId(activitySet.getId());
		List<Map<String, Object>> delBatchProductList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(marketActivityProductList)) {
			marketActivityProductList.stream()
			.filter(product -> StringUtils.isNotBlank(product.getSbatchId())).forEach(product -> {
				Map<String, Object> delMap = new HashMap<>();
				String[] batchIds = product.getSbatchId().split(",");
				for(String batchId : batchIds) {
					delMap.put("batchId", batchId);
					delMap.put("businessType", BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType());
					delMap.put("url", marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL);
					delBatchProductList.add(delMap);
				}
			});
		}

        // 保存渠道 TODO copy 以前活动的代码 检查有没有坑
        saveChannelsWhenUpdate(updateVo.getChannelParams(),activitySet.getId());

        // 保存产品 TODO copy 以前活动的代码 检查有没有坑 以及处理如何保存到码管理来确保领券问题
        boolean send = false;
        if(updateVo.getAcquireCondition().intValue() == CouponAcquireConditionEnum.SHOPPING.getCondition().intValue() ){
            send = true;
        }
        // 覆盖: 去除重复
        saveProductBatchsWhenUpdate(updateVo.getProductParams(),delBatchProductList,activitySet.getId(), updateVo.getAutoFetch(),send);
        // 保存抵扣券规则
        saveCouponRulesWhenUpdate(updateVo.getCoupon(),activitySet.getId());

        return RestResult.success();
    }

    private void doBizBeforeUpdate(MarketingActivityCouponUpdateParam setVo) throws SuperCodeException {
        Long id = setVo.getId();
        // 删除码平台的绑定
        productMapper.deleteByActivitySetId(id);
        channelMapper.deleteByActivitySetId(id);
        couponMapper.deleteByActivitySetId(id);

    }

    private void saveChannelsWhenUpdate(List<MarketingChannelParam> channelParams, Long activitySetId) {
    	if(!CollectionUtils.isEmpty(channelParams)) {
	    	List<MarketingChannel> mList=new ArrayList<MarketingChannel>();
	        //遍历顶层
	        for (MarketingChannelParam marketingChannelParam : channelParams) {
	            Byte customerType=marketingChannelParam.getCustomerType();
	            // 将基础信息的customerId插入customerCode
	            String customerId=marketingChannelParam.getCustomerId();
	            MarketingChannel mChannel=new MarketingChannel();
	            mChannel.setActivitySetId(activitySetId);
	            mChannel.setCustomerId(marketingChannelParam.getCustomerId());
	            mChannel.setCustomerName(marketingChannelParam.getCustomerName());
	            mChannel.setCustomerSuperior(marketingChannelParam.getCustomerSuperior());
	            mChannel.setCustomerSuperiorType(marketingChannelParam.getCustomerSuperiorType());
	            mChannel.setCustomerType(customerType);
	            mList.add(mChannel);
	            List<MarketingChannelParam> childrens=marketingChannelParam.getChildrens();
	            recursiveCreateChannel(customerId,customerType,activitySetId,childrens,mList);
	        }
	        channelMapper.batchInsert(mList);
    	}
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


    private void saveProductBatchsWhenUpdate(List<MarketingActivityProductParam> maProductParams, List<Map<String,Object>> deleteProductBatchList, Long activitySetId, int autoFecth,boolean send) throws SuperCodeException {
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
        // 绑定绑定生码批次
        couponService.getProductBatchSbatchId(productAndBatchGetCodeMOs, mList);
        
        // TODO 等待建强那边处理交互协议
    	String superToken = commonUtil.getSuperToken();
		String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,
				WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
		JSONObject obj = JSONObject.parseObject(body);
		int state = obj.getInteger("state");
		if (200 == state) {
			JSONArray arr = obj.getJSONArray("results");
			if(!CollectionUtils.isEmpty(deleteProductBatchList)) {
				String delbatchBody = commonService.deleteUrlToBatch(deleteProductBatchList, superToken);
				JSONObject delBatchobj = JSONObject.parseObject(delbatchBody);
				Integer delBatchstate = delBatchobj.getInteger("state");
				if (null != delBatchstate && delBatchstate.intValue() != 200) {
					throw new SuperCodeException("请求码删除生码批次和url错误：" + delbatchBody, 500);
				}
			}
			if(send) {
				int businessType = BusinessTypeEnum.MARKETING_COUPON.getBusinessType();
				List<Map<String, Object>> paramsList = commonService.getUrlToBatchParam(arr, marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,businessType);
				// 绑定生码批次到url
				String bindbatchBody = commonService.bindUrlToBatch(paramsList, superToken);
				JSONObject bindBatchobj = JSONObject.parseObject(bindbatchBody);
				Integer batchstate = bindBatchobj.getInteger("state");
				if (null != batchstate && batchstate.intValue() != HttpStatus.SC_OK) {
					throw new SuperCodeException("请求码管理生码批次和url错误：" + bindbatchBody, HttpStatus.SC_INTERNAL_SERVER_ERROR);
				}
			}
			Map<String, Map<String, Object>> paramsMap = commonService.getUrlToBatchParamMap(arr,
					marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,
					BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType());
			mList.forEach(marketingActivityProduct -> {
				String key = marketingActivityProduct.getProductId()+","+marketingActivityProduct.getProductBatchId();
				marketingActivityProduct.setSbatchId((String)paramsMap.get(key).get("batchId"));
			});
		} else {
			throw new SuperCodeException("通过产品及产品批次获取码信息错误：" + body, 500);
		}
        //插入对应活动产品数据
        productMapper.batchDeleteByProBatchsAndRole(mList, ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
        productMapper.activityProductInsert(mList);

    }

    private void saveCouponRulesWhenUpdate(MarketingCouponVo couponRules, Long activitySetId) {
        List<MarketingCoupon> toDbEntitys = new ArrayList<>(5);
        List<MarketingCouponAmoutAndDateVo> couponAmoutAndDateVo = couponRules.getCouponRules();
        for(MarketingCouponAmoutAndDateVo vo: couponAmoutAndDateVo){
            MarketingCoupon toDbEntity = new MarketingCoupon();
            toDbEntity.setActivitySetId(activitySetId);
            toDbEntity.setDeductionStartDate(vo.getDeductionStartDate());
            toDbEntity.setDeductionEndDate(vo.getDeductionEndDate());
            toDbEntity.setCouponAmount(vo.getCouponAmount());
            toDbEntity.setDeductionChannelType(couponRules.getDeductionChannelType());
            toDbEntity.setDeductionProductType(couponRules.getDeductionProductType());
            toDbEntitys.add(toDbEntity);
        }
        couponMapper.batchInsert(toDbEntitys);

    }


    private MarketingActivitySet changeVoToDtoForMarketingActivitySet(MarketingActivityCouponAddParam addVO) throws SuperCodeException {
        // 前置参数
        Date date = new Date();
        String formatDate          = format.format(date);
        String organizationId         = commonUtil.getOrganizationId();
        String organizationName     = commonUtil.getOrganizationName();
        String userId     = commonUtil.getUserLoginCache().getUserId();
        MarketingActivitySet activitySet  = new MarketingActivitySet();
        String username = commonUtil.getUserLoginCache().getUserName();

        // 活动设置参数
        activitySet                                                        .setUpdateUserId(userId);
        activitySet                                                      .setUpdateDate(formatDate);
        activitySet                                                    .setUpdateUserName(username);
        activitySet                                              .setOrganizationId(organizationId);
        activitySet                                             .setAutoFetch(addVO.getAutoFetch());
        activitySet                                        .setOrganizatioIdlName(organizationName);
        activitySet                                     .setActivityTitle(addVO.getActivityTitle());
        activitySet                             .setActivityStatus(ActivityStatusEnum.UP.getType());
        activitySet              .setActivityId(ActivityIdEnum.ACTIVITY_COUPON.getId().longValue());
        activitySet    .setActivityEndDate(format.format(addVO.getActivityEndDate()));
        activitySet.setActivityStartDate(format.format(addVO.getActivityStartDate()));
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



    private void validateBizByUpdate(MarketingActivityCouponUpdateParam updateVo) throws SuperCodeException {
        String organizationId = commonUtil.getOrganizationId();

        // 标题不可重复校验
        String activityTitle = updateVo.getActivityTitle();
        MarketingActivitySet marketingActivitySet = setMapper.selectByTitleOrgIdWithActivityIdWhenUpdate(activityTitle,updateVo.getId(), organizationId, ActivityIdEnum.ACTIVITY_COUPON.getId());
        if(marketingActivitySet != null){
            throw new SuperCodeException("标题已创建");
        }
    }

    private void validateBasicByUpdate(MarketingActivityCouponUpdateParam updateVo) throws SuperCodeException {
        if(updateVo == null){
            throw new SuperCodeException("优惠券新建参数异常001");
        }
        // 活动标题
        if(updateVo.getActivityTitle() == null){
            throw new SuperCodeException("优惠券新建参数异常002");
        }

        // 每人每天限量
        if(updateVo.getEachDayNumber() == null){
            updateVo.setEachDayNumber(ActivityDefaultConstant.eachDayNum);
        }else if(updateVo.getEachDayNumber() <= 0){
            throw new SuperCodeException("限量需大于0...");
        }

        // 自动追加
        if(updateVo.getAutoFetch() == null  ){
            throw new SuperCodeException("优惠券新建参数异常003");
        }
        if(updateVo.getAutoFetch() != AutoGetEnum.BY_AUTO.getAuto() && updateVo.getAutoFetch() != AutoGetEnum.BY_NOT_AUTO.getAuto()){
            throw new SuperCodeException("活动码追加方式不在系统定义范围...");
        }

        // 获得条件
        if(updateVo.getAcquireCondition() == null){
            throw new SuperCodeException("优惠券新建参数异常004...");
        }
        if(updateVo.getAcquireCondition().intValue() != CouponAcquireConditionEnum.FIRST.getCondition().intValue()
                && updateVo.getAcquireCondition().intValue() != CouponAcquireConditionEnum.ONCE_LIMIT.getCondition().intValue()
                && updateVo.getAcquireCondition().intValue() != CouponAcquireConditionEnum.LIMIT.getCondition().intValue()
                && updateVo.getAcquireCondition().intValue() != CouponAcquireConditionEnum.SHOPPING.getCondition().intValue()){
            throw new SuperCodeException("获得条件选择错误...");
        }

        // 获得条件所需积分
        if((updateVo.getAcquireCondition().intValue() == CouponAcquireConditionEnum.ONCE_LIMIT.getCondition().intValue()
                || updateVo.getAcquireCondition().intValue() == CouponAcquireConditionEnum.LIMIT.getCondition().intValue())
                && (updateVo.getAcquireConditionIntegral() ==null || updateVo.getAcquireConditionIntegral() <= 0  )){
            throw new SuperCodeException("一次积分数输入错误...");
        }

        // 活动时间
        if(updateVo.getActivityStartDate() == null && updateVo.getActivityEndDate() != null){
            throw new SuperCodeException("新建活动时间填写...");
        }
        if(updateVo.getActivityStartDate() != null && updateVo.getActivityEndDate() == null){
            throw new SuperCodeException("新建活动时间填写...");
        }
        if(updateVo.getActivityStartDate() == null && updateVo.getActivityEndDate() == null){
            throw new SuperCodeException("优惠券新建参数异常001");
        }
        if(updateVo.getActivityStartDate() == null && updateVo.getActivityEndDate() == null){
            updateVo.setActivityStartDate(new Date());
            try {
                updateVo.setActivityEndDate(format.parse(ActivityDefaultConstant.activityEndDate));
            } catch (ParseException e) {
                if(logger.isErrorEnabled()){
                    logger.error("新建优惠券系统默认结束时间解析失败{}",e.getMessage());
                }
                throw new SuperCodeException("新建优惠券活动时间解析失败");
            }
        }else{
            // 校验用户时间合法性
            if(updateVo.getActivityStartDate().after( updateVo.getActivityEndDate() )){
                throw new SuperCodeException("起止时间错误...");
            }
        }
        // 产品校验
        validateBasicByUpdateForProducts(updateVo.getProductParams());
        // 渠道校验
        validateBasicByUpdateForChannels(updateVo.getChannelParams());
        // 优惠券规则校验
        validateBasicByUpdateForCouponRules(updateVo.getCoupon());

    }


    private void validateBasicByUpdateForCouponRules(MarketingCouponVo couponRules) throws SuperCodeException {
        if(couponRules == null){
            throw new SuperCodeException("优惠券规则未指定");
        }

        if(CollectionUtils.isEmpty(couponRules.getCouponRules())){
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
        for(MarketingCouponAmoutAndDateVo couponAmoutAndDateVo : couponRules.getCouponRules()){
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
    private void validateBasicByUpdateForChannels(List<MarketingChannelParam> channelParams) throws SuperCodeException {
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

//                if(StringUtils.isBlank(channelParam.getCustomerSuperior())){
//                    throw new SuperCodeException("渠道参数非法004");
//                }

//                if(channelParam.getCustomerSuperiorType() == null){
//                    throw new SuperCodeException("渠道参数非法005");
//                }

            }
        }
    }

    /**
     * 新建活动产品批次校验
     * @param productParams
     * @throws SuperCodeException
     */
    private void validateBasicByUpdateForProducts(List<MarketingActivityProductParam> productParams) throws SuperCodeException {
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



}
