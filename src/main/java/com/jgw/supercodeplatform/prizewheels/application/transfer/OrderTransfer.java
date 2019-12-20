package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer.CommonTransfer.defaultSize;
/**
 * @author fangshiping
 * @date 2019/10/25 10:05
 */
public class OrderTransfer {
    public static IPage<PrizeWheelsOrderPojo> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<PrizeWheelsOrderPojo> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;
    }

    public static Wrapper<PrizeWheelsOrderPojo> getPageParam(DaoSearch daoSearch, String organizationId, long id) {
        QueryWrapper<PrizeWheelsOrderPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OrganizationId",organizationId);
        queryWrapper.eq("PrizeRewardId",id);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query ->query.like("mobile",daoSearch.getSearch())
                    .or().like("receiverName",daoSearch.getSearch())
                    .or().like("receiverMobile",daoSearch.getSearch())
                    .or().like("Address",daoSearch.getSearch())
                    .or().apply("CreateTime  LIKE binary CONCAT('%',{0},'%') ",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc("CreateTime");
        return queryWrapper;
    }

    public static AbstractPageService.PageResults<List<PrizeWheelsOrderPojo>> toPageResult(IPage<PrizeWheelsOrderPojo> prizeWheelsOrderPojoIPage) {
        List<PrizeWheelsOrderPojo> records = prizeWheelsOrderPojoIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)prizeWheelsOrderPojoIPage.getSize()
                    ,(int)prizeWheelsOrderPojoIPage.getCurrent()
                    ,(int)prizeWheelsOrderPojoIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<PrizeWheelsOrderPojo>> results =
                new AbstractPageService.PageResults(records, page);
        return results;
    }
}
