package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Order;
@MyBatisRepository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    //获取订单总数
	int getTotalCount(Order order);

	//获取订单集合
	List<Order> getOrderList(Order order);

	//获取未确认的订单数量
	int getUnsureOrderCount(int i);

	Order getOrderByConNum(String contractNum);
    //即将到期保修服务
	List<Order> getExpiredOrder();

	// 通过手机号去获取订单
	List<Order> getOrderListByMobile(String mobile);

	//获取用户的合同号
	List<String> getContractNumList(Integer id);

}
