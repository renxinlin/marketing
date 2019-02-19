package com.jgw.supercodeplatform.marketing.ms;

import java.util.Date;

/**
 * 数据库对应生码排队实体
 *
 * @author Created by jgw136 on 2018/07/30.
 */
public class CreateCodeQueue {
    private Long sBatchId;
    private String accountId;
    private Long codeNumber;
    private Date applyTime;
    private String paramer;
    private Date createTime;
    private Integer codeType;
    private Long estimatedTime;

    public Long getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Long estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Long getsBatchId() {
        return sBatchId;
    }

    public void setsBatchId(Long sBatchId) {
        this.sBatchId = sBatchId;
    }

    public Long getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(Long codeNumber) {
        this.codeNumber = codeNumber;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getParamer() {
        return paramer;
    }

    public void setParamer(String paramer) {
        this.paramer = paramer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
