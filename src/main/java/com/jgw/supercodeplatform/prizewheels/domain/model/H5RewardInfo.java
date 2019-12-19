package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.domain.constants.RewardTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.http.util.Asserts;
import org.springframework.util.StringUtils;

/**
 *
 奖励值对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("奖励值对象")
@AllArgsConstructor
@NoArgsConstructor
public class H5RewardInfo {
    @ApiModelProperty("奖项id")
    private Long id;
    @ApiModelProperty("虚拟产品图片")
    private String picture;
    @ApiModelProperty("cdk")
    private String cdk;
    @ApiModelProperty("虚拟实物")
    private byte type;
    @ApiModelProperty("实物产品名称")
    private String name;


    @ApiModelProperty("实物产品名称")
    private Integer sendDay;



    @ApiModelProperty("中奖金额")
    private double money;

    @ApiModelProperty("中奖金额")
    private Long recordId;



    public void initVirtualRewardInfo(Long id,String picture, String cdk) {
        Asserts.check(!StringUtils.isEmpty(cdk),"虚拟奖品cdk无");
        this.id=id;
        this.cdk=cdk;
        this.picture = picture;
        this.type = RewardTypeConstant.virtual;
    }

    public void initRealReward(Long id,String picture,String name ,Integer sendDay,Long wheelsRecordId) {
        this.id=id;
        Asserts.check(!StringUtils.isEmpty(name),"实物奖品名称不存在");
        this.name=name;
        this.picture = picture;
        this.type = RewardTypeConstant.real;
        this.sendDay = sendDay;
        this.recordId =wheelsRecordId;
    }

    public void initMoneyReward(Long id, String picture, String name, double rewardMoneyForUser) {
        this.id=id;
        this.name=name;
        this.picture = picture;
        this.type = RewardTypeConstant.money;
        this.money = rewardMoneyForUser;
    }
}
