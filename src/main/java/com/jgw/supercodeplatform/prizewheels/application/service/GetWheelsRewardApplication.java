package com.jgw.supercodeplatform.prizewheels.application.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.application.transfer.ProductTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsRewardTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsTransfer;
import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.*;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ScanRecordRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.domain.service.CodeDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.WheelsRewardDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
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
    private WheelsPublishRepository wheelsPublishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WheelsRewardRepository wheelsRewardRepository;

    @Autowired
    private ScanRecordRepository scanRecordRepository;

    @Autowired
    private CodeDomainService codeDomainService;

    @Autowired
    private ProductDomainService productDomainService;

    @Autowired
    private WheelsRewardDomainService wheelsRewardDomainService;
    /**
     * 用户校验提到用户域: 基于aop
     * 当前不做用户校验:导致结果，用户被禁用后，等到下次登录，禁用才生效
     * @param prizeWheelsRewardDto
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String reward(PrizeWheelsRewardDto prizeWheelsRewardDto, H5LoginVO user) {
        log.info("大转盘领奖:用户{}，领取活动{}", JSONObject.toJSONString(user), JSONObject.toJSONString(prizeWheelsRewardDto));
        // 数据获取
        List<Product> products = productRepository.getByPrizeWheelsId(prizeWheelsRewardDto.getId());
        Wheels wheelsInfo = wheelsPublishRepository.getWheelsInfo(prizeWheelsRewardDto.getId());
        List<WheelsReward> wheelsRewards = wheelsRewardRepository.getDomainByPrizeWheelsId(prizeWheelsRewardDto.getId());
        String codeTypeId = prizeWheelsRewardDto.getCodeTypeId();
        String outerCodeId = prizeWheelsRewardDto.getOuterCodeId();
        ScanRecord mayBeScanedCode = scanRecordRepository.getCodeRecord(outerCodeId, codeTypeId);
        // 业务校验
        // 1-1 码，码制校验
        String sbatchId = codeDomainService.vaildAndGetBatchId(codeTypeId, outerCodeId);
        // 1-2 批次id能否匹配活动
        productDomainService.isPrizeWheelsMatchThisBatchId(products,sbatchId);

        // 1-3 码被扫校验 返回公众号信息[如果存在]
        codeDomainService.noscanedOrTerminated(mayBeScanedCode ,wheelsInfo.getWxErcode());

        // 业务执行
        // 2-1 没扫描产生扫码记录
        ScanRecord scanRecord = new ScanRecord(outerCodeId,Integer.parseInt(codeTypeId));
        String userName = !StringUtils.isEmpty(user.getMemberName())?user.getMemberName():user.getMobile();
        scanRecord.initScanerInfo(user.getMemberId(),userName,user.getMobile(),user.getOrganizationId(),user.getOrganizationName());

        // 概率计算 未成功返回没有领取
        ProbabilityCalculator probabilityCalculator = new ProbabilityCalculator();
        probabilityCalculator.initRewards(wheelsRewards);
        WheelsReward finalReward = probabilityCalculator.calculator();
        // 领取成功 cdk - 1 领取记录

        // 领取记录
        wheelsRewardDomainService.getReward(finalReward,user,outerCodeId,codeTypeId);


        // 持久化
        scanRecordRepository.saveScanRecord(scanRecord);


        return null;
    }

    public WheelsDetailsVo detail(String productBatchId) {
        log.info("H5大转盘详情:产品批次ID{}", productBatchId);
        //
        WheelsDetailsVo wheelsDetailsVo=new WheelsDetailsVo();
        //获取产品
        List<ProductPojo> productPojos = productRepository.getPojoByBatchId(productBatchId);
        Asserts.check(!CollectionUtils.isEmpty(productPojos),"未获取到产品信息");
        // 大转盘活动ID
        Long id=productPojos.get(0).getActivitySetId();
        List<ProductUpdateDto> productUpdateDtos=productTransfer.productPojoToProductUpdateDto(productPojos);
        wheelsDetailsVo.setProductUpdateDtos(productUpdateDtos);

        WheelsPojo wheelsPojo=wheelsPublishRepository.getWheelsById(id);
        Asserts.check(wheelsPojo!=null,"未获取到大转盘信息");
        wheelsDetailsVo=wheelsTransfer.tranferWheelsPojoToDomain(wheelsPojo);
        //获取奖励
        List<WheelsRewardPojo> wheelsRewardPojos=wheelsRewardRepository.getByPrizeWheelsId(id);
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewardPojos),"未获取到奖励信息");
        //剔除list中的未中奖，并将未中奖的数据的中奖率返回
        WheelsRewardPojo notwheelsRewardPojo=new WheelsRewardPojo();
        for (WheelsRewardPojo wheelsRewardPojo:wheelsRewardPojos){
            if (wheelsRewardPojo.getLoseAward().intValue() == LoseAwardConstant.yes.intValue()){
                notwheelsRewardPojo=wheelsRewardPojo;
                break;
            }
        }
        wheelsRewardPojos.remove(notwheelsRewardPojo);
        List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos=wheelsRewardTransfer.transferRewardToDomain(wheelsRewardPojos);
        wheelsDetailsVo.setWheelsRewardUpdateDtos(wheelsRewardUpdateDtos);
        wheelsDetailsVo.setAutoType(productPojos.get(0).getAutoType());
        //赋值未中奖率
        wheelsDetailsVo.setLoseAwardProbability(notwheelsRewardPojo.getProbability());
        return wheelsDetailsVo;
    }
}
