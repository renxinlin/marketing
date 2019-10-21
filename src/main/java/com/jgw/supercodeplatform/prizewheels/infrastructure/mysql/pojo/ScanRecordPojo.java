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
@TableName("marketing_activity_prize_wheels_scan_record")
public class ScanRecordPojo implements Serializable {

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    @TableField("OuterCodeId")
    private String outerCodeId;

    @TableField("CodeTypeId")
    private Integer codeTypeId;

    /**
     * 扫码用户
     */
    @TableField("ScannerId")
    private Long scannerId;

    @TableField("CreateDate")
    private Date createDate;

    /**
     * 导购0会员
     */
    @TableField("ScannerType")
    private String scannerType;

    @TableField("OrganizationId")
    private String organizationId;


    @TableField("ScannerName")
    private String scannerName;


    @TableField("OrganizationName")
    private String organizationName;

    @TableField("Mobile")
    private String mobile;

}
