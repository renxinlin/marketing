package com.jgw.supercodeplatform.two.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.BindConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.two.dto.MarketingSaleUserBindMobileParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fangshiping
 * @date 2019/11/14 14:15
 */
@Service
public class UserLoginService {
    @Autowired
    private MarketingUserMapper marketingUserMapper;

    @Autowired
    private CommonService commonService;


    /**
     * 设置H5LoginVO
     * @param marketingUser
     * @return
     */
    public H5LoginVO setH5LoginVO(MarketingUser marketingUser){
        H5LoginVO h5LoginVO=new H5LoginVO();
        h5LoginVO.setMemberId(marketingUser.getId());
        h5LoginVO.setMemberName(marketingUser.getUserName());
        h5LoginVO.setOrganizationId(marketingUser.getOrganizationId());
        h5LoginVO.setCustomerId(marketingUser.getCustomerId());
        h5LoginVO.setCustomerName(marketingUser.getCustomerName());
        h5LoginVO.setHaveIntegral(marketingUser.getHaveIntegral());
        h5LoginVO.setMobile(marketingUser.getMobile());
        h5LoginVO.setOpenid(marketingUser.getOpenid());
        h5LoginVO.setWechatHeadImgUrl(marketingUser.getWechatHeadImgUrl());
        h5LoginVO.setMemberType(marketingUser.getMemberType());
        return h5LoginVO;
    }



    /**
     * 绑定手机号
     * @param marketingSaleUserBindMobileParam
     * @throws SuperCodeException
     */
    public RestResult bindMobile(MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam, H5LoginVO h5LoginVO) throws SuperCodeException{
        if(StringUtils.isBlank(marketingSaleUserBindMobileParam.getMobile())){
            throw new SuperCodeException("手机号不存在");
        }

        if(StringUtils.isBlank(marketingSaleUserBindMobileParam.getVerificationCode())){
            throw new SuperCodeException("验证码不存在");
        }

        //导购员:手机号全局唯一
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("Mobile",marketingSaleUserBindMobileParam.getMobile());
        MarketingUser exitMarketingUser=marketingUserMapper.selectOne(queryWrapper);
        if (exitMarketingUser != null){
            throw new SuperCodeException("该手机号已被绑定");
        }
        boolean success = commonService.validateMobileCode(marketingSaleUserBindMobileParam.getMobile(), marketingSaleUserBindMobileParam.getVerificationCode());
        if(!success){
            throw new SuperCodeException("验证码校验失败");
        }
        MarketingUser marketingUser=new MarketingUser();
        marketingUser.setId(h5LoginVO.getMemberId());
        marketingUser.setMobile(marketingSaleUserBindMobileParam.getMobile());
        Integer result=marketingUserMapper.updateById(marketingUser);
        if (result.equals(BindConstants.RESULT)){
            MarketingUser newMarketingUser=marketingUserMapper.selectById(h5LoginVO.getMemberId());
            newMarketingUser.setHaveIntegral(newMarketingUser.getHaveIntegral()+BindConstants.SUCCESS);
            marketingUserMapper.updateById(newMarketingUser);
            return RestResult.success();
        }
        return RestResult.failDefault("绑定失败");
    }
}
