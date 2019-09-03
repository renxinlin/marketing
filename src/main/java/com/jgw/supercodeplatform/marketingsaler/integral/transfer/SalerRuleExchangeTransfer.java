package com.jgw.supercodeplatform.marketingsaler.integral.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 积分兑换
 */
public class SalerRuleExchangeTransfer extends CommonTransfer {
    /**
     * 获取分页参数
     *
     * @param search
     * @return
     */
    public static IPage<SalerRuleExchange> getPage(DaoSearch search) {
        IPage page = new Page(search.getCurrent(),search.getPageSize() == null ? defaultSize :search.getPageSize());
        return page;
    }

    /**
     * 获取分页条件参数
     *
     * @param search
     * @return
     */
    public static Wrapper<SalerRuleExchange> getPageParam(DaoSearch daoSearch,String organizationId) {
        QueryWrapper<SalerRuleExchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OrganizationId",organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query ->query.like("ExchangeIntegral",daoSearch.getSearch())
                    .or().like("ExchangeStock",daoSearch.getSearch())
                    .or().like("HaveStock",daoSearch.getSearch())
                    .or().like("CustomerLimitNum",daoSearch.getSearch())
            );
        }
        return queryWrapper;
    }

    /**
     * 转换分页结果
     *
     * @param salerRuleExchangeIPage
     * @return
     */
    public static AbstractPageService.PageResults<List<SalerRuleExchange>> toPageResults(IPage<SalerRuleExchange> salerRuleExchangeIPage) {
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)salerRuleExchangeIPage.getSize()
                    ,(int)salerRuleExchangeIPage.getCurrent()
                    ,(int)salerRuleExchangeIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }
        AbstractPageService.PageResults<List<SalerRuleExchange>> pageResult = new AbstractPageService.PageResults<>(salerRuleExchangeIPage.getRecords(),page);
        return pageResult;
    }
}
