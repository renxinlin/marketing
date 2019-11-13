package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProcessActivityDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ActivitySetMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 该领域服务处理大转盘活动到老活动的业务
 */
@Service
public class ProcessActivityDomainServiceImpl implements ProcessActivityDomainService {


    @Autowired
    private WheelsMapper wheelsMapper;


    @Autowired
    private ActivitySetMapper setMapper;


    @Autowired
    private CommonUtil commonUtil;

    public ActivitySet formPrizeWheelsToOldActivity(Wheels wheels,Integer autoFetch){
        ActivitySet activitySet = new ActivitySet();

        WheelsPojo prizeWheels = wheelsMapper.selectById(wheels.getId());
        activitySet.setActivityEndDate(wheels.getEndTime());
        activitySet.setActivityId(ActivityTypeConstant.wheels.longValue());
        activitySet.setActivityStartDate(wheels.getStartTime());
        activitySet.setActivityTitle(wheels.getTitle1());
        activitySet.setAutoFetch(autoFetch);
        activitySet.setActivityDesc("大转盘活动");
        activitySet.setId1(prizeWheels.getId());
        activitySet.setActivityStatus(wheels.getActivityStatus());
        activitySet.setUpdateDate(new Date());
        activitySet.setUpdateUserId(commonUtil.getUserLoginCache().getAccountId());
        activitySet.setUpdateUserName(commonUtil.getUserLoginCache().getUserName());
        activitySet.setOrganizatioIdlName(commonUtil.getOrganizationName());
        activitySet.setOrganizationId(commonUtil.getOrganizationId());
        return activitySet;
    }

    @Override
    public void updateStatus(Long id, String status) {
        ActivitySet activitySet = new ActivitySet();
        activitySet.setId1(id);
        activitySet.setActivityStatus(status);
        QueryWrapper<ActivitySet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id1",id);
        setMapper.update(activitySet,queryWrapper);


    }
}
