package com.jxlt.stage.service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jxlt.stage.dao.PayMapper;
import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.PayVM;
import com.jxlt.stage.service.PayService;
@Service("payService")
public class PayServiceImpl implements PayService {

	@Resource
	private PayMapper payMapper;

	@Override
	public List<Pay> getPayList(Pay pay) {
		// TODO Auto-generated method stub
		return payMapper.getPayList(pay);
	}

	@Override
	public int getTotalCount(Pay pay) {
		// TODO Auto-generated method stub
		return payMapper.getPayCount(pay);
	}

	@Override
	public List<PayVM> getSaleAmount(Pay pay) {
		// TODO Auto-generated method stub
		List<PayVM> list = new ArrayList<PayVM>();
		PayVM pv = new PayVM();
		int i = 3;
		do{
			pay.setOrderType(i);
			pv = payMapper.getSaleAmount(pay);
			list.add(pv);
			i--;
		}while(i>=0);
		list.get(0).setName("净水系统");
		list.get(1).setName("中央空调");
		list.get(2).setName("地暖安装");
		list.get(3).setName("销售总计");
		return list;
	}

}
