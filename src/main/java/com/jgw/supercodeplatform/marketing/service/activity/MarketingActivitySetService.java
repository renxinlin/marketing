package com.jgw.supercodeplatform.marketing.service.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingReceivingPageMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingWinningPageMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPageUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWinningPageParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.vo.activity.ReceivingAndWinningPageVO;

@Service
public class MarketingActivitySetService extends CommonUtil {
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
	private MarketingActivityMapper mActivityMapper;
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
	@Transactional
	public RestResult<String> add(MarketingActivitySetParam activitySetParam) throws SuperCodeException {
		List<MarketingChannelParam> mChannelParams=activitySetParam.getmChannelParams();
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		MarketingReceivingPageParam mReceivingPageParam=activitySetParam.getmReceivingPageParam();
		MarketingWinningPageParam mWinningPageParam=activitySetParam.getmWinningPageParam();
		MarketingActivitySet mActivitySetParam = activitySetParam.getmActivitySetParam();
		MarketingActivity mActivityParam = activitySetParam.getmActivityParam();
		
		if (null==mPrizeTypeParams || mPrizeTypeParams.isEmpty()) {
			throw new SuperCodeException("奖次信息不能为空", 500);
		}

		List list = new ArrayList();
		for (MarketingPrizeTypeParam prizeTypeParam:mPrizeTypeParams){
			list.add(prizeTypeParam.getPrizeTypeName());
		}

		for  (int i = 0 ;i<list.size()-1;i++)  {
			for  (int j = list.size()-1;j>i;j--)  {
				if  (list.get(j).equals(list.get(i)))  {
					throw new SuperCodeException("奖项名称不能重复", 500);
				}
			}
		}

		if (null==maProductParams || maProductParams.isEmpty()) {
			throw new SuperCodeException("产品信息不能为空", 500);
		}
		


		mActivityMapper.addActivity(mActivityParam);
		mActivitySetParam.setActivityId(mActivityParam.getId());
		mActivitySetParam.setActivityStatus(1);
		mActivitySetParam.setOrganizationId(getOrganizationId());
		mActivitySetParam.setOrganizatioIdlName(getOrganizationName());
		if (mActivitySetParam.getActivityTitle()==null){
			throw new SuperCodeException("活动标题不能为空", 500);
		}
		mSetMapper.addActivitySet(mActivitySetParam);
		Long activitySetId= mActivityParam.getId();

		if (null!=mChannelParams && mChannelParams.size()!=0) {
			//保存渠道
			saveChannels(mChannelParams,activitySetId);
		}

		for (MarketingActivityProductParam mProduct:maProductParams){
			for (ProductBatchParam productBatch:mProduct.getBatchParams()){
				if (mProductMapper.selectByProductAndProductBatchId(mProduct.getProductId(),productBatch.getProductBatchId())==null){
					//保存商品批次
					saveProductBatchs(maProductParams,activitySetId);
				}else{
					throw new SuperCodeException("该批次已经被添加过了无法再次添加", 500);
				}
			}
		}


		
		//保存奖次
		savePrizeTypes(mPrizeTypeParams,activitySetId);
		
		//保存中奖页
		saveWinningPage(mWinningPageParam,activitySetId);
		
		//保存领取页
		saveReceivingPage(mReceivingPageParam,activitySetId);
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("成功");
		return restResult;
	}
	
	/**
	 * 保存中奖页
	 * @param mWinningPageParam
	 * @param activitySetId
	 */
	private void saveWinningPage(MarketingWinningPageParam mWinningPageParam, Long activitySetId) {
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
	private void saveReceivingPage(MarketingReceivingPageParam mReceivingPageParam, Long activitySetId) {
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
		int total = 0;
		for (MarketingPrizeTypeParam marketingPrizeTypeParam : mPrizeTypeParams) {
			MarketingPrizeType mPrizeType=new MarketingPrizeType();
			mPrizeType.setActivitySetId(activitySetId);
			mPrizeType.setPrizeAmount(marketingPrizeTypeParam.getPrizeAmount());
			mPrizeType.setPrizeProbability(marketingPrizeTypeParam.getPrizeProbability());
			mPrizeType.setPrizeTypeName(marketingPrizeTypeParam.getPrizeTypeName());
			mPrizeType.setRandomAmount(marketingPrizeTypeParam.getRandomAmount());
			mPrizeType.setRealPrize((byte) 1);
			total = total+mPrizeType.getPrizeProbability();
			mList.add(mPrizeType);
		}
		if (total>100){
			throw new SuperCodeException("中奖概率有误", 500);
		}else if (total<100){
			int i = 100-total;
			MarketingPrizeType NoReal=new MarketingPrizeType();
			NoReal.setActivitySetId(activitySetId);
			NoReal.setPrizeAmount(0);
			NoReal.setPrizeProbability(i);
			NoReal.setPrizeTypeName("未中奖");
			NoReal.setRandomAmount((byte) 0);
			NoReal.setRealPrize((byte) 0);
			mList.add(NoReal);
		}
		mPrizeTypeMapper.batchInsert(mList);
	}
	/**
	 * 保存产品批次
	 * @param maProductParams
	 * @param activitySetId
	 * @throws SuperCodeException 
	 */
	private void saveProductBatchs(List<MarketingActivityProductParam> maProductParams, Long activitySetId) throws SuperCodeException {
		List<MarketingActivityProduct> mList=new ArrayList<MarketingActivityProduct>();
		for (MarketingActivityProductParam marketingActivityProductParam : maProductParams) {
			List<ProductBatchParam> batchParams=marketingActivityProductParam.getBatchParams();
			if (null!=batchParams && !batchParams.isEmpty()) {
				for (ProductBatchParam prBatchParam : batchParams) {
					MarketingActivityProduct mActivityProduct=new MarketingActivityProduct();
					mActivityProduct.setActivitySetId(activitySetId);
					mActivityProduct.setProductBatchId(prBatchParam.getProductBatchId());
					mActivityProduct.setProductBatchName(prBatchParam.getProductBatchName());
					mActivityProduct.setProductId(marketingActivityProductParam.getProductId());
					mActivityProduct.setProductName(marketingActivityProductParam.getProductName());
					mList.add(mActivityProduct);
				}
			}
		}
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
			String customerCode=marketingChannelParam.getCustomerCode();
			MarketingChannel mChannel=new MarketingChannel();
			mChannel.setActivitySetId(activitySetId);
			mChannel.setCustomerCode(customerCode);
			mChannel.setCustomerName(marketingChannelParam.getCustomerName());
			mChannel.setCustomerSuperior(marketingChannelParam.getCustomerSuperior());
			mChannel.setCustomerSuperiorType(marketingChannelParam.getCustomerSuperiorType());
			mChannel.setCustomerType(customerType);
			mList.add(mChannel);
			List<MarketingChannelParam> childrens=marketingChannelParam.getChildrens();
			recursiveCreateChannel(customerCode,customerType,activitySetId,childrens,mList);
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
			String customerCode=marketingChannelParam.getCustomerCode();
			MarketingChannel mChannel=new MarketingChannel();
			mChannel.setActivitySetId(activitySetId);
			mChannel.setCustomerCode(customerCode);
			mChannel.setCustomerName(marketingChannelParam.getCustomerName());
			mChannel.setCustomerSuperior(parentCustomerCode);
			mChannel.setCustomerSuperiorType(parentCustomerType);
			mChannel.setCustomerType(customerType);
			mList.add(mChannel);
			List<MarketingChannelParam> childrens2=marketingChannelParam.getChildrens();
			recursiveCreateChannel(customerCode,customerType,activitySetId,childrens2,mList);
		}
	}
	/**
	 * 更新领取页中奖页
	 * @param mUpdateParam
	 * @return
	 */
	@Transactional
	public RestResult<String> updatePage(MarketingPageUpdateParam mUpdateParam) {
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
		
		RestResult<String> restResult=new RestResult<String>();
		restResult.setState(200);
		restResult.setMsg("更新成功");
		return restResult;
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
	public ScanCodeInfoMO judgeActivityScanCodeParam(String codeId, String codeTypeId, String productId, String productBatchId) throws SuperCodeException, ParseException {
		logger.info("扫码接收到参数codeId="+codeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId);
		if (StringUtils.isBlank(codeId) || StringUtils.isBlank(codeId)||StringUtils.isBlank(productId)||StringUtils.isBlank(productBatchId)) {
			throw new SuperCodeException("接收到码平台扫码信息有空值", 500);
		}
    	//1、判断该码批次是否参与活动
		MarketingActivityProduct mProduct=mProductMapper.selectByProductAndProductBatchId(productId,productBatchId);
		if (null==mProduct) {
			throw new SuperCodeException("该码对应的产品批次未参与活动", 500);
		}
		Long activitySetId=mProduct.getActivitySetId();
    	//2、判断该活动是否存在及是否已经停用
    	MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
    	if (null==mActivitySet) {
    		throw new SuperCodeException("活动已被删除无法参与", 500);
    	}
    	Integer activityStatus= mActivitySet.getActivityStatus();
    	if (null==activityStatus || 0==activityStatus) {
    		throw new SuperCodeException("活动已停止", 500);
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
    			throw new SuperCodeException("活动还未开始", 500);
			}
		}
    	
    	if (StringUtils.isNotBlank(enddate)) {
    		long endTime=format.parse(enddate).getTime();
    		if (currentTime>endTime) {
    			throw new SuperCodeException("活动已结束", 500);
			}
		}
		ScanCodeInfoMO pMo=new ScanCodeInfoMO();
		pMo.setCodeId(codeId);
		pMo.setCodeTypeId(codeTypeId);
		pMo.setProductBatchId(productBatchId);
		pMo.setProductId(productId);
		pMo.setActivitySetId(activitySetId);
		return pMo;
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

}
