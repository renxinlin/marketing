package com.jgw.supercodeplatform.signin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author fangshiping
 * @since 2019-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_sign_in_mall")
public class SignInMall implements Serializable {


    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品类型1虚拟2代币
     */
    @TableField("GoodsType")
    private Integer goodsType;

    /**
     * 商品名称
     */
    @TableField("GoodsName")
    private String goodsName;

    /**
     * 商品领取条件
     */
    @TableField("GoodsCondition")
    private String goodsCondition;

    /**
     * 商品图片
     */
    @TableField("GoodsPicture")
    private String goodsPicture;

    /**
     * 奖励列表
     */
    @TableField("GoodsList")
    private String goodsList;

    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 组织id
     */
    @TableField("OrganizationName")
    private String organizationName;

    @TableField("UpdateUesrId")
    private String updateUesrId;

    @TableField("UpdateUserName")
    private String updateUserName;

    @TableField("UpdateDate")
    private Date updateDate;

    @TableField("CreateUserId")
    private String createUserId;

    @TableField("CreateUserName")
    private String createUserName;

    @TableField("CreteDate")
    private Date creteDate;


}
