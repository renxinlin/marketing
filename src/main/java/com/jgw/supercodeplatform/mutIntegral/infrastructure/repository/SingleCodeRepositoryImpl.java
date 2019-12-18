package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.SingleCodeTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SingleCodeRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralSingleCodeService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralSingleCodeMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSingleCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.SingleCodePojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SingleCodeRepositoryImpl implements SingleCodeRepository {
    @Autowired private IntegralSingleCodeMapper mapper;
    @Autowired private IntegralSingleCodeService batchDaao;

    @Autowired private SingleCodePojoTransfer singleCodePojoTransfer;

    @Autowired private CommonUtil commonUtil;

    @Override
    public void deleteOldSetting() {
        // 简单的数据转换直接写
        LambdaQueryWrapper<IntegralSingleCode> deleteByMarketingCodes = new LambdaQueryWrapper<>();
        deleteByMarketingCodes.in(IntegralSingleCode::getOrganizationId,commonUtil.getOrganizationId());
        mapper.delete(deleteByMarketingCodes);
    }

    @Override
    public void saveNewSetting(List<IntegralSingleCodeDomain> singledomains) {
        List<IntegralSingleCode> singlepojos = singleCodePojoTransfer.transfer(singledomains);
        batchDaao.saveBatch(singlepojos);
    }
}
