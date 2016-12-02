package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.ReturnResult;
import com.jxlt.stage.model.User;

public interface UserService {

	ReturnResult<User> login(String name, String psd);

	int getUserCount(User user);

	List<User> getUserList(User user);

	User getUserById(Integer id);

	JsonResult<User> deleteUser(Integer id);

	JsonResult<User> saveOrUpdateUser(User user);

	List<User> getUserByUtype(User user);

	JsonResult<User> ChangFlag(Integer id);

	User getUserByMobile(String mobile);

}
