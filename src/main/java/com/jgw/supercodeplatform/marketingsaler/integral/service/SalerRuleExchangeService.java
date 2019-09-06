package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.ExchangeUpDownStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.transfer.SalerRuleExchangeTransfer;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;


import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import    com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleExchangeMapper;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author renxinlin
 * @since 2019-09-02
 */
@Service
public class SalerRuleExchangeService extends SalerCommonService<SalerRuleExchangeMapper, SalerRuleExchange> {

    /**
     * 分页查询
     * @param search
     * @return
     */
    public AbstractPageService.PageResults<List<SalerRuleExchange>> searchPage(DaoSearch search) {
        IPage<SalerRuleExchange> salerRuleExchangeIPage = baseMapper.selectPage(SalerRuleExchangeTransfer.getPage(search), SalerRuleExchangeTransfer.getPageParam(search,commonUtil.getOrganizationId()));
        return SalerRuleExchangeTransfer.toPageResults(salerRuleExchangeIPage);
    }

    public int deleteById(Long id) {
        int delete = baseMapper.delete(query().eq("OrganizationId", commonUtil.getOrganizationId()).eq("Id", id).getWrapper());
        Asserts.check(delete == 1,"删除销售员兑换失败");
        return delete;
    }

    public SalerRuleExchange getByIdWithOrg(Long id) {
        SalerRuleExchange salerRuleExchange = baseMapper.selectById(id);
        Asserts.check(salerRuleExchange!=null,"数据不存在");
        Asserts.check(commonUtil.getOrganizationId().equals(salerRuleExchange.getOrganizationId()),"数据越权");
        return salerRuleExchange;
    }

    public void updateStatus(Long id, Byte status) {
        Asserts.check(status.byteValue() == ExchangeUpDownStatus.up
                        || status.byteValue() == ExchangeUpDownStatus.noAutoDown
                ,"上下架状态非法");
        int updateNum = baseMapper.updateById(SalerRuleExchange.toUpdateStatus(id, status));
        Asserts.check(updateNum == 1,"更新上下架失败");
    }

    public void addExchange(SalerRuleExchangeDto salerRuleExchangeDto) {
        int insert = baseMapper.insert(salerRuleExchangeDto.toPojo(commonUtil.getOrganizationId(), commonUtil.getOrganizationName()));
        Asserts.check(insert == 1,"新增兑换失败");

    }

    public void updateExchange(SalerRuleExchangeDto salerRuleExchangeDto) {
        Asserts.check(salerRuleExchangeDto.getId() != null,"更新数据不存在");
        Asserts.check(baseMapper.selectById(salerRuleExchangeDto.getId())!= null,"数据不存在");
        int updateNum = baseMapper.updateById(salerRuleExchangeDto.toPojo(commonUtil.getOrganizationId(), commonUtil.getOrganizationName()));
        Asserts.check(updateNum == 1,"更新上下架失败");

    }
}
