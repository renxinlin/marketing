package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.SalerRecordTransfer;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.DaoSearchWithOrganizationId;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.CommonTransfer.defaultSize;

public class RecordTransfer {
    public static IPage<WheelsRecordPojo> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<WheelsRecordPojo> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;
    }

    public static AbstractPageService.PageResults<List<WheelsRecordPojo>> toPageResult(IPage<WheelsRecordPojo> wheelsRecordPojoIPage) {
        List<WheelsRecordPojo> records = wheelsRecordPojoIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)wheelsRecordPojoIPage.getSize()
                    ,(int)wheelsRecordPojoIPage.getCurrent()
                    ,(int)wheelsRecordPojoIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<WheelsRecordPojo>> results =
                new AbstractPageService.PageResults(records, page);
        return results;
    }

    public static Wrapper<WheelsRecordPojo> getPageParam(DaoSearch daoSearch, String organizationId) {
        QueryWrapper<WheelsRecordPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OrganizationId",organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query ->query.like("mobile",daoSearch.getSearch())
                    .or().like("userName",daoSearch.getSearch())
                    .or().like("RewardName",daoSearch.getSearch())
                    .or().like("Address",daoSearch.getSearch())
                    .or().apply("CreateTime  LIKE binary CONCAT('%',#{0},'%') ",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc("CreateTime");
        return queryWrapper;
    }
}
