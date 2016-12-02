package com.jxlt.stage.wechat.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Order;
import com.jxlt.stage.wechat.service.JX_UserAppointmentService;

@Controller
@RequestMapping(value="Appointment")
public class JX_AppointmentController {
	@Resource
	private JX_UserAppointmentService userAppointmentService;
	
	// 我要预约订单
	@RequestMapping(value="jxAppointment.do")
	public String JXAppointment(){
		
		return "wechat/appointment/JX_appointment";
		
	}
	
	/**
	 * 新增预约订单
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="appointtemtInfo.do")
	public JsonResult<Order> appointtemtOrder(Order order){
		JsonResult<Order> result = new JsonResult<Order>();
		result = userAppointmentService.addOrderInfo(order);
		return result;
		
	}
	
}
