package com.jgw.supercodeplatform.marketing.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.*;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.LotteryOprationDto;
import com.jgw.supercodeplatform.marketing.enums.market.*;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.constants.ParticipationConditionConstant;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.SalerScanInfo;
import com.jgw.supercodeplatform.marketing.exception.SalerLotteryException;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;

/**
 * 导购员领奖
 */
@Slf4j
@Service
public class SalerLotteryService {
 
    @Autowired
    private MarketingPrizeTypeMapper mMarketingPrizeTypeMapper;

    @Autowired
    private WXPayTradeOrderMapper wXPayTradeOrderMapper;

    @Autowired
    private GlobalRamCache globalRamCache;

    @Autowired
    private WXPayService wxpService;


    @Autowired
    private CodeEsService codeEsService;

    @Autowired
    private WXPayTradeNoGenerator wXPayTradeNoGenerator ;

    @Autowired
    private MarketingUserMapperExt mapper;
    @Autowired
    private MarketingSaleMemberService marketingSaleMemberService;
    @Autowired
    private MarketingActivitySetMapper mSetMapper;
    @Autowired
    private  IntegralRecordMapperExt integralRecordMapperExt;
    @Autowired
    private MarketingMembersService marketingMembersService;
    @Autowired
    private MarketingActivityProductMapper productMapper;

    @Autowired
    private MarketingMembersWinRecordMapper mWinRecordMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MarketingWxMerchantsMapper mWxMerchantsMapper;
    @Autowired
    private RedisLockUtil lock;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${marketing.server.ip}")
    private String serverIp;

    /**
     * 现在开始所有的扫码信息都要录入到marketingscan
     * 每个领奖码信息录入到自己的业务索引
     * todo 后期需要考虑渠道处理，本期不加
     * @param scanCodeInfoMO
     * @param jwtUser
     * @return
     * @throws SuperCodeException
     */
    public LotteryResultMO salerlottery(ScanCodeInfoMO scanCodeInfoMO, H5LoginVO jwtUser,HttpServletRequest request) throws Exception {
        /**
         * 扫码条件:
         *  1 活动规则
         *    1.1 活动时间【现在到未来】
         *    1.2 活动状态被启用
         *    1.3 活动类型
         *    1.3 中奖概率
         *    1.4 每人每天领取上限【默认200】
         *    1.5 参与条件
         *    1.6 活动产品【码平台】 消息中心处理
         *    1.7 自动追加【码平台】 消息中心处理
         *
         *
         *  3 属于该码对应组织下的销售员【不可以跨组织】
         *  4 活动码没有被扫过
         *  5 配置了活动规则
         *
         *  6
         *  获取结果=》【中奖金额，随机/固定】
         *  微信公众号相关信息支付配置
         *  支付相关配置信息配置
         *  ====================异步;对接微信处理中奖金额账本=====================
         *  【微信成功处理后如网页中断则通过查询记录看自己的数据/或者微信看自己的数据】
         *  7 返回中奖或没中奖金额
         *
         *
         *  备注:多人同时扫码的并发处理
         */

        // step-2 活动主体数据获取
        String productId           = scanCodeInfoMO.getProductId();
        String productBatchId = scanCodeInfoMO.getProductBatchId();
        if ("".equals(productBatchId)) {
            productBatchId = null;
        }
        Long activitySetId     = scanCodeInfoMO.getActivitySetId();
        //   活动数据
        Map<String, Object> map = getBizData(productId, productBatchId, activitySetId);
        //  活动数据
        MarketingActivitySet marketingActivitySet = (MarketingActivitySet) map.get("marketingActivitySet");
        List<MarketingPrizeTypeMO> marketingPrizeTypes = (List<MarketingPrizeTypeMO>) map.get("marketingPrizeTypes");
        MarketingActivityProduct marketingActivityProduct = (MarketingActivityProduct) map.get("marketingActivityProduct");
        // step3:业务数据和校验
        // 校验后返回业务【活动】数据用于业务处理
        UserWithWechat userWithWechat = validateBizForUserBySalerlottery(jwtUser);
        MarketingActivitySetCondition marketingActivitySetCondition = validateSetRule(marketingActivitySet, scanCodeInfoMO.getCodeId(), scanCodeInfoMO.getCodeTypeId(), userWithWechat.getOrganizationId(), userWithWechat.getMemberId());
        // 中奖金额
        MarketingPrizeTypeMO marketingPrizeTypeMO = LotteryUtilWithOutCodeNum.startLottery(marketingPrizeTypes);
        //TODO 需要先判断消费者是否已经领取
        // 参与条件
        IntegralRecord record = new IntegralRecord();
        Byte participationCondition = marketingActivitySetCondition.getParticipationCondition();
        LotteryResultMO lotteryResultMO = new LotteryResultMO();
        if (participationCondition.intValue() == ParticipationConditionConstant.activity ){
            MarketingMembersWinRecord membersWinRecord = mWinRecordMapper.getRecordByCodeId(scanCodeInfoMO.getCodeId());
            if(membersWinRecord == null) {
                lotteryResultMO.setWinnOrNot(0);
                lotteryResultMO.setMsg("请先让消费者扫码领红包");
                return lotteryResultMO;
            }
            MemberWithWechat marketingMembers = marketingMembersService.selectByOpenIdAndOrgIdWithTemp(membersWinRecord.getOpenid(), membersWinRecord.getOrganizationId());
            record.setMobile(marketingMembers.getMobile());
            record.setMemberName(marketingMembers.getUserName());
            record.setMemberId(marketingMembers.getMemberId());
        }else if(participationCondition.intValue() ==ParticipationConditionConstant.integral ){
            List<IntegralRecord> integralRecordList = integralRecordMapperExt.getMemberIntegralRecord(scanCodeInfoMO.getCodeId());
            if(CollectionUtils.isEmpty(integralRecordList)) {
                lotteryResultMO.setWinnOrNot(0);
                lotteryResultMO.setMsg("请先让消费者扫码领取积分");
                return lotteryResultMO;
            }
            IntegralRecord integralRecord = integralRecordList.get(0);
            record.setMobile(integralRecord.getMobile());
            record.setMemberName(integralRecord.getMemberName());
            record.setMemberId(integralRecord.getMemberId());
        }

        // 备注:保存前在原子性校验一次【扫码成功则保存】 es准实时的特性有坑，采用es自带的乐观锁功能解决该问题！！！！
        // 除去备注校验,此处业务在逻辑上可以发送微信红包金额,此外不管微信是否发送红包成功都产生相关正向记录
        // 后期可以通过对账解决为支付红包的中奖记录
        return doSalerlottery(record,userWithWechat,marketingActivitySet,marketingPrizeTypeMO,marketingActivityProduct,scanCodeInfoMO,marketingActivitySetCondition,request);
    }


    private Float prizeTypeMo(MarketingPrizeTypeMO mPrizeTypeMO) {
        Float amount=mPrizeTypeMO.getPrizeAmount();
        Byte randAmount=mPrizeTypeMO.getIsRrandomMoney();
        //如果是随机金额则生成随机金额
        if (randAmount.equals((byte)1)) {
            float min=mPrizeTypeMO.getLowRand();
            float max=mPrizeTypeMO.getHighRand();
            amount = new Random().nextFloat() *((max-min)) +min;
        }
        return amount == null? 0:amount;
    }


    private void weixinpayForSaler(String partnerTradeNo,byte sendAudit, String winningCode, String mobile, String openId, String organizationId, Float finalAmount, String remoteAddr, byte referenceRole)
            throws SuperCodeException{
        if (StringUtils.isBlank(openId)) {
            throw  new SuperCodeException("微信支付openid不能为空",500);
        }
        MarketingWxMerchants marketingWxMerchants = mWxMerchantsMapper.get(organizationId);
        if (marketingWxMerchants == null ){
            throw new SuperCodeExtException("该组织未绑定公众号信息");
        }
        if (marketingWxMerchants.getMerchantType() == 1) {
            if (marketingWxMerchants.getJgwId() != null) {
                organizationId = mWxMerchantsMapper.getJgw(marketingWxMerchants.getJgwId()).getOrganizationId();
            } else {
                organizationId = mWxMerchantsMapper.getDefaultJgw().getOrganizationId();
            }
        }
        // TODO 改成枚举
        log.error("{ 中奖记录保存：手机号=> + " + mobile +"==}");
        //保存订单
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WXPayTradeOrder tradeOrder = new WXPayTradeOrder();
        tradeOrder.setAmount(finalAmount);
        tradeOrder.setOpenId(openId);
        tradeOrder.setTradeStatus((byte) 0);
        tradeOrder.setPartnerTradeNo(partnerTradeNo);
        tradeOrder.setTradeDate(format.format(new Date()));
        tradeOrder.setOrganizationId(organizationId);
        tradeOrder.setWinningCode(winningCode);
        tradeOrder.setReferenceRole(referenceRole);

        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = serverIp;
        }
        tradeOrder.setRemoteAddr(remoteAddr);
        wXPayTradeOrderMapper.insert(tradeOrder);
        try {
            // 目前的中奖逻辑是补偿用户相关中奖金额
            if (sendAudit == 0) {
                wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(), partnerTradeNo, organizationId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * 执行中奖逻辑
     * @param  导购用户信息
     * @param marketingActivitySet 活动信息
     * @param marketingPrizeType 中奖信息
     * @param marketingActivityProduct 产品信息
     */
    private LotteryResultMO doSalerlottery(IntegralRecord record, UserWithWechat userWithWechat, MarketingActivitySet marketingActivitySet,
                                           MarketingPrizeTypeMO marketingPrizeType, MarketingActivityProduct marketingActivityProduct,
                                           ScanCodeInfoMO scanInfo, MarketingActivitySetCondition marketingActivitySetCondition,HttpServletRequest request) throws Exception {
        LotteryResultMO lotteryResultMO = new LotteryResultMO();
        lotteryResultMO.setWinnOrNot(0);
        lotteryResultMO.setSendAudit(marketingActivitySet.getSendAudit());
        // 索引 es saler成功信息
        if(StringUtils.isBlank(userWithWechat.getOpenid())){
            throw new SuperCodeException("首次扫码请使用微信...");
        }
        //TODO 需要判断消费者是否已经领取了积分或者红包
        String lockName = new StringBuffer("saler:").append(scanInfo.getCodeId()).append(":").append(scanInfo.getCodeTypeId()).toString();
        boolean acquireLock = lock.lock(lockName,5000,5,200);
        boolean indexSuccess = false;
        if(acquireLock){
            try {
                // TODO 数据一致性: 查询是不是没扫过
                // 备注:保存前在原子性校验一次【扫码成功则保存】 es准实时的特性有坑，采用es自带的乐观锁功能解决该问题！！！！
                boolean scaned = codeEsService.searchCodeScaned(scanInfo.getCodeTypeId(),scanInfo.getCodeId());
                if(scaned){
                    throw new SalerLotteryException("手速慢啦，换个码在试吧");
                }
                // 基于es 乐观锁处理非实时特性
                SalerScanInfo param                             = new SalerScanInfo();
                param                                .setCodeId(scanInfo.getCodeId());
                param                               .setUserId(userWithWechat.getMemberId());
                param                           .setOpenId(userWithWechat.getOpenid());
                param                           .setMobile(userWithWechat.getMobile());
                param                        .setCodeTypeId(scanInfo.getCodeTypeId());
                param                   .setMemberType(userWithWechat.getMemberType());
                param                  .setScanCodeTime(System.currentTimeMillis());
                param                 .setActivitySetId(marketingActivitySet.getId());
                param            .setActivityId(marketingActivitySet.getActivityId());
                param           .setOrganizationId(userWithWechat.getOrganizationId());// 此时一定是同一个组织id
                param          .setProductId(marketingActivityProduct.getProductId());
                param.setProductBatchId(marketingActivityProduct.getProductBatchId());
                param  .setReferenceRole(marketingActivityProduct.getReferenceRole());
                codeEsService.indexSalerScanInfo(param);// 基于锁机制保存领奖成功信息到es
                indexSuccess = true;
            } catch (SuperCodeException e) {
                e.printStackTrace();
            }finally {
                lock.releaseLock(lockName);
            }
            if(indexSuccess){
                // 保存 微信支付数据
                // 更新微信回调状态
                // 中奖记录
                String successFlag = SalerAmountStatusEnum.ACQUIRE_FAIL.status;
                String partnerTradeNo = null;
                Float amount                          = prizeTypeMo(marketingPrizeType);
                if (amount != null && amount > 0) {
                    partnerTradeNo = wXPayTradeNoGenerator.tradeNo();
                    successFlag = SalerAmountStatusEnum.ACQUIRE_SUCCESS.status;
                }
                record                                                         .setStatus(successFlag);
                record                                                         .setSalerAmount(amount);
                record                                                      .setCreateDate(new Date());
                record                                              .setSalerId(userWithWechat.getMemberId());
                record                                           .setOuterCodeId(scanInfo.getCodeId());
                record                                        .setCodeTypeId(scanInfo.getCodeTypeId());
                record                                      .setSalerMobile(userWithWechat.getMobile());
                record                                      .setSalerName(userWithWechat.getUserName());
                record                                   .setCustomerId(userWithWechat.getCustomerId());
                record                                   .setMemberType(userWithWechat.getMemberType());
                record                                 .setActivitySetId(marketingActivitySet.getId());
                record                               .setCustomerName(userWithWechat.getCustomerName());
                record                           .setOrganizationId(userWithWechat.getOrganizationId());
                record                          .setProductId(marketingActivityProduct.getProductId());
                record                      .setProductName(marketingActivityProduct.getProductName());
                record.setTradeNo(partnerTradeNo);
                // 参与条件
                Byte participationCondition = marketingActivitySetCondition.getParticipationCondition();

                if (participationCondition.intValue() == ParticipationConditionConstant.activity ){
                    record.setIntegralReason(IntegralReasonEnum.SALER_ACTIVITY.getIntegralReason());
                    record.setIntegralReasonCode(IntegralReasonEnum.SALER_ACTIVITY.getIntegralReasonCode());
                }else if(participationCondition.intValue() ==ParticipationConditionConstant.integral ){
                    record.setIntegralReason(IntegralReasonEnum.SALER_INTEGRAL.getIntegralReason());
                    record.setIntegralReasonCode(IntegralReasonEnum.SALER_INTEGRAL.getIntegralReasonCode());
                }else {
                    record.setIntegralReason(IntegralReasonEnum.SALER_NO_CONDITION.getIntegralReason());
                    record.setIntegralReasonCode(IntegralReasonEnum.SALER_NO_CONDITION.getIntegralReasonCode());
                }
                integralRecordMapperExt.insertSelective(record);
                if (partnerTradeNo == null) {
                    lotteryResultMO.setMsg("手气不好，没抽中！");
                    return lotteryResultMO;
                }
                weixinpayForSaler(partnerTradeNo,marketingActivitySet.getSendAudit(),scanInfo.getCodeId(),userWithWechat.getMobile(), userWithWechat.getOpenid(), userWithWechat.getOrganizationId(), amount*100, request.getRemoteAddr(), ReferenceRoleEnum.ACTIVITY_SALER.getType());
                lotteryResultMO.setWinnOrNot(1);
                lotteryResultMO.setMsg(String.format("%.2f", amount));
                lotteryResultMO.setData(lotteryResultMO.getMsg());
                return lotteryResultMO;
            }
        }
        lotteryResultMO.setMsg("抽奖失败，请稍后重试!");
        return lotteryResultMO;
    }


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdfWithSec = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *
     * TODO 分布式锁锁住码和码制以及共享数据相关校验：想想有没有必要
     * 活动设置业务规则校验
     * 基于码和码制唯一确定码
     * @param marketingActivitySet
     */
    private MarketingActivitySetCondition validateSetRule(MarketingActivitySet marketingActivitySet,String codeId,String codeTypeId,String organizationId,Long userId) throws SuperCodeException, SalerLotteryException {
        // 业务规则
        Date now = new Date();
        String nowStr = sdf.format(now);
        String activityStartDate = marketingActivitySet.getActivityStartDate();
        String activityEndDate = marketingActivitySet.getActivityEndDate();

        Integer activityStatus = marketingActivitySet.getActivityStatus();
        if(activityStatus == null || activityStatus != ActivityStatusEnum.UP.getType()){
            throw new SuperCodeException("活动未开启...");
        }


        if(activityStartDate == null || nowStr.compareTo(activityStartDate) < 0){
            // todo 检查日期比较
            throw new SuperCodeException("活动没有开始...");
        }

        if(activityEndDate == null || nowStr.compareTo(activityEndDate) > 0){
            // todo 检查日期比较
            throw new SuperCodeException("活动已经结束...");

        }



        String validCondition = marketingActivitySet.getValidCondition();
        // 反序列化门槛 :validCondition
        MarketingActivitySetCondition marketingActivitySetCondition = JSONObject.parseObject(validCondition, MarketingActivitySetCondition.class);
        // 参与红包条件
        if(marketingActivitySetCondition.getEachDayNumber() ==null ){
            // 这里不做200上限补偿处理，直接抛出异常保障业务逻辑清晰不耦合
            throw new SuperCodeException("活动数据没有完善...");
        }

        if(marketingActivitySetCondition.getParticipationCondition() ==null ){
            throw new SuperCodeException("活动数据没有完善呀...");
        }

        // 规则校验1
        switch (marketingActivitySetCondition.getParticipationCondition().intValue() ){

            case ParticipationConditionConstant.activity:
                Long vipscanNum = codeEsService.countByCode(codeId, codeTypeId, (int) MemberTypeEnums.VIP.getType());
                if(vipscanNum ==null || vipscanNum <= 0){
                    throw new SuperCodeException("请先协助会员领取红包");
                }
                break;
            // 是否有会员领取活动
            case ParticipationConditionConstant.integral:
                // 是否有会员领取积分
                Long memberscanNum = codeEsService.countCodeIntegral(codeId, codeTypeId);
                if(memberscanNum ==null || memberscanNum <= 0){
                    throw new SuperCodeException("请先协助会员领取积分");
                }
                break;
            case ParticipationConditionConstant.noCondition:
                break;
            default:
                throw new SuperCodeException("参与条件他说:不存在即是不合理...");
        }

        // 规则校验2
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startTime = cal.getTimeInMillis();
        cal.add(Calendar.DATE, 1);
        long endTime = cal.getTimeInMillis();
        int todayScanNum = codeEsService.countSalerNumByUserIdAndDate(organizationId, userId, startTime,endTime);
        if(todayScanNum > marketingActivitySetCondition.getEachDayNumber()){
            throw new SalerLotteryException("扫码虽好,可不要贪多哦!欢迎明天在试");
        }
        // 规则校验3
        int i = codeEsService.searchCodeScanedBySaler(codeId, codeTypeId);
        if(i>0){
            throw new SalerLotteryException("哎呀,有人领走啦！");
        }
        return marketingActivitySetCondition;

    }

    private Map<String, Object> getBizData(String productId, String productBatchId, Long activitySetId) throws SuperCodeException {
        // 1 活动数据
        MarketingActivitySet marketingActivitySet = mSetMapper.selectByIdWithActivityId(activitySetId, ActivityIdEnum.ACTIVITY_SALER.getId().longValue());
        // 2 奖次数据
        List<MarketingPrizeTypeMO> marketingPrizeTypes = mMarketingPrizeTypeMapper. selectMOByActivitySetIdIncludeUnreal( activitySetId);
        // 3 产品数据
        MarketingActivityProduct marketingActivityProduct = productMapper.selectByProductAndProductBatchIdWithReferenceRoleAndSetId(productId,
                productBatchId, ReferenceRoleEnum.ACTIVITY_SALER.getType(),activitySetId);
        // 业务数据初始校验
        if(marketingActivityProduct ==null || CollectionUtils.isEmpty(marketingPrizeTypes)
                || marketingActivitySet == null){
            if(log.isErrorEnabled()){
                log.error("扫码信息如下:productId_productBatchId_activitySetId:{}_{}_{},数据库信息如下活动:{} 奖次{} 产品{}",
                        productId,productBatchId,activitySetId,JSONObject.toJSONString(marketingActivitySet),
                        JSONObject.toJSONString(marketingPrizeTypes),JSONObject.toJSONString(marketingActivityProduct));
            }
            throw new SuperCodeException("导购活动设置未完善......");

        }


        Map<String, Object> result = new HashMap<>();
        result.put("marketingActivitySet",marketingActivitySet);
        result.put("marketingPrizeTypes",marketingPrizeTypes);
        result.put("marketingActivityProduct",marketingActivityProduct);
        return  result;
    }

    private UserWithWechat validateBizForUserBySalerlottery(H5LoginVO jwtUser) throws SuperCodeException{
        UserWithWechat userWithWechat = marketingSaleMemberService.selectById(jwtUser.getMemberId());
        if(userWithWechat == null){
            throw new SuperCodeException("biz:用户不存在");
        }
        if(userWithWechat.getState().intValue() != SaleUserStatus.ENABLE.getStatus().intValue()){
            throw new SuperCodeException("用户处于非启用状态");
        }
        String openid = redisUtil.get("memberuser:id:"+userWithWechat.getMemberId());
        if (mWxMerchantsMapper.get(jwtUser.getOrganizationId()).getMerchantType() == 1 && StringUtils.isNotBlank(openid)) {
            userWithWechat.setOpenid(openid);
        }
        return userWithWechat;
    }

    public ScanCodeInfoMO validateBasicBySalerlottery(String wxstate, H5LoginVO jwtUser) throws SuperCodeException {
        // 第一部分
        if(StringUtils.isBlank(jwtUser.getOrganizationId())){
            // 系统写token丢失参数
            throw new SuperCodeException("用户数据获取失败");
        }

        if(StringUtils.isBlank(jwtUser.getMobile())){
            // 系统写token丢失参数
            throw new SuperCodeException("用户数据获取失败");
        }

        if(jwtUser.getMemberId() == null){
            // 系统写token丢失参数
            throw new SuperCodeException("用户数据获取失败");
        }

        if(jwtUser.getMemberType() == null || MemberTypeEnums.SALER.getType().intValue() != jwtUser.getMemberType() ){
            throw new SuperCodeException("用户角色错误");
        }
        // 第二部分
        ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(wxstate);
        // 活动主体基本校验
        if(scanCodeInfoMO == null){
            if(log.isInfoEnabled()){
                log.info("扫码领奖没有获取到redis缓存:用户{},wxstate:{},now:{}", JSONObject.toJSONString(jwtUser),wxstate, new Date());
                throw new SuperCodeException("无法获取扫码相关信息");
            }
        }


        // 用户角色校验
        if(MemberTypeEnums.SALER.getType().intValue() != jwtUser.getMemberType().intValue()){
            throw new SuperCodeException("您不是销售员用户，无法领奖");
        }


        // 组织校验
        String organizationId = scanCodeInfoMO.getOrganizationId();
        if(jwtUser.getOrganizationId() == null || !jwtUser.getOrganizationId().equals(organizationId)){
            if(log.isInfoEnabled() || jwtUser.getOrganizationId() != null){
                log.error("扫码时获取的组织Id与领奖时用户信息的组织Id不统一:scanCodeInfoMO的信息{},jwtUser信息:{},now:{}",
                        JSONObject.toJSONString(scanCodeInfoMO),JSONObject.toJSONString(jwtUser),JSONObject.toJSONString(jwtUser), new Date());
                throw new SuperCodeException("无法获取扫码相关信息");
            }
            throw new SuperCodeException("组织信息不统一");
        }


        // 活动参数校验1
        if(scanCodeInfoMO.getActivitySetId() == null ){
            throw new SuperCodeException("活动不存在");
        }
        // 活动参数校验2
        if(scanCodeInfoMO.getCodeId() == null ){
            throw new SuperCodeException("对不起，您所扫码的产品未参与活动");
        }
        // 活动参数校验3
        if(scanCodeInfoMO.getProductId() == null ){
            throw new SuperCodeException("对不起，您所扫码的产品未参与活动");
        }
        // 活动参数校验5:这个参数不在乐观锁判断;不校验
        //        if(scanCodeInfoMO.getCreateTime() == null ){
        //            throw new SuperCodeException("扫码时间不存在...");
        //        }
        // 活动主体



        return scanCodeInfoMO;
    }
}
