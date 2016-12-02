package com.jxlt.stage.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.dao.GradeMapper;
import com.jxlt.stage.dao.GroupMapper;
import com.jxlt.stage.dao.OrderImageMapper;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.dao.OrderTypeMapper;
import com.jxlt.stage.dao.ProjectImageMapper;
import com.jxlt.stage.dao.ProjectMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderImage;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;
import com.jxlt.stage.wechat.service.JX_ProjectService;

@Service("JX_ProjectService")
public class JX_ProjectServiceImpl implements JX_ProjectService {

	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private OrderTypeMapper orderTypeMapper;
	
	@Resource
	private ProjectImageMapper projectImageMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private GradeMapper gradeMapper;
	
	@Resource
	private OrderImageMapper orderImageMapper;
	
	@Resource
	private GroupMapper groupMapper;
	
	/**
	 * 通过项目经理id去得到分配给他的工程
	 */
	@Override
	public List<String> getProjectByUserId(Integer id,Integer utype) {
		List<String> result = new ArrayList<String>();
		if(utype != 1){
			result = projectMapper.getProjectListByUserId(id,utype);
		}else{
			result = orderMapper.getContractNumList(id);
		}
		return result;
	}

	//根据合同号获取工程列表
	@Override
	public List<Project> getProjectListByConNum(String contractNum,Integer utype,Integer userId) {
		return projectMapper.getProjectListByConNum(contractNum,utype,userId);
	}

	@Override
	public Project getProjectById(Integer proId) {
		return projectMapper.selectByPrimaryKey(proId);
	}

	@Override
	public Order getOrderById(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public OrderType getOrderTypeById(Integer orderTypeId) {
		return orderTypeMapper.selectByPrimaryKey(orderTypeId);
	}

	@Override
	public List<ProjectImage> getImgByProId(Integer id) {
		return projectImageMapper.getImageById(id);
	}

	@Override
	public void savePro(Project project) {
		projectMapper.updateByPrimaryKeySelective(project);
	}

	/**
	 * 根据订单id获取剩余未完成的工程
	 */
	@Override
	public List<Project> getRemainProListById(Integer orderId) {
		return projectMapper.getRemainProListById(orderId);
	}

	/**
	 * 保存项目
	 */
	@Override
	public void saveOrder(Order order) {
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public void insertPhoto(ProjectImage photo) {
		if(photo.getId() > 0){
			projectImageMapper.updateByPrimaryKeySelective(photo);
		}else{
			projectImageMapper.insertSelective(photo);
		}
	}

	@Override
	public ProjectImage getImgByPhotoId(Integer id) {
		return projectImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteProImageById(Integer id) {
		projectImageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Order getOrderByConNum(String contractNum) {
		return orderMapper.getOrderByConNum(contractNum);
	}

	@Override
	public void saveUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public void saveGrade(Grade grade) {
		gradeMapper.insertSelective(grade);
	}

	@Override
	public void saveProImg(ProjectImage pro) {
		projectImageMapper.updateByPrimaryKeySelective(pro);
	}

	@Override
	public void updateProjectImgByProId(Integer id) {
		projectImageMapper.updateProjectImgByProId(id);
	}

	@Override
	public List<ProjectImage> getOrderImageList() {
		List<ProjectImage> orderImageList = projectImageMapper.getProjectImageListInfo();
		return orderImageList;
	}

	@Override
	public String getGroupLeaderPhone(Integer groupId) {
		return groupMapper.getGroupLeaderPhone(groupId);
	}
}
