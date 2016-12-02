package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.GroupItem;

public interface GroupService {

	List<Group> getGroupList(Group group);

	int getGroupCount(Group group);

	Group getGroupById(Integer id);

	List<GroupItem> getGroupItemByGroupId(Integer id);

	JsonResult<Group> deleteGroupById(Integer id);

	JsonResult<GroupItem> deleteGroupItemById(Integer id);

	JsonResult<Group> saveOrUpdateGroup(Group group);

	JsonResult<GroupItem> addGroupItem(GroupItem gi);

	

}
