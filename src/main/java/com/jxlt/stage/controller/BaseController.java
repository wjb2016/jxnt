package com.jxlt.stage.controller;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import org.apache.log4j.Logger; 
import org.apache.shiro.SecurityUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.model.User;


 

public abstract class BaseController extends CommonsMultipartResolver {
	protected Logger log = Logger.getLogger(getClass());

	private String resultCode;
	private String resultMessage;
	
	private HttpServletRequest req;
	private HttpServletResponse res;
	

	public HttpServletRequest getReq() {
		return req;
	}

	public void setReq(HttpServletRequest req) {
		this.req = req;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public void setRes(HttpServletResponse res) {
		this.res = res;
	}
 
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	/**
	 * 登陆用户session
	 * 
	 * @return
	 */
	protected User getLoginUser() {
		User loginUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.USER_SESSION_NAME);
		return loginUser;
	}

	/**
	 * 鐢ㄦ埛session
	 * 
	 * @param loginUser
	 */
	protected void setLoginUser(User loginUser) {
		SecurityUtils.getSubject().getSession().setAttribute(Constants.USER_SESSION_NAME, loginUser);
	}
	
}
