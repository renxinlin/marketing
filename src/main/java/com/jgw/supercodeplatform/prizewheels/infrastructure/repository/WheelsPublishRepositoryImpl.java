package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.WheelsPojoTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 六边形架构模式:实现解耦放置于基础设施
 */
@Slf4j
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
        wheels.setId(wheelsPojo.getId());
        log.info("新增大转盘返回大转盘主键{}",wheelsPojo.getId());

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
        wrapper.eq("Id",id).eq("OrganizationId",commonUtil.getOrganizationId());
        return wheelsMapper.selectOne(wrapper);
    }

    @Override
    public WheelsPojo getWheelsById(Long id) {
        QueryWrapper<WheelsPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("Id",id);
        return wheelsMapper.selectOne(wrapper);
    }

    @Override
    public Wheels getWheelsInfo(Long id) {
        QueryWrapper<WheelsPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("Id",id);
        WheelsPojo wheelsPojo = wheelsMapper.selectOne(wrapper);
        Asserts.check(wheelsPojo!=null, ErrorCodeEnum.NOT_EXITS_ERROR.getErrorMessage());
        return wheelsPojoTransfer.tranferPojoToDomain(wheelsPojo);
    }

}
