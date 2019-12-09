package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 所有活动自动获取的处理方式
 */
@Slf4j
public abstract class AutoFetchChainAbs<T> {
    private AutoFetchChainAbs<T> next;
    /**
     * 是否传播执行; 默认传播
     */
    boolean transfer = true;

    public  void  stopTransfer(){
        transfer = false;
    }


    public AutoFetchChainAbs<T> getNext() {
        return next;
    }

    public void setNext(AutoFetchChainAbs<T> next) {
        this.next = next;
    }

    public  void  boBizAndSend(T datafromMq){
        if( shouldProcess(datafromMq)){
            try {
                ifDoBiz(datafromMq);
            } catch (Exception e) {
                log.warn("{}调用 ifDoBiz({})异常{}", this.getClass().getName(), JSONObject.toJSONString(datafromMq),e.getMessage());
            }
        }else {
            try {
                ifNotBiz(datafromMq);
            } catch (Exception e) {
                log.warn("{}调用 ifNotBiz({})异常{}", this.getClass().getName(), JSONObject.toJSONString(datafromMq),e.getMessage());
            }

        }
        if(next != null && transfer){
            next.boBizAndSend(datafromMq);
        }

    }

    /**
     * 业务判断
     * @param datafromMq
     * @return
     */
    public abstract  boolean shouldProcess(T datafromMq);

    /**
     * 业务处理
     * @param datafromMq
     */
    protected abstract void ifDoBiz(T datafromMq);

    /**
     * 反向业务逻辑补充
     * @param datafromMq
     */
    protected abstract void ifNotBiz(T datafromMq);


}
