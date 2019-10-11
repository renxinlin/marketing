package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Wheels implements Serializable {

    public Wheels() {
    }

    public Wheels(String organizationId, String organizatioIdName) {
        Asserts.check(!StringUtils.isEmpty(organizationId), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils.isEmpty(organizatioIdName), ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        this.organizationId = organizationId;
        this.organizatioIdName = organizatioIdName;
    }

    private Long id;

    private String title1;

    private String title2;

    private String title3;

    /**
     * 企业logo
     */
    private String logo;

    private Date startTime;

    private Date endTime;

    private String wxErcode;

    /**
     * 公众号图片规格信息
     */
    private String wxErcodeSpecs;

    /**
     * 第三方链接
     */
    private String thirdUrl;

    private String thirdUrlButton;
    /**
     * 本次活动奖品种类
     */
    private Integer prizeNum;


    private String organizationId;


    private String organizatioIdName;


    private Publisher publisher;
    // 前台活动模板id
    private String  templateId;

    public void addPublisher(Publisher publisher) {
        Asserts.check(publisher != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        this.publisher = publisher;
    }


    //  领域服务由外部传入


}
