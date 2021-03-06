package com.jgw.supercodeplatform.marketing.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*",filterName = "CrossDomainFilter")
public class CrossDomainFilter implements Filter{
	
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		//设置支持跨域
		resp.setHeader("Access-Control-Allow-Origin", "*");  
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
		resp.setHeader("Access-Control-Max-Age", "3600");  
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,Origin");  
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Content-type", "application/json;charset=UTF-8");
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
		
	}

}
