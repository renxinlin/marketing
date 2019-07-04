package com.jgw.supercodeplatform.marketing.service.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.zxing.WriterException;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.check.activity.StandActicityParamCheck;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingSalerActivitySetMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingReceivingPageMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityPreviewParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusBatchUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.vo.activity.ReceivingAndWinningPageVO;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;

@Service
public class MarketingActivitySetService extends AbstractPageService<DaoSearchWithOrganizationIdParam> {
	protected static Logger logger = LoggerFactory.getLogger(MarketingActivitySetService.class);

	@Autowired
	private MarketingActivitySetMapper mSetMapper;

	@Autowired
	private MarketingWinningPageMapper marWinningPageMapper;

	@Autowired
	private MarketingReceivingPageMapper maReceivingPageMapper;

	@Autowired
	private MarketingPrizeTypeMapper mPrizeTypeMapper;

	@Autowired
	private MarketingChannelMapper mChannelMapper;

	@Autowired
	private MarketingActivityProductMapper mProductMapper;

	@Autowired
	private StandActicityParamCheck standActicityParamCheck;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonService commonService;

	@Autowired
	private MarketingActivityChannelService channelService;


	@Value("${rest.codemanager.url}")
	private String codeManagerUrl;

	@Value("${marketing.domain.url}")
	private String marketingDomain;

	/**
	 * 根据活动id获取领取页和中奖页信息
	 * @param activitySetId
	 * @return
	 */
	public RestResult<ReceivingAndWinningPageVO> getPageInfo(Long activitySetId) {
		MarketingWinningPage marWinningPage=marWinningPageMapper.getByActivityId(activitySetId);
		MarketingReceivingPage mReceivingPage=maReceivingPageMapper.getByActivityId(activitySetId);

		RestResult<ReceivingAndWinningPageVO> restResult=new RestResult<ReceivingAndWinningPageVO>();
		ReceivingAndWinningPageVO rePageVO=new ReceivingAndWinningPageVO();
		rePageVO.setMaReceivingPage(mReceivingPage);
		rePageVO.setMaWinningPage(marWinningPage);
		restResult.setState(200);
		restResult.setResults(rePageVO);
		restResult.setMsg("成功");
		return restResult;
	}
	/**
	 * 创建会员活动
	 * @param activitySetParam
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional(rollbackFor = Exception.class)
	public RestResult<String> memberActivityAdd(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {
		MarketingActivitySet mActivitySet =baseAdd(activitySetParam);
		//保存领取页
		MarketingReceivingPageParam mReceivingPageParam=activitySetParam.getmReceivingPageParam();
		saveReceivingPage(mReceivingPageParam,mActivitySet.getId());

		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}

	/**
	 *
	 * @param activitySetParam
	 * @return
	 * @throws SuperCodeException
	 */
	public RestResult<String> guideActivityAdd(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {
		baseAdd(activitySetParam);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;

	}
	/**
	 * 创建活动
	 * @param activitySetParam
	 * @return
	 * @throws SuperCodeException
	 */
	public MarketingActivitySet baseAdd(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {

		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
		List<MarketingChannelParam> mChannelParams=activitySetParam.getmChannelParams();
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		//获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(activitySetParam.getmActivitySetParam().getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		//获取活动实体
		MarketingActivitySet mActivitySet = convertActivitySet(activitySetParam.getmActivitySetParam(),organizationId,organizationName);
		
		//检查奖次类型
		standActicityParamCheck.basePrizeTypeCheck(mPrizeTypeParams);

		//检查产品
	    standActicityParamCheck.baseProductBatchCheck(maProductParams);
		
		Long activitySetId= mActivitySet.getId();
		if (null!=mChannelParams && mChannelParams.size()!=0) {
			//保存渠道
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypes(mPrizeTypeParams,activitySetId);
		//保存商品批次活动总共批次参与的码总数
		saveProductBatchs(maProductParams,activitySetId,ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
		return mActivitySet;
	}
	/**
	 * 编辑的规则是前端传了参数就更新 没传就不做操作
	 * @param activitySetParam
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional
	public RestResult<String> update(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
		List<MarketingChannelParam> mChannelParams=activitySetParam.getmChannelParams();
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		//获取奖次参数
		List<MarketingPrizeTypeParam> mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		
		MarketingActivitySetParam mSetParam=activitySetParam.getmActivitySetParam();
		Long id=mSetParam.getId();
		if (null==id) {
			throw new SuperCodeException("活动设置id不能为空", 500);
		}
		
		MarketingReceivingPageParam mReceivingPageParam=activitySetParam.getmReceivingPageParam();
		//获取活动实体
		MarketingActivitySet mActivitySet = convertActivitySet(mSetParam,organizationId,organizationName);
		List<MarketingActivityProduct> marketActivityProductList = mProductMapper.selectByActivitySetId(mActivitySet.getId());
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

		updatePage(mReceivingPageParam);
		
		//检查奖次类型
		standActicityParamCheck.basePrizeTypeCheck(mPrizeTypeParams);

		//检查产品
	    standActicityParamCheck.baseProductBatchCheck(maProductParams);

		Long activitySetId= mActivitySet.getId();
		
		//保存商品批次活动总共批次参与的码总数
		saveProductBatchs(mActivitySet.getId(), maProductParams,delBatchProductList,activitySetId,0);
		if (null!=mChannelParams && mChannelParams.size()!=0) {
			//保存渠道
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypes(mPrizeTypeParams,activitySetId);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}



	private MarketingActivitySet convertActivitySet(MarketingActivitySetParam activitySetParam, String organizationId, String organizationName) throws SuperCodeException {
		String title=activitySetParam.getActivityTitle();
		if (StringUtils.isBlank(title)) {
			throw new SuperCodeException("添加的活动设置标题不能为空", 500);
		}
		activityTimeCheck(activitySetParam.getActivityStartDate(),activitySetParam.getActivityEndDate());
		Long id=activitySetParam.getId();
		MarketingActivitySet mSet=new MarketingActivitySet();
		// 保存创建更新用户
		AccountCache userLoginCache = commonUtil.getUserLoginCache();
		mSet.setUpdateUserId(userLoginCache.getUserId());
		mSet.setUpdateUserName(userLoginCache.getUserName());
		String activityStartDate = StringUtils.isBlank(activitySetParam.getActivityStartDate())?null:activitySetParam.getActivityStartDate();
		String activityEndDate = StringUtils.isBlank(activitySetParam.getActivityEndDate())?null:activitySetParam.getActivityEndDate();
		mSet.setActivityEndDate(activityEndDate);
		mSet.setActivityId(activitySetParam.getActivityId());
		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activityStartDate);
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setId(id);
		mSet.setActivityDesc(activitySetParam.getActivityDesc());
		MarketingActivitySetCondition condition = new MarketingActivitySetCondition();
		condition.setEachDayNumber(activitySetParam.getEachDayNumber() == null?200:activitySetParam.getEachDayNumber());
		condition.setConsumeIntegral(activitySetParam.getConsumeIntegralNum());
		condition.setParticipationCondition(activitySetParam.getParticipationCondition());
		mSet.setValidCondition(condition.toJsonString());
		// 起止时间校验【允许活动不传时间，但起止时间不可颠倒】
		mSet.setActivityStatus(1);
		mSet.setOrganizationId(organizationId);
		mSet.setOrganizatioIdlName(organizationName);
		if (null==id) {
			mSetMapper.insert(mSet);
		}else {
			mSetMapper.update(mSet);
		}
		
		return mSet;
	}

	/**
	 * 校验活动创建时间
	 * @param mActivitySet
	 * @throws SuperCodeException
	 */
	private void activityTimeCheck(String activityStartDate,String activityEndDate) throws SuperCodeException {
		// 活动起始时间空串处理
		if(StringUtils.isBlank(activityStartDate)){
			activityStartDate=null;
        }

		if(StringUtils.isBlank(activityEndDate)){
			activityEndDate=null;
        }
		Date endDate = null;
		Date startDate = null;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        if (null==activityStartDate && null==activityEndDate) {
        	activityEndDate=format.format(new Date());
		}else if (null!=activityStartDate && null!=activityEndDate) {
			try {
				endDate = DateUtil.parse(activityEndDate,"yyyy-MM-dd");
				startDate = DateUtil.parse(activityStartDate,"yyyy-MM-dd");
				if(startDate.after(endDate)){
					throw new SuperCodeException("日期起止时间不合法",500);
				}
			} catch (ParseException e) {
				throw new SuperCodeException("日期起止时间不合法",500);
			}
		}else {
			throw new SuperCodeException("活动时间要么全选要么全部为空",500);
		}
	}
	/**
	 * 保存领取页
	 * @param mReceivingPageParam
	 * @param activitySetId
	 */
	private void saveReceivingPage(MarketingReceivingPageParam mReceivingPageParam, Long activitySetId) throws SuperCodeException {
		// 校验
		if (StringUtils.isBlank(mReceivingPageParam.getTemplateId())){
			throw new SuperCodeException("领取页参数不全", 500);
		}
		if (activitySetId == null || activitySetId <= 0){
			throw new SuperCodeException("领取页参数不全", 500);
		}
		if (mReceivingPageParam.getIsReceivePage() == null ){
			throw new SuperCodeException("领取页参数不全", 500);
		}
		if (mReceivingPageParam.getIsQrcodeView() == null ){
			throw new SuperCodeException("领取页参数不全", 500);
		}

		// 保存
		MarketingReceivingPage mPage=new MarketingReceivingPage();
		mPage.setIsQrcodeView(mReceivingPageParam.getIsQrcodeView());
		mPage.setIsReceivePage(mReceivingPageParam.getIsReceivePage());
		mPage.setPicAddress(mReceivingPageParam.getPicAddress());
		mPage.setActivitySetId(activitySetId);
		mPage.setQrcodeUrl(mReceivingPageParam.getQrcodeUrl());
		mPage.setTemplateId(mReceivingPageParam.getTemplateId());
		mPage.setTextContent(mReceivingPageParam.getTextContent());
		mPage.setFlipTimes(mReceivingPageParam.getFlipTimes());
		maReceivingPageMapper.insert(mPage);
	}

	/**
	 * 保存中奖奖次
	 * @param mPrizeTypeParams
	 * @param activitySetId
	 * @throws SuperCodeException
	 */
	private void savePrizeTypes(List<MarketingPrizeTypeParam> mPrizeTypeParams, Long activitySetId) throws SuperCodeException {

		List<MarketingPrizeType> mList=new ArrayList<MarketingPrizeType>(mPrizeTypeParams.size());
		int sumprizeProbability=0;
		for (MarketingPrizeTypeParam marketingPrizeTypeParam : mPrizeTypeParams) {
			Integer prizeProbability=marketingPrizeTypeParam.getPrizeProbability();
			MarketingPrizeType mPrizeType=new MarketingPrizeType();
			mPrizeType.setActivitySetId(activitySetId);
			mPrizeType.setPrizeAmount(marketingPrizeTypeParam.getPrizeAmount());
			mPrizeType.setPrizeProbability(prizeProbability);
			mPrizeType.setPrizeTypeName(marketingPrizeTypeParam.getPrizeTypeName());
			mPrizeType.setIsRrandomMoney(marketingPrizeTypeParam.getIsRrandomMoney());
			mPrizeType.setRealPrize((byte) 1);
			mPrizeType.setLowRand(marketingPrizeTypeParam.getLowRand());
			mPrizeType.setHighRand(marketingPrizeTypeParam.getHighRand());
			mPrizeType.setAwardType(marketingPrizeTypeParam.getAwardType());
			mPrizeType.setAwardIntegralNum(marketingPrizeTypeParam.getAwardIntegralNum());
			mPrizeType.setCardLink(marketingPrizeTypeParam.getCardLink());
			mPrizeType.setRemainingStock(marketingPrizeTypeParam.getRemainingStock());
			mList.add(mPrizeType);
			sumprizeProbability+=prizeProbability;
		}
		if (sumprizeProbability>100) {
			throw new SuperCodeException("概率参数非法，总数不能大于100", 500);
		}else if (sumprizeProbability<100){
			int i = 100-sumprizeProbability;
			MarketingPrizeType NoReal=new MarketingPrizeType();
			NoReal.setActivitySetId(activitySetId);
			NoReal.setPrizeAmount((float)0);
			NoReal.setPrizeProbability(i);
			NoReal.setPrizeTypeName("未中奖");
			NoReal.setIsRrandomMoney((byte) 0);
			NoReal.setRealPrize((byte) 0);
			mList.add(NoReal);
		}
		mPrizeTypeMapper.batchInsert(mList);
	}
	/**
	 * 保存产品批次
	 * @param maProductParams
	 * @param activitySetId
	 * @param memberType
	 * @return
	 * @throws SuperCodeException
	 */
	public void saveProductBatchs(Long setVoId, List<MarketingActivityProductParam> maProductParams, List<Map<String,Object>> deleteProductBatchList, Long activitySetId, int referenceRole) throws SuperCodeException {
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
					mActivityProduct.setReferenceRole((byte) referenceRole);
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
		List<MarketingActivityProduct> marketingActivityProductList = mProductMapper.selectByProductAndBatch(mList, ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
        if(setVoId != null && setVoId > 0) {
    		mPrizeTypeMapper.deleteByActivitySetId(setVoId);
    		mProductMapper.deleteByActivitySetId(setVoId);
    		mChannelMapper.deleteByActivitySetId(setVoId);
        }
		if(!CollectionUtils.isEmpty(marketingActivityProductList)) {
			List<Long> activitySetIds = new ArrayList<>();
			marketingActivityProductList.forEach(product -> {if(!activitySetIds.contains(product.getActivitySetId())) activitySetIds.add(product.getActivitySetId());});
			List<MarketingActivitySet> marketingActivitySetList = activitySetIds.isEmpty()?null:mSetMapper.selectMarketingActivitySetByIds(activitySetIds);
			if(!CollectionUtils.isEmpty(marketingActivitySetList)) {
				Map<Long, MarketingActivitySet> marketingActivitySetMap = marketingActivitySetList.stream().collect(Collectors.toMap(MarketingActivitySet::getId, mas -> mas));
				List<Map<String, Object>> deleteBatchList = new ArrayList<>();
				for(MarketingActivityProduct marketingActivityProduct : marketingActivityProductList) {
					Long aSetId = marketingActivityProduct.getActivitySetId();
					MarketingActivitySet mas = marketingActivitySetMap.get(aSetId);
					if(mas != null) {
						Long activityId = mas.getActivityId();
						Integer bizType = null;
						if(activityId.intValue() == 4) {
							MarketingActivitySetCondition validCondition = JSON.parseObject(mas.getValidCondition(), MarketingActivitySetCondition.class);
							validCondition.getAcquireCondition();
							if(CouponAcquireConditionEnum.SHOPPING.getCondition().equals(validCondition.getAcquireCondition())){
								bizType = BusinessTypeEnum.MARKETING_COUPON.getBusinessType();
							}
						} else {
							bizType = BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType();
						}
						if (bizType != null) {
							String sbatchIds = marketingActivityProduct.getSbatchId();
							String[] sbatchIdArray = sbatchIds.split(",");
							sbatchList:
							for(String sbatchId : sbatchIdArray) {
								if(!CollectionUtils.isEmpty(deleteProductBatchList)) {
									for(Map<String, Object> delPrdMap : deleteProductBatchList) {
										if(sbatchId.equals(delPrdMap.get("batchId")) && bizType.equals(delPrdMap.get("businessType"))) {
											break sbatchList;
										}
									}
								}
								Map<String, Object> delMap = new HashMap<>();
								delMap.put("batchId", sbatchId);
								delMap.put("businessType", bizType);
								delMap.put("url", marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL);
								deleteBatchList.add(delMap);
							}
						}
					}
				};
				deleteProductBatchList.addAll(deleteBatchList);
			}
		}
		//如果是会员活动需要去绑定扫码连接到批次号
		if (referenceRole == RoleTypeEnum.MEMBER.getMemberType()) {
			String superToken = commonUtil.getSuperToken();
			String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,
					WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
			JSONObject obj = JSONObject.parseObject(body);
			int state = obj.getInteger("state");
			if (200 == state) {
				JSONArray arr = obj.getJSONArray("results");
				List<Map<String, Object>> paramsList = commonService.getUrlToBatchParam(arr,
						marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,
						BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType());
				if(!CollectionUtils.isEmpty(deleteProductBatchList)) {
					String delbatchBody = commonService.deleteUrlToBatch(deleteProductBatchList, superToken);
					JSONObject delBatchobj = JSONObject.parseObject(delbatchBody);
					Integer delBatchstate = delBatchobj.getInteger("state");
					if (null != delBatchstate && delBatchstate.intValue() != 200) {
						throw new SuperCodeException("请求码删除生码批次和url错误：" + delbatchBody, 500);
					}
				}
				// 绑定生码批次到url
				String bindbatchBody = commonService.bindUrlToBatch(paramsList, superToken);
				JSONObject bindBatchobj = JSONObject.parseObject(bindbatchBody);
				Integer batchstate = bindBatchobj.getInteger("state");
				if (null != batchstate && batchstate.intValue() != 200) {
					throw new SuperCodeException("请求码管理生码批次和url错误：" + bindbatchBody, 500);
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
		}
		//插入对应活动产品数据
		mProductMapper.batchDeleteByProBatchsAndRole(mList, referenceRole);
		mProductMapper.activityProductInsert(mList);
	}

	private void saveProductBatchs(List<MarketingActivityProductParam> maProductParams, Long activitySetId, int referenceRole) throws SuperCodeException {
		saveProductBatchs(null, maProductParams, new ArrayList<>(), activitySetId, referenceRole);
	}

	/**
	 * 保存渠道数据
	 * @param mChannelParams
	 * @param activitySetId
	 * @throws SuperCodeException
	 */
	private void saveChannels(List<MarketingChannelParam> mChannelParams,Long activitySetId) throws SuperCodeException {
		if(!CollectionUtils.isEmpty(mChannelParams)) {
			List<MarketingChannel> mList=new ArrayList<MarketingChannel>();
			//遍历顶层
			for (MarketingChannelParam marketingChannelParam : mChannelParams) {
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
			mChannelMapper.batchInsert(mList);
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
	/**
	 * 更新领取页中奖页
	 * @param mUpdateParam
	 * @return
	 */
	@Transactional
	public RestResult<String> updatePage(MarketingReceivingPageParam mReceivingPageParam) {
		RestResult<String> restResult=new RestResult<String>();

		// 保存领取页信息
		MarketingReceivingPage mReceivingPage=new MarketingReceivingPage();
		mReceivingPage.setId(mReceivingPageParam.getId());
		mReceivingPage.setIsQrcodeView(mReceivingPageParam.getIsQrcodeView());
		mReceivingPage.setIsReceivePage(mReceivingPageParam.getIsReceivePage());
		mReceivingPage.setPicAddress(mReceivingPageParam.getPicAddress());
		mReceivingPage.setQrcodeUrl(mReceivingPageParam.getQrcodeUrl());
		mReceivingPage.setTemplateId(mReceivingPageParam.getTemplateId());
		mReceivingPage.setTextContent(mReceivingPageParam.getTextContent());
		mReceivingPage.setFlipTimes(mReceivingPageParam.getFlipTimes());
		maReceivingPageMapper.update(mReceivingPage);

		restResult.setState(200);
		restResult.setMsg("更新成功");
		return restResult;
	}

	/**
	 * 活动扫码跳转授权前判断逻辑
	 * @param productBatchId
	 * @param productId
	 * @param codeTypeId
	 * @param referenceRole
	 * @param codeId
	 * @return
	 * @throws SuperCodeException
	 * @throws ParseException
	 */
	public RestResult<ScanCodeInfoMO> judgeActivityScanCodeParam(String outerCodeId, String codeTypeId, String productId, String productBatchId, byte referenceRole) throws ParseException {
		RestResult<ScanCodeInfoMO> restResult=new RestResult<ScanCodeInfoMO>();
		if (StringUtils.isBlank(outerCodeId) || StringUtils.isBlank(outerCodeId)||StringUtils.isBlank(productId)||StringUtils.isBlank(productBatchId)) {
			restResult.setState(500);
			restResult.setMsg("接收到码平台扫码信息有空值");
			return restResult;
		}
		//1、判断该码批次是否参与活动
		MarketingActivityProduct mProduct=mProductMapper.selectByProductAndProductBatchIdWithReferenceRole(productId,productBatchId,referenceRole);
		if (null==mProduct) {
			restResult.setState(500);
			restResult.setMsg("该码对应的产品批次未参与活动");
			return restResult;
		}

		Long activitySetId=mProduct.getActivitySetId();
		//2、判断该活动是否存在及是否已经停用
		MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
		if (null==mActivitySet) {
			restResult.setState(500);
			restResult.setMsg("活动已被删除无法参与");
			return restResult;
		}
		Integer activityStatus= mActivitySet.getActivityStatus();
		if (null==activityStatus || 0==activityStatus) {
			restResult.setState(500);
			restResult.setMsg("活动已停止");
			return restResult;
		}
		//2、如果活动开始或结束时间不为空的话则判断扫码时间是否处于活动时间之内
		String startdate=mActivitySet.getActivityStartDate();
		String enddate=mActivitySet.getActivityEndDate();

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String nowdate=format.format(new Date());
		long currentTime=format.parse(nowdate).getTime();

		if (StringUtils.isNotBlank(startdate)) {
			long startTime=format.parse(startdate).getTime();
			if (currentTime<startTime) {
				restResult.setState(500);
				restResult.setMsg("活动还未开始");
				return restResult;
			}
		}

		if (StringUtils.isNotBlank(enddate)) {
			long endTime=format.parse(enddate).getTime();
			if (currentTime>endTime) {
				restResult.setState(500);
				restResult.setMsg("活动已结束");
				return restResult;
			}
		}
		ScanCodeInfoMO pMo=new ScanCodeInfoMO();
		pMo.setCodeId(outerCodeId);
		pMo.setCodeTypeId(codeTypeId);
		pMo.setProductBatchId(productBatchId);
		pMo.setProductId(productId);
		pMo.setActivitySetId(activitySetId);
		pMo.setOrganizationId(mActivitySet.getOrganizationId());

		Long id = mActivitySet.getId();
		// 此方法统一活动类型！！！不可是其他业务
		pMo.setActivityType(ActivityIdEnum.ACTIVITY_2.getType());
		pMo.setActivityId(mActivitySet.getActivityId());

		restResult.setResults(pMo);
		restResult.setState(200);
		return restResult;
	}

	public RestResult<String> updateActivitySetStatus(MarketingActivitySetStatusUpdateParam mUpdateStatus){
		if(mUpdateStatus.getActivityStatus() == null ){
			throw new RuntimeException("状态值不存在...");
		}
		if(mUpdateStatus.getActivitySetId() == null || mUpdateStatus.getActivitySetId() <=0){
			throw new RuntimeException("ID不存在...");
		}
		mSetMapper.updateActivitySetStatus(mUpdateStatus);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("更新成功");
		return restResult;
	}

	public MarketingActivitySet selectById(Long activitySetId) {
		return mSetMapper.selectById(activitySetId);
	}
	public Integer selectEachDayNumber(Long activitySetId) {
		return mSetMapper.selectEachDayNumber(activitySetId);
	}

	/**
	 * 获取活动基础信息
	 * @param activitySetId
	 * @return
	 */
	public RestResult<MarketingActivitySetParam> getActivityBaseInfoByeditPage(Long activitySetId) {
		RestResult<MarketingActivitySetParam> restResult = new RestResult<>();
		// 校验
		if(activitySetId == null || activitySetId <= 0 ){
			restResult.setState(500);
			restResult.setMsg("活动id校验失败");
			return  restResult;
		}
		// 获取
		MarketingActivitySet marketingActivitySet = mSetMapper.selectById(activitySetId);
		MarketingActivitySetParam MarketingActivitySetParam = new MarketingActivitySetParam();
		BeanUtils.copyProperties(marketingActivitySet, MarketingActivitySetParam);
		if(marketingActivitySet != null && StringUtils.isNotBlank(marketingActivitySet.getValidCondition())) {
			MarketingActivitySetCondition conditonJson = JSON.parseObject(marketingActivitySet.getValidCondition(), MarketingActivitySetCondition.class);
			MarketingActivitySetParam.setConsumeIntegralNum(conditonJson.getConsumeIntegral());
			MarketingActivitySetParam.setEachDayNumber(conditonJson.getEachDayNumber());
			MarketingActivitySetParam.setParticipationCondition(conditonJson.getParticipationCondition());
		}
		// 返回
		restResult.setState(200);
		restResult.setMsg("success");
		restResult.setResults(MarketingActivitySetParam);
		return  restResult;

	}


    /**
	 * 获取营销活动列表
	 */
    @Override
    protected List<MarketingSalerActivitySetMO> searchResult(DaoSearchWithOrganizationIdParam searchParams) throws Exception {
        // 查询满足条件的营销活动集合
        List<MarketingSalerActivitySetMO> list = mSetMapper.list(searchParams);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        // 获取渠道信息的树形结构
        list.forEach(mo -> {
            List<MarketingChannel> marketingChannels = mChannelMapper.selectByActivitySetId(mo.getId());
            // 转换渠道为树结构： 1先获取所有根节点，2在获取所有当前父节点以及子节点，3将子节点添加到父节点
            // 渠道父级编码可以不存在，但渠道编码必须存在
            List<MarketingChannel> treeMarketingChannels = channelService.getTree(marketingChannels);
            mo.setMarketingChannels(treeMarketingChannels);
        });
        return list;
    }

    @Override
    protected int count(DaoSearchWithOrganizationIdParam searchParams) throws Exception {
        return mSetMapper.count(searchParams);
    }

	public RestResult<String> updateSalerActivitySetStatus(MarketingActivitySetStatusUpdateParam setStatusUpdateParam) throws SuperCodeException {
		// 获取当前的用户信息
//		AccountCache userLoginCache = getUserLoginCache();
		mSetMapper.updateActivitySetStatus(setStatusUpdateParam);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("更新成功");
		return restResult;
	}


	public RestResult<String> updateSalerActivitySetStatus(MarketingActivitySetStatusBatchUpdateParam batchUpdateParam) throws SuperCodeException {
        // 获取当前的用户信息
        AccountCache userLoginCache = getUserLoginCache();
        batchUpdateParam.getActivitySetIds().forEach(activitySetId -> {
            mSetMapper.updateSalerActivitySetStatus(activitySetId, batchUpdateParam.getActivityStatus(), userLoginCache.getUserId(), userLoginCache.getUserName());
        });
        RestResult<String> restResult=new RestResult<String>();
        restResult.setState(200);
        restResult.setMsg("更新成功");
        return restResult;
    }


	/**
	 * 获取编辑参数
	 * @param activitySetId
	 * @return
	 */
	public MarketingSalerActivityCreateParam activityInfo(Long activitySetId) {
		MarketingSalerActivityCreateParam marketingActivityCreateParam = new MarketingSalerActivityCreateParam();
		//活动参数设置项
		MarketingActivitySet marketingActivitySet = mSetMapper.selectById(activitySetId);
		MarketingActivitySetParam marketingActivitySetParam = new MarketingActivitySetParam();
		BeanUtils.copyProperties(marketingActivitySet, marketingActivitySetParam);
		String conditionStr = marketingActivitySet.getValidCondition();
		if(StringUtils.isNotBlank(conditionStr)) {
			MarketingActivitySetCondition condition = JSON.parseObject(conditionStr, MarketingActivitySetCondition.class);
			marketingActivitySetParam.setParticipationCondition(condition.getParticipationCondition());
			marketingActivitySetParam.setEachDayNumber(condition.getEachDayNumber());
			marketingActivitySetParam.setConsumeIntegralNum(condition.getConsumeIntegral());
		}
		marketingActivityCreateParam.setmActivitySetParam(marketingActivitySetParam);
		//获取拼接活动设置产品参数
		List<MarketingActivityProduct> marketingActivityProductList = mProductMapper.selectByActivitySetId(activitySetId);
		Map<String, MarketingActivityProductParam> mActivityProductParamMap = new HashMap<>();
		if(!CollectionUtils.isEmpty(marketingActivityProductList)) {
			for(MarketingActivityProduct product : marketingActivityProductList) {
				String productId = product.getProductId();
				MarketingActivityProductParam marketingActivityProductParam = mActivityProductParamMap.get(productId);
				if(marketingActivityProductParam == null) {
					marketingActivityProductParam = new MarketingActivityProductParam();
					marketingActivityProductParam.setProductId(product.getProductId());
					marketingActivityProductParam.setProductName(product.getProductBatchName());
					//添加批次
					ProductBatchParam productBatchParam = new ProductBatchParam();
					productBatchParam.setProductBatchId(product.getProductBatchId());
					productBatchParam.setProductBatchName(product.getProductBatchName());
					marketingActivityProductParam.setProductBatchParams(Lists.newArrayList(productBatchParam));
					mActivityProductParamMap.put(productId, marketingActivityProductParam);
				} else {
					ProductBatchParam productBatchParam = new ProductBatchParam();
					productBatchParam.setProductBatchId(product.getProductBatchId());
					productBatchParam.setProductBatchName(product.getProductBatchName());
					marketingActivityProductParam.getProductBatchParams().add(productBatchParam);
				}
			}
		}
		marketingActivityCreateParam.setmProductParams(new ArrayList<MarketingActivityProductParam>(mActivityProductParamMap.values()));
		//获取设置中奖奖次
		List<MarketingPrizeType> marketingPrizeTypeList = mPrizeTypeMapper.selectByActivitySetId(activitySetId);
		if(!CollectionUtils.isEmpty(marketingPrizeTypeList)) {
			List<MarketingPrizeTypeParam> marketingPrizeTypeParams = marketingPrizeTypeList.stream().map(priceType -> {
				MarketingPrizeTypeParam marketingPrizeTypeParam = new MarketingPrizeTypeParam();
				BeanUtils.copyProperties(priceType, marketingPrizeTypeParam);
				return marketingPrizeTypeParam;
			}).collect(Collectors.toList());
			marketingActivityCreateParam.setMarketingPrizeTypeParams(marketingPrizeTypeParams);
		}
		//获取并拼接渠道
		List<MarketingChannel> marketingChannelList  = mChannelMapper.selectByActivitySetId(activitySetId);
		if(!CollectionUtils.isEmpty(marketingChannelList)) {
			Map<String, MarketingChannelParam> MarketingChannelParamMap = marketingChannelList.stream()
				.collect(Collectors.toMap(
					MarketingChannel::getCustomerId, marketingChannel -> {
					MarketingChannelParam marketingChannelParam = new MarketingChannelParam();
					BeanUtils.copyProperties(marketingChannel, marketingChannelParam);
					return marketingChannelParam;
			}));
			Set<MarketingChannelParam> MarketingChannelParam = getSonByFatherWithAllData(MarketingChannelParamMap);
			marketingActivityCreateParam.setmChannelParams(new ArrayList<MarketingChannelParam>(MarketingChannelParam));
		}
		return marketingActivityCreateParam;
	}


	//遍历渠道数据并添加为树形结构
	private Set<MarketingChannelParam> getSonByFatherWithAllData(Map<String, MarketingChannelParam> marketingChannelMap) {
		Set<MarketingChannelParam> channelSet = new HashSet<>();
		Collection<MarketingChannelParam> channelCollection = marketingChannelMap.values();
		for(MarketingChannelParam marketingChannel : channelCollection) {
			MarketingChannelParam channel = putChildrenChannel(marketingChannelMap, marketingChannel);
			if(channel != null)
				channelSet.add(channel);
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
			if(CollectionUtils.isEmpty(childList)) {
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

	public RestResult<String> preview(MarketingActivityPreviewParam mPreviewParam) throws WriterException, IOException, SuperCodeException {
		RestResult<String> restResult=new RestResult<String>();
		List<MarketingPrizeTypeParam> moPrizeTypes=mPreviewParam.getMarketingPrizeTypeParams();
		if (null==moPrizeTypes || moPrizeTypes.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该活动未设置中奖奖次");
			return restResult;
		}
		//检查奖次类型
		standActicityParamCheck.basePrizeTypeCheck(moPrizeTypes);

		String uuid=commonUtil.getUUID();
		String json=JSONObject.toJSONString(mPreviewParam);
		boolean flag=redisUtil.set(RedisKey.ACTIVITY_PREVIEW_PREFIX+uuid, json, 600L);
		if (flag) {
			restResult.setResults(uuid);
			restResult.setState(200);
			restResult.setMsg("成功");
		}else {
			restResult.setState(500);
			restResult.setMsg("失败");
		}
		return restResult;
	}

	public RestResult<MarketingReceivingPage> getPreviewParam(String uuid) {
		RestResult<MarketingReceivingPage> restResult=new RestResult<>();
		String value=redisUtil.get(RedisKey.ACTIVITY_PREVIEW_PREFIX+uuid);
		if (StringUtils.isBlank(value)) {
			restResult.setState(500);
			restResult.setMsg("扫码已过期请重新扫码预览");
			return restResult;
		}
		MarketingReceivingPage marketingReceivingPage = new MarketingReceivingPage();
		MarketingActivityPreviewParam mPreviewParam=JSONObject.parseObject(value, MarketingActivityPreviewParam.class);
		MarketingActivitySetParam mActivitySetParam = mPreviewParam.getmActivitySetParam();
		MarketingReceivingPageParam mReceivingPageParam = mPreviewParam.getmReceivingPageParam();
		BeanUtils.copyProperties(mReceivingPageParam, marketingReceivingPage);
		if(mActivitySetParam != null)
			marketingReceivingPage.setActivityDesc(mActivitySetParam.getActivityDesc());
		restResult.setResults(marketingReceivingPage);
		restResult.setState(200);
		return restResult;
	}

}
