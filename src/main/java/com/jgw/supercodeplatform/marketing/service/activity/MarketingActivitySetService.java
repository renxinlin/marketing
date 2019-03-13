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
	private CommonUtil commonUtil;
	
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
     * 创建活动
     * @param activitySetParam
     * @return
     * @throws SuperCodeException 
     */
	@Transactional
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
		//校验奖次信息
		if (null==mPrizeTypeParams || mPrizeTypeParams.isEmpty()) {
			throw new SuperCodeException("奖次信息不能为空", 500);
		}else {
			Set<String> set = new HashSet<String>();
			for (MarketingPrizeTypeParam prizeTypeParam:mPrizeTypeParams){
				Byte randomAmont=prizeTypeParam.getRandomAmount();
				if (null==randomAmont) {
					throw new SuperCodeException("是否固定金额不能为空", 500);
				}else if (randomAmont.equals((byte)0)) {
					//如果固定金额则不能小于1大于5000
					Integer amount=prizeTypeParam.getPrizeAmount();
					if (null==amount|| amount<1 ||amount>5000) {
						throw new SuperCodeException("金额参数非法，不能为空只能在1-5000以内", 500);
					}
					prizeTypeParam.setPrizeAmount(prizeTypeParam.getPrizeAmount()*100);//转换为分
				}else if (randomAmont.equals((byte)1)) {
					//如果是随机金额则校验随机金额取值
					Integer lowrand=prizeTypeParam.getLowRand();
					Integer highrand=prizeTypeParam.getHighRand();
					if (null==lowrand || null==highrand || lowrand.intValue()>=highrand.intValue()) {
						throw new SuperCodeException("随机金额取值范围不能为空且低取值不能大于等于高取值", 500);
					}
					if (lowrand.intValue()<1 || highrand.intValue()>5000) {
						throw new SuperCodeException("随机金额参数非法，低值和高值取值只能在1-5000以内", 500);
					}
				}
				Integer prizeProbability=prizeTypeParam.getPrizeProbability();
				if (null==prizeProbability || prizeProbability<0 || prizeProbability>100) {
					throw new SuperCodeException("概率参数非法prizeProbability="+prizeProbability, 500);
				}
				set.add(prizeTypeParam.getPrizeTypeName());
			}
			if (set.size()>1) {
				throw new SuperCodeException("奖项名称不能重复", 500);
			}
		}
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
        
		MarketingActivitySet existmActivitySet =mSetMapper.selectByTitleOrgId(mActivitySet.getActivityTitle(),organizationId);
		if (null!=existmActivitySet) {
			throw new SuperCodeException("您已设置过相同标题的活动不可重复设置", 500);
		}
		mActivitySet.setActivityStatus(1);
		mActivitySet.setOrganizationId(organizationId);
		mActivitySet.setOrganizatioIdlName(organizationName);
		mSetMapper.insert(mActivitySet);
		
		Long activitySetId= mActivitySet.getId();


        //待优化 校验商品批次是否被添加过
		for (MarketingActivityProductParam mProduct:maProductParams){
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
		int sumprizeProbability=0;
		for (MarketingPrizeTypeParam marketingPrizeTypeParam : mPrizeTypeParams) {
			Integer prizeProbability=marketingPrizeTypeParam.getPrizeProbability();
			MarketingPrizeType mPrizeType=new MarketingPrizeType();
			mPrizeType.setActivitySetId(activitySetId);
			mPrizeType.setPrizeAmount(marketingPrizeTypeParam.getPrizeAmount());
			mPrizeType.setPrizeProbability(prizeProbability);
			mPrizeType.setPrizeTypeName(marketingPrizeTypeParam.getPrizeTypeName());
			mPrizeType.setRandomAmount(marketingPrizeTypeParam.getRandomAmount());
			mPrizeType.setRealPrize((byte) 1);
			mList.add(mPrizeType);
			sumprizeProbability+=prizeProbability;
		}
		if (sumprizeProbability>100) {
			throw new SuperCodeException("概率参数非法，总数不能大于100", 500);
		}else if (sumprizeProbability<100){
			int i = 100-sumprizeProbability;
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
		try {
			String superToken=commonUtil.getSuperToken();
			String jsonData=JSONObject.toJSONString(productAndBatchGetCodeMOs);
			Map<String,String> headerMap=new HashMap<String, String>();
			headerMap.put("super-token", superToken);
			ResponseEntity<String>  response=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL, jsonData, headerMap);
			String body=response.getBody();
			JSONObject obj=JSONObject.parseObject(body);
			int state=obj.getInteger("state");
			if (200==state) {
				JSONArray array=obj.getJSONArray("results");
				if (array.isEmpty()) {
					throw new SuperCodeException("获取码管理批次信息为空请确保该产品批次已进行码关联", 500);
				}
				List<Map<String, Object>> bindBatchList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<array.size();i++) {
					JSONObject batchobj=array.getJSONObject(i);
					String productId=batchobj.getString("productId");
					String productBatchId=batchobj.getString("productBatchId");
					Long codeTotal=batchobj.getLong("codeTotal");
					String codeBatch=batchobj.getString("codeBatch");
					if (StringUtils.isBlank(productId)||StringUtils.isBlank(productBatchId)||StringUtils.isBlank(codeBatch) || null==codeTotal) {
						throw new SuperCodeException("获取码管理批次信息返回数据不合法有参数为空，对应产品id及产品批次为"+productId+","+productBatchId, 500);
					}
					Map<String, Object> batchMap=new HashMap<String, Object>();
					batchMap.put("batchId", codeBatch);
					batchMap.put("businessType", 1);
					batchMap.put("url", marketingDomain+WechatConstants.SCAN_CODE_JUMP_URL);
					bindBatchList.add(batchMap);
					
					MarketingActivityProduct mActivityProduct=activityProductMap.get(productId+productBatchId);
					if (null!=mActivityProduct) {
						mActivityProduct.setCodeTotalAmount(codeTotal);
						mActivityProduct.setCodeType(codeBatch);
						mList.add(mActivityProduct);
						codeSum+=codeTotal;
					}
				}
				//生码批次跟url绑定
				String bindJson=JSONObject.toJSONString(bindBatchList);
				ResponseEntity<String>  bindBatchresponse=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+WechatConstants.CODEMANAGER_BIND_BATCH_TO_URL, bindJson, headerMap);
				String batchBody=bindBatchresponse.getBody();
				JSONObject batchobj=JSONObject.parseObject(batchBody);
				Integer batchstate=batchobj.getInteger("state");
				if (null!=batchstate && batchstate.intValue()==200) {
					mProductMapper.activityProductInsert(mList);
				}else {
					throw new SuperCodeException("请求码管理生码批次和url错误："+bindBatchresponse.toString(), 500);
				}
			}else {
				throw new SuperCodeException("通过产品及产品批次获取码信息错误："+response.toString(), 500);
			}
			
		} catch (Exception e) {
			throw new SuperCodeException("获取码管理批次信息错误："+e.getLocalizedMessage(), 500);
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
	public RestResult<ScanCodeInfoMO> judgeActivityScanCodeParam(String codeId, String codeTypeId, String productId, String productBatchId) throws SuperCodeException, ParseException {
		logger.info("扫码接收到参数codeId="+codeId+",codeTypeId="+codeTypeId+",productId="+productId+",productBatchId="+productBatchId);
		RestResult<ScanCodeInfoMO> restResult=new RestResult<ScanCodeInfoMO>();
		if (StringUtils.isBlank(codeId) || StringUtils.isBlank(codeId)||StringUtils.isBlank(productId)||StringUtils.isBlank(productBatchId)) {
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
		ScanCodeInfoMO pMo=new ScanCodeInfoMO();
		pMo.setCodeId(codeId);
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

}
