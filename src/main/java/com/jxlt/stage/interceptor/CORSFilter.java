package com.jxlt.stage.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
//@Component
public class CORSFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;  
		resp.setHeader("Access-Control-Allow-Origin", "*");  
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");  
		resp.setHeader("Access-Control-Max-Age", "3600");  
		resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
	    System.out.println("跨域过滤！");
		chain.doFilter(request, resp);
	}

	@Override
	public void destroy() {
	}
}
