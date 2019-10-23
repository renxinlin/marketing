package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("实物信息录入实体")
public class PrizeWheelsOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotEmpty(message = "请填写实物地址")
    @ApiModelProperty("实物地址")
     private String address;

    @NotEmpty(message = "请填写实物")
    @ApiModelProperty("实物")
     private String name;

    /**
     * 收货人姓名[备用]
     */
    @NotEmpty(message = "请填写收获人姓名")
    @ApiModelProperty("收获人姓名")
    private String receiverName;

    /**
     * 收货手机
     */
    @NotEmpty(message = "请填写收获人手机")
    @ApiModelProperty("收获人手机")
    private String receiverMobile;



}
