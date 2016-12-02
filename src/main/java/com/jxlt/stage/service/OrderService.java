package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;

public interface OrderService {
	
	//获取订单总数
	int getTotalCount(Order order);

	//获取订单集合
	List<Order> getOrderList(Order order);

	//获取品牌集合
	List<OrderType> getItemList(OrderType item);

	//获取节点数量
	int getTotalCount(OrderType item);

	//逻辑删除品牌
	void deleteTypeById(Integer typeId);

	//根据id获取品牌
	OrderType getOrderTypeById(Integer typeId);

	//保存品牌
	void saveType(OrderType orderType);

	//取品牌名称相同的品牌数量
	int getExistType(OrderType orderType);

	//获取未确认订单列表
	List<Order> getUnsureOrder(Order order);

	//根据id获取订单
	Order getOrderById(Integer orderId);

	//保存订单
	void saveOrder(Order order);

	//保存支付表
	void savePay(Pay pay);

	//根据id查找用户
	User getUserById(Integer operId);

	//获取所有团队集合
	List<Group> getAllGroup();

	//获取订单品牌类型所属的所有品牌
	List<OrderType> getTypeByParentId(Integer orderTypeId);

	//保存项目工程
	void savePro(Project pro);

	//获取工程列表
	List<Project> getProjectListById(Integer id);

	//删除工程
	void deleteProById(Integer id);

	//更新工程
	void updatePro(Project pro);

	//获取工程
	Project getProjectById(Integer id);

	//插入照片
	void insertPhoto(ProjectImage photo);

	//获取工程的所有照片
	List<ProjectImage> getImageById(Integer id);

	//根据id获取工程照片
	ProjectImage getProImageById(Integer id);

	//根据id删除工程
	void deleteProImageById(Integer id);

	//根据名称查找是否已存在同名工程
	List<Project> getExistProjectByName(Project pro);

	//取消订单工程中断状态
	void updateProByOrderId(Integer orderId);

	//更新支付表记录
	void updatePayByOrderId(Pay pay);

	//获取照片集合
	List<ProjectImage> getImageList(ProjectImage open);

	//获取照片数量
	int getImageCount(ProjectImage pji);

	//更新图片是否公开
	void updateProjectImage(ProjectImage pi);

	//获取未确认的订单数量
	int getUnsureOrderCount(int i);

	List<User> getUserByMobile(String mobile);

	void saveUser(User user);
}
