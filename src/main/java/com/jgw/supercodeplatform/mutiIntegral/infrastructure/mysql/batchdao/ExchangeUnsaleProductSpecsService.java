package com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.batchdao;


import org.springframework.stereotype.Service;
import com.jgw.supercodeplatform.common.AbstractPageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.transaction.annotation.Transactional;

import com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.pojo.ExchangeUnsaleProductSpecs;
import    com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.dao.ExchangeUnsaleProductSpecsMapper;
import   com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * <p>
 * 少一张规格结构化表，没啥子业务到时候再说 服务实现类
 * </p>
 * @author renxinlin
 * @since 2019-12-13
 */
@Service
public class ExchangeUnsaleProductSpecsService extends ServiceImpl<ExchangeUnsaleProductSpecsMapper, ExchangeUnsaleProductSpecs> {


}
