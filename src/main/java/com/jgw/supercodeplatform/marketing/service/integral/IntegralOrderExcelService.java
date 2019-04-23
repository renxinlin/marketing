package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单分页查询优化
 */
@Service
public class IntegralOrderExcelService extends AbstractPageService<IntegralOrder> {


    private static Logger logger = LoggerFactory.getLogger(IntegralExchangeService.class);

    @Autowired
    private IntegralOrderMapperExt mapper;

    @Override
    protected List<IntegralOrderPageParam> searchResult(IntegralOrder searchParams) throws Exception {
        // 返回格式修改
        return mapper.listWithExcel(searchParams);
    }

    @Override
    protected int count(IntegralOrder searchParams) throws Exception {
        return mapper.count(searchParams);
    }
}
