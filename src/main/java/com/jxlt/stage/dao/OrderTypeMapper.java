package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.OrderType;
@MyBatisRepository
public interface OrderTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderType record);

    int insertSelective(OrderType record);

    OrderType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderType record);

    int updateByPrimaryKey(OrderType record);

    //获取品牌集合
	List<OrderType> getItemList(OrderType item);

	//获取节点数量
	int getTotalCount(OrderType item);

	//根据id获取品牌
	OrderType getOrderTypeById(Integer typeId);

	//取品牌名称相同的品牌数量
	int getExistType(OrderType orderType);

	//获取订单品牌类型所属的所有品牌
	List<OrderType> getTypeByParentId(Integer orderTypeId);
}