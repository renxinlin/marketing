package com.jgw.supercodeplatform.prizewheels.application.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.application.transfer.PrizeWheelsOrderTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.ProductTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsRewardTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsTransfer;
import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.domain.event.ScanRecordWhenRewardEvent;
import com.jgw.supercodeplatform.prizewheels.domain.model.*;
import com.jgw.supercodeplatform.prizewheels.domain.publisher.ScanRecordWhenRewardPublisher;
import com.jgw.supercodeplatform.prizewheels.domain.repository.*;
import com.jgw.supercodeplatform.prizewheels.domain.service.CodeDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.WheelsRewardDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.ScanRecordWhenRewardSubscriber;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsOrderDto;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.jgw.supercodeplatform.marketing.scheduled.PlatformPaySchedule.SEND_FAIL_WX_ORDER;

@Slf4j
@Service
public class GetWheelsRewardApplication {

    private static final String PREFIXX = "marketing:prizeWheels:h5Reward:";
    @Autowired
    private WheelsTransfer wheelsTransfer;
    @Autowired
    private ProductTransfer productTransfer;

    @Autowired
    private PrizeWheelsOrderTransfer prizeWheelsOrderTransfer;

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

    @Autowired
    private ScanRecordWhenRewardPublisher scanRecordWhenRewardPublisher;


    @Autowired
    private ScanRecordWhenRewardSubscriber scanRecordWhenRewardSubscriber;

    @Autowired
    private PrizeWheelsOrderRepository prizeWheelsOrderRepository;

    @Autowired
    private RedisLockUtil lock;

    /**
     * 用户校验提到用户域: 基于aop
     * 当前不做用户校验:导致结果，用户被禁用后，等到下次登录，禁用才生效
     * @param prizeWheelsRewardDto
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public H5RewardInfo reward(PrizeWheelsRewardDto prizeWheelsRewardDto, H5LoginVO user) {
        String outerCodeId = prizeWheelsRewardDto.getOuterCodeId();
        boolean acquireLock = false;
        try {
            acquireLock = lock.lock(PREFIXX + outerCodeId, 60000, 1, 50);
            if (!acquireLock) {
                log.info("未获取到{}锁", PREFIXX + outerCodeId);
                throw new RuntimeException("该码正在被其他人领取...");
            }
            log.info("大转盘领奖:用户{}，领取活动{}", JSONObject.toJSONString(user), JSONObject.toJSONString(prizeWheelsRewardDto));
            // 数据获取
            Long id = prizeWheelsRewardDto.getId(); // 活动id
            List<Product> products = productRepository.getByPrizeWheelsId(id);
            Wheels wheelsInfo = wheelsPublishRepository.getWheelsInfo(id);
            List<WheelsReward> wheelsRewards = wheelsRewardRepository.getDomainByPrizeWheelsId(id);
            String codeTypeId = prizeWheelsRewardDto.getCodeTypeId();
            ScanRecord mayBeScanedCode = scanRecordRepository.getCodeRecord(outerCodeId, codeTypeId);
            // 业务校验

            // 1-1 码，码制校验
            String sbatchId = codeDomainService.vaildAndGetBatchId(codeTypeId, outerCodeId);
            // 1-2 批次id能否匹配活动
            productDomainService.isPrizeWheelsMatchThisBatchId(products, sbatchId);

            // 1-3 码被扫校验 返回公众号信息[如果存在]
            codeDomainService.noscanedOrTerminated(mayBeScanedCode, wheelsInfo.getWxErcode());

            // 2 活动校验
            wheelsInfo.checkAcitivyStatusWhenHReward();


            // 业务执行
            // 2-1 没扫描产生扫码记录
            newScanRecord(user, codeTypeId, outerCodeId);
            // 概率计算 未成功返回没有领取
            ProbabilityCalculator probabilityCalculator = new ProbabilityCalculator();
            probabilityCalculator.initRewards(wheelsRewards);
            WheelsReward finalReward = probabilityCalculator.calculator();

            // 领取奖励
            H5RewardInfo reward = wheelsRewardDomainService.getReward(finalReward, user, outerCodeId, codeTypeId, id);


            return reward;

        } catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        } finally {
            if (acquireLock) {
                lock.releaseLock(PREFIXX + outerCodeId);
            }
        }
    }

    /**
     * 异步:不受业务异常影响
     * @param user
     * @param codeTypeId
     * @param outerCodeId
     */
    private void newScanRecord(H5LoginVO user, String codeTypeId, String outerCodeId) {
        ScanRecord scanRecord = new ScanRecord(outerCodeId,Integer.parseInt(codeTypeId));
        String userName = !StringUtils.isEmpty(user.getMemberName())?user.getMemberName():user.getMobile();
        scanRecord.initScanerInfo(user.getMemberId(),userName,user.getMobile(),user.getOrganizationId(),user.getOrganizationName());
        scanRecordWhenRewardPublisher.addSubscriber(scanRecordWhenRewardSubscriber);
        scanRecordWhenRewardPublisher.commitAsyncEvent(new ScanRecordWhenRewardEvent(scanRecord));
    }


    /**
     *  TODO 应用层业务下沉
     * @param productBatchId
     * @return
     */
    public WheelsDetailsVo detail(String productBatchId) {
        log.info("H5大转盘详情:产品批次ID{}", productBatchId);
        //
        WheelsDetailsVo wheelsDetailsVo=new WheelsDetailsVo();
        //获取产品
        // TODO 仓库获取的数据经转换后成领域实体而非pojo
        List<ProductPojo> productPojos = productRepository.getPojoByBatchId(productBatchId);
        // TODO 应用层无业务:下沉 Asserts
        Asserts.check(!CollectionUtils.isEmpty(productPojos),"未获取到产品信息");
        // 大转盘活动ID
        Long id=productPojos.get(0).getActivitySetId();
        List<ProductUpdateDto> productUpdateDtos=productTransfer.productPojoToProductUpdateDto(productPojos);
        wheelsDetailsVo.setProductDtos(productUpdateDtos);

        WheelsPojo wheelsPojo=wheelsPublishRepository.getWheelsById(id);
        // TODO 应用层无业务:下沉 Asserts
        Asserts.check(wheelsPojo!=null,"未获取到大转盘信息");
        wheelsDetailsVo=wheelsTransfer.tranferWheelsPojoToDomain(wheelsPojo);
        //获取奖励
        List<WheelsRewardPojo> wheelsRewardPojos=wheelsRewardRepository.getByPrizeWheelsId(id);
        // TODO 应用层无业务::业务下沉 Asserts
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewardPojos),"未获取到奖励信息");
        //剔除list中的未中奖，并将未中奖的数据的中奖率返回
        WheelsRewardPojo notwheelsRewardPojo=new WheelsRewardPojo();
        // TODO 应用层无业务::业务下沉
        for (WheelsRewardPojo wheelsRewardPojo:wheelsRewardPojos){
            if (wheelsRewardPojo.getLoseAward().intValue() == LoseAwardConstant.yes.intValue()){
                notwheelsRewardPojo=wheelsRewardPojo;
                break;
            }
        }
        wheelsRewardPojos.remove(notwheelsRewardPojo);

        List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos=wheelsRewardTransfer.transferRewardToDomain(wheelsRewardPojos);
        // TODO 合并到领域层
        wheelsDetailsVo.setWheelsRewardDtos(wheelsRewardUpdateDtos);
        wheelsDetailsVo.setAutoType(!CollectionUtils.isEmpty(productPojos)?productPojos.get(0).getAutoType():1);
        //赋值未中奖率
        wheelsDetailsVo.setLoseAwardProbability(notwheelsRewardPojo.getProbability());
        return wheelsDetailsVo;
    }

    public void setAdddress(PrizeWheelsOrderDto prizeWheelsOrderDto,H5LoginVO user) {
        PrizeWheelsOrder prizeWheelsOrder = prizeWheelsOrderTransfer.tranferToDomain(prizeWheelsOrderDto);
        prizeWheelsOrder.initRealRewardInfo(user.getMemberId(),user.getMobile(),user.getMemberName(),user.getOrganizationId(),user.getOrganizationName());
        prizeWheelsOrderRepository.addOrder(prizeWheelsOrder);

    }

}
