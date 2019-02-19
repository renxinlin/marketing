package com.jgw.supercodeplatform.fake.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableUtil {
	
	/**
     * 根据主表名和序列号，获取子表名
     *
     * @param tableName
     * @param number
     * @return
     * @author liujianqiang
     * @data 2018年1月16日
     * 1890283027
     */
    public String getTableName(String tableName, long number) {
        long mod = number % 10000000;//对1000万取模
        long num = number / 10000000;//除以1000万
//    	long mod = number % 10000;//对1000万取模
//    	long num = number / 10000;//除以1000万
        if (mod == 0) {//假如正好整除，返回主表名加除法结果
            return tableName + num;
        } else {//否则，返回主表名加(除法结果+1)
            return tableName + (num + 1);
        }
    	
    	//***************新加的用于测试使用
    	/*long mod = number % 10000;//对1000万取模
        long num = number / 10000;//除以1000万
        if (mod == 0) {//假如正好整除，返回主表名加除法结果
        	
        	String numa = num+"";
        	int len = numa.length();
        	String tableNum = numa.substring(len-2, len);
        	
            return tableName + num;
        } else {//否则，返回主表名加(除法结果+1)  188925
        	String numa = num+"";
        	int len = numa.length();
        	String tableNum = numa.substring(len-2, len);
        	
            return tableName + (num + 1);
        }*/
    	
     	
    }
    
    /**
     * 根据表名返回第几个库
     *
     * @param tableName
     * @return
     * @author liujianqiang
     * @data 2018年5月23日
     */
    public Integer getDataSourceNumber(String tableName) {
        Integer length = tableName.length();
        String lastThree = tableName.substring(length - 3, length - 2);
        if(checkInt(lastThree)){//假如倒数第三个数是int
        	Integer tableNumber = Integer.parseInt(tableName.substring(length - 3, length));
        	if(tableNumber <= 100){//假如是前100张表
        		return 1;
        	}else{
            	return 2;
        	}
        } else {//否则前小于100张表,都在库1中
            return 1;
        }
    	
    	//****************新加的，用于测试使用
    	/*Integer length = tableName.length();
        String lastThree = tableName.substring(length - 2, length - 1);
        if(checkInt(lastThree)){//假如倒数第三个数是int
        	Integer tableNumber = Integer.parseInt(tableName.substring(length - 2, length));
        	if(tableNumber <= 28){//假如是前30张表
        		return 1;
        	}else{
            	return 2;
        	}
        } else {//否则前小于100张表,都在库1中
            return 1;
        }*/
        
    }
    
    /**
     * 验证是否为正整数公共方法
     * 正整数返回true,不是正整数返回false
     *
     * @param number
     * @return
     * @author liujianqiang
     * @data 2018年4月2日
     */
    public boolean checkInt(String number) {
        String regex = "^[1-9]+[0-9]*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
	
}
