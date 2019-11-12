package com.jgw.supercodeplatform.SyncNetData.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@ApiModel("会员用户")
public class MarketingMemberDto {

    @ApiModelProperty("2.0平台的对应的ID")
    private String netCustomerID;
    @ApiModelProperty("微信昵称")
    private String wxName;//微信昵称
    @ApiModelProperty("微信id号")
    private String openid;//微信id号
    @ApiModelProperty("手机")
    private String mobile;//手机
    @ApiModelProperty(value = "用户ID", hidden = true)
    private String userId;//用户Id
    @ApiModelProperty("用户名")
    private String userName;//用户姓名
    @ApiModelProperty("性别")
    private Byte sex;//性别
    @ApiModelProperty("出生日期")
    private String birthday;//生日
    @ApiModelProperty("省编码")
    private String provinceCode;// 省编码
    @ApiModelProperty("省名")
    private String provinceName;// 省名
    @ApiModelProperty("城市编码")
    private String cityCode;
    @ApiModelProperty("城市名")
    private String cityName;
    @ApiModelProperty("区编码")
    private String countyCode;
    @ApiModelProperty("区名")
    private String countyName;
    @ApiModelProperty("注册时间")
    private String registDate;//注册时间
    @ApiModelProperty("状态(1、 表示正常，0 表示下线)")
    private Byte state;//状态(1、 表示正常，0 表示下线)
    @ApiModelProperty("组织ID")
    private String organizationId;//组织Id
    @ApiModelProperty("是否是新注册的标志(1  表示是，0 表示不是)")
    private String newRegisterFlag;//是否新注册的标志(1  表示是，0 表示不是)
    @ApiModelProperty("创建日期")
    private String createDate;//建立日期
    @ApiModelProperty("修改日期")
    private String updateDate;//修改日期
    @ApiModelProperty("门店名词")
    private String customerName;//门店名称
    @ApiModelProperty("门店ID")
    private String customerId;//门店编码
    @ApiModelProperty("宝宝生日")
    private String babyBirthday;//宝宝生日
    @ApiModelProperty("是否已完善(1、表示已完善，0 表示未完善)")
    private Byte isRegistered;// 是否已完善(1、表示已完善，0 表示未完善)
    @ApiModelProperty("pCCcode")
    private String pCCcode;
    @ApiModelProperty("会员积分")
    private Integer haveIntegral; //  会员积分
    @ApiModelProperty("最新一次积分领取时间")
    private Date integralReceiveDate; // 最新一次积分领取时间
    @ApiModelProperty("注册来源1招募会员")
    private Byte userSource;// 注册来源1招募会员
    @ApiModelProperty("设备来源")
    private Byte deviceType;// 设备来源
}
