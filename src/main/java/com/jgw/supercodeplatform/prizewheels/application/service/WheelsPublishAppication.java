package com.jgw.supercodeplatform.prizewheels.application.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRecordTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.prizewheels.application.transfer.ProductTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.RecordTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsRewardTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsTransfer;
import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.repository.*;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProcessActivityDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.WheelsRewardDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.*;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.*;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 发布服务涉及多个聚合的处理
 *
 * 此服务采用实现类实现
 *
 * 创建方式：通过工厂解耦 [no]
 * 创建方式: 依赖注入 [yes]
 */
@Service
public class WheelsPublishAppication {

    @Autowired
    private CommonUtil commonUtil;

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
    private RecordRepository recordRepository;

    @Autowired
    private WheelsRewardDomainService wheelsRewardDomainService;

    @Autowired
    private ProductDomainService productDomainService;

    @Autowired
    private ProcessActivityDomainService processActivityDomainService;

    @Autowired
    private ActivitySetRepository activitySetRepository;


    @Autowired
    private WheelsRewardCdkRepository wheelsRewardCdkRepository;

    /**
     * 新增大转盘活动
     * @param wheelsDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void publish(WheelsDto wheelsDto){
        // 数据转换
        byte autoType = wheelsDto.getAutoType();
        Wheels wheels =  wheelsTransfer.tranferToDomainWhenAdd(wheelsDto);

        List<ProductDto> productDtos = wheelsDto.getProductDtos();
        List<WheelsRewardDto> wheelsRewardDtos = wheelsDto.getWheelsRewardDtos();
        List<Product> products = productTransfer.transferDtoToDomain(productDtos, autoType);

        double loseAwardProbability = wheelsDto.getLoseAwardProbability();
        List<WheelsReward> wheelsRewards = wheelsRewardTransfer.transferDtoToDomain(wheelsRewardDtos,loseAwardProbability);
        // 1 业务处理
        // 大转盘
        Publisher publisher = new Publisher();
        publisher.initUserInfoWhenFirstPublish(
                commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        wheels.initOrgInfo(commonUtil.getOrganizationId(),commonUtil.getOrganizationName());
        wheels.addPublisher(publisher);
        wheels.checkWhenAdd();
        // 持久化 返回主键
        wheelsPublishRepository.publish(wheels);
        Long prizeWheelsid = wheels.getId();

        // 2 奖励
        wheelsRewardDomainService.initPrizeWheelsid(wheelsRewards,prizeWheelsid);
        wheelsRewardDomainService.checkWhenAdd(wheelsRewards);
        // 持久化返回主键
        wheelsRewardRepository.batchSave(wheelsRewards);

        // 2-1 cdk 领域事件 奖品与cdk绑定
        wheelsRewardDomainService.cdkEventCommitedWhenNecessary(wheelsRewards);

        // 3 码管理业务 绑定活动Id
        products = productDomainService.initPrizeWheelsId(products,prizeWheelsid);
        // 3-1 获取生码批次
        products = productDomainService.initSbatchIds(products);
        productDomainService.checkSbatchId(products);
        // 3-2 将此活动涉及产品与码管理的信息解绑
        productDomainService.removeOldProduct(products);
        // 3-3 发送新的产品绑定请求
        productDomainService.executeBizWhichCodeManagerWant(products);
        // 4 修改活动聚合老表
        ActivitySet activitySet = processActivityDomainService.formPrizeWheelsToOldActivity(wheels, (int) autoType);
        // 持久化

        productRepository.saveButDeleteOld(products);

        activitySetRepository.addWhenWheelsAdd(activitySet);

    }


    @Transactional(rollbackFor = Exception.class)
    public void deletePrizeWheelsById(Long id) {
        wheelsPublishRepository.deletePrizeWheelsById(id);
        productRepository.deleteByPrizeWheelsId(id);
        wheelsRewardRepository.deleteByPrizeWheelsId(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void update(WheelsUpdateDto wheelsUpdateDto) {
        // 数据转换
        Long prizeWheelsid = wheelsUpdateDto.getId();
        byte autoType = wheelsUpdateDto.getAutoType();
        Wheels wheels =  wheelsTransfer.tranferToDomain(wheelsUpdateDto);

        List<ProductUpdateDto> productUpdateDtos = wheelsUpdateDto.getProductDtos();
        List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos = wheelsUpdateDto.getWheelsRewardDtos();
        List<Product> products = productTransfer.transferUpdateDtoToDomain(productUpdateDtos, prizeWheelsid, autoType);
        List<WheelsReward> wheelsRewards = wheelsRewardTransfer.transferUpdateDtoToDomain(wheelsRewardUpdateDtos, prizeWheelsid,wheelsUpdateDto.getLoseAwardProbability());
        // 1 业务处理
        // 大转盘
        Publisher publisher = new Publisher();
        publisher.initUserInfo(
                commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        wheels.initOrgInfo(commonUtil.getOrganizationId(),commonUtil.getOrganizationName());
        wheels.addPublisher(publisher);
        wheels.checkWhenUpdate();

        // 2 奖励  返回主键
        List<WheelsReward> oldwheelsRewards = wheelsRewardRepository.getDomainByPrizeWheelsId(prizeWheelsid);

        wheelsRewardDomainService.checkWhenUpdate(wheelsRewards);
        wheelsRewardRepository.deleteByPrizeWheelsId(prizeWheelsid);
        wheelsRewardRepository.batchSave(wheelsRewards);

        // 2-1 cdk 领域事件 奖品与cdk绑定 旧的cdk删除
        wheelsRewardCdkRepository.deleteOldCdk(oldwheelsRewards);
        wheelsRewardDomainService.cdkEventCommitedWhenNecessary(wheelsRewards);
        // 3 码管理业务
        // 3-1 获取生码批次
        products = productDomainService.initSbatchIds(products);
        productDomainService.checkSbatchId(products);

        // 3-2 将此活动之前产品与码管理的信息解绑
        List<Product> oldPrizeWheelsProduct = productRepository.getByPrizeWheelsId(prizeWheelsid);
        productDomainService.removeOldProduct(oldPrizeWheelsProduct);
        // 将准备绑定的产品原来的绑定删除: 里面可能有部分产品之前属于其他活动需要解绑:
        productDomainService.removeOldProduct(products);
        // 3-3 发送新的产品绑定请求
        productDomainService.executeBizWhichCodeManagerWant(products);
        // 4 修改活动聚合老表
        ActivitySet activitySet = processActivityDomainService.formPrizeWheelsToOldActivity(wheels, (int) autoType);
        // 持久化
        wheelsPublishRepository.updatePrizeWheel(wheels);


        productRepository.saveButDeleteOld(products);

        activitySetRepository.updateWhenWheelsChanged(activitySet);

    }


    /**
     * B端 根据组织id和组织名称获取大转盘详情
     * @return
     */
    public WheelsDetailsVo getWheelsDetails(Long id ){
        // 组织数据获取
        // 获取大转盘
        WheelsPojo wheelsPojo=wheelsPublishRepository.getWheels(id);
        Asserts.check(wheelsPojo!=null,"未获取到大转盘信息");
        WheelsDetailsVo wheelsDetailsVo=wheelsTransfer.tranferWheelsPojoToDomain(wheelsPojo);
        //获取产品
        List<ProductPojo> productPojos = productRepository.getPojoByPrizeWheelsId(id);
        // Asserts.check(!CollectionUtils.isEmpty(productPojos),"未获取到产品信息");
        List<ProductUpdateDto> productUpdateDtos=productTransfer.productPojoToProductUpdateDto(productPojos);
        wheelsDetailsVo.setProductDtos(productUpdateDtos);
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
        wheelsDetailsVo.setWheelsRewardDtos(wheelsRewardUpdateDtos);
        wheelsDetailsVo.setAutoType(!CollectionUtils.isEmpty(productPojos)?productPojos.get(0).getAutoType():1);
        wheelsDetailsVo.setLoseAwardProbability(notwheelsRewardPojo.getProbability());
        return wheelsDetailsVo;
    }


    @Transactional(rollbackFor = Exception.class)
    public void upadteStatus(ActivityStatus activityStatus) {
        wheelsPublishRepository.updateStatus(activityStatus.getId(),activityStatus.getStatus());
        processActivityDomainService.updateStatus(activityStatus.getId(),activityStatus.getStatus());
    }

    public AbstractPageService.PageResults<List<WheelsRecordPojo>> records(DaoSearchWithPrizeWheelsIdDto daoSearch) {
        IPage<WheelsRecordPojo> wheelsRecordPojoIPage = recordRepository.selectPage(RecordTransfer.getPage(daoSearch)
                , RecordTransfer.getPageParam(daoSearch, commonUtil.getOrganizationId(),daoSearch.getId()));
        return RecordTransfer.toPageResult(wheelsRecordPojoIPage);

    }

}
