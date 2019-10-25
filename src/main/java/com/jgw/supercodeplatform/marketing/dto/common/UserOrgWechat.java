package com.jgw.supercodeplatform.marketing.dto.common;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserOrgWechat {
    private Long id;
    private String organizationId;
    private String organizationName;
    private String appId;
    private String secret;
    private String h5Domain;
    private Date createTime;
    private Date updateTime;

}