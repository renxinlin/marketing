package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import org.springframework.stereotype.Service;

/**
 * 该领域服务处理大转盘活动到老活动的业务
 */
@Service
public interface ProcessActivityDomainService {

     ActivitySet formPrizeWheelsToOldActivity(Wheels prizeWheels,Integer autoFetch);
}
