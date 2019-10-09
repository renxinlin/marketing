package com.jgw.supercodeplatform.prizewheels.application.service;


import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.pojo.cache.OrganizationCache;
import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private WheelsPublishRepository wheelsPublishRepository;

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
        Publisher publisher = new Publisher();
        publisher.initUserInfoWhenFirstPublish(accountId,userName);

        Wheels wheels = new Wheels(organizationId,organization);
        wheels.addPublisher(publisher);

        // 持久化业务
        wheelsPublishRepository.publish(wheels);

    }

}
