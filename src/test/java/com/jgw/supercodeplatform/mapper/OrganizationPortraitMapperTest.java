package com.jgw.supercodeplatform.mapper;

import java.util.ArrayList;
import java.util.List;

import com.jgw.supercodeplatform.marketing.enums.portrait.PortraitTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class OrganizationPortraitMapperTest {
@Autowired
private OrganizationPortraitMapper dao;
@Autowired
private MarketingMembersWinRecordMapper mWinRecordMapper;

	
	@Test
	public void batchinsert() {
      MarketingOrganizationPortrait m=new MarketingOrganizationPortrait();
      m.setUnitCodeId(1l);
      
      MarketingOrganizationPortrait m2=new MarketingOrganizationPortrait();
      m2.setUnitCodeId(1l);
      
      List<MarketingOrganizationPortrait>list=new ArrayList<MarketingOrganizationPortrait>();
      list.add(m);
      list.add(m2);
      dao.batchInsert(list);
	}
	
	@Test
	public void insert() {
      MarketingOrganizationPortrait m=new MarketingOrganizationPortrait();
      m.setUnitCodeId(1l);
      
      dao.addOrgPortrait(m);
      
	}
	
	@Test
	public void select() {
     List<MarketingOrganizationPortraitListParam> list=dao.getSelectedPortrait("55fbb365680a4fa9bfc8d1628d20deef", PortraitTypeEnum.PORTRAIT.getTypeId());
     List<MarketingUnitcode> list2=dao.getUnselectedPortrait("55fbb365680a4fa9bfc8d1628d20deef", PortraitTypeEnum.PORTRAIT.getTypeId());
     System.out.println(1);
	}
}
