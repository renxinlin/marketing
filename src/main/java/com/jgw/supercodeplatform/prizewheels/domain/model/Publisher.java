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
public class Publisher implements Serializable {




    public Publisher (){}
    // 以下大转盘发布人域


    private String updateUserId;

    private String updateUserName;

    private Date updateDate;

    private Date createDate;

    private String createUser;

    private String createUserId;

    public void initUserInfoWhenFirstPublish(String createUserId,String createUser){
        Asserts.check(!StringUtils.isEmpty(createUserId), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils.isEmpty(createUser), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        createDate = new Date();
        this.createUser = createUser;
        this.createUserId =createUserId;

    }


    public void initUserInfo(String updateUserId,String updateUserName){
        Asserts.check(!StringUtils.isEmpty(updateUserId), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        Asserts.check(!StringUtils.isEmpty(updateUserName), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        updateDate = new Date();
        this.updateUserId = updateUserId;
        this.updateUserName =updateUserName;

    }




}
