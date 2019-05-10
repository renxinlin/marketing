package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.vo.CricleVo;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/top6Task")
@Api(tags = "TOP6")

public class Top6TaskController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.TOP6;
    @Autowired
    private TaskTimeCalculator taskTimeCalculator;

    @Autowired
    private IntegralRecordService service;

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
        List<Date> date = taskTimeCalculator.getWeek();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public RestResult twoWeekTask( ) throws SuperCodeException {
        String organizationId = getOrganizationId();
        List<Date> date = taskTimeCalculator.getTwoWeek();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public RestResult monthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> date = taskTimeCalculator.getMonth();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public RestResult threeMonthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> date = taskTimeCalculator.getThreeMonth();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public RestResult halfYearTask( ) throws SuperCodeException{

        String organizationId = getOrganizationId();
        List<Date> date = taskTimeCalculator.getHalfYear();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public RestResult yearTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> date = taskTimeCalculator.getYear();
        List<IntegralRecord> top6Dtos = service.getOrganizationTop6IntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        Integer all = service.getOrganizationAllIntegralProduct(organizationId, date.get(0), date.get(date.size() - 1));
        List<CricleVo> cricleVos = new LinkedList();

        if(!CollectionUtils.isEmpty(top6Dtos)){
            int  sum  = 0;
            for(IntegralRecord dto : top6Dtos){
                // vo处理
                CricleVo vo                 = new CricleVo();
                vo            .setItem(dto.getProductName());
                vo           .setCount(dto.getIntegralNum());
                double percent=dto.getIntegralNum()*1.00/all;
                vo                      .setPercent(percent);
                vo                .setPercentStr(percent+"");
                sum                   +=dto.getIntegralNum();
                cricleVos                           .add(vo);
            }

        }

        return RestResult.success("success",cricleVos);
    }

    public boolean isFinished() {
        return false;
    }

}
