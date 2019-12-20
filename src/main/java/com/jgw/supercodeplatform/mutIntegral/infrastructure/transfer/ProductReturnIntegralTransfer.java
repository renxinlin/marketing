package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.CommonTransfer.defaultSize;

@Component
@Slf4j
public class ProductReturnIntegralTransfer {
    public IPage<ProductReturnIntegral> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<ProductReturnIntegral> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;

    }

    public Wrapper<ProductReturnIntegral> getPageParam(DaoSearch daoSearch, String organizationId) {
        LambdaQueryWrapper<ProductReturnIntegral> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductReturnIntegral::getOrganizationId,organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query->
                            query.like(ProductReturnIntegral::getMemberMobile,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getCustomerName,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getProductName,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getReturnIntegralNum,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getOutCodeId,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getReason,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getTotalIntegral,daoSearch.getSearch())
                            .or().like(ProductReturnIntegral::getHaveIntegral,daoSearch.getSearch())
                            .or().apply("IntegralTime  LIKE binary CONCAT('%',#{0},'%') ",daoSearch.getSearch())
                            .or().apply("ReturnTime  LIKE binary CONCAT('%',#{0},'%') ",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc(ProductReturnIntegral::getReturnTime);
        return queryWrapper;  }

    public AbstractPageService.PageResults<List<ProductReturnIntegral>> toPageResult(IPage<ProductReturnIntegral> productReturnIntegral) {
        List<ProductReturnIntegral> records = productReturnIntegral.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)productReturnIntegral.getSize()
                    ,(int)productReturnIntegral.getCurrent()
                    ,(int)productReturnIntegral.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }
        AbstractPageService.PageResults<List<ProductReturnIntegral>> results =
                new AbstractPageService.PageResults(records, page);
        return results;
    }
}
