package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFetchChainAbs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Primary
public class SystemChainService extends AutoFetchChainAbs {
    /**
     *
     * @param datafromMq
     * @return 通过return 决定执行 ifDoBiz 还是执行ifNotBiz
     */
    @Override
    public boolean shouldProcess(Object datafromMq) {
        return true;
    }

    @Override
    protected void ifDoBiz(Object datafromMq) {
        log.info("==>开始链式处理生码批次与产品绑定{}", JSONObject.toJSONString(datafromMq));
        // stopTransfer(); 执行完毕调用stopTransfer可以终止其他service执行 取决业务
    }

    @Override
    protected void ifNotBiz(Object datafromMq) {

    }
}
