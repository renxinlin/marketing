package com.jgw.supercodeplatform.prizewheels.domain.model;

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
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PrizeWheelsOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String organizationId;

    private String organizatioIdName;

    private Date createDate;

    private String createUser;

    private Long createUserId;

    private Integer status;

    private Long prizeRewardId;

    private String address;

    private String content;

    /**
     * 收货人姓名[备用]
     */
    private String receiverName;

    /**
     * 收货手机
     */
    private String receiverMobile;

    /**
     * 用户手机
     */
    private String mobile;


    public void initRealRewardInfo(Long memberId, String mobile,String username, String organizationId, String organizationName) {
        this.mobile = mobile;
        this.createUserId = memberId;
        this.organizatioIdName = organizationName;
        this.organizationId = organizationId;
        this.createDate = new Date();
        this.createUser = username;
    }
}
