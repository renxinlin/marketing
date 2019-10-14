package com.jgw.supercodeplatform.prizewheels.interfaces.vo;

import com.jgw.supercodeplatform.prizewheels.interfaces.dto.ProductUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardUpdateDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;
import java.util.List;

/**
 *
 * @author fangshiping
 * @date 2019/10/14 8:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("大转盘详情")
public class WheelsDetailsVo {
    @ApiModelProperty("id")private Long  id;

    @ApiModelProperty("活动模板id") private String  templateId;

    @ApiModelProperty("标题1")  private String title1;

    @ApiModelProperty("标题2")  private String title2;

    @ApiModelProperty("标题3")  private String title3;

    @ApiModelProperty("企业logo") private String logo;

    @ApiModelProperty("活动开始时间")  private Date startTime;

    @ApiModelProperty("活动结束时间")  private Date endTime;

    @ApiModelProperty("公众号二维码")   private String wxErcode;

    @ApiModelProperty("第三方链接按钮")   private String thirdUrlButton;

    @ApiModelProperty("第三方链接") private String thirdUrl;

    @ApiModelProperty("奖励总数") private Integer prizeNum;

    @ApiModelProperty("大转盘产品集合")  private List<ProductUpdateDto> productUpdateDtos;

    @ApiModelProperty("大转盘奖励集合")  private List<WheelsRewardUpdateDto> wheelsRewardUpdateDtos;

    @ApiModelProperty("是否自动获取(1、自动获取 2、仅此一次 )")   private byte autoType;

}
