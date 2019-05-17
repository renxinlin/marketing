package com.jgw.supercodeplatform.marketing.service.user;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.*;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.CustomerInfo;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.SalerLoginParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.enums.market.SaleUserStatus;
import com.jgw.supercodeplatform.marketing.enums.portrait.PortraitTypeEnum;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MarketingSaleMemberService extends AbstractPageService<MarketingMembersListParam> {
	protected static Logger logger = LoggerFactory.getLogger(MarketingSaleMemberService.class);

	private String SHORT_MSG = "亲爱的【注册人姓名】，您已通过审核，可登录红包中心https:xxxxxxx";
	@Autowired
	private MarketingUserMapperExt mapper;

	@Autowired
	private ModelMapper modelMapper;


	@Override
	protected List<MarketingUser> searchResult(MarketingMembersListParam searchParams) throws SuperCodeException{
		if(StringUtils.isBlank(searchParams.getOrganizationId())){
			throw new SuperCodeException("发生越权...");
		}
		 return mapper.list(searchParams);
	}

	@Override
	protected int count(MarketingMembersListParam searchParams) throws SuperCodeException{
		if(StringUtils.isBlank(searchParams.getOrganizationId())){
			throw new SuperCodeException("发生越权...");
		}
		Integer count=mapper.count(searchParams);
		return count;
	}

	/**
	 * 改变会员状态
	 * @param id
	 * @param state
	 * @param organizationId
	 * @throws SuperCodeException
	 */
	public void updateMembersStatus(Long id, int state, String organizationId) throws SuperCodeException{
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织信息不存在");
		}
		if(id == null || id<=0){
			throw new SuperCodeException("id不存在...");
		}
		if(state > SaleUserStatus.Max.getStatus().intValue() || state < SaleUserStatus.Min.getStatus().intValue()){
			throw new SuperCodeException("状态不合法...");

		}

		MarketingUser marketingUser = mapper.selectByPrimaryKey(id);
		if(!organizationId.equals(marketingUser.getOrganizationId())){
			throw new SuperCodeException("组织越权...");
		}

		if(marketingUser.getState().intValue() == SaleUserStatus.AUDITED.getStatus().intValue()
				&& state == SaleUserStatus.ENABLE.getStatus().intValue()  ){
			//  审核通过发送短信
		}
		MarketingUser dto = new MarketingUser();
		dto.setId(id);
		dto.setState((byte)state);
		mapper.updateByPrimaryKeySelective(dto);

	}

	public void deleteSalerByOrg(Long id, String organizationId) throws SuperCodeException{
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织信息不存在");
		}
		if(id == null || id<=0){
			throw new SuperCodeException("id不存在...");
		}
		MarketingUser marketingUser = mapper.selectByPrimaryKey(id);
		if(marketingUser != null && organizationId.equals( marketingUser.getOrganizationId())){
			int i = mapper.deleteByPrimaryKey(id);
			if(i != 1){
				throw  new SuperCodeException("删除失败...");
			}
		}else {
			throw  new SuperCodeException("删除失败...");
		}

	}

	public MarketingUser getMemberById(Long id) throws SuperCodeException{
		// 不校验组织
		if(id == null || id <= 0){
			throw new SuperCodeException("id校验失败...");
		}
		MarketingUser marketingUser = mapper.selectByPrimaryKey(id);
		return  marketingUser;
	}


	/**
	 * 前端组件传递时携带的areaCode key
	 */
	private static final String areaCode="areaCode";
	/**
	 * 前端组件传递时携带的areaName key
	 */
	private static final String areaName="areaName";

	public void updateMembers(MarketingSaleMembersUpdateParam marketingMembersUpdateParam, String organizationId) throws SuperCodeException{
		// 基础校验
		if(marketingMembersUpdateParam == null){
			throw new SuperCodeException("更新数据不存在...");
		}
		if(marketingMembersUpdateParam.getId() == null ){
			throw new SuperCodeException("id不存在...");

		}
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织不存在...");

		}

		// 业务校验
		MarketingUser marketingUser = mapper.selectByPrimaryKey(marketingMembersUpdateParam.getId());
		if(marketingUser == null || !organizationId.equals(marketingUser.getOrganizationId())){
			throw new SuperCodeException("更新失败...");
		}
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织信息获取失败...");
		}

		MarketingUser dto = modelMapper.map(marketingMembersUpdateParam,MarketingUser.class);


		// 数据转化
		// 机构信息
		List<CustomerInfo> customers = marketingMembersUpdateParam.getCustomer();
		if(!CollectionUtils.isEmpty(customers)){
			StringBuffer customerIds =new StringBuffer("");
			StringBuffer customerNames =new StringBuffer("");
			for(CustomerInfo customer: customers){
				if(StringUtils.isEmpty(customer.getCustomerId()) || (StringUtils.isEmpty(customer.getCustomerName()))){
					throw new SuperCodeException("门店信息不全...");
				}
				customerIds.append(",").append(customer.getCustomerId());
				customerNames.append(",").append(customer.getCustomerName());
			}
			// 移除第一个逗号
			dto.setCustomerId(customerIds.toString().substring(0));
			dto.setCustomerName(customerNames.toString().substring(0));
		}

		// 行政信息处理

		String pcccode = marketingMembersUpdateParam.getPCCcode();
		List<JSONObject> objects = JSONObject.parseArray(pcccode,JSONObject.class);
		JSONObject province = objects.get(0);
		JSONObject city = objects.get(1);
		JSONObject country = objects.get(2);
		// 省市区编码

		dto.setProvinceCode(province.getString(areaCode));
		dto.setCityCode(city.getString(areaCode));
		dto.setCountyCode(country.getString(areaCode));
		dto.setProvinceName(province.getString(areaName));
		dto.setCityName(city.getString(areaName));
		dto.setCountyName(country.getString(areaName));
		dto.setpCCcode(pcccode);


		// 更新操作
		int i = mapper.updateByPrimaryKeySelective(dto);
		if(i!=1){
			throw new SuperCodeException("更新失败...");
		}
	}

	/**
	 *
	 *
	 * 此方法为注册用户微信授权获取openid专用
	 * @param marketingUser
	 */
	public void saveUser(MarketingUser marketingUser) {
		mapper.insert(marketingUser);
	}

	public MarketingUser selectBylogin(SalerLoginParam loginUser) {

		return  null;
	}
}



