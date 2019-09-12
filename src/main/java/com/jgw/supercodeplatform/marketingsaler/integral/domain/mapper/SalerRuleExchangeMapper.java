package com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.base.mapper.CommonMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
public interface SalerRuleExchangeMapper extends CommonMapper<SalerRuleExchange> {

    @Update(" update marketing_saler_rule_exchange set HaveStock = HaveStock - 1  where HaveStock - 1 > 0  and id = #{id} ")
    int reduceHaveStock(SalerRuleExchange salerRuleExchange);
    @Update(" update marketing_saler_rule_exchange set PreHaveStock = PreHaveStock - 1  where PreHaveStock - 1 > 0  and id = #{id} ")
    int updateReduceHaveStock(SalerRuleExchange salerRuleExchange);
}
