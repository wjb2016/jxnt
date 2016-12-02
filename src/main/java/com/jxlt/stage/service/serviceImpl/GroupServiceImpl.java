package com.jxlt.stage.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.dao.GroupItemMapper;
import com.jxlt.stage.dao.GroupMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.GroupItem;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupMapper groupMapper;
	
	@Resource
	private GroupItemMapper groupItemMapper;
	
	@Resource
	private UserMapper userMapper;

	@Override
	public List<Group> getGroupList(Group group) {
		// TODO Auto-generated method stub
		return groupMapper.getGroupList(group);
	}

	@Override
	public int getGroupCount(Group group) {
		// TODO Auto-generated method stub
		return groupMapper.getGroupCount(group);
	}

	@Override
	public Group getGroupById(Integer id) {
		// TODO Auto-generated method stub
		return groupMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<GroupItem> getGroupItemByGroupId(Integer id) {
		// TODO Auto-generated method stub
		return groupItemMapper.getGroupItemByGroupId(id);
	}

	@Override
	public JsonResult<Group> deleteGroupById(Integer id) {
		// TODO Auto-generated method stub
		JsonResult<Group> js = new JsonResult<Group>();
		js.setCode(1);
		js.setMessage("删除失败!");
		Group group = new Group();
		try{
			group = groupMapper.selectByPrimaryKey(id);
			group.setFlag(0);
			groupMapper.updateByPrimaryKeySelective(group);
			js.setCode(0);
			js.setMessage("删除团队成功!");
			js.setObj(group.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public JsonResult<GroupItem> deleteGroupItemById(Integer id) {
		// TODO Auto-generated method stub
		JsonResult<GroupItem> js = new JsonResult<GroupItem>();
		js.setCode(1);
		js.setMessage("删除团队成员失败！");
        GroupItem gi = new GroupItem();
		try{
			gi = groupItemMapper.selectByPrimaryKey(id);
			groupItemMapper.deleteByPrimaryKey(id);
			js.setObj(gi.getGroupId());
			js.setCode(0);
			js.setMessage("删除团队成员成功!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public JsonResult<Group> saveOrUpdateGroup(Group group) {
		// TODO Auto-generated method stub
		JsonResult<Group> js = new JsonResult<Group>();
		js.setCode(1);
		js.setMessage("保存团队信息失败!");
		if(StringUtil.isEmpty(group.getName())){
			return js;
		}
		List<Group> lc = new ArrayList<Group>();
		try{
			lc = groupMapper.getExsitGroup(group);
			if(lc.size() > 0){
				js.setMessage("团队名称重复!");
				return js;
			}
			group.setCreateTime(new Date());
			group.setFlag(1);
			if(group.getId() > 0){
				groupMapper.updateByPrimaryKeySelective(group);
			}else{
				groupMapper.insert(group);
			}
			js.setCode(0);
			js.setMessage("保存团队信息成功!");
			js.setObj(group.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}

	@Override
	public JsonResult<GroupItem> addGroupItem(GroupItem gi) {
		// TODO Auto-generated method stub
		JsonResult<GroupItem> js = new JsonResult<GroupItem>();
		js.setCode(1);
		js.setMessage("添加团队成员失败!");	
		try{
			Group group = groupMapper.selectByPrimaryKey(gi.getGroupId());
			User user = userMapper.selectByPrimaryKey(gi.getUserId());
			if(gi.getId() == 0){
				groupItemMapper.insert(gi);
				js.setCode(0);
				js.setMessage("添加团队成员成功!");
				js.setObj("团队"+group.getName()+"添加成员"+user.getName()+"成功");
			}else{
				groupItemMapper.updateByPrimaryKeySelective(gi);
				js.setCode(0);
				js.setMessage("替换团队队长成功!");
				js.setObj("团队"+group.getName()+"替换队长为"+user.getName()+"成功");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}



}
