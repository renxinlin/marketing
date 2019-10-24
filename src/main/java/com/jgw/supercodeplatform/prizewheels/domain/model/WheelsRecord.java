package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.prizewheels.domain.constants.RewardTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WheelsRecord implements Serializable {


    private Long id;

    private String mobile;

    private String userId;

    private String userName;

    private String rewardName;

    /**
     * 1虚拟2 实物
     */
    private byte type  ;

    private String address;

    private Date createTime;
    private String organizationId;
    private String organizatioIdName;
    private Long prizeWheelId;
    private Long rewardId;


    public void initvirtualInfo(String mobile, String name, String memberId, String memberName, Long prizeWheelsId, Long id, String organizationName, String organizationId) {
        this.setCreateTime(new Date());
        this.setMobile(mobile);
        this.setRewardName(name);
        this.setType(RewardTypeConstant.virtual);
        this.setUserId(memberId);
        this.setUserName(memberName);
        this.setPrizeWheelId(prizeWheelsId);
        this.setRewardId(id);
        this.setOrganizatioIdName(organizationName);
        this.setOrganizationId(organizationId);
    }

    public void initrealInfo(String mobile, String name, String memberId, String memberName, Long prizeWheelsId, Long id, String organizationName, String organizationId) {
        this.setCreateTime(new Date());
        this.setMobile(mobile);
        this.setRewardName(name);
        this.setType(RewardTypeConstant.real);
        this.setUserId(memberId);
        this.setUserName(memberName);
        this.setPrizeWheelId(prizeWheelsId);
        this.setRewardId(id);
        this.setOrganizatioIdName(organizationName);
        this.setOrganizationId(organizationId);
    }
}
