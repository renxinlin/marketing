package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.MutiIntegralRecordMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.MutiIntegralRecordTransfer;
import com.jgw.supercodeplatform.prizewheels.application.transfer.OrderTransfer;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MutiIntegralRecordRepository {

    @Autowired
    private MutiIntegralRecordMapper mutiIntegralRecordMapper;



    @Autowired
    private CommonUtil commonUtil;


    @Autowired
    private MutiIntegralRecordTransfer recordTransfer;



    public AbstractPageService.PageResults<List<IntegralRecord>> getMemberMutiIntegralRecordPage(DaoSearch daoSearch) {
        IPage<IntegralRecord> integralRecordIPage = mutiIntegralRecordMapper.selectPage(recordTransfer.getPage(daoSearch)
                , recordTransfer.getPageParam(daoSearch
                        , commonUtil.getOrganizationId()
                )
        );
        return recordTransfer.toPageResult(integralRecordIPage);
    }
}
