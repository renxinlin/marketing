package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("会员画像")
public class MemberPortraitDataVo {
    @ApiModelProperty("男数量")
    private Long manNum;
    @ApiModelProperty("女数量")
    private Long womanNum;
    @ApiModelProperty("男比率")
    private String manRate;
    @ApiModelProperty("女比率")
    private String womanRate;
    @ApiModelProperty("微信数量")
    private Long weChatNum;
    @ApiModelProperty("浏览器数量")
    private Long browser;
    @ApiModelProperty("微信比率")
    private String weChatNumRate;
    @ApiModelProperty("浏览器比率")
    private String browserRate;
    @ApiModelProperty("低年龄数量")
    private Long lowAgeNum;
    @ApiModelProperty("高年龄数量")
    private Long highAgeNum;
    @ApiModelProperty("低年龄比率")
    private String lowAgeNumRate;
    @ApiModelProperty("高年龄比率")
    private String highAgeNumRate;

}
