package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.CommonTransfer.defaultSize;

@Component
@Slf4j
public class MutiIntegralRecordTransfer  extends MutiIntegralCommonTransfer{
    public Wrapper<IntegralRecord> getPageParam(DaoSearch daoSearch, String organizationId) {
        LambdaQueryWrapper<IntegralRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IntegralRecord::getOrganizationId,organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(
                    // TODO 其他分頁参数查询
                    query ->query.like(IntegralRecord::getMemberName,daoSearch.getSearch())
                            .or().like(IntegralRecord::getMobile,daoSearch.getSearch())
                            .or().like(IntegralRecord::getMobile,daoSearch.getSearch())
                            .or().like(IntegralRecord::getIntegralNum,daoSearch.getSearch())
                            .or().like(IntegralRecord::getIntegralMoney,daoSearch.getSearch())
                            .or().like(IntegralRecord::getIntegralReason,daoSearch.getSearch())
                            .or().like(IntegralRecord::getOuterCodeId,daoSearch.getSearch())
                            .or().apply("CreateTime  LIKE binary CONCAT('%',#{0},'%') ",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc(IntegralRecord::getCreateDate);
        return queryWrapper;
    }

    public IPage<IntegralRecord> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<IntegralRecord> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;
    }

    public AbstractPageService.PageResults<List<IntegralRecord>> toPageResult(IPage<IntegralRecord> integralRecordIPage) {
        List<IntegralRecord> records = integralRecordIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)integralRecordIPage.getSize()
                    ,(int)integralRecordIPage.getCurrent()
                    ,(int)integralRecordIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<IntegralRecord>> results =
                new AbstractPageService.PageResults(records, page);
        return results;
    }
}
