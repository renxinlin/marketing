package com.jgw.supercodeplatform.marketingsaler.order.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 产品积分规则表
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SalerOrderFormDto implements Serializable {


    /**
     * 主键
     */
    private Long id;

    /**
     * 表单名称
     */
    @NotEmpty(message = "表单名称不可为空")
    private String formName;

    /**
     * 表单类型1文本2下拉框
     */
    @ApiModelProperty("1文本2下拉框")
    @NotNull(message = "表单类型不可为空")
    @Min(value = 1,message = "表单类型不合法")
    @Max(value = 1,message = "表单类型不合法")
    private Byte formType;

    /**
     * 表单值
     */
    private String value;

    /**
     * 动态表表名[组织名+id]
     */
    private String tableName;

    /**
     * 数据库类型
     */
    private String columnType;

    private String organizationId;

    private String organizationName;

    /**
     * 数据库类型varchar bigint
     */
    private String columnName;


}
