package com.jxlt.stage.wechat.service;

import com.jxlt.stage.common.JsonResult;
import com.jxlt.stage.model.Order;

public interface JX_UserAppointmentService {
    /**
     * 新增预约订单
     * @param order
     * @return
     */
	JsonResult<Order> addOrderInfo(Order order);

}
