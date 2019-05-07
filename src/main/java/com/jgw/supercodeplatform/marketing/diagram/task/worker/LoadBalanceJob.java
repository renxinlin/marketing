package com.jgw.supercodeplatform.marketing.diagram.task.worker;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.diagram.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/diagram")
public class LoadBalanceJob {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Worker worker;

    @RequestMapping("/task")
    public RestResult doTask(Task task) {
        // 负载
        restTemplate.postForObject("servicename/taskImpl",task,RestResult.class);
        return RestResult.success();
    }






    @RequestMapping("/taskImpl")
    public RestResult taskImpl(Task task) {
        // 负载
        worker.doTask(task);
        return RestResult.success();
    }


}
