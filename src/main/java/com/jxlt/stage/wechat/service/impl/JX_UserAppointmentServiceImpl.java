package com.jxlt.stage.wechat.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.dao.OrderMapper;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.wechat.service.JX_UserAppointmentService;

@Service("JX_UserAppointmentService")
public class JX_UserAppointmentServiceImpl implements JX_UserAppointmentService {
  
	@Resource
	private OrderMapper orderMapper;
	
	/**
	 * 新增预约订单
	 */
	@Override
	public JsonResult<Order> addOrderInfo(Order order) {
		JsonResult<Order> result = new JsonResult<Order>();
		result.setCode(0);
		result.setMessage("提交失败！");
		if(order != null){
			Order newOrder = new Order();
			newOrder.setStatus(0);                          // 订单状态0 下单
			newOrder.setName(order.getName());              // 姓名
			newOrder.setMobile(order.getMobile());          // 联系电话 
			newOrder.setHomeAddress(order.getHomeAddress());// 地址
			newOrder.setHouseType(order.getHouseType());    // 户型
			newOrder.setCreateTime(new Date());
			if(order.getRefMobile() == ""){               // 推荐人手机
				
			}else{
				newOrder.setRefMobile(order.getRefMobile());
			}
			orderMapper.insertSelective(newOrder);
			result.setCode(1);
			result.setMessage("提交成功！");
		}
		return result;
	}

}
