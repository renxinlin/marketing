package com.jgw.supercodeplatform.marketing.diagram.task;

import lombok.Data;

import java.util.Date;

/**
 * 任务对象
 */
@Data
public class Task<T> {
    /**
     * 任务ID
     */
    private String id;
    /**
     * 任务类型: 对应任务枚举
     */
    private String type;

    /**
     * 任务时间戳
     */
    private long timeStamp;

    /**
     * 扩展任务携带的字段
     */
    private T params;

    /**
     * 任务状态:0待处理,1待完成
     */
    private String status;

}
