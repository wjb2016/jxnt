package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.User;
import com.jxlt.stage.dao.MyBatisRepository;
@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据手机号查询用户
	User getUserByMobile(User u);
    //用户总数
	int getUserCount(User user);
    //用户列表
	List<User> getUserList(User user);
    //后台保存重复判断
	List<User> getExistUser(User user);

    //按类型查找用户
	List<User> getUserByUtype(User user);
    //当天生日用户
	List<User> getBirthList();
	
	//按手机号查询所有等同用户
	List<User> getUserOnlyByMobile(User user);

}
