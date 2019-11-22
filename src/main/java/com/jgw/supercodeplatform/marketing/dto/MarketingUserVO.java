package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 * 前端不愿意改pcccode的大小写问题,......
 * lombak导致的坑
 */
@Data
public class MarketingUserVO {
    /** 序号 */
    private Long id;

    /** 微信昵称 */
    private String wxName;

    /** 微信id号 */
    private String openid;

    /** 手机 */
    private String mobile;

    /** 用户Id */
    private String userId;

    /** 用户姓名 */
    private String userName;

    /** 性别1女0男 */
    private String sex;

    /** 生日 */
    private Date birthday;

    /** 省编码 */
    private String provinceCode;

    /** 县编码 */
    private String countyCode;

    /** 市编码 */
    private String cityCode;

    /** 省名称 */
    private String provinceName;

    /** 县名称 */
    private String countyName;

    /** 市名称 */
    private String cityName;

    /** 组织Id */
    private String organizationId;

    /** 建立日期 */
    private Date createDate;

    /** 修改日期 */
    private Date updateDate;

    /** 门店类型 */
    @ApiModelProperty("机构类型1总部2子公司3经销商4门店5库房10子门店15地方政府16公司20销售公司25农场31其他")
    private String mechanismType;

    /** 门店名称 */
    private String customerName;

    /** 门店编码 */
    private String customerId;

    /** 省市区前端编码 */
    private String pCCcode;

    /** 微信头像/个人头像 */
    private String wechatHeadImgUrl;

    /** 默认1导购员,其他员工等 */
    private Byte memberType;

    /** 用户状态(1、 表示正常，0 表示下线)导购员状态 */
    private Byte state;

    /** 扫码设备类型 */
    private Byte deviceType;

    /** 累计积分*/
    @ApiModelProperty("累计积分")
    private Integer totalIntegral;

    /** 可用积分*/
    @ApiModelProperty("可用积分")
    private Integer haveIntegral;

    private Integer version;

    /** 来源1商城2积分网站*/
    @ApiModelProperty("来源")
    private Byte source;
}
