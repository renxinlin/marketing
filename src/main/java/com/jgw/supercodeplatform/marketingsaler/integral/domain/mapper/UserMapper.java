package com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper;

import com.jgw.supercodeplatform.marketingsaler.base.mapper.CommonMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
public interface UserMapper extends CommonMapper<User> {

    @Update(" update marketing_user set  HaveIntegral = HaveIntegral -  #{exchangeIntegral} where id = #{id} and organizationId = #{organizationId} and HaveIntegral -  #{exchangeIntegral} >= 0 ")
    int reduceIntegral(Integer exchangeIntegral, Long id, String organizationId);

    @Update(" update marketing_user set  HaveIntegral = HaveIntegral +  #{addIntegral} where id = #{id} and organizationId = #{organizationId}  ")
    int addIntegral(Integer addIntegral, Long id, String organizationId);
}
