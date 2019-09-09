package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel("导购员积分记录")
@Data
public class SalerIntegralRecordParam {


    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 注册门店【h5注册的门店信息】 */
    @ApiModelProperty(value = "注册门店【h5注册的门店信息】")
    private String customerName;


    /** 导购员名称 */
    @ApiModelProperty("导购员名称")
    private String salerName;


    /** 导购员手机 */
    @ApiModelProperty("导购员手机")
    private String salerMobile;
    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;



    /** 码信息 */
    @ApiModelProperty(value = "码信息")
    private String outerCodeId;


    /** 导购员红包金额 */
    @ApiModelProperty("导购红包金额")
    private Float salerAmount;



    /** 奖品增减原因 */
    @ApiModelProperty(value = "奖品增减原因")
    private String integralReason;




    /** 手机 */
    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty("订单号")
    private String tradeNo;



    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间字符串")
    private String createDateStr;


    /** 扫码状态1获得2未获得 */
    @ApiModelProperty("红包状态<1:获得红包未发送，2:未获得，3:红包发送中，4:发送失败，5:发送成功>")
    private String status;

}
