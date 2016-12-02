package com.jxlt.stage;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.User;

public class TestUserDao extends BaseTest{

	@Resource
	private UserMapper userMapper;
	
	//测试根据手机号获取用户
	@Test
	public void TestGetUserByMobile(){
		User oldUser = new User();
		oldUser.setMobile("123456");
		User user = userMapper.getUserByMobile(oldUser);
		Assert.assertEquals("admins",user.getName());
		
	}
	
	//测试已存在用户
		@Test
		public void TestExsitUser(){
			User user = new User();
			user.setId(0);
			user.setMobile("123456");
			List<User> list = userMapper.getExistUser(user);
			Assert.assertEquals(1,list.size());
			
		}
}
