package com.jxlt.stage.wechat.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.User;
import com.jxlt.stage.wechat.service.JX_UserPersonCenterService;

@Controller
@RequestMapping(value="PerCentral")
public class JX_PersonCenterController {

	@Resource
	private JX_UserPersonCenterService userPersonCenterService; 
	
	@Resource
	private UserMapper userMapper;
	// 登录-注册-忘记密码
	@RequestMapping(value="jxLogin.do")
	public String JX_Login(){
		
		return "wechat/person/JX_login_register_forget";
		
	}
	
	/**
	 * 用户登录
	 * @param mobile
	 * @param psd
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="login.do")
	public JsonResult<User> login( User user,
			/*@RequestParam(value = "mobile", required = false) String mobile, 
			@RequestParam(value = "psd", required = false) String psd,*/
			HttpServletRequest request){
		
		JsonResult<User> result = new JsonResult<User>();
		result = userPersonCenterService.getUserInfo(user,request);
		return result;
		
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="register.do")
	public JsonResult<User> register(User user,HttpServletRequest request){
		
		JsonResult<User> result = new JsonResult<User>();
		result = userPersonCenterService.regiterUserInfo(user,request);
		return result;
		
	}
	
	/**
	 * 用户获取验证码
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="sendCode.do")
	public JsonResult<User> getSendCode(
			@RequestParam(value = "mobile", required = false) String mobile,
			HttpServletRequest request){
		
		JsonResult<User> result = new JsonResult<User>();
		result = userPersonCenterService.getSendCode(mobile,request);
		return result;
		
	}
	
	/**
	 * 忘记密码
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="forgetPsd.do")
	public JsonResult<User> forgetPsd(User user , HttpServletRequest request){
		
		JsonResult<User> result = new JsonResult<User>();
		result = userPersonCenterService.forgetPassword(user,request);
		return result;
		
	}
	// 个人中心
	@RequestMapping(value="jxPerson.do")
	public String JX_Person(HttpServletRequest request){
		
		// 从session缓存里面去登录的用户
		User user = (User)request.getSession().getAttribute("loginUser");
		if(user!= null){

			request.getSession().setAttribute("userName", user.getName());
			request.getSession().setAttribute("ID", user.getId());			
			request.getSession().setAttribute("User", user);
		}else{
			request.getSession().setAttribute("userName", "未登录");
			request.getSession().setAttribute("ID", 0);
		}
		return "wechat/person/JX_person";
		
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="jxUserExitLogin.do")
	public String JX_UserExitLogin(HttpServletRequest request){
		// 退出登录操作销毁session缓存
		User user = (User)request.getSession().getAttribute("loginUser");				
		if (user != null) {
			try {
				request.getSession().removeAttribute("loginUser");
			} catch (Exception e) {
				user = null;
			}
		}
		return "wechat/person/JX_login_register_forget";		
	}
	// 用户信息
	@RequestMapping(value="jxUserInfo.do")
	public String JX_UserInfo(HttpServletRequest request){
		Integer userID = Integer.parseInt(request.getParameter("userId"));
		User user = userPersonCenterService.getUserInfoById(userID);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String birthday = null;
		try {
			if(user.getBirth() != null){
				birthday = sdf.format(user.getBirth());
			}
		} catch (Exception e) {
			//System.out.println("日期异常！");
			e.printStackTrace();
		}
		request.setAttribute("user", user);
		request.setAttribute("birthday", birthday);
		return "wechat/person/JX_userInfo";
		
	}
	
	/**
	 * 个人资料
	 * @param user
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="jsonSaveUserInfo.do")
	public JsonResult<User> SaveUserInfo(User user, HttpServletRequest request){
		//Integer userId = Integer.parseInt(request.getParameter("id"));
		JsonResult<User> result = new JsonResult<User>();
		result = userPersonCenterService.saveUserInfo(user,request);
	
		return result;
		
	}
	// 用户积分
	@RequestMapping(value="jxUserPoints.do")
	public String JX_UserPoints(HttpServletRequest request){
		
		Integer id = Integer.valueOf(request.getParameter("userId"));
		// 根据用户id查询用户的积分
		User user = userMapper.selectByPrimaryKey(id);
		List<Grade> grade = userPersonCenterService.getGradeByUserId(id);
		// 转换日期
		String time = null;
		for (Grade grade2 : grade) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			time = sdf.format(grade2.getCreateTime());
			grade2.setCreateTimes(time);			
		}
		
		request.setAttribute("user", user);
		request.setAttribute("gradeObj", grade);
		return "wechat/person/JX_userPoints";
		
	}
	@ResponseBody
	@RequestMapping(value="cashingPoint.do")
	public JsonResult<Grade> cashingPoint(HttpServletRequest request){
		//String id =  (String) request.getSession().getAttribute("ID");
		Integer userId = Integer.parseInt(request.getParameter("id"));
		Integer grade = Integer.parseInt(request.getParameter("grade"));
		JsonResult<Grade> result = new JsonResult<Grade>();
		result = userPersonCenterService.cashingPoint(userId,grade);
		return result;
		
	}
	// 客服妹妹
	@RequestMapping(value="jxCustomService.do")
	public String JX_CustomService(){
		
		return "wechat/person/JX_CustomService";
		
	}
	
	@ResponseBody
	@RequestMapping(value="CustomServiceAnswer.do")
	public JsonResult<Auto> JX_CustomServiceAnswer(String question ){
		//根据用户输入的关键词，进行模糊匹配
		JsonResult<Auto> result = userPersonCenterService.getAnswerByQuestion(question);
		
		return result;
	}
	// 用户注册协议
	@RequestMapping(value="jxUserRegisterAgreement.do")
	public String JX_UserRegisterAgreement(){
		
		return "wechat/person/JX_userRegisterAgreement";
		
	}
}
