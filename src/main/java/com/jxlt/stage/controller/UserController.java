package com.jxlt.stage.controller;
   

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.common.utils.MD5Util;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.LogService;
import com.jxlt.stage.service.UserService;
import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.StringUtil;

@Scope("prototype")
@Controller
@RequestMapping("/User")
public class UserController extends BaseController { 
    
	@Resource(name = "userService")
	private UserService userService; 
	
	@Resource(name = "logService")
	private LogService logService;
	
	/**
	 * 用户列表
	 * @param user
	 * @param request
	 * @param response
	 * @return "web/base/autoList"
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/userList.do")
	public String userList(User user,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		if (user.getSearchName() != null	&& user.getSearchName().length() > 0) 
		{
			String searchName = new String(user.getSearchName().getBytes("iso8859-1"), "utf-8");
			user.setSearchName(searchName);		
		}
		if (user.getPageNo() == null)
			user.setPageNo(1);
		if(user.getSearchUtype() == null ){
			user.setSearchUtype(0);
		}
		user.setPageSize(Constants.DEFAULT_PAGE_SIZE); 
		int totalCount = 0;
		List<User> userlist = new ArrayList<User>();
		User loginUser = new User();
		try{
			totalCount = userService.getUserCount(user);
			userlist = userService.getUserList(user);
			loginUser = this.getLoginUser();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		user.setTotalCount(totalCount);
		request.setAttribute("Userlist", userlist);
		request.setAttribute("User", user);
		request.setAttribute("loginUser", loginUser);
		return "web/base/userList";	     
	}
	
	
	/**
	 * 用户详情
	 * @param id
	 * @param request
	 * @param response
	 * @return "web/base/userInfo"
	 */
	@RequestMapping(value = "/userInfo.do", method = RequestMethod.GET, produces = { "text/html;charset=UTF-8" })
	public String UserInfo(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		User loginUser = new User();
		try{
			if(id > 0){
				user = userService.getUserById(id);
			}else{
				user.setId(0);
				user.setUtype(11);
			}
			loginUser = this.getLoginUser();
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("loginUser", loginUser);
		request.setAttribute("User", user);
		return "web/base/userInfo";		
	}
	
	
	/**
	 * 删除账号
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonDeleteUserById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<User> DeleteUser(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);
		js.setMessage("删除失败!");
		try{
			if(id > 0){
				//删除并插入日志
				js = userService.deleteUser(id);
				if(js.getCode() == 0){
					logService.writeLog("删除"+js.getObj()+"成功");
				}
			}						
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	
	
	/**
	 * 登录限制修改
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonChangeFlagById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<User> ChangFlag(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);
		js.setMessage("修改登录限制失败!");
		try{
			if(id > 0){
				//登录限制修改并插入日志
				js = userService.ChangFlag(id);
				if(js.getCode() == 0){
					logService.writeLog(""+js.getObj());
				}
			}						
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	

	
	/**
	 * 新建/保存/修改密码
	 * @param user
	 * @param request
	 * @param response
	 * @return JsonResult<User>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonSaveOrUpdateUser.do", method = RequestMethod.POST)
	public JsonResult<User> SaveOrUpdateUser(User user,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);
		js.setMessage("保存失败!");
		try{
			//修改密码
			if(user.getUtype() == 100){
				js = checkPsd(user);
				if(js.getCode() == 2){
					return js;
				}else{
					user = (User) js.getObj();
				}
			}
			//保存
			js = userService.saveOrUpdateUser(user);
			User loginUser = this.getLoginUser();
			if(user.getId() == loginUser.getId()){
				loginUser.setName(user.getName());
				loginUser.setSex(user.getSex());
				loginUser.setAddress(user.getAddress());
				loginUser.setMobile(user.getMobile());
				SecurityUtils.getSubject().getSession().setAttribute(Constants.USER_SESSION_NAME, loginUser);
			}
			//日志
			if(js.getCode() == 0){
				if(user.getUtype() == 100){
					logService.writeLog("修改密码成功");
				}else{
					if(user.getId() > 0){
						logService.writeLog("修改"+user.getName()+"信息成功");
					}else{
						logService.writeLog("添加"+user.getName()+"信息成功");
					}
				}
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return js;
	}
	
	
	/**
	 * 密码检验
	 * @param user
	 * @return
	 */
	private JsonResult<User> checkPsd(User user){
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);	
		User loginUser = this.getLoginUser();
		//登陆验证密码
		String psd = StringUtil.trim(user.getPsd());
		//新密码
		String psd1 = StringUtil.trim(user.getPsd1());
		//新密码验证
		String psd2 = StringUtil.trim(user.getPsd2());
		//登录验证密码加密
		String psdcheck = MD5Util.getMD5(psd);
		//已加密原密码
		String oldpsd = loginUser.getPsd();
		//新密码加密
		String newpsd = MD5Util.getMD5(psd1);
		
		if(StringUtil.isEmpty(psd) 
				|| StringUtil.isEmpty(psd1) || StringUtil.isEmpty(psd2)){
			js.setMessage("原密码和新密码不能有空！");
			js.setCode(2);
			return js;
		}
		
		if(!psdcheck.equals(oldpsd)){
			js.setMessage("登陆密码错误！");
			js.setCode(2);
			return js;
		}
		if(!psd1.equals(psd2)){
			js.setMessage("两次输入密码不一致！");
			js.setCode(2);
			return js;
		}
		//设置新密码
		loginUser.setPsd(newpsd);
		js.setObj(loginUser);
		return js;
	}
}
