package com.jgw.supercodeplatform.prizewheels.application.service;


import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.application.transfer.ProductTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsRewardTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.WheelsTransfer;
import com.jgw.supercodeplatform.prizewheels.domain.model.Product;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ActivitySetRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ProductRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProcessActivityDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.WheelsRewardDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private WheelsRewardDomainService wheelsRewardDomainService;

    @Autowired
    private ProductDomainService productDomainService;

    @Autowired
    private ActivitySetRepository activitySetRepository;

    @Autowired
    private ProcessActivityDomainService processActivityDomainService;
    /**
     * 新增大转盘活动
     * @param wheelsDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void publish(WheelsDto wheelsDto){
        // 数据获取
        String organizationId = commonUtil.getOrganizationId();
        String organization = commonUtil.getOrganizationName();
        String accountId = commonUtil.getUserLoginCache().getAccountId();
        String userName = commonUtil.getUserLoginCache().getUserName();


        // 创建发布大转盘相关领域并完成业务

        // 设置中奖产品 剔除存在的产品批次活动

        // 设置奖励信息
        // 异步事件 获取导入的cdk_key 发布cdk关联产品事件 并给cdk关联产品事件绑定消费者

        Publisher publisher = new Publisher();
        publisher.initUserInfoWhenFirstPublish(accountId,userName);

        List<ProductDto> productDtos = wheelsDto.getProductDtos();
        List<Product> products = productTransfer.dtoToProduct(productDtos);


        Wheels wheels = new Wheels(organizationId,organization);
        wheels.addPublisher(publisher);

        // 持久化业务
        productRepository.saveButDeleteOld(products);

        wheelsPublishRepository.publish(wheels);

    }// 单次操作:100万CDK 的更新 3000/s => 300s 管理系统上线活动允许

    @Transactional
    public void deletePrizeWheelsById(Long id) {
        wheelsPublishRepository.deletePrizeWheelsById(id);
        productRepository.deleteByPrizeWheelsId(id);
        wheelsRewardRepository.deleteByPrizeWheelsId(id);

        // TODO cdk后期删除
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(WheelsUpdateDto wheelsUpdateDto) {
        // 数据转换
        Long prizeWheelsid = wheelsUpdateDto.getId();
        byte autoType = wheelsUpdateDto.getAutoType();
        Wheels wheels =  wheelsTransfer.tranferToDomain(wheelsUpdateDto);

        List<ProductUpdateDto> productUpdateDtos = wheelsUpdateDto.getProductUpdateDtos();
        List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos = wheelsUpdateDto.getWheelsRewardUpdateDtos();
        List<Product> products = productTransfer.transferUpdateDtoToDomain(productUpdateDtos, prizeWheelsid, autoType);
        List<WheelsReward> wheelsRewards = wheelsRewardTransfer.transferUpdateDtoToDomain(wheelsRewardUpdateDtos, prizeWheelsid);
        // 1 业务处理
        // 大转盘
        Publisher publisher = new Publisher();
        publisher.initUserInfo(
                commonUtil.getUserLoginCache().getAccountId()
                ,commonUtil.getUserLoginCache().getUserName());
        wheels.initOrgInfo(commonUtil.getOrganizationId(),commonUtil.getOrganizationName());
        wheels.addPublisher(publisher);
        wheels.checkWhenUpdate();



        // 2 奖励
        wheelsRewardDomainService.checkWhenUpdate(wheelsRewards);
        // 2-1 cdk 领域事件 奖品与cdk绑定
        wheelsRewardDomainService.cdkEventCommitedWhenNecessary(wheelsRewards);

        // 3 码管理业务
        // 3-1 获取生码批次
        products = productDomainService.initSbatchIds(products);

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

        wheelsRewardRepository.deleteByPrizeWheelsId(prizeWheelsid);
        wheelsRewardRepository.batchSave(wheelsRewards);

        productRepository.saveButDeleteOld(products);

        activitySetRepository.updateWhernWheelsChanged(activitySet);

    }

    public WheelsUpdateDto detail(Long id) {
        return null;
    }

    public AbstractPageService.PageResults<List<WheelsUpdateDto>> list(DaoSearch daoSearch) {
        return null;
    }
}
