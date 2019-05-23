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
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPageUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityTypeEnum;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
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
import com.jgw.supercodeplatform.utils.SpringContextUtil;

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
	@Transactional(rollbackFor = SuperCodeException.class)
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
		saveProductBatchs(maProductParams,activitySetId,0);
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
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		
		MarketingActivitySetParam mSetParam=activitySetParam.getmActivitySetParam();
		Long id=mSetParam.getId();
		if (null==id) {
			throw new SuperCodeException("活动设置id不能为空", 500);
		}
		
		MarketingReceivingPageParam mReceivingPageParam=activitySetParam.getmReceivingPageParam();
		
		//获取活动实体
		MarketingActivitySet mActivitySet = convertActivitySet(mSetParam,organizationId,organizationName);
		mPrizeTypeMapper.deleteByActivitySetId(mActivitySet.getId());
		mProductMapper.deleteByActivitySetId(mActivitySet.getId());
		mChannelMapper.deleteByActivitySetId(mActivitySet.getId());
		updatePage(mReceivingPageParam);
		
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
		saveProductBatchs(maProductParams,activitySetId,0);
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
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(activitySetParam.getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		activityTimeCheck(activitySetParam.getActivityStartDate(),activitySetParam.getActivityEndDate());
		Long id=activitySetParam.getId();
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		mSet.setActivityId(activitySetParam.getActivityId());
		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber());
		mSet.setId(id);
		mSet.setActivityDesc(activitySetParam.getActivityDesc());
		mSet.setConsumeIntegralNum(activitySetParam.getConsumeIntegralNum());
		// 岂止时间校验【允许活动不传时间，但起止时间不可颠倒】
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


	private MarketingActivitySet convertActivitySetBySaler(MarketingActivitySetParam activitySetParam, String organizationId, String organizationName) throws SuperCodeException {
		String title=activitySetParam.getActivityTitle();
		if (StringUtils.isBlank(title)) {
			throw new SuperCodeException("添加的活动设置标题不能为空", 500);
		}
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(activitySetParam.getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		activityTimeCheck(activitySetParam.getActivityStartDate(),activitySetParam.getActivityEndDate());
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setId(activitySetParam.getId());
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		mSet.setActivityId(activitySetParam.getActivityId());
		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber());
		// 门槛保存红包条件和每人每天上限
		MarketingActivitySetCondition condition = new MarketingActivitySetCondition();
		condition.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber() );
		condition.setParticipationCondition(activitySetParam.getParticipationCondition());
		condition.setConsumeIntegral(activitySetParam.getConsumeIntegralNum());
		String conditinoString = condition.toJsonString();
		mSet.setValidCondition(conditinoString);
		// 岂止时间校验【允许活动不传时间，但起止时间不可颠倒】
		mSet.setActivityStatus(1);
		mSet.setOrganizationId(organizationId);
		mSet.setOrganizatioIdlName(organizationName);
		
		mSetMapper.insert(mSet);
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
		}else if (null!=activityEndDate && null!=activityEndDate) {
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
	private void saveProductBatchs(List<MarketingActivityProductParam> maProductParams, Long activitySetId, int referenceRole) throws SuperCodeException {
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = new ArrayList<ProductAndBatchGetCodeMO>();
//		Map<String, MarketingActivityProduct> activityProductMap = new HashMap<String, MarketingActivityProduct>();
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
//					activityProductMap.put(productId + productBatchId, mActivityProduct);
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
		//如果是会员活动需要去绑定扫码连接到批次号
		if (referenceRole == RoleTypeEnum.MEMBER.getMemberType()) {
			String superToken = commonUtil.getSuperToken();
			String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,
					WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL);
			JSONObject obj = JSONObject.parseObject(body);
			int state = obj.getInteger("state");
			if (200 == state) {
				JSONArray arr = obj.getJSONArray("results");
				List<Map<String, Object>> params = commonService.getUrlToBatchParam(arr,
						marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,
						BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType());
				// 绑定生码批次到url
				String bindbatchBody = commonService.bindUrlToBatch(params, superToken);
				JSONObject bindBatchobj = JSONObject.parseObject(bindbatchBody);
				Integer batchstate = bindBatchobj.getInteger("state");
				if (null != batchstate && batchstate.intValue() != 200) {
					throw new SuperCodeException("请求码管理生码批次和url错误：" + bindbatchBody, 500);
				}
			} else {
				throw new SuperCodeException("通过产品及产品批次获取码信息错误：" + body, 500);
			}
		}
		//插入对应活动产品数据
		mProductMapper.batchDeleteByProBatchsAndRole(mList, referenceRole);
		mProductMapper.activityProductInsert(mList);
	}




	private void saveProductBatchsWithSaler(List<MarketingActivityProductParam> maProductParams, Long activitySetId) throws SuperCodeException {
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = new ArrayList<ProductAndBatchGetCodeMO>();
//		Map<String, MarketingActivityProduct> activityProductMap = new HashMap<String, MarketingActivityProduct>();
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
					mActivityProduct.setReferenceRole(MemberTypeEnums.SALER.getType());
//					activityProductMap.put(productId + productBatchId, mActivityProduct);
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

		//插入对应活动产品数据
		mProductMapper.batchDeleteByProBatchsAndRole(mList, MemberTypeEnums.SALER.getType());
		mProductMapper.activityProductInsert(mList);
	}



	/**
	 * 保存渠道数据
	 * @param mChannelParams
	 * @param activitySetId
	 * @throws SuperCodeException
	 */
	private void saveChannels(List<MarketingChannelParam> mChannelParams,Long activitySetId) throws SuperCodeException {
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
		maReceivingPageMapper.update(mReceivingPage);

		restResult.setState(200);
		restResult.setMsg("更新成功");
		return restResult;
	}

	private boolean validateParam(MarketingPageUpdateParam mUpdateParam) {
		// 校验更新中奖和领奖的参数;都执行了update所以参数要合法
		boolean validateResult = false;
		if (mUpdateParam == null){
			return  validateResult;
		}
		// 领取页校验
		MarketingReceivingPageParam marketingReceivingPageParam = mUpdateParam.getmReceivingPageParam();
		if (org.springframework.util.StringUtils.isEmpty(marketingReceivingPageParam)) {
			return  validateResult;
		}
		// 校验ID
		if (null==marketingReceivingPageParam.getId() || marketingReceivingPageParam.getId() <= 0  ){
			return  validateResult;
		}
		// 校验取值范围0-1 领取页是否显示
		if (!(marketingReceivingPageParam.getIsReceivePage() ==0 || marketingReceivingPageParam.getIsReceivePage() ==1) ){
			return  validateResult;
		}
		// 校验取值范围0-1 二维码是否显示
		if (!(marketingReceivingPageParam.getIsQrcodeView() ==0 || marketingReceivingPageParam.getIsQrcodeView() ==1) ){
			return  validateResult;
		}
		// 校验通过
		return  ! validateResult;
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
		logger.info("扫码接收到参数outerCodeId="+outerCodeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId);
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
		restResult.setResults(pMo);
		restResult.setState(200);
		return restResult;
	}

	public RestResult<String> updateActivitySetStatus(MarketingActivitySetStatusUpdateParam mUpdateStatus){
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
	public RestResult<MarketingActivitySet> getActivityBaseInfoByeditPage(Long activitySetId) {
		RestResult<MarketingActivitySet> restResult = new RestResult<>();
		// 校验
		if(activitySetId == null || activitySetId <= 0 ){
			restResult.setState(500);
			restResult.setMsg("活动id校验失败");
			return  restResult;
		}
		// 获取
		MarketingActivitySet marketingActivitySet = mSetMapper.selectById(activitySetId);
		// 返回
		restResult.setState(200);
		restResult.setMsg("success");
		restResult.setResults(marketingActivitySet);
		return  restResult;

	}

	/**
	 * 导购红包创建
	 * @param activitySetParam
	 * @return
	 */
//	@Transactional(rollbackFor = {SuperCodeException.class,RuntimeException.class})
	public RestResult<String> salerAdd(MarketingSalerActivityCreateParam activitySetParam) throws SuperCodeException{
		// 业务逻辑
		// 1新增set表信息
		// 2新增产品表信息
		// 3新增奖次表信息
		// 4新增渠道信息，暂无 TODO 本期无渠道，后期关联码管理的渠道
		// 5发送至码管理相关码信息 注意:导购不需要绑定码管理
		// 6异步:获取消息队列处理需要自动绑定活动的码信息
		// ******************************
		// 实现
		// 基础校验
		// 业务校验
		//
		// 多线程处理【数据转换，数据保存】
		// 返回客户端
		// 子线程手动事务处理事务处理
		// *******************************
		// 事务计数器
		AtomicInteger successNum = new AtomicInteger(0);
		// 事务参与计量器
		CyclicBarrier cb = new CyclicBarrier(TX_THREAD_NUM);
// step-1：获取实体
		// 获取非前端参数
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getMProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getMChannelParams();

// step-2：校验实体
		validateBasicBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);

// step-3：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		MarketingActivitySet mActivitySet = convertActivitySetBySaler(activitySetParam.getMActivitySetParam(),organizationId,organizationName);
		// 插入数据库后获取
		Long activitySetId= mActivitySet.getId();


		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId,cb,successNum);
		//保存商品批次活动总共批次参与的码总数【像码平台和营销库操作】
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ActivityTypeEnum.ACTIVITY_SALER.getType().intValue(),cb,successNum );



		// ****************************************end****************************************


		// 保存导购活动结果
		int finalSuccessNum = successNum.get();
		if(finalSuccessNum == TX_THREAD_NUM){
			return RestResult.success();
		}else{
			return RestResult.error("保存数据失败" ,null);
		}
	}

	private void saveProductBatchsWithThread(List<MarketingActivityProductParam> maProductParams, Long activitySetId, int intValue, CyclicBarrier cb, AtomicInteger successNum) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 初始化事务
				initTx();
				//==================================buziness-start===========================
				try {
					saveProductBatchsWithSaler(maProductParams,activitySetId);
					// 计数器成功加1
					successNum.addAndGet(1);
				} catch (SuperCodeException e) {
					logger.error("[保存导购活动奖次信息失败:{}]",e.getMessage());
					e.printStackTrace();
				}
				//==================================buziness-end=============================
				// 事务处理
				transControl(cb,successNum.get());
			}
		});
	}

	private void savePrizeTypesWithThread(List<MarketingPrizeTypeParam> mPrizeTypeParams, Long activitySetId, CyclicBarrier cb, AtomicInteger successNum) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				// 初始化事务
				initTx();
				//==================================buziness-start===========================
				try {
					savePrizeTypes(mPrizeTypeParams,activitySetId);
					// 计数器成功加1
					successNum.addAndGet(1);
				} catch (SuperCodeException e) {
					logger.error("[保存导购活动奖次信息失败:{}]",e.getMessage());
					e.printStackTrace();
				}
				//==================================buziness-end=============================
				// 事务处理
				transControl(cb,successNum.get());
			}
		});
	}

	/**
	 * 导购活动创建的业务校验
	 * @param activitySetParam
	 * @param maProductParams
	 * @param mPrizeTypeParams
	 */
	private void validateBizBySalerAdd(MarketingSalerActivityCreateParam activitySetParam, List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams) {


	}

	/**
	 * 导购活动创建的基础校验
	 * @param activitySetParam
	 * @param maProductParams
	 * @param mPrizeTypeParams
	 * @throws SuperCodeException
	 */
	private void validateBasicBySalerAdd(MarketingSalerActivityCreateParam activitySetParam
			,List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams)
			throws SuperCodeException{
		if(activitySetParam == null){
			throw new SuperCodeException("导购活动参数丢失001");
		}
//		if(activitySetParam == null){
//			throw new SuperCodeException("导购活动参数丢失001");
//		}
//		if(activitySetParam == null){
//			throw new SuperCodeException("导购活动参数丢失001");
//		}
//		if(activitySetParam == null){
//			throw new SuperCodeException("导购活动参数丢失001");
//		}
//		if(activitySetParam == null){
//			throw new SuperCodeException("导购活动参数丢失001");
//		}
		// 检查奖次类型:无db检验【基础检验】
		standActicityParamCheck.basePrizeTypeCheck(mPrizeTypeParams);

		//检查产品：无db校验【基础检验】
		standActicityParamCheck.baseProductBatchCheck(maProductParams);
	}



	@Autowired
	private TaskExecutor taskExecutor;
	/**
	 * 事务管理器
	 */
	ThreadLocal<PlatformTransactionManager> transm = new ThreadLocal<>();
	/**
	 * 事务状态ID
	 */
	ThreadLocal<TransactionStatus> transs = new ThreadLocal<>();
	/**
	 * 参与事务的线程数
	 */
	private static final int TX_THREAD_NUM = 2;

	/**
	 * 初始化子线程事务
	 */
	private void initTx() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		PlatformTransactionManager txManager = SpringContextUtil.getBean(PlatformTransactionManager.class);
		TransactionStatus status = txManager.getTransaction(def);
		transm.set(txManager);
		transs.set(status);
	}
	/**
	 * 多线程事务提交/回滚
	 * @param cb
	 * @param num
	 */
	private void transControl(  CyclicBarrier cb ,int num){
		try {
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}finally {
			commitOrRollback(num);
		}
	}
	private void commitOrRollback(int num) {
		PlatformTransactionManager txManager = (PlatformTransactionManager) transm.get();
		TransactionStatus status = (TransactionStatus) transs.get();
		if(TX_THREAD_NUM == num){
			txManager.commit(status);
		}else {
			txManager.rollback(status);

		}
	}


	/**
	 * 导购活动更新
	 * @param activitySetParam
	 * @return
	 */
	@Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
	public RestResult<String> salerUpdate(MarketingSalerActivityCreateParam activitySetParam) throws SuperCodeException {
		// 业务逻辑,先删除后更新:
		/**
		 * 删除 产品
		 * 删除 渠道
		 * 删除 奖次
		 * 修改 set主表
		 * 覆盖产品
		 *
		 * 新增
		 *
		 */
		haveActivitySetId(activitySetParam);

		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();


		AtomicInteger successNum = new AtomicInteger(0);
		// 事务参与计量器
		CyclicBarrier cb = new CyclicBarrier(TX_THREAD_NUM);
// step-1：获取实体
		// 获取非前端参数

		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getMProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getMChannelParams();

// step-2：校验实体
		validateBasicBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);



		// 先删后增
		mChannelMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		mPrizeTypeMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		mProductMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		MarketingActivitySetParam mActivitySetParam = activitySetParam.getMActivitySetParam();
		mSetMapper.update(changeDtoToDoWhenUpdate(mActivitySetParam,organizationId,organizationName));




		// 判断新选择的产品是否存在,存在则删除[覆盖式操作]
		if(!CollectionUtils.isEmpty(maProductParams)){
			for( MarketingActivityProductParam vo:maProductParams ){
				if(vo.getProductId() == null){
					throw new SuperCodeException("编辑需要传入productId");
				}
			}
			// 删除[导购]存在的原活动产品
			mProductMapper.deleteOldProducts(maProductParams);
		}


// step-3：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		MarketingActivitySet mActivitySet = convertActivitySetBySaler(activitySetParam.getMActivitySetParam(),organizationId,organizationName);
		// 插入数据库后获取
		Long activitySetId= mActivitySet.getId();


		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId,cb,successNum);
		//保存商品批次 [导购不像码平台发起调用]
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ActivityTypeEnum.ACTIVITY_SALER.getType().intValue(),cb,successNum );



		// ****************************************end****************************************


		// 保存导购活动结果
		int finalSuccessNum = successNum.get();
		if(finalSuccessNum == TX_THREAD_NUM){
			return RestResult.success();
		}else{
			// 外层事务回滚
			throw new SuperCodeException("保存数据失败...");
		}


 	}

	private void haveActivitySetId(MarketingSalerActivityCreateParam activitySetParam) throws SuperCodeException{
		try {
			if(StringUtils.isEmpty(activitySetParam.getMActivitySetParam().getId().toString())){
				throw new SuperCodeException("校验失败");
			}
		} catch (SuperCodeException e) {
			throw new SuperCodeException("活动Id不存在");
		}
	}

	/**
	 * 导购活动专用
	 * @param activitySetParam
	 * @param organizationId
	 * @param organizationName
	 * @return
	 * @throws SuperCodeException
	 */
	private MarketingActivitySet changeDtoToDoWhenUpdate(MarketingActivitySetParam activitySetParam,String organizationId,String organizationName) throws SuperCodeException {
		String title=activitySetParam.getActivityTitle();
		if (StringUtils.isBlank(title)) {
			throw new SuperCodeException("添加的活动设置标题不能为空", 500);
		}
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgIdWhenUpdate(activitySetParam.getActivityTitle(),activitySetParam.getId(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		activityTimeCheck(activitySetParam.getActivityStartDate(),activitySetParam.getActivityEndDate());
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		// 导购活动Id
		if(activitySetParam.getActivityId() != ActivityIdEnum.ACTIVITY_SALER.getId().intValue()){
			throw new SuperCodeException("非导购活动");
		}
		mSet.setActivityId(activitySetParam.getActivityId());


		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber());
		mSet.setId(activitySetParam.getId());
		// 门槛保存红包条件和每人每天上限
		MarketingActivitySetCondition condition = new MarketingActivitySetCondition();
		condition.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber() );
		condition.setParticipationCondition(activitySetParam.getParticipationCondition());
		String conditinoString = condition.toJsonString();
		mSet.setValidCondition(conditinoString);
		// 岂止时间校验【允许活动不传时间，但起止时间不可颠倒】
		mSet.setActivityStatus(1);
		mSet.setOrganizationId(organizationId);
		mSet.setOrganizatioIdlName(organizationName);
		return mSet;
	}

	public RestResult<MarketingSalerActivityCreateParam> detail(Long id) throws SuperCodeException {
		if(id == null || id<= 0){
			throw new SuperCodeException("活动设置ID不存在...");
		}

		return null;
	}

    /**
	 * 获取营销活动列表
	 */
    @Override
    protected List<MarketingSalerActivitySetMO> searchResult(DaoSearchWithOrganizationIdParam searchParams) throws Exception {
        // 查询满足条件的营销活动集合
        List<MarketingSalerActivitySetMO> list = mSetMapper.list(searchParams);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
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
        AccountCache userLoginCache = getUserLoginCache();
        mSetMapper.updateSalerActivitySetStatus(setStatusUpdateParam, userLoginCache.getUserId(), userLoginCache.getUserName());
        RestResult<String> restResult=new RestResult<String>();
        restResult.setState(200);
        restResult.setMsg("更新成功");
        return restResult;
    }

	/**
	 * 复制导购活动
	 * @param activitySetParam
	 * @return
	 */
	@Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
	public RestResult<String> salerCopy(MarketingSalerActivityCreateParam activitySetParam) throws SuperCodeException {
		// 业务逻辑,先插入主表后删除子表更新子表:
		/**
		 * 删除 产品
		 * 删除 渠道
		 * 删除 奖次
		 * 新增 set主表
		 * 获取主键[事务内依旧可以获取,当前mysql的事务隔离级别]
		 * 覆盖产品
		 *
		 * 新增
		 *
		 */
		haveActivitySetId(activitySetParam);
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();


		// 事务预提交结果计数器
		AtomicInteger successNum = new AtomicInteger(0);
		// 事务参与计量器
		CyclicBarrier cb = new CyclicBarrier(TX_THREAD_NUM);
// step-1：获取实体
		// 获取非前端参数

		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getMProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getMChannelParams();

// step-2：校验实体
		validateBasicBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);

// step-3：先删后增
		mChannelMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		mPrizeTypeMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		mProductMapper.deleteByActivitySetId(activitySetParam.getMActivitySetParam().getId());
		MarketingActivitySetParam mActivitySetParam = activitySetParam.getMActivitySetParam();
		// 插入完成携带主键
		MarketingActivitySet marketingActivitySet = changeDtoToDoWhenCopy(mActivitySetParam, organizationId, organizationName);
		mSetMapper.insert(marketingActivitySet);
		// 替换复制功能后[新增主表数据,先删或加子表数据]的主键
		activitySetParam.getMActivitySetParam().setId(marketingActivitySet.getId());



		// 判断新选择的产品是否存在,存在则删除[覆盖式操作]
		if(!CollectionUtils.isEmpty(maProductParams)){
			for( MarketingActivityProductParam vo:maProductParams ){
				if(vo.getProductId() == null){
					throw new SuperCodeException("编辑需要传入productId");
				}
			}
			// 删除[导购]存在的原活动产品
			mProductMapper.deleteOldProducts(maProductParams);
		}


// step-4：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		MarketingActivitySet mActivitySet = convertActivitySetBySaler(activitySetParam.getMActivitySetParam(),organizationId,organizationName);
		// 插入数据库后获取
		Long activitySetId= mActivitySet.getId();


		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId,cb,successNum);
		//保存商品批次 【导购不像码平台发起产品业务绑定】
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ActivityTypeEnum.ACTIVITY_SALER.getType().intValue(),cb,successNum );



		// ****************************************end****************************************


		// 保存导购活动结果
		int finalSuccessNum = successNum.get();
		if(finalSuccessNum == TX_THREAD_NUM){
			return RestResult.success();
		}else{
			// 外层事务回滚
			throw new SuperCodeException("保存数据失败...");
		}

	}

	private MarketingActivitySet changeDtoToDoWhenCopy(MarketingActivitySetParam activitySetParam, String organizationId, String organizationName) throws SuperCodeException {
		String title=activitySetParam.getActivityTitle();
		if (StringUtils.isBlank(title)) {
			throw new SuperCodeException("添加的活动设置标题不能为空", 500);
		}
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(activitySetParam.getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		activityTimeCheck(activitySetParam.getActivityStartDate(),activitySetParam.getActivityEndDate());
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		// 导购活动Id
		if(activitySetParam.getActivityId() != ActivityIdEnum.ACTIVITY_SALER.getId().intValue()){
			throw new SuperCodeException("非导购活动");
		}
		mSet.setActivityId(activitySetParam.getActivityId());


		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber());
		// 门槛保存红包条件和每人每天上限
		MarketingActivitySetCondition condition = new MarketingActivitySetCondition();
		condition.setEachDayNumber(activitySetParam.getEachDayNumber()==null ? 200:activitySetParam.getEachDayNumber() );
		condition.setParticipationCondition(activitySetParam.getParticipationCondition());
		String conditinoString = condition.toJsonString();
		mSet.setValidCondition(conditinoString);
		// 岂止时间校验【允许活动不传时间，但起止时间不可颠倒】
		mSet.setActivityStatus(1);
		mSet.setOrganizationId(organizationId);
		mSet.setOrganizatioIdlName(organizationName);
		return mSet;
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
		}
		marketingActivityCreateParam.setMActivitySetParam(marketingActivitySetParam);
		//获取拼接活动设置产品参数
		List<MarketingActivityProduct> marketingActivityProductList = mProductMapper.selectByActivitySetId(activitySetId.toString());
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
					marketingActivityProductParam.setProductBatchParams(Collections.singletonList(productBatchParam));
					mActivityProductParamMap.put(productId, marketingActivityProductParam);
				} else {
					ProductBatchParam productBatchParam = new ProductBatchParam();
					productBatchParam.setProductBatchId(product.getProductBatchId());
					productBatchParam.setProductBatchName(product.getProductBatchName());
					marketingActivityProductParam.getProductBatchParams().add(productBatchParam);
				}
			}
		}
		marketingActivityCreateParam.setMProductParams(new ArrayList<MarketingActivityProductParam>(mActivityProductParamMap.values()));
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
					MarketingChannel::getCustomerId ,marketingChannel -> {
					MarketingChannelParam marketingChannelParam = new MarketingChannelParam();
					BeanUtils.copyProperties(marketingChannel, marketingChannelParam);
					return marketingChannelParam;
			}));
			Set<MarketingChannelParam> MarketingChannelParam = getSonByFatherWithAllData(MarketingChannelParamMap);
			marketingActivityCreateParam.setMChannelParams(new ArrayList<MarketingChannelParam>(MarketingChannelParam));
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
		Set<String> mkSet = marketingChannelMap.keySet();
		if(mkSet.contains(channel.getCustomerSuperior())) {
			MarketingChannelParam parentChannel = marketingChannelMap.get(channel.getCustomerSuperior());
			List<MarketingChannelParam> childList = parentChannel.getChildrens();
			//如果父级的children为空，则说明第一次添加，需递归调用，如果不为空，则说明不是第一次添加，
			//以前已经递归调用过，父级以上的关系已添加过，不用再次递归，也无需返回实例。
			if(childList == null) {
				childList = new ArrayList<>();
				childList.add(channel);
				parentChannel.setChildrens(childList);
				reChannel = putChildrenChannel(marketingChannelMap, parentChannel);
			} else {
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
	
	public RestResult<MarketingReceivingPageParam> getPreviewParam(String uuid) {
		RestResult<MarketingReceivingPageParam> restResult=new RestResult<MarketingReceivingPageParam>();
		String value=redisUtil.get(RedisKey.ACTIVITY_PREVIEW_PREFIX+uuid);
		if (StringUtils.isBlank(value)) {
			restResult.setState(500);
			restResult.setMsg("扫码已过期请重新扫码预览");
			return restResult;
		}
		MarketingActivityPreviewParam mPreviewParam=JSONObject.parseObject(value, MarketingActivityPreviewParam.class);
		restResult.setResults(mPreviewParam.getmReceivingPageParam());
		restResult.setState(200);
		return restResult;
	}
	
}
