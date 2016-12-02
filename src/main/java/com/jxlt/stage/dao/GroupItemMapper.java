package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.GroupItem;

@MyBatisRepository
public interface GroupItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupItem record);

    int insertSelective(GroupItem record);

    GroupItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupItem record);

    int updateByPrimaryKey(GroupItem record);
    //团队成员
	List<GroupItem> getGroupItemByGroupId(Integer id);
    //个人加入团队数据列表
	List<GroupItem> getItemListWithUserId(Integer id);
}