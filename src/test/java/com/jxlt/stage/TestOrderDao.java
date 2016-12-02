package com.jxlt.stage;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.model.Order;

public class TestOrderDao extends BaseTest {
	
	@Resource
	private OrderMapper orderMapper;
	
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
}
