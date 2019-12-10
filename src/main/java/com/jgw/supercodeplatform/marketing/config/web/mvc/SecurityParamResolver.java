package com.jgw.supercodeplatform.marketing.config.web.mvc;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketing.exception.UserExpireException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.UserMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.MembersMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.MembersPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc参数额外解析：JwtUser.class
 * 注意：凡是CONTROLLER接口参数注入JWTUSER全部会在这里被解析
 */
@Component
@Slf4j
public class SecurityParamResolver implements HandlerMethodArgumentResolver {
    @Autowired private MembersMapper membersMapper;
    @Autowired private UserMapper userMapper;

    @Value("${cookie.domain}")
    private String domain;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz=methodParameter.getParameterType();
        // 指定什么样的对象需要被解析
        return clazz== H5LoginVO.class;
    }

    /**
     * controller 方法参数包含 JwtUser则进行解析;当解析失败返回null
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public H5LoginVO resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws UserExpireException {
        String token = null;
        try {
            String methodName=methodParameter.getMethod().getName();
            log.info("开始解析方法："+methodName+"jwtToken");
            // HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            // token = request.getHeader(CommonConstants.JWT_TOKEN);
            Cookie[] cookies = request.getCookies();
            if (null!=cookies) {
                for(Cookie cookie : cookies){
                    if(CommonConstants.JWT_TOKEN.equals(cookie.getName())){
                        token = cookie.getValue();
                    }
                }
            }
            if (token == null) {
                token=request.getHeader(CommonConstants.JWT_TOKEN);
                if (token == null) {
                    throw new UserExpireException("用户不存在...");
                }
            }
            H5LoginVO jwtUser = JWTUtil.verifyToken(token);


            checkUserStatus(jwtUser);


            if (jwtUser == null || jwtUser.getMemberId() == null) {
                log.error("jwt信息不全" + jwtUser);
                // 重新登录的异常信息
                throw new UserExpireException("用户信息不存在...");
            }
            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            try {
                String jwtToken=JWTUtil.createTokenWithClaim(jwtUser);
                Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
                // 两小时登录信息
                jwtTokenCookie.setMaxAge(60*60*2);
                jwtTokenCookie.setPath("/");
                jwtTokenCookie.setDomain(domain);
                response.addCookie(jwtTokenCookie);
                response.setHeader(CommonConstants.JWT_TOKEN,jwtToken);
            }catch (Exception e){
                e.printStackTrace();
            }
            return jwtUser;
        } catch (Exception e) {
            log.error("解析jwt异常" + token);
            e.printStackTrace();
            // 重新登录的异常信息
            throw new UserExpireException("用户信息获取失败...");
        }
    }

    /**
     * 导购status 用户状态(1、 待审核，2 停用3启用)导购员状态
     * 会员status 会员状态(1、 表示正常，0 表示下线)状态2表示临时数据
     * @desc 以上状态copy自数据库comment
     * @param jwtUser
     */
    private void checkUserStatus(H5LoginVO jwtUser) {
        Byte memberType = jwtUser.getMemberType();
        Asserts.check( memberType!=null,"会员角色无法鉴定...");
        if(RoleTypeEnum.GUIDE.getMemberType() == memberType.intValue()){
            // 验证导购合法性
            User user = userMapper.selectById(jwtUser.getMemberId());
            if(user.getState() != 3){
                throw new BizRuntimeException("用户未启用");
            }
        }else if(RoleTypeEnum.MEMBER.getMemberType() == memberType.intValue()){
            // 验证会员合法性
            MembersPojo membersPojo = membersMapper.selectById(jwtUser.getMemberId());

            if(membersPojo.getState() != 1){
                throw new BizRuntimeException("用户未启用");
            }
//
//            if(!membersPojo.getIsRegistered()){
//                throw new BizRuntimeException("用户未注册");
//            }
        }else {
            throw new BizRuntimeException("用户角色非法");

        }
    }
}
