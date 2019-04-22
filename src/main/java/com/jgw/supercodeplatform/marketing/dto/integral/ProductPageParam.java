package com.jgw.supercodeplatform.marketing.dto.integral;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("自卖非自卖产品类别")
public class ProductPageParam extends DaoSearch {
    @ApiModelProperty("0非自卖1自卖产品")
    private Byte type;

    @ApiModelProperty("兑换记录id|存在表示编辑时获取自卖非自卖,否则表示新增 ")
    private Long id;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
