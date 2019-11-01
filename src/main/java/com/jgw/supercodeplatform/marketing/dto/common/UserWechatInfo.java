package com.jgw.supercodeplatform.marketing.dto.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserWechatInfo {
    private Long id;
    private Long orgWechatId;
    private String certificateAddress;
    private String certificatePassword;
    private String mchId;
    private String merchantKey;
    private String merchantName;
    private Byte merchantType;
    private String jgwAppid;
    private Byte jgwType;
    private Byte defaultUse;
    private Date createTime;
    private Date updateTime;
    private String organizationId;

}
