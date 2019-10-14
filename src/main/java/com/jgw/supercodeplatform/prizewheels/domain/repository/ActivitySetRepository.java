package com.jgw.supercodeplatform.prizewheels.domain.repository;


import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ActivitySet;
import org.springframework.stereotype.Service;

/**
 * 发布服务涉及多个聚合的处理
 *
 * 此服务采用实现类实现
 *
 * 创建方式：通过工厂解耦 [no]
 * 创建方式: 依赖注入 [yes]
 */
@Service
public interface ActivitySetRepository {
    /**
     * 修改活动聚合的老表
     * @param activitySet
     */
    void  updateWhernWheelsChanged(ActivitySet activitySet);

     void addWhenWheelsAdd(ActivitySet activitySet) ;

}
