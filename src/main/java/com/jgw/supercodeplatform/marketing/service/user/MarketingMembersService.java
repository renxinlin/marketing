package com.jgw.supercodeplatform.marketing.service.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.admincode.AdminstrativeCodeMapper;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.admincode.MarketingAdministrativeCode;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;

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
    private MarketingPrizeTypeMapper mMarketingPrizeTypeMapper;
    
    @Autowired
    private MarketingActivityMapper mActivityMapper;
    
    @Autowired
    private MarketingWxMerchantsMapper mWxMerchantsMapper;
    
    
    @Autowired
    private MarketingMembersWinRecordMapper mWinRecordMapper;
    
    @Autowired
    private WXPayTradeOrderMapper wXPayTradeOrderMapper;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private WXPayService wxpService;
    
    @Autowired
    private CodeEsService codeEsService;
    
    @Value("${marketing.server.ip}")
    private String serverIp;
    
    
    private static SimpleDateFormat staticESSafeFormat=new SimpleDateFormat("yyyy-MM-dd");
    
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
        List<MarketingOrganizationPortraitListParam> organizationPortraits = organizationPortraitMapper.getSelectedPortrait(map.get("organizationId").toString());
        List<String> portraitsList = new ArrayList<>();
        portraitsList.add("Mobile");
        portraitsList.add("WxName");
        portraitsList.add("Openid");
        for (MarketingOrganizationPortraitListParam portrait:organizationPortraits){
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
        map.put("mobile",membersUpdateParam.getMobile());
        map.put("state",membersUpdateParam.getState());
        map.put("isRegistered",membersUpdateParam.getIsRegistered());
        map.put("userId",membersUpdateParam.getUserId());
        map.put("userName",membersUpdateParam.getUserName());
        map.put("sex",membersUpdateParam.getSex());
        map.put("birthday",membersUpdateParam.getBirthday());
        map.put("cityCode",membersUpdateParam.getCityCode());
        map.put("customerName",membersUpdateParam.getCustomerName());
        map.put("customerCode",membersUpdateParam.getCustomerId());
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

	public void addMember(MarketingMembers members) {
		marketingMembersMapper.insert(members);
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
     * h5页面登录接口--既然已经执行登录接口那肯定是该活动中奖页设置了手机登录（通过活动设置id查询中奖页信息得知）
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
	
	public RestResult<H5LoginVO> login(String mobile, String wxstate, String verificationCode) throws SuperCodeException {
		RestResult<H5LoginVO> restResult=new RestResult<H5LoginVO>();
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(verificationCode)|| StringUtils.isBlank(wxstate)) {
			restResult.setState(500);
			restResult.setMsg("请检查参数，参数不能为空");
			return restResult;
		}
		ScanCodeInfoMO scanCodeInfoMO=GlobalRamCache.scanCodeInfoMap.get(wxstate);
		if (null==scanCodeInfoMO) {
			restResult.setState(500);
			restResult.setMsg("参数wxstate对应的后台扫码缓存信息不存在，请重新扫码");
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
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet maActivitySet=mSetMapper.selectById(activitySetId);
		if (null==maActivitySet) {
			restResult.setState(500);
			restResult.setMsg("该活动设置id不存在");
			return restResult;
		}
		String openId=scanCodeInfoMO.getOpenId();
		String organizationId=maActivitySet.getOrganizationId();
		//1、首先保证授权时用户是保存成功的
		MarketingMembers marketingMembersByOpenId=marketingMembersMapper.selectByOpenIdAndOrgId(openId, organizationId);
		if (null==marketingMembersByOpenId) {
			logger.info("登录时无法根据openId及组织id查找到用户,openId="+openId+",组织id="+organizationId);
			throw new SuperCodeException("无法根据openId及组织id查找到用户。可能用户已被删除，请尝试重新扫码进入授权或联系商家", 500);
		}
		List<MarketingOrganizationPortraitListParam> mPortraits=organizationPortraitMapper.getSelectedPortrait(organizationId);
		if (null==mPortraits || mPortraits.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("登录时获取企业画像设置为空，无法进行后续逻辑");
			return restResult;
		}
		
		
		H5LoginVO h5LoginVO=new H5LoginVO();
		Long userIdByOpenId=marketingMembersByOpenId.getId();
		//2、根据输入的手机号和组织id查询该手机号是否存在记录
		MarketingMembers marketingMembersByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
		
		//3、如果根据登录手机号无法查询到记录，则说明该手机号未进行过注册也为进行过绑定。可能情况：
		 //3.1该openid对应的用户之前绑定过手机号但是想换手机号了、3.2该openid用户从未绑定过手机号 
		if (null==marketingMembersByPhone) {
		    Map<String,Object> updatemap=new HashMap<String, Object>();
		    updatemap.put("Id", userIdByOpenId);
		    updatemap.put("mobile", mobile);
			marketingMembersMapper.updateMembers(updatemap);
			if (mPortraits.size()==1) {
				//如果企业画像只有一个那默认为手机号就不需要再去完善信息
				h5LoginVO.setRegistered(1);
			}else {
				h5LoginVO.setRegistered(0);
				h5LoginVO.setMemberId(userIdByOpenId);
			}
		}else {
			//如果根据登录手机号能找到用户则说明之前登陆过或者注册过就不需要注册完善信息，但需要比较跟openid查出的记录是否是一条记录，不是的话要合并
			h5LoginVO.setRegistered(1);
			Long userIdByPhone=marketingMembersByPhone.getId();
			//4、如果分别根据openid和手机号查出两条记录且主键id不一致，则说明
			// 4.1、这两条信息没合并过
			// 4.2、手机号这条记录合并过openid，可用户此时想换一个微信号openid
			if (!userIdByOpenId.equals(userIdByPhone)) {
				
				String openIdByPhone=marketingMembersByPhone.getOpenid();
				String openIdByOpendId=marketingMembersByOpenId.getOpenid();
				//手机号这条记录的openid不为空，合并过openid就通过之前的openid更新中奖纪录里的openid和手机号
				if (StringUtils.isNotBlank(openIdByPhone)) {
					//如果之前该手机号绑定过openid则更新之前的中奖纪录，没有的话就不更新哦
					mWinRecordMapper.updateOpenIdAndMobileByOpenIdAndOrgId(openIdByOpendId,mobile,organizationId,openIdByPhone);
				}
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
    /**
     * 点击中奖逻辑
     * @param activitySetId
     * @param openId
     * @return
     * @throws SuperCodeException
     */
	public RestResult<String> lottery(String wxstate,String mobile) throws SuperCodeException {
		RestResult<String> restResult=new RestResult<String>();

		ScanCodeInfoMO scanCodeInfoMO=GlobalRamCache.scanCodeInfoMap.get(wxstate);
		if (null==scanCodeInfoMO) {
			restResult.setState(500);
			restResult.setMsg("不存在扫码唯一纪录="+wxstate+"的扫码缓存信息，请重新扫码");
			return restResult;
		}
		String openId=scanCodeInfoMO.getOpenId();
		if (StringUtils.isBlank(openId)) {
			restResult.setState(500);
			restResult.setMsg("openId参数不能为空");
			return restResult;
		}
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		
		MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
		if (null==mActivitySet) {
			restResult.setState(500);
			restResult.setMsg("该活动设置不存在");
			return restResult;
		}
		List<MarketingPrizeType> mPrizeTypes=mMarketingPrizeTypeMapper.selectByActivitySetId(activitySetId);
		if (null==mPrizeTypes || mPrizeTypes.isEmpty()) {
			restResult.setState(500);
			restResult.setMsg("该活动未设置中奖奖次");
			return restResult;
		}
		
		MarketingActivity activity=mActivityMapper.selectById(mActivitySet.getActivityId());
		if (null==activity) {
			restResult.setState(500);
			restResult.setMsg("该活动设置对应的活动不存在");
			return restResult;
		}
		//获取该活动设置下的参与码总数
		Long codeTotalNum=mActivitySet.getCodeTotalNum();
		String organizationId=mActivitySet.getOrganizationId();

		//执行中奖算法
		MarketingPrizeTypeMO mPrizeTypeMO = LotteryUtil.lottery(mPrizeTypes,codeTotalNum);
		
		//同步代码块**很重要，要先查询该码此时是不是被其它用户已扫过，如果扫过就不能发起微信支付等操作
		synchronized (this) {
			Long codeCount=codeEsService.countByCode(scanCodeInfoMO.getCodeId(), scanCodeInfoMO.getCodeTypeId());
			if (null==codeCount ||codeCount.intValue()==0) {
				//更新奖次被扫码数量
				mPrizeTypeMO.setWiningNum(mPrizeTypeMO.getWiningNum() + 1);
				MarketingPrizeType marketingPrizeType =new MarketingPrizeType();
				marketingPrizeType.setId(mPrizeTypeMO.getId());
				marketingPrizeType.setWiningNum(mPrizeTypeMO.getWiningNum());
				mMarketingPrizeTypeMapper.update(marketingPrizeType);
				codeEsService.addScanCodeRecord(null, scanCodeInfoMO.getProductId(), scanCodeInfoMO.getProductBatchId(), scanCodeInfoMO.getCodeId(), scanCodeInfoMO.getCodeTypeId(), activitySetId, staticESSafeFormat.format(new Date()));
			}else {
				restResult.setState(200);
				restResult.setMsg("您手速太慢，刚刚该码已被其它用户扫过");
				return restResult;
			}
		}
		
		int amount=mPrizeTypeMO.getPrizeAmount();
		Byte realPrize=mPrizeTypeMO.getRealPrize();
		if (realPrize.equals((byte)0)) {
			restResult.setState(200);
			restResult.setMsg("‘啊呀没中，一定是打开方式不对’：没中奖");
		}else if (realPrize.equals((byte)1)) {
			
			MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.selectByOrganizationId(organizationId);
			if (null==mWxMerchants) {
				restResult.setState(500);
				restResult.setMsg("当前企业未绑定公众号数据");
				return restResult;
			}
			//插入中奖纪录
			MarketingMembersWinRecord redWinRecord=new MarketingMembersWinRecord();
			redWinRecord.setActivityId(activity.getId());
			redWinRecord.setActivityName(activity.getActivityName());
			redWinRecord.setActivitySetId(activitySetId);
			redWinRecord.setMobile(mobile);
			redWinRecord.setOpenid(openId);
			redWinRecord.setPrizeTypeId(mPrizeTypeMO.getId());
			redWinRecord.setWinningAmount(amount);
			redWinRecord.setWinningCode(scanCodeInfoMO.getCodeId());
			redWinRecord.setOrganizationId(organizationId);
			mWinRecordMapper.addWinRecord(redWinRecord);
			
			//生成订单号
			String partner_trade_no=WXPayTradeNoGenerator.tradeNo();
			//保存订单
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			WXPayTradeOrder tradeOrder=new WXPayTradeOrder();
			tradeOrder.setAmount(amount);
			tradeOrder.setOpenId(openId);
			tradeOrder.setTradeStatus((byte)0);
			tradeOrder.setPartnerTradeNo(partner_trade_no);
			tradeOrder.setTradeDate(format.format(new Date()));
			tradeOrder.setOrganizationId(organizationId);
			wXPayTradeOrderMapper.insert(tradeOrder);
			
			String remoteAddr = request.getRemoteAddr();
			if (StringUtils.isBlank(remoteAddr)) {
				remoteAddr=serverIp;
			}
			try {
				wxpService.qiyePay(openId, remoteAddr, amount,partner_trade_no, organizationId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			restResult.setState(200);
			restResult.setMsg("恭喜您获得"+amount+"元惊喜红包！");
		}
		return restResult;
	}


    
}
