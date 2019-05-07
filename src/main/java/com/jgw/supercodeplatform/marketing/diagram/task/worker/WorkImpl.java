package com.jgw.supercodeplatform.marketing.diagram.task.worker;

import com.jgw.supercodeplatform.marketing.diagram.task.Task;
import com.jgw.supercodeplatform.marketing.diagram.task.enums.TaskTypeEnum;
import org.springframework.stereotype.Service;

/**
 * 营销数据统计任务
 */
@Service("marketWorker")
public class WorkImpl implements Worker {
    @Override
    public boolean doTask(Task task) {
        if(TaskTypeEnum.MEMBER_MAP.getType().equals(task.getType())){
            return  doMemberMap(task);
        }
        if(TaskTypeEnum.MEMBER_PORTRAIT.getType().equals(task.getType())){
            return  doMemberPortrait(task);

        }
        if(TaskTypeEnum.REGISTER_NUM.getType().equals(task.getType())){
            return  doRegisterNum(task);

        }
        if(TaskTypeEnum.SALE.getType().equals(task.getType())){
            return  doSale(task);

        }
        if(TaskTypeEnum.TOTAL_MEMBER.getType().equals(task.getType())){
            return  doTotalMember(task);

        }
        if(TaskTypeEnum.TOP6.getType().equals(task.getType())){
            return  doTop6(task);

        }
        if(TaskTypeEnum.STATISTICS.getType().equals(task.getType())){
            return  doStatistics(task);

        }

        return false;
    }

    private boolean doStatistics(Task task) {
        return false;

    }

    private boolean doTop6(Task task) {
        return false;

    }

    private boolean doTotalMember(Task task) {
        return false;

    }

    private boolean doSale(Task task) {

        return false;

    }

    private boolean doRegisterNum(Task task) {
        return false;

    }

    private boolean doMemberPortrait(Task task) {
        return false;

    }

    private boolean doMemberMap(Task task) {

            return false;
    }
}
