package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProcessActivityDomainService;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 该领域服务处理大转盘活动到老活动的业务
 */
@Service
public class ProcessActivityDomainServiceImpl implements ProcessActivityDomainService {


    @Autowired
    private WheelsMapper wheelsMapper;


    @Autowired
    private CommonUtil commonUtil;

    public ActivitySet formPrizeWheelsToOldActivity(Wheels wheels,Integer autoFetch){
        ActivitySet activitySet = new ActivitySet();

        WheelsPojo prizeWheels = wheelsMapper.selectById(wheels.getId());
        activitySet.setActivityEndDate(prizeWheels.getEndTime());
        activitySet.setActivityId(ActivityTypeConstant.wheels.longValue());
        activitySet.setActivityStartDate(prizeWheels.getStartTime());
        activitySet.setActivityTitle(prizeWheels.getTitle1());
        activitySet.setAutoFetch(autoFetch);
        activitySet.setActivityDesc("大转盘活动");
        activitySet.setId1(prizeWheels.getId());
        activitySet.setUpdateDate(prizeWheels.getUpdateDate());
        activitySet.setUpdateUserId(commonUtil.getUserLoginCache().getAccountId());
        activitySet.setUpdateUserName(commonUtil.getUserLoginCache().getUserName());
        activitySet.setOrganizatioIdlName(commonUtil.getOrganizationName());
        activitySet.setOrganizationId(commonUtil.getOrganizationId());
        return activitySet;
    }
}
