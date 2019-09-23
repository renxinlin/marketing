package com.jgw.supercodeplatform.marketing.dto.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.LotteryResultMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Setter
@Getter
public class LotteryOprationDto {
    /**
     * 参与活动门槛所需积分
     */
    private int consumeIntegralNum;
    /**
     * 用户拥有的积分
     */
    private int haveIntegral;
    /**
     * 标识是否为一次成功的抽奖(不一定抽中，但是一次完整的抽奖流程，需要加入抽奖记录和扫码记录,1：成功抽奖，0:非成功的抽奖)
     */
    private int successLottory;
    /**
     * 返回结果
     */
    private RestResult<LotteryResultMO> restResult;
    /**
     * 中奖MO
     */
    private LotteryResultMO lotteryResultMO;
    /**
     * 活动组织ID
     */
    private String organizationId;
    /**
     * 活动组织名称
     */
    private String organizationName;
    /**
     * 微信扫码信息
     */
    private ScanCodeInfoMO scanCodeInfoMO;
    /**
     * 抽奖结果
     */
    private MarketingPrizeTypeMO prizeTypeMO;
    /**
     * 会员信息
     */
    private MarketingMembers marketingMembersInfo;
    /**
     * 每天限制扫码次数
     */
    private Integer eachDayNumber;
    /**
     * 积分记录
     */
    private IntegralRecord integralRecord;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 抽奖对应的活动
     */
    private MarketingActivity marketingActivity;
    /**
     * 该活动发放红包时是否需要审核
     */
    private byte sendAudit;
    /**
     * 内码
     */
    private String innerCode;
    /**
     * 点击跳转更多链接
     */
    private String sourceLink;

    public LotteryOprationDto lotterySuccess(int winnOrNot, String msg) {
        if (lotteryResultMO == null){
            lotteryResultMO = new LotteryResultMO();
        }
        if (winnOrNot > 0) {
            lotteryResultMO.setWinnOrNot(winnOrNot);
        }
        if (StringUtils.isNotBlank(msg)) {
            lotteryResultMO.setMsg(msg);
        }
        this.setRestResult(RestResult.success(msg, lotteryResultMO));
        return this;
    }

    public LotteryOprationDto lotterySuccess(String msg) {
        return lotterySuccess(0, msg);
    }

    public LotteryOprationDto lotterySuccess(int winnOrNot) {
        return lotterySuccess(winnOrNot, null);
    }

}
