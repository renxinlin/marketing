package com.jgw.supercodeplatform.marketing.service.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.constants.PcccodeConstants;
import com.jgw.supercodeplatform.marketing.common.constants.SexConstants;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingUserMapperExt;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingWxMemberMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.SaleMemberBatchStatusParam;
import com.jgw.supercodeplatform.marketing.dto.SalerLoginParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.enums.market.SaleUserStatus;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMember;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.UserWithWechat;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MarketingSaleMemberService extends AbstractPageService<MarketingMembersListParam> {
	/**
	 * 短信链接URI
	 */
	@Value("#/sales/index?organizationId=")
	private  String WEB_SALER_CENTER_URI_FOR_SHORT_MSG ;


	@Value("${marketing.activity.h5page.url}")
	private  String WEB_SALER_CENTER_DOMAIN;


	protected static Logger logger = LoggerFactory.getLogger(MarketingSaleMemberService.class);
	@Value("亲爱的{{user}}，您已通过审核，可登录销售员中心{{url}}")
	private String SHORT_MSG ;
	@Autowired
	private MarketingUserMapperExt mapper;

	@Autowired
	private ModelMapper modelMapper;


	@Autowired
	private CommonService commonService;

	@Autowired
	private MarketingMembersService membersService;

	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;

	@Autowired
	private MarketingWxMemberMapper marketingWxMemberMapper;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private MarketingUserMapper marketingUserMapper;

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
	 * 批量改变会员状态
	 * @param
	 * @param
	 * @param organizationId
	 * @throws SuperCodeException
	 */
	public void updateMembersBatchStatus(SaleMemberBatchStatusParam ids, String organizationId) throws SuperCodeException {
		if(ids == null ){
			throw new SuperCodeException("参数不存在");
		}
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织信息不存在");
		}
		if(CollectionUtils.isEmpty(ids.getIds())){
			throw new SuperCodeException("id不存在...");
		}
		if(ids.getState() > SaleUserStatus.Max.getStatus().intValue() || ids.getState() < SaleUserStatus.Min.getStatus().intValue()){
			throw new SuperCodeException("状态不合法...");

		}

		mapper.updateBatch(ids, organizationId);
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
		// 审核时发送短信
		if(marketingUser.getState().intValue() == SaleUserStatus.AUDITED.getStatus().intValue()
				&& state == SaleUserStatus.ENABLE.getStatus().intValue()  ){
			String msg = msgTimplate(marketingUser.getUserName()==null ? "您":marketingUser.getUserName()
					, WEB_SALER_CENTER_DOMAIN+WEB_SALER_CENTER_URI_FOR_SHORT_MSG ,organizationId);
			try {
				checkPhoneFormat(marketingUser.getMobile());

				membersService.sendRegisterMessage(marketingUser.getMobile(),msg);
			} catch (SuperCodeException e) {
				e.printStackTrace();
				logger.error("发送导购员审核通过失败:手机:{},信息:{}",marketingUser.getMobile(),msg);
			}
		}
		MarketingUser dto = new MarketingUser();
		dto.setId(id);
		dto.setState((byte)state);
		mapper.updateByPrimaryKeySelective(dto);

	}



	/**
	 * 短信注册模板生成
	 * @param userName
	 * @param url
	 * @return
	 */
	private String msgTimplate(String userName, String url,String organizationId) {
		url = url+organizationId;
		return  SHORT_MSG.replace("{{user}}",userName).replace("{{url}}",url);

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
		if(!StringUtils.isEmpty(marketingMembersUpdateParam.getMobile())){
			checkPhoneFormat(marketingMembersUpdateParam.getMobile());
			MarketingUser marketingUser = mapper.selectByPhone(marketingMembersUpdateParam.getMobile());
			if(marketingUser != null && marketingUser.getId()!=null){
				Long id = marketingUser.getId().longValue();
				if(marketingMembersUpdateParam.getId().longValue() != id ){
					throw new RuntimeException("手机号已经存在");
				}
			}
		}

//		if(StringUtils.isBlank(marketingMembersUpdateParam.getCustomerId())){
//			throw new SuperCodeException("机构id不存在...");
//
//		}
//		if(StringUtils.isBlank(marketingMembersUpdateParam.getCustomerName())){
//			throw new SuperCodeException("机构名称不存在...");
//
//		}

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
//		List<CustomerInfo> customers = marketingMembersUpdateParam.getCustomer();
//		if(!CollectionUtils.isEmpty(customers)){
//			StringBuffer customerIds =new StringBuffer("");
//			StringBuffer customerNames =new StringBuffer("");
//			for(CustomerInfo customer: customers){
//				if(StringUtils.isEmpty(customer.getCustomerId()) || (StringUtils.isEmpty(customer.getCustomerName()))){
//					throw new SuperCodeException("门店信息不全...");
//				}
//				customerIds.append(",").append(customer.getCustomerId());
//				customerNames.append(",").append(customer.getCustomerName());
//			}
//			// 移除第一个逗号

//		}

		// 行政信息处理

		String pcccode = marketingMembersUpdateParam.getPCCcode();
		List<JSONObject> objects = JSONObject.parseArray(pcccode,JSONObject.class);
		int size = objects.size();
		JSONObject province = size > 0 ? objects.get(0)  : new JSONObject()  ;
		JSONObject city = size > 1  ? objects.get(1) : new JSONObject() ;
		JSONObject country = size > 2 ? objects.get(2) : new JSONObject();
		// 省市区编码

		dto.setProvinceCode(province.getString(PcccodeConstants.areaCode));
		dto.setCityCode(city.getString(PcccodeConstants.areaCode));
		dto.setCountyCode(country.getString(PcccodeConstants.areaCode));
		dto.setProvinceName(province.getString(PcccodeConstants.areaName));
		dto.setCityName(city.getString(PcccodeConstants.areaName));
		dto.setCountyName(country.getString(PcccodeConstants.areaName));
		dto.setpCCcode(pcccode);
		dto.setMechanismType(marketingMembersUpdateParam.getMechanismType());
		dto.setCustomerId(marketingMembersUpdateParam.getCustomerId());
		dto.setCustomerName(marketingMembersUpdateParam.getCustomerName());
		// 更新操作 pcccode不为空则修改省市区全部字段
		int i = mapper.updateByPrimaryKeySelectiveWithBiz(dto);
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
	public MarketingUser saveUser(MarketingUser marketingUser) throws SuperCodeException{
		// 业务校验: 判断用户是否存在,全局唯一，产品定义
		MarketingUser userDto = mapper.selectByPhone(marketingUser.getMobile());
		if(userDto != null){
			throw new SuperCodeException("手机号已存在...");
		}
		mapper.insert(marketingUser);
		return marketingUser;
	}

	public MarketingUser selectBylogin(SalerLoginParam loginUser) throws SuperCodeException{

		if(StringUtils.isBlank(loginUser.getMobile())){
			throw new SuperCodeException("手机不存在");
		}
		if(StringUtils.isBlank(loginUser.getOrganizationId())){
			throw new SuperCodeException("组织不存在");
		}
		if(StringUtils.isBlank(loginUser.getVerificationCode())){
			throw new SuperCodeException("验证码不存在");
		}
		boolean success = commonService.validateMobileCode(loginUser.getMobile(), loginUser.getVerificationCode());
		if(!success){
			throw new SuperCodeException("验证码校验失败");
		}
		MarketingUser marketingUser = mapper.selectByPhone(loginUser.getMobile());
		if(marketingUser == null){
			throw new SuperCodeException("用户不存在");

		}
		if(!loginUser.getOrganizationId().equals(marketingUser.getOrganizationId())){
			throw new SuperCodeException("组织校验失败");

		}
		// 手机号只能跟一个组织 产品定义
		return  marketingUser;
	}

	/**
	 * 无微信授权的用户注册
	 * @param userInfo
	 * @return
	 * @throws SuperCodeException
	 */
	@Transactional(rollbackFor = Exception.class)
	public UserWithWechat saveRegisterUser(MarketingSaleMembersAddParam userInfo) throws SuperCodeException{
		// 1基础校验
		validateBasicByRegisterUser(userInfo);

		// 2.1业务校验：验证码
		boolean success = commonService.validateMobileCode(userInfo.getMobile(), userInfo.getVerificationCode());
		if(!success){
			throw new SuperCodeException("验证码校验失败...");
		}
		// 2.1业务校验: 判断用户是否存在,全局唯一，产品定义
		MarketingUser userDto = mapper.selectByPhone(userInfo.getMobile());
		if(userDto != null){
			throw new SuperCodeException("手机号已存在...");
		}

		// 3数据转换和保存
		UserWithWechat userDo = changeToDo(userInfo);
		if (StringUtils.isNotBlank(userDo.getMobile())) {
			MarketingUser marketingUser = new MarketingUser();
			marketingUser.setSource(SourceType.H5); //    3、H5 4
			BeanUtils.copyProperties(userDo, marketingUser);
//			marketingUser.setMechanismType(userInfo.getMechanismType());
			mapper.insertSelective(marketingUser);
		}

		return userDo;

	}

	/**
	 * 保存非微信授权用户基础校验
	 * @param userInfo
	 * @throws SuperCodeException
	 */
	private void validateBasicByRegisterUser(MarketingSaleMembersAddParam userInfo) throws SuperCodeException{
		if(userInfo == null){
			throw new SuperCodeException("保存用户失败001...");
		}

		if(StringUtils.isBlank(userInfo.getOrganizationId())){
			throw new SuperCodeException("组织信息获取失败...");
		}
		if(StringUtils.isBlank(userInfo.getMobile())){
			throw new SuperCodeException("手机号不存在...");
		}
		if(StringUtils.isBlank(userInfo.getUserName())){
			throw new SuperCodeException("请填入用户名...");
		}
		if(StringUtils.isBlank(userInfo.getVerificationCode())){
			throw new SuperCodeException("请填入验证码...");
		}
		if(StringUtils.isBlank(userInfo.getpCCcode())){
			throw new SuperCodeException("请输入所在地信息...");
		}
		// 产品需求改变:必填
		if(StringUtils.isBlank(userInfo.getCustomerId())){
			throw new SuperCodeException("请输入渠道ID信息...");
		}

		if(StringUtils.isBlank(userInfo.getCustomerName())){
			throw new SuperCodeException("请输入渠道名称信息...");
		}


	}

	/**
	 * 保存用户注册信息
	 * 	vo	to do
	 * @param userInfo
	 * @return
	 */
	private UserWithWechat changeToDo(MarketingSaleMembersAddParam userInfo) throws SuperCodeException {
		UserWithWechat userDtoToDb = modelMapper.map(userInfo,UserWithWechat.class);

		// 门店信息转换
//		List<CustomerInfo> customers = userInfo.getCustomer();
//		if(!CollectionUtils.isEmpty(customers)){
//			StringBuffer ids = new StringBuffer();
//			StringBuffer names = new StringBuffer();
//			int i = 0;
//			for(CustomerInfo  customer:customers){
//				if(StringUtils.isEmpty(customer.getCustomerId()) || (StringUtils.isEmpty(customer.getCustomerName()))){
//					throw new SuperCodeException("门店信息不全...");
//				}
//				i++;
//				if(i == customers.size()){
//					ids.append(customer.getCustomerId());
//					names.append(customer.getCustomerName());
//				}else {
//					ids.append(customer.getCustomerId()).append(",");
//					names.append(customer.getCustomerName()).append(",");
//
//				}
//			}
//			userDtoToDb.setCustomerId(ids.toString());
//			userDtoToDb.setCustomerName(names.toString());
//		}

		// pcccode转换
		// 省市区编码
		String pcccode = userInfo.getpCCcode();
		List<JSONObject> objects = JSONObject.parseArray(pcccode,JSONObject.class);
		int size = objects.size();
		JSONObject province = size > 0 ? objects.get(0)  : new JSONObject()  ;
		JSONObject city = size > 1  ? objects.get(1) : new JSONObject() ;
		JSONObject country = size > 2 ? objects.get(2) : new JSONObject();
		userDtoToDb.setProvinceCode(province.getString(PcccodeConstants.areaCode));
		userDtoToDb.setCityCode(city.getString(PcccodeConstants.areaCode));
		userDtoToDb.setCountyCode(country.getString(PcccodeConstants.areaCode));
		userDtoToDb.setProvinceName(province.getString(PcccodeConstants.areaName));
		userDtoToDb.setCityName(city.getString(PcccodeConstants.areaName));
		userDtoToDb.setCountyName(country.getString(PcccodeConstants.areaName));
		// 导购员
		userDtoToDb.setMemberType(MemberTypeEnums.SALER.getType());
		// USER ID
		userDtoToDb.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
		if(!StringUtils.isBlank(userInfo.getOpenId())){
			userDtoToDb.setOpenid(userInfo.getOpenId());
		}
		return userDtoToDb;
	}

	public void addUserOpenId(MarketingUser user, String openid) {
		String organizationId = user.getOrganizationId();
		QueryWrapper<MarketingWxMember> queryWrapper = Wrappers.query();
		queryWrapper.eq("MemberId",user.getId()).eq("CurrentUse", 1).eq("MemberType", MemberTypeEnums.SALER.getType());
		MarketingWxMember addMarketingWxMember = new MarketingWxMember();
		MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(queryWrapper);
		if (marketingWxMember != null) {
			UpdateWrapper nouseUpdateWrapper = Wrappers.<MarketingWxMember>update().set("CurrentUse",(byte)0).eq("OrganizationId", organizationId).eq("CurrentUse", (byte)1).eq("MemberType", MemberTypeEnums.SALER.getType());
			marketingWxMemberMapper.update(null, nouseUpdateWrapper);
			BeanUtils.copyProperties(marketingWxMember, addMarketingWxMember);
		}
		MarketingWxMerchants marketingWxMerchants = mWxMerchantsMapper.get(user.getOrganizationId());
		String organizationName = marketingWxMerchants.getOrganizatioIdlName();
		if (marketingWxMerchants.getMerchantType() == (byte)1) {
			organizationId = marketingWxMerchants.getOrganizationId();
			if (marketingWxMerchants.getJgwId() != null) {
				marketingWxMerchants = mWxMerchantsMapper.getJgw(marketingWxMerchants.getJgwId());
			} else {
				marketingWxMerchants = mWxMerchantsMapper.getDefaultJgw();
			}
			addMarketingWxMember.setJgwType((byte)1);
		} else {
			addMarketingWxMember.setJgwType((byte)0);
		}
		addMarketingWxMember.setOrganizationId(organizationId);
		addMarketingWxMember.setOrganizationFullName(organizationName);
		addMarketingWxMember.setAppid(marketingWxMerchants.getMchAppid());
		addMarketingWxMember.setOpenid(openid);
		addMarketingWxMember.setCreateTime(new Date());
		addMarketingWxMember.setUpdateTime(new Date());
		addMarketingWxMember.setMemberId(user.getId());
		addMarketingWxMember.setMemberType(MemberTypeEnums.SALER.getType());
		addMarketingWxMember.setCurrentUse((byte)1);
		marketingWxMemberMapper.insert(addMarketingWxMember);
	}


//	public MarketingUser selectByOpenid(String openid) throws SuperCodeException {
//		if(openid == null){
//			throw new SuperCodeException("参数获取失败");
//		}
//		return mapper.selectByOpenid(openid);
//
//
//	}

	@Transactional
	public UserWithWechat selectByOpenidAndOrgId(String openid, String organizationId) {
		MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(Wrappers.<MarketingWxMember>query().eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.SALER.getType()));
		if (marketingWxMember == null) {
			return null;
		}
		if(marketingWxMember.getCurrentUse() == 0) {
			UpdateWrapper nouseUpdateWrapper = Wrappers.<MarketingWxMember>update().set("CurrentUse",(byte)0).eq("OrganizationId", organizationId).eq("CurrentUse", (byte)1).eq("MemberType", MemberTypeEnums.SALER.getType());
			marketingWxMemberMapper.update(null, nouseUpdateWrapper);
			UpdateWrapper currentUpdateWrapper = Wrappers.<MarketingWxMember>update().set("CurrentUse",(byte)1).eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.SALER.getType());
			marketingWxMemberMapper.update(null, currentUpdateWrapper);
		}
		UserWithWechat userWithWechat = new UserWithWechat();
		MarketingUser marketingUser = mapper.selectByPrimaryKey(marketingWxMember.getMemberId());
		BeanUtils.copyProperties(marketingUser, userWithWechat);
		BeanUtils.copyProperties(marketingWxMember, userWithWechat);
		return userWithWechat;
	}

	public UserWithWechat selectById(Long memberId) {
		MarketingUser marketingUser = mapper.selectByPrimaryKey(memberId);
		if (marketingUser == null) {
			return null;
		}
		MarketingWxMember marketingWxMember = marketingWxMemberMapper.selectOne(Wrappers.<MarketingWxMember>query().eq("MemberId",memberId).eq("CurrentUse", 1).eq("MemberType", MemberTypeEnums.SALER.getType()));
		UserWithWechat userWithWechat = new UserWithWechat();
		BeanUtils.copyProperties(marketingUser, userWithWechat);
		userWithWechat.setMemberId(marketingUser.getId());
		if (marketingWxMember != null) {
			userWithWechat.setWxName(marketingWxMember.getWxName());
			userWithWechat.setOpenid(marketingWxMember.getOpenid());
			userWithWechat.setWechatHeadImgUrl(marketingWxMember.getWechatHeadImgUrl());
		}
		return userWithWechat;
	}

	/**
	 * 更新openid 用户头像
	 * @param marketingWxMember
	 * @return
	 */
	public int updateWxInfo(MarketingWxMember marketingWxMember) {
		if (marketingWxMember == null) {
			throw new SuperCodeExtException("用户更新信息不能为空");
		}
		String openid = marketingWxMember.getOpenid();
		if (StringUtils.isBlank(openid)){
			throw new SuperCodeExtException("openid不能为空");
		}
		String organizationId = marketingWxMember.getOrganizationId();
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeExtException("组织ID不能为空");
		}
		UpdateWrapper<MarketingWxMember> updateWrapper = Wrappers.<MarketingWxMember>update().eq("Openid", openid).eq("OrganizationId", organizationId).eq("MemberType", MemberTypeEnums.SALER.getType());
		return marketingWxMemberMapper.update(marketingWxMember, updateWrapper);
	}

	/**
	 * 导购员资料
	 */
	public List<MarketingUser> getSalerInfoList() throws SuperCodeException {
		String organoizationId= commonUtil.getOrganizationId();
		if(StringUtils.isBlank(organoizationId)){
			throw new SuperCodeException("组织不存在...");
		}
		QueryWrapper queryWrapper=new QueryWrapper();
		queryWrapper.eq("OrganizationId",organoizationId);
		//默认0会员，1导购员,其他员工等
		queryWrapper.eq("MemberType",1);
		List<MarketingUser> list= marketingUserMapper.selectList(queryWrapper);
		list.stream().filter(marketingUser -> {
			if (SexConstants.WOMEN.equals(marketingUser.getSex())){
				marketingUser.setSex("女");
			}else if(SexConstants.MEN.equals(marketingUser.getSex())){
				marketingUser.setSex("男");
			}else {
				marketingUser.setSex("--");
			}
			return true;
		}).collect(Collectors.toList());
		return list;
	}

}



