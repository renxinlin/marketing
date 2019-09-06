package com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_user")
public class User implements Serializable {


    /**
     * 序号
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 微信昵称
     */
    @TableField("WxName")
    private String wxName;

    /**
     * 微信id号
     */
    @TableField("Openid")
    private String openid;

    /**
     * 手机
     */
    @TableField("Mobile")
    private String mobile;

    /**
     * 用户Id
     */
    @TableField("UserId")
    private String userId;

    /**
     * 用户姓名
     */
    @TableField("UserName")
    private String userName;

    /**
     * 性别1女0男
     */
    @TableField("Sex")
    private String sex;

    /**
     * 生日
     */
    @TableField("Birthday")
    private Date birthday;

    /**
     * 省编码
     */
    @TableField("ProvinceCode")
    private String provinceCode;

    /**
     * 县编码
     */
    @TableField("CountyCode")
    private String countyCode;

    /**
     * 市编码
     */
    @TableField("CityCode")
    private String cityCode;

    /**
     * 省名称
     */
    @TableField("ProvinceName")
    private String provinceName;

    /**
     * 县名称
     */
    @TableField("CountyName")
    private String countyName;

    /**
     * 市名称
     */
    @TableField("CityName")
    private String cityName;

    /**
     * 组织Id
     */
    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 建立日期
     */
    @TableField("CreateDate")
    private Date createDate;

    /**
     * 修改日期
     */
    @TableField("UpdateDate")
    private Date updateDate;

    /**
     * 门店名称
     */
    @TableField("CustomerName")
    private String customerName;

    /**
     * 门店编码
     */
    @TableField("CustomerId")
    private String customerId;

    /**
     * 省市区前端编码
     */
    @TableField("PCCcode")
    private String pCCcode;

    /**
     * 微信头像/个人头像
     */
    @TableField("WechatHeadImgUrl")
    private String wechatHeadImgUrl;

    /**
     * 默认1导购员,其他员工等
     */
    @TableField("MemberType")
    private Boolean memberType;

    /**
     * 用户状态(1、 待审核，2 停用3启用)导购员状态
     */
    @TableField("State")
    private Integer state;

    /**
     * 扫码设备类型
     */
    @TableField("DeviceType")
    private Boolean deviceType;

    /**
     * 添加可用积分
     */
    @TableField("HaveIntegral")
    private Integer haveIntegral;


}
