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
    private byte type = RewardTypeConstant.virtual;

    private String address;

    private Date createTime;

    public void changeRecordTypeToGift(){
        type = RewardTypeConstant.real;
    }


}
