package com.jgw.supercodeplatform.marketing.dto.codemanagerservice;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 抵扣券服务交互数据
 */
@Data
public class CouponActivity implements Serializable {

    private String productId;

    private String productBatchId;
    private List<String> codeBatchIds;
    /**
     * 0 删除绑定 1 添加绑定
     */
    private Byte status;
    public void say(String ... say ){

    }

}
