package com.jgw.supercodeplatform.marketingsaler.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("marketing_saler_order_form")
public class SalerOrderForm implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 表单名称
     */
    @NotEmpty(message = "表单名称不可为空")
    @TableField("FormName")
    private String formName;

    /**
     * 表单类型1文本2下拉框
     */
    @TableField("FormType")
    @NotNull(message = "表单类型不可为空")
    private Byte formType;

    /**
     * 表单值
     */
    @TableField("Value")
    private String value;

    /**
     * 动态表表名[组织名+id]
     */
    @TableField("TableName")
    private String tableName;

    /**
     * 数据库类型
     */
    @TableField("ColumnType")
    private String columnType;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 数据库类型varchar bigint
     */
    @TableField("ColumnName")
    private String columnName;


}
