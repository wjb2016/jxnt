package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Group;
@MyBatisRepository
public interface GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    //获取团队列表
	List<Group> getGroupList(Group group);
    //获取团队总数
	int getGroupCount(Group group);
    //判断团队是否存在（团队名称唯一）
	List<Group> getExsitGroup(Group group);

	//获取所有团队列表
	List<Group> getAllGroup();

	//获取团队领导人手机号
	String getGroupLeaderPhone(Integer groupId);
	//获取项目经理当前团队
	List<Group> getGroupListByLeader(Integer userId);
}