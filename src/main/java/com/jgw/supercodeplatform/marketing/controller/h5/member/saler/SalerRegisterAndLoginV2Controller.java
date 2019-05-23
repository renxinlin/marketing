package com.jgw.supercodeplatform.marketing.controller.h5.member.saler;


import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.constants.PcccodeConstants;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpsUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dto.CustomerInfo;
import com.jgw.supercodeplatform.marketing.dto.MarketingSaleMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.SalerLoginParam;
import com.jgw.supercodeplatform.marketing.enums.market.BrowerTypeEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 废弃V1的后台处理微信授权问题,交给前端处理
 *
 */
@Controller
@RequestMapping("/marketing/front/saler/v2")
@Api(tags = "销售员登录注册")
public class SalerRegisterAndLoginV2Controller {

    private Logger logger = LoggerFactory.getLogger(SalerRegisterAndLoginV2Controller.class);

//    redirect_uri域名与后台配置不一致则失败
    private final String redirctUrl              = "http://marketing.kf315.net/marketing/front/saler/register";
    private final String loginRedirctUrl              = "http://marketing.kf315.net/marketing/front/saler/update";
    // 获取code
    private final String OAUTH2_WX_URL           = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx32ab5628a5951ecc&redirect_uri="+"[backUrl]"+"&response_type=code&scope=snsapi_base&state="+"[mobile]"+"#wechat_redirect";
    // 获取一次性access_token openid
    private final String openidandaccesstokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx32ab5628a5951ecc&secret=e3fb09c9126cd8bc12399e56a35162c4&code=[code]&grant_type=authorization_code";
    @Autowired
    private MarketingSaleMemberService service;
    // TODO 前端销售员中心
    @Value("https://www.baidu.com")
    private String WEB_URL ;
    /**
     * 注册的临时信息前缀
     */
//    @Value("${redis.saler.register}")
    private static final String REGISTER_PERFIX = "saler:register:";
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${cookie.domain}")
    private String cookieDomain;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 登录
     * @param loginUser
     * @param response
     * @return
     * @throws SuperCodeException
     */
    @ResponseBody
    @GetMapping("login")
    @ApiOperation(value = "手机号登录", notes = "")
    public RestResult<MarketingUser> login(SalerLoginParam loginUser ,HttpServletResponse response) throws SuperCodeException{
            // 或者 微信回调携带openid登录
            // 手机登录
            // TODO  验证码
            MarketingUser user = service.selectBylogin(loginUser);
            // 写jwt
            if(user != null){
                if(loginUser.getOpenid()!= null){
                    // 说明微信登录失败,但用户存在
                    // 说明没绑定openid
                    Long id = user.getId();
                    user = new MarketingUser();
                    user.setId(id);
                    user.setOpenid(loginUser.getOpenid());
                    service.updateUserOpenId(user);
                }
                H5LoginVO jwtUser = new H5LoginVO();
                jwtUser.setMobile(loginUser.getMobile());
                jwtUser.setMemberId(user.getId());
                jwtUser.setOrganizationId(loginUser.getOrganizationId());
                // TODO 可能存在其他登录信息需要设置

                String jwtToken = JWTUtil.createTokenWithClaim(jwtUser);
                Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
                // jwt有效期为2小时，保持一致
                jwtTokenCookie.setMaxAge(60*60*2);
                jwtTokenCookie.setPath("/");
                jwtTokenCookie.setDomain(cookieDomain);
                response.addCookie(jwtTokenCookie);
            }else{
                // 说明用户不存在
                return RestResult.error("请前往注册后登录",null,401);

            }


        // 异步授权openid
        return RestResult.success("success",null);
    }


   @GetMapping("/tempRegister")
   @ResponseBody
   @ApiOperation(value = "临时注册 此版本为前端授权后调用的注册接口", notes = "")
   public RestResult loadingRegisterBeforeWxReturnOpenId(MarketingSaleMembersAddParam userInfo, HttpServletResponse response) throws SuperCodeException, IOException {
           // 直接保存 有微信用户时携带openiD访问该接口
         service.saveRegisterUser(userInfo);
         return RestResult.success();
   }





    /**
     * 访问微信https
     * @param openidandaccesstokenUrl
     * @return
     */
    private String getOpenid(String openidandaccesstokenUrl) {
        String result = HttpsUtil.get(openidandaccesstokenUrl,null);
        return  result;
    }


}