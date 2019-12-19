package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral;

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
public class ProductSendIntegralDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Long id;

    /**
     * 会员
     */
    private Long memberId;

    /**
     * 会员
     */
    private String memberName;

    /**
     * 会员
     */
    private String memberMobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 门店
     */
    private String customerId;

    /**
     * 门店
     */
    private String customerName;

    /**
     * 积分
     */
    private Integer integralNum;

    /**
     * 积分时间
     */
    private Date operationTime;

    /**
     * 操作人
     */
    private String operaterId;

    /**
     * 操作人
     */
    private String operaterName;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 组织名称
     */
    private String organizationName;


}
