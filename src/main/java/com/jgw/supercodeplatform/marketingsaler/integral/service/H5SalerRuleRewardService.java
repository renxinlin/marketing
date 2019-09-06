package com.jgw.supercodeplatform.marketingsaler.integral.service;

import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleExchangeMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleRewardMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleRewardNum;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.User;
import com.jgw.supercodeplatform.marketingsaler.integral.transfer.H5SalerRuleRewardTransfer;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class H5SalerRuleRewardService  extends SalerCommonService<SalerRuleRewardMapper, SalerRuleReward> {
    @Autowired private SalerRuleRewardNumService salerRuleRewardNumService;
    @Autowired private UserService userService;
    @Autowired private RedisLockUtil lockUtil;
    /**
     * h5领积分:
     * @param reward 只包含产品信息
     * @param user
     */
    @Transactional
    public void getIntegral(String outCodeId,SalerRuleReward reward, H5LoginVO user) throws Exception {
        // 同步扫码
        try {
            boolean lock = lockUtil.lock(UserConstants.SALER_INTEGRAL_REWARD_PREFIX + outCodeId);
            if(!lock){
                throw new RuntimeException("哎呀,被别人抢啦!请稍后重试...");
            }
            // 有没有被扫
            boolean exists = salerRuleRewardNumService.exists(outCodeId);
            if(exists){
                throw new RuntimeException("该码已经被领取");
            }
            Asserts.check(user.getMemberId()!=null,"用户id不存在");
            Asserts.check(!StringUtils.isEmpty(user.getOrganizationId()),"组织信息不存在");
            // 扫码信息保存
            salerRuleRewardNumService.save(new SalerRuleRewardNum(null, user.getMemberId(), user.getOrganizationId(), outCodeId));
            // 领取完成
            User userPojo = userService.exists(user);
            Asserts.check(userPojo != null,"系统不存在该用户信息");

            SalerRuleReward rewardPojo = baseMapper.selectOne(query().eq("ProductId", reward.getProductId()).eq("OrganizationId", user.getOrganizationId()).getWrapper());
            Asserts.check(rewardPojo != null,"系统不存在积分奖励信息");

            userService.addIntegral(H5SalerRuleRewardTransfer.computeIntegral(rewardPojo),userPojo);
            // 添加扫码记录
            // 释放同步
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            lockUtil.releaseLock(UserConstants.SALER_INTEGRAL_REWARD_PREFIX + outCodeId);
        }
    }
}
