package com.jgw.supercodeplatform.burypoint.prizewheels.service.get;

import com.jgw.supercodeplatform.burypoint.prizewheels.constants.RedisKeyConstants;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangshiping
 * @date 2019/10/22 9:05
 */
@Service
public class BuryPointDataGetService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * String pvAll
     *
     * @return
     */
    public String getPvAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.PV_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HashMap pvDay
     */
    public Map<Object, Object> getPvDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.PV_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * String pvAll
     *
     * @return
     */
    public String getUvAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.UV_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * HashMap uvDay
     */
    public Map<Object, Object> getUvDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.UV_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * b端配置外链总数
     *
     * @return
     */
    public String getOuterChainConfigAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.OUTER_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端点击外链总数
     *
     * @return
     */
    public String getOuterChainClickAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.OUTER_CLICK_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端点击外链每天总数
     *
     * @return
     */
    public Map<Object, Object> getOuterChainClickDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.OUTER_CLICK_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 奖励发放总数
     *
     * @return
     */
    public String getRewardOutAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.REWARD_OUT_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 奖励发放每天各奖励i发放情况
     *
     * @return
     */
    public Map<Object, Object> getRewardOutDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.REWARD_OUT_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 奖励发放各奖励id发放情况，总数，不区分每天
     *
     * @return
     */
    public Map<Object, Object> getRewardOutRewardIdDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.REWARD_OUT_RewardId_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端扫码模板总数
     *
     * @return
     */
    public String getTemplateScanAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.TEMPLATE_SCAN_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端扫码模板总数每天
     *
     * @return
     */
    public Map<Object, Object> getTemplateScanDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.TEMPLATE_SCAN_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端扫码模板总数每小时
     *
     * @return
     */
    public Map<Object, Object> getTemplateScanHour() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.TEMPLATE_SCAN_HOUR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 大转盘点击总次数
     *
     * @return
     */
    public String getWheelsClickAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.WHEELS_CLICK_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 大转盘每天点击
     *
     * @return
     */
    public Map<Object, Object> getWheelsClickDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.WHEELS_CLICK_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * b端配置微信
     *
     * @return
     */
    public String getWxConfigAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.WX_CONFIG_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * c端微信关注
     *
     * @return
     */
    public String getWxFollowAll() {
        String result = null;
        try {
            result = redisUtil.get(RedisKeyConstants.WX_FOLLOW_ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * b端微信关注每天
     *
     * @return
     */
    public Map<Object, Object> getWxFollowDay() {
        Map<Object, Object> result = new HashMap<>();
        try {
            result = redisUtil.getAllHash(RedisKeyConstants.WX_FOLLOW_DAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
