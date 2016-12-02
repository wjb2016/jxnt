package com.jxlt.stage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;

import com.jxlt.stage.common.utils.DateUtil;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.dao.PayMapper;
import com.jxlt.stage.dao.ProjectMapper;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.PayVM;
import com.jxlt.stage.model.Project;

public class TestSale extends BaseTest {

	
	@Resource
	private PayMapper payMapper;
	
	@Resource
	private ProjectMapper projectMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	    /**
	     * 销售总
	     * @throws ParseException
	     */
		@Test
		public void getSaleList() throws ParseException{
			Pay pay = new Pay();
			Date startTime = DateUtil.longParse("2016-11-01 00:00:00");
			Date endTime = DateUtil.longParse("2016-11-11 00:00:00");
			pay.setStartTime(startTime);
			pay.setEndTime(endTime);
			pay.setOrderType(1);
			PayVM pv = payMapper.getSaleAmount(pay);
	        double amount = saleAmount(pay);
	        double assess = assessCount(pay);
	        System.err.println(pv.getAssessCount());
			//Assert.assertEquals(amount,pv.getPayAmount());
			Assert.assertEquals(assess,pv.getAllAmount());
		}
        
		/**
		 * 支付金额
		 * @param pay
		 * @return
		 */
		private double saleAmount(Pay pay){
			double amount = 0;
			Order order = new Order();
			 List<Pay> list = new ArrayList<Pay>();
			    //支付列表
		        list = payMapper.getPayList(pay);
		        for(Pay p:list){
		        	//订单类型判断
		        	if(pay.getOrderType() != null && pay.getOrderType() > 0){
		        		order = orderMapper.selectByPrimaryKey(p.getOrderId());
		        		if(order == null){
		        			continue;
		        		}else if(!pay.getOrderType().equals(order.getOrderTypeId())){
		        			continue;
		        		}
		        	}
		        	//条件下支付总和
		        	amount = amount + p.getPayPrice() - p.getPayBackPrice();
		        }
		        	
			return amount;
		}
		
		/**
		 * 评价总分
		 * @param pay
		 * @return
		 */
		private double assessCount(Pay pay){
			double assess = 0;
			Order order = new Order();
			List<Pay> list = new ArrayList<Pay>();
			List<Project> plist = new ArrayList<Project>();
			list = payMapper.getPayList(pay);
	        for(Pay p:list){
	        	//订单类型判断
	        	if(pay.getOrderType() != null && pay.getOrderType() > 0){
	        		order = orderMapper.selectByPrimaryKey(p.getOrderId());
	        		if(order == null){
	        			continue;
	        		}else if(!pay.getOrderType().equals(order.getOrderTypeId())){
	        			continue;
	        		}
	        		plist = projectMapper.getProjectListById(order.getId());
	        		if(plist.size() == 0)
	        			continue;
	        		//条件下评价总分
	        		for(Project pj:plist)
	        			if(pj.getAssessValue() != null)
	        		    	assess += pj.getAssessValue();
	        	}
	        }
			return assess;
		}
}
