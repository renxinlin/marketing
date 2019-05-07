package com.jgw.supercodeplatform.marketing.diagram.task.worker;

import com.jgw.supercodeplatform.marketing.diagram.task.Task;

public interface Worker {
    /**
     * 真正工作者
     * @param task 任务
     * @return 结果发给tasker调度，由tasker调度告诉master，被标记的任务可以删除了
     */
    boolean doTask(Task task);
}
