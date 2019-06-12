package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AutoFatchChainProxy<T> {
    @Autowired
    @Qualifier("systemChainService")
    private AutoFetchChainAbs<T> chains;




    public void initChain(AutoFetchChainAbs<T> chain){
        if( chains == null){
            chains = chain;
        }
        AutoFetchChainAbs<T> tempChain = chains;
       while(tempChain.getNext() != null){
           tempChain = tempChain.getNext();
        }
        tempChain.setNext(chain);

    }
    public void startBiz(T datafromMq){
        if(chains == null){
            return;
        }
        chains.boBizAndSend(datafromMq);
    }
}
