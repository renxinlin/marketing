package com.jgw.supercodeplatform.burypoint.prizewheels.util;

import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.exception.UserExpireException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fangshiping
 * @date 2019/11/27 14:14
 */
@Component
@Slf4j
public class H5LoginUtil {
 
    public H5LoginVO get(HttpServletRequest request){
        String token = null;
        try {
            log.info("开始解析方法："+request+"jwtToken");
            // HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
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


            if (jwtUser == null || jwtUser.getMemberId() == null) {
                log.warn("jwt信息不全" + jwtUser);
                // 重新登录的异常信息
                throw new UserExpireException("用户信息不存在...");
            }

            return jwtUser;
        } catch (Exception e) {
            log.warn("解析jwt异常" + token);
            e.printStackTrace();
            // 重新登录的异常信息
            return null;
        }
    }

}
