package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

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
 * @since 2019-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_members")
public class MembersPojo implements Serializable {


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
     * 注册时间
     */
    @TableField("RegistDate")
    private Date registDate;

    /**
     * 会员状态(1、 表示正常，0 表示下线)状态2表示临时数据
     */
    @TableField("State")
    private Integer state;

    /**
     * 组织Id[名称缓存在redis]
     */
    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 是否新注册的标志(1  表示是，0 表示不是)
     */
    @TableField("NewRegisterFlag")
    private String newRegisterFlag;

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
     * 宝宝生日
     */
    @TableField("BabyBirthday")
    private Date babyBirthday;

    /**
     * 是否已完善(1、表示已完善，0 表示未完善)
     */
    @TableField("IsRegistered")
    private Boolean isRegistered;

    @TableField("PCCcode")
    private String pCCcode;

    @TableField("WechatHeadImgUrl")
    private String wechatHeadImgUrl;

    /**
     * 默认0会员，1导购员,其他员工等
     */
    @TableField("MemberType")
    private Boolean memberType;

    /**
     * 添加可用积分
     */
    @TableField("HaveIntegral")
    private Integer haveIntegral;

    /**
     * 最新一次积分领取时间
     */
    @TableField("IntegralReceiveDate")
    private Date integralReceiveDate;

    /**
     * 注册来源1招募会员
     */
    @TableField("UserSource")
    private Boolean userSource;

    /**
     * 扫码设备类型
     */
    @TableField("DeviceType")
    private Boolean deviceType;

}
