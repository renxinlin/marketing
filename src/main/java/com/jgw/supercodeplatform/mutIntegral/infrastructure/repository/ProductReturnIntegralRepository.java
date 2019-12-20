package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.ProductReturnIntegralMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.ProductReturnIntegralTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductReturnIntegralRepository {
    @Autowired
    private ProductReturnIntegralMapper mapper;

    @Autowired
    private ProductReturnIntegralTransfer transfer;


    @Autowired
    private CommonUtil commonUtil;

    public AbstractPageService.PageResults<List<ProductReturnIntegral>> returnBackList(DaoSearch daoSearch) {
        IPage<ProductReturnIntegral> productSendIntegralIPageroductSendIntegral = mapper.selectPage(transfer.getPage(daoSearch)
                , transfer.getPageParam(daoSearch
                        , commonUtil.getOrganizationId()
                )
        );
        return transfer.toPageResult(productSendIntegralIPageroductSendIntegral);
    }
}
