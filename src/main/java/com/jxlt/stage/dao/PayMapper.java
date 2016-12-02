package com.jxlt.stage.dao;

import java.util.List;

import com.jxlt.stage.model.Pay;
import com.jxlt.stage.model.PayVM;

@MyBatisRepository
public interface PayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pay record);

    int insertSelective(Pay record);

    Pay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pay record);

    int updateByPrimaryKey(Pay record);
    //获取支付列表
	List<Pay> getPayList(Pay pay);
    //获取支付记录总数
	int getPayCount(Pay pay);
	//销售统计
	PayVM getSaleAmount(Pay pay);

	//更新支付表记录
	void updatePayByOrderId(Pay pay);
    
}