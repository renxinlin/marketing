package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("marketing_wx_member")
public class MarketingWxMember {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String wxName;

    private String openid;

    private Long memberId;

    private Byte wxSex;

    private String wechatHeadImgUrl;

    private String organizationId;

    private String organizationFullName;

    private Byte memberType;

    private String appid;

    private Date createTime;

    private Date  updateTime;

    private Byte jgwType;

    private Byte currentUse;

}
