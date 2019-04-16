package com.jgw.supercodeplatform.marketing.dto.integral;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("自卖非自卖产品类别")
public class ProductPageParam extends DaoSearch {
    @ApiModelProperty("0非自卖1自卖产品")
    private Byte type;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
