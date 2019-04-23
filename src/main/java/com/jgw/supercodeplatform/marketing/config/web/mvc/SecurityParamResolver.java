package com.jgw.supercodeplatform.marketing.config.web.mvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.exception.UserExpireException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

/**
 * mvc参数额外解析：JwtUser.class
 * 注意：凡是CONTROLLER接口参数注入JWTUSER全部会在这里被解析
 */
@Component
public class SecurityParamResolver implements HandlerMethodArgumentResolver {
    private static Logger logger = LoggerFactory.getLogger(SecurityParamResolver.class);


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
            // HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
           // token = request.getHeader(CommonConstants.JWT_TOKEN);
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(CommonConstants.JWT_TOKEN.equals(cookie.getName())){
                    token = cookie.getValue();
                }
            }
            if (token == null) {
                throw new UserExpireException("用户不存在...");
            }
            H5LoginVO jwtUser = JWTUtil.verifyToken(token);
            if (jwtUser == null || jwtUser.getMemberId() == null) {
                logger.error("jwt信息不全" + jwtUser);
                throw new UserExpireException("用户信息不存在...");
            }
            return jwtUser;
        } catch (Exception e) {
            logger.error("解析jwt异常" + token);
            e.printStackTrace();
            throw new UserExpireException("用户信息获取失败...");
        }
    }
}
