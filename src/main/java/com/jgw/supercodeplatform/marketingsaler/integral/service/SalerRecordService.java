package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.integral.transfer.SalerRecordTransfer;
import org.springframework.stereotype.Service;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;


import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRecord;
import    com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRecordMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author renxinlin
 * @since 2019-09-02
 */
@Service
public class SalerRecordService extends SalerCommonService<SalerRecordMapper, SalerRecord> {


    public AbstractPageService.PageResults<List<SalerRecord>>  selectPage(DaoSearch daoSearch) {
        IPage<SalerRecord> salerRecordIPage = baseMapper.selectPage(SalerRecordTransfer.getPage(daoSearch)
                , SalerRecordTransfer.getPageParam(daoSearch, commonUtil.getOrganizationId()));
        return SalerRecordTransfer.toPageResult(salerRecordIPage);
    }
}
