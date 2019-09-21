package com.jgw.supercodeplatform.marketing.service;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.*;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PlatformLotteryService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private MarketingActivitySetMapper mSetMapper;
    @Autowired
    private MarketingMembersMapper marketingMembersMapper;
    @Autowired
    private MarketingActivityMapper mActivityMapper;
    @Autowired
    private MarketingPrizeTypeMapper marketingPrizeTypeMapper;
    @Autowired
    private MarketingPlatformOrganizationMapper marketingPlatformOrganizationMapper;
    @Autowired
    private CodeEsService codeEsService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisLockUtil lock;
    @Autowired
    private WXPayTradeNoGenerator wXPayTradeNoGenerator;
    @Value("${marketing.server.ip}")
    private String serverIp;
    @Autowired
    private WXPayService wxpService;
    @Autowired
    private WXPayTradeOrderMapper wXPayTradeOrderMapper;
    @Autowired
    private MarketingWxMerchantsMapper marketingWxMerchantsMapper;
    @Autowired
    private MarketingMembersWinRecordMapper mWinRecordMapper;

    public LotteryOprationDto checkLotteryCondition(LotteryOprationDto lotteryOprationDto, ScanCodeInfoMO scanCodeInfoMO) throws ParseException, SuperCodeException {
        String codeId=scanCodeInfoMO.getCodeId();
        String codeTypeId=scanCodeInfoMO.getCodeTypeId();
        String productName = scanCodeInfoMO.getProductBatchId();
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
        MarketingActivity activity = mActivityMapper.selectById(mActivitySet.getActivityId());
        if (null == activity) {
            throw new SuperCodeExtException("该活动不存在", 200);
        }
        Long userId = scanCodeInfoMO.getUserId();
        MarketingMembers marketingMembersInfo = marketingMembersMapper.getMemberById(userId);
        if(marketingMembersInfo == null){
            throw new SuperCodeExtException("会员信息不存在",200);
        }
        if( null != marketingMembersInfo.getState() && marketingMembersInfo.getState() == 0){
            throw new SuperCodeExtException("对不起,该会员已被加入黑名单",200);
        }
        List<MarketingPrizeTypeMO> moPrizeTypes = marketingPrizeTypeMapper.selectMOByActivitySetIdIncludeUnreal(activitySetId);
        if (CollectionUtils.isEmpty(moPrizeTypes)) {
            return lotteryOprationDto.lotterySuccess("该活动未设置中奖奖次");
        }
        lotteryOprationDto.setSendAudit(mActivitySet.getSendAudit());
        lotteryOprationDto.setMarketingMembersInfo(marketingMembersInfo);
        lotteryOprationDto.setOrganizationId(scanCodeInfoMO.getOrganizationId());
        MarketingPlatformOrganization marketingPlatformOrganization = marketingPlatformOrganizationMapper.selectByActivitySetIdAndOrganizationId(mActivitySet.getId(), scanCodeInfoMO.getOrganizationId());
        if (marketingPlatformOrganization == null) {
            throw new SuperCodeExtException("当前该组织未参与全网运营活动抽奖");
        }
        lotteryOprationDto.setOrganizationName(marketingPlatformOrganization.getOrganizationFullName());
        lotteryOprationDto.setScanCodeInfoMO(scanCodeInfoMO);
        lotteryOprationDto.setMarketingActivity(activity);
        lotteryOprationDto.setProductName(productName);
        String conditon = mActivitySet.getValidCondition();
        if (StringUtils.isBlank(conditon)) {
            Integer maxJoinNum = JSON.parseObject(conditon).getInteger("maxJoinNum");
            if (maxJoinNum == null) {
                lotteryOprationDto.setEachDayNumber(0);
            } else {
                lotteryOprationDto.setEachDayNumber(maxJoinNum);
            }
        }
        //执行抽奖逻辑
        lotteryOprationDto.setPrizeTypeMO(LotteryUtilWithOutCodeNum.startLottery(moPrizeTypes));
        return lotteryOprationDto;
    }


    public LotteryOprationDto holdLockJudgeES(LotteryOprationDto lotteryOprationDto) {
        ScanCodeInfoMO scanCodeInfoMO = lotteryOprationDto.getScanCodeInfoMO();
        MarketingPrizeTypeMO prizeTypeMO = lotteryOprationDto.getPrizeTypeMO();
        Float amount = prizeTypeMO.getPrizeAmount();
        String innerCode = lotteryOprationDto.getInnerCode();
        //获取该活动的每个用户最多扫码次数
        Integer maxScanNumber = lotteryOprationDto.getEachDayNumber();
        String organizationId = lotteryOprationDto.getOrganizationId();
        long activitySetId = scanCodeInfoMO.getActivitySetId();
        String codeId = scanCodeInfoMO.getCodeId();
        String codeTypeId = scanCodeInfoMO.getCodeTypeId();
        String openId = scanCodeInfoMO.getOpenId();
        Long userId = scanCodeInfoMO.getUserId();
        String productId = scanCodeInfoMO.getProductId();
        String productBatchId = scanCodeInfoMO.getProductBatchId();
        String organizationName = lotteryOprationDto.getOrganizationName();
        boolean acquireLock =false;
        String lockKey = activitySetId + ":" + codeId + ":" + codeTypeId;
        try {
            acquireLock = lock.lock(lockKey, 5000, 5, 200);
            if(!acquireLock) {
                log.error("{锁获取失败:" +lockKey+ ",请检查}");
                redisUtil.hmSet("marketing:lock:fail",lockKey,new Date());
                return lotteryOprationDto.lotterySuccess("扫码人数过多,请稍后再试");
            }
            String opneIdNoSpecialChactar = StringUtils.isBlank(openId)? null: CommonUtil.replaceSpicialChactar(openId);
            long codeCount = codeEsService.countPlatformScanCodeRecord(innerCode, null);
            if (codeCount > 0) {
                return lotteryOprationDto.lotterySuccess("您手速太慢，该码已被其它用户领取");
            }
            long nowTimeMills = DateUtils.parseDate(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(System.currentTimeMillis()),"yyyy-MM-dd").getTime();
            //校验有没有设置活动用户扫码量限制
            if (maxScanNumber != null && maxScanNumber > 0) {
                long userscanNum = codeEsService.countByUserAndActivityPlatform(opneIdNoSpecialChactar, activitySetId);
                log.info("领取方法=====：根据openId="+opneIdNoSpecialChactar+",activitySetId="+activitySetId+"获得的用户扫码记录次数为="+userscanNum+",当前活动扫码限制次数为："+maxScanNumber);
                if (userscanNum >= maxScanNumber) {
                    return lotteryOprationDto.lotterySuccess("您扫码已超过该活动限制数量");
                }
            }
            codeEsService.addPlatformScanCodeRecord(innerCode, productId, productBatchId, codeId, openId,userId,0, 5L, codeTypeId,activitySetId, nowTimeMills,organizationId,organizationName,amount);
            lotteryOprationDto.setSuccessLottory(1);
            return lotteryOprationDto;
        } catch (Exception e){
            log.error("扫码获取锁出错抽奖失败", e);
            return lotteryOprationDto.lotterySuccess("扫码人数过多,请稍后再试");
        } finally {
            if(acquireLock){
                lock.releaseLock(lockKey);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RestResult<LotteryResultMO> saveLottory(LotteryOprationDto lotteryOprationDto, String remoteAddr) throws Exception {
        String winningCode = lotteryOprationDto.getScanCodeInfoMO().getCodeId();
        String openId = lotteryOprationDto.getScanCodeInfoMO().getOpenId();
        String mobile = lotteryOprationDto.getScanCodeInfoMO().getMobile();
        MarketingActivity marketingActivity = lotteryOprationDto.getMarketingActivity();
        String productId = lotteryOprationDto.getScanCodeInfoMO().getProductId();
        String productName = lotteryOprationDto.getScanCodeInfoMO().getProductName();
        String productBatchId = lotteryOprationDto.getScanCodeInfoMO().getProductBatchId();
        //该组织ID为支付的组织ID
        String organizationId = marketingWxMerchantsMapper.getJgw().getOrganizationId();
        //转化为分
        MarketingPrizeTypeMO prizeTypeMo = lotteryOprationDto.getPrizeTypeMO();
        Float amount = prizeTypeMo.getPrizeAmount();
        Float finalAmount = amount * 100;
        if (StringUtils.isBlank(openId)) {
            throw  new SuperCodeExtException("微信支付openid不能为空",200);
        }
        //添加中奖纪录
        addWinRecord(winningCode, mobile, openId,productName,lotteryOprationDto.getScanCodeInfoMO().getActivitySetId(), marketingActivity, lotteryOprationDto.getOrganizationId(), lotteryOprationDto.getOrganizationName(), prizeTypeMo.getId(),amount,productId,productBatchId);
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
        tradeOrder.setReferenceRole(ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = serverIp;
        }
        tradeOrder.setRemoteAddr(remoteAddr);
        wXPayTradeOrderMapper.insert(tradeOrder);
        //如果该活动时立刻发送红包，则直接调用微信支付发红包
        wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(), partner_trade_no, organizationId);
        String strAmount = String.format("%.2f", amount);
        LotteryResultMO lotteryResultMO = new LotteryResultMO();
        RestResult<LotteryResultMO> restResult = new RestResult<>();
        lotteryResultMO.setData(strAmount);
        lotteryResultMO.setMsg(strAmount);
        restResult.setMsg(lotteryResultMO.getMsg());
        restResult.setResults(lotteryResultMO);
        return restResult;
    }

    private void addWinRecord(String outCodeId, String mobile, String openId, String productName,Long activitySetId,
                              MarketingActivity activity, String organizationId,String organizationFullName, Long prizeTypeId, Float amount, String productId, String productBatchId) {
        //插入中奖纪录
        MarketingMembersWinRecord redWinRecord=new MarketingMembersWinRecord();
        redWinRecord.setActivityId(activity.getId());
        redWinRecord.setActivityName(activity.getActivityName());
        redWinRecord.setActivitySetId(activitySetId);
        redWinRecord.setMobile(mobile);
        redWinRecord.setOpenid(openId);
        redWinRecord.setPrizeTypeId(prizeTypeId);
        redWinRecord.setWinningAmount(amount );
        redWinRecord.setProductId(productId);
        redWinRecord.setWinningCode(outCodeId);
        redWinRecord.setPrizeName(productName);
        redWinRecord.setOrganizationId(organizationId);
        redWinRecord.setOrganizationFullName(organizationFullName);
        redWinRecord.setProductBatchId(productBatchId);
        mWinRecordMapper.addWinRecord(redWinRecord);
    }

}
