package com.jxlt.stage.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.common.utils.StringUtil;
import com.jxlt.stage.model.Auto;
import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.GroupItem;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.GroupService;
import com.jxlt.stage.service.LogService;
import com.jxlt.stage.service.UserService;

@Scope("prototype")
@Controller
@RequestMapping("/Group")
public class GroupController extends BaseController {

	@Resource(name = "groupService")
	private GroupService groupService; 
	
	@Resource(name = "logService")
	private LogService logService;

	@Resource(name = "userService")
	private UserService userService;
	

	/**
	 * 团队列表
	 * @param group
	 * @param request
	 * @param response
	 * @return "web/base/groupList"
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/groupList.do")
	public String groupList(Group group,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		//System.out.println("UserController-test");
		if(!StringUtil.isEmpty(group.getSearchName())){
			String searchName = new String(group.getSearchName().getBytes("iso8859-1"), "utf-8");
			group.setSearchName(searchName);
		}
		if(group.getPageNo() == null)
			group.setPageNo(1);
		group.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		List<Group> grouplist = new ArrayList<Group>();
		int totalCount = 0;
		try{
			grouplist = groupService.getGroupList(group);
			totalCount = groupService.getGroupCount(group);
			for(Group p:grouplist){
				String ctime = DateUtil.sortFormat(p.getCreateTime());
				p.setCreateTimes(ctime);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		group.setTotalCount(totalCount);
		request.setAttribute("groupList",grouplist);
		request.setAttribute("Group",group);
		return "web/base/groupList";     
	}
	
	/**
	 * 团队详情
	 * @param id
	 * @param request
	 * @param response
	 * @return "web/base/groupInfo"
	 */
	@RequestMapping(value = "/groupInfo.do", method = RequestMethod.GET, produces = { "text/html;charset=UTF-8" })
	public String GroupInfo(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
	    	Group group = new Group();
	    	GroupItem groupItem = new GroupItem();
	    	List<GroupItem> gilist = new ArrayList<GroupItem>();
	    	List<User> workerlist = new ArrayList<User>();
	    	List<User> leaderlist = new ArrayList<User>();
	    	List<User> chooselist = new ArrayList<User>();
	    	int count0 = 0,count1 = 0,count2 = 0;
	    	try{	    		
	    		if(id > 0){
	    			group = groupService.getGroupById(id);
	    			groupItem.setId(0);
	    			groupItem.setGroupId(id);
	    			String ctime = DateUtil.longFormat(group.getCreateTime());
	    			group.setCreateTimes(ctime);
	    			//团队成员集
	    			gilist = groupService.getGroupItemByGroupId(id);	
	    			//团队成员各类型人数
	    			for(GroupItem p:gilist){
	    				if(p.getIsLeader() == 2){
	    					count2++;
	    				}else if(p.getIsLeader() == 1){
	    					count1++;
	    				}else{
	    					count0++;
	    				}
	    			}
	    		}
	    		group.setCount(count0+count1+count2);
				group.setCount1(count1);
				group.setCount2(count2);				    	
	    		//团队员工选择集
    			workerlist = getListByUtype(11,gilist);
    			//项目经理选择集
    			leaderlist = getListByUtype(12,gilist);
    			//未有队长先选队长
    			if(group.getCount2() != null){
    				if(group.getCount2() == 0){
    					chooselist = leaderlist;
    				}else{
    					chooselist = workerlist;
    				}
    			}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	request.setAttribute("chooselist",chooselist);
	    	request.setAttribute("leaderlist", leaderlist);
	    	request.setAttribute("groupItemList", gilist);
	    	request.setAttribute("Group", group);
	    	request.setAttribute("GroupItem", groupItem);
			return "web/base/groupInfo";
		
	}
	
	/**
	 * 删除团队(逻辑）
	 * @param id
	 * @param request
	 * @param response
	 * @return JsonResult<User>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonDeleteGroupById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<Group> DeleteGroup(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<Group> js = new JsonResult<Group>();
		js.setCode(1);
		js.setMessage("删除失败!");	
		try{
			if(id > 0){				
				//删除并插入日志
				js = groupService.deleteGroupById(id);
				if(js.getCode() == 0){
					logService.writeLog("删除"+js.getObj()+"团队成功");
				}
			}						
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	
	
	/**
	 * 删除团队成员
	 * @param id
	 * @param request
	 * @param response
	 * @return JsonResult<GroupItem>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonDeleteGroupItemById.do", method = RequestMethod.POST, produces = { "text/html;charset=UTF-8" })
	public JsonResult<GroupItem> DeleteGroupItem(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<GroupItem> js = new JsonResult<GroupItem>();
		js.setCode(1);
		js.setMessage("删除失败!");	
		try{
			if(id > 0){				
				//删除并插入日志
				js = groupService.deleteGroupItemById(id);
				if(js.getCode() == 0){
					int userId = (int) js.getObj();
					User user = userService.getUserById(userId);
					logService.writeLog("删除团队成员"+user.getName()+"成功");
				}
			}						
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	
	
	/**
	 * 添加团队成员
	 * @param groupItem
	 * @param request
	 * @param response
	 * @return JsonResult<GroupItem>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonSaveOrUpdateMenber.do", method = RequestMethod.POST)
	public JsonResult<GroupItem> AddGroupItem(
			GroupItem groupItem,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<GroupItem> js = new JsonResult<GroupItem>();
		List<GroupItem> gilist = new ArrayList<GroupItem>();
		js.setCode(1);
		js.setMessage("添加团队成员失败!");		
		try{			
			groupItem.setId(0);	
			//添加队长时替换原队长
			if(groupItem.getIsLeader() == 2){
				gilist = groupService.getGroupItemByGroupId(groupItem.getGroupId());
				for(GroupItem gi:gilist){
					if(gi.getIsLeader() == 2){
						groupItem.setId(gi.getId());
					}
				}	
			}		
			js = groupService.addGroupItem(groupItem);
			if(js.getCode() == 0){		
				logService.writeLog(""+js.getObj());
				js.setObj(groupItem.getGroupId());
			}						
		}catch(Exception e){
			e.printStackTrace();
		}
		return js;
	}
	
	
	
	/**
	 * 保存团队信息
	 * @param group
	 * @param request
	 * @param response
	 * @return JsonResult<Group>
	 */
	@ResponseBody
	@RequestMapping(value = "/jsonSaveOrUpdateGroup.do", method = RequestMethod.POST)
	public JsonResult<Group> SaveOrUpdateGroup(Group group,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult<Group> js = new JsonResult<Group>();
		js.setCode(1);
		js.setMessage("保存失败!");
		if(group.getId() == null)
			group.setId(0);
		try{
			//保存
			js = groupService.saveOrUpdateGroup(group);
			//日志
			if(js.getCode() == 0){		
				if(group.getId() > 0){
					logService.writeLog("修改团队"+group.getName()+"信息成功");
				}else{
					logService.writeLog("添加团队"+group.getName()+"信息成功");
				}				
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return js;
	}
	
	
	/**
	 * 团队成员挑选列表
	 * @param utype
	 * @return List<User>
	 */
	private List<User> getListByUtype(Integer utype,List<GroupItem> gilist){
		List<User> list = new ArrayList<User>();
		List<User> resultlist = new ArrayList<User>();		
		User user = new User();
		try{
			user.setUtype(utype);
			list = userService.getUserByUtype(user);
			//排除已有团队成员
			if(gilist.size() > 0){
				for(User u:list){
					int id1 = u.getId();
					int count = 0;
					for(GroupItem g:gilist){
						int id2 = g.getUserId();
						if(id1 == id2)
							count++;
					}
					if(count == 0)
						resultlist.add(u);
				}			
			}else{
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultlist;		
	}
	

}
