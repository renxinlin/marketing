package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 先初始化链
 * 在执行业务
 * @param <T>
 */
@Component
public class AutoFatchChainCompoment<T> {
    @Autowired
     private AutoFatchChainProxy<T> chainProxy;

     public void fireBiz(T data){
         chainProxy.startBiz(data);
     }

    /**
     * 这一步硬编码配置,就不自动处理了
     *
     * @param chains
     */
     public void initchains( AutoFetchChainAbs<T>... chains ){
         int length = chains.length;
         int i = 0;
         AutoFetchChainAbs<T>[] clonechains = chains.clone();
         while (i<length){
             chainProxy.initChain(clonechains[i]);
             i++;
         }
     }

}
