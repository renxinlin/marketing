package com.jgw.supercodeplatform.marketing.diagram.task;

import com.jgw.supercodeplatform.marketing.diagram.task.enums.TaskStatus;
import com.jgw.supercodeplatform.marketing.diagram.task.enums.TaskTypeEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class TaskFactory {
    /**
     * 一次产生所有的任务
     * @return
     */
    public List<Task> factory(){
        List<Task> tasks= new ArrayList<>();
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()){

            Task task = new Task();
            // 任务全局编号
            task.setId(UUID.randomUUID().toString().replaceAll("-",""));
            // 任务时间戳，不同机器的时间戳不一致，非绝对时间
            task.setTimeStamp(System.currentTimeMillis());
            task.setParams(null);
            task.setType(taskTypeEnum.getType());
            task.setStatus(TaskStatus.READING.getStatus());
            tasks.add(task);

        }

        return tasks;
    }
}
