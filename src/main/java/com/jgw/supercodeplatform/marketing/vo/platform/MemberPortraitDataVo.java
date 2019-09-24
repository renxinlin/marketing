package com.jgw.supercodeplatform.marketing.vo.platform;

import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("会员画像")
public class MemberPortraitDataVo {
    @ApiModelProperty("性别")
    List<PieChartVo> sex;
    @ApiModelProperty("年龄")
    List<PieChartVo> age;
    @ApiModelProperty("扫码来源")
    List<PieChartVo> scanSource;

}
