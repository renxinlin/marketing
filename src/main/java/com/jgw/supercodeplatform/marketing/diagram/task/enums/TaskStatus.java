package com.jgw.supercodeplatform.marketing.diagram.task.enums;


/**
 * 任务状态
 */
public enum TaskStatus {
    /**
     * 处理中
     */
    WORKING("1"),
    /**
     * 待处理
     */
    READING("0");


    private String status;


    TaskStatus(String status) {
        this.status = status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }}
