package com.jgw.supercodeplatform.two.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.BindConstants;
import com.jgw.supercodeplatform.marketing.common.constants.StateConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.two.constants.JudgeBindConstants;
import com.jgw.supercodeplatform.two.dto.MarketingMembersBindMobileParam;
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
public class MemberLoginService {

    private static Logger logger = Logger.getLogger(MemberLoginService.class);

    @Autowired
    private MarketingMembersMapper marketingMembersMapper;


    @Autowired
    private CommonService commonService;

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    private IntegralRuleService integralRuleService;

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
    @Transactional(rollbackFor = Exception.class)
    public RestResult bindMobile(MarketingMembersBindMobileParam marketingMembersBindMobileParam) throws SuperCodeException{
        if(StringUtils.isBlank(marketingMembersBindMobileParam.getMobile())){
            throw new SuperCodeException("手机号不存在");
        }

        if(StringUtils.isBlank(marketingMembersBindMobileParam.getVerificationCode())){
            throw new SuperCodeException("验证码不存在");
        }

        boolean success = commonService.validateMobileCode(marketingMembersBindMobileParam.getMobile(), marketingMembersBindMobileParam.getVerificationCode());
        if(!success){
            throw new SuperCodeException("验证码校验失败");
        }

        //ID用于获取2.0数据
        MarketingMembers marketingMembersTwo=marketingMembersMapper.selectById(marketingMembersBindMobileParam.getId());
        if (marketingMembersTwo==null){
            throw new SuperCodeException("绑定2.0失败");
        }
        //会员：一个组织中一个手机号唯一
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("Mobile",marketingMembersBindMobileParam.getMobile());
        queryWrapper.eq("OrganizationId",marketingMembersBindMobileParam.getOrganizationId());
        MarketingMembers exitMarketingMembers=marketingMembersMapper.selectOne(queryWrapper);
        Integer result = null;
        IntegralRule integralRule=integralRuleService.selectByOrgId(marketingMembersBindMobileParam.getOrganizationId());
        if (exitMarketingMembers != null){
            /*throw new SuperCodeException("该手机号已被绑定");*/
            //说明3.0数据中已绑定手机号
            //进行积分转移
            //可用积分和总积分
            if (exitMarketingMembers.getBinding()==null ||JudgeBindConstants.NOBIND.equals(exitMarketingMembers.getBinding())){
                exitMarketingMembers.setHaveIntegral(
                        (exitMarketingMembers.getHaveIntegral() == null ? 0: exitMarketingMembers.getHaveIntegral())
                                +(marketingMembersTwo.getHaveIntegral() == null ? 0: marketingMembersTwo.getHaveIntegral())
                                );
                exitMarketingMembers.setTotalIntegral(
                        (exitMarketingMembers.getTotalIntegral() == null ? 0: exitMarketingMembers.getTotalIntegral())
                                + (marketingMembersTwo.getTotalIntegral()  == null ? 0: marketingMembersTwo.getTotalIntegral())
                                );
                marketingMembersTwo.setHaveIntegral(0);
                marketingMembersTwo.setTotalIntegral(0);
                result=marketingMembersMapper.updateById(exitMarketingMembers);
                logger.info("------marketingMembersTwo-----"+marketingMembersTwo);
                marketingMembersMapper.updateById(marketingMembersTwo);
            }
        }else{
            //不存在则将2.0的数据复制到3.0
            MarketingMembers marketingMembersNew=new MarketingMembers();
            BeanUtils.copyProperties(marketingMembersTwo,marketingMembersNew,"id");
            marketingMembersNew.setMobile(marketingMembersBindMobileParam.getMobile());
            marketingMembersNew.setHaveIntegral(
                    (marketingMembersNew.getHaveIntegral()== null ? 0:marketingMembersNew.getHaveIntegral())
                            +(integralRule.getIntegralByRegister()== null ? 0:integralRule.getIntegralByRegister()));
            marketingMembersNew.setTotalIntegral(
                    (marketingMembersNew.getTotalIntegral()== null ? 0:marketingMembersNew.getTotalIntegral())
                            +(integralRule.getIntegralByRegister()== null ? 0:integralRule.getIntegralByRegister()));
            marketingMembersNew.setLoginName("");
            marketingMembersNew.setPassword("");
            marketingMembersNew.setState(StateConstants.ENABLE);
            marketingMembersTwo.setBinding(JudgeBindConstants.HAVEBIND);
            //插入一条新数据
            result=marketingMembersMapper.insert(marketingMembersNew);
            logger.info("------marketingMembersTwo-----"+marketingMembersTwo);
            marketingMembersMapper.updateById(marketingMembersTwo);
        }

        if (result.equals(BindConstants.RESULT)){
            return RestResult.success(200,"success","绑定成功");
        }
        return RestResult.failDefault("绑定失败");
    }
}
