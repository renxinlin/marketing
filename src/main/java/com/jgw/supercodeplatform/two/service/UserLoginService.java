package com.jgw.supercodeplatform.two.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.BindConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.two.constants.JudgeBindConstants;
import com.jgw.supercodeplatform.two.dto.MarketingSaleUserBindMobileParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fangshiping
 * @date 2019/11/14 14:15
 */
@Service
public class UserLoginService {
    private static Logger logger = Logger.getLogger(UserLoginService.class);

    @Autowired
    private MarketingUserMapper marketingUserMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    protected ModelMapper modelMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public RestResult bindMobile(MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam) throws SuperCodeException{
        if(StringUtils.isBlank(marketingSaleUserBindMobileParam.getMobile())){
            throw new SuperCodeException("手机号不存在");
        }

        if(StringUtils.isBlank(marketingSaleUserBindMobileParam.getVerificationCode())){
            throw new SuperCodeException("验证码不存在");
        }

        boolean success = commonService.validateMobileCode(marketingSaleUserBindMobileParam.getMobile(), marketingSaleUserBindMobileParam.getVerificationCode());
        if(!success){
            throw new SuperCodeException("验证码校验失败");
        }

        MarketingUser marketingUserTwo=marketingUserMapper.selectById(marketingSaleUserBindMobileParam.getId());
        if (marketingUserTwo ==null){
            throw new SuperCodeException("绑定2.0失败");
        }
        //导购员:手机号全局唯一
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("Mobile",marketingSaleUserBindMobileParam.getMobile());
        MarketingUser exitMarketingUser=marketingUserMapper.selectOne(queryWrapper);
        Integer result = null;
        if (exitMarketingUser != null){
            /*throw new SuperCodeException("该手机号已被绑定");*/
            //说明3.0数据中已绑定手机号
            //进行积分转移
            //可用积分和总积分
            //未绑定进行绑定
            if (exitMarketingUser.getBinding()==null ||JudgeBindConstants.NOBIND.equals(exitMarketingUser.getBinding())){
                exitMarketingUser.setHaveIntegral(
                        (exitMarketingUser.getHaveIntegral()== null ? 0:exitMarketingUser.getHaveIntegral())
                                +(marketingUserTwo.getHaveIntegral()== null ? 0:marketingUserTwo.getHaveIntegral())
                                +BindConstants.SUCCESS);
                exitMarketingUser.setTotalIntegral(
                        (exitMarketingUser.getTotalIntegral()== null ? 0:exitMarketingUser.getTotalIntegral())
                                +(marketingUserTwo.getTotalIntegral()== null ? 0:marketingUserTwo.getTotalIntegral())
                                +BindConstants.SUCCESS);
                exitMarketingUser.setBinding(JudgeBindConstants.HAVEBIND);
                marketingUserTwo.setHaveIntegral(0);
                marketingUserTwo.setTotalIntegral(0);
                result=marketingUserMapper.updateById(exitMarketingUser);
                marketingUserMapper.updateById(marketingUserTwo);
            }
        }
        else{
            //不存在则将2.0的数据复制到3.0

            MarketingUser  marketingUserNew = new MarketingUser();
            BeanUtils.copyProperties(marketingUserTwo,marketingUserNew,"id");
            marketingUserNew.setMobile(marketingSaleUserBindMobileParam.getMobile());
            marketingUserNew.setHaveIntegral(
                    (marketingUserNew.getHaveIntegral()== null ? 0:marketingUserNew.getHaveIntegral())
                            +BindConstants.SUCCESS);
            marketingUserNew.setTotalIntegral(
                    (marketingUserNew.getTotalIntegral()== null ? 0:marketingUserNew.getTotalIntegral())
                            +BindConstants.SUCCESS);
            marketingUserNew.setLoginName("");
            marketingUserNew.setPassword("");
            marketingUserTwo.setBinding(JudgeBindConstants.HAVEBIND);
            marketingUserTwo.setId(marketingSaleUserBindMobileParam.getId());
            //插入一条新数据
            logger.info("------marketingUserNew-----"+marketingUserNew.toString());
            result=marketingUserMapper.insert(marketingUserNew);
            logger.info("------marketingMembersTwo-----"+marketingUserTwo );
            marketingUserMapper.updateById(marketingUserTwo);
        }
        if (result.equals(BindConstants.RESULT)){
            return RestResult.success(200,"success","绑定成功");
        }
        return RestResult.failDefault("绑定失败");
    }
}
