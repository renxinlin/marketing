package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.vo.SerialVo;
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

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/marketing/saleTask")
@Api(tags = "总产品销售额趋势")
public class SaleTaskController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.SALE;
    private static final String SPLIT = "~";
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
        List<Date> dateParams = taskTimeCalculator.getWeek();
        List<String> dateParamsString = taskTimeCalculator.getWeekString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return timePointtask(organizationAllSalePrice, dateParamsString);
    }


    public RestResult twoWeekTask( ) throws SuperCodeException {
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getTwoWeek();
        List<String> dateParamsString = taskTimeCalculator.getTwoWeekString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return timePointtask(organizationAllSalePrice, dateParamsString);
    }

    public RestResult monthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getMonth();
        List<String> dateParamsString = taskTimeCalculator.getMonthString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId, dateParams.get(0), dateParams.get(dateParams.size() - 1));
        return timePointtask(organizationAllSalePrice, dateParamsString);
    }



    public RestResult threeMonthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getThreeMonth();
        List<String> dateParamsString = taskTimeCalculator.getThreeMonthString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId
                ,dateParams.get(0),taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        return timeIntervalWeektask(organizationAllSalePrice, dateParamsString);

    }



    public RestResult halfYearTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getHalfYear();
        List<String> dateParamsString = taskTimeCalculator.getHalfYearString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId
                ,dateParams.get(0),taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        return timeIntervalMonthtask(organizationAllSalePrice, dateParamsString);
    }

    public RestResult yearTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> dateParams = taskTimeCalculator.getYear();
        List<String> dateParamsString = taskTimeCalculator.getYearString();
        // 只有日期，价格数据
        List<IntegralRecord> organizationAllSalePrice = service.getOrganizationAllSalePrice(organizationId
                ,dateParams.get(0),taskTimeCalculator.getYesterday(dateParams.get(dateParams.size() - 1)));
        return timeIntervalMonthtask(organizationAllSalePrice, dateParamsString);      }

    /**
     * 时间区间[月]查询
     * @param organizationAllSalePrice
     * @param dateParamsString
     * @return
     */
    private RestResult timeIntervalMonthtask(List<IntegralRecord> organizationAllSalePrice, List<String> dateParamsString) throws SuperCodeException {
        Map<String, SerialVo> timeVo = new TreeMap<>();
        // 图表数据格式
        if(!CollectionUtils.isEmpty(organizationAllSalePrice)){
            for(String day : dateParamsString){
                SerialVo vo = new SerialVo();
                vo.setTime(day.substring(0,7));
                timeVo.put(day,vo);
            }
            // 移除最后一个数据，最后一个数据的区间已经加载在i-1上
            timeVo.remove(dateParamsString.get(dateParamsString.size()-1));
            // 累加
            for (IntegralRecord record : organizationAllSalePrice){
                for( String day : timeVo.keySet()){
                    // 注册时间在[day,day+7),即[day,day+6]
                    try {
                        if(taskTimeCalculator.inOneMonth(day,record.getCreateDateStr())){
                            timeVo.get(day).add(1);
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new SuperCodeException("比较日期一周内解析异常...");
                    }
                }
            }
        }
        // web style
        if(timeVo.values() == null){
            return  RestResult.success("success", new ArrayList<>());
        }
        return RestResult.success("success", timeVo.values());
    }

    /**
     * 时间区间[周]查询
     * @param organizationAllSalePrice
     * @param dateParamsString
     * @return
     */
    private RestResult timeIntervalWeektask(List<IntegralRecord> organizationAllSalePrice, List<String> dateParamsString) throws SuperCodeException {

        Map<String, SerialVo> timeVo = new TreeMap<>();
        // 图表数据格式
        if(!CollectionUtils.isEmpty(organizationAllSalePrice)){
            for(int i=0;i<dateParamsString.size();i++){
                SerialVo vo = new SerialVo();
                try {
                    // yy-MM-dd
                    vo.setTime(dateParamsString.get(i).substring(2,10)+SPLIT+dateParamsString.get(i+1).substring(2,10));
                } catch (Exception e) {
                    vo.setTime(dateParamsString.get(i));
                }
                timeVo.put(dateParamsString.get(i),vo);
            }
            // 移除最后一个数据，最后一个数据的区间已经加载在i-1上
            timeVo.remove(dateParamsString.get(dateParamsString.size()-1));
            // 累加
            for (IntegralRecord record : organizationAllSalePrice){
                for( String day : timeVo.keySet()){
                    // 注册时间在[day,day+7),即[day,day+6]
                    try {
                        if(taskTimeCalculator.inOneWeek(day,record.getCreateDateStr())){
                            timeVo.get(day).add(1);
                            continue;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new SuperCodeException("比较日期一周内解析异常...");
                    }
                }
            }
        }
        // web style
        if(timeVo.values() == null){
            return  RestResult.success("success", new ArrayList<>());
        }
        return RestResult.success("success", timeVo.values());
    }





    /**
     * 时间点查询
     * @param organizationAllSalePrice
     * @param dateParamsString
     * @return
     */
    private RestResult timePointtask(List<IntegralRecord> organizationAllSalePrice, List<String> dateParamsString) {
        Map<String, SerialVo> timeVo = new TreeMap<>();
        // 图表数据格式
        if(!CollectionUtils.isEmpty(organizationAllSalePrice)){
            for(String day : dateParamsString){
                SerialVo vo = new SerialVo();
                vo.setTime(day);
                timeVo.put(day,vo);
            }
            // 累加
            for (IntegralRecord record : organizationAllSalePrice){
                for( String day : timeVo.keySet()){
                    if(day.equals(record.getCreateDateStr())){
                        timeVo.get(day).add(1);
                        continue;
                    }
                }
            }
        }
        // web style
        if(timeVo.values() == null){
            return  RestResult.success("success", new ArrayList<>());
        }
        return RestResult.success("success", timeVo.values());
    }

    public boolean isFinished() {
        return false;
    }

}
