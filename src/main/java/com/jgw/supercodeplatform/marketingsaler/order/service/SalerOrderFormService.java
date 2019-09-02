package com.jgw.supercodeplatform.marketingsaler.order.service;


import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.dynamic.mapper.DynamicMapper;
import com.jgw.supercodeplatform.marketingsaler.order.mapper.SalerOrderFormMapper;
import com.jgw.supercodeplatform.marketingsaler.order.pojo.SalerOrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品积分规则表 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Service
public class SalerOrderFormService extends SalerCommonService<SalerOrderFormMapper, SalerOrderForm> {
    @Autowired
    private DynamicMapper dynamicMapper;
    public int createTable(){
       return dynamicMapper.createTable("18265r");
    }

}
