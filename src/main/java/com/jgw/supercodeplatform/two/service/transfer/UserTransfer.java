package com.jgw.supercodeplatform.two.service.transfer;

import com.jgw.supercodeplatform.marketing.common.constants.StateConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.two.constants.JudgeBindConstants;
import com.jgw.supercodeplatform.two.dto.MarketingSaleUserBindMobileParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author fangshiping
 * @date 2019/11/28 15:36
 */
@Component
public class UserTransfer {
    public void transferExists(MarketingUser marketingUserTwo, MarketingUser exitMarketingUser){
        BeanUtils.copyProperties(marketingUserTwo,exitMarketingUser,"id","openid","mobile","userId","haveIntegral","totalIntegral");
        exitMarketingUser.setHaveIntegral(
                (exitMarketingUser.getHaveIntegral()== null ? 0:exitMarketingUser.getHaveIntegral())
                        +(marketingUserTwo.getHaveIntegral()== null ? 0:marketingUserTwo.getHaveIntegral())
        );
        exitMarketingUser.setTotalIntegral(
                (exitMarketingUser.getTotalIntegral()== null ? 0:exitMarketingUser.getTotalIntegral())
                        +(marketingUserTwo.getTotalIntegral()== null ? 0:marketingUserTwo.getTotalIntegral())
        );
        marketingUserTwo.setHaveIntegral(0);
        marketingUserTwo.setTotalIntegral(0);
    }
    /**
     *
     * @param marketingSaleUserBindMobileParam 网页入参
     * @param marketingUserTwo  2.0用户
     * @return
     */
    public MarketingUser transferNotExists0(MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam,MarketingUser marketingUserTwo){
        MarketingUser marketingUserNew = new MarketingUser();
        BeanUtils.copyProperties(marketingUserTwo,marketingUserNew,"id");
        marketingUserNew.setMobile(marketingSaleUserBindMobileParam.getMobile());


        marketingUserTwo.setHaveIntegral(0);
        marketingUserTwo.setTotalIntegral(0);


        marketingUserNew.setLoginName("");
        marketingUserNew.setPassword("");
        marketingUserNew.setState(StateConstants.ENABLE);

        return marketingUserNew;
    }
}
