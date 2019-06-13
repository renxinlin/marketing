package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有活动自动获取的处理方式
 */
public abstract class AutoFetchChainAbs<T> {
    private Logger logger = LoggerFactory.getLogger(AutoFetchChainAbs.class);
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
                logger.error("{}调用 ifDoBiz({})异常{}", this.getClass().getName(), JSONObject.toJSONString(datafromMq),e.getMessage());
            }
        }else {
            try {
                ifNotBiz(datafromMq);
            } catch (Exception e) {
                logger.error("{}调用 ifNotBiz({})异常{}", this.getClass().getName(), JSONObject.toJSONString(datafromMq),e.getMessage());
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
