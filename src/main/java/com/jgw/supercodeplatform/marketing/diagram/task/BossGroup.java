package com.jgw.supercodeplatform.marketing.diagram.task;

import com.jgw.supercodeplatform.marketing.diagram.task.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动服务时启动startJob
 */
@Component
public class BossGroup {

    /**
     * 线程池资源分开
     */
    private static ExecutorService masterThreadPool = Executors.newFixedThreadPool(3);

    @Autowired
    private TaskGroup taskGroup;

    @Autowired
    private TaskPool taskPool;


    /**
     * 主节点负责获取任务池中的任务并负载发送给任务节点
     * 如果任务节点接收则标记资源池中的相关任务
     *
     * 任务节点处理成功后消除当前任务
     *
     *  任务池中的任务根据配置时间规则进行补充
     *
     *  多个节点的主线程池共同竞争资源保障数据处理的唯一性和高可用
     *
     *
     *  任务的定时是通过定时像任务池中补充任务形成
     */


    public void startJob(){
        //
        while (true){
            // 1获取任务池中的任务
            Task task = taskPool.getTask();

            if(task != null && TaskStatus.READING.getStatus().equals(task.getStatus())) {
                masterThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {

                        // 2通过springcloud负载taskGroup【真正负载的是worker】
                        Boolean finished = taskGroup.execute(task);
                        if(finished){
                            taskPool.finishedTask(task);
                        }
                        // 3删除处理完毕的任务
                    }
                });
            }
            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                // 线程中断异常
                e.printStackTrace();
            }
        }


    }
}
