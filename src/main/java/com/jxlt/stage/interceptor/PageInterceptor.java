package com.jxlt.stage.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.model.User;

public class PageInterceptor implements HandlerInterceptor{

	private List<String> allowAction;
	
	//后台地址
	public String[] listUri = {"/User/","/Auto/","/Group/","/Order/","/Statistic/"};
	
	public List<String> getAllowAction() {
		return allowAction;
	}

	public void setAllowAction(List<String> allowAction) {
		this.allowAction = allowAction;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		Logger log = Logger.getLogger(method.getClass());
		String requestUri = request.getRequestURI();
		//System.out.println(requestUri);
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		log.info("PageInterceptor [" + method.getBean() +":"+url+ " Started...]");
		log.info(" ");
		User loginUser = (User)request.getSession().getAttribute(Constants.USER_SESSION_NAME);
		
		for(String uri:listUri){
			if(requestUri.contains(uri)){
				if(loginUser == null){
					response.sendRedirect("../index.do");
					//request.getRequestDispatcher("/page/login.jsp").forward(request,response);
					return false;
				}else{
					return true;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerMethod h =  (HandlerMethod)handler;
		Logger log =Logger.getLogger(h.getClass());
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		//System.out.println(url.substring(url.lastIndexOf("/")+1));
		request.setAttribute("requestCurrURL", url.substring(url.lastIndexOf("/")+1));
		log.info("PageInterceptor [" + h.getBean() +":"+url+ " Normally End.]");
	}
}
