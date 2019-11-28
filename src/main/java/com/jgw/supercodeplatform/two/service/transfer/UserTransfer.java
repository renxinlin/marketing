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
        BeanUtils.copyProperties(marketingUserTwo,exitMarketingUser,"id","mobile","userId","haveIntegral","totalIntegral");
        exitMarketingUser.setHaveIntegral(
                (exitMarketingUser.getHaveIntegral()== null ? 0:exitMarketingUser.getHaveIntegral())
                        +(marketingUserTwo.getHaveIntegral()== null ? 0:marketingUserTwo.getHaveIntegral())
        );
        exitMarketingUser.setTotalIntegral(
                (exitMarketingUser.getTotalIntegral()== null ? 0:exitMarketingUser.getTotalIntegral())
                        +(marketingUserTwo.getTotalIntegral()== null ? 0:marketingUserTwo.getTotalIntegral())
        );
        exitMarketingUser.setBinding(JudgeBindConstants.HAVEBIND);
        marketingUserTwo.setHaveIntegral(0);
        marketingUserTwo.setTotalIntegral(0);
    }

    public MarketingUser transferNotExists0(MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam,MarketingUser marketingUserTwo){
        MarketingUser marketingUserNew = new MarketingUser();
        BeanUtils.copyProperties(marketingUserTwo,marketingUserNew,"id");
        marketingUserNew.setMobile(marketingSaleUserBindMobileParam.getMobile());
        marketingUserNew.setHaveIntegral(
                (marketingUserNew.getHaveIntegral()== null ? 0:marketingUserNew.getHaveIntegral())
        );
        marketingUserNew.setTotalIntegral(
                (marketingUserNew.getTotalIntegral()== null ? 0:marketingUserNew.getTotalIntegral())
        );
        marketingUserNew.setLoginName("");
        marketingUserNew.setPassword("");
        //启用
        marketingUserNew.setState(StateConstants.ENABLE);

        return marketingUserNew;
    }
}
