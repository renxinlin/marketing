package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.UserMapper;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.SendIntegralPersonDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SendIntegralPersonDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.MembersMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.MembersPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class SendIntegralPersonDomainServiceImpl implements SendIntegralPersonDomainService {

    @Autowired
    private MembersMapper membersMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SendIntegralPersonDomain findSendIntegralPerson(SendIntegralPersonDomain sendIntegralPersonDomain) {
        LambdaQueryWrapper<MembersPojo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MembersPojo::getOrganizationId,sendIntegralPersonDomain.getOrganizationId());
        queryWrapper.eq(MembersPojo::getMobile,sendIntegralPersonDomain.getMobile());
        MembersPojo membersPojo = membersMapper.selectOne(queryWrapper);
        if(membersPojo!=null){
            SendIntegralPersonDomain domain = modelMapper.map(membersPojo, SendIntegralPersonDomain.class);
            domain.setMemberName(membersPojo.getUserName());
            return domain;
        }
        return null;
    }

    @Override
    public void addIntegral(SendIntegralPersonDomain person) {
        userMapper.addIntegral(person.getIntegralNum(),person.getId(),person.getOrganizationId());
    }
}
