package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.WheelsPojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 六边形架构模式:实现解耦放置于基础设施
 */
@Repository
public class WheelsPublishRepositoryImpl implements WheelsPublishRepository {

    @Autowired
    private WheelsMapper wheelsMapper;

    @Autowired
    private WheelsPojoTransfer wheelsPojoTransfer;

    @Autowired
    private CommonUtil commonUtil;


    @Override
    public void publish(Wheels wheels) {
        WheelsPojo wheelsPojo =  wheelsPojoTransfer.tranferDomainToPojo(wheels);
        wheelsMapper.insert(wheelsPojo);
    }

    @Override
    public int deletePrizeWheelsById(Long id) {
        return wheelsMapper.deleteById(id);
    }

    @Override
    public int updatePrizeWheel(Wheels wheels) {
        WheelsPojo wheelsPojo =  wheelsPojoTransfer.tranferDomainToPojoWhenUpdate(wheels);
        return wheelsMapper.updateById(wheelsPojo);
    }

    @Override
    public WheelsPojo getWheels(Long id) {
        QueryWrapper<WheelsPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("Id",id).eq("OrganizationId",commonUtil.getOrganizationId()).eq("OrganizationName",commonUtil.getOrganizationName());
        return wheelsMapper.selectOne(wrapper);
    }
}
