package com.jgw.supercodeplatform.mutIntegral.infrastructure.forkjoin;

import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.pojo.IntegralMessageInfo;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.util.AverageListUtil;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.util.SendUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * RecursiveAction    一个递归无结果的ForkJoinTask（没有返回值）
 * RecursiveTask    一个递归有结果的ForkJoinTask（有返回值）
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class ParallelMessageTask  extends RecursiveAction {
    // 默认的阀值
    private static int THRESHOLD  = 100; // 默认阈值

    private List<IntegralMessageInfo> integralMessageInfoList;
    @Override
    protected void compute() {
        if(CollectionUtils.isEmpty(integralMessageInfoList)){
            return;
        }
        if(integralMessageInfoList.size()<THRESHOLD){
            // 发送短信
            for (IntegralMessageInfo integralMessageInfo:integralMessageInfoList){
                SendUtil sendUtil = SpringContextUtil.getBean(SendUtil.class);
                sendUtil.sendMsg(integralMessageInfo);
            }

        }else {
            // 归并
            int middle = integralMessageInfoList.size()/2;
            List<List<IntegralMessageInfo>> twoList = AverageListUtil.averageAssign(integralMessageInfoList, middle);
            ParallelMessageTask leftTask = new ParallelMessageTask(twoList.get(0));
            ParallelMessageTask rightTask = new ParallelMessageTask(twoList.get(1));
            leftTask.fork();
            rightTask.fork();
        }

    }
}
