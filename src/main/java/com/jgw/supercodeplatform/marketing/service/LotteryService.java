package com.jgw.supercodeplatform.marketing.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityPreviewParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;

import redis.clients.jedis.JedisCommands;

@Service
public class LotteryService {
	protected static Logger logger = LoggerFactory.getLogger(LotteryService.class);

	@Autowired
	private MarketingPrizeTypeMapper mMarketingPrizeTypeMapper;

	@Autowired
	private IntegralRecordMapperExt integralRecordMapperExt;

	@Autowired
	private MarketingActivityProductMapper maProductMapper;

	@Autowired
	private MarketingActivityMapper mActivityMapper;

	@Autowired
	private MarketingMembersWinRecordMapper mWinRecordMapper;

	@Autowired
	private WXPayTradeOrderMapper wXPayTradeOrderMapper;

	@Autowired
	private GlobalRamCache globalRamCache;

	@Autowired
	private WXPayService wxpService;

	@Autowired
	private CodeEsService codeEsService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private WXPayTradeNoGenerator wXPayTradeNoGenerator ;

	@Autowired
	private MarketingMembersMapper marketingMembersMapper;

	@Autowired
	private MarketingActivitySetMapper mSetMapper;

	private static SimpleDateFormat staticESSafeFormat=new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MarketingPrizeTypeMapper mPrizeTypeMapper;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private RedisLockUtil lock;

	@Value("${marketing.server.ip}")
	private String serverIp;

	@Autowired
	private StringRedisTemplate redisTemplate;

	public RestResult baselottery(String wxstate) throws SuperCodeException, ParseException {
		RestResult<String> restResult=new RestResult<>();
		ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(wxstate);
		if (null==scanCodeInfoMO) {
			// 贴合前端提示方式
			logger.error("不存在扫码唯一纪录="+wxstate+"的扫码缓存信息，请重新扫码");
			LotteryResultMO mo = new LotteryResultMO();
			mo.setMsg("您的进入方式错啦！请重新扫码");
			return RestResult.success("success",mo);
			// 贴合前端提示方式
		}
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
		if (null==mActivitySet) {
			throw new SuperCodeException("该活动设置不存在", 200);
		}

		MarketingActivity activity=mActivityMapper.selectById(mActivitySet.getActivityId());
		if (null==activity) {
			throw new SuperCodeException("该活动不存在", 200);
		}
		int activityType=activity.getActivityType().intValue();
		switch (activityType) {
		case 1:
			//微信红包需要
			String openId=scanCodeInfoMO.getOpenId();
			if (StringUtils.isBlank(openId)) {
				throw new SuperCodeException("微信红包活动openId参数不能为空", 200);
			}
			break;
		default:
			break;
		}
		// 手机校验,抛出异常
		String mobile=scanCodeInfoMO.getMobile();
		if (StringUtils.isNotBlank(mobile)) {
			commonUtil.checkPhoneFormat(mobile);
		}
		return restResult;
	}

	/**
	 * 点击中奖逻辑
	 * @param activitySetId
	 * @param openId
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional(rollbackFor = Exception.class)
	public RestResult<LotteryResultMO> lottery(String wxstate, String remoteAddr) throws SuperCodeException, ParseException {
		RestResult<LotteryResultMO> restResult=new RestResult<>();
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(wxstate);
		logger.info("领奖传入参数:{}", scanCodeInfoMO);
		if (null==scanCodeInfoMO) {
			// 贴合前端提示方式
			logger.error("不存在扫码唯一纪录="+wxstate+"的扫码缓存信息，请重新扫码");
			LotteryResultMO mo = new LotteryResultMO();
			mo.setMsg("您的进入方式错啦！请重新扫码");
			return RestResult.success("success",mo);
		}
		String openId=scanCodeInfoMO.getOpenId();
		String codeId=scanCodeInfoMO.getCodeId();
		String codeTypeId=scanCodeInfoMO.getCodeTypeId();
		String productId=scanCodeInfoMO.getProductId();
		String productBatchId=scanCodeInfoMO.getProductBatchId();
		String mobile=scanCodeInfoMO.getMobile();
		String sbatchId = scanCodeInfoMO.getSbatchId();
		commonService.checkCodeValid(codeId, codeTypeId);
		commonService.checkCodeTypeValid(Long.valueOf(codeTypeId));
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
		if (null==mActivitySet) {
			restResult.setState(200);
			restResult.setMsg("该活动设置不存在");
			return restResult;
		}
		if(mActivitySet.getActivityStatus() == 0) {
			throw new SuperCodeException("该活动已停用", 200);
		}
		long currentMills = System.currentTimeMillis();
		String startDateStr = mActivitySet.getActivityStartDate();
		if(StringUtils.isNotBlank(startDateStr)) {
			long startMills = DateUtil.parse(startDateStr, "yyyy-MM-dd HH:mm:ss").getTime();
			if(currentMills < startMills)
				throw new SuperCodeException("该活动还未开始", 200);
			
		}
		String endDateStr = mActivitySet.getActivityEndDate();
		if(StringUtils.isNotBlank(endDateStr)) {
			long endMills = DateUtil.parse(endDateStr, "yyyy-MM-dd HH:mm:ss").getTime();
			if(currentMills > endMills)
				throw new SuperCodeException("该活动已经结束", 200);
		}
		
		MarketingActivityProduct mActivityProduct = maProductMapper.selectByProductAndProductBatchIdWithReferenceRoleAndSetId(productId, productBatchId, ReferenceRoleEnum.ACTIVITY_MEMBER.getType(), activitySetId);
		String productSbatchId = mActivityProduct.getSbatchId();
		if(productSbatchId == null || !productSbatchId.contains(sbatchId)) {
			throw new SuperCodeException("码批次有误", 200);
		}
		MarketingActivity activity=mActivityMapper.selectById(mActivitySet.getActivityId());
		if (null==activity) {
			throw new SuperCodeException("该活动不存在", 200);
		}
		Long userId=scanCodeInfoMO.getUserId();
		MarketingMembers marketingMembersInfo = marketingMembersMapper.getMemberById(userId);
		if(marketingMembersInfo == null){
			throw  new SuperCodeException("会员信息不存在",200);
		}
		if( null!=marketingMembersInfo.getState() && marketingMembersInfo.getState() == 0){
			throw  new SuperCodeException("对不起,该会员已被加入黑名单",200);
		}

		String condition=mActivitySet.getValidCondition();
		MarketingActivitySetCondition mSetCondition=null;
		if (StringUtils.isNotBlank(condition)) {
			mSetCondition=JSONObject.parseObject(condition, MarketingActivitySetCondition.class);
		}else {
			mSetCondition=new MarketingActivitySetCondition();
		}
		int consumeIntegralNum=mSetCondition.getConsumeIntegral() == null? 0: mSetCondition.getConsumeIntegral();
		int haveIntegral=marketingMembersInfo.getHaveIntegral() == null? 0:marketingMembersInfo.getHaveIntegral();
		if (haveIntegral < consumeIntegralNum) {
			throw new SuperCodeException("对不起,领取本活动需要消耗"+consumeIntegralNum+"积分，您的积分不够",200);
		}
		LotteryResultMO lotteryResultMO = new LotteryResultMO();
		restResult.setResults(lotteryResultMO);
		List<MarketingPrizeTypeMO> moPrizeTypes=mMarketingPrizeTypeMapper.selectMOByActivitySetIdIncludeUnreal(activitySetId);
		if (null==moPrizeTypes || moPrizeTypes.isEmpty()) {
			restResult.setState(200);
			lotteryResultMO.setWinnOrNot(0);
			lotteryResultMO.setMsg("该活动未设置中奖奖次");
			restResult.setMsg(lotteryResultMO.getMsg());
			return restResult;
		}
		String organizationId=mActivitySet.getOrganizationId();
		MarketingActivityProduct marketingActivityProduct = new MarketingActivityProduct();
		marketingActivityProduct.setProductId(productId);
		marketingActivityProduct.setActivitySetId(activitySetId);
		marketingActivityProduct.setProductBatchId(productBatchId);
		marketingMembersInfo.setHaveIntegral(haveIntegral - consumeIntegralNum);
		IntegralRecord integralRecord = new IntegralRecord();
		integralRecord.setIntegralNum(0 - consumeIntegralNum);
		integralRecord.setIntegralReason(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReason());
		integralRecord.setIntegralReasonCode(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReasonCode());
		//执行抽奖逻辑 
		MarketingPrizeTypeMO mPrizeTypeMO=LotteryUtilWithOutCodeNum.startLottery(moPrizeTypes);
		Byte awardType=mPrizeTypeMO.getAwardType();
		String key = mPrizeTypeMO.getId() + "_"+mPrizeTypeMO.getActivitySetId();
		if (awardType != null && (awardType.intValue() == 1 || awardType.intValue() == 9)) {
			if(mPrizeTypeMO.getRemainingStock() != null) {
				String result = redisTemplate.execute(new RedisCallback<String>() {
					@Override
					public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
						JedisCommands jedisCommands = (JedisCommands) redisConnection.getNativeConnection();
						String res = jedisCommands.set(key, mPrizeTypeMO.getRemainingStock().toString(), "NX", "EX", 60);
						jedisCommands.expire(key, 60);
						return res;
					}
				});
				logger.info("抽奖结果：{},设置redis返回值：{}", mPrizeTypeMO, result);
			}
			if(StringUtils.isBlank(valueOperations.get(key))) {
				globalRamCache.deleteScanCodeInfoMO(wxstate);
				if(consumeIntegralNum != 0) {
					marketingMembersMapper.update(marketingMembersInfo);
					addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, integralRecord, marketingActivityProduct);
				}
				lotteryResultMO.setWinnOrNot(0);
				restResult.setState(200);
				lotteryResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
				restResult.setMsg(lotteryResultMO.getMsg());
				return restResult;
			}
			Long reStockNum = valueOperations.increment(key, -1);
			//说明此时库存已不够
			if(reStockNum < 0) {
				valueOperations.increment(key, 1);
				globalRamCache.deleteScanCodeInfoMO(wxstate);
				if(consumeIntegralNum != 0) {
					marketingMembersMapper.update(marketingMembersInfo);
					addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, integralRecord, marketingActivityProduct);
				}
				lotteryResultMO.setWinnOrNot(0);
				restResult.setState(200);
				lotteryResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
				restResult.setMsg(lotteryResultMO.getMsg());
				return restResult;
			}
		}

		boolean flag=holdLockJudgeES(restResult,marketingMembersInfo.getId(),marketingMembersInfo.getMemberType().intValue(), openId,productId,productBatchId, activitySetId, mSetCondition, organizationId, codeId, codeTypeId);
		if (!flag ) {
			if (awardType != null && (awardType.intValue() == 1 || awardType.intValue() == 9)) {
				valueOperations.increment(key, 1);
			}
			globalRamCache.deleteScanCodeInfoMO(wxstate);
			lotteryResultMO.setWinnOrNot(0);
			restResult.setState(200);
			lotteryResultMO.setMsg(restResult.getMsg());
			return restResult;
		}
		//判断realprize是否为0,0表示为新增的虚拟不中奖奖项，为了计算中奖率设置
		Byte realPrize=mPrizeTypeMO.getRealPrize();
		if (realPrize.equals((byte)0)) {
			if (awardType != null && (awardType.intValue() == 1 || awardType.intValue() == 9)) {
				valueOperations.increment(key, 1);
			}
			restResult.setState(200);
			lotteryResultMO.setWinnOrNot(0);
			lotteryResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
			restResult.setMsg(lotteryResultMO.getMsg());
			globalRamCache.deleteScanCodeInfoMO(wxstate);
			if(consumeIntegralNum != 0) {
				marketingMembersMapper.update(marketingMembersInfo);
				addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, integralRecord, marketingActivityProduct);
			}
		}else{
			lotteryResultMO.setWinnOrNot(1);
			//已中奖执行奖品方法中奖纪录保存等逻辑
			try {
				//如果是微信红包奖项类型可能为空需特殊处理
				if (null==awardType || awardType.intValue()==4) {
					lotteryResultMO.setAwardType((byte)4);
					// amount单位是元
					Float amount = weixinpay(mobile, openId, organizationId, mPrizeTypeMO,remoteAddr);
					addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, amount,productId,productBatchId);
					DecimalFormat decimalFormat=new DecimalFormat(".00");
					String strAmount=decimalFormat.format(amount);
					lotteryResultMO.setData(strAmount);
					lotteryResultMO.setMsg(strAmount);
					integralRecord.setProductPrice(amount);
				}else {
					lotteryResultMO.setAwardType(awardType);
					int redisRemainingStock = -1;
					switch (awardType.intValue()) {
					case 1://实物
						redisRemainingStock = Integer.parseInt(valueOperations.get(key));
						if (redisRemainingStock < 0) {
							lotteryResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
							lotteryResultMO.setWinnOrNot(0);
							valueOperations.increment(key, 1);
						}else {
							mPrizeTypeMapper.updateRemainingStock(mPrizeTypeMO.getId());
							lotteryResultMO.setMsg("恭喜您，获得"+mPrizeTypeMO.getPrizeTypeName());
							Map<String, Object> lotteryDataMap = new HashMap<>();
							lotteryDataMap.put("prizeId", mPrizeTypeMO.getId());
							lotteryDataMap.put("prizeName", mPrizeTypeMO.getPrizeTypeName());
							lotteryResultMO.setData(lotteryDataMap);
							addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, null,productId,productBatchId);
							integralRecord.setProductPrice(mPrizeTypeMO.getPrizeAmount());
						}
						break;
					case 2: //奖券
						lotteryResultMO.setData(mPrizeTypeMO.getCardLink());
						lotteryResultMO.setMsg("恭喜您，获得"+mPrizeTypeMO.getPrizeTypeName());
						addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, null,productId,productBatchId);
						break;
					case 3: //积分
						int awardIntegralNum=mPrizeTypeMO.getAwardIntegralNum().intValue();
						marketingMembersInfo.setHaveIntegral(haveIntegral+awardIntegralNum-consumeIntegralNum);
						lotteryResultMO.setMsg("恭喜您，获得"+awardIntegralNum+"积分");
						IntegralRecord iRecord = new IntegralRecord();
						iRecord.setIntegralNum(awardIntegralNum);
						iRecord.setIntegralReason(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReason());
						iRecord.setIntegralReasonCode(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReasonCode());
						addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, null,productId,productBatchId);
						addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, iRecord, mActivityProduct);
						if(consumeIntegralNum != 0) {
							addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, integralRecord, marketingActivityProduct);
						}
						if(consumeIntegralNum != awardIntegralNum) {
							marketingMembersMapper.update(marketingMembersInfo);
						}
						break;

					case 9://其它
						redisRemainingStock = Integer.parseInt(valueOperations.get(key));
						if (redisRemainingStock < 0) {
							restResult.setState(200);
							lotteryResultMO.setWinnOrNot(0);
							lotteryResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
							valueOperations.increment(key, 1);
						} else {
							mPrizeTypeMapper.updateRemainingStock(mPrizeTypeMO.getId());
							lotteryResultMO.setMsg("恭喜您，获得"+mPrizeTypeMO.getPrizeTypeName());
							Map<String, Object> lotteryDataMap = new HashMap<>();
							lotteryDataMap.put("prizeId", mPrizeTypeMO.getId());
							lotteryDataMap.put("prizeName", mPrizeTypeMO.getPrizeTypeName());
							lotteryResultMO.setData(lotteryDataMap);
							integralRecord.setProductPrice(mPrizeTypeMO.getPrizeAmount());
							addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, null,productId,productBatchId);
						}
						break;
					default:
						break;
					}
				}
				if(consumeIntegralNum != 0 && (awardType == null || awardType.intValue() != 3)) {
					addToInteral(scanCodeInfoMO, marketingMembersInfo, organizationId, codeId, productId, integralRecord, marketingActivityProduct);
					marketingMembersMapper.update(marketingMembersInfo);
				}
			} catch (Exception e) {
				if (awardType != null && (awardType.intValue() == 1 || awardType.intValue() == 9)) {
					valueOperations.increment(key, 1);
				}
				throw new SuperCodeException(e.getLocalizedMessage(), 200);
			}finally {
				//一切ok后清除缓存
				globalRamCache.deleteScanCodeInfoMO(wxstate);
			}
			restResult.setState(200);
		}
		return restResult;
	}

	private void addToInteral(ScanCodeInfoMO scanCodeInfoMO, MarketingMembers marketingMembersInfo,
			String organizationId, String codeId, String productId, IntegralRecord integralRecord,
			MarketingActivityProduct marketingActivityProduct) {
		integralRecord.setActivitySetId(marketingActivityProduct.getActivitySetId());
		integralRecord.setCodeTypeId(scanCodeInfoMO.getCodeTypeId());
		integralRecord.setCreateDate(new Date());
		integralRecord.setCustomerId(marketingMembersInfo.getCustomerId());
		integralRecord.setCustomerName(marketingMembersInfo.getCustomerName());
		integralRecord.setMemberId(marketingMembersInfo.getId());
		integralRecord.setMemberName(marketingMembersInfo.getUserName());
		integralRecord.setMemberType(marketingMembersInfo.getMemberType());
		integralRecord.setMobile(marketingMembersInfo.getMobile());
		integralRecord.setOrganizationId(organizationId);
		integralRecord.setOuterCodeId(codeId);
		integralRecord.setProductId(productId);
		integralRecord.setProductName(marketingActivityProduct.getProductName());
		integralRecord.setStatus("1");
		integralRecordMapperExt.insertSelective(integralRecord);
	}

	private boolean holdLockJudgeES(RestResult<LotteryResultMO> restResult,Long memberId,int memberType, String openId,String productId, String productBatchId, Long activitySetId,
			MarketingActivitySetCondition mSetCondition, String organizationId, String codeId, String codeTypeId) {
		boolean acquireLock =false;
		try {
			// 超时时间,重试次数，重试间隔
			acquireLock = lock.lock(activitySetId + ":" + codeId + ":" + codeTypeId,5000,5,200);
			if(acquireLock){
				String nowTime=staticESSafeFormat.format(new Date());
				long nowTtimeStemp=staticESSafeFormat.parse(nowTime).getTime();

				String opneIdNoSpecialChactar=null;
				if (StringUtils.isNotBlank(openId)) {
					opneIdNoSpecialChactar=CommonUtil.replaceSpicialChactar(openId);
				}
				//校验码有没有被扫过
				Long codeCount=codeEsService.countByCode(codeId, codeTypeId,memberType);
				logger.info("领取方法=====：根据codeId="+codeId+",codeTypeId="+codeTypeId+"获得的扫码记录次数为="+codeCount);
				if (null==codeCount ||codeCount.intValue()<1) {
					//校验有没有设置活动用户扫码量限制
					Integer scanLimit=mSetCondition.getEachDayNumber();
					if (null!=scanLimit&& scanLimit.intValue()>0) {
						Long userscanNum=codeEsService.countByUserAndActivityQuantum(opneIdNoSpecialChactar, activitySetId, nowTtimeStemp);
						logger.info("领取方法=====：根据openId="+opneIdNoSpecialChactar+",activitySetId="+activitySetId+",nowTime="+nowTime+"获得的用户扫码记录次数为="+userscanNum+",当前活动扫码限制次数为："+scanLimit);
						if (null!=userscanNum && userscanNum.intValue()>=scanLimit.intValue()) {
							restResult.setState(200);
							restResult.setMsg("您今日扫码已超过该活动限制数量");
							return false;
						}
					}
				}else {
					restResult.setState(200);
					restResult.setMsg("您手速太慢，该码已被其它用户领取");
					return false;
				}

				codeEsService.addScanCodeRecord(opneIdNoSpecialChactar, productId, productBatchId, codeId, codeTypeId, activitySetId,nowTtimeStemp,organizationId,0,memberId);
				logger.info("领取方法====：抽奖数据已保存到es");
			}else {
				logger.error("{锁获取失败:" +activitySetId + codeId +codeTypeId+ ",请检查}");
				try {
					// 统计失败
					redisUtil.hmSet("marketing:lock:fail",activitySetId + codeId +codeTypeId,new Date());
				} catch (Exception e) {
				}
				restResult.setState(200);
				restResult.setMsg("扫码人数过多,请稍后再试");
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			restResult.setState(200);
			restResult.setMsg("扫码人数过多,请稍后再试");
			logger.error("扫码判断出错", e);
			return false;
		}finally {
			if(acquireLock){
				try{
					// lua脚本
					lock.releaseLock(activitySetId + ":" + codeId + ":" + codeTypeId);
				}catch (Exception e){
					logger.error("{锁释放失败:" +activitySetId + codeId +codeTypeId+ ",请检查}");
					e.printStackTrace();
				}
			}
		}
		return  true;
	}


	private void addWinRecord(String outCodeId, String mobile, String openId, Long activitySetId,
			MarketingActivity activity, String organizationId, MarketingPrizeTypeMO mPrizeTypeMO, Float amount, String productId, String productBatchId) {
		//插入中奖纪录
		MarketingMembersWinRecord redWinRecord=new MarketingMembersWinRecord();
		redWinRecord.setActivityId(activity.getId());
		redWinRecord.setActivityName(activity.getActivityName());
		redWinRecord.setActivitySetId(activitySetId);
		redWinRecord.setMobile(mobile);
		redWinRecord.setOpenid(openId);
		redWinRecord.setPrizeTypeId(mPrizeTypeMO.getId());
		redWinRecord.setWinningAmount(amount );
		redWinRecord.setProductId(productId);
		redWinRecord.setWinningCode(outCodeId);
		redWinRecord.setPrizeName(mPrizeTypeMO.getPrizeTypeName());
		redWinRecord.setOrganizationId(organizationId);
		redWinRecord.setProductBatchId(productBatchId);
		mWinRecordMapper.addWinRecord(redWinRecord);
	}

	private Float weixinpay(String mobile, String openId, String organizationId, MarketingPrizeTypeMO mPrizeTypeMO, String remoteAddr)
			throws SuperCodeException, Exception {
		if (StringUtils.isBlank(openId)) {
			throw  new SuperCodeException("微信支付openid不能为空",200);
		}
		Float amount=mPrizeTypeMO.getPrizeAmount();
		Byte randAmount=mPrizeTypeMO.getIsRrandomMoney();
		//如果是随机金额则生成随机金额
		if (randAmount.equals((byte)1)) {
			float min=mPrizeTypeMO.getLowRand();
			float max=mPrizeTypeMO.getHighRand();
			amount = new Random().nextFloat() *((max-min)) +min;
		}
		Float finalAmount = amount * 100;//金额转化为分

		logger.error("{ 中奖记录保存：手机号=> + " + mobile +"==}");

		//生成订单号
		String partner_trade_no=wXPayTradeNoGenerator.tradeNo();
		//保存订单
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WXPayTradeOrder tradeOrder=new WXPayTradeOrder();
		tradeOrder.setAmount(finalAmount);
		tradeOrder.setOpenId(openId);
		tradeOrder.setTradeStatus((byte)0);
		tradeOrder.setPartnerTradeNo(partner_trade_no);
		tradeOrder.setTradeDate(format.format(new Date()));
		tradeOrder.setOrganizationId(organizationId);
		wXPayTradeOrderMapper.insert(tradeOrder);
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr=serverIp;
		}
		wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(),partner_trade_no, organizationId);
		return amount;
	}

	public RestResult<LotteryResultMO> previewLottery(String uuid, HttpServletRequest request) throws SuperCodeException {
		RestResult<LotteryResultMO> restResult = new RestResult<>();
		String value = redisUtil.get(RedisKey.ACTIVITY_PREVIEW_PREFIX + uuid);
		if (StringUtils.isBlank(value)) {
			restResult.setState(200);
			restResult.setMsg("扫码信息已过期请重新扫码预览");
			return restResult;
		}
		MarketingActivityPreviewParam mPreviewParam = JSONObject.parseObject(value,
				MarketingActivityPreviewParam.class);
		List<MarketingPrizeTypeParam> moPrizeTypes = mPreviewParam.getMarketingPrizeTypeParams();

		List<MarketingPrizeTypeMO> mList = new ArrayList<>(moPrizeTypes.size());
		int sumprizeProbability = 0;
		for (MarketingPrizeTypeParam marketingPrizeTypeParam : moPrizeTypes) {
			Integer prizeProbability = marketingPrizeTypeParam.getPrizeProbability();
			MarketingPrizeTypeMO mPrizeType = new MarketingPrizeTypeMO();
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
			sumprizeProbability += prizeProbability;
		}
		if (sumprizeProbability > 100) {
			throw new SuperCodeException("概率参数非法，总数不能大于100", 200);
		} else if (sumprizeProbability < 100) {
			int i = 100 - sumprizeProbability;
			MarketingPrizeTypeMO NoReal = new MarketingPrizeTypeMO();
			NoReal.setPrizeAmount((float) 0);
			NoReal.setPrizeProbability(i);
			NoReal.setPrizeTypeName("未中奖");
			NoReal.setIsRrandomMoney((byte) 0);
			NoReal.setRealPrize((byte) 0);
			mList.add(NoReal);
		}

		// 执行抽奖逻辑
		MarketingPrizeTypeMO mPrizeTypeMO = LotteryUtilWithOutCodeNum.startLottery(mList);
		LotteryResultMO lResultMO=new LotteryResultMO();
		// 判断realprize是否为0,0表示为新增的虚拟不中奖奖项，为了计算中奖率设置
		Byte realPrize = mPrizeTypeMO.getRealPrize();
		if (realPrize.equals((byte) 0)) {
			restResult.setState(200);
			lResultMO.setWinnOrNot(0);
			lResultMO.setMsg("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
		} else {
			Byte awardType = mPrizeTypeMO.getAwardType();
			if (null==awardType ||awardType.intValue()==4 ) {
				restResult.setState(200);
				lResultMO.setWinnOrNot(1);
				lResultMO.setData(mPrizeTypeMO.getPrizeAmount());
				lResultMO.setAwardType((byte)4);
				restResult.setResults(lResultMO);
				return restResult;
			}
			lResultMO.setAwardType(awardType);
			lResultMO.setWinnOrNot(1);
			// 已中奖执行奖品方法中奖纪录保存等逻辑
			try {
				switch (awardType.intValue()) {
				case 1:// 实物
					lResultMO.setMsg("恭喜您，获得" + mPrizeTypeMO.getPrizeTypeName());
					break;
				case 2: // 奖券
					lResultMO.setMsg("恭喜您，获得" + mPrizeTypeMO.getPrizeTypeName());
					lResultMO.setData(mPrizeTypeMO.getCardLink());
					break;
				case 3: // 积分
					Integer awardIntegralNum = mPrizeTypeMO.getAwardIntegralNum();
					lResultMO.setMsg("恭喜您，获得" + awardIntegralNum + "积分");
					lResultMO.setData(awardIntegralNum);
					break;
				case 9:// 其它
					lResultMO.setMsg("恭喜您，获得" + mPrizeTypeMO.getPrizeTypeName());
					break;
				default:
					System.out.println(1);
					break;
				}
			} catch (Exception e) {
			}
		}
		restResult.setResults(lResultMO);
		restResult.setState(200);
		return restResult;
	}

}
