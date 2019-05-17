package com.jgw.supercodeplatform.marketing.controller.h5.member.saler;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.PcccodeConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dto.CustomerInfo;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.SalerLoginParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 组织信息怎么获取
 * 从公众号获取组织ID??????????
 */
@Controller
@RequestMapping("/marketing/front/saler")
@Api(tags = "h5用户注册登录信息完善点击领奖")
public class SalerController {
    // TODO 组织id获取问题
    // TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题// TODO 组织id获取问题
    // TODO 组织id获取问题


    @Autowired
    private MarketingSaleMemberService service;
    /**
     * 注册的临时信息前缀
     */
    private static final String REGISTER_PERFIX = "saler:register:";
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ModelMapper modelMapper;
    /**
     *  导购员注册详解
     *      必要条件:用户注册必须携带openid
     *      首先调用此接口注册：获取用户信息并缓存
     *      并返回相关获取openid的微信oauth2配置信息已经重定向接口
     *      前端携带此信息访问微信授权
     *      微信返回openid跳转注册保存接口
     *      获取缓存用户信息和openid正式保存
     * @param userInfo
     * @return
     */
   @RequestMapping("/tempRegister")
   public String loadingRegisterBeforeWxReturnOpenId(@RequestBody  MarketingSaleMembersAddParam userInfo) throws SuperCodeException{
       // 临时缓存用户信息;此时用户无组织信息
       registerAsTempWaybeforeAuquireOpenId(userInfo);
       // 获取微信配置信息 同时传递手机号
       // 重定向到微信授权
       // 直接请求微信静默授权
       // 微信重定向到 保存用户信息接口
       return "redirct:"+"";
   }


   @ResponseBody
   @RequestMapping("register")
   public RestResult<String> saveRegisterInfo(String openId,String mobile) throws SuperCodeException {
       if(StringUtils.isBlank(mobile)){
           throw new SuperCodeException("微信回调手机号失败...");
       }

       if(StringUtils.isBlank(openId)){
           throw new SuperCodeException("获取openId失败...");

       }
       MarketingUser marketingUser = null;
       try {
           String userDtoString = redisUtil.get(REGISTER_PERFIX + mobile);
           marketingUser = JSONObject.parseObject(userDtoString, MarketingUser.class);
           marketingUser.setOpenid(openId);
       } catch (Exception e) {
           throw new SuperCodeException("获取临时用户信息失败...");
       }

       service.saveUser(marketingUser);

       return RestResult.success();


   }

    private void registerAsTempWaybeforeAuquireOpenId(MarketingSaleMembersAddParam userInfo) throws SuperCodeException{
       if(userInfo == null){
           throw new SuperCodeException("注册信息获取失败...");
       }
        if(StringUtils.isBlank(userInfo.getUserName())){
            throw new SuperCodeException("用户名必填...");
        }
        // 校验转化成省市区是否成功
        if(StringUtils.isBlank(userInfo.getPCCcode())){
            throw new SuperCodeException("地址信息必填...");
        }
        // 校验机构信息
        if(CollectionUtils.isEmpty(userInfo.getCustomer())){
            throw new SuperCodeException("机构信息必填...");
        }
        if(StringUtils.isBlank(userInfo.getMobile())){
            throw new SuperCodeException("手机号必填...");

        }else {
            // 校验手机格式
            commonUtil.checkPhoneFormat(userInfo.getMobile());
        }
        if(StringUtils.isBlank(userInfo.getVerificationCode())){
            throw new SuperCodeException("验证码必填...");
         }
        String mobile = userInfo.getMobile();
        String redisPhoneCode=redisUtil.get(RedisKey.phone_code_prefix+ mobile);
        if (StringUtils.isBlank(redisPhoneCode) ) {
            throw new SuperCodeException("验证码不存在或已过期请重新获取验证码",500);
        }
        if (!redisPhoneCode.equals(userInfo.getVerificationCode())) {
            throw new SuperCodeException("验证码不正确",500);
        }


        // 数据处理
        MarketingUser dto = modelMapper.map(userInfo,MarketingUser.class);
        // 省市区编码
        String pcccode = userInfo.getPCCcode();
        List<JSONObject> objects = JSONObject.parseArray(pcccode,JSONObject.class);
        JSONObject province = objects.get(0);
        JSONObject city = objects.get(1);
        JSONObject country = objects.get(2);
        dto.setProvinceCode(province.getString(PcccodeConstants.areaCode));
        dto.setCityCode(city.getString(PcccodeConstants.areaCode));
        dto.setCountyCode(country.getString(PcccodeConstants.areaCode));
        dto.setProvinceName(province.getString(PcccodeConstants.areaName));
        dto.setCityName(city.getString(PcccodeConstants.areaName));
        dto.setCountyName(country.getString(PcccodeConstants.areaName));
        dto.setpCCcode(pcccode);
        // 机构信息
        List<CustomerInfo> customers = userInfo.getCustomer();
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


        // 缓存注册信息 5 MINUTES
        redisUtil.set(REGISTER_PERFIX+dto.getMobile(),JSONObject.toJSONString(dto),60*5L);
    }

    @ResponseBody
    @RequestMapping("login")
    public RestResult login(@RequestBody SalerLoginParam loginUser){
       // TODO 组织id问题
       MarketingUser user = service.selectBylogin(loginUser);
        // 写jwt

        return RestResult.success();
    }

}
