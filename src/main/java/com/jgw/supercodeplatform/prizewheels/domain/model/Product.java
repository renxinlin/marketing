package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * <p>
 * 产品域
 * <p>
 * <p>
 * 参与大转盘活动的产品
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Product implements Serializable {


    public static final String SPLIT_SYMBOL = ",";
    /**
     * Id
     */
    private Long id;

    /**
     * 活动设置主键Id
     */
    private Long activitySetId;


    /**
     * 产品批次号
     */
    private String productBatchId;

    /**
     * 产品批次名称
     */
    private String productBatchName;

    /**
     * 活动产品Id
     */
    private String productId;

    /**
     * 活动产品名称
     */
    private String productName;


    /**
     * 活动大类0会员活动1导购活动
     */
    private Byte referenceRole = 0;

//    /**
//     * 生码批次ID
//     */
    private String sbatchId;

    /**
     * 产品所属活动类型其他0大转盘12签到
     */
    private Integer type = ActivityTypeConstant.wheels;

    /**
     * 码管理回调url
     */
    private String urlByCodeManagerCallBack;
    /**
     * 是否自动获取(1、自动获取 2、仅此一次 )当前仅当为大转盘和签到有效
     */
    private byte autoType;


    public void appendSbatchId(String sbatchId) {
        if(StringUtils.isEmpty(this.sbatchId)){
            this.sbatchId = sbatchId;
        }else {
            this.sbatchId = this.sbatchId + SPLIT_SYMBOL + sbatchId;
        }

    }
}
