package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.ProductSendIntegralDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.ProductSendIntegralRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.ProductSendIntegralMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.MutiIntegralRecordTransfer;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.ProductSendIntegralTransfer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductSendIntegralRepositoryImpl implements ProductSendIntegralRepository {
    @Autowired
    private ProductSendIntegralMapper mapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommonUtil commonUtil;



    @Autowired
    private ProductSendIntegralTransfer sendIntegralTransfer;

    @Override
    public void saveRecord(ProductSendIntegralDomain productSendIntegralDomain) {
        ProductSendIntegral productSendIntegralPojo = modelMapper.map(productSendIntegralDomain,ProductSendIntegral.class);
        mapper.insert(productSendIntegralPojo);
    }

    @Override
    public AbstractPageService.PageResults<List<ProductSendIntegral>> sendRecordList(DaoSearch daoSearch) {
        IPage<ProductSendIntegral> productSendIntegralIPageroductSendIntegral = mapper.selectPage(sendIntegralTransfer.getPage(daoSearch)
                , sendIntegralTransfer.getPageParam(daoSearch
                        , commonUtil.getOrganizationId()
                )
        );
        return sendIntegralTransfer.toPageResult(productSendIntegralIPageroductSendIntegral);
    }
}
