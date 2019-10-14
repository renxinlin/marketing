package com.jgw.supercodeplatform.prizewheels.application.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.application.transfer.ProductTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsRewardTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsTransfer;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.repository.ProductRepositoryImpl;
import com.jgw.supercodeplatform.prizewheels.infrastructure.repository.WheelsPublishRepositoryImpl;
import com.jgw.supercodeplatform.prizewheels.infrastructure.repository.WheelsRewardRepositoryImpl;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class GetWheelsRewardApplication {

    @Autowired
    private WheelsTransfer wheelsTransfer;
    @Autowired
    private ProductTransfer productTransfer;

    @Autowired
    private WheelsRewardTransfer wheelsRewardTransfer;

    @Autowired
    private WheelsPublishRepositoryImpl wheelsPublishRepositoryImpl;

    @Autowired
    private ProductRepositoryImpl productRepositoryImpl;

    @Autowired
    private WheelsRewardRepositoryImpl wheelsRewardRepositoryImpl;


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
        WheelsDetailsVo wheelsDetailsVo=new WheelsDetailsVo();

        //获取产品
        List<ProductPojo> productPojos = productRepositoryImpl.getPojoByPrizeWheelsId(productBatchId);
        Asserts.check(!CollectionUtils.isEmpty(productPojos),"未获取到产品信息");
        //一个批次id对应一个产品id
        Long id=productPojos.get(0).getId();
        List<ProductUpdateDto> productUpdateDtos=productTransfer.productPojoToProductUpdateDto(productPojos);
        wheelsDetailsVo.setProductUpdateDtos(productUpdateDtos);

        WheelsPojo wheelsPojo=wheelsPublishRepositoryImpl.getWheels(id);
        Asserts.check(wheelsPojo!=null,"未获取到大转盘信息");
        wheelsDetailsVo=wheelsTransfer.tranferWheelsPojoToDomain(wheelsPojo);
        //获取奖励
        List<WheelsRewardPojo> wheelsRewardPojos=wheelsRewardRepositoryImpl.getByPrizeWheelsId(id);
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewardPojos),"未获取到奖励信息");
        List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos=wheelsRewardTransfer.transferRewardToDomain(wheelsRewardPojos);
        wheelsDetailsVo.setWheelsRewardUpdateDtos(wheelsRewardUpdateDtos);
        wheelsDetailsVo.setAutoType(productPojos.get(0).getAutoType());
        return wheelsDetailsVo;
    }
}
