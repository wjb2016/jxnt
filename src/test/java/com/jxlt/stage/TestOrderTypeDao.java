package com.jxlt.stage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.jxlt.stage.common.utils.Constants;
import com.jxlt.stage.dao.OrderTypeMapper;
import com.jxlt.stage.model.OrderType;

public class TestOrderTypeDao extends BaseTest {
	
	@Resource
	private OrderTypeMapper orderTypeMapper;
	
	/**
	 * 测试获取品牌集合
	 */
	@Test
	public void testGetItemList(){
		OrderType item = new OrderType();
		item.setParentId(new Integer(0));
		List<OrderType> list = new ArrayList<OrderType>();
		try {
			//获取品牌集合
			list = orderTypeMapper.getItemList(item);
			Assert.assertEquals(3, list.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试获取品牌总数
	 */
	@Test
	public void testGetTotalCount(){
		OrderType item = new OrderType();
		item.setParentId(new Integer(0));
		item.setPageNo(1);
		item.setPageSize(Constants.DEFAULT_PAGE_SIZE);
		List<OrderType> list = new ArrayList<OrderType>();
		try {
			//获取品牌集合
			list = orderTypeMapper.getItemList(item);
			int count = orderTypeMapper.getTotalCount(item);
			Assert.assertEquals(3, list.size());
			Assert.assertEquals(3, count);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试逻辑删除品牌类型
	 */
	@Test
	public void testDelType(){
		orderTypeMapper.deleteByPrimaryKey(11);
		OrderType ot = orderTypeMapper.selectByPrimaryKey(11);
		Assert.assertEquals(new Integer(0),ot.getFlag());
	}
	
	/**
	 * 测试根据id取品牌
	 */
	@Test
	public void testGetByTypeId(){
		OrderType ot = orderTypeMapper.getOrderTypeById(6);
		Assert.assertEquals(new Integer(2), ot.getParentId());
	}
	
	/**
	 * 测试获取重复名的品牌数量
	 */
	@Test
	public void testGetTypeByName(){
		OrderType orderType = new OrderType();
		orderType.setId(9);
		orderType.setName("测试净水一");
		int count = orderTypeMapper.getExistType(orderType);
		Assert.assertEquals(0,count);
	}
}
