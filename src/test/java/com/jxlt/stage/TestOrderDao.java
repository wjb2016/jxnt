package com.jxlt.stage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.dao.OrderItemMapper;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.OrderItem;

public class TestOrderDao extends BaseTest {
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private OrderItemMapper orderItemMapper;
	
	/**
	 * 测试订单总数
	 */
	@Test
	public void testGetTotalCount(){
		Order order = new Order();
		order.setSearchName("15883283040");
		Assert.assertEquals(1,orderMapper.getTotalCount(order));
	}
	
	/**
	 * 测试订单集合
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testGetOrderList(){
		Order order = new Order();
		order.setContractNumber("#11111");
		Assert.assertEquals(1,orderMapper.getOrderList(order).size());
	}
	
	/**
	 * 测试获取未确认订单数量
	 */
	@Test
	public void testGetUnsureOrder(){
		int i = orderMapper.getUnsureOrderCount(0);
		Assert.assertEquals(0, i);
	}
	
	@Test
	public void addOrderItem(){
		Order order = new Order();
		order.setPageNo(1);
		order.setPageSize(10000);
		List<Order> list = new ArrayList<Order>();
		list = orderMapper.getOrderList(order);
		for(Order p:list){
			OrderItem oi = new OrderItem();
			oi.setOrderId(p.getId());
			oi.setFlag(1);
			oi.setOrderType(p.getOrderTypeId());
			oi.setAmount(p.getAmount());
			orderItemMapper.insertSelective(oi);
		}
	}
}
