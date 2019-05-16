package com.jgw.supercodeplatform.marketing.service.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.check.activity.StandActicityParamCheck;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingReceivingPageMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPageUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.vo.activity.ReceivingAndWinningPageVO;

@Service
public class MarketingActivitySetService  {
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
	private CommonService commonService;
	
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
	public RestResult<String> update(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {
		
		return null;
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
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		mSet.setActivityId(activitySetParam.getActivityId());
		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber());
		mSet.setId(activitySetParam.getId());
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
	public RestResult<String> updatePage(MarketingPageUpdateParam mUpdateParam) {
		RestResult<String> restResult=new RestResult<String>();
		// 更新参数校验，中奖页和领取页参数
		boolean legal = validateParam(mUpdateParam);
		if (!legal){
			restResult.setState(500);
			restResult.setMsg("参数校验失败");
			return restResult;
		}

		// 保存领取页信息
		MarketingReceivingPageParam mReceivingPageParam=mUpdateParam.getmReceivingPageParam();
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
		RestResult restResult = new RestResult();
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


}
