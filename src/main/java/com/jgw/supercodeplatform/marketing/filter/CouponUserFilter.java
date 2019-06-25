package com.jgw.supercodeplatform.marketing.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.exception.UserExpireException;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

@WebFilter(urlPatterns = "/marketing/front/coupon/*",filterName = "CouponUserFilter")
public class CouponUserFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(CouponUserFilter.class);
	
	private final String[] memberPaths = {"/listCoupon","/obtainCoupon"};
	
	private final String[] salerPaths = {"/couponVerify","/listVerify"};
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			String token = null;
			Cookie[] cookies = ((HttpServletRequest) request).getCookies();
			if (null!=cookies) {
				for(Cookie cookie : cookies){
					if(CommonConstants.JWT_TOKEN.equals(cookie.getName())){
						token = cookie.getValue();
					}
				}
			}
			if (token == null) {
				token=((HttpServletRequest) request).getHeader(CommonConstants.JWT_TOKEN);
				if (token == null) {
					throw new UserExpireException("用户不存在...");
				}
			}
			H5LoginVO jwtUser = JWTUtil.verifyToken(token);
			if(jwtUser == null) 
				throw new UserExpireException("用户不存在...");
			String servletPath = ((HttpServletRequest) request).getServletPath();
			if(jwtUser.getMemberType() == null)
				throw new UserExpireException("用户类型不对...");
			if(jwtUser.getMemberType().byteValue() == MemberTypeEnums.VIP.getType().byteValue()) {
				for(String path : salerPaths) {
					if(servletPath.contains(path))
						throw new SuperCodeException("无权限,会员用户不能操作导购");
				}
			}
			if(jwtUser.getMemberType().byteValue() == MemberTypeEnums.SALER.getType().byteValue()) {
				for(String path : memberPaths) {
					if(servletPath.contains(path))
						throw new SuperCodeException("无权限,导购不能操作会员");
				}
			}
		} catch (Exception e) {
			logger.error("CouponUserFilter出错", e);
			throw new ServletException(e.getMessage());
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
