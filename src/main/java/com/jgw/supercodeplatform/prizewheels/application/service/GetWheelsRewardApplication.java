package com.jgw.supercodeplatform.prizewheels.application.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GetWheelsRewardApplication {

    @Transactional(rollbackFor = Exception.class)
    public String reward(Long id, H5LoginVO user) {
        log.info("大转盘领奖:用户{}，领取活动{}", JSONObject.toJSONString(user), id);
        // 校验用户状态 码，码制校验
        // 码被扫校验   有公众号返回公众号否则返回扫过提示

        // 没扫描产生扫码记录

        // 概率计算 未成功返回没有领取


        // 领取成功 cdk - 1 领取记录

        // 领取记录
        return null;
    }

    public WheelsDetailsVo detail(String productBatchId) {
        log.info("H5大转盘详情:产品批次ID{}", productBatchId);
        // TODO 实现
        return null;
    }
}
