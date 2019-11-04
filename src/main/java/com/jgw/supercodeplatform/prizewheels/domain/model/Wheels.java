package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityStatusConstant;
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
 *     TODO 大转盘回顾：领域实体设计不合理,尤其是大转盘缺少聚合根
 *     TODO 领域服务过多，除去大转盘之外 其他实体大多数存在一定的贫血情况
 *     TODO 实体业务过少 除去addPublisher 基本是校验类业务 其他实体基本没有业务
 *
 *     单一实体集合处理问题：单一实体集合实在领域实体内部处理业务 还是在领域服务中处理 有待商榷
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

    private String  activityStatus;


    public void addPublisher(Publisher publisher) {
        Asserts.check(publisher != null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        this.publisher = publisher;
    }

    public void initOrgInfo(String organizationId, String organizationName) {
        Asserts.check(!StringUtils .isEmpty(organizationId) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils .isEmpty(organizationName) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        this.organizationId = organizationId;
        this.organizatioIdName = organizationName;
    }

    private void checkBase() {
        Asserts.check(!StringUtils .isEmpty(title1) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils .isEmpty(title2) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils .isEmpty(title3) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        Asserts.check(!StringUtils .isEmpty(templateId) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        Asserts.check(!StringUtils .isEmpty(organizationId) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils .isEmpty(organizatioIdName) , ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        Asserts.check(startTime !=null ,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(endTime !=null ,ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        if(activityStatus == null){
            activityStatus =ActivityStatusConstant.UP;
        }
    }
    public void checkWhenUpdate() {
        // 基本校验
        Asserts.check(id != null && id > 0,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        checkBase();

        // TODO 业务校验

    }



    public void checkWhenAdd() {
        checkBase();
    }

    public void checkAcitivyStatusWhenHReward() {

        Date date = new Date();
        Asserts.check(startTime.getTime() <= date.getTime(),"活动未开始");
        Asserts.check(endTime.getTime() > date.getTime(),"活动已结束");
        Asserts.check(ActivityStatusConstant.UP.equals(activityStatus),"活动未启用");
    }


    //  领域服务由外部传入


}
  