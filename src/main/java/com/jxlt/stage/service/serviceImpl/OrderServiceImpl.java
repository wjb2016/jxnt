package com.jxlt.stage.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.dao.GroupMapper;
import com.jxlt.stage.dao.OrderImageMapper;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.dao.OrderTypeMapper;
import com.jxlt.stage.dao.PayMapper;
import com.jxlt.stage.dao.ProjectImageMapper;
import com.jxlt.stage.dao.ProjectMapper;
import com.jxlt.stage.dao.UserMapper;
import com.jxlt.stage.model.Group;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderType;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.Project;
import com.jxlt.stage.model.ProjectImage;
import com.jxlt.stage.model.User;
import com.jxlt.stage.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Resource 
	private OrderMapper orderMapper;
	
	@Resource 
	private OrderImageMapper orderImageMapper;
	
	@Resource 
	private OrderTypeMapper orderTypeMapper;
	
	@Resource
	private PayMapper payMapper;

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private GroupMapper groupMapper;
	
	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private ProjectImageMapper projectImageMapper;
	
	/**
	 * 获取订单总数
	 */
	@Override
	public int getTotalCount(Order order) {
		return orderMapper.getTotalCount(order);
	}

	/**
	 * 获取订单集合
	 */
	@Override
	public List<Order> getOrderList(Order order) {
		return orderMapper.getOrderList(order);
	}

	/**
	 * 获取品牌集合
	 */
	@Override
	public List<OrderType> getItemList(OrderType item) {
		return orderTypeMapper.getItemList(item);
	}

	/**
	 * 获取节点数量
	 */
	@Override
	public int getTotalCount(OrderType item) {
		return orderTypeMapper.getTotalCount(item);
	}

	/**
	 * 逻辑删除品牌
	 */
	@Override
	public void deleteTypeById(Integer typeId) {
		orderTypeMapper.deleteByPrimaryKey(typeId);
	}

	/**
	 * 根据id获取品牌
	 */
	@Override
	public OrderType getOrderTypeById(Integer typeId) {
		return orderTypeMapper.getOrderTypeById(typeId);
	}

	/**
	 * 保存品牌
	 */
	@Override
	public void saveType(OrderType orderType) {
		if(orderType.getId() > 0){
			//编辑
			orderTypeMapper.updateByPrimaryKeySelective(orderType);
		}else{
			//新增
			orderTypeMapper.insertSelective(orderType);
		}
	}

	/**
	 * 取品牌名称相同的品牌数量
	 */
	@Override
	public int getExistType(OrderType orderType) {
		return orderTypeMapper.getExistType(orderType);
	}

	/**
	 * 获取未确认订单列表
	 */
	@Override
	public List<Order> getUnsureOrder(Order order) {
		return orderMapper.getOrderList(order);
	}

	/**
	 * 根据主键获取订单
	 */
	@Override
	public Order getOrderById(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	/**
	 * 保存订单
	 */
	@Override
	public void saveOrder(Order order) {
		if(order.getId() == 0){
			orderMapper.insertSelective(order);
		}else{
			orderMapper.updateByPrimaryKeySelective(order);
		}
	}

	/**
	 * 保存支付记录
	 */
	@Override
	public void savePay(Pay pay) {
		payMapper.insertSelective(pay);
	}

	/**
	 * 根据id查询用户
	 * @param operId
	 * @return
	 */
	@Override
	public User getUserById(Integer operId) {
		return userMapper.selectByPrimaryKey(operId);
	}

	/**
	 * 获取所有团队列表
	 * @return
	 */
	@Override
	public List<Group> getAllGroup() {
		return groupMapper.getAllGroup();
	}

	/**
	 * 获取订单品牌类型所属的所有品牌
	 * @param orderTypeId
	 * @return
	 */
	@Override
	public List<OrderType> getTypeByParentId(Integer orderTypeId) {
		return orderTypeMapper.getTypeByParentId(orderTypeId);
	}

	/**
	 * 保存项目工程
	 */
	@Override
	public void savePro(Project pro) {
		if(pro.getId() == 0){
			projectMapper.insertSelective(pro);
		}else{
			projectMapper.updateByPrimaryKeySelective(pro);
		}
	}

	/**
	 * 获取订单的工程列表
	 */
	@Override
	public List<Project> getProjectListById(Integer id) {
		return projectMapper.getProjectListById(id);
	}

	/**
	 * 根据id删除工程
	 */
	@Override
	public void deleteProById(Integer id) {
		projectMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 更新工程
	 */
	@Override
	public void updatePro(Project pro) {
		projectMapper.updateByPrimaryKeySelective(pro);
	}

	/**
	 * 获取工程
	 */
	@Override
	public Project getProjectById(Integer id) {
		return projectMapper.getProjectById(id);
	}

	/**
	 * 保存照片
	 * @param photo
	 */
	@Override
	public void insertPhoto(ProjectImage photo) {
		if(photo.getId() > 0){
			projectImageMapper.updateByPrimaryKeySelective(photo);
		}else{
			projectImageMapper.insertSelective(photo);
		}
		
	}

	/**
	 * 获取工程的所有照片
	 */
	@Override
	public List<ProjectImage> getImageById(Integer id) {
		return projectImageMapper.getImageById(id);
	}

	@Override
	public ProjectImage getProImageById(Integer id) {
		return projectImageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteProImageById(Integer id) {
		projectImageMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 根据名称查找是否已存在同名工程
	 */
	@Override
	public List<Project> getExistProjectByName(Project pro) {
		return projectMapper.getExistProjectByName(pro);
	}

	/**
	 * 取消订单工程中断状态
	 * @param orderId
	 */
	@Override
	public void updateProByOrderId(Integer orderId) {
		projectMapper.updateProByOrderId(orderId);
	}

	/**
	 * 更新支付表记录
	 * @param pay
	 */
	@Override
	public void updatePayByOrderId(Pay pay) {
		payMapper.updatePayByOrderId(pay);
	}

	/**
	 * 获取照片集合
	 * @param open
	 * @return
	 */
	@Override
	public List<ProjectImage> getImageList(ProjectImage open) {
		return projectImageMapper.getImageList(open);
	}

	/**
	 * 获取照片数量
	 * @param pji
	 * @return
	 */
	@Override
	public int getImageCount(ProjectImage pji) {
		return projectImageMapper.getImageCount(pji);
	}

	/**
	 * 更新图片是否公开
	 */
	@Override
	public void updateProjectImage(ProjectImage pi) {
		projectImageMapper.updateByPrimaryKeySelective(pi);
	}

	/**
	 * 获取未确认的订单数量
	 */
	@Override
	public int getUnsureOrderCount(int i) {
		return orderMapper.getUnsureOrderCount(i);
	}

	@Override
	public List<User> getUserByMobile(String mobile) {
		User user = new User();
		user.setMobile(mobile);
		return userMapper.getUserOnlyByMobile(user);
	}

	@Override
	public void saveUser(User user) {
		if(user.getId() == 0){
			userMapper.insertSelective(user);
		}else{
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	@Override
	public int getPermissionPhotoCount() {
		return projectImageMapper.getPermissionPhotoCount();
	}

	@Override
	public List<OrderType> getItemListByParentId(OrderType item) {
		return orderTypeMapper.getItemListByParentId(item);
	}

	@Override
	public int getTotalCountByParentId(OrderType item) {
		// TODO Auto-generated method stub
		return orderTypeMapper.getTotalCountByParentId(item);
	}

	@Override
	public void updateByPrimaryKeySelective(Order order) {
		// TODO Auto-generated method stub
		orderMapper.updateByPrimaryKeySelective(order);
	}
}
