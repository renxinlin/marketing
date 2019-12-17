package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SbatchRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSbatchService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSegmentCodeService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSbatchMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSegmentCodeMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSbatch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SbatchPojoTransfer;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SegmentCodePojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SbatchRepositoryImpl implements SbatchRepository {

    @Autowired
    private IntegralSbatchMapper mapper;
    @Autowired private IntegralSbatchService batchDao;

    @Autowired private SbatchPojoTransfer segmentCodePojoTransfer;

    @Autowired private CommonUtil commonUtil;


    @Override
    public void deleteOldSetting(List<IntegralSbatchDomain> sbatchDomains) {
        LambdaQueryWrapper<IntegralSbatch> deleteByMarketingCodes = new LambdaQueryWrapper<>();
        deleteByMarketingCodes.in(IntegralSbatch::getOrganizationId,commonUtil.getOrganizationId());
        mapper.delete(deleteByMarketingCodes);
    }

    @Override
    public void saveNewSetting(List<IntegralSbatchDomain> sbatchDomains) {
        List<IntegralSbatch> sbatchPojos = segmentCodePojoTransfer.transfer(sbatchDomains);
        batchDao.saveBatch(sbatchPojos);
    }











}
