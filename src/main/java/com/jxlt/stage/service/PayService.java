package com.jxlt.stage.service;

import java.util.List;

import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.PayVM;

public interface PayService {

	List<Pay> getPayList(Pay pay);

	int getTotalCount(Pay pay);

	List<PayVM> getSaleAmount(Pay pay);

}
