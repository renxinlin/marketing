package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.check.activity.StandActicityParamCheck;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingChannelMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateNewParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.*;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.pojo.cache.AccountCache;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlUnBindDto;
import com.jgw.supercodeplatform.utils.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class MarketingActivitySalerSetService   {
	protected static Logger logger = LoggerFactory.getLogger(MarketingActivitySalerSetService.class);

	@Autowired
	private MarketingActivitySetMapper mSetMapper;

	@Autowired
	private MarketingActivitySetService marketingActivitySetService;

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
	private MarketingActivityChannelService channelService;

	@Value("${rest.codemanager.url}")
	private String codeManagerUrl;

	@Value("${marketing.domain.url}")
	private String marketingDomain;


	/**
	 * 导购活动更新:存在全局锁导致的死锁:需要索引
	 * @param activitySetParam
	 * @return
	 */
	@Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
	public RestResult<String> salerUpdate(MarketingSalerActivityUpdateParam activitySetParam) throws SuperCodeException, BrokenBarrierException, InterruptedException {
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


		// 获取非前端参数

		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getmChannelParams();

// step-2：校验实体
		validateBasicBySalerUpdate(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerUpdate(activitySetParam,maProductParams,mPrizeTypeParams);


// step-3：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		MarketingActivitySalerSetUpdateParam mActivitySetParam = activitySetParam.getmActivitySetParam();
		mSetMapper.update(changeDtoToDoWhenUpdate(mActivitySetParam,organizationId,organizationName));

		// 插入数据库后获取
		Long activitySetId= mActivitySetParam.getId();


		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId);

		//保存商品批次 [导购不像码平台发起调用]
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ReferenceRoleEnum.ACTIVITY_SALER.getType().intValue());
//		savePrizeTypes(mPrizeTypeParams,activitySetId);
//		saveProductBatchsWithSaler(maProductParams,activitySetId);

		return RestResult.success();


	}

	private void validateBizBySalerUpdate(MarketingSalerActivityUpdateParam activitySetParam, List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams) {

	}


	/**
	 * 导购红包创建
	 * @param activitySetParam
	 * @return
	 */
	@Transactional(rollbackFor = {SuperCodeException.class,RuntimeException.class})
	public RestResult<String> salerAdd(MarketingSalerActivityCreateNewParam activitySetParam) throws SuperCodeException {
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
// step-1：获取实体
		// 获取非前端参数
		String organizationId=commonUtil.getOrganizationId();
		String organizationName=commonUtil.getOrganizationName();
		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getmChannelParams();

// step-2：校验实体
		validateBasicBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerAdd(activitySetParam,maProductParams,mPrizeTypeParams);

// step-3：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		MarketingActivitySet mActivitySet = convertActivitySetBySalerAdd(activitySetParam.getmActivitySetParam(),organizationId,organizationName);
		// 插入数据库后获取id
		mSetMapper.insert(mActivitySet);
		Long activitySetId= mActivitySet.getId();



		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId);
		//保存商品批次活动总共批次参与的码总数【像码平台和营销库操作】
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ReferenceRoleEnum.ACTIVITY_SALER.getType().intValue());
		return RestResult.success();

	}

	/**
	 * 导购活动创建的基础校验
	 * @param activitySetParam
	 * @param maProductParams
	 * @param mPrizeTypeParams
	 * @throws SuperCodeException
	 */
	private void validateBasicBySalerAdd(MarketingSalerActivityCreateNewParam activitySetParam
			,List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams)
			throws SuperCodeException{
		if(activitySetParam == null){
			throw new SuperCodeException("导购活动参数丢失001");
		}
		// 检查奖次类型:无db检验【基础检验】
		standActicityParamCheck.basePrizeTypeCheck(mPrizeTypeParams);

		//检查产品：无db校验【基础检验】
		standActicityParamCheck.baseProductBatchCheck(maProductParams);
	}

	/**
	 * 复制导购活动
	 * 导购活动更新:存在全局锁导致的死锁:需要索引
	 * @param activitySetParam
	 * @return
	 */
	@Transactional(rollbackFor = {SuperCodeException.class,Exception.class})
	public RestResult<String> salerCopy(MarketingSalerActivityUpdateParam activitySetParam) throws SuperCodeException, BrokenBarrierException, InterruptedException {
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


// step-1：获取实体
		// 1 产品参数
		List<MarketingActivityProductParam> maProductParams=activitySetParam.getmProductParams();
		// 2 获取奖次参数
		List<MarketingPrizeTypeParam>mPrizeTypeParams=activitySetParam.getMarketingPrizeTypeParams();
		// 3 渠道参数:TODO 本期不做校验不做保存
		List<MarketingChannelParam> mChannelParams = activitySetParam.getmChannelParams();
		// 4 获取主表数据
		MarketingActivitySalerSetUpdateParam mActivitySetParam = activitySetParam.getmActivitySetParam();

// step-2：校验实体
		validateBasicBySalerUpdate(activitySetParam,maProductParams,mPrizeTypeParams);
		validateBizBySalerUpdate(activitySetParam,maProductParams,mPrizeTypeParams);




// step-3：转换保存实体
		// 4 获取活动实体：校验并且保存 返回活动主键ID
		// 插入完成携带主键
		MarketingActivitySet marketingActivitySet = changeDtoToDoWhenCopy(mActivitySetParam, organizationId, organizationName);
		mSetMapper.insert(marketingActivitySet);
		// 替换复制功能后[新增主表数据,先删或加子表数据]的主键
		// 插入数据库后获取
		Long activitySetId= marketingActivitySet.getId();
		//保存渠道 TODO 后期增加该逻辑
		if (!CollectionUtils.isEmpty(mChannelParams)) {
			saveChannels(mChannelParams,activitySetId);
		}
		//保存奖次
		savePrizeTypesWithThread(mPrizeTypeParams,activitySetId);
		//保存商品批次 【导购不像码平台发起产品业务绑定】
		saveProductBatchsWithThread(maProductParams,activitySetId,
				ReferenceRoleEnum.ACTIVITY_SALER.getType().intValue());
//		savePrizeTypes(mPrizeTypeParams,activitySetId);
//		saveProductBatchsWithSaler(maProductParams,activitySetId);
		return RestResult.success();


	}

	private MarketingActivitySet changeDtoToDoWhenCopy(MarketingActivitySalerSetUpdateParam activitySetParam, String organizationId, String organizationName) throws SuperCodeException {
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
		mSet.setActivityId(ActivityIdEnum.ACTIVITY_SALER.getId().longValue());
		// 保存创建更新用户
		AccountCache userLoginCache = commonUtil.getUserLoginCache();
		mSet.setUpdateUserId(userLoginCache.getUserId());
		mSet.setUpdateUserName(userLoginCache.getUserName());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
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




	private MarketingActivitySet convertActivitySetBySalerAdd(MarketingActivitySalerSetAddParam activitySetParam, String organizationId, String organizationName)throws SuperCodeException{
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
//		mSet.setId(null);
		mSet.setActivityEndDate(activitySetParam.getActivityEndDate());
		mSet.setActivityId(ActivityIdEnum.ACTIVITY_SALER.getId().longValue());
		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
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
		mSet.setSendAudit(activitySetParam.getSendAudit());
		return mSet;
	}

	private void saveProductBatchsWithThread(List<MarketingActivityProductParam> maProductParams, Long activitySetId, int intValue) throws SuperCodeException {

		// 判断新选择的产品是否存在,存在则删除[覆盖式操作]
		saveProductBatchsWithSaler(maProductParams,activitySetId);

	}

	private void savePrizeTypesWithThread(List<MarketingPrizeTypeParam> mPrizeTypeParams, Long activitySetId) throws SuperCodeException {

		savePrizeTypes(mPrizeTypeParams,activitySetId);

	}

	/**
	 * 导购活动创建的业务校验
	 * @param activitySetParam
	 * @param maProductParams
	 * @param mPrizeTypeParams
	 */
	private void validateBizBySalerAdd(MarketingSalerActivityCreateNewParam activitySetParam, List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams) {

	}


	private void validateBasicBySalerUpdate(MarketingSalerActivityUpdateParam activitySetParam, List<MarketingActivityProductParam> maProductParams, List<MarketingPrizeTypeParam> mPrizeTypeParams) throws SuperCodeException {
		if(activitySetParam == null){
			throw new SuperCodeException("导购活动参数丢失001");
		}
		// 检查奖次类型:无db检验【基础检验】
		standActicityParamCheck.basePrizeTypeCheck(mPrizeTypeParams);

		//检查产品：无db校验【基础检验】
		standActicityParamCheck.baseProductBatchCheck(maProductParams);
	}

	private void haveActivitySetId(MarketingSalerActivityUpdateParam activitySetParam) throws SuperCodeException{
		try {
			if(StringUtils.isEmpty(activitySetParam.getmActivitySetParam().getId().toString())){
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
	private MarketingActivitySet changeDtoToDoWhenUpdate(MarketingActivitySalerSetUpdateParam activitySetParam,String organizationId,String organizationName) throws SuperCodeException {
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
		mSet.setActivityId(ActivityIdEnum.ACTIVITY_SALER.getId().longValue());


		mSet.setActivityStartDate(activitySetParam.getActivityStartDate());
		mSet.setActivityTitle(title);
		mSet.setAutoFetch(activitySetParam.getAutoFetch());
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
		mSet.setSendAudit(activitySetParam.getSendAudit());
		return mSet;
	}






	/**
	 * 校验活动创建时间
	 * @param
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
	 * 保存渠道数据
	 * @param mChannelParams
	 * @param activitySetId
	 * @throws SuperCodeException
	 */
	private void saveChannels(List<MarketingChannelParam> mChannelParams,Long activitySetId) throws SuperCodeException {
		mChannelMapper.deleteByActivitySetId(activitySetId);
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


	private void saveProductBatchsWithSaler(List<MarketingActivityProductParam> maProductParams, Long activitySetId) throws SuperCodeException {

		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = new ArrayList<ProductAndBatchGetCodeMO>();
//		Map<String, MarketingActivityProduct> activityProductMap = new HashMap<String, MarketingActivityProduct>();
		List<MarketingActivityProduct> mList = new ArrayList<MarketingActivityProduct>();
		List<MarketingActivityProduct> upProductList = mProductMapper.selectByActivitySetId(activitySetId);

		mProductMapper.deleteByActivitySetId(activitySetId);
		for (MarketingActivityProductParam marketingActivityProductParam : maProductParams) {
			String productId = marketingActivityProductParam.getProductId();
			List<ProductBatchParam> batchParams = marketingActivityProductParam.getProductBatchParams();
			ProductAndBatchGetCodeMO productAndBatchGetCodeMO = new ProductAndBatchGetCodeMO();
			List<Map<String, String>> productBatchList = new ArrayList<Map<String, String>>();
			if (CollectionUtils.isEmpty(batchParams)) {
				MarketingActivityProduct mActivityProduct = new MarketingActivityProduct();
				mActivityProduct.setProductId(marketingActivityProductParam.getProductId());
				mActivityProduct.setProductName(marketingActivityProductParam.getProductName());
				mActivityProduct.setReferenceRole(ReferenceRoleEnum.ACTIVITY_SALER.getType());
				mList.add(mActivityProduct);
			} else {
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

			}
			// 拼装请求码管理批次信息接口商品参数
			productAndBatchGetCodeMO.setProductBatchList(productBatchList);
			productAndBatchGetCodeMO.setProductId(productId);
			productAndBatchGetCodeMOs.add(productAndBatchGetCodeMO);
		}
		List<SbatchUrlUnBindDto> deleteProductBatchList = new ArrayList<>();
		//得到已经绑定过url的product
		List<MarketingActivityProduct> maProductList = mProductMapper.selectByProductAndBatch(mList, ReferenceRoleEnum.ACTIVITY_SALER.getType());
		if(maProductList == null) {
            maProductList = new ArrayList<>();
        }
		maProductList.addAll(upProductList);
		List<MarketingActivityProduct> marketingActivityProductList = maProductList.stream().distinct().collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(marketingActivityProductList)) {
			marketingActivityProductList.forEach(marketingActivityProduct -> {
				String sbatchIds = marketingActivityProduct.getSbatchId();
				if (org.apache.commons.lang3.StringUtils.isNotBlank(sbatchIds)) {
					String[] sbatchIdArray = sbatchIds.split(",");
					for(String sbatchId : sbatchIdArray) {
						SbatchUrlUnBindDto sbatchUrlDto = new SbatchUrlUnBindDto();
						sbatchUrlDto.setUrl(marketingDomain + WechatConstants.SALER_SCAN_CODE_JUMP_URL);
						List<Integer> bizTypeList = new ArrayList<>();
						bizTypeList.add(BusinessTypeEnum.MARKETING_ACTIVITY.getBusinessType());
						sbatchUrlDto.setBusinessTypes(bizTypeList);
						sbatchUrlDto.setBatchId(Long.parseLong(sbatchId));
						sbatchUrlDto.setClientRole(MemberTypeEnums.SALER.getType()+"");
						sbatchUrlDto.setProductId(marketingActivityProduct.getProductId());
						sbatchUrlDto.setProductBatchId(marketingActivityProduct.getProductBatchId());
						deleteProductBatchList.add(sbatchUrlDto);
					}
				}
			});
		}
		mList.forEach(prd -> prd.setActivitySetId(activitySetId));
		marketingActivitySetService.saveProductBatchs(productAndBatchGetCodeMOs, deleteProductBatchList,mList, MemberTypeEnums.SALER.getType());
	}

	/**
	 * 保存中奖奖次
	 * @param mPrizeTypeParams
	 * @param activitySetId
	 * @throws SuperCodeException
	 */
	private void savePrizeTypes(List<MarketingPrizeTypeParam> mPrizeTypeParams, Long activitySetId) throws SuperCodeException {
		mPrizeTypeMapper.deleteByActivitySetId(activitySetId);

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












	/*========================================= 多线程 事务 trans start=========================================*/
	/******************************************
	 *
	 */

	class JgwTransaction{
		/**
		 * tx manager
		 */
		ThreadLocal<PlatformTransactionManager> manager = new ThreadLocal<>();

		/**
		 * status
		 */
		ThreadLocal<TransactionStatus> status = new ThreadLocal<>();

		/**
		 * method using thread  num
		 */
		private int TX_THREAD_NUM;
		/**
		 * thread pool for biz
		 */
		@Autowired
		private TaskExecutor taskExecutor;
		/**
		 * its throw exception where mrthods Son threads + 1 != TX_THREAD_NUM
		 */
		int throwExIfThreadOut;
		/**
		 * 应用层同一全局事务Id标志;aop通过该参数的该属性确定是否为同一应用层事务
		 */
		private String uuid;


	}
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
	private static final int TX_THREAD_NUM = 3;

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
		logger.error("开启事务");

	}

	private void commitOrRollback(AtomicInteger successNum) {
		logger.error("事务预提交数目{}",successNum.get());
		PlatformTransactionManager txManager = transm.get();
		TransactionStatus status = transs.get();
		if(TX_THREAD_NUM == successNum.get()){
			txManager.commit(status);
		}else {
			txManager.rollback(status);
		}
	}

	/*=========================================trans start=========================================*/

}
