package com.jgw.supercodeplatform.marketingsaler.integral.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_saler_exchange_num")
@AllArgsConstructor
@NoArgsConstructor
public class SalerExchangeNum implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("UserId")
    private Long userId;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("exchangeId")
    private Long exchangeId;


}
