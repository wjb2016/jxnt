package com.jxlt.stage.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.ReturnResult;
import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.model.Function;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.FunctionService;
import com.jxlt.stage.service.UserService;



@Scope("prototype")
@Controller
public class LoginController extends BaseController {
	@Resource(name = "userService")
	private UserService userService; 
	
	@Resource(name="functionService")
	private FunctionService functionService;
	
	@ResponseBody
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public JsonResult <User> login(User user, HttpServletRequest request, HttpServletResponse response){
		JsonResult <User>json  = new JsonResult<User>();
		json.setCode(new Integer(1));
		json.setMessage("登录失败!");
		try{
			ReturnResult<User> res = userService.login(user.getName(), user.getPsd());
			
			
			if(res.getCode() == ReturnResult.SUCCESS){
				List<Function> lf = functionService.getFunctionList();
				request.getSession().setAttribute(Constants.USER_SESSION_FUNCTION,lf);
				
				//List<Function> mainList = functionService.getFunctionMainList();
				//List<Function> childList = functionService.getFunctionChildList(mainList.get(0).getId());
				res.getResultObject().setSelectedMainMemu(lf.get(0).getId());
				res.getResultObject().setSelectedChildMenu(lf.get(0).getChildFunctionlist().get(0).getId());
				res.getResultObject().setChildMenuList(lf.get(0).getChildFunctionlist());
				this.setLoginUser(res.getResultObject());
				
				json.setCode(new Integer(0));
				json.setGotoUrl(lf.get(0).getUrl());
				json.setMessage("登录成功!");
			}else{				
				json.setMessage(res.getMessage());
				json.setCode(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/jsonLoadSession.do")
	public JsonResult <User> jsonLoadSession(
		@RequestParam(value = "selectedMainMemu", required = false) Integer selectedMainMemu,
		@RequestParam(value = "selectedChildMenu", required = false) Integer selectedChildMenu, 
		HttpServletRequest request, HttpServletResponse response){
		JsonResult <User>json  = new JsonResult<User>();
		if (selectedMainMemu != null) {
		    this.getLoginUser().setSelectedMainMemu(selectedMainMemu);
		    List<Function> lf = (List<Function>) request.getSession().getAttribute(Constants.USER_SESSION_FUNCTION);
		    for (Function function : lf) {
				if (function.getId().intValue() == selectedMainMemu.intValue()) {
					this.getLoginUser().setSelectedChildMenu(function.getChildFunctionlist().get(0).getId());
					break;
				}
			}
		}
		else if (selectedChildMenu != null) {
			this.getLoginUser().setSelectedChildMenu(selectedChildMenu);
//			this.getLoginUser().setSelectedMainMemu(Integer.parseInt(String.valueOf(selectedChildMenu).substring(0, 1)));
		}
		json.setCode(new Integer(0));
		json.setMessage("更新成功!");
		
		return json;
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest req){
		req.getSession().setAttribute(Constants.USER_SESSION_NAME,null);
		return "login";
		
	}
	/*private List<Function> parseFunctionList(List<Function> src){
		for(Function f : src){
			f.setChildFunctionlist(functionService.getFunctionByParentId(f.getId()));
		}
		return src;
	}*/
	
	
}
