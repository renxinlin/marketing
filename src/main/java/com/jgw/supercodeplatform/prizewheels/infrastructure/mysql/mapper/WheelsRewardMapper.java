package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
public interface WheelsRewardMapper extends BaseMapper<WheelsRewardPojo> {

@Update("update marketing_activity_prize_wheels_reward set Stock  = Stock - 1 where id = #{id} and Stock > 0")
    int reduceStock(Long id);
}
