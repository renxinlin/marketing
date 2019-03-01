package com.jgw.supercodeplatform.marketing.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.admincode.AdminstrativeCodeMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketing.pojo.admincode.MarketingAdministrativeCode;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

@Service
public class MarketingMembersService extends CommonUtil {
	protected static Logger logger = LoggerFactory.getLogger(MarketingMembersService.class);
    @Autowired
    private MarketingMembersMapper marketingMembersMapper;

    @Autowired
    private OrganizationPortraitMapper organizationPortraitMapper;

    @Autowired
    private AdminstrativeCodeMapper adminstrativeCodeMapper;

    @Autowired
    private MarketingActivitySetMapper mSetMapper;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 会员注册
     * @param map
     * @return
     * @throws Exception
     */
    public int addMember(MarketingMembersAddParam marketingMembersAddParam) throws Exception{
        String userId = getUUID();
        marketingMembersAddParam.setUserId(userId);
        Map<String,Object> areaCode = new HashMap<>();
        areaCode.put("areaCode",marketingMembersAddParam.getCityCode());
        MarketingAdministrativeCode marketingAdministrativeCode = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setCityName(marketingAdministrativeCode.getCityName());
        areaCode.put("areaCode",marketingAdministrativeCode.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode2 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setCountyName(marketingAdministrativeCode2.getCityName());
        marketingMembersAddParam.setCountyCode(marketingAdministrativeCode2.getAreaCode());
        areaCode.put("areaCode",marketingAdministrativeCode2.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode3 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        marketingMembersAddParam.setProvinceName(marketingAdministrativeCode3.getCityName());
        marketingMembersAddParam.setProvinceCode(marketingAdministrativeCode3.getAreaCode());
        return marketingMembersMapper.addMembers(marketingMembersAddParam);
    }

    /**
     * 条件查询会员
     * @param map
     * @return
     */
    public List<MarketingMembers> getAllMarketingMembersLikeParams(Map<String,Object> map){
        List<MarketingOrganizationPortrait> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(map.get("organizationId").toString());
        List<String> portraitsList = new ArrayList<>();
        portraitsList.add("Mobile");
        portraitsList.add("WxName");
        portraitsList.add("Openid");
        for (MarketingOrganizationPortrait portrait:organizationPortraits){
            if (!"Mobile".equals(portrait.getPortraitCode())){
                if ("Birthday".equals(portrait.getPortraitCode())||"BabyBirthday".equals(portrait.getPortraitCode())){
                    StringBuilder sb = new StringBuilder();
                    sb.append(" DATE_FORMAT( ");
                    sb.append( portrait.getPortraitCode());
                    sb.append(",'%Y-%m-%d') as " );
                    sb.append(portrait.getPortraitCode() );
                    portraitsList.add(sb.toString());
                }else {
                    portraitsList.add(portrait.getPortraitCode());
                }
            }
        }
        portraitsList.add("State");
        String list = portraitsList.toString().replace("[","").replace("]","");;
        map.put("portraitsList",list);
        System.out.println(list);
        return marketingMembersMapper.getAllMarketingMembersLikeParams(map);
    }

    /**
     * 条件查询会员数量
     * @param map
     * @return
     */
    public Integer getAllMarketingMembersCount(Map<String,Object> map){
        return marketingMembersMapper.getAllMarketingMembersCount(map);
    }

    /**
     * 修改会员信息
     * @param map
     * @return
     */
    public int updateMembers(MarketingMembersUpdateParam membersUpdateParam){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",membersUpdateParam.getUserId());
        map.put("userName",membersUpdateParam.getUserName());
        map.put("sex",membersUpdateParam.getSex());
        map.put("birthday",membersUpdateParam.getBirthday());
        map.put("cityCode",membersUpdateParam.getCityCode());
        map.put("customerName",membersUpdateParam.getCustomerName());
        map.put("customerCode",membersUpdateParam.getCustomerCode());
        map.put("babyBirthday",membersUpdateParam.getBabyBirthday());
        Map<String,Object> areaCode = new HashMap<>();
        areaCode.put("areaCode",map.get("cityCode").toString());
        MarketingAdministrativeCode marketingAdministrativeCode = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("cityName",marketingAdministrativeCode.getCityName());
        areaCode.put("areaCode",marketingAdministrativeCode.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode2 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("countyName",marketingAdministrativeCode2.getCityName());
        map.put("countyCode",marketingAdministrativeCode2.getAreaCode());
        areaCode.put("areaCode",marketingAdministrativeCode2.getParentAreaCode());
        MarketingAdministrativeCode marketingAdministrativeCode3 = adminstrativeCodeMapper.getAdminCodeByAreaCode(areaCode);
        map.put("provinceName",marketingAdministrativeCode3.getCityName());
        map.put("provinceCode",marketingAdministrativeCode3.getAreaCode());
        return marketingMembersMapper.updateMembers(map);
    }

    /**
     * 修改会员状态
     * @param map
     * @return
     */
    public int updateMembersStatus(Map<String,Object> map){
        return marketingMembersMapper.updateMembers(map);
    }

    /**
     * 获取单个会员信息
     * @param map
     * @return
     */
    public MarketingMembers getMemberById(Map<String,Object> map){
        String userId = map.get("userId").toString();
        String organizationId = map.get("organizationId").toString();
        return marketingMembersMapper.getMemberById(userId,organizationId);
    }

	public MarketingMembers selectByOpenIdAndOrgId(String openid, String organizationId) {
		return marketingMembersMapper.selectByOpenIdAndOrgId(openid,organizationId);
	}
    /**
     * h5页面登录接口
     * @param mobile
     * @param openId 
     * @param activitySetId
     * @param verificationCode
     * @return
     * @throws SuperCodeException
     * 
     * 1、根据手机号和组织id查
     *   1.1、查的到则说明该用户注册过(再根据用户已注册字段确认)--这时需要把openid的记录和手机号两条记录合并
     *   1.2、如果查不到
     *      1.2.1、用户注册过但手机号换了---根据openid查，如果查出的记录用户已注册字段为是则验证该情况
     *      1.2.2、用户未注册                       ---根据openid查，如果查出的记录用户已注册字段为否则验证该情况
     *      

     */
	
	public RestResult<H5LoginVO> login(String mobile, String openId, Long activitySetId, String verificationCode) throws SuperCodeException {
		RestResult<H5LoginVO> restResult=new RestResult<H5LoginVO>();
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(verificationCode)|| StringUtils.isBlank(openId) || null==activitySetId) {
			restResult.setState(500);
			restResult.setMsg("请检查参数，参数不能为空");
			return restResult;
		}
		String redisPhoneCode=redisUtil.get(RedisKey.phone_code_prefix+mobile);
		if (StringUtils.isBlank(redisPhoneCode) ) {
			restResult.setState(500);
			restResult.setMsg("验证码不存在或已过期请重新获取验证码");
			return restResult;
		}
		
		if (!redisPhoneCode.equals(verificationCode)) {
			restResult.setState(500);
			restResult.setMsg("验证码不正确");
			return restResult;
		}
		
		MarketingActivitySet maActivitySet=mSetMapper.selectById(activitySetId);
		if (null==maActivitySet) {
			restResult.setState(500);
			restResult.setMsg("该活动设置id不存在");
			return restResult;
		}
		  
		String organizationId=maActivitySet.getOrganizationId();
		MarketingMembers marketingMembersByOpenId=marketingMembersMapper.selectByOpenIdAndOrgId(openId, organizationId);
		if (null==marketingMembersByOpenId) {
			logger.info("无法根据openId及组织id查找到用户,openId="+openId+",组织id="+organizationId);
			throw new SuperCodeException("无法根据openId及组织id查找到用户。可能用户已被删除，请尝试重新扫码进入或联系商家", 500);
		}
		H5LoginVO h5LoginVO=new H5LoginVO();
		Long userIdByOpenId=marketingMembersByOpenId.getId();
		
		MarketingMembers marketingMembersByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
		
		//1、如果根据登录手机号无法查询到记录，则判断marketingMembersByOpenId的已注册字段是否为已注册
		if (null==marketingMembersByPhone) {
			  Integer flag=1;
			//2、如果已注册则直接更新手机号
			  if (1==flag) {
				    Map<String,Object> map=new HashMap<String, Object>();
					map.put("Id", userIdByOpenId);
					map.put("mobile", mobile);
					marketingMembersMapper.updateMembers(map);
					h5LoginVO.setRegistered(1);
			  }else {
				  //3、如果未注册则进行注册 a直接注册 b前端再调接口完善
					List<MarketingOrganizationPortrait> mPortraits=organizationPortraitMapper.getSelectedPortrait(organizationId);
					//如果只有一个企业画像设置则认为就是手机号就跳过信息完善
					if (null!=mPortraits && !mPortraits.isEmpty()) {
						if (mPortraits.size()==1) {
						    Map<String,Object> map=new HashMap<String, Object>();
							map.put("Id", userIdByOpenId);
							map.put("mobile", mobile);
							marketingMembersMapper.updateMembers(map);
							h5LoginVO.setRegistered(1);
						}else {
							h5LoginVO.setRegistered(0);
							h5LoginVO.setMemberId(userIdByOpenId);
						}
					}
			  }
		}else {
			//如果根据登录手机号能找到用户则说明之前登陆过或者注册过就不需要注册完善信息，但需要比较跟openid查出的记录是否是一条记录，不是的话要合并
			h5LoginVO.setRegistered(1);
			Long userIdByPhone=marketingMembersByPhone.getId();
			if (!userIdByOpenId.equals(userIdByPhone)) {
                //更新手机号对应的记录设置微信openid及昵称
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("Id", userIdByPhone);
				map.put("openid", marketingMembersByOpenId.getOpenid());
				map.put("wxName", marketingMembersByOpenId.getWxName());
				marketingMembersMapper.updateMembers(map);
				//删除openid查出的用户
				marketingMembersMapper.deleteById(userIdByOpenId);
			}
		}
		restResult.setState(200);
		restResult.setResults(h5LoginVO);
		restResult.setMsg("登录成功");
		return restResult;
	}
    
}
