package com.jgw.supercodeplatform.two.service.transfer;

import com.jgw.supercodeplatform.marketing.common.constants.StateConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.two.dto.MarketingMembersBindMobileParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MemberTransfer {
    public void transferExists(MarketingMembers marketingMembersTwo, MarketingMembers exitMarketingMembers) {
        //说明3.0数据中已绑定手机号 可用积分和总积分进行积分转移 其他属性转义
        BeanUtils.copyProperties(marketingMembersTwo,exitMarketingMembers,"id","openid","mobile","userId","haveIntegral","totalIntegral");
        exitMarketingMembers.setHaveIntegral(
                (exitMarketingMembers.getHaveIntegral() == null ? 0: exitMarketingMembers.getHaveIntegral())
                        +(marketingMembersTwo.getHaveIntegral() == null ? 0: marketingMembersTwo.getHaveIntegral())
        );
        exitMarketingMembers.setTotalIntegral(
                (exitMarketingMembers.getTotalIntegral() == null ? 0: exitMarketingMembers.getTotalIntegral())
                        + (marketingMembersTwo.getTotalIntegral()  == null ? 0: marketingMembersTwo.getTotalIntegral())
        );



    }

    /**
     *
     * @param marketingMembersBindMobileParam 网页入参
     * @param marketingMembersTwo  2.0用户
     * @param integralByRegister 注册送积分
     * @return
     */
    public MarketingMembers transferNotExists0(MarketingMembersBindMobileParam marketingMembersBindMobileParam, MarketingMembers marketingMembersTwo, int integralByRegister) {
        MarketingMembers marketingMembersNew=new MarketingMembers();
        BeanUtils.copyProperties(marketingMembersTwo,marketingMembersNew,"id");
        marketingMembersNew.setMobile(marketingMembersBindMobileParam.getMobile());

        marketingMembersNew.setLoginName("");
        marketingMembersNew.setPassword("");

        marketingMembersNew.setState(StateConstants.ENABLE);


        return marketingMembersNew;
    }
}
