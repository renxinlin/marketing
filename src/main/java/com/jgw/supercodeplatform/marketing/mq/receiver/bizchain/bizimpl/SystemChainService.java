package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFetchChainAbs;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class SystemChainService extends AutoFetchChainAbs {
    @Override
    public boolean shouldProcess(Object datafromMq) {
        return true;
    }

    @Override
    protected void ifDoBiz(Object datafromMq) {
        // do nothing with biz
        System.out.println("===============================start chain biz ========================");
    }

    @Override
    protected void ifNotBiz(Object datafromMq) {

    }
}
