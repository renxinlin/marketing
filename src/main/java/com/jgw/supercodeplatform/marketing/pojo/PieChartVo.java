package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("饼图返回")
public class PieChartVo implements Comparable<PieChartVo> {
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("value")
    private Long vale;

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return name.equals(((PieChartVo)obj).getName());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public int compareTo(PieChartVo o) {
        if (this.name != null && o.name != null) {
            return this.name.compareTo(o.getName());
        }
        return 0;
    }
}
