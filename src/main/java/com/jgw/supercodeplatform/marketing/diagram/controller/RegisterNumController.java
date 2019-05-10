package com.jgw.supercodeplatform.marketing.diagram.controller;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.enums.TaskTypeEnum;
import com.jgw.supercodeplatform.marketing.diagram.tasktime.TaskTimeCalculator;
import com.jgw.supercodeplatform.marketing.diagram.vo.SerialVo;
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

import java.text.ParseException;
import java.util.*;

/**
 * 前三个是点计算
 * 后三个是区间计算
 */
@RestController
@RequestMapping("/registerNumTask")
@Api(tags = "招募会员海报注册数")
public class RegisterNumController extends CommonUtil {
    /**
     * 任务标志
     */
    private static final Enum type = TaskTypeEnum.REGISTER_NUM;
    @Autowired
    private TaskTimeCalculator taskTimeCalculator;


    @Autowired
    private MarketingMembersService service;
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

    /**
     * 按照天显示
     * @return
     * @throws SuperCodeException
     */
    private RestResult weekTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> week = taskTimeCalculator.getWeek();
        List<String> weekString = taskTimeCalculator.getWeekString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, week.get(0), week.get(week.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        SerialVo first = new SerialVo();
        first.setTime(weekString.get(0));
        SerialVo two   = new SerialVo();
        two.setTime(weekString.get(1));
        SerialVo three = new SerialVo();
        three.setTime(weekString.get(2));
        SerialVo four  = new SerialVo();
        four.setTime(weekString.get(3));
        SerialVo five  = new SerialVo();
        five.setTime(weekString.get(4));
        SerialVo six   = new SerialVo();
        six.setTime(weekString.get(5));
        SerialVo seven = new SerialVo();
        seven.setTime(weekString.get(6));
        if(!CollectionUtils.isEmpty(registerNumMembers)){
            for (MarketingMembers registerNumMember : registerNumMembers){
                if(weekString.get(0).equals(registerNumMember.getCreateDate())){
                    first.add(1);
                }
                if(weekString.get(1).equals(registerNumMember.getCreateDate())){
                    two.add(1);
                }
                if(weekString.get(2).equals(registerNumMember.getCreateDate())){
                    three.add(1);
                }
                if(weekString.get(3).equals(registerNumMember.getCreateDate())){
                    four.add(1);
                }
                if(weekString.get(4).equals(registerNumMember.getCreateDate())){
                    five.add(1);
                }
                if(weekString.get(5).equals(registerNumMember.getCreateDate())){
                    six.add(1);

                }
                if(weekString.get(6).equals(registerNumMember.getCreateDate())){
                    seven.add(1);
                }
            }
        }
        List result = new LinkedList();
        result.add(first);
        result.add(two);
        result.add(three);
        result.add(four);
        result.add(five);
        result.add(six);
        result.add(seven);


        return RestResult.success("success",result);
    }
    /**
     * 按照天显示
     * @return
     * @throws SuperCodeException
     */
    public RestResult twoWeekTask( ) throws SuperCodeException {
        String organizationId = getOrganizationId();
        List<Date> twoWeek = taskTimeCalculator.getTwoWeek();
        List<String> twoWeekString = taskTimeCalculator.getTwoWeekString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, twoWeek.get(0), twoWeek.get(twoWeek.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        Map<String, SerialVo> twoWeekVo = new TreeMap<>();
        for(String day : twoWeekString){
            SerialVo vo = new SerialVo();
            vo.setTime(day);
            twoWeekVo.put(day,vo);
        }

        // 累加
        for (MarketingMembers registerNumMember : registerNumMembers){
            for( String day : twoWeekVo.keySet()){
                if(day.equals(registerNumMember.getCreateDate())){
                    twoWeekVo.get(day).add(1);
                    continue;
                }
            }
        }
        return RestResult.success("success",twoWeekVo.values());
    }

    /**
     * 按照天显示
     * @return
     * @throws SuperCodeException
     */
    public RestResult monthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> month = taskTimeCalculator.getMonth();
        List<String> monthString = taskTimeCalculator.getMonthString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, month.get(0), month.get(month.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        Map<String, SerialVo> monthVo = new TreeMap<>();
        for(String day : monthString){
            SerialVo vo = new SerialVo();
            vo.setTime(day);
            monthVo.put(day,vo);
        }

        // 累加
        for (MarketingMembers registerNumMember : registerNumMembers){
            for( String day : monthVo.keySet()){
                if(day.equals(registerNumMember.getCreateDate())){
                    // 会不会有意外导致异常？
                    // TODO monthVo.get(registerNumMember.getCreateDate()).add(1);
                    monthVo.get(day).add(1);
                    continue;
                }
            }
        }
        return RestResult.success("success",monthVo.values());
    }

    /**
     * 按照周显示
     * @return
     * @throws SuperCodeException
     */
    public RestResult threeMonthTask( ) throws SuperCodeException{
        String organizationId = getOrganizationId();
        List<Date> threeMonth = taskTimeCalculator.getThreeMonth();
        List<String> threeMonthString = taskTimeCalculator.getThreeMonthString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, threeMonth.get(0), threeMonth.get(threeMonth.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        Map<String, SerialVo> threeMonthVo = new TreeMap<>();
        for(String weekDayPoint : threeMonthString){
            SerialVo vo = new SerialVo();
            // 数据value存储的是threeMonthString[i]到threeMonthString[i+1]的和
            // 其中最后一个区间<=7天
            vo.setTime(weekDayPoint);
            threeMonthVo.put(weekDayPoint,vo);
        }
        // 移除最后一个数据，最后一个数据的区间已经加载在i-1上
        threeMonthVo.remove(threeMonthString.get(threeMonthString.size()-1));


        // 累加
        for (MarketingMembers registerNumMember : registerNumMembers){
            for( String day : threeMonthVo.keySet()){
                // 注册时间在[day,day+7),即[day,day+6]
                try {
                    if(taskTimeCalculator.inOneWeek(day,registerNumMember.getCreateDate())){
                        threeMonthVo.get(day).add(1);
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new SuperCodeException("比较日期一周内解析异常...");
                }
            }
        }
        return RestResult.success("success",threeMonthVo.values());
    }
    /**
     * 按照月显示
     * @return
     * @throws SuperCodeException
     */
    public RestResult halfYearTask( ) throws SuperCodeException{

        String organizationId = getOrganizationId();
        List<Date> halfYear = taskTimeCalculator.getHalfYear();
        List<String> halfYearString = taskTimeCalculator.getHalfYearString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, halfYear.get(0), halfYear.get(halfYear.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        Map<String, SerialVo> halfYearVo = new TreeMap<>();
        for(String weekDayPoint : halfYearString){
            SerialVo vo = new SerialVo();
            // 数据value存储的是threeMonthString[i]到threeMonthString[i+1]的和
            // 其中最后一个区间<=7天
            vo.setTime(weekDayPoint);
            halfYearVo.put(weekDayPoint,vo);
        }
        // 移除最后一个数据，最后一个数据的区间已经加载在i-1上
        halfYearVo.remove(halfYearString.get(halfYearString.size()-1));


        // 累加
        for (MarketingMembers registerNumMember : registerNumMembers){
            for( String day : halfYearVo.keySet()){
                // 注册时间在[day,day+7),即[day,day+6]
                try {
                    if(taskTimeCalculator.inOneMonth(day,registerNumMember.getCreateDate())){
                        halfYearVo.get(day).add(1);
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new SuperCodeException("比较日期一月内解析异常...");
                }
            }
        }
        return RestResult.success("success",halfYearVo.values());
    }
    /**
     * 按照月显示
     * @return
     * @throws SuperCodeException
     */
    public RestResult yearTask( ) throws SuperCodeException{

        String organizationId = getOrganizationId();
        List<Date> year = taskTimeCalculator.getYear();
        List<String> yearString = taskTimeCalculator.getYearString();
        List<MarketingMembers> registerNumMembers = service.getRegisterNum(organizationId, year.get(0), year.get(year.size() - 1));

        // 图表数据格式
        //       data  =  [{year:  '1991',value:  3},{year:  '1999',value:  13}];
        Map<String, SerialVo> yearVo = new TreeMap<>();
        for(String weekDayPoint : yearString){
            SerialVo vo = new SerialVo();
            // 数据value存储的是threeMonthString[i]到threeMonthString[i+1]的和
            // 其中最后一个区间<=7天
            vo.setTime(weekDayPoint);
            yearVo.put(weekDayPoint,vo);
        }
        // 移除最后一个数据，最后一个数据的区间已经加载在i-1上
        yearVo.remove(yearString.get(yearString.size()-1));


        // 累加
        for (MarketingMembers registerNumMember : registerNumMembers){
            for( String day : yearVo.keySet()){
                // 注册时间在[day,day+7),即[day,day+6]
                try {
                    if(taskTimeCalculator.inOneMonth(day,registerNumMember.getCreateDate())){
                        yearVo.get(day).add(1);
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new SuperCodeException("比较日期一月内解析异常...");
                }
            }
        }
        return RestResult.success("success",yearVo.values());
    }

    public boolean isFinished() {
        return false;
    }

}
