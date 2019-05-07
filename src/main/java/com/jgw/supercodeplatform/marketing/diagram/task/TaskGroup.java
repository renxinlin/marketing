package com.jgw.supercodeplatform.marketing.diagram.task;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.diagram.task.worker.LoadBalanceJob;
import com.jgw.supercodeplatform.marketing.diagram.task.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 任务线程组:负责处理主线程组分配的任务
 */
@Component
public class TaskGroup {
    /**
     * 目前一个taskgroup不会接到太多的任务
     * 如果后期太多需要定义缓存队列以及在拒绝策略中将task反回到任务池
     */
    private static ExecutorService masterThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private Worker worker;
    @Autowired
    private  LoadBalanceJob loadBalanceJob;
    /**
     * 所以的任务节点根据主节点获取的任务执行相关任务
     * 任务时间维度
     * 近一周（7个点）
     * 近两周（14个点）
     * 近一个月（30个点）
     * 近三个月（以7天为跨度，统计周总量，估计13个点）
     * 近半年/一年（以月份为跨度，统计月总量）
     *
     * 处理成功后存储数据，通知master消除任务
    */
    public  Boolean execute(Task task){
        Future<Boolean> submit = masterThreadPool.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                RestResult finished = loadBalanceJob.doTask(task);
                if(finished.getState() == 200){
                    return true;
                }
                return false;

            }
        });
        // 等待执行结果|| 异步阻塞；也可以通过队列做到异步非阻塞
        Boolean result = false;
        try {
             result = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  result;

    }


}
