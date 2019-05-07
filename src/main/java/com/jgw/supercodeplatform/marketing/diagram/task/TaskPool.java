package com.jgw.supercodeplatform.marketing.diagram.task;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.diagram.task.enums.TaskStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 任务池，借助令牌桶思想定时补充任务
 * 时间规则交给config
 */
@Component
public class TaskPool {
    private Logger logger = LoggerFactory.getLogger(TaskPool.class);
    @Value("1")
   private long days;

    @Value("market:tasks:task")
    private String marketTaskName;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TaskFactory taskFactory;
    @Autowired
    private ModelMapper modelMapper;
    private static ScheduledExecutorService cronJob = Executors.newScheduledThreadPool(1);

    /**
     * 获取任务
     * @return
     */
    public  Task getTask(){
        // 添加全局锁
        Task waitWork = null;
        List<Object> tasks = redisUtil.getHashValues(marketTaskName);
        for(Object taskObject : tasks){
            Task task = (Task)taskObject;
            if(TaskStatus.READING.getStatus().equals(task.getStatus())){
                waitWork = modelMapper.map(task,Task.class);
                task.setStatus(TaskStatus.WORKING.getStatus());
                redisUtil.hmSet(marketTaskName,task.getId(),JSONObject.toJSONString(task));
                break;
            }
        }
        return waitWork;

    }

    /**
     * 任务完成后删除任务池的任务
     * @param task
     */
    public void finishedTask(Task task){
        // 无需加锁
        redisUtil.deleteHmKey(marketTaskName,task.getId());
    }

    /**
     * cron自定义任务添加
     */
    private void cronPushTaskToTaskPool(){
        cronJob.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                logger.error("[向任务池注入最新任务{}]","date");
                // 获取枚举定义的全部任务
                List<Task> tasks = taskFactory.factory();
                // 解析cron表达式
                // 先直接删除
//                redisUtil.delsHmKey(marketTaskName);
                // TODO 获取原来的
//                redisUtil.getHashValues(marketTaskName);
                for (Task task : tasks){
                    // TODO 一次放入所有任务
                    // redisutil添加list操作
                    redisUtil.hmSet(marketTaskName,task.getId(), JSONObject.toJSONString(task));
                }
                // 如果成功删除原来的

            }
            // TODO 计算延迟，于凌晨计算,覆盖重复的任务
        },0, days, TimeUnit.DAYS);
    }
}
