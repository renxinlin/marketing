package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IntegralProductAutoAppendDto implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    private Long id;



    /**
     * 产品批次名称
     */
    private String sbatchID;

    /**
     * 活动产品Id
     */
    private String startSegmentCode;

    /**
     * 活动产品名称
     */
    private String endSegmentCode;

    /**
     * 建立日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 2单码1号段码3 批次码
     */
    private Integer level;

    private String productID;

    private String productName;

    private String productBatchId;

    private String productBatchName;

    /**
     * 马关联主键，
     */
    private String codeRelationId;


}
