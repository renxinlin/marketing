package com.jgw.supercodeplatform.marketing.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@TableName("marketing_wx_merchants")
public class MarketingWxMerchants {
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;
    private String mchAppid;//商户账号appid
    private String mchid;//商户号
    private String merchantName;//商户名称
    private String merchantKey;//商户key，微信支付签名时需要
    private String certificateAddress;//证书地址
    private String certificatePassword;//证书密码
    private String organizationId;//组织id
    private String organizatioIdlName;//组织
    private String merchantSecret;//公众号secret微信授权获取token时需要用到
    private Byte merchantType;
    private Byte belongToJgw;
    private Byte defaultUse;
    private Long platformId;
    private Long jgwId;

}
