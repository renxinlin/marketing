package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.MutiProductRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralProductService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSbatchService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralProductMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSbatchMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralProduct;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSbatch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.MutiProductPojoTransfer;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SbatchPojoTransfer;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.ProductPojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class MutiProductRepositoryImpl implements MutiProductRepository {


    @Autowired
    private IntegralProductMapper mapper;
    @Autowired private IntegralProductService batchDao;

    @Autowired private MutiProductPojoTransfer productPojoTransfer;

    @Autowired private CommonUtil commonUtil;

    @Override
    public void deleteOldSetting() {
        LambdaQueryWrapper<IntegralProduct> deleteByMarketingCodes = new LambdaQueryWrapper<>();
        deleteByMarketingCodes.in(IntegralProduct::getOrganizationId,commonUtil.getOrganizationId());
        mapper.delete(deleteByMarketingCodes);
    }

    @Override
    public void saveNewSetting(List<IntegralProductDomain> productDomains) {
        List<IntegralProduct> productPojos = productPojoTransfer.transfer(productDomains);
        batchDao.saveBatch(productPojos);
    }



}
