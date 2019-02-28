package com.jgw.supercodeplatform.marketing.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;

@Service
public class ScanCodeService {
	 
    @Autowired
    private MarketingMembersService membersService;
    
    @Autowired
    private MarketingActivitySetService activitySetService;
    
    @Autowired
    private CodeEsService codeEsService;
    /**
     * 扫码授权后验证
     * @param openid
     * @param nickname
     * @param state
     * @return
     */
    public RestResult<String> deal(String openid,String nickname,String state){
    	RestResult<String> restResult=new RestResult<String>();
        ScanCodeInfoMO scCodeInfoMO=GlobalRamCache.scanCodeInfoMap.get(state);
    	//1、从es查询该码有没有被扫过
    	Long count=codeEsService.countByCode(scCodeInfoMO.getCodeId(), scCodeInfoMO.getCodeTypeId());
    	if (null!=count && count>0) {
    		restResult.setState(500);
    		restResult.setMsg("该码已被扫过");
    		return restResult;
    	}
    	Long activitySetId=scCodeInfoMO.getActivitySetId();
        //TODO 获取根据产品信息获取组织id
        String organizationId=null;
        //2、判断该用户是否存在及状态是否合法
        MarketingMembers member=membersService.selectByOpenIdAndOrgId(openid,organizationId);
        if (null==member) {
    		//TODO 添加用户
    	}else {
    		Byte userstate=member.getState();
    		if (null!=userstate && 0==userstate) {
    			restResult.setState(500);
        		restResult.setMsg("当前用户已被禁用，请联系企业管理员");
        		return restResult;
    		}
    		//5、判断该用户是否超过每天的扫码量
    		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    		Long userscancodenum=codeEsService.countByUserAndActivityQuantum(member.getUserId(), activitySetId, format.format(new Date()));
    		
    		Integer dayscannum=activitySetService.selectEachDayNumber(activitySetId);
    		if (null!=userscancodenum) {
    			//如果用户今天扫码数已超过活动设置的每人每天最多扫码次数则返回
				if (userscancodenum.intValue()>=dayscannum.intValue()) {
					restResult.setState(500);
	        		restResult.setMsg("当前用户扫码次数已达到本活动设置每人每日扫码上限，请明天再试");
	        		return restResult;
				}
			}
    	}
		return restResult;
        
      
       //3.判断是否需要手机登录及注册及绑定对应的openid或更新手机号

    }

}
