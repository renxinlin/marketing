package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 * 产品积分规则表
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_new_channel_order")
public class ChannelOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 表单名称
     */
    @TableField("FormName")
    private String formName;

    /**
     * 表单类型1文本2下拉框
     */
    @TableField("FormType")
    private Integer formType;

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
     * 数据库类型varchar bigint
     */
    @TableField("ColumnType")
    private String columnType;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 默认中文转拼音
     */
    @TableField("ColumnName")
    private String columnName;

    /**
     * 必填1非必填2
     */
    @TableField("Status")
    private Integer status;


}
