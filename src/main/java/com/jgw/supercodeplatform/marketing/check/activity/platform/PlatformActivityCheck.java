package com.jgw.supercodeplatform.marketing.check.activity.platform;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityAdd.PrizeType;
import com.jgw.supercodeplatform.marketing.dto.platform.PlatformActivityUpdate;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlatformActivityCheck {

    @Autowired
    private MarketingActivitySetMapper marketingActivitySetMapper;

    /**
     * 创建活动基本条件检查
     * @param platformActivityAdd
     */
    public void platformActivityAddCheck(PlatformActivityAdd platformActivityAdd) {
        if (platformActivityAdd.getActivityEndDate().compareTo(platformActivityAdd.getActivityStartDate()) <= 0) {
            throw new SuperCodeExtException("活动结束时间必须大于活动开始时间");
        }
        if (!(platformActivityAdd instanceof PlatformActivityUpdate)) {
            MarketingActivitySet marketingActivitySet = marketingActivitySetMapper.getOnlyPlatformActivity();
            if (marketingActivitySet != null) {
                throw new SuperCodeExtException("当前已经存在正在运营的活动，不可重复创建");
            }
            MarketingActivitySet existmActivitySet = marketingActivitySetMapper.selectByTitlePlatform(platformActivityAdd.getActivityTitle(), 5L);
            if (existmActivitySet != null) {
                throw new SuperCodeExtException("您已设置过相同标题的活动不可重复设置");
            }
        }
        List<PrizeType> prizeTypeList = platformActivityAdd.getPrizeTypeList();
        int totalPrizeProbability = 0;
        for (PrizeType prizeType : prizeTypeList) {
            totalPrizeProbability = totalPrizeProbability + prizeType.getPrizeProbability();
        }
        if (totalPrizeProbability > 100) {
            throw new SuperCodeExtException("活动概率总和不能大于100%");
        }
        PrizeType prizeType = new PrizeType();
        prizeType.setPrizeAmount(new BigDecimal(0));
        prizeType.setPrizeProbability(100 - totalPrizeProbability);
        prizeType.setPrizeTypeName("未中奖");
        prizeType.setAwardGrade((byte) 0);
        prizeType.setRemainingNumber(9999999);
        platformActivityAdd.getPrizeTypeList().add(prizeType);

    }

}
