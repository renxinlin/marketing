package com.jgw.supercodeplatform.marketing.diagram.tasktime;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 获取任务的时间维度
 */
@Component
public class TaskTimeCalculator {
    /**
     * 截止时间，今天凌晨
     * 近一周（7个点）
     * 近两周（14个点）
     * 近一个月（30个点）
     * 近三个月（以7天为跨度，统计周总量，估计13个点）
     * 近半年/一年（以月份为跨度，统计月总量）
     *
     * 处理成功后存储数据，通知master消除任务
     */
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 判断是否一周内
     * @param startDateStr
     * @param value
     * @return
     * @throws ParseException
     */
    public boolean inOneWeek(String startDateStr,String value) throws ParseException {
        if(StringUtils.isBlank(value) || StringUtils.isBlank(startDateStr) ){
            return false;
        }
        Date startDate = format.parse(startDateStr);
        Date vauleDate = format.parse(value);

        calendar.setTime(startDate);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, 7);  //设置为7天后
        Date sevenAfter = calendar.getTime();   //得到7天后的时间
        if (startDate.getTime() <= vauleDate.getTime() && sevenAfter.getTime() > vauleDate.getTime()) {
            return true;
        } else {
            return false;

        }
    }

    /**
     * 当前号的定义是当前X号 ~ 下个月X号-1
     * @param startDateStr yyyy-MM-dd
     * @param value yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public boolean inOneMonth(String startDateStr,String value) throws ParseException {
        if(StringUtils.isBlank(value) || StringUtils.isBlank(startDateStr) ){
            return false;
        }
        Date startDate = format.parse(startDateStr);
        Date vauleDate = format.parse(value);

        calendar.setTime(startDate);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, 1);  //设置为30天后
        Date monthAfter = calendar.getTime();   //得到30天后的时间
        if (startDate.getTime() <= vauleDate.getTime() && monthAfter.getTime() > vauleDate.getTime()) {
            return true;
        } else {
            return false;

        }
    }

    public List getWeek(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -8);

        for(int i=-7;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }
        return dates;
    }

    public List getWeekString(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -8);

        for(int i=-7;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        return dates;
    }


    /**
     * 14个点
     * @return
     */
    public List getTwoWeek(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        // 按x轴添加
        calendar.add(Calendar.DATE, -15);

        for(int i=-14;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }
        return dates;
    }


    /**
     * 14个点
     * @return
     */
    public List getTwoWeekString(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        // 按x轴添加
        calendar.add(Calendar.DATE, -15);

        for(int i=-14;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        return dates;
    }

    /**
     * 30个点
     * @return
     */
    public List getMonth(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        // 按x轴添加
        calendar.add(Calendar.DATE, -31);

        for(int i=-30;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }
        return dates;
    }


    /**
     * 30个点
     * @return
     */
    public List getMonthString(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        // 按x轴添加
        calendar.add(Calendar.DATE, -31);

        for(int i=-30;i<0;i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        return dates;
    }


    /**
     *
     * 近三个月（以7天为跨度，统计周总量，估计13个点）
     * 注意最后一周的统计往往不满一周！！！
     * @return
     */
    public List getThreeMonth(){
        Date current = new Date();

        List dates = new LinkedList();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, -1);
        // 先获取到产品定义的三个月，在从三个月前的那一天+7的方式标注时间戳到当前
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthAgo = calendar.getTime();
        dates.add(threeMonthAgo);

        // 按x轴添加
        while (true) {
            calendar.add(Calendar.DATE, 7);
            Date date = calendar.getTime();
            if(date.after(current)){
                break;
            }
            dates.add(date);
        }
        calendar.setTime(current);
        calendar.add(Calendar.DATE, -1);
        Date yestoday = calendar.getTime();
        dates.add(yestoday);
        return dates;
    }


    /**
     *
     * 近三个月（以7天为跨度，统计周总量，估计13个点）
     * 注意最后一周的统计往往不满一周！！！
     * @return
     */
    public List getThreeMonthString(){
        Date current = new Date();

        List dates = new LinkedList();
        calendar.setTime(current);
        calendar.add(Calendar.DATE, -1);
        // 先获取到产品定义的三个月，在从三个月前的那一天+7的方式标注时间戳到当前
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthAgo = calendar.getTime();
        dates.add(format.format(threeMonthAgo));

        // 按x轴添加
        while (true) {
            calendar.add(Calendar.DATE, 7);
            Date date = calendar.getTime();
            if(date.after(current)){
                break;
            }
            dates.add(format.format(date));
        }
        calendar.setTime(current);
        calendar.add(Calendar.DATE, -1);
        Date yestoday = calendar.getTime();

        dates.add(format.format(yestoday));

        return dates;
    }




    /**
     * 以月份为跨度，统计月总量，6个点
     * @return
     */
    public List getHalfYear(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        // 按x轴添加
        calendar.add(Calendar.MONTH, -7);
        for(int i=-7;i<0;i++){
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }
        return dates;
    }

    /**
     * 以月份为跨度，统计月总量，6个点
     * @return
     */
    public List getHalfYearString(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        // 按x轴添加
        calendar.add(Calendar.MONTH, -7);

        for(int i=-7;i<0;i++){
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        return dates;
    }



    /**
     * 以月份为跨度，统计月总量
     * @return
     */
    public List getYear(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        // 按x轴添加
        calendar.add(Calendar.MONTH, -13);
        for(int i=-13;i<0;i++) {
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }
        return dates;
    }


    /**
     * 以月份为跨度，统计月总量
     * @return
     */
    public List getYearString(){
        List dates = new LinkedList();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        // 按x轴添加
        calendar.add(Calendar.MONTH, -13);
        for(int i=-13;i<0;i++) {
            calendar.add(Calendar.MONTH, 1);
            Date date = calendar.getTime();
            dates.add(format.format(date));
        }
        return dates;
    }

    /**
     * 测试产品需求X轴;
     * 数据已经符合格式
     * @param args
     */
    public static void main(String[] args)throws Exception {
        TaskTimeCalculator t = new TaskTimeCalculator();
        System.out.println(JSONObject.toJSONString(t.getWeekString()));
        System.out.println(JSONObject.toJSONString(t.getTwoWeekString()));
        System.out.println(JSONObject.toJSONString(t.getMonthString()));
        System.out.println(JSONObject.toJSONString(t.getThreeMonthString()));
        System.out.println(JSONObject.toJSONString(t.getHalfYearString()));
        System.out.println(JSONObject.toJSONString(t.getYearString()));
        // 是否一周内测试正常
        System.out.println(t.inOneWeek("2019-04-25","2019-04-24"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-25"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-26"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-27"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-28"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-29"));
        System.out.println(t.inOneWeek("2019-04-25","2019-04-30"));
        System.out.println(t.inOneWeek("2019-04-25","2019-05-01"));
        System.out.println(t.inOneWeek("2019-04-25","2019-05-02"));
        System.out.println("================================================");

        System.out.println(t.inOneMonth("2019-04-25","2019-04-24"));
        System.out.println(t.inOneMonth("2019-04-25","2019-04-25"));
        System.out.println(t.inOneMonth("2019-04-25","2019-04-26"));
        System.out.println(t.inOneMonth("2019-04-25","2019-05-24"));
        System.out.println(t.inOneMonth("2019-04-25","2019-05-25"));
        System.out.println(t.inOneMonth("2019-04-25","2019-05-26"));
        System.out.println("================================================");


        System.out.println(t.inOneMonth("2019-05-25","2019-05-24"));
        System.out.println(t.inOneMonth("2019-05-25","2019-05-25"));
        System.out.println(t.inOneMonth("2019-05-25","2019-05-26"));
        System.out.println(t.inOneMonth("2019-05-25","2019-06-24"));
        System.out.println(t.inOneMonth("2019-05-25","2019-06-25"));
        System.out.println(t.inOneMonth("2019-05-25","2019-06-26"));
        System.out.println("================================================");


        System.out.println(t.inOneMonth("2019-01-25","2019-01-24"));
        System.out.println(t.inOneMonth("2019-01-25","2019-01-25"));
        System.out.println(t.inOneMonth("2019-01-25","2019-01-26"));
        System.out.println(t.inOneMonth("2019-01-25","2019-02-24"));
        System.out.println(t.inOneMonth("2019-01-25","2019-02-25"));
        System.out.println(t.inOneMonth("2019-01-25","2019-02-26"));


        System.out.println("================================================");


        System.out.println(t.inOneMonth("2019-02-25","2019-02-24"));
        System.out.println(t.inOneMonth("2019-02-25","2019-02-25"));
        System.out.println(t.inOneMonth("2019-02-25","2019-02-26"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-24"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-25"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-26"));
     ;
    }
}