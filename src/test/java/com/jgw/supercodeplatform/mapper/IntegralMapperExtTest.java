package com.jgw.supercodeplatform.mapper;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.DeliveryAddressMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.integral.DeliveryAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 积分测试
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class)
public class IntegralMapperExtTest {


    @Autowired
    private DeliveryAddressMapperExt mapper;

    @Test
    public void test() {
        // 积分dao层功能测试：测试d积分dao层是否正常使用
        DeliveryAddress pojo = new DeliveryAddress();
        pojo.setCity("000");
        mapper.insertSelective(pojo);

//		maSetMapper.addCodeTotalNum(1L, 2L);
    }
}
