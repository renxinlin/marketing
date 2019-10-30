package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
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
public class ScanRecord implements Serializable {
    public  ScanRecord(){

    }
    public ScanRecord(String outerCodeId,Integer codeTypeId){
        this.codeTypeId=codeTypeId;
        this.outerCodeId=outerCodeId;
    }

    public void initScanerInfo(Long scannerId,String scannerName,String mobile, String organizationId,String organizationName
            ,String openId,String wxName){
        this.scannerId = scannerId;
        this.scannerName = scannerName;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.mobile = mobile;
        this.openId = openId;
        this.wxName = wxName;
    }

    private String openId;
    private String wxName;


    private String outerCodeId;
    private String mobile;

    private Integer codeTypeId;



    private Date createDate = new Date();

    /**
     * 导购0会员
     */
    private String scannerType = MemberTypeEnums.VIP.getType().toString();


    /**
     * 扫码用户
     */
    private Long scannerId;
    private String scannerName;


    private String organizationId;
    private String organizationName;


}
