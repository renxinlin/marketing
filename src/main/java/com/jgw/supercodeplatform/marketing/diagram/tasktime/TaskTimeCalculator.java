package com.jgw.supercodeplatform.marketing.diagram.tasktime;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
    SimpleDateFormat todayZeroFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

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
        return startDate.getTime() <= vauleDate.getTime() && sevenAfter.getTime() > vauleDate.getTime();
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
        // 按30天
//        Date startDate = format.parse(startDateStr);
//        Date vauleDate = format.parse(value);
//
//        calendar.setTime(startDate);//把当前时间赋给日历
//        calendar.add(Calendar.MONTH, 1);  //设置为30天后
//        Date monthAfter = calendar.getTime();   //得到30天后的时间
//        if (startDate.getTime() <= vauleDate.getTime() && monthAfter.getTime() > vauleDate.getTime()) {
//            return true;
//        } else {
//            return false;
//
//        }
        // 按自然月
        String yyyyMMStr = startDateStr.substring(0, 7);
        String yyyyMMStr1 = value.substring(0, 7);
        return yyyyMMStr.equals(yyyyMMStr1);

    }

    public List getWeek(){


        List<Date> dates = new LinkedList();
        List<String> weekString = getWeekString();
        for(String dateStr : weekString){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
        List<Date> dates = new LinkedList();
        List<String> weekString = getTwoWeekString();
        for(String dateStr : weekString){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
        List<Date> dates = new LinkedList();
        List<String> weekString = getMonthString();
        for(String dateStr : weekString){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
        List dates = new LinkedList();
        List<String> dateStrs = getThreeMonthString();
        for(String dateStr : dateStrs){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
//        Date current = new Date();
//
//        List dates = new LinkedList();
//        calendar.setTime(current);
//        calendar.add(Calendar.DATE, -1);
//        // 先获取到产品定义的三个月，在从三个月前的那一天+7的方式标注时间戳到当前
//        calendar.add(Calendar.MONTH, -3);
//        Date threeMonthAgo = calendar.getTime();
//        dates.add(threeMonthAgo);
//
//        // 按x轴添加
//        while (true) {
//            calendar.add(Calendar.DATE, 7);
//            Date date = calendar.getTime();
//            if(date.after(current)){
//                break;
//            }
//            dates.add(date);
//        }
//        dates.add(current);
//        return dates;
    }


    /**
     *
     * 近三个月（以7天为跨度，统计周总量，估计13个点）
     * 注意最后一周的统计往往不满一周！！！
     * @return
     */
    public List getThreeMonthString(){
        Date current = new Date();
        Set dates = new TreeSet<>();
//        List dates = new LinkedList();
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
        dates.add(format.format(current));
        ArrayList lastDate = new ArrayList(dates);
        return lastDate;
    }




    /**
     * 以月份为跨度，统计月总量，6个点
     * @return
     */
    public List getHalfYear(){
        List dates = new LinkedList();
        List<String> dateStrs = getHalfYearString();
        for(String dateStr : dateStrs){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
//        List dates = new LinkedList();
//        for(String date :getHalfYearString()){
//            try {
//                dates.add(format.parse(date));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return dates;

    }

    /**
     * 以月份为跨度，统计月总量，6个点
     * @return
     */
    public List<String> getHalfYearString(){
//        List dates = new LinkedList();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, -1);
//        // 按x轴添加
//        calendar.add(Calendar.MONTH, -7);
//
//        for(int i=-7;i<0;i++){
//            calendar.add(Calendar.MONTH, 1);
//            Date date = calendar.getTime();
//            dates.add(format.format(date));
//        }
//        return dates;



        // 自然月
        List dates= new LinkedList();
        Date date = new Date();
        calendar.setTime(date);
        //按格式输出
        calendar.add(Calendar.MONTH, -5);

        for(int i = 0 ;i<6;i++){
            String time2 = sdf.format(calendar.getTime()); // 本月第一天
            dates.add(time2);
            calendar.add(Calendar.MONTH, 1);
        }
        dates.add(format.format(date));

        return dates;
    }



    /**
     * 以月份为跨度，统计月总量
     * @return
     */
    public List getYear(){
        List dates = new LinkedList();
        List<String> dateStrs = getYearString();
        for(String dateStr : dateStrs){
            try {
                dates.add(todayZeroFormat.parse(dateStr+" 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dates;
//        List dates = new LinkedList();
//        for(String date :getYearString()){
//            try {
//                dates.add(format.parse(date));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//        return dates;
    }


    /**
     * 以月份为跨度，统计月总量
     * @return
     */
    public List<String> getYearString(){
//        List dates = new LinkedList();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DATE, -1);
//        // 按x轴添加
//        calendar.add(Calendar.MONTH, -13);
//        for(int i=-13;i<0;i++) {
//            calendar.add(Calendar.MONTH, 1);
//            Date date = calendar.getTime();
//            dates.add(format.format(date));
//        }
//        return dates;


        // 自然月
        //["2019-05-16","2019-05-01","2019-04-01","2019-03-01","2019-02-01",
        // "2019-01-01","2018-12-01","2018-11-01","2018-10-01","2018-09-01","2018-08-01","2018-07-01","2018-06-01"]
        List dates= new LinkedList();
        Date date = new Date();
        calendar.setTime(date);
        //按格式输出
        calendar.add(Calendar.MONTH, -11);
        for(int i = 0 ;i<12;i++){
            String time2 = sdf.format(calendar.getTime()); //上月第一天
            dates.add(time2);
            calendar.add(Calendar.MONTH, 1);

        }
        dates.add(format.format(date));

        return dates;
    }


    public  Date getYesterday(Date date){
        String yesterday = getYesterdayStr(date);
        Date redate = null;
        try {
            redate = format.parse(yesterday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return redate;
    }

    public  String getNextdayStr(Date date){
        calendar.setTime(date);
        calendar.add(Calendar.DATE,   1);
        return format.format(calendar.getTime());
    }

    public  Date getNextDay(Date date){
        String nextDay = getNextdayStr(date);
        Date redate = null;
        try {
            redate = format.parse(nextDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return redate;
    }


    public  String getYesterdayStr(Date date){
        calendar.setTime(date);
        calendar.add(Calendar.DATE,   -1);
        return format.format(calendar.getTime());
    }

    public  String getYesterdayStr(String yyyyMMddDate){
        Date parse = null;
        try {
            parse = format.parse(yyyyMMddDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar   cal   =   Calendar.getInstance();
        cal.setTime(parse);
        cal.add(Calendar.DATE,   -1);
        return format.format(cal.getTime());
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
        System.out.println("================inoneweek===============");
        // ,"2019-04-27","2019-05-04",
        System.out.println(t.inOneWeek("2019-04-27","2019-04-27"));
        System.out.println(t.inOneWeek("2019-04-27","2019-04-28"));
        System.out.println(t.inOneWeek("2019-04-27","2019-04-29"));
        System.out.println(t.inOneWeek("2019-04-27","2019-04-30"));
        System.out.println(t.inOneWeek("2019-04-27","2019-05-01"));
        System.out.println(t.inOneWeek("2019-04-27","2019-05-02"));
        System.out.println(t.inOneWeek("2019-04-27","2019-05-03"));
        System.out.println(t.inOneWeek("2019-04-27","2019-05-04"));
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


        System.out.println(t.inOneMonth("2019-02-28","2019-03-01"));
        System.out.println(t.inOneMonth("2019-02-25","2019-02-25"));
        System.out.println(t.inOneMonth("2019-02-25","2019-02-26"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-24"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-25"));
        System.out.println(t.inOneMonth("2019-02-25","2019-03-26"));




//        5 4 3 2 1 12 11 10  9 8 7 6
        System.out.println(JSONObject.toJSONString(t.getHalfYear()));
        System.out.println(JSONObject.toJSONString(t.getYear()));
        String startDateStr="2019-02-25";
        String yyyyMMStr = startDateStr.substring(2, 7);
        String yyyyMMStr1 = startDateStr.substring(0, 7);
        System.out.println(yyyyMMStr);

    }



}
