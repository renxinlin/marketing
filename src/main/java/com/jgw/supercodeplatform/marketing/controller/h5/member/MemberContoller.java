package com.jgw.supercodeplatform.marketing.controller.h5.member;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dto.members.H5MembersInfoParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/marketing/h5/member")
@Api(tags = "H5会员信息管理")
public class MemberContoller {


    @Autowired
    private MarketingMembersService service;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(value = "/getMemberId",method = RequestMethod.GET)
    @ApiOperation(value = "获取会员详情|获取会员积分", notes = "")
    @ApiImplicitParams(value= {  @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult<H5MembersInfoParam> get(@ApiIgnore H5LoginVO jwtUser) throws Exception {
        MarketingMembers memberById = service.getMemberById(jwtUser.getMemberId());
        H5MembersInfoParam memberVO = modelMapper.map(memberById, H5MembersInfoParam.class);
        return RestResult.success("success", memberVO);

    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新会员信息|获取会员积分", notes = "")
    @ApiImplicitParams(value= {  @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "ldpfbsujjknla;s.lasufuafpioquw949gyobrljaugf89iweubjkrlnkqsufi.awi2f7ygihuoquiu", value = "jwt-token信息", required = true)
    })
    public RestResult update(@RequestBody MarketingMembersUpdateParam member, @ApiIgnore H5LoginVO jwtUser ) throws Exception {
        // 通过jwt-token + H5LoginVO保证接口安全
        // 验证码处理: 目前验证码维度为手机维度,不同业务共用KEY,不影响功能
        // 如出现验证码覆盖情况则重新发送验证码
        String verificationCode = redisUtil.get(RedisKey.phone_code_prefix + member.getMobile());
        if(StringUtils.isBlank(verificationCode)){
            throw new SuperCodeException("验证码不存在");
        }
        if (member.getVerificationCode() == null || !verificationCode.equals(member.getVerificationCode())){
            // 可能是业务覆盖
            throw new SuperCodeException("验证码错误,请重新发送");
        }
        MarketingMembers memberDto = modelMapper.map(member, MarketingMembers.class);
        service.update(memberDto);
        return RestResult.success("success",null);

    }




}