package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.ProductSendIntegralDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.SendIntegralPersonDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.factory.SendIntegralPersonFactory;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.ProductSendIntegralRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SendIntegralPersonDomainService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.ProductSendIntegralDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Component
@Slf4j
public class ProductSendIntegralApplication {

    // 领域服务
    @Autowired
    private SendIntegralPersonDomainService sendIntegralPersonDomainService;

    @Autowired
    private ProductSendIntegralRepository sendIntegralRepository;
    // 基础设施
    @Autowired
    private CommonUtil commonUtil;



    /**
     * <p>这是一个经典的多上下文交互;但会员已经被腐蚀</p>
     * 产品积分派送
     * @param sendIntegralDto
     */
    @Transactional
    public void productSendIntegral(ProductSendIntegralDto sendIntegralDto) {
        // 数据转换
        String remark = sendIntegralDto.getRemark();
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        SendIntegralPersonDomain person = SendIntegralPersonFactory.build(
                organizationId,organizationName
                ,sendIntegralDto.getMemberMobile()
                ,sendIntegralDto.getIntegralNum());

        // 从会员上下文获取会员转换成派送人
        SendIntegralPersonDomain existsPerson = sendIntegralPersonDomainService.findSendIntegralPerson(person);
        // 执行派送人领积分相关业务 产生积分记录
        person.cpoyInfo(existsPerson);
        ProductSendIntegralDomain productSendIntegralDomain = person.newRecord(
                commonUtil.getUserLoginCache().getAccountId(), commonUtil.getUserLoginCache().getUserName(), remark
        );
        // 新增派送人積分 保存记录
        sendIntegralPersonDomainService.addIntegral(person);
        sendIntegralRepository.saveRecord(productSendIntegralDomain);

    }

    public AbstractPageService.PageResults<List<ProductSendIntegral>> sendRecordList(DaoSearch daoSearch) {
        return  sendIntegralRepository.sendRecordList(daoSearch);

    }
}
