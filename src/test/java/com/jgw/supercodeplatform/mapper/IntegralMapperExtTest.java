package com.jgw.supercodeplatform.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.integral.DeliveryAddressMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;

/**
 * 积分测试
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class)
public class IntegralMapperExtTest {

    
    @Autowired
    private DeliveryAddressMapperExt mapper;

    @Autowired
    private IntegralRecordMapperExt integralRecordDao;
    @Test
    public void test() {
    }
    
    @Test
    public void test2() {
//    	 List<IntegralRecord> inRecords=new ArrayList<IntegralRecord>();
//    	 IntegralRecord i1=new IntegralRecord();
//    	 i1.setCodeTypeId("dd");
//    	 
//      	 IntegralRecord i21=new IntegralRecord();
//    	 i21.setCodeTypeId("dd");
//    	 
//    	 inRecords.add(i1);
//    	 inRecords.add(i21);
//    	 
//    	 integralRecordDao.batchInsert(inRecords);
//    	 
//      	 IntegralRecord i3=new IntegralRecord();
//    	 i3.setCodeTypeId("dd");
//    	 integralRecordDao.insert(i3);
    	 
    	List<IntegralRecord>list= integralRecordDao.list(null);
    	System.out.println(list);
    }
    public static void main(String[] args) {
	}
}
