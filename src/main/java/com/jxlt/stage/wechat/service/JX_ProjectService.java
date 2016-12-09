package com.jxlt.stage.wechat.service;

import java.util.List;

import com.jxlt.stage.model.Grade;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;

public interface JX_ProjectService {
    
	/**
	 * 通过项目经理id去得到分配给他的工程
	 * @param id
	 * @param integer 
	 * @return
	 */
	List<String> getProjectByUserId(Integer id, Integer integer);

	//根据合同号获取工程列表
	List<Project> getProjectListByConNum(String contractNum, Integer integer, Integer integer2);

	//根据id获取工程
	Project getProjectById(Integer proId);

	//根据id获取项目
	Order getOrderById(Integer orderId);

	//根据id获取品牌
	OrderType getOrderTypeById(Integer orderTypeId);

	//根据id获取工程图片列表
	List<ProjectImage> getImgByProId(Integer id);

	//保存工程
	void savePro(Project project);

	//根据订单id获取剩余未完成的工程
	List<Project> getRemainProListById(Integer orderId);

	//保存项目
	void saveOrder(Order order);

	//插入照片
	void insertPhoto(ProjectImage photo);

	//根据id查询照片
	ProjectImage getImgByPhotoId(Integer id);

	//根据id删除照片
	void deleteProImageById(Integer id);

	//根据合同号获取订单
	Order getOrderByConNum(String contractNum);

	void saveUser(User user);

	void saveGrade(Grade grade);

	void saveProImg(ProjectImage pro);

	void updateProjectImgByProId(Integer id);

	// 获取客服工程图的信息
	List<ProjectImage> getOrderImageList();

	String getGroupLeaderPhone(Integer groupId);

}
