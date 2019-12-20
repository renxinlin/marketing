package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SegmentCodeRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSegmentCodeService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSingleCodeService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSegmentCodeMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSingleCodeMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSingleCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SegmentCodePojoTransfer;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SingleCodePojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public class SegmentCodeRepositoryImpl implements SegmentCodeRepository {



    @Autowired
    private IntegralSegmentCodeMapper mapper;
    @Autowired private IntegralSegmentCodeService batchDaao;

    @Autowired private SegmentCodePojoTransfer segmentCodePojoTransfer;

    @Autowired private CommonUtil commonUtil;

    @Override
    public void deleteOldSetting() {
        LambdaQueryWrapper<IntegralSegmentCode> deleteByMarketingCodes = new LambdaQueryWrapper<>();
        deleteByMarketingCodes.in(IntegralSegmentCode::getOrganizationId,commonUtil.getOrganizationId());
        mapper.delete(deleteByMarketingCodes);
    }

    @Override
    public void saveNewSetting(List<IntegralSegmentCodeDomain> segmentCodeDomains) {
        List<IntegralSegmentCode> segmentCodepojos = segmentCodePojoTransfer.transfer(segmentCodeDomains);
        batchDaao.saveBatch(segmentCodepojos);
    }





}
