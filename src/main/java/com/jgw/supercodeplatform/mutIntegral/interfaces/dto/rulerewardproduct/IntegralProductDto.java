package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("马关联的产品")
@AllArgsConstructor
@NoArgsConstructor
public class IntegralProductDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 活动产品Id
     */
    @NotEmpty(message = "产品批次ID")
    private String productId;

    /**
     * 活动产品名称
     */
    @NotEmpty(message = "产品批次ID")
    private String productName;

    private List<IntegralProductBatchDto> productBatchDtos;


}
