package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("marketing_wx_member")
public class MarketingWxMember {

    private Long id;

    private String wxName;

    private String openid;

    private Long memberId;

    private byte wxSex;

    private String wechatHeadImgUrl;

    private String organizationId;

    private String organizationFullName;

    private byte memberType;

    private String appid;

    private Date createTime;

    private Date  updateTime;

    private byte jgwType;

    private byte currentUse;

}
