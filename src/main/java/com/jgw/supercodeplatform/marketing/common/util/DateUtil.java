package com.jgw.supercodeplatform.marketing.common.util;

import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;


public class DateUtil
{
  public static String getTime()
  {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    simpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
    String datesStr = simpleDateFormat.format(Long.valueOf(System.currentTimeMillis()));
    return datesStr;
  }
	public static Date yyyyMMddStrToDate(String dateStr) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern("yyyy-MM-dd");
		return simpleDateFormat.parse(dateStr);
	}
  /*

  
  
  public static String yearMonthFormat(Date date) {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    simpleDateFormat.applyPattern("yyyy-MM");
	    return simpleDateFormat.format(date);
  }
  
  public static String monthDayFormat(Date date) {
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    simpleDateFormat.applyPattern("MM-dd");
	    return simpleDateFormat.format(date);
}

	public static String hourMinuteFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    simpleDateFormat.applyPattern("HH:mm");
	    return simpleDateFormat.format(date);
}
*/	
	public static int getHour(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	    simpleDateFormat.applyPattern("HH");
	    return Integer.valueOf(simpleDateFormat.format(date));
	}
	
	public static String dateFormat(Date date,String pattern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	public static String DateFormat(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String DateFormat(Object date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String dateFormat(String date,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date2 = sdf.parse(date);
		return sdf.format(date2);	
	}

	public static void main(String[] args) throws Exception {
		Date date = new Date();
		Thread.sleep(100);
		System.out.println(getAge(date));
	}
	    //由出生日期获得年龄  
	    public static int getAge(Date birthDay) throws Exception {  
	    	
	        Calendar cal = Calendar.getInstance();  
	  
	        if (cal.before(birthDay)) {  
	            throw new IllegalArgumentException(  
	                    "The birthDay is after Now.It's unbelievable!");
	        }  
	        int yearNow = cal.get(Calendar.YEAR);  
	        int monthNow = cal.get(Calendar.MONTH);  
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
	        cal.setTime(birthDay);  
	  
	        int yearBirth = cal.get(Calendar.YEAR);  
	        int monthBirth = cal.get(Calendar.MONTH);  
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
	  
	        int age = yearNow - yearBirth;  
	  
	        if (monthNow <= monthBirth) {  
	            if (monthNow == monthBirth) {  
	                if (dayOfMonthNow < dayOfMonthBirth) {
                        age--;
                    }
	            }else{  
	                age--;  
	            }  
	        }  
	        return age+1;
	    }
	    
	    
	    public static Date parse(String dateString,String pattern) throws ParseException{
	    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    	return sdf.parse(dateString);
	    	
	    }


	public static SortedSet<PieChartVo> monthFmt(Date monDate) {
		SortedSet<PieChartVo> valueSet = new TreeSet<>();
		Calendar cal = Calendar.getInstance();
		long nowMills = cal.getTimeInMillis();
		if(monDate.getTime() >= cal.getTimeInMillis()) {
            return valueSet;
        }
		cal.setTime(monDate);
		int sorYear = cal.get(Calendar.YEAR);
		int sorMonth = cal.get(Calendar.MONTH) + 1;
		while(cal.getTimeInMillis() <= nowMills) {
			String sorMonthStr = "" + sorMonth;
			if(sorMonth < 10) {
                sorMonthStr = "0" + sorMonth;
            }
			PieChartVo value = new PieChartVo(sorYear+"-"+sorMonthStr, 0L);
			valueSet.add(value);
			cal.add(Calendar.MONTH, 1);
			sorYear = cal.get(Calendar.YEAR);
			sorMonth = cal.get(Calendar.MONTH) + 1;
		}
		return valueSet;
	}

	public static SortedSet<PieChartVo> dayFmt(Date dayDate, Date endDate){
		SortedSet<PieChartVo> valueSet = new TreeSet<>();
		long endMills = endDate.getTime();
		if(dayDate.getTime() > endMills) {
			return valueSet;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(dayDate);
		int sorYear = cal.get(Calendar.YEAR);
		int sorMonth = cal.get(Calendar.MONTH) + 1;
		int sorDay = cal.get(Calendar.DATE);
		while(cal.getTimeInMillis() <= endMills) {
			String sorMonthStr = "" + sorMonth;
			if(sorMonth < 10) {
                sorMonthStr = "0" + sorMonth;
            }
			String sorDayStr = "" + sorDay;
			if(sorDay < 10) {
                sorDayStr = "0" + sorDay;
            }
			PieChartVo value = new PieChartVo(sorYear+"-"+sorMonthStr+"-"+sorDayStr, 0L);
			valueSet.add(value);
			cal.add(Calendar.DATE, 1);
			sorYear = cal.get(Calendar.YEAR);
			sorMonth = cal.get(Calendar.MONTH) + 1;
			sorDay = cal.get(Calendar.DATE);
		}
		return valueSet;
	}

}
