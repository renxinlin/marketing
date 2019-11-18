package com.jgw.supercodeplatform.two.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.BindConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.two.dto.MarketingMembersBindMobileParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fangshiping
 * @date 2019/11/14 14:15
 */
@Service
public class MemberLoginService {

    @Autowired
    private MarketingMembersMapper marketingMembersMapper;


    @Autowired
    private CommonService commonService;


    /**
     * 设置H5LoginVO
     * @param marketingMembers
     * @return
     */
    public H5LoginVO setH5LoginVO(MarketingMembers marketingMembers){
        System.out.println(marketingMembers.toString());
        H5LoginVO h5LoginVO=new H5LoginVO();
        h5LoginVO.setMemberId(marketingMembers.getId());
        h5LoginVO.setMemberName(marketingMembers.getUserName());
        h5LoginVO.setRegistered(marketingMembers.getIsRegistered());
        h5LoginVO.setOrganizationId(marketingMembers.getOrganizationId());
        h5LoginVO.setCustomerId(marketingMembers.getCustomerId());
        h5LoginVO.setCustomerName(marketingMembers.getCustomerName());
        h5LoginVO.setHaveIntegral(marketingMembers.getHaveIntegral());
        h5LoginVO.setMobile(marketingMembers.getMobile());
        h5LoginVO.setOpenid(marketingMembers.getOpenid());
        h5LoginVO.setMemberType(marketingMembers.getMemberType());
        return h5LoginVO;
    }



    /**
     * 绑定手机号
     * @param marketingMembersBindMobileParam
     * @throws SuperCodeException
     */
    public RestResult bindMobile(MarketingMembersBindMobileParam marketingMembersBindMobileParam,H5LoginVO h5LoginVO) throws SuperCodeException{
        if(StringUtils.isBlank(marketingMembersBindMobileParam.getMobile())){
            throw new SuperCodeException("手机号不存在");
        }

        if(StringUtils.isBlank(marketingMembersBindMobileParam.getVerificationCode())){
            throw new SuperCodeException("验证码不存在");
        }
        //会员：一个组织中一个手机号唯一
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("Mobile",marketingMembersBindMobileParam.getMobile());
        queryWrapper.eq("OrganizationId",h5LoginVO.getOrganizationId());
        MarketingMembers exitMarketingMembers=marketingMembersMapper.selectOne(queryWrapper);
        if (exitMarketingMembers != null){
            throw new SuperCodeException("该手机号已被绑定");
        }

        boolean success = commonService.validateMobileCode(marketingMembersBindMobileParam.getMobile(), marketingMembersBindMobileParam.getVerificationCode());
        if(!success){
            throw new SuperCodeException("验证码校验失败");
        }
        MarketingMembers marketingMembers=new MarketingMembers();
        marketingMembers.setId(h5LoginVO.getMemberId());
        marketingMembers.setMobile(marketingMembersBindMobileParam.getMobile());
        Integer result=marketingMembersMapper.updateById(marketingMembers);
        if (result.equals(BindConstants.RESULT)){
            MarketingMembers newMarketingMembers=marketingMembersMapper.selectById(h5LoginVO.getMemberId());
            newMarketingMembers.setHaveIntegral(newMarketingMembers.getHaveIntegral()+BindConstants.SUCCESS);
            marketingMembersMapper.updateById(newMarketingMembers);
            return RestResult.success();
        }
        return RestResult.failDefault("绑定失败");
    }
}
