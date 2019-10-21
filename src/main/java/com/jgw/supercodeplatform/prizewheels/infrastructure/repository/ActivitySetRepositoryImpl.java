package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ActivitySetRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ActivitySetMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitySetRepositoryImpl implements ActivitySetRepository {
    @Autowired
    private ActivitySetMapper activitySetMapper;

    @Override
    public void updateWhenWheelsChanged(ActivitySet activitySet) {
        Asserts.check(activitySet != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(activitySet.getId1() != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(activitySet.getActivityId() != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        QueryWrapper<ActivitySet> query = new QueryWrapper<>();
        query.eq("id1",activitySet.getId1());
        query.eq("ActivityId", ActivityTypeConstant.wheels);
        activitySetMapper.update(activitySet,query);
    }


    @Override
    public void addWhenWheelsAdd(ActivitySet activitySet) {
        Asserts.check(activitySet != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(activitySet.getId1() != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(activitySet.getActivityId() != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        activitySetMapper.insert(activitySet);
    }
}
