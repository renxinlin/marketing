package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Blob;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_wx_merchants_ext")
@AllArgsConstructor
@NoArgsConstructor
public class MarketingWxMerchantsExt {

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 组织Id
     */
    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 组织
     */
    @TableField("OrganizatioIdlName")
    private String organizatioIdlName;

    /**
     * 标注是否是甲骨文公众号，0：否，1：是
     */
    @TableField("BelongToJgw")
    private Byte belongToJgw;

    /**
     * 证书
     */
    @TableField(value = "CertificateInfo")
    private byte[] certificateInfo;


}
