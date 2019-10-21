package com.jgw.supercodeplatform.marketing.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
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
import com.jgw.supercodeplatform.marketing.dao.activity.*;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.WxOrderPayDto;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityPreviewParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
	private MarketingMembersService marketingMembersService;

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

	@Autowired
	private MarketingPrizeTypeMapper mPrizeTypeMapper;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private RedisLockUtil lock;

	@Value("${marketing.server.ip}")
	private String serverIp;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private MarketingPlatformOrganizationMapper marketingPlatformOrganizationMapper;

	/**
	 * 检查抽奖参数及抽奖逻辑
	 * @param scanCodeInfoMO
	 * @return 返回 RestResult或者MarketingPrizeTypeMO
	 * @throws ParseException
	 * @throws SuperCodeException
	 */
	public LotteryOprationDto checkLotteryCondition(LotteryOprationDto lotteryOprationDto, ScanCodeInfoMO scanCodeInfoMO) throws ParseException, SuperCodeException {
		String codeId=scanCodeInfoMO.getCodeId();
		String codeTypeId=scanCodeInfoMO.getCodeTypeId();
		String productId=scanCodeInfoMO.getProductId();
		String productBatchId=scanCodeInfoMO.getProductBatchId();
		String sbatchId = scanCodeInfoMO.getSbatchId();
		commonService.checkCodeValid(codeId, codeTypeId);
		commonService.checkCodeTypeValid(Long.valueOf(codeTypeId));
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet mActivitySet = mSetMapper.selectById(activitySetId);
		if (null == mActivitySet) {
			return lotteryOprationDto.lotterySuccess("该活动设置不存在");
		}
		if(mActivitySet.getActivityStatus() == 0) {
			throw new SuperCodeExtException("该活动已停用", 200);
		}
		long currentMills = System.currentTimeMillis();
		String startDateStr = mActivitySet.getActivityStartDate();
		if(StringUtils.isNotBlank(startDateStr)) {
			long startMills = DateUtil.parse(startDateStr, "yyyy-MM-dd").getTime();
			if(currentMills < startMills){
				throw new SuperCodeExtException("该活动还未开始", 200);
			}
		}
		String endDateStr = mActivitySet.getActivityEndDate();
		if(StringUtils.isNotBlank(endDateStr)) {
			long endMills = DateUtil.parse(endDateStr, "yyyy-MM-dd").getTime();
			if(currentMills > endMills){
				throw new SuperCodeExtException("该活动已经结束", 200);
			}
		}
		MarketingActivityProduct mActivityProduct = maProductMapper.selectByProductAndProductBatchIdWithReferenceRoleAndSetId(productId, productBatchId, ReferenceRoleEnum.ACTIVITY_MEMBER.getType(), activitySetId);
		String productSbatchId = mActivityProduct.getSbatchId();
		if(productSbatchId == null || !productSbatchId.contains(sbatchId)) {
			throw new SuperCodeExtException("码批次有误", 200);
		}
		MarketingActivity activity = mActivityMapper.selectById(mActivitySet.getActivityId());
		if (null == activity) {
			throw new SuperCodeExtException("该活动不存在", 200);
		}
		Long userId = scanCodeInfoMO.getUserId();
		MemberWithWechat memberWithWechat = marketingMembersService.selectById(userId);
		if(memberWithWechat == null){
			throw new SuperCodeException("会员信息不存在",200);
		}
		MarketingMembers marketingMembersInfo = new MarketingMembers();
		BeanUtils.copyProperties(memberWithWechat, marketingMembersInfo);
		marketingMembersInfo.setId(userId);
		if( null != marketingMembersInfo.getState() && marketingMembersInfo.getState() == 0){
			throw new SuperCodeException("对不起,该会员已被加入黑名单",200);
		}
		String condition = mActivitySet.getValidCondition();
		MarketingActivitySetCondition mSetCondition = null;
		if (StringUtils.isNotBlank(condition)) {
			mSetCondition = JSONObject.parseObject(condition, MarketingActivitySetCondition.class);
		} else {
			mSetCondition = new MarketingActivitySetCondition();
		}
		int consumeIntegralNum = mSetCondition.getConsumeIntegral() == null? 0: mSetCondition.getConsumeIntegral();
		int haveIntegral = marketingMembersInfo.getHaveIntegral() == null? 0:marketingMembersInfo.getHaveIntegral();
		if (haveIntegral < consumeIntegralNum) {
			throw new SuperCodeException("对不起,领取本活动需要消耗"+consumeIntegralNum+"积分，您的积分不够",200);
		}
		List<MarketingPrizeTypeMO> moPrizeTypes = mMarketingPrizeTypeMapper.selectMOByActivitySetIdIncludeUnreal(activitySetId);
		if (CollectionUtils.isEmpty(moPrizeTypes)) {
			return lotteryOprationDto.lotterySuccess("该活动未设置中奖奖次");
		}
		lotteryOprationDto.setSendAudit(mActivitySet.getSendAudit());
		lotteryOprationDto.setMarketingMembersInfo(marketingMembersInfo);
		lotteryOprationDto.setOrganizationId(scanCodeInfoMO.getOrganizationId());
		if (lotteryOprationDto.getOrganizationName() == null ) {
			lotteryOprationDto.setOrganizationName(mActivitySet.getOrganizatioIdlName());
		}
		lotteryOprationDto.setScanCodeInfoMO(scanCodeInfoMO);
		lotteryOprationDto.setConsumeIntegralNum(consumeIntegralNum);
		lotteryOprationDto.setHaveIntegral(haveIntegral);
		lotteryOprationDto.setProductName(mActivityProduct.getProductName());
		lotteryOprationDto.setMarketingActivity(activity);
		//执行抽奖逻辑
		lotteryOprationDto.setPrizeTypeMO(LotteryUtilWithOutCodeNum.startLottery(moPrizeTypes));
		return lotteryOprationDto;
	}

	public LotteryOprationDto drawLottery(LotteryOprationDto lotteryOprationDto) {
		ScanCodeInfoMO scanCodeInfoMO = lotteryOprationDto.getScanCodeInfoMO();
		MarketingPrizeTypeMO prizeTypeMO = lotteryOprationDto.getPrizeTypeMO();
		IntegralRecord integralRecord = new IntegralRecord();
		integralRecord.setCodeTypeId(scanCodeInfoMO.getCodeTypeId());
		integralRecord.setOuterCodeId(scanCodeInfoMO.getCodeId());
		integralRecord.setProductId(scanCodeInfoMO.getProductId());
		integralRecord.setProductName(lotteryOprationDto.getProductName());
		integralRecord.setActivitySetId(scanCodeInfoMO.getActivitySetId());
		integralRecord.setOrganizationId(lotteryOprationDto.getOrganizationId());
		integralRecord.setOrganizationName(lotteryOprationDto.getOrganizationName());
		integralRecord.setIntegralReason(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReason());
		integralRecord.setIntegralReasonCode(IntegralReasonEnum.ACTIVITY_INTEGRAL.getIntegralReasonCode());
		lotteryOprationDto.setIntegralRecord(integralRecord);
		//判断realprize是否为0,0表示为新增的虚拟不中奖奖项，为了计算中奖率设置
		int realPrize = prizeTypeMO.getRealPrize().intValue();
		if(realPrize == 0) {
			return lotteryOprationDto.lotterySuccess("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
		}
		Byte awardType = prizeTypeMO.getAwardType();
		//如果awardType中奖类型为空或者4则表示为红包中奖，这里统一都设置为4
		if (awardType == null) {
			awardType = (byte)4;
			prizeTypeMO.setAwardType(awardType);
			lotteryOprationDto.setPrizeTypeMO(prizeTypeMO);
		}
		//奖品类型为1或者9的表示需要有库存的
		if (awardType.intValue() == 1 || awardType.intValue() == 9) {
			ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
			String key = prizeTypeMO.getId() + "_"+prizeTypeMO.getActivitySetId();
			//需要消耗库存的
			valueOperations.setIfAbsent(key, prizeTypeMO.getRemainingStock().toString());
			redisTemplate.expire(key, 60, TimeUnit.SECONDS);
			Long stockNum = valueOperations.increment(key, -1);
			if (stockNum == null || stockNum < 0) {
				return lotteryOprationDto.lotterySuccess("别灰心，这次的擦肩而过，是为了下次拔得更大的奖项");
			}
		}
		return lotteryOprationDto.lotterySuccess(1);
	}


	@Transactional(rollbackFor = Exception.class)
	public WxOrderPayDto saveLottory(LotteryOprationDto lotteryOprationDto, String remoteAddr) throws Exception {
		IntegralRecord integralRecord = lotteryOprationDto.getIntegralRecord();
		MarketingMembers marketingMembersInfo = lotteryOprationDto.getMarketingMembersInfo();
		int consumeIntegralNum = lotteryOprationDto.getConsumeIntegralNum();
		//添加积分消耗记录
		if (consumeIntegralNum > 0) {
			integralRecord.setIntegralNum(0 - consumeIntegralNum);
			addToInteral(marketingMembersInfo, integralRecord);
		}
		int changeIntegral = 0 - consumeIntegralNum;
		ScanCodeInfoMO scanCodeInfoMO = lotteryOprationDto.getScanCodeInfoMO();
		String mobile = scanCodeInfoMO.getMobile();
		String openId = scanCodeInfoMO.getOpenId();
		String outerCodeId = scanCodeInfoMO.getCodeId();
		Long activitySetId = scanCodeInfoMO.getActivitySetId();
		MarketingActivity activity = lotteryOprationDto.getMarketingActivity();
		MarketingPrizeTypeMO prizeTypeMO = lotteryOprationDto.getPrizeTypeMO();
		String organizationId = lotteryOprationDto.getOrganizationId();
		String productId = scanCodeInfoMO.getProductId();
		String productBatchId = scanCodeInfoMO.getProductBatchId();
		Byte awardType = prizeTypeMO.getAwardType();
		if (awardType == null ) {
			awardType = (byte)4;
		}
		LotteryResultMO lotteryResultMO = lotteryOprationDto.getLotteryResultMO();
		Float amount = null;
		//已中奖执行奖品方法中奖纪录保存等逻辑
		lotteryResultMO.setAwardType(awardType);
		//SuccessLottory == 1
		if (lotteryOprationDto.getSuccessLottory() == 1) {
			switch (awardType.intValue()) {
				case 1:
				case 9://实物
					mPrizeTypeMapper.updateRemainingStock(prizeTypeMO.getId());
					lotteryResultMO.setMsg("恭喜您，获得" + prizeTypeMO.getPrizeTypeName());
					Map<String, Object> lotteryDataMap = new HashMap<>();
					lotteryDataMap.put("prizeId", prizeTypeMO.getId());
					lotteryDataMap.put("prizeName", prizeTypeMO.getPrizeTypeName());
					lotteryResultMO.setData(lotteryDataMap);
					break;
				case 2: //奖券
					lotteryResultMO.setData(prizeTypeMO.getCardLink());
					lotteryResultMO.setMsg("恭喜您，获得" + prizeTypeMO.getPrizeTypeName());
					break;
				case 3: //积分
					int awardIntegralNum = prizeTypeMO.getAwardIntegralNum().intValue();
					changeIntegral = changeIntegral + awardIntegralNum;
					lotteryResultMO.setMsg("恭喜您，获得" + awardIntegralNum + "积分");
					integralRecord.setIntegralNum(awardIntegralNum);
					addToInteral(marketingMembersInfo, integralRecord);
					break;
				case 4:
					amount = prizeTypeMo(prizeTypeMO);
					String strAmount = String.format("%.2f", amount);
					lotteryResultMO.setData(strAmount);
					lotteryResultMO.setMsg(strAmount);
				default:
					break;
			}
		}
		addWinRecord(outerCodeId, mobile, openId, activitySetId, activity, organizationId,lotteryOprationDto.getOrganizationName(), prizeTypeMO, amount, productId, productBatchId);
		if (changeIntegral != 0) {
			marketingMembersMapper.deleteIntegral(0 - changeIntegral, marketingMembersInfo.getId());
		}
		RestResult restResult = lotteryOprationDto.getRestResult();
		restResult.setMsg(lotteryResultMO.getMsg());
		if (amount != null && amount > 0) {
			WxOrderPayDto wxOrderPayDto = new WxOrderPayDto();
			wxOrderPayDto.setAmount(amount*100);
			wxOrderPayDto.setMobile(mobile);
			wxOrderPayDto.setOpenId(openId);
			wxOrderPayDto.setOrganizationId(organizationId);
			wxOrderPayDto.setOuterCodeId(outerCodeId);
			wxOrderPayDto.setReferenceRole(ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
			wxOrderPayDto.setRemoteAddr(remoteAddr);
			wxOrderPayDto.setSendAudit(lotteryOprationDto.getSendAudit());
			return wxOrderPayDto;
		} else {
			lotteryResultMO = new LotteryResultMO("哎呀，手气不好，没抽中");
			lotteryResultMO.setData("哎呀，手气不好，没抽中");
			restResult.setResults(lotteryResultMO);
			restResult.setMsg(lotteryResultMO.getMsg());
			lotteryOprationDto.setRestResult(restResult);
			lotteryOprationDto.setLotteryResultMO(lotteryResultMO);
		}
		return null;
	}

	/**
	 * 保存订单并发起微信支付
	 *
	 * @param wxOrderPayDto
	 * @throws Exception
	 */
	public void saveTradeOrder(WxOrderPayDto wxOrderPayDto) throws Exception {
		float amount = wxOrderPayDto.getAmount();
		String mobile = wxOrderPayDto.getMobile();
		String openId = wxOrderPayDto.getOpenId();
		String organizationId = wxOrderPayDto.getOrganizationId();
		String outerCodeId = wxOrderPayDto.getOuterCodeId();
		byte referenceRole = wxOrderPayDto.getReferenceRole();
		String remoteAddr = wxOrderPayDto.getRemoteAddr();
		byte sendAudit = wxOrderPayDto.getSendAudit();
		weixinpay(sendAudit,outerCodeId, mobile, openId, organizationId, amount, remoteAddr, referenceRole);
	}

	private void addWinRecord(String outCodeId, String mobile, String openId, Long activitySetId,
							  MarketingActivity activity, String organizationId,String organizationFullName, MarketingPrizeTypeMO mPrizeTypeMO, Float amount, String productId, String productBatchId) {
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
		redWinRecord.setOrganizationFullName(organizationFullName);
		redWinRecord.setProductBatchId(productBatchId);
		mWinRecordMapper.addWinRecord(redWinRecord);
	}

	private void addToInteral(MarketingMembers marketingMembersInfo, IntegralRecord integralRecord) {
		integralRecord.setCreateDate(new Date());
		integralRecord.setCustomerId(marketingMembersInfo.getCustomerId());
		integralRecord.setCustomerName(marketingMembersInfo.getCustomerName());
		integralRecord.setMemberId(marketingMembersInfo.getId());
		integralRecord.setMemberName(marketingMembersInfo.getUserName());
		integralRecord.setMemberType(marketingMembersInfo.getMemberType());
		integralRecord.setMobile(marketingMembersInfo.getMobile());
		integralRecord.setStatus("1");
		integralRecordMapperExt.insertSelective(integralRecord);
	}
	/////////////////////////////////////////ES判断抽奖码/////////////////////////////////////////////////////
	public LotteryOprationDto holdLockJudgeES(LotteryOprationDto lotteryOprationDto) {
		ScanCodeInfoMO scanCodeInfoMO = lotteryOprationDto.getScanCodeInfoMO();
		int memberType = lotteryOprationDto.getMarketingMembersInfo().getMemberType();
		Long memberId = lotteryOprationDto.getMarketingMembersInfo().getId();
		Integer eachDayNumber = lotteryOprationDto.getEachDayNumber();
		String organizationId = lotteryOprationDto.getOrganizationId();
		MarketingMembers marketingMembersInfo = lotteryOprationDto.getMarketingMembersInfo();
		IntegralRecord integralRecord = lotteryOprationDto.getIntegralRecord();
		//MarketingActivityProduct marketingActivityProduct
		long activitySetId = scanCodeInfoMO.getActivitySetId();
		String codeId = scanCodeInfoMO.getCodeId();
		String codeTypeId = scanCodeInfoMO.getCodeTypeId();
		String openId = scanCodeInfoMO.getOpenId();
		String productId = scanCodeInfoMO.getProductId();
		String productBatchId = scanCodeInfoMO.getProductBatchId();
		boolean acquireLock =false;
		String lockKey = activitySetId + ":" + codeId + ":" + codeTypeId;
		try {
			acquireLock = lock.lock(lockKey, 5000, 5, 200);
			if(!acquireLock) {
				logger.error("{锁获取失败:" +lockKey+ ",请检查}");
				redisUtil.hmSet("marketing:lock:fail",lockKey,new Date());
				return lotteryOprationDto.lotterySuccess("扫码人数过多,请稍后再试");
			}
			String opneIdNoSpecialChactar = StringUtils.isBlank(openId)? null:CommonUtil.replaceSpicialChactar(openId);
			long codeCount = codeEsService.countByCode(codeId, codeTypeId, memberType);
			if (codeCount > 0) {
				return lotteryOprationDto.lotterySuccess("您手速太慢，该码已被其它用户领取");
			}
			long nowTimeMills = DateUtils.parseDate(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(System.currentTimeMillis()),"yyyy-MM-dd").getTime();
			//校验有没有设置活动用户扫码量限制
			if (eachDayNumber != null && eachDayNumber > 0) {
				long userscanNum = codeEsService.countByUserAndActivityQuantum(opneIdNoSpecialChactar, activitySetId, nowTimeMills);
				logger.info("领取方法=====：根据openId="+opneIdNoSpecialChactar+",activitySetId="+activitySetId+"获得的用户扫码记录次数为="+userscanNum+",当前活动扫码限制次数为："+eachDayNumber);
				if (userscanNum >= eachDayNumber) {
					return lotteryOprationDto.lotterySuccess("您今日扫码已超过该活动限制数量");
				}
			}
			codeEsService.addScanCodeRecord(opneIdNoSpecialChactar, productId, productBatchId, codeId, codeTypeId, activitySetId,nowTimeMills,organizationId,0,memberId);
			lotteryOprationDto.setSuccessLottory(1);
			return lotteryOprationDto;
		} catch (Exception e){
			logger.error("扫码获取锁出错抽奖失败", e);
			return lotteryOprationDto.lotterySuccess("扫码人数过多,请稍后再试");
		} finally {
			if(acquireLock){
				lock.releaseLock(lockKey);
			}
		}
	}

	/////////////////////////////////////////ES判断抽奖码/////////////////////////////////////////////////////

	private Float prizeTypeMo(MarketingPrizeTypeMO mPrizeTypeMO) {
		Float amount=mPrizeTypeMO.getPrizeAmount();
		Byte randAmount=mPrizeTypeMO.getIsRrandomMoney();
		//如果是随机金额则生成随机金额
		if (randAmount.equals((byte)1)) {
			float min=mPrizeTypeMO.getLowRand();
			float max=mPrizeTypeMO.getHighRand();
			amount = new Random().nextFloat() *((max-min)) +min;
		}
		Float finalAmount = amount;//金额转化为分
		return finalAmount;
	}

	private void weixinpay(byte sendAudit, String winningCode, String mobile, String openId, String organizationId, Float finalAmount, String remoteAddr, byte referenceRole)
			throws SuperCodeException, Exception {
		if (StringUtils.isBlank(openId)) {
			throw  new SuperCodeException("微信支付openid不能为空",200);
		}
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
		tradeOrder.setWinningCode(winningCode);
		tradeOrder.setReferenceRole(referenceRole);
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr = serverIp;
		}
		tradeOrder.setRemoteAddr(remoteAddr);
		wXPayTradeOrderMapper.insert(tradeOrder);
		//如果该活动时立刻发送红包，则直接调用微信支付发红包
		if (sendAudit == 0) {
			wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(), partner_trade_no, organizationId);
		}
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
