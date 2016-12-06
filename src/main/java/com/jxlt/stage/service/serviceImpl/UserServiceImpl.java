package com.jxlt.stage.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;











import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.stereotype.Service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.ReturnResult;
import com.jxlt.stage.common.utils.MD5Util;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.controller.BaseController;
import com.jxlt.stage.dao.GradeMapper;
import com.jxlt.stage.dao.GroupItemMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.GroupItem;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseController implements UserService {

	@Resource 
	private UserMapper userMapper;
	
	@Resource 
	private GradeMapper gradeMapper;
	
	@Resource 
	private GroupItemMapper groupItemMapper;

	public ReturnResult<User> login(String mobile, String psd) {
		mobile = StringUtil.trim(mobile);
		psd = StringUtil.trim(psd);
		ReturnResult<User> res = new ReturnResult<User>();
		User u = new User();		
		res.setCode(ReturnResult.FAILURE);
		res.setMessage("登录失败");
		try {
			u.setMobile(mobile);
			u = userMapper.getUserByMobile(u);	
			if (null == u) {
				res.setMessage("用户[" + mobile + "]不存在！");
			} else {
				String password = MD5Util.getMD5(psd);
				if(u.getUtype() != 10 && u.getUtype() != 13){
					res.setMessage("权限不足！");
				}else if(u.getFlag() == 2){
					res.setMessage("禁止登陆！");
				}else if(password.equals(u.getPsd())){
					res.setCode(ReturnResult.SUCCESS);
					res.setMessage("登录成功！");
					res.setResultObject(u);
				}else{
					res.setMessage("登录失败，密码错误。");
				}
			}
		} catch (ExcessiveAttemptsException ex) {
			res.setCode(ReturnResult.FAILURE);
			res.setMessage("登录失败，未知错误。");
		} catch (AuthenticationException ex) {
			res.setCode(ReturnResult.FAILURE);
			res.setMessage("登录失败，密码错误。");
		}
		return res;
	}

	@Override
	public int getUserCount(User user) {
		// TODO Auto-generated method stub
		return userMapper.getUserCount(user);
	}

	@Override
	public List<User> getUserList(User user) {
		// TODO Auto-generated method stub
		List<User> list = new ArrayList<User>();
		list = userMapper.getUserList(user);
		//排序
//		int[] arr = new int[]{1,20,11,12,10,13};
//		list = sortUser(list,arr);					
		return list;
	}
	
//	/**
//	 * User根据utype排序方法
//	 * @param list
//	 * @param arr
//	 * @return
//	 */
//	private List<User> sortUser(List<User> list,int[] arr){
//		if(list.size() == 0)
//			return null;
//		if(arr.length == 0)
//			return list;
//		List<User> list1 = new ArrayList<User>();
//		for(int i=0;i<arr.length;i++){
//			for(User u:list){
//				if(u.getUtype() == arr[i])
//					list1.add(u);
//			}
//		}	
//		return list1;		
//	}

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public JsonResult<User> deleteUser(Integer id) {
		// TODO Auto-generated method stub
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);
		js.setMessage("删除用户失败！");
		User user = userMapper.selectByPrimaryKey(id);
		User loginUser = this.getLoginUser();
		//超级管理员删除管理员
		if(user.getUtype() == 10 && loginUser.getUtype() != 13){
			js.setMessage("权限不足！");
			return js;
		}
		//逻辑删除并清零积分
		user.setFlag(0);
		int grades = 0-user.getGrade();
		user.setGrade(0);
		userMapper.updateByPrimaryKeySelective(user);
		if(grades < 0){
			Grade grade = new Grade();
			grade.setUserId(user.getId());
			grade.setOperId(loginUser.getId());
			grade.setDescription("后台删除用户积分清零");
			grade.setId(0);
			grade.setCreateTime(new Date());
			grade.setGrade(grades);
			gradeMapper.insert(grade);
		}		
		js.setObj(user.getUtypeName()+user.getName());
		js.setCode(0);
		js.setMessage("删除成功!");
		return js;
	}

	@Override
	public JsonResult<User> saveOrUpdateUser(User user) {
		// TODO Auto-generated method stub
		JsonResult<User> js = new JsonResult<User>();
		User oldUser = new User();
		List<GroupItem> itemlist = new ArrayList<GroupItem>();
		js.setCode(1);
		js.setMessage("保存失败！");
		user.setFlag(1);
		List<User> lu = userMapper.getUserOnlyByMobile(user);
		//数据库重复验证
		if( lu.size() > 1 ||(lu.size() == 1 && user.getId() != lu.get(0).getId() && lu.get(0).getFlag() > 0)){
			js.setMessage("该手机号已注册!");
			return js;
		}else{
			if(user.getId() == 0){				
				//初始化密码手机后六位
				String psd = user.getMobile().substring(5, 11);
			//	System.out.println(psd);
				String password = MD5Util.getMD5(psd);
				user.setPsd(password);
				user.setGrade(0);
				//有记录的用户覆盖
				if(lu.size() == 1 && lu.get(0).getFlag() == 0){
					user.setId(lu.get(0).getId());
					userMapper.updateByPrimaryKeySelective(user);
				}else{
					userMapper.insert(user);
				}									
			}else{
				oldUser = userMapper.selectByPrimaryKey(user.getId());
				//员工与项目经理类型转换判断
				if((oldUser.getUtype() == 11 || oldUser.getUtype() == 12) 
						&& oldUser.getUtype() != user.getUtype()){
					itemlist = groupItemMapper.getItemListWithUserId(user.getId());
					if(itemlist.size() > 0){
						//员工删除团队记录
						if(oldUser.getUtype() == 11){
							groupItemMapper.deleteByUserId(oldUser.getId());
						//项目经理带团时不能变更
						}else{
							js.setMessage("该用户是工程团队负责人，不可改变类型!");
							return js;
						}					
					}
				}
				userMapper.updateByPrimaryKeySelective(user);
			}
			js.setCode(0);
			js.setMessage("保存成功!");
		}
		return js;
	}

	@Override
	public List<User> getUserByUtype(User user) {
		// TODO Auto-generated method stub
		return userMapper.getUserByUtype(user);
	}


	@Override
	public JsonResult<User> ChangFlag(Integer id) {
		// TODO Auto-generated method stub
		JsonResult<User> js = new JsonResult<User>();
		js.setCode(1);
		js.setMessage("修改用户登录限制失败！");
		User user = userMapper.selectByPrimaryKey(id);
		User loginUser = this.getLoginUser();
		//超级管理员删除管理员
		if(user.getUtype() == 10 && loginUser.getUtype() != 13){
			js.setMessage("权限不足！");
			return js;
		}
		//逻辑删除
		if(user.getFlag() == 1){
			user.setFlag(2);
			userMapper.updateByPrimaryKeySelective(user);		
			js.setCode(0);
			js.setMessage("限制登录成功!");
			js.setObj("限制"+user.getUtypeName()+user.getName()+"登录成功");
		}else if(user.getFlag() == 2){
			user.setFlag(1);
			userMapper.updateByPrimaryKeySelective(user);		
			js.setCode(0);
			js.setMessage("解除登录限制成功!");
			js.setObj(user.getUtypeName()+user.getName()+"解除登录限制成功");
		}		
		
		
		return js;
	}

	@Override
	public User getUserByMobile(String mobile) {
		// TODO Auto-generated method stub
		User u = new User();
		u.setMobile(mobile);
		return userMapper.getUserByMobile(u);
	}
	
	
	

}
