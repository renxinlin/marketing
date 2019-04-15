package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel("积分订单分页VO")
public class IntegralOrderPageParam {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 订单业务主键 */
    @ApiModelProperty(value = "订单业务主键")
    private String orderId;

    /** 兑换资源0非自卖1自卖产品 */
    @ApiModelProperty(value = "兑换资源0非自卖1自卖产品")
    private Byte exchangeResource;

    /** 产品id|UUID */
    @ApiModelProperty(value = "产品id|UUID ")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /** sku信息 */
    @ApiModelProperty(value = "sku信息")
    private String skuName;

    /** sku图片url */
    @ApiModelProperty(value = "sku图片url")
    private String skuUrl;

    /** 兑换积分【单积分*数量】 */
    @ApiModelProperty(value = "兑换积分【单积分*数量】")
    private Integer exchangeIntegralNum;

    /** 兑换数量 */
    @ApiModelProperty(value = "兑换数量")
    private Integer exchangeNum;

    /** 收货名 */
    @ApiModelProperty(value = "收货人")
    private String name;

    /** 收货手机 */
    @ApiModelProperty(value = "收货手机")
    private String mobile;

    /** 收货地址 */
    @ApiModelProperty(value = "收货地址")
    private String address;

    /** 物流状态0待发货1已发货 */
    @ApiModelProperty(value = "物流状态0待发货1已发货")
    private Byte status;

    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /** 发货时间 */
    @ApiModelProperty(value = "发货时间")
    private Date deliveryDate;


    /** 组织id */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;
}
