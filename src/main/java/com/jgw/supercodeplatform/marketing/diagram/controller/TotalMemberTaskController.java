package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.diagram.vo.CricleVo;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/marketing/totalMemberTask")
@Api(tags = "累计会员数")

public class TotalMemberTaskController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.TOTAL_MEMBER;

    @Autowired
    private MarketingMembersService service;

    @Autowired
    private TaskTimeCalculator taskTimeCalculator;
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "header", value = "新平台token--开发联调使用", name = "super-token"),
            @ApiImplicitParam(paramType = "query", value = "1一周,2,3,4,5,6一年,字符串格式，按顺序一周到一年", name = "timeValue")
    })
    @GetMapping("query")
    public RestResult query(String timeValue) throws SuperCodeException {
        if(QueryEnum.WEEK.getStatus().equals(timeValue)){
            return weekTask();
        }
        if(QueryEnum.TWO_WEEK.getStatus().equals(timeValue)){
            return twoWeekTask();
        }
        if(QueryEnum.MONTH.getStatus().equals(timeValue)){
            return monthTask();
        }
        if(QueryEnum.THREE_MONTH.getStatus().equals(timeValue)){
            return threeMonthTask();
        }
        if(QueryEnum.HALF_YEAR.getStatus().equals(timeValue)){
            return halfYearTask();
        }
        if(QueryEnum.YEAR.getStatus().equals(timeValue)){
            return yearTask();
        }


        return RestResult.error("请选择时间范围...");
    }

    public RestResult weekTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getWeek();
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        // 获取所有
        Integer total = service.getAllMarketingMembersCount(conditions);
        // 查询日期内的数据
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, week.get(0), week.get(week.size() - 1));


        return task(registerNumMembers, total);

    }



    public RestResult twoWeekTask( ) throws SuperCodeException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getTwoWeek();
        // 查询日期内的数据
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        Integer total = service.getAllMarketingMembersCount(conditions);

        return task(registerNumMembers, total);

    }

    public RestResult monthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getMonth();
        List<String> weekString = taskTimeCalculator.getMonthString();
        // 查询日期内的数据
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        Integer total = service.getAllMarketingMembersCount(conditions);
        return task(registerNumMembers, total);
   }

    public RestResult threeMonthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getThreeMonth();
         // 查询日期内的数据
        // TODO 优化，改成求和函数计算
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        Integer total = service.getAllMarketingMembersCount(conditions);

        return task(registerNumMembers, total);
    }

    public RestResult halfYearTask( ) throws SuperCodeException{

        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getHalfYear();
         // 查询日期内的数据
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        Integer total = service.getAllMarketingMembersCount(conditions);
        return task(registerNumMembers, total);
    }

    public RestResult yearTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getYear();
         // 查询日期内的数据
        List<MarketingMembers> registerNumMembers = service.getOrganizationAllMemberWithDate(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        // 查询组织下的会员总量
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("organizationId",organizationId);
        Integer total = service.getAllMarketingMembersCount(conditions);
        return task(registerNumMembers, total);
    }
    private RestResult task(List<MarketingMembers> registerNumMembers, Integer total) {
        int newMemberTotal = CollectionUtils.isEmpty(registerNumMembers)  ? 0:registerNumMembers.size() ;


        // 数据格式
        // data = [{item: '事例一',count: 40,percent: 0.4}, {item: '事例二',count: 21,percent: 0.21}];
        List result = new ArrayList();
        // 新用户
        CricleVo newMembers = new CricleVo();
        newMembers.setItem("新会员");
        newMembers.setCount(newMemberTotal);
        // 老用户
        CricleVo oldMembers = new CricleVo();
        oldMembers.setItem("老会员");
        // 所有会员减去新会员
        oldMembers.setCount(total-newMemberTotal);
        result.add(newMembers);
        result.add(oldMembers);

        // web 格式
        Map resultMap = new HashMap();
        Map totalMap = new HashMap();
        resultMap.put("total",total);

        resultMap.put("data",result);
        resultMap.put("other",totalMap);



        return RestResult.success("success",resultMap);
    }
    public boolean isFinished() {
        return false;
    }

}
