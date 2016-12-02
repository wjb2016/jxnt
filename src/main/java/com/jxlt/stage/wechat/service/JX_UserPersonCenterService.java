package com.jxlt.stage.wechat.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.User;

public interface JX_UserPersonCenterService {

	/**
	 * 用户登录
	 * @param mobile
	 * @param psd
	 * @return
	 */
	JsonResult<User> getUserInfo(User user,HttpServletRequest request);

	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	JsonResult<User> regiterUserInfo(User user,HttpServletRequest request);

	/**
	 * 个人资料
	 * @param userID
	 * @return
	 */
	User getUserInfoById(Integer userID);

	/**
	 * 保存个人资料
	 * @param userId
	 * @return
	 */
	JsonResult<User> saveUserInfo(User user,HttpServletRequest request);

	/**
	 * 获取验证码
	 * @param mobile
	 * @param request
	 * @return
	 */
	JsonResult<User> getSendCode(String mobile, HttpServletRequest request);

	/**
	 * 忘记密码
	 * @param user
	 * @param request
	 * @return
	 */
	JsonResult<User> forgetPassword(User user, HttpServletRequest request);

	/**
	 * 通过用户id获取积分信息
	 * @param id
	 * @return
	 */
	List<Grade> getGradeByUserId(Integer id);

	/**
	 * 兑换积分
	 * @param userId
	 * @return
	 */
	JsonResult<Grade> cashingPoint(Integer userId,Integer grade);

	/**
	 * 自动应答 （客服）
	 * @param question
	 * @return
	 */
	JsonResult<Auto> getAnswerByQuestion(String question);

}
