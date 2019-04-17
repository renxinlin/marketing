package com.jgw.supercodeplatform.marketing.service.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
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
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWinningPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
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
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	private CodeEsService codeEsService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CommonService commonService;
	
	@Value("${rest.codemanager.url}")
	private String codeManagerUrl;

	@Value("${marketing.domain.url}")
	private String marketingDomain;

	private static Object mqlock=new Object();
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
	 * 创建活动
	 * @param activitySetParam
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional(rollbackFor = SuperCodeException.class)
	public RestResult<String> add(MarketingActivityCreateParam activitySetParam) throws SuperCodeException {
		List<MarketingChannelParam> mChannelParams=activitySetParam.getmChannelParams();
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		//获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		//获取领取页参数
		MarketingReceivingPageParam mReceivingPageParam=activitySetParam.getmReceivingPageParam();
		//获取中奖页参数
		MarketingWinningPageParam mWinningPageParam=activitySetParam.getmWinningPageParam();
		//获取活动实体
		MarketingActivitySet mActivitySet = convertActivitySet(activitySetParam.getmActivitySetParam());

		//校验产品及批次数据是否为空
		if (null==maProductParams || maProductParams.isEmpty()) {
			throw new SuperCodeException("产品信息不能为空", 500);
		}

		// 活动起始时间空串处理
		if("".equals(mActivitySet.getActivityStartDate())){
            mActivitySet.setActivityStartDate(null);
        }

		if("".equals(mActivitySet.getActivityEndDate())){
            mActivitySet.setActivityEndDate(null);
        }
		//校验奖次信息
		if (null==mPrizeTypeParams || mPrizeTypeParams.isEmpty()) {
			throw new SuperCodeException("奖次信息不能为空", 500);
		}else {
			Set<String> set = new HashSet<String>();
			for (MarketingPrizeTypeParam prizeTypeParam:mPrizeTypeParams){
				Byte randomAmont=prizeTypeParam.getIsRrandomMoney();
				if (null==randomAmont) {
					throw new SuperCodeException("是否固定金额不能为空", 500);
				}else if (randomAmont.equals((byte)0)) {
					//如果固定金额则不能小于1大于5000
					Float amount=prizeTypeParam.getPrizeAmount();
					if (null==amount|| amount<1 ||amount>5000) {
						throw new SuperCodeException("金额参数非法，不能为空只能在1-5000以内", 500);
					}
					prizeTypeParam.setPrizeAmount(prizeTypeParam.getPrizeAmount());//转换为分
				}else if (randomAmont.equals((byte)1)) {
					//如果是随机金额则校验随机金额取值
					Float lowrand=prizeTypeParam.getLowRand();
					Float highrand=prizeTypeParam.getHighRand();
					if (null==lowrand || null==highrand || lowrand >=highrand) {
						throw new SuperCodeException("随机金额取值范围不能为空且低取值不能大于等于高取值", 500);
					}
					if (lowrand<1 || highrand>5000) {
						throw new SuperCodeException("随机金额参数非法，低值和高值取值只能在1-5000以内", 500);
					}
				}
				Integer prizeProbability=prizeTypeParam.getPrizeProbability();
				if (null==prizeProbability || prizeProbability<0 || prizeProbability>100) {
					throw new SuperCodeException("概率参数非法prizeProbability="+prizeProbability, 500);
				}
				set.add(prizeTypeParam.getPrizeTypeName());
			}
			if (set.size()<mPrizeTypeParams.size()) {
				throw new SuperCodeException("奖项名称不能重复", 500);
			}
		}
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();

		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(mActivitySet.getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}


		// 岂止时间校验【允许活动不传时间，但起止时间不可颠倒】
		String activityEndDate = mActivitySet.getActivityEndDate();
		String activityStartDate = mActivitySet.getActivityStartDate();
		Date endDate = null;
		Date startDate = null;
		if (StringUtils.isNotBlank(activityEndDate)
				&& StringUtils.isNotBlank(activityStartDate)) {
			try {
				endDate = DateUtil.parse(activityEndDate,"yyyy-MM-dd");
				startDate = DateUtil.parse(activityStartDate,"yyyy-MM-dd");
				if(startDate.after(endDate)){
					throw new SuperCodeException("日期起止时间不合法",500);
				}
			} catch (ParseException e) {
				throw new SuperCodeException("日期起止时间不合法",500);
			}
		}

		mActivitySet.setActivityStatus(1);
		mActivitySet.setOrganizationId(organizationId);
		mActivitySet.setOrganizatioIdlName(organizationName);
		mSetMapper.insert(mActivitySet);

		Long activitySetId= mActivitySet.getId();

		Set set = new HashSet();
		int size = 0;
		for (MarketingActivityProductParam mProduct:maProductParams){
			// 去重
			String productId = mProduct.getProductId();
			for (ProductBatchParam productBatch:mProduct.getProductBatchParams()){
				String productBatchId = productBatch.getProductBatchId();
				set.add(productId+productBatchId);
				size++;
			}
		}
		if(set.size() != size){
			throw new SuperCodeException("产品批次存在重复",500);
		}

		//待优化 校验商品批次是否被添加过
		for (MarketingActivityProductParam mProduct:maProductParams){
			// 去重

			for (ProductBatchParam productBatch:mProduct.getProductBatchParams()){
				if (mProductMapper.selectByProductAndProductBatchId(mProduct.getProductId(),productBatch.getProductBatchId())!=null){
					throw new SuperCodeException("商品"+mProduct.getProductName()+"的批次"+productBatch.getProductBatchName()+"已经被添加过了无法再次添加", 500);
				}
			}
		}

		if (null!=mChannelParams && mChannelParams.size()!=0) {
			//保存渠道
			saveChannels(mChannelParams,activitySetId);
		}

		//保存奖次
		savePrizeTypes(mPrizeTypeParams,activitySetId);

		//保存中奖页
		saveWinningPage(mWinningPageParam,activitySetId);

		//保存领取页
		saveReceivingPage(mReceivingPageParam,activitySetId);

		//保存商品批次活动总共批次参与的码总数
		Long codeSum=saveProductBatchs(maProductParams,activitySetId);
		mSetMapper.updateCodeTotalNum(activitySetId,codeSum);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}

	private MarketingActivitySet convertActivitySet(MarketingActivitySetParam activitySetParam) throws SuperCodeException {
		String title=activitySetParam.getActivityTitle();
		if (StringUtils.isBlank(title)) {
			throw new SuperCodeException("添加的活动设置标题不能为空", 500);
		}
		MarketingActivitySet mSet=new MarketingActivitySet();
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		mSet.setActivityId(activitySetParam.getActivityId());
		mSet.setActivityRangeMark(activitySetParam.getActivityRangeMark());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
		mSet.setEachDayNumber(activitySetParam.getEachDayNumber());
		return mSet;
	}
	/**
	 * 保存中奖页
	 * @param mWinningPageParam
	 * @param activitySetId
	 */
	private void saveWinningPage(MarketingWinningPageParam mWinningPageParam, Long activitySetId) throws  SuperCodeException{
		// 校验
		if ( StringUtils.isBlank(mWinningPageParam.getTemplateId())){
			throw new SuperCodeException("中奖页参数不全", 500);
		}
		if (activitySetId == null || activitySetId <= 0){
			throw new SuperCodeException("中奖页参数不全", 500);
		}
		if (mWinningPageParam.getLoginType() == null ){
			throw new SuperCodeException("中奖页参数不全", 500);
		}

		// 保存
		MarketingWinningPage mWinningPage=new MarketingWinningPage();
		mWinningPage.setLoginType(mWinningPageParam.getLoginType());
		mWinningPage.setTemplateId(mWinningPageParam.getTemplateId());
		mWinningPage.setActivitySetId(activitySetId);
		marWinningPageMapper.insert(mWinningPage);
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
	 * @return
	 * @throws SuperCodeException
	 */
	private Long saveProductBatchs(List<MarketingActivityProductParam> maProductParams, Long activitySetId) throws SuperCodeException {
		List<MarketingActivityProduct> mList=new ArrayList<MarketingActivityProduct>();
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs=new ArrayList<ProductAndBatchGetCodeMO>();
		Map<String, MarketingActivityProduct> activityProductMap=new HashMap<String, MarketingActivityProduct>();

		Long codeSum=0L;
		for (MarketingActivityProductParam marketingActivityProductParam : maProductParams) {
			String productId=marketingActivityProductParam.getProductId();
			List<ProductBatchParam> batchParams=marketingActivityProductParam.getProductBatchParams();
			if (null!=batchParams && !batchParams.isEmpty()) {
				ProductAndBatchGetCodeMO productAndBatchGetCodeMO=new ProductAndBatchGetCodeMO();
				List<Map<String, String>>productBatchList=new ArrayList<Map<String,String>>();
				for (ProductBatchParam prBatchParam : batchParams) {
					String productBatchId=prBatchParam.getProductBatchId();
					MarketingActivityProduct mActivityProduct=new MarketingActivityProduct();
					mActivityProduct.setActivitySetId(activitySetId);
					mActivityProduct.setProductBatchId(productBatchId);
					mActivityProduct.setProductBatchName(prBatchParam.getProductBatchName());
					mActivityProduct.setProductId(marketingActivityProductParam.getProductId());
					mActivityProduct.setProductName(marketingActivityProductParam.getProductName());
					activityProductMap.put(productId+productBatchId, mActivityProduct);
					//拼装请求码管理批次信息接口商品批次参数
					Map<String, String> batchmap=new HashMap<String, String>();
					batchmap.put("productBatchId", prBatchParam.getProductBatchId());
					productBatchList.add(batchmap);
				}
				//拼装请求码管理批次信息接口商品参数
				productAndBatchGetCodeMO.setProductBatchList(productBatchList);
				productAndBatchGetCodeMO.setProductId(productId);
				productAndBatchGetCodeMOs.add(productAndBatchGetCodeMO);
			}
		}
			String superToken=commonUtil.getSuperToken();
			String body=commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken);
			JSONObject obj=JSONObject.parseObject(body);
			int state=obj.getInteger("state");
			if (200==state) {
				JSONArray arr=obj.getJSONArray("results");
				List<Map<String, Object>> params=commonService.getUrlToBatchParam(arr, marketingDomain+WechatConstants.SCAN_CODE_JUMP_URL,5);
				//绑定生码批次到url
				String bindbatchBody=commonService.bindUrlToBatch(params, superToken);
				JSONObject bindBatchobj=JSONObject.parseObject(bindbatchBody);
				Integer batchstate=bindBatchobj.getInteger("state");
				if (null!=batchstate && batchstate.intValue()==200) {
					for (int i=0;i<arr.size();i++) {
						JSONObject batchobj=arr.getJSONObject(i);
						String productId=batchobj.getString("productId");
						String productBatchId=batchobj.getString("productBatchId");
						Long codeTotal=batchobj.getLong("codeTotal");
						String codeBatch=batchobj.getString("codeBatch");
						MarketingActivityProduct mActivityProduct=activityProductMap.get(productId+productBatchId);
						if (null!=mActivityProduct) {
							mActivityProduct.setCodeTotalAmount(codeTotal);
							mActivityProduct.setCodeType(codeBatch);
							mList.add(mActivityProduct);
							codeSum+=codeTotal;
						}
					}
					mProductMapper.activityProductInsert(mList);
				}else {
					throw new SuperCodeException("请求码管理生码批次和url错误："+bindbatchBody, 500);
				}
			}else {
				throw new SuperCodeException("通过产品及产品批次获取码信息错误："+body, 500);
			}

		if (null==codeSum || codeSum.intValue()<1) {
			throw new SuperCodeException("添加的产品批次的码关联数量小于1无法参与活动", 500);
		}
		return codeSum;
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

		MarketingWinningPageParam mWinningPageParam=mUpdateParam.getmWinningPageParam();
		MarketingWinningPage mWinningPage=new MarketingWinningPage();
		mWinningPage.setId(mWinningPageParam.getId());
		mWinningPage.setLoginType(mWinningPageParam.getLoginType());
		mWinningPage.setTemplateId(mWinningPageParam.getTemplateId());
		marWinningPageMapper.update(mWinningPage);

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
		if (marketingReceivingPageParam.getId() <= 0  ){
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
		// 中奖页校验
		MarketingWinningPageParam marketingWinningPageParam = mUpdateParam.getmWinningPageParam();
		if (org.springframework.util.StringUtils.isEmpty(marketingWinningPageParam)) {
			return  validateResult;
		}
		// 校验ID
		if ( marketingWinningPageParam.getId() <= 0  ){
			return  validateResult;
		}
		// 1手机 2 微信
		Byte loginType = marketingWinningPageParam.getLoginType();
		if (loginType != 1 && loginType != 2){
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
	 * @param codeId
	 * @return
	 * @throws SuperCodeException
	 * @throws ParseException
	 */
	public RestResult<ScanCodeInfoMO> judgeActivityScanCodeParam(String outerCodeId, String codeTypeId, String productId, String productBatchId) throws ParseException {
		logger.info("扫码接收到参数outerCodeId="+outerCodeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId);
		RestResult<ScanCodeInfoMO> restResult=new RestResult<ScanCodeInfoMO>();
		if (StringUtils.isBlank(outerCodeId) || StringUtils.isBlank(outerCodeId)||StringUtils.isBlank(productId)||StringUtils.isBlank(productBatchId)) {
			restResult.setState(500);
			restResult.setMsg("接收到码平台扫码信息有空值");
			return restResult;
		}
		//1、判断该码批次是否参与活动
		MarketingActivityProduct mProduct=mProductMapper.selectByProductAndProductBatchId(productId,productBatchId);
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
		Long codeCount=codeEsService.countByCode(outerCodeId, codeTypeId);
		logger.info("扫码方法=====：根据codeId="+outerCodeId+",codeTypeId="+codeTypeId+"获得的扫码记录次数为="+codeCount);
		if (null!=codeCount && codeCount.intValue()>=1) {
			restResult.setState(500);
			restResult.setMsg("该码已参与过活动不能重复参与");
			return restResult;
		}
		//如果该活动已扫的码大于等于该活动参与的码总数则返回错误
		Long scanSum=codeEsService.countByActivitySetId(activitySetId);
		if (null!=scanSum && scanSum.intValue()>=mActivitySet.getCodeTotalNum().intValue()) {
			restResult.setState(500);
			restResult.setMsg("该活动已扫码数量"+scanSum+"已达到活动设置的活动码数量"+mActivitySet.getCodeTotalNum()+"，请联系管理员");
			return restResult;
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
	 * 如果mq消费失败如何保证重复消费时不导致活动码数量非法增加
	 * 处理码管理平台新绑定的产品批次和生码批次
	 * @param batchList
	 */
	public void handleNewBindBatch(List<Map<String, Object>> batchList) {
		synchronized (mqlock) {
			Map<Long, Long> activityCodeSumMap=new HashMap<Long, Long>();
			List<Map<String, Object>> bindBatchList=new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : batchList) {
				Object productId=map.get("productId");
				Object productBatchId=map.get("productBatchId");
				Object codeTotal=map.get("codeTotal");
				Object codeBatch=map.get("codeBatch");
				logger.info("收到mq:productId="+productId+",productBatchId="+productBatchId+",codeTotal="+codeTotal+",codeBatch="+codeBatch);
				if (null==productId || null==productBatchId ||null==codeTotal|| null==codeBatch) {
					logger.error("获取码管理平台推送的新增批次mq消息，值有空值productId="+productId+",productBatchId="+productBatchId+",codeTotal="+codeTotal+",codeBatch="+codeBatch);
					continue;
				}
				Long codeTotalLon=Long.parseLong(String.valueOf(codeTotal));
				String strProductId=String.valueOf(productId);
				String strProductBatchId=String.valueOf(productBatchId);
				MarketingActivityProduct pActivityProduct=mProductMapper.selectByProductAndProductBatchId(strProductId, strProductBatchId);
				if (null!=pActivityProduct) {
					Long activitySetId=pActivityProduct.getActivitySetId();
					MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
					if (null==mActivitySet ) {
						return;
					}
					Integer autoFecth=mActivitySet.getAutoFetch();
					if (null==autoFecth || autoFecth.intValue()==2) {
						return;
					}
					Long activityCodeSum=activityCodeSumMap.get(activitySetId);
					if (null==activityCodeSum) {
						activityCodeSumMap.put(activitySetId, codeTotalLon);
					}else {
						activityCodeSumMap.put(activitySetId,  codeTotalLon+activityCodeSum);
					}

					Map<String, Object> batchMap=new HashMap<String, Object>();
					batchMap.put("batchId", codeBatch);
					batchMap.put("businessType", 1);
					batchMap.put("url", marketingDomain+WechatConstants.SCAN_CODE_JUMP_URL);
					bindBatchList.add(batchMap);
				}
			}
			try {
				if (bindBatchList.isEmpty()) {
					return;
				}
				//绑定生码批次与url的关系
				//生码批次跟url绑定
				String bindJson=JSONObject.toJSONString(bindBatchList);
				ResponseEntity<String>  bindBatchresponse=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+WechatConstants.CODEMANAGER_BIND_BATCH_TO_URL, bindJson, null);
				String batchBody=bindBatchresponse.getBody();
				JSONObject batchobj=JSONObject.parseObject(batchBody);
				Integer batchstate=batchobj.getInteger("state");
//				commonUtil.getSuperToken();
				if (batchstate.intValue()!=200) {
					logger.error("处理码管理推送的mq消息时绑定生码批次与url的关系出错，错误信息："+bindBatchresponse.toString()+",批次信息："+bindJson);
					return;
				}
				if (!activityCodeSumMap.isEmpty()){
					for(Long activitySetid:activityCodeSumMap.keySet()) {
						Long codeNum=activityCodeSumMap.get(activitySetid);
						synchronized (this) {
							mSetMapper.addCodeTotalNum(codeNum,activitySetid);
						}
					}
				}
			} catch (SuperCodeException e) {
				e.printStackTrace();
			}
		}

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
