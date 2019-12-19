package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.CommonTransfer.defaultSize;
@Component
public class ProductSendIntegralTransfer {
    public AbstractPageService.PageResults<List<ProductSendIntegral>> toPageResult(IPage<ProductSendIntegral> productSendIntegralIPage) {
        List<ProductSendIntegral> records = productSendIntegralIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)productSendIntegralIPage.getSize()
                    ,(int)productSendIntegralIPage.getCurrent()
                    ,(int)productSendIntegralIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<ProductSendIntegral>> results =
                new AbstractPageService.PageResults(records, page);
        return results;}

    public LambdaQueryWrapper<ProductSendIntegral> getPageParam(DaoSearch daoSearch, String organizationId) {
        LambdaQueryWrapper<ProductSendIntegral> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductSendIntegral::getOrganizationId,organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(
                    // TODO 其他分頁参数查询
                    query ->query.like(ProductSendIntegral::getMemberName,daoSearch.getSearch())
                            .or().like(ProductSendIntegral::getMemberMobile,daoSearch.getSearch())
                            .or().like(ProductSendIntegral::getCustomerName,daoSearch.getSearch())
                            .or().like(ProductSendIntegral::getIntegralNum,daoSearch.getSearch())
                            .or().like(ProductSendIntegral::getOperaterName,daoSearch.getSearch())
                            .or().like(ProductSendIntegral::getRemark,daoSearch.getSearch())
                             .or().apply("operationTime  LIKE binary CONCAT('%',#{0},'%') ",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc(ProductSendIntegral::getOperationTime);
        return queryWrapper;
    }

    public IPage<ProductSendIntegral> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<ProductSendIntegral> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;

    }
}
