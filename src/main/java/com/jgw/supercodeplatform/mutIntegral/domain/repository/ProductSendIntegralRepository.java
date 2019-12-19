package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.ProductSendIntegralDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSendIntegralRepository {
    void saveRecord(ProductSendIntegralDomain productSendIntegralDomain);

    AbstractPageService.PageResults<List<ProductSendIntegral>> sendRecordList(DaoSearch daoSearch);
}
