package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.OuterCodeInfoService;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.SalerRuleRewardMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleRewardNum;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.H5SalerRuleRewardTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRecordTranser;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
@Slf4j
@Service
public class H5SalerRuleRewardService  extends SalerCommonService<SalerRuleRewardMapper, SalerRuleReward> {
    @Autowired private UserService userService;
    @Autowired private RedisLockUtil lockUtil;
    @Autowired private OuterCodeInfoService outerCodeInfoService;
    @Autowired private SalerRuleRewardNumService salerRuleRewardNumService;
    @Autowired SalerRecordService salerRecordService;
    /**
     * h5领积分:
     * @param reward 只包含产品信息
     * @param user
     */
    @Transactional
    public void getIntegral(String outCodeId,SalerRuleReward reward, H5LoginVO user) throws Exception {
        log.info("扫码入参 outCodeId{}",outCodeId);
        log.info("扫码入参 SalerRuleReward{}",reward);
        log.info("扫码入参 H5LoginVO{}", JSONObject.toJSONString(user));
        // 同步扫码
        try {
            boolean lock = lockUtil.lock(UserConstants.SALER_INTEGRAL_REWARD_PREFIX + outCodeId);
            if(!lock){
                throw new RuntimeException("哎呀,被别人抢啦!请稍后重试...");
            }
            // 码层级获取:销售员只有单码有积分，单码关联的盒码、箱码等其他嵌套码没有积分
            try {
                RestResult<Long> currentLevel = outerCodeInfoService.getCurrentLevel(new OutCodeInfoDto(outCodeId, UserConstants.MARKETING_CODE_TYPE));
                Asserts.check(currentLevel!=null && currentLevel.getResults().intValue() == UserConstants.SINGLE_CODE.intValue(),"非单码");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("非单码或获取码信息失败");
            }
            // 有没有被扫
            boolean exists = salerRuleRewardNumService.exists(outCodeId);
            if(exists){
                throw new RuntimeException("该码已经被领取");
            }
            //
            // 用户信息校验
            Asserts.check(user.getMemberId()!=null,"用户id不存在");
            Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()),"组织信息不存在");
            User userPojo = userService.exists(user);
            Asserts.check(userPojo != null,"系统不存在该用户信息");


            // 积分规则信息校验
            SalerRuleReward rewardPojo = baseMapper.selectOne(query().eq("ProductId", reward.getProductId()).eq("OrganizationId", user.getOrganizationId()).getWrapper());
            Asserts.check(rewardPojo != null,"系统不存在积分奖励信息");


            // 扫码信息保存
            salerRuleRewardNumService.save(new SalerRuleRewardNum(null, user.getMemberId(), user.getOrganizationId(), outCodeId));

            // 积分记录
            SalerRecord salerRecord = SalerRecordTranser.getSalerRecord(outCodeId, reward, user, userPojo, rewardPojo);
            salerRecordService.save(salerRecord);
            // 导购积分添加
            userService.addIntegral(H5SalerRuleRewardTransfer.computeIntegral(rewardPojo),userPojo);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            lockUtil.releaseLock(UserConstants.SALER_INTEGRAL_REWARD_PREFIX + outCodeId);
        }
    }


}
