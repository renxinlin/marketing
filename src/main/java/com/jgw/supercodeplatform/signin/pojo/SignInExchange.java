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
@TableName("marketing_activity_sign_in_exchange")
public class SignInExchange implements Serializable {


    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 商城ID
     */
    @TableField("MallId")
    private String mallId;

    /**
     * 兑换商品类型
     */
    @TableField("GoodsType")
    private Boolean goodsType;

    /**
     * 商品名称
     */
    @TableField("GoddsName")
    private String goddsName;

    /**
     * 商品代币价值
     */
    @TableField("GoodsScore")
    private String goodsScore;

    @TableField("UserId")
    private String userId;

    @TableField("UserName")
    private String userName;

    /**
     * 兑换时间
     */
    @TableField("CreateTime")
    private Date createTime;


}
