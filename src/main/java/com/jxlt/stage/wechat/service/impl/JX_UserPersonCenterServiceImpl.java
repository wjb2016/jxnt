package com.jxlt.stage.wechat.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.MD5Util;
import com.jxlt.stage.dao.AutoMapper;
import com.jxlt.stage.dao.GradeMapper;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.User;
import com.jxlt.stage.wechat.service.JX_UserPersonCenterService;
import com.jxlt.stage.wechat.utils.SendCodeUtil;


@Service("JX_UserPersonCenterService")
public class JX_UserPersonCenterServiceImpl implements JX_UserPersonCenterService{
	
	@Resource
	private UserMapper userMapper;

	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private GradeMapper gradeMapper;
	
	@Resource
	private AutoMapper autoMapper;
	/**
	 * 用户登录
	 */
	@Override
	public JsonResult<User> getUserInfo(User user,HttpServletRequest request) {
		JsonResult<User> result = new JsonResult<User>();
		result.setCode(0);
		result.setMessage("用户登录失败,请重新登录！");
		try {
			if(user != null){
				// 检查登录手机号是否符合登录条件
				User loginUser = userMapper.getUserByMobile(user);
				if(loginUser != null){
					if(loginUser.getFlag() == 2){
						result.setCode(4);
						result.setMessage("该用户受限制！");
						return result;
					}
					// 验证密码是否正确
					String psd = user.getPsd();
					// 登录密码加密
					String password = MD5Util.getMD5(psd);
					String loginPsd = loginUser.getPsd();
					if(password.equals(loginPsd) ){
						result.setCode(1);
						result.setMessage("登录成功！");
						request.getSession().setAttribute("loginUser", loginUser);
						
						return result;
					}else{
						result.setCode(2);
						result.setMessage("密码不正确,请重新输入！");
						return result;
					}
				}else{
					result.setCode(3);
					result.setMessage("该用户未注册！");
				}
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * 用户注册
	 */
	@Override
	public JsonResult<User> regiterUserInfo(User user,HttpServletRequest request) {
		
		JsonResult<User> result = new JsonResult<User>();
		result.setCode(0);
		result.setMessage("用户注册失败,请重新注册！");	
		String psd = user.getPsd();		
		try {
			if(user !=null){
				// 检查重复注册
				List<User> user2 = userMapper.getUserOnlyByMobile(user);
				if(user2.size() > 1 || (user2.size() == 1 && user2.get(0).getFlag() > 0)){
					result.setCode(2);
					result.setMessage("该用户已注册！");
					return result;
				}
				//User user1 = userMapper.getUserByMobile(user);
				
				// 手机验证码
				if(user.getSendCode() !=null){
					String code = (String) request.getSession().getAttribute("sendCode");
					if(user.getSendCode().equals(code) ){
						user.setUtype(1);                // uType=1是普通用户
						user.setName(user.getMobile());    // 注册时用户名默认是手机号
						//user.setSex(user.getSex());      // 性别 0保密 1男 2女
						user.setMobile(user.getMobile());// 手机号
						user.setFlag(1);
						// 注册密码加密
						String password = MD5Util.getMD5(psd);
						user.setPsd(password);
						// flag = 0 的数据存在，更新这个用户注册的数据
						if(user2.size() == 1){
							
							user.setId(user2.get(0).getId());
							userMapper.updateByPrimaryKeySelective(user);
						}else{
							userMapper.insertSelective(user);
						}
						
						result.setCode(1);
		                result.setMessage("注册成功！");
		                User registUser = userMapper.getUserByMobile(user);
						// 注册成功把用户加到session
						request.getSession().setAttribute("loginUser", registUser);
					}else{
						result.setCode(3);
						result.setMessage("验证码有误，请重新输入！");
						return result;
					}
				}
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
    /**
     * 获取用户信息
     */
	@Override
	public User getUserInfoById(Integer userID) {
		User user = new User();
		try {
			user = userMapper.selectByPrimaryKey(userID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
    
	/**
	 * 保存用户信息
	 */
	@Override
	public JsonResult<User> saveUserInfo(User user,HttpServletRequest request) {
		JsonResult<User> result = new JsonResult<User>();
		
		if(user != null){
			// 先检查用户是否改动信息
			User user1 = userMapper.selectByPrimaryKey(user.getId());
			
			// 检查生日是否设置，生日只可以设置一次
			if(user.getBirthday() == ""){
				
			}else{
				String birthday = user.getBirthday();			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
				try {
					Date birth = sdf.parse(birthday);
					user1.setBirth(birth);
				} catch (ParseException e) {					
					e.printStackTrace();
					System.out.println("生日日期转换异常！");
				}
				
			}
			
			// 检查用户名是否修改
			if(user.getName() == null || user.getName().equals(user1.getName())){
				
			}else{
				user1.setName(user.getName());
			}
			
			// 检查用户性别是否修改
			if(user.getSex() == null || user.getSex().equals(user1.getSex())){
				
			}else{
				user1.setSex(user.getSex());
			}
			// 检查地址是否修改
			if(user.getAddress() == null || user.getAddress().equals(user1.getAddress())){
				
			}else{
				user1.setAddress(user.getAddress());
			}
			// 检查手机是否更改
			if(user.getMobile() == null || user.getMobile().equals(user1.getMobile())){
				
			}else{
				
				// 设置新密码两次输入是否相同
				if(user.getFirstPsd() .equals(user.getPsd())){
					
				}else{
					result.setCode(3);
					result.setMessage("两次输入的密码不一致，请重新输入!");
					return result;
				}
				// 解绑手机检测手机号数据库是否存在；
				User user2 = userMapper.getUserByMobile(user);
				if(user2 != null){
					result.setCode(0);
					result.setMessage("解绑新的手机号存在，不能解绑！");
					return result;
				}
				// 验证码检验
				String code = (String) request.getSession().getAttribute("sendCode");
				if(user.getSendCode().equals(code)){
					// 把order里面的手机号做修改
					List<Order> orderList = orderMapper.getOrderListByMobile(user1.getMobile());
					// 如果订单为空则不处理
					if(orderList == null){
						
					}else{
						// 订单不为空则把订单对应的手机号修改过来
						try {
							for (Order order : orderList) {
								order.setMobile(user.getMobile());
								orderMapper.updateByPrimaryKeySelective(order);
							}
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						
					}
					user1.setMobile(user.getMobile());
					// 密码加密
					String password = MD5Util.getMD5(user.getPsd());
					user1.setPsd(password);
					
				}else{
					result.setCode(2);
					result.setMessage("验证码有误，请重新输入!");
					return result;
				}				
				
			}
			// 将用户修改的数据更新
			userMapper.updateByPrimaryKeySelective(user1);
			
			// 将最新的用户数据更新到session
			request.getSession().setAttribute("loginUser", user1);
			result.setCode(1);
			result.setMessage("保存成功!");
			result.setObj(user1);
		}else{
			result.setMessage("保存失败!");
		}
		return result;
	}

	/**
	 * 获取验证码
	 */
	@Override
	public JsonResult<User> getSendCode(String mobile,HttpServletRequest request) {
		JsonResult<User> json = new JsonResult<User>();
		json.setCode(1);
		json.setMessage("获取验证码失败，请重新获取!");
		try {
			HashMap<String, Object> result = null;
			//初始化SDK
			CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
			
			//******************************注释*********************************************
			//*初始化服务器地址和端口                                                       *
			//*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
			//*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
			//*******************************************************************************
			restAPI.init("app.cloopen.com", "8883");
			
			
			//******************************注释*********************************************
			//*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
			//*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
			//*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
			//*******************************************************************************
			restAPI.setAccount("8a216da858867fd701588a1f15270185", "c7893a8ace024ee884e8623f2a146453");
			
			
			//******************************注释*********************************************
			//*初始化应用ID                                                                 *
			//*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
			//*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
			//*******************************************************************************
			restAPI.setAppId("8a216da858867fd701588a1f15bb018a");
			
			
			//******************************注释****************************************************************
			//*调用发送模板短信的接口发送短信                                                                  *
			//*参数顺序说明：                                                                                  *
			//*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
			//*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
			//*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
			//*第三个参数是要替换的内容数组。																														       *
			//**************************************************************************************************
			
			//**************************************举例说明***********************************************************************
			//*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
			//*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});																		  *
			//*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
			//*********************************************************************************************************************
			
			String sendCode = SendCodeUtil.createSendCode();
			result = restAPI.sendTemplateSMS(mobile,"137163" ,new String[]{sendCode,"2"});
			System.out.println("SDKTestGetSubAccounts result=" + result);
			if("000000".equals(result.get("statusCode"))){
				//正常返回输出data包体信息（map）
				@SuppressWarnings("unchecked")
				HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for(String key:keySet){
					Object object = data.get(key);
					System.out.println(key +" = "+object);
				}
			}else{
				//异常返回输出错误码和错误信息
				System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
				if(result.get("statusCode").equals("160040") ){
					json.setCode(2);
					json.setMessage("您今天的验证码已使用上限，请谨慎操作！");
				}
				return json;
			}
			// 将得到的随机验证码存到session
			request.getSession().setAttribute("sendCode", sendCode);
			json.setCode(0);
			json.setObj(sendCode);
			json.setMessage("获取验证码成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;

	}

	/**
	 * 忘记密码
	 */
	@Override
	public JsonResult<User> forgetPassword(User user, HttpServletRequest request) {
		JsonResult<User> result = new JsonResult<User>();
		result.setCode(0);
		result.setMessage("修改密码失败！");
		if(user != null){
			// 手机号是否注册或者是否受限制
			User user1 = userMapper.getUserByMobile(user);
			if(user1 != null){
				if(user1.getFlag() == 2){
					result.setCode(3);
					result.setMessage("该用户受限制！");
					return result;
				}
			}else{
				result.setCode(4);
				result.setMessage("该用户未注册！");
				return result;
			}
			// 手机验证码
			if(user.getSendCode() !=null){
				String code = (String) request.getSession().getAttribute("sendCode");
				if(user.getSendCode().equals(code)){
					String psd = user.getPsd();
					// 判断两次输入的新密码是否相同
					if(user.getFirstPsd().equals(psd)){
						
						// 修改的新密码加密
						String password = MD5Util.getMD5(psd);
						user1.setPsd(password);
						userMapper.updateByPrimaryKeySelective(user1);
						result.setCode(1);
						result.setMessage("修改密码成功！");
					}else{
						result.setCode(2);
						result.setMessage("两次密码不相同！");
						return result;
					}
					
				}else{
					result.setCode(5);
					result.setMessage("验证码错误！");
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * 通过userID得到积分信息
	 */
	@Override
	public List<Grade> getGradeByUserId(Integer id) {
		List<Grade> grade = gradeMapper.getGradeByUserId(id);
		return grade;
	}

	/**
	 * 兑换积分
	 */
	@Override
	public JsonResult<Grade> cashingPoint(Integer userId, Integer grade) {
		JsonResult<Grade> result = new JsonResult<Grade>();
		result.setCode(0);
		result.setMessage("兑换失败！");
		try {
			// 兑换积分的时候将兑换信息插入积分表
			if(grade != null && userId != null){
				Grade grades = new Grade();
				grades.setUserId(userId);        // 用户id
				grades.setGrade(-grade);          // 兑换的积分
				grades.setCreateTime(new Date());// 创建时间
				grades.setDescription("兑换积分"); // 描述
				gradeMapper.insertSelective(grades);
				// 用户当前积分减去兑换积分
				User user = userMapper.selectByPrimaryKey(userId);
				Integer userGrade = user.getGrade();   // 用户当前积分
				user.setGrade(userGrade-grade);        // 用户最新的积分= 当前积分 - 兑换积分
				userMapper.updateByPrimaryKeySelective(user);
				result.setCode(1);
				result.setMessage("兑换成功！");
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public JsonResult<Auto> getAnswerByQuestion(String question) {
		JsonResult<Auto> result = new JsonResult<Auto>();
		Auto auto = new Auto();
		List<Auto> autoList = new ArrayList<Auto>();
		result.setCode(0);
		try {
			if(question != ""){
				String keyWord = new String(question.getBytes("iso-8859-1"),"utf-8");
				auto.setKeyword(keyWord);
				if(keyWord.trim().equals("我需要帮助")){
					autoList = autoMapper.getAutoKeyWord();
					StringBuilder sb = new StringBuilder("请输入关键词</br>");
					for(int i=0;i<autoList.size();i++){
						if(i<9){
							sb.append("<span style='padding-left:70px;'>0"+(i+1) +"、"+autoList.get(i).getKeyword()+"</span></br>");
						}else{
							sb.append("<span style='padding-left:70px;'>"+(i+1) +"、"+autoList.get(i).getKeyword()+"</span></br>");
						}
					}
					auto.setAnswer(sb.toString());
					result.setCode(1);
					result.setObj(auto);
					return result;
				}
			}
			// 模糊查找数据库里面的关键字对应的回答
			autoList = autoMapper.getAnswerByQuestion(auto);
			//集合大于0，则取第一个元素返回前台
			if(autoList.size() > 0){
				auto = autoList.get((new Random()).nextInt(autoList.size()));
				result.setCode(1);
				result.setObj(auto);
			} 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
